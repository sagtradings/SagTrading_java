package main;

import listeners.DefaultCTPListener;
import listeners.NonEsperMarketDataListener;
import nativeinterfaces.MarketDataNativeInterface;

public class Startup {
	

    static {
        try {
            System.loadLibrary("CTPDLL");
            System.out.println("library loadedxfxxxxx");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(System.getProperty("path"));

        DefaultCTPListener ctpListener = new NonEsperMarketDataListener();
        MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
        nativeInterface.subscribeListener(ctpListener);
        String[] quote1 = {"IF1307"};
        String[] quote2 = {"IF1309"};
        //String[] quotes = {"IF1307", "IF1308", "IF1309"};
        bardatamanager.BarDataManager.getInstance().initializeEntry("IF1307", 5000);
        bardatamanager.BarDataManager.getInstance().initializeEntry("IF1309", 10000);
        new MarketDataNativeInterface().sendLoginMessage("1013", "123321", "00000008");
        new MarketDataNativeInterface().sendQuoteRequest(quote1);
        new MarketDataNativeInterface().sendQuoteRequest(quote2);
        long currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime <= 5000) ;
        //new MarketDataNativeInterface().sendQuoteRequest(quote2);

        currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime <= 5000) ;
        //new DefaultNativeInterface().sendUnsubscribeQuoteRequest(quote1);
        System.out.println("exiting program");
    }
}
