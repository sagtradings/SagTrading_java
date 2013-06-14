package main;

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
		String timerContext = "create context CtxEachSecond initiated  by pattern [timer:interval(0) or every timer:interval(5 second)]  terminated after 5 seconds";
		String listenerStmt = "context CtxEachSecond select instrumentId, first(lastPrice) as openPrice, lastPrice as closePrice, min(lastPrice) as minPrice, max(lastPrice) as maxPrice from bo.MarketDataResponse group by instrumentId  output last when terminated";
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new UpdateListener(){
			public void update(EventBean[] arg0, EventBean[] arg1) {
				
					for(int k = 0, n = arg0.length; k < n; k++){
						System.out.println(" arg0["+k+"]instrumentId: " +arg0[k].get("instrumentId") +" minPrice: " + arg0[k].get("minPrice") + " maxPrice: " + arg0[k].get("maxPrice") + " closePrice: " + arg0[k].get("closePrice") + "openPrice: " + arg0[k].get("openPrice"));
					}				
				}
			});
		

		EPRuntime runtime = epService.getEPRuntime();
		double i = 0;
		
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
			
			Thread.sleep(1000);
		}
		
		
	}
}
