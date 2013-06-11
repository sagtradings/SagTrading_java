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
import matlab.MatLabEvent;
import matlab.MatLabEventListener;
import matlab.MatLabTickEvent;
import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.MarketDataResponse;


public class MockEventMatLabIntegrator {
	 
	public MockEventMatLabIntegrator(String mockDataFile){
		MockMDNativeInterface mockJNI = new MockMDNativeInterface(mockDataFile);
		mockJNI.subscribeListener(new ICCtpListener());

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
	

	
	public void initialize(){
		//MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
		//nativeInterface.subscribeListener(new ICMDListener());
	}
	
	public void subscribeMarketData(String instrument){
		//JOptionPane.showMessageDialog(null, "subscribing bar data");
		//new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
	}
	
	public void subscribeBarData(String instrument, long barLength){
		BarDataManager.getInstance().initializeEntry(instrument, barLength);
		subscribeMarketData(instrument);
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		//new MarketDataNativeInterface().subscribeListener(new ICMDListener());
		//new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId);
		
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
