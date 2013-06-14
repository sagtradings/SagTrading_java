package listeners;

import javax.swing.JOptionPane;

import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
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
		OrderRepository repository = OrderRepository.getInstance();
		OrderBucket bucket = repository.getOrderBucket(instrument);
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
		//JOptionPane.showMessageDialog(null, "logged in");
		System.out.println("logged in");
		//new TradingNativeInterface().sendSettlementReqest("1013", "00000008");
	}

}
