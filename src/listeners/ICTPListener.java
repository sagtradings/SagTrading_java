package listeners;

import bo.ErrorResult;
import bo.MarketDataResponse;
import bo.OrderInsertResponse;
import bo.TradeDataResponse;

public interface ICTPListener {
	public void onRspUserLogin();
	public void onFrontConnected();
	public void onFrontDisconnected();
	public void onRspOrderInsert();
	public void onRtnOrder(OrderInsertResponse response);
	public void onRspError(ErrorResult errorRslt);
	public void onRtnDepthMarketData(MarketDataResponse response);
	public void onRtnTradingData(TradeDataResponse response);
	
	
}
