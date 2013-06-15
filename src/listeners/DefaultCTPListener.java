package listeners;

import java.util.Calendar;

import bo.ErrorResult;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.SettlementResponse;
import bo.SubscribeMarketDataResponse;
import bo.TradeDataResponse;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

public class DefaultCTPListener implements ICTPListener {
    private Calendar initCal = Calendar.getInstance();

    public DefaultCTPListener() {


    }

    @Override
    public void onRspUserLogin(LoginResponse loginResponse) {
        System.out.println("logged in");

    }

    @Override
    public void onFrontConnected() {
        System.out.println("onFrontConnected invoked");

    }

    @Override
    public void onFrontDisconnected() {
        System.out.println("onFrontDisconnected invoked");

    }

    @Override
    public void onRspOrderInsert() {
        System.out.println("onRspOrderInsert invoked");

    }

    @Override
    public void onRtnOrder(OrderInsertResponse response) {
        System.out.println("onRtnOrder invoked");

    }

    @Override
    public void onRspError(ErrorResult errorRslt) {
        System.out.println("OnRspError invoked");

    }

    @Override
    public void onRtnDepthMarketData(MarketDataResponse response) {

        double deltaAskPrice1 = (response.getLastPrice() - response.getAskPrice1());
        double deltaBidPrice1 = response.getLastPrice() - response.getBidPrice1();
        if (Math.abs(deltaAskPrice1) < deltaBidPrice1) {
            response.setUpVolume(response.getVolume());
        } else {
            response.setDownVolume(response.getVolume());
        }

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        EPRuntime runtime = epService.getEPRuntime();
        int d = (int) ((System.currentTimeMillis() - initCal.getTimeInMillis()) / 1000.0);
        response.setUpdateMillisec(d);
        runtime.sendEvent(response);


    }

    @Override
    public void onRtnTradingData(TradeDataResponse response) {

        System.out.println("onRtnTradingDataInvoked");
    }

    @Override
    public void onSettlementResponse(SettlementResponse response) {
        System.out.println("Settlement");

    }

    @Override
    public void onOrderActionResponse(OrderActionRequest initiatingAction) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSubscribeMarketDataResponse(
            SubscribeMarketDataResponse subscribeResponse) {
        // TODO Auto-generated method stub

    }

}
