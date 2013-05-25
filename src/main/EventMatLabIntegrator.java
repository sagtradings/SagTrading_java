package main;

import bo.BarData;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import listeners.ICTPListener;
import listeners.TestCTPListener;
import matlab.MatLabEvent;
import matlab.MatLabEventListener;

public class EventMatLabIntegrator {

    static {
        System.loadLibrary("CTPDLL");
        System.out.println("library loaded");
    }

    public EventMatLabIntegrator() {


        String timerContext = "create context CtxEachSecond initiated  by pattern [timer:interval(0) or every timer:interval(1 second)]  terminated after 1 seconds";
        String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice, sum(upVolume) as upVolume, sum(downVolume) as downVolume from bo.MarketDataResponse group by instrumentId  output last when terminated";
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        epService.getEPAdministrator().createEPL(timerContext);
        EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
        statement.addListener(new ICBarDataManager());
        ICTPListener ctpListener = new TestCTPListener("C:\\Users\\bgussiaas\\git\\SagTrading4\\src\\20130522_103040\\rawdata.csv");

    }

    private class ICBarDataManager implements UpdateListener {

        @Override
        public void update(EventBean[] arg0, EventBean[] arg1) {
            //JOptionPane.showMessageDialog(null, arg0.length);

            for (int k = 0, n = arg0.length; k < n; k++) {
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
        java.util.Vector dataCopy;

        synchronized (this) {
            dataCopy = (java.util.Vector) data.clone();
        }
        float price = (float) (Math.random() * 100);
        for (int i = 0; i < dataCopy.size(); i++) {
            MatLabEvent event = new MatLabEvent(this, barData);
            ((MatLabEventListener) dataCopy.elementAt(i)).matLabEvent(event);
        }
    }


}
