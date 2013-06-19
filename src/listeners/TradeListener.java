package listeners;

import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import orderrefgenerator.OrderRefGenerator;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;
import bo.ErrorResult;
import bo.LoginResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.SettlementResponse;
import bo.TradeDataResponse;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

public class TradeListener extends DefaultCTPListener {


	@Override
	public void onRspOrderInsert() {
		System.out.println("onRspOrderInsert");
	}

	@Override
	public void onRspError(ErrorResult errorRslt) {
		System.out.println("error: " + errorRslt.getErrorId());
	}

	@Override
	public void onRtnTradingData(TradeDataResponse response) {
		
		System.out.println("Trade ref: " + response.getOrderRef());
		
		String instrument = response.getInstrumentID();
		String originatingOrderRef = response.getOrderRef();
		OrderRepository repository = OrderRepository.getInstance();
		OrderBucket bucket = repository.getOrderBucket(instrument, originatingOrderRef);
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPRuntime runtime = epService.getEPRuntime();
		if(bucket.getOrderState() == OrderBucket.orderStates.INITIAL_REQUEST){
			bucket.setOrderState(OrderBucket.orderStates.EXIT_REQUEST);
			new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
			System.out.println("sending exit order: " + bucket.getExitRequest().getOrderRef());
			new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getExitRequest());
			runtime.sendEvent(bucket.getStopLossRequest());
		}
		else if(bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST){
			bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
			new MarketDataNativeInterface().sendUnsubscribeQuoteRequest(new String[]{instrument});
		}
		else if(bucket.getOrderState() == OrderBucket.orderStates.STOP_LOSS_COMPLETED){
			OrderActionRequest cancelRequest = new OrderActionRequest();
			cancelRequest.setActionFlag("0");
			cancelRequest.setBrokerID("1013");
			cancelRequest.setExchangeID("");
			cancelRequest.setFrontID(0);
			cancelRequest.setInvestorID("00000008");
			cancelRequest.setInstrumentID(instrument);
			cancelRequest.setLimitPrice(0);
			cancelRequest.setOrderActionRef(0);
			cancelRequest.setOrderRef(bucket.getExitRequest().getOrderRef());
			cancelRequest.setOrderSysID("");
			cancelRequest.setRequestID(0);
			cancelRequest.setSessionID(0);
			cancelRequest.setUserID("1013");
			new TradingNativeInterface().sendOrderAction("1013", "123321", "00000008", cancelRequest);
		}
		
	}

	@Override
	public void onOrderActionResponse(OrderActionRequest initiatingAction) {
		System.out.println("Order action");
	}

	@Override
	public void onRtnOrder(OrderInsertResponse response) {
		System.out.println("orderRef: " + response.getOrderRef());
		System.out.println("rtnOrderInvoked");
		
	}

	@Override
	public void onSettlementResponse(SettlementResponse response) {
		// TODO Auto-generated method stub
		System.out.println("the setllement");
	}

	@Override
	public void onRspUserLogin(LoginResponse loginResponse) {
		OrderRefGenerator.getInstance().setInitialValue(loginResponse.getMaxOrder());
	}

}
