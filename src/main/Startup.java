package main;

import listeners.BarDataManager;
import listeners.DefaultCTPListener;
import listeners.NonEsperMarketDataListener;
import nativeinterfaces.MarketDataNativeInterface;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Startup {
	
	static{
		System.loadLibrary("CTPDLL");

		
		System.out.println("library loaded");
	}
	
	public static void main(String[] args){
		System.out.println(System.getProperty("java.library.path"));
		System.out.println(System.getProperty("path"));
		String timerContext = "create context CtxEachSecond initiated  by pattern [timer:at(*, *, *, *, *, 1) or every timer:interval(1000 msec)]  terminated after 1 seconds";
		String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice, sum(upVolume) as upVolume, sum(downVolume) as downVolume from bo.MarketDataResponse group by instrumentId  output last when terminated";
		
	//	EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		//epService.getEPAdministrator().createEPL(timerContext);
		//EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		//statement.addListener(new BarDataManager());
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
		while(System.currentTimeMillis() - currentTime <= 5000);
		//new MarketDataNativeInterface().sendQuoteRequest(quote2);
		
		currentTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - currentTime <= 5000);
		//new DefaultNativeInterface().sendUnsubscribeQuoteRequest(quote1);
		System.out.println("exiting program");
	}
}
