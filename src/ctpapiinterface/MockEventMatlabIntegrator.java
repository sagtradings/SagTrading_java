package ctpapiinterface;

import listeners.DefaultCTPListener;
import matlab.BarDataEvent;
import matlab.MarketDataEvent;
import matlab.MatlabEventListener;
import matlab.MatlabOnLoginEvent;
import matlab.MatlabOnSubscribeDataEvent;
import nativeinterfaces.MockMDNativeInterface;

import org.apache.log4j.Logger;

import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.SubscribeMarketDataResponse;


public class MockEventMatlabIntegrator {
	 private static Logger log = Logger.getLogger(MockEventMatlabIntegrator.class);
	public MockEventMatlabIntegrator(){


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
					notifyMatlabBarData(compiledData);
				}
			} catch (EntryNotInitializedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			notifyMatlabLabTickEvent(response);
		}
		
	}
	

	
	public void initialize(String mockDataFile){
		//MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
		//nativeInterface.subscribeListener(new ICMDListener());
		MockMDNativeInterface mockJNI = new MockMDNativeInterface(mockDataFile);
		mockJNI.subscribeListener(new ICCtpListener());
		mockJNI.initiateTimer();
	}
	
	public void subscribeMarketData(String instrument){
		//JOptionPane.showMessageDialog(null, "subscribing bar data");
		//new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
		LoginResponse mockResponse = new LoginResponse();
		log.info("Subscribed market data");
		notifyMatlabLogInEvent(mockResponse);
	}
	
	public void subscribeBarData(String instrument, long barLength){
		BarDataManager.getInstance().initializeEntry(instrument, barLength);
		subscribeMarketData(instrument);
		SubscribeMarketDataResponse mockResponse = new SubscribeMarketDataResponse();
		mockResponse.setSpecificInstrument(instrument);
		notifyMatlabOnSubscribeEvent(mockResponse);
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		//new MarketDataNativeInterface().subscribeListener(new ICMDListener());
		//new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId);
		
	}
	
	public void kill(){
		System.exit(0);
	}

	
	
	
	private java.util.Vector data = new java.util.Vector();
	
    public synchronized void addMatlabEventListener(MatlabEventListener lis) {
        data.addElement(lis);
    }
    public synchronized void removeMatlabEventListener(MatlabEventListener lis) {
       
    	data.removeElement(lis);
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
    
    public void notifyMatlabLogInEvent(LoginResponse response){
    	MatlabOnLoginEvent event = new MatlabOnLoginEvent(this, response);
    	for(int i = 0, n = data.size(); i < n; i++){
    		((MatlabEventListener)data.elementAt(i)).matlabOnLoginEvent(event);
    	}
    }
    
    public void notifyMatlabOnSubscribeEvent(SubscribeMarketDataResponse response){
    	MatlabOnSubscribeDataEvent event = new MatlabOnSubscribeDataEvent(this, response);
    	for(int i = 0, n = data.size(); i < n; i++){
    		((MatlabEventListener)data.elementAt(i)).matlabOnSubscribeDataEvent(event);
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
    
    
}
