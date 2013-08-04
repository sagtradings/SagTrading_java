package ctpapiinterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.BarDataDAO;
import dao.MarketDataDAO;

import properties.PropertiesManager;

import listeners.DefaultCTPListener;
import matlab.BarDataEvent;
import matlab.MarketDataEvent;
import matlab.MatlabEventListener;
import matlab.MatlabOnLoginEvent;
import nativeinterfaces.MarketDataNativeInterface;
import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.LoginResponse;
import bo.MarketDataResponse;


public class EventMatlabIntegrator {
	
	BarDataManager barManager;
	private static final  String connectionURL =PropertiesManager.getInstance().getProperty("marketdataurl");
	
	
	public EventMatlabIntegrator(){
		barManager = new BarDataManager();
		System.loadLibrary("CTPDLL");

	}
	
	private class ICMDListener extends DefaultCTPListener{

		private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		@Override
		public void onRspUserLogin(LoginResponse loginResponse) {
			notifyLoginEvent(new MatlabOnLoginEvent(loginResponse));
		}
		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {
			MarketDataDAO dao = new MarketDataDAO();
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
	
	public void subscribeBarData(String instrument, long barLength){
		barManager.initializeEntry(instrument, barLength);
		subscribeMarketData(instrument);
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
