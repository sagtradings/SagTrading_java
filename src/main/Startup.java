package main;



import properties.PropertiesManager;
import listeners.DefaultCTPListener;
import listeners.NonEsperMarketDataListener;
import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;

//import org.apache.log4j.Logger;

public class Startup {
	
	//private static Logger log = Logger.getLogger(Startup.class);
	private static final String marketURL = "tcp://180.166.165.179:41213";
	private static final String tradeConnectionURL = "tcp://180.166.165.179:41205";
	
	//private static final String marketURL = "xxx";
	static {
        try {
            System.loadLibrary("CTPDLL");
            System.loadLibrary("CTPTRADEDLL");
            System.out.println("library loadedxfxxxxx");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(System.getProperty("path"));
        PropertiesManager.getInstance();
       // log.info("A test messae");
        //log.debug("A test messacgsex");
        DefaultCTPListener ctpListener = new NonEsperMarketDataListener();
        MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
      System.out.println("Gussiaas");
         nativeInterface.subscribeListener(ctpListener);
        String[] quote1 = {"IF1307"};
        String[] quote2 = {"IF1309"};
        //String[] quotes = {"IF1307", "IF1308", "IF1309"};
        bardatamanager.BarDataManager.getInstance().initializeEntry("IF1307", 5000);
        bardatamanager.BarDataManager.getInstance().initializeEntry("IF1309", 10000);
        
        new MarketDataNativeInterface().sendLoginMessage("1013", "123321", "00000008", PropertiesManager.getInstance().getProperty("marketdataurl"));
        new TradingNativeInterface().sendLoginMessage("1013", "123321", "00000008", PropertiesManager.getInstance().getProperty("tradedataurl"));
        new MarketDataNativeInterface().sendQuoteRequest(quote1);
        new MarketDataNativeInterface().sendQuoteRequest(quote2);
        long currentTime = System.currentTimeMillis();
       // while (System.currentTimeMillis() - currentTime <= 5000) ;
        //new MarketDataNativeInterface().sendQuoteRequest(quote2);
        
        currentTime = System.currentTimeMillis();
      //  while (System.currentTimeMillis() - currentTime <= 5000) ;
        //new DefaultNativeInterface().sendUnsubscribeQuoteRequest(quote1);
        System.out.println("exiting program");
       // new MarketDataNativeInterface().unSubscribeListener(null);
        currentTime = System.currentTimeMillis();
       // while(System.currentTimeMillis() - currentTime <= 20000);
        System.out.println("all done!");
       // new MarketDataNativeInterface().unSubscribeListener(null);
        
        nativeInterface.subscribeListener(ctpListener);
        new MarketDataNativeInterface().sendLoginMessage("1013", "123321", "00000008", marketURL);
      //  new MarketDataNativeInterface().unSubscribeListener(null);
        
        nativeInterface.subscribeListener(ctpListener);
        new MarketDataNativeInterface().sendLoginMessage("1013", "123321", "00000008", marketURL);
       // new MarketDataNativeInterface().unSubscribeListener(null);
        while(true);

    }
}
