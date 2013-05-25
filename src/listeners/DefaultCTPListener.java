package listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bo.MarketDataResponse;
import bo.TradeDataResponse;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

public class DefaultCTPListener implements ICTPListener {
    private Calendar cal = Calendar.getInstance();
    private Calendar initCal = Calendar.getInstance();

    public DefaultCTPListener() {
        initCal.set(Calendar.HOUR_OF_DAY, 0);
        initCal.set(Calendar.MINUTE, 0);
        initCal.set(Calendar.SECOND, 0);
        initCal.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String dataDirectory = format.format(cal.getTime());
        File f = new File(dataDirectory);
        if (!f.exists()) {
            f.mkdir();
            File rawData = new File(dataDirectory + "\\rawdata.csv");
            File barData = new File(dataDirectory + "\\bardata.csv");
            try {
                rawData.createNewFile();
                barData.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onRspUserLogin() {
        System.out.println("onRspUserLogin invoked");

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
    public void onRtnOrder() {
        System.out.println("onRtnOrder invoked");

    }

    @Override
    public void onRspError() {
        System.out.println("OnRspError invoked");

    }

    @Override
    public void onRtnDepthMarketData(MarketDataResponse response) {
        //System.out.println("time received: " + response.getTimeOfEvent() + " System.currentTimeMillis(): " + System.currentTimeMillis());
        //System.out.println("time lag for event: " + (System.currentTimeMillis() - response.getTimeOfEvent()));

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
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String dataDirectory = format.format(cal.getTime());
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(dataDirectory + "\\rawdata.csv", true));
            StringBuffer data = new StringBuffer();


            data.append(response.getActionDay()).append(",");
            data.append("" + response.getAskPrice1()).append(",");
            data.append("" + response.getAskPrice2()).append(",");
            data.append("" + response.getAskPrice3()).append(",");
            data.append("" + response.getAskPrice4()).append(",");
            data.append("" + response.getAskPrice5()).append(",");

            data.append("" + response.getAskVolume1()).append(",");
            data.append("" + response.getAskVolume2()).append(",");
            data.append("" + response.getAskVolume3()).append(",");
            data.append("" + response.getAskVolume4()).append(",");
            data.append("" + response.getAskVolume5()).append(",");

            data.append("" + response.getAveragePrice()).append(",");

            data.append("" + response.getBidPrice1()).append(",");
            data.append("" + response.getBidPrice2()).append(",");
            data.append("" + response.getBidPrice3()).append(",");
            data.append("" + response.getBidPrice4()).append(",");
            data.append("" + response.getBidPrice5()).append(",");

            data.append("" + response.getBidVolume1()).append(",");
            data.append("" + response.getBidVolume2()).append(",");
            data.append("" + response.getBidVolume3()).append(",");
            data.append("" + response.getBidVolume4()).append(",");
            data.append("" + response.getBidVolume5()).append(",");

            data.append("" + response.getClosePrice()).append(",");
            data.append("" + response.getCurrDelta()).append(",");

            data.append("" + response.getDownVolume()).append(",");
            data.append("" + response.getExchangeID()).append(",");
            data.append("" + response.getExchangeInstId()).append(",");
            data.append("" + response.getHighestPrice()).append(",");
            data.append("" + response.getInstrumentId()).append(",");

            data.append("" + response.getLastPrice()).append(",");
            data.append("" + response.getLowerLimitPrice()).append(",");
            data.append("" + response.getLowestPrice()).append(",");
            data.append("" + response.getOpenInterest()).append(",");
            data.append("" + response.getOpenPrice()).append(",");

            data.append("" + response.getPreClosePrice()).append(",");
            data.append("" + response.getPreDelta()).append(",");
            data.append("" + response.getPreOpenInterest()).append(",");
            data.append("" + response.getPreSettlementPrice()).append(",");
            data.append("" + response.getSettlementPrice()).append(",");

            data.append("" + response.getTimeOfEvent()).append(",");
            data.append("" + response.getTradingDay()).append(",");
            data.append("" + response.getTurnOver()).append(",");
            data.append("" + response.getUpdateMillisec()).append(",");
            data.append("" + response.getUpperLimitPrice()).append(",");

            data.append("" + response.getUpVolume()).append(",");
            data.append("" + response.getVolume()).append("\n");


            //data.append(response.getInstrumentId()).append(",").append(""+d).append(",").append(response.getLastPrice()).append("\n");
            writer.write(data.toString());
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void onRtnTradingData(TradeDataResponse response) {

        System.out.println("onRtnTradingDataInvoked");
    }

}
