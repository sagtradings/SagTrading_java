package listeners;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;
import bo.ErrorResult;
import bo.LoginResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.TradeDataResponse;

public class TradeListener extends DefaultCTPListener {


	@Override
	public void onRtnTradingData(TradeDataResponse response) {
		String instrument = response.getInstrumentID();
		OrderRepository repository = OrderRepository.getInstance();
		OrderBucket bucket = repository.getOrderBucket(instrument);
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPRuntime runtime = epService.getEPRuntime();
		if(bucket.getOrderState() == OrderBucket.orderStates.INITIAL_REQUEST){
			bucket.setOrderState(OrderBucket.orderStates.EXIT_REQUEST);
			new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
			new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getExitRequest());
			runtime.sendEvent(bucket.getExitRequest());
		}
		else if(bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST){
			bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
			new MarketDataNativeInterface().sendUnsubscribeQuoteRequest(new String[]{instrument});
		}
		
	}

	@Override
	public void onOrderActionResponse(OrderActionRequest initiatingAction) {
		System.out.println("Order action");
	}

	@Override
	public void onRtnOrder(OrderInsertResponse response) {
		System.out.println("rtnOrderInvoked");
	}

	@Override
	public void onRspUserLogin(LoginResponse loginResponse) {
		System.out.println("logged in");
		new TradingNativeInterface().sendSettlementReqest("1013", "00000008");
	}

}
