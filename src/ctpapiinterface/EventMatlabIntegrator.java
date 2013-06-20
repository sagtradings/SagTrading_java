package ctpapiinterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import listeners.DefaultCTPListener;
import matlab.BarDataEvent;
import matlab.MarketDataEvent;
import matlab.MatlabEventListener;
import nativeinterfaces.MarketDataNativeInterface;
import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.MarketDataResponse;


public class EventMatlabIntegrator {
	
	static{
		System.loadLibrary("CTPDLL");
		
	}
	
	public EventMatlabIntegrator(){

		

	}
	
	private class ICMDListener extends DefaultCTPListener{

		private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
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
		BarDataManager.getInstance().initializeEntry(instrument, barLength);
		subscribeMarketData(instrument);
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		new MarketDataNativeInterface().subscribeListener(new ICMDListener());
		new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId);
		
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
