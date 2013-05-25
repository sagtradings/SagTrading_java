package listeners;

import bo.MarketDataResponse;
import bo.TradeDataResponse;

public interface ICTPListener {
    public void onRspUserLogin();

    public void onFrontConnected();

    public void onFrontDisconnected();

    public void onRspOrderInsert();

    public void onRtnOrder();

    public void onRspError();

    public void onRtnDepthMarketData(MarketDataResponse response);

    public void onRtnTradingData(TradeDataResponse response);


}
