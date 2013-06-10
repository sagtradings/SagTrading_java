package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nativeinterfaces.MarketDataNativeInterface;
import listeners.DefaultCTPListener;
import matlab.MatLabEvent;
import matlab.MatLabEventListener;
import matlab.MatLabTickEvent;
import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.MarketDataResponse;


public class EventMatLabIntegrator {
	
	static{
		System.loadLibrary("CTPDLL");
		
	}
	
	public EventMatLabIntegrator(){

		

	}
	
	private class ICMDListener extends DefaultCTPListener{

		private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		private long startTime = System.currentTimeMillis();
		public void onRtnDepthMarketData(MarketDataResponse response) {

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
			bardatamanager.BarDataManager barManager = bardatamanager.BarDataManager.getInstance();
			try {
				BarData compiledData = barManager.sendMarketData(response);
				if(compiledData != null){
					long elapsedTime = System.currentTimeMillis() - startTime;
					System.out.println("got marketData");
					System.out.println("Open: " + compiledData.getOpen() + " Close: " + compiledData.getClose() + " Low: " + compiledData.getLow() + " High: " + compiledData.getHigh() + " UpVolume: " + compiledData.getUpVolume() + " DownVolume: " + compiledData.getDownVolume());
					System.out.println("elapsed time: " + elapsedTime);
					startTime = System.currentTimeMillis();
					notifyMatLabBarData(compiledData);
				}
			} catch (EntryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notifyMatLabLabTickEvent(response);
		}
		
	}
	
	public void initialize(){
		MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
		nativeInterface.subscribeListener(new ICMDListener());
	}
	
	public void subscribeMarketData(String instrument){
		new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
	}
	
	public void subscribeBarData(String instrument, long barLength){
		BarDataManager.getInstance().initializeEntry(instrument, barLength);
		subscribeMarketData(instrument);
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId);
		
	}

	
	
	
	private java.util.Vector data = new java.util.Vector();
	
    public synchronized void addMatLabEventListener(MatLabEventListener lis) {
        data.addElement(lis);
    }
    public synchronized void removeMatLabEventListener(MatLabEventListener lis) {
       
    	data.removeElement(lis);
    }
    

    public void notifyMatLabLabTickEvent(MarketDataResponse response){
        java.util.Vector dataCopy;
        synchronized(this) {
            dataCopy = (java.util.Vector)data.clone();
        }
        for (int i=0; i<dataCopy.size(); i++) {
            MatLabTickEvent event = new  MatLabTickEvent(this, response);
              ((MatLabEventListener)dataCopy.elementAt(i)).matLabTickEvent(event);
        }
    }

    public void notifyMatLabBarData(BarData barData) {
        java.util.Vector dataCopy;
        synchronized(this) {
            dataCopy = (java.util.Vector)data.clone();
        }
        for (int i=0; i<dataCopy.size(); i++) {
            MatLabEvent event = new  MatLabEvent(this, barData);
              ((MatLabEventListener)dataCopy.elementAt(i)).matLabEvent(event);
        }
    }

    

}
