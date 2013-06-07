package listeners;

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
		if(bucket.getOrderState() == OrderBucket.orderStates.INITIAL_REQUEST){
			bucket.setOrderState(OrderBucket.orderStates.EXIT_REQUEST);
			new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getExitRequest());
		}
		else if(bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST){
			bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
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
