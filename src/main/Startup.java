package main;

import java.util.Calendar;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import nativeinterfaces.DefaultNativeInterface;
import listeners.BarDataManager;
import listeners.DefaultCTPListener;
import listeners.ICTPListener;
import listeners.TestCTPListener;

public class Startup {
	
	static{
		System.loadLibrary("CTPDLL");
		System.out.println("library loaded");
	}
	
	public static void main(String[] args){

		System.out.println("helloxxxxyz");
		
		//String timerContext = "create context CtxEachSecond initiated  by pattern [every timer:interval(1 sec)] terminated after 5 minutes";
		//String listenerStmt = "context CtxEachSecond select min(lastPrice) as minPrrice, max(lastPrice) as maxPrice from bo.MarketDataResponse group by instrumentId output snapshot every 1 seconds";
		//String timerContext = "create context CtxEachSecond initiated  by pattern [every timer:interval(5 seconds)] terminated after 5 minutes";
		//String timerContext = "create context CtxEachSecond start pattern[every bo.MarketDataResponse] end after 10 sec";
		//String listenerStmt = "context CtxEachSecond select lastPrice from bo.MarketDataResponse  output snapshot every 5 seconds";
		//String listenerStmt = "context CtxEachSecond select lastPrice from bo.MarketDataResponse  output snapshot every 5 seconds";
		
		String timerContext = "create context CtxEachSecond initiated  by pattern [timer:interval(0) or every timer:interval(1 second)]  terminated after 1 seconds";
		String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice, sum(upVolume) as upVolume, sum(downVolume) as downVolume from bo.MarketDataResponse group by instrumentId  output last when terminated";
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		//epService.getEPAdministrator().createEPL(contextStmt);
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new BarDataManager());
		//ICTPListener ctpListener = new TestCTPListener("20130522_103040\\rawdata.csv");
		//while(true);
		DefaultCTPListener ctpListener = new DefaultCTPListener();
		DefaultNativeInterface nativeInterface = new DefaultNativeInterface();
		nativeInterface.subscribeListener(ctpListener);
		nativeInterface.sendQuoteRequest(new String[]{});
	}
}
