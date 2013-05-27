package listeners;

import java.util.Calendar;

import bo.MarketDataResponse;
import bo.TradeDataResponse;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

public class DefaultCTPListener implements ICTPListener {
	private Calendar initCal = Calendar.getInstance();
	
	public DefaultCTPListener(){

		
	}
	
	@Override
	public void onRspUserLogin() {

		
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
		
		double deltaAskPrice1 = (response.getLastPrice() - response.getAskPrice1());
		double deltaBidPrice1 = response.getLastPrice() - response.getBidPrice1();
		if(Math.abs(deltaAskPrice1) < deltaBidPrice1){
			response.setUpVolume(response.getVolume());
		}
		else{
			response.setDownVolume(response.getVolume());
		}
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPRuntime runtime = epService.getEPRuntime();
		 int d = (int)((System.currentTimeMillis() - initCal.getTimeInMillis()) / 1000.0);
		 response.setUpdateMillisec(d);
		runtime.sendEvent(response);


		
	}
	
	public void onRtnTradingData(TradeDataResponse response){
		
		System.out.println("onRtnTradingDataInvoked");
	}

}
