package nativeinterfaces;

import listeners.DefaultCTPListener;

public class MarketDataNativeInterface {


    public native void subscribeListener(DefaultCTPListener obj);

    public native void unSubscribeListener(DefaultCTPListener obj);

    public native void sendLoginMessage(String brokerId, String password, String investorId);

    public native void sendQuoteRequest(String[] insturments);

    public native void sendUnsubscribeQuoteRequest(String[] insturments);

}
