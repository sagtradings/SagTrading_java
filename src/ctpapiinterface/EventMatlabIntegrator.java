package ctpapiinterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.BarDataDAO;
import dao.MarketDataDAO;

import properties.PropertiesManager;

import listeners.DefaultCTPListener;
import matlab.BarDataEvent;
import matlab.MarketDataEvent;
import matlab.MatlabEventListener;
import matlab.MatlabOnLoginEvent;
import nativeinterfaces.MarketDataNativeInterface;
import bardatamanager.BarDataComposer;
import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bardatamanager.TimeIntervalNotDivisibleByTenException;
import bo.BarData;
import bo.LoginResponse;
import bo.MarketDataResponse;


public class EventMatlabIntegrator {
	
	BarDataManager barManager;
	private static final  String connectionURL =PropertiesManager.getInstance().getProperty("marketdataurl");
	private String instrumentOfInterest;
	private MarketDataDAO dao = new MarketDataDAO();
	private BarDataDAO barDao = new BarDataDAO();
	private List<BarData> inMemoryBarData = new ArrayList<BarData>(10);
	public EventMatlabIntegrator(){
		barManager = new BarDataManager();
		System.loadLibrary("CTPDLL");

	}
	
	public double getLowest(String value, int numberOfBars){
		double lowestValue = Double.MAX_VALUE;
		if(value.equalsIgnoreCase("open")){
			for(int i = inMemoryBarData.size() - 1; i >= 0 && i >= inMemoryBarData.size() - numberOfBars; i--){
				if(inMemoryBarData.get(i).getOpen() < lowestValue){
					lowestValue = inMemoryBarData.get(i).getOpen();
				}
			}
			return lowestValue;
		}
		else if(value.equalsIgnoreCase("low")){
			for(int i = inMemoryBarData.size() - 1; i >= 0 && i >= inMemoryBarData.size() - numberOfBars; i--){
				if(inMemoryBarData.get(i).getLow() < lowestValue){
					lowestValue = inMemoryBarData.get(i).getLow();
				}
			}
			return lowestValue;
		}
		else if(value.equalsIgnoreCase("close")){
			for(int i = inMemoryBarData.size() - 1; i >= 0 && i >= inMemoryBarData.size() - numberOfBars; i--){
				if(inMemoryBarData.get(i).getClose() < lowestValue){
					lowestValue = inMemoryBarData.get(i).getClose();
				}
			}
			return lowestValue;
		}
		
		return 0;
	}
	
	
	public double getHighest(String value, int numberOfBars){
		double highestValue = Double.MIN_VALUE;
		if(value.equalsIgnoreCase("open")){
			for(int i = inMemoryBarData.size() - 1; i >= 0 && i >= inMemoryBarData.size() - numberOfBars; i--){
				if(inMemoryBarData.get(i).getOpen() > highestValue){
					highestValue = inMemoryBarData.get(i).getOpen();
				}
			}
			return highestValue;
		}
		else if(value.equalsIgnoreCase("low")){
			for(int i = inMemoryBarData.size() - 1; i >= 0 && i >= inMemoryBarData.size() - numberOfBars; i--){
				if(inMemoryBarData.get(i).getLow() > highestValue){
					highestValue = inMemoryBarData.get(i).getLow();
				}
			}
			return highestValue;
		}
		else if(value.equalsIgnoreCase("close")){
			for(int i = inMemoryBarData.size() - 1; i >= 0 && i >= inMemoryBarData.size() - numberOfBars; i--){
				if(inMemoryBarData.get(i).getClose() > highestValue){
					highestValue = inMemoryBarData.get(i).getClose();
				}
			}
			return highestValue;
		}
		
		return 0;
	}
	 
	private class ICMDListener extends DefaultCTPListener{

