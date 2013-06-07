package main;

import bo.MarketDataResponse;
import bo.TradeRequest;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class TradeCEPThrowaway {
	public static void main(String[] args){
		String timerContext = "create context SegmentedByInstrument partition by  instrumentID from bo.TradeRequest, instrumentId from bo.MarketDataResponse";
		String listenerStmt = "context SegmentedByInstrument select a.instrumentID, b.lastPrice from pattern[every a=bo.TradeRequest -> b=bo.MarketDataResponse(b.lastPrice <= a.limitPrice)]";
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new UpdateListener(){

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				System.out.println("update");
				
			}
			
		});
		
		EPRuntime runtime = epService.getEPRuntime();
		TradeRequest request1 = new TradeRequest();
		TradeRequest request2 = new TradeRequest();
		MarketDataResponse marketDataResponse2 = new MarketDataResponse();
		
		MarketDataResponse marketDataResponse = new MarketDataResponse();
		marketDataResponse.setInstrumentId("1");
		marketDataResponse2.setInstrumentId("2");
		marketDataResponse2.setLastPrice(50);
		request2.setInstrumentID("2");
		
		marketDataResponse.setLastPrice(99);
		request1.setLimitPrice(100);
		request2.setLimitPrice(1);
		request1.setInstrumentID("1");
		runtime.sendEvent(request1);
		runtime.sendEvent(request2);
		runtime.sendEvent(marketDataResponse);
		
		marketDataResponse.setLastPrice(101);
		runtime.sendEvent(marketDataResponse);
		runtime.sendEvent(marketDataResponse2);
		
		runtime.sendEvent(request1);
		marketDataResponse.setLastPrice(99);
		marketDataResponse.setLastPrice(45);
		
		runtime.sendEvent(marketDataResponse);
		runtime.sendEvent(marketDataResponse2);
	
		marketDataResponse.setLastPrice(101);
		marketDataResponse2.setLastPrice(2);
		runtime.sendEvent(marketDataResponse);
		runtime.sendEvent(marketDataResponse2);
		marketDataResponse.setLastPrice(102);
		runtime.sendEvent(marketDataResponse);
		runtime.sendEvent(marketDataResponse2);
		marketDataResponse.setLastPrice(96.5);
		marketDataResponse.setLastPrice(1.01);
		runtime.sendEvent(marketDataResponse);
		runtime.sendEvent(marketDataResponse2);
		runtime.sendEvent(marketDataResponse);
		marketDataResponse2.setLastPrice(.99);
		runtime.sendEvent(marketDataResponse2);
		runtime.sendEvent(marketDataResponse2);
		
		
	}
}
	