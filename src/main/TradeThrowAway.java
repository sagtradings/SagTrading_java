package main;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import bo.OrderActionRequest;
import bo.TradeRequest;
import listeners.DefaultCTPListener;
import listeners.StopLossListener;
import listeners.TradeListener;
import listeners.TradeMarketDataListener;
import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;

public class TradeThrowAway {

	static{
		System.loadLibrary("CTPTRADEDLL");
		System.out.println("TRADEDLL LOADED");
		System.loadLibrary("CTPDLL");
	}
	
	public static void main(String[] args){
		//TradingNativeInterface testInterface = new TradingNativeInterface();
		
		

		
		//const char  *userID = "00000008";
		//const char  *password = "123321";
		//const char  *brokerID = "1013";
		//new Thread(new TradeIntegratorThread("1013", "123321", "00000008")).start();
		new TradingNativeInterface().subscribeListener(new TradeListener());
		new MarketDataNativeInterface().subscribeListener(new TradeMarketDataListener());
		new MarketDataNativeInterface().sendLoginMessage("1013", "123321", "00000008");
		new TradingNativeInterface().sendLoginMessage("1013", "123321", "00000008");
		
		String timerContext = "create context SegmentedByInstrument partition by  instrumentID from bo.TradeRequest, instrumentId from bo.MarketDataResponse";
		String listenerStmt = "context SegmentedByInstrument select a.instrumentID, b.lastPrice from pattern[every a=bo.TradeRequest -> b=bo.MarketDataResponse(b.lastPrice <= a.limitPrice)]";
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new StopLossListener());
		
		TradeRequest initialRequest = new TradeRequest();
		//request.setDirection("0");
		initialRequest.setDirection("1");
		initialRequest.setOrderPriceType("2");
		initialRequest.setCombOffsetFlag("0");
		initialRequest.setCombHedgeFlag("1");
		initialRequest.setLimitPrice(2600);
		initialRequest.setGtdDate("");
		initialRequest.setVolumeCondition("1");
		initialRequest.setMinVolume(1);
		initialRequest.setContingentCondition("1");
		initialRequest.setStopPrice(0);
		initialRequest.setForceCloseReason("0");
		initialRequest.setAutoSuspend(0);
		initialRequest.setVolumeTotalOriginal(10);
		initialRequest.setTimeCondition("3");
		initialRequest.setInstrumentID("IF1307");
		initialRequest.setOrderRef("00000000002");
		

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
		//new TradingNativeInterface().sendSettlementReqest("1013", "00000008");
		new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", initialRequest);
		//new TradingNativeInterface().sendOrderAction("1013", "123321", "00000008", cancelRequest);
		
		//testInterface.sendOrderAction("1013", "123321", "00000008", null);
	}
}
