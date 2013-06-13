package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nativeinterfaces.MockMDNativeInterface;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import listeners.DefaultCTPListener;
import listeners.ICTPListener;
import listeners.TestCTPListener;
import matlab.BarDataEvent;
import matlab.MatLabEventListener;
import matlab.MatLabOnLoginEvent;
import matlab.MatLabOnSubscribeDataEvent;
import matlab.MarketDataEvent;
import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.SubscribeMarketDataResponse;


public class MockEventMatLabIntegrator {
	 
	public MockEventMatLabIntegrator(){


	}
	
	private class ICCtpListener extends DefaultCTPListener{

		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {

			double deltaAskPrice1 = (response.getLastPrice() - response.getAskPrice1());
			double deltaBidPrice1 = response.getLastPrice() - response.getBidPrice1();
			if(Math.abs(deltaAskPrice1) < deltaBidPrice1){
				response.setUpVolume(response.getVolume());
			}
			else{
				response.setDownVolume(response.getVolume());
			}
			bardatamanager.BarDataManager barManager = bardatamanager.BarDataManager.getInstance();
			try {
				BarData compiledData = barManager.sendMarketData(response);
				if(compiledData != null){
					notifyMatLabBarData(compiledData);
				}
			} catch (EntryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			notifyMatLabLabTickEvent(response);
		}
		
	}
	

	
	public void initialize(String mockDataFile){
		//MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
		//nativeInterface.subscribeListener(new ICMDListener());
		MockMDNativeInterface mockJNI = new MockMDNativeInterface(mockDataFile);
		mockJNI.subscribeListener(new ICCtpListener());
	}
	
	public void subscribeMarketData(String instrument){
		//JOptionPane.showMessageDialog(null, "subscribing bar data");
		//new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
		LoginResponse mockResponse = new LoginResponse();
		
		notifyMatLabLogInEvent(mockResponse);
	}
	
	public void subscribeBarData(String instrument, long barLength){
		BarDataManager.getInstance().initializeEntry(instrument, barLength);
		subscribeMarketData(instrument);
		SubscribeMarketDataResponse mockResponse = new SubscribeMarketDataResponse();
		mockResponse.setSpecificInstrument(instrument);
		notifyMatLabOnSubscribeEvent(mockResponse);
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		//new MarketDataNativeInterface().subscribeListener(new ICMDListener());
		//new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId);
		
	}
	
	public void kill(){
		System.exit(0);
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
            MarketDataEvent event = new  MarketDataEvent(this, response);
              ((MatLabEventListener)dataCopy.elementAt(i)).marketDataEvent(event);
        }
    }
    
    public void notifyMatLabLogInEvent(LoginResponse response){
    	MatLabOnLoginEvent event = new MatLabOnLoginEvent(this, response);
    	for(int i = 0, n = data.size(); i < n; i++){
    		((MatLabEventListener)data.elementAt(i)).matLabOnLoginEvent(event);
    	}
    }
    
    public void notifyMatLabOnSubscribeEvent(SubscribeMarketDataResponse response){
    	MatLabOnSubscribeDataEvent event = new MatLabOnSubscribeDataEvent(this, response);
    	for(int i = 0, n = data.size(); i < n; i++){
    		((MatLabEventListener)data.elementAt(i)).matLabOnSubscribeDataEvent(event);
    	}
    }

    public void notifyMatLabBarData(BarData barData) {
        java.util.Vector dataCopy;
        synchronized(this) {
            dataCopy = (java.util.Vector)data.clone();
        }
        for (int i=0; i<dataCopy.size(); i++) {
            BarDataEvent event = new  BarDataEvent(this, barData);
              ((MatLabEventListener)dataCopy.elementAt(i)).barDataEvent(event);
        }
    }
    
    
}
