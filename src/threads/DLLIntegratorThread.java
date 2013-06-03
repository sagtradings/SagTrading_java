package threads;

import listeners.DefaultCTPListener;
import nativeinterfaces.DefaultNativeInterface;

public class DLLIntegratorThread implements Runnable {

	@Override
	public void run() {
		DefaultCTPListener ctpListener = new DefaultCTPListener();
		DefaultNativeInterface nativeInterface = new DefaultNativeInterface();
		nativeInterface.subscribeListener(ctpListener);
		nativeInterface.sendQuoteRequest(new String[]{});

	}

}
