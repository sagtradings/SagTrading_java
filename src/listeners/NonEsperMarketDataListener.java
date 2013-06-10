package listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.MarketDataResponse;

public final class NonEsperMarketDataListener extends DefaultCTPListener {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
	private long startTime = System.currentTimeMillis();
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
				long elapsedTime = System.currentTimeMillis() - startTime;
				System.out.println("got marketData");
				System.out.println("Open: " + compiledData.getOpen() + " Close: " + compiledData.getClose() + " Low: " + compiledData.getLow() + " High: " + compiledData.getHigh() + " UpVolume: " + compiledData.getUpVolume() + " DownVolume: " + compiledData.getDownVolume());
				System.out.println("elapsed time: " + elapsedTime);
				startTime = System.currentTimeMillis();
			}
		} catch (EntryNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
