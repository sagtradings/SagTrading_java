package listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.ErrorResult;
import bo.MarketDataResponse;
import bo.SubscribeMarketDataResponse;

public final class NonEsperMarketDataListener extends DefaultCTPListener {
	@Override
	public void onSubscribeMarketDataResponse(
			SubscribeMarketDataResponse subscribeResponse) {
		System.out.println("Market data subscribed");
	}




	private Map<String, Long> startTimes = new HashMap<String, Long>(10);
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");

	
	
	
	@Override
	public void onRspError(ErrorResult errorRslt) {
		System.out.println("error");
	}




	public void onRtnDepthMarketData(MarketDataResponse response) {
		double deltaAskPrice1 = (response.getLastPrice() - response.getAskPrice1());
		double deltaBidPrice1 = response.getLastPrice() - response.getBidPrice1();
		if(Math.abs(deltaAskPrice1) < deltaBidPrice1){
			response.setUpVolume(response.getVolume());
		}
		else{
			response.setDownVolume(response.getVolume());
		}
		try {
			Date updateTime = formatter.parse(response.getTradingDay() + response.getUpdateTime());
			response.setMillisecConversionTime(updateTime.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//BarDataManager.
		bardatamanager.BarDataManager barManager = bardatamanager.BarDataManager.getInstance();
		try {
			BarData compiledData = barManager.sendMarketData(response);
			if(compiledData != null){
				Long startTime = startTimes.get(response.getInstrumentId());
				if(startTime == null){
					startTime = System.currentTimeMillis();
					startTimes.put(response.getInstrumentId(), startTime);
				}
				long elapsedTime = System.currentTimeMillis() - startTime;
				System.out.println("got marketData");
				System.out.println("Instrument: " + response.getInstrumentId() + " Open: " + compiledData.getOpen() + " Close: " + compiledData.getClose() + " Low: " + compiledData.getLow() + " High: " + compiledData.getHigh() + " UpVolume: " + compiledData.getUpVolume() + " DownVolume: " + compiledData.getDownVolume());
				System.out.println("elapsed time: " + elapsedTime);
				startTimes.put(response.getInstrumentId(), System.currentTimeMillis());
			}
		} catch (EntryNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
