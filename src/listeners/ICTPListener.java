package listeners;

import bo.ErrorResult;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.SettlementResponse;
import bo.SubscribeMarketDataResponse;
import bo.TradeDataResponse;

public interface ICTPListener {
	public void onRspUserLogin(LoginResponse loginResponse);
	public void onFrontConnected();
	public void onFrontDisconnected();
	public void onRspOrderInsert();
	public void onRtnOrder(OrderInsertResponse response);
	public void onRspError(ErrorResult errorRslt);
	public void onRtnDepthMarketData(MarketDataResponse response);
	public void onRtnTradingData(TradeDataResponse response);
	public void onSettlementResponse(SettlementResponse response);
	public void onOrderActionResponse(OrderActionRequest initiatingAction);
	public void onSubscribeMarketDataResponse(SubscribeMarketDataResponse subscribeResponse);
	
	
}
