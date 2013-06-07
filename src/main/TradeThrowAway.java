package main;

import threads.TradeIntegratorThread;
import bo.OrderActionRequest;
import bo.TradeRequest;
import listeners.DefaultCTPListener;
import listeners.TradeListener;
import nativeinterfaces.TradingNativeInterface;

public class TradeThrowAway {

	static{
		System.loadLibrary("CTPTRADEDLL");
		System.out.println("TRADEDLL LOADED");
	}
	
	public static void main(String[] args){
		//TradingNativeInterface testInterface = new TradingNativeInterface();
		
		

		
		//const char  *userID = "00000008";
		//const char  *password = "123321";
		//const char  *brokerID = "1013";
		//new Thread(new TradeIntegratorThread("1013", "123321", "00000008")).start();
		new TradingNativeInterface().subscribeListener(new TradeListener());
		new TradingNativeInterface().sendLoginMessage("1013", "123321", "00000008");
		TradeRequest request = new TradeRequest();
		//request.setDirection("0");
		request.setDirection("1");
		request.setOrderPriceType("2");
		request.setCombOffsetFlag("0");
		request.setCombHedgeFlag("1");
		request.setLimitPrice(2600);
		request.setGtdDate("");
		request.setVolumeCondition("1");
		request.setMinVolume(1);
		request.setContingentCondition("1");
		request.setStopPrice(0);
		request.setForceCloseReason("0");
		request.setAutoSuspend(0);
		request.setVolumeTotalOriginal(10);
		request.setTimeCondition("3");
		request.setInstrumentID("IF1307");
		request.setOrderRef("00000000002");
		
		OrderActionRequest cancelRequest = new OrderActionRequest();
		cancelRequest.setActionFlag("0");
		cancelRequest.setBrokerID("1013");
		cancelRequest.setExchangeID("");
		cancelRequest.setFrontID(0);
		cancelRequest.setInvestorID("00000008");
		cancelRequest.setInstrumentID("IF1307");
		cancelRequest.setLimitPrice(0);
		cancelRequest.setOrderActionRef(0);
		cancelRequest.setOrderRef("00000000002");
		cancelRequest.setOrderSysID("");
		cancelRequest.setRequestID(0);
		cancelRequest.setSessionID(0);
		cancelRequest.setUserID("1013");
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
		new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", request);
		//new TradingNativeInterface().sendOrderAction("1013", "123321", "00000008", cancelRequest);
		
		//testInterface.sendOrderAction("1013", "123321", "00000008", null);
	}
}
