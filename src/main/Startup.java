package main;

import listeners.BarDataManager;
import listeners.DefaultCTPListener;
import nativeinterfaces.DefaultNativeInterface;

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
		String timerContext = "create context CtxEachSecond initiated  by pattern [timer:interval(0) or every timer:interval(1 second)]  terminated after 1 seconds";
		String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice, sum(upVolume) as upVolume, sum(downVolume) as downVolume from bo.MarketDataResponse group by instrumentId  output last when terminated";
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new BarDataManager());
		DefaultCTPListener ctpListener = new DefaultCTPListener();
		DefaultNativeInterface nativeInterface = new DefaultNativeInterface();
		nativeInterface.subscribeListener(ctpListener);
		String[] quote1 = {"IF1307"};
		String[] quote2 = {"IF1309"};
		//String[] quotes = {"IF1307", "IF1308", "IF1309"};
		new DefaultNativeInterface().sendLoginMessage("1013", "123321", "00000008");
		new DefaultNativeInterface().sendQuoteRequest(quote1);
		
		long currentTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - currentTime <= 5000);
		new DefaultNativeInterface().sendQuoteRequest(quote2);
		
		currentTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - currentTime <= 5000);
		new DefaultNativeInterface().sendUnsubscribeQuoteRequest(quote1);
		System.out.println("exiting program");
	}
}
