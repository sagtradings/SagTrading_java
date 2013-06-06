package nativeinterfaces;

import listeners.DefaultCTPListener;
import bo.OrderActionRequest;
import bo.TradeRequest;

public class TradingNativeInterface {
	public native void sendLoginMessage(String brokerId, String password, String investorId);
	public native void sendTradeRequest(String brokerId, String password, String investorId, TradeRequest request);
	public native void sendOrderAction(String brokerId, String password, String investorId, OrderActionRequest request);
	public native void  subscribeListener(DefaultCTPListener obj);
    public native void unSubscribeListener(DefaultCTPListener obj);
    public native void sendSettlementReqest(String brokerID, String userID);
}
