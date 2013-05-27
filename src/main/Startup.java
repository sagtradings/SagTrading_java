package main;

import java.util.Calendar;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

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
		
		String timerContext = "create context CtxEachSecond initiated  by pattern [timer:interval(0) or every timer:interval(1 second)]  terminated after 1 seconds";
		String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice, sum(upVolume) as upVolume, sum(downVolume) as downVolume from bo.MarketDataResponse group by instrumentId  output last when terminated";
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new BarDataManager());
		DefaultCTPListener ctpListener = new DefaultCTPListener();
		DefaultNativeInterface nativeInterface = new DefaultNativeInterface();
		nativeInterface.subscribeListener(ctpListener);
		nativeInterface.sendQuoteRequest(new String[]{});
	}
}
