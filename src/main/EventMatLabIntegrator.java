package main;

import javax.swing.JOptionPane;

import nativeinterfaces.DefaultNativeInterface;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import listeners.BarDataManager;
import listeners.DefaultCTPListener;
import listeners.ICTPListener;
import listeners.TestCTPListener;

import matlab.MatLabEvent;
import matlab.MatLabEventListener;
import bo.BarData;
import bo.MarketDataResponse;

public class EventMatLabIntegrator {
	
	static{
		System.loadLibrary("CTPDLL");
		
	}
	
	public EventMatLabIntegrator(){

		

	}
	
	public void initialize(){
		String timerContext = "create context CtxEachSecond initiated  by pattern [timer:interval(0) or every timer:interval(1 second)]  terminated after 1 seconds";
		String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice, sum(upVolume) as upVolume, sum(downVolume) as downVolume from bo.MarketDataResponse group by instrumentId  output last when terminated";
		BarData testa = new BarData();
		BarData testb = new BarData();
		BarData testc = new BarData();
		testa.setClose(-1);
		testb.setClose(-2);
		testc.setClose(-3);
		notifyMatLabBarData(testa);
		//EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		//epService.getEPAdministrator().createEPL(timerContext);
		notifyMatLabBarData(testb);
		//EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		//statement.addListener(new ICBarDataManager());
		notifyMatLabBarData(testb);
		//ICTPListener ctpListener = new TestCTPListener("C:\\Users\\bgussiaas\\git\\SagTrading4\\src\\20130522_103040\\rawdata.csv");
		BarData test1 = new BarData();
		BarData test2 = new BarData();
		test1.setClose(1);
		test2.setClose(2);
		notifyMatLabBarData(test1);
		DefaultCTPListener ctpListener = new DefaultCTPListener();
		DefaultNativeInterface nativeInterface = new DefaultNativeInterface();
		nativeInterface.subscribeListener(ctpListener);
		nativeInterface.sendQuoteRequest(new String[]{});
		notifyMatLabBarData(test2);
		
		
		//while(true);
	}
	
	private class ICBarDataManager implements UpdateListener{

		@Override
		public void update(EventBean[] arg0, EventBean[] arg1) {
			
			 
			for(int k = 0, n = arg0.length; k < n; k++){
				BarData barData = new BarData();
				barData.setClose((Double) arg0[k].get("closePrice"));
				barData.setDownVolume((Double) arg0[k].get("downVolume"));
				barData.setHigh((Double) arg0[k].get("maxPrice"));
				barData.setLow((Double) arg0[k].get("minPrice"));
				barData.setOpen((Double) arg0[k].get("openPrice"));
				barData.setUpVolume((Double) arg0[k].get("upVolume"));
				notifyMatLabBarData(barData);
				//System.out.println(" arg0["+k+"]instrumentId: " +arg0[k].get("instrumentId") +" minPrice: " +  + " maxPrice: " +  + " closePrice: " +  + "openPrice: " + + " upVolume: " +  + " downVolume: " + );
			}
			
			
		}
		
	}
	
	
	
	private java.util.Vector data = new java.util.Vector();
	
    public synchronized void addMatLabEventListener(MatLabEventListener lis) {
        data.addElement(lis);
    }
    public synchronized void removeMatLabEventListener(MatLabEventListener lis) {
       
    	data.removeElement(lis);
    }
    

    

    public void notifyMatLabBarData(BarData barData) {
        //java.util.Vector dataCopy;
        JOptionPane.showMessageDialog(null, barData.getHigh());
        synchronized(this) {
          //  dataCopy = (java.util.Vector)data.clone();
        }
        //float price = (float) (Math.random() * 100);
        for (int i=0; i<data.size(); i++) {
            MatLabEvent event = new  MatLabEvent(this, barData);
              ((MatLabEventListener)data.elementAt(i)).matLabEvent(event);
        }
    }
    

}