		private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		@Override
		public void onRspUserLogin(LoginResponse loginResponse) {
			notifyLoginEvent(new MatlabOnLoginEvent(loginResponse));
		}
		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {
			
			dao.addMarketData(response);
			double deltaAskPrice1 = (response.getLastPrice() - response.getAskPrice1());
			double deltaBidPrice1 = response.getLastPrice() - response.getBidPrice1();
			if(Math.abs(deltaAskPrice1) < deltaBidPrice1){
				response.setUpVolume(response.getVolume());
			}
			else{
				response.setDownVolume(response.getVolume());
			}
			try {
				Date updateTime = formatter.parse(response.getTradingDay() + response.getUpdateTime());
				response.setMillisecConversionTime(updateTime.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//BarDataManager.
			
			try {
				
				BarData compiledData = barManager.sendMarketData(response);
				if(compiledData != null){
					BarDataDAO bao = new BarDataDAO();
					bao.addBarData(compiledData);
					notifyMatlabBarData(compiledData);
					inMemoryBarData.add(compiledData);
				}
			} catch (EntryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notifyMatlabLabTickEvent(response);
		}
		
	}
	
	public void initialize(){
		MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
		nativeInterface.subscribeListener(new ICMDListener());
	}
	
	public void subscribeMarketData(String instrument){
		//JOptionPane.showMessageDialog(null, "subscribing bar data");
		new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
	}
	
	public void subscribeBarData(String instrument, long barLength) throws TimeIntervalNotDivisibleByTenException{
		if(barLength % 10000 != 0){
			throw new TimeIntervalNotDivisibleByTenException("Bar length must be evenly divisble by 10 seconds");
		}
		barManager.initializeEntry(instrument, barLength);
		subscribeMarketData(instrument);
		instrumentOfInterest = instrument;
		Date currentDate = Calendar.getInstance().getTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DATE, -100);
		Date startDate = endCalendar.getTime();
		List<BarData> previousBars = barDao.getAllBarDataByDateRange(startDate, currentDate, instrument);
		BarDataComposer composer = new BarDataComposer();
		List<BarData> historicalData = composer.composeList(previousBars, barLength);
		for(int i = 0, n = historicalData.size(); i < n; i++){
			inMemoryBarData.add(historicalData.get(i));
		}
		
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		new MarketDataNativeInterface().subscribeListener(new ICMDListener());
		new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId, connectionURL);
		
		
	}

	
	
	
	private java.util.Vector data = new java.util.Vector();
	
    public synchronized void addMatlabEventListener(MatlabEventListener lis) {
        data.addElement(lis);
    }
    public synchronized void removeMatlabEventListener(MatlabEventListener lis) {
       
    	data.removeElement(lis);
    }
    
    public synchronized void notifyLoginEvent(MatlabOnLoginEvent loginEvent){
		for(int i = 0, n = data.size(); i  < n; i++){
			((MatlabEventListener)data.elementAt(i)).matlabOnLoginEvent(loginEvent);
		}
    }
    

    public void notifyMatlabLabTickEvent(MarketDataResponse response){
        java.util.Vector dataCopy;
        synchronized(this) {
            dataCopy = (java.util.Vector)data.clone();
        }
        for (int i=0; i<dataCopy.size(); i++) {
            MarketDataEvent event = new  MarketDataEvent(this, response);
              ((MatlabEventListener)dataCopy.elementAt(i)).marketDataEvent(event);
        }
    }

    public void notifyMatlabBarData(BarData barData) {
        java.util.Vector dataCopy;
        synchronized(this) {
            dataCopy = (java.util.Vector)data.clone();
        }
        for (int i=0; i<dataCopy.size(); i++) {
            BarDataEvent event = new  BarDataEvent(this, barData);
              ((MatlabEventListener)dataCopy.elementAt(i)).barDataEvent(event);
        }
    }
    
    public void release(){
    	data.removeAllElements();
    	new MarketDataNativeInterface().unSubscribeListener(null);
    	
    	
    }

    

}
