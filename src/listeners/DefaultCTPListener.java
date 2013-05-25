package listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import bo.MarketDataResponse;
import bo.TradeDataResponse;

public class DefaultCTPListener implements ICTPListener {
	private Calendar initCal = Calendar.getInstance();
	
	public DefaultCTPListener(){

		
	}
	
	@Override
	public void onRspUserLogin() {
		MarketDataResponse response = new MarketDataResponse();
		//System.out.println("onRspUserLogin invoked");
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPRuntime runtime = epService.getEPRuntime();
		 int d = (int)((System.currentTimeMillis() - initCal.getTimeInMillis()) / 1000.0);
		 response.setUpdateMillisec(d);
		runtime.sendEvent(response);
		
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
