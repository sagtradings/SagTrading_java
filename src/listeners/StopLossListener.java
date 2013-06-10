package listeners;

import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;

import bo.OrderActionRequest;
import bo.TradeRequest;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class StopLossListener implements UpdateListener {

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
		for(int i = 0, n = arg0.length; i < n; i++){
			String instrument = (String) arg0[i].get("instrumentID");
			OrderBucket bucket = OrderRepository.getInstance().getOrderBucket(instrument);
			bucket.setOrderState(OrderBucket.orderStates.STOP_LOSS_COMPLETED);
			
			TradeRequest stopLoss = bucket.getStopLossRequest();
			OrderActionRequest cancelRequest = new OrderActionRequest();
			cancelRequest.setActionFlag("0");
			cancelRequest.setBrokerID("1013");
			cancelRequest.setExchangeID("");
			cancelRequest.setFrontID(0);
			cancelRequest.setInvestorID("00000008");
			cancelRequest.setInstrumentID(instrument);
			cancelRequest.setLimitPrice(0);
			cancelRequest.setOrderActionRef(0);
			cancelRequest.setOrderRef(stopLoss.getOrderRef());
			cancelRequest.setOrderSysID("");
			cancelRequest.setRequestID(0);
			cancelRequest.setSessionID(0);
			cancelRequest.setUserID("1013");
			
			new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getStopLossRequest());
			new TradingNativeInterface().sendOrderAction("1013", "123321", "00000008", cancelRequest);
			new MarketDataNativeInterface().sendUnsubscribeQuoteRequest(new String[] {instrument});
		}

	}

}