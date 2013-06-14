package main;

import orderrefgenerator.MessageIDGenerator;
import orderrefgenerator.OrderRefGenerator;
import orderrepository.IncompleteBucketException;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;
import listeners.StopLossListener;
import listeners.TradeListener;
import listeners.TradeMarketDataListener;
import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import bo.TradeRequest;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class TradeThrowAway {

	static{
		System.loadLibrary("CTPTRADEDLL");
		System.loadLibrary("CTPDLL");
		
		System.out.println("TRADEDLL LOADED");
		
	}
	
	public static void main(String[] args){
		TradingNativeInterface tradingNativeInterface = new TradingNativeInterface();
		tradingNativeInterface.subscribeListener(new TradeListener());
		MarketDataNativeInterface marketDataNativeInterface = new MarketDataNativeInterface();
		marketDataNativeInterface.subscribeListener(new TradeMarketDataListener());
		marketDataNativeInterface.sendLoginMessage("1013", "123321", "00000008");
		new TradingNativeInterface().sendLoginMessage("1013", "123321", "00000008");
		
		String timerContext = "create context SegmentedByInstrument partition by  instrumentID from bo.TradeRequest, instrumentId from bo.MarketDataResponse";
		String listenerStmt = "context SegmentedByInstrument select a.instrumentID, b.lastPrice from pattern[every a=bo.TradeRequest -> b=bo.MarketDataResponse(b.lastPrice <= a.cutOffPrice)]";
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new StopLossListener());
		
		TradeRequest initialRequest = new TradeRequest();
		initialRequest.setDirection("0");
		//initialRequest.setDirection("1");
		initialRequest.setOrderPriceType("2");
		initialRequest.setCombOffsetFlag("0");
		initialRequest.setCombHedgeFlag("1");
		initialRequest.setLimitPrice(2399);
		initialRequest.setGtdDate("");
		initialRequest.setVolumeCondition("1");
		initialRequest.setMinVolume(1);
		initialRequest.setContingentCondition("1");
		initialRequest.setStopPrice(0);
		initialRequest.setForceCloseReason("0");
		initialRequest.setAutoSuspend(0);
		initialRequest.setVolumeTotalOriginal(1);
		initialRequest.setTimeCondition("3");
		initialRequest.setInstrumentID("IF1307");
		initialRequest.setOrderRef(OrderRefGenerator.getInstance().getNextRef());
		initialRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		
		TradeRequest exitRequest = new TradeRequest();
		exitRequest.setDirection("1");
		exitRequest.setOrderPriceType("2");
		exitRequest.setCombOffsetFlag("0");
		exitRequest.setCombHedgeFlag("1");
		exitRequest.setLimitPrice(10000);
		exitRequest.setGtdDate("");
		exitRequest.setVolumeCondition("1");
		exitRequest.setMinVolume(1);
		exitRequest.setContingentCondition("1");
		exitRequest.setStopPrice(0);
		exitRequest.setForceCloseReason("0");
		exitRequest.setAutoSuspend(0);
		exitRequest.setVolumeTotalOriginal(1);
		exitRequest.setTimeCondition("3");
		exitRequest.setInstrumentID("IF1307");
		exitRequest.setOrderRef(OrderRefGenerator.getInstance().getNextRef());
		exitRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		
		TradeRequest stopLossRequest = new TradeRequest();
		stopLossRequest.setDirection("1");
		stopLossRequest.setOrderPriceType("2");
		stopLossRequest.setCombOffsetFlag("0");
		stopLossRequest.setCombHedgeFlag("1");
		stopLossRequest.setLimitPrice(2386);
		stopLossRequest.setGtdDate("");
		stopLossRequest.setVolumeCondition("1");
		stopLossRequest.setMinVolume(1);
		stopLossRequest.setContingentCondition("1");
		stopLossRequest.setStopPrice(0);
		stopLossRequest.setForceCloseReason("0");
		stopLossRequest.setAutoSuspend(0);
		stopLossRequest.setVolumeTotalOriginal(1);
		stopLossRequest.setTimeCondition("3");
		stopLossRequest.setInstrumentID("IF1307");
		stopLossRequest.setOrderRef(OrderRefGenerator.getInstance().getNextRef());
		stopLossRequest.setCutOffPrice(2590);
		stopLossRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		
		OrderBucket bucket = new OrderBucket();
		bucket.setInitialRequest(initialRequest);
		bucket.setExitRequest(exitRequest);
		bucket.setStopLossRequest(stopLossRequest);
		bucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
		try {
			OrderRepository.getInstance().addOrderBucket(initialRequest.getInstrumentID(), bucket);
		} catch (IncompleteBucketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new TradingNativeInterface().sendSettlementReqest("1013", "00000008");
		tradingNativeInterface.sendTradeRequest("1013", "123321", "00000008", initialRequest);

		System.out.println("exiting program");
	}
}
