package listeners;

import bo.ErrorResult;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.SettlementResponse;
import bo.TradeDataResponse;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

public class TradeMarketDataListener extends DefaultCTPListener {

	@Override
	public void onRspUserLogin(LoginResponse loginResponse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFrontConnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFrontDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRspOrderInsert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRtnOrder(OrderInsertResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRspError(ErrorResult errorRslt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRtnDepthMarketData(MarketDataResponse response) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPRuntime runtime = epService.getEPRuntime();
		runtime.sendEvent(response);
		

	}

	@Override
	public void onRtnTradingData(TradeDataResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSettlementResponse(SettlementResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOrderActionResponse(OrderActionRequest initiatingAction) {
		// TODO Auto-generated method stub

	}

}
