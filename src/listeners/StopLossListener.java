package listeners;

import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class StopLossListener implements UpdateListener {

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
		for(int i = 0, n = arg0.length; i < n; i++){
			String instrument = (String) arg0[i].get("a.instrumentID");
			OrderBucket bucket = OrderRepository.getInstance().getOrderBucket(instrument);
			bucket.setOrderState(OrderBucket.orderStates.STOP_LOSS_COMPLETED);		
			new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getStopLossRequest());
			new MarketDataNativeInterface().sendUnsubscribeQuoteRequest(new String[] {instrument});
		}

	}

}