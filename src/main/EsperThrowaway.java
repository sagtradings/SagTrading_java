package main;

import listeners.BarDataManager;
import listeners.DefaultCTPListener;
import nativeinterfaces.MarketDataNativeInterface;

import bo.MarketDataResponse;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class EsperThrowaway {
	public static void main(String[] args) throws InterruptedException{
		System.out.println("helloxxxxyz");
		//String timerContext = "create context RawMarketData partition by instrumentId from bo.MarketDataResponse ";
		String timerContext = "create context CtxEachSecond initiated  by pattern [timer:interval(0) or every timer:interval(5 second)]  terminated after 5 seconds";
		//String timerContext = "create context CtxEachSecond initiated  by bo.MarketDataResponse   terminated after 5 seconds";
		//String listenerStmt = "context CtxEachSecond select lastPrice as lastPrice, instrumentId as instrumentId, timeOfEvent as timeOfEvent from bo.MarketDataResponse  output when terminated";
		String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice from bo.MarketDataResponse group by instrumentId  output last when terminated";
		//String listenerStmt = "select lastPrice as lastPrice from bo.MarketDataResponse output snapshot every 5 seconds ";
		//String listenerStmt = "context RawMarketData select lastPrice as lastPrice from bo.MarketDataResponse.win:time(5 seconds) output snapshot every 5 seconds ";
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new UpdateListener(){
			
			private long time = System.currentTimeMillis();
			public void update(EventBean[] arg0, EventBean[] arg1) {
				
				for(int k = 0, n = arg0.length; k < n; k++){
					
					System.out.println(" arg0["+k+"]instrumentId: " +arg0[k].get("instrumentId") +" minPrice: " + arg0[k].get("minPrice") + " maxPrice: " + arg0[k].get("maxPrice") + " closePrice: " + arg0[k].get("closePrice") + "openPrice: " + arg0[k].get("openPrice"));
				}
				//System.exit(0);
				
			}
		}
				);
		

		EPRuntime runtime = epService.getEPRuntime();
		double i = 0;
		int x = 0;
		while(true){
			MarketDataResponse event = new MarketDataResponse();
			MarketDataResponse event2 = new MarketDataResponse();
			//i  += (x % 2 == 0 ? 2 : - 3 );
			event.setInstrumentId("1");
			event.setLastPrice(i++);
			event2.setInstrumentId("2");
			event2.setLastPrice(i + 1);
			runtime.sendEvent(event);
			runtime.sendEvent(event2);
			x++;
			Thread.sleep(1000);
		}
		
		
	}
}
