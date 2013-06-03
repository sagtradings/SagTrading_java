package listeners;

import bo.ErrorResult;
import bo.OrderInsertResponse;

public class TradeListener extends DefaultCTPListener {


	@Override
	public void onRtnOrder(OrderInsertResponse response) {
		System.out.println("rtnOrderInvoked");
	}

	@Override
	public void onRspUserLogin() {
	//	System.out.println("logged in");
	}

}
