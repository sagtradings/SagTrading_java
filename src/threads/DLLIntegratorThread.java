package threads;

import listeners.DefaultCTPListener;
import nativeinterfaces.MarketDataNativeInterface;

public class DLLIntegratorThread implements Runnable {

	@Override
	public void run() {
		DefaultCTPListener ctpListener = new DefaultCTPListener();
		MarketDataNativeInterface nativeInterface = new MarketDataNativeInterface();
		nativeInterface.subscribeListener(ctpListener);
		nativeInterface.sendQuoteRequest(new String[]{});

	}

}
