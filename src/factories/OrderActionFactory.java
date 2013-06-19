package factories;

import bo.OrderActionRequest;

public class OrderActionFactory {
	public OrderActionRequest createOrderActionRequest(String insturment, String orderRef){
		OrderActionRequest cancelRequest = new OrderActionRequest();
		cancelRequest.setActionFlag("0");
		cancelRequest.setBrokerID("1013");
		cancelRequest.setExchangeID("");
		cancelRequest.setFrontID(0);
		cancelRequest.setInvestorID("00000008");
		cancelRequest.setInstrumentID(insturment);
		cancelRequest.setLimitPrice(0);
		cancelRequest.setOrderActionRef(0);
		cancelRequest.setOrderRef(orderRef);
		cancelRequest.setOrderSysID("");
		cancelRequest.setRequestID(0);
		cancelRequest.setSessionID(0);
		cancelRequest.setUserID("1013");
		return cancelRequest;
	}
}
