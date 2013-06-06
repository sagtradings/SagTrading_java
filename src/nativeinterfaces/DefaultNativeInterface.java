package nativeinterfaces;

import listeners.DefaultCTPListener;
import listeners.ICTPListener;

public class DefaultNativeInterface{

	
	public native void  subscribeListener(DefaultCTPListener obj);
    public native void unSubscribeListener(DefaultCTPListener obj);
    public native void sendLoginMessage(String brokerId, String password, String investorId);
	public native void sendOrderInsertMessage(String[] packet);
	public native void sendReturnOrderMessage(String[] packet);
	public native void sendQuoteRequest(String[] insturments);
	public native void sendUnsubscribeQuoteRequest(String[] insturments);

}
