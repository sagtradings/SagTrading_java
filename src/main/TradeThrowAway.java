package main;

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
		//TradingNativeInterface testInterface = new TradingNativeInterface();
		
		

		
		//const char  *userID = "00000008";
		//const char  *password = "123321";
		//const char  *brokerID = "1013";
		//new Thread(new TradeIntegratorThread("1013", "123321", "00000008")).start();
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
		initialRequest.setOrderRef("00000000002");
		initialRequest.setRequestID(1);
		
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
		exitRequest.setOrderRef("00000000003");
		exitRequest.setRequestID(2);
		
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
		stopLossRequest.setOrderRef("00000000004");
		stopLossRequest.setCutOffPrice(2590);
		stopLossRequest.setRequestID(3);
		
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
		
		

		/*orderField.Direction = THOST_FTDC_D_Sell;
		orderField.OrderPriceType = THOST_FTDC_OPT_LimitPrice;
		//orderField.Direction = THOST_FTDC_D_Buy;
		strcpy_s(orderField.CombOffsetFlag, "0");
		strcpy_s(orderField.CombHedgeFlag, "1");
		orderField.LimitPrice = 2600; 
		strcpy_s(orderField.GTDDate, "");
		orderField.VolumeCondition = THOST_FTDC_VC_AV;
		orderField.MinVolume = 0;
		orderField.ContingentCondition = THOST_FTDC_CC_Immediately;
		orderField.StopPrice = 0;
		orderField.ForceCloseReason = THOST_FTDC_FCC_NotForceClose;
		orderField.IsAutoSuspend = 0;
		orderField.VolumeTotalOriginal = 10;
		orderField.TimeCondition = THOST_FTDC_TC_GFD; */
		new TradingNativeInterface().sendSettlementReqest("1013", "00000008");
		tradingNativeInterface.sendTradeRequest("1013", "123321", "00000008", initialRequest);
		//new TradingNativeInterface().sendOrderAction("1013", "123321", "00000008", cancelRequest);
		
		//testInterface.sendOrderAction("1013", "123321", "00000008", null);
		long startTime = System.currentTimeMillis();
		//while(true);
		
		//while(System.currentTimeMillis() - startTime <= 5000);
		//new DefaultNativeInterface().sendUnsubscribeQuoteRequest(quote1);
		System.out.println("exiting program");
	}
}
