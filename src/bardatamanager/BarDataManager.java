package bardatamanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bo.BarData;
import bo.MarketDataResponse;

public class BarDataManager {
	private BarDataRegistry registry = new BarDataRegistry();
	
	private static BarDataManager instance = new BarDataManager();
	
	public static BarDataManager getInstance(){
		return instance;
	}
	
	public BarDataManager(){
		
	}
	
	public void initializeEntry(String instrumentId, long evictionTime){
		BarDataEntry newEntry = new BarDataEntry();
		newEntry.setEvictionTime(evictionTime);
		newEntry.reset();
		registry.addEntry(instrumentId, newEntry);
		
	}
	
	public BarData sendMarketData(MarketDataResponse mdResponse) throws EntryNotInitializedException{
		BarDataEntry entry = registry.getEntry(mdResponse.getInstrumentId());
		BarData evictionAnswer = null;
		if(entry == null){
			throw new EntryNotInitializedException("No entry created in bardatamanager.BarDataRegistry for instrument " + mdResponse.getInstrumentId());
		}
		if(entry.isEvictable(mdResponse)){
			evictionAnswer = entry.evict();
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.000");
			Date evictionDate = Calendar.getInstance().getTime();
			evictionDate.setTime(mdResponse.getMillisecConversionTime());
			evictionAnswer.setTimestamp(formatter.format(evictionDate));
			Date day = new Date();
			day.setTime(mdResponse.getMillisecConversionTime());
			evictionAnswer.setDay(day);
			entry.reset();
		}
		double tickPrice = mdResponse.getLastPrice();
		BarData barData = entry.getBarDataEntry();
		if(tickPrice > barData.getHigh()){
			barData.setHigh(tickPrice);
		}
		if(tickPrice < barData.getLow()){
			barData.setLow(tickPrice);
		}
		barData.setUpVolume(mdResponse.getUpVolume() + barData.getUpVolume());
		barData.setDownVolume(mdResponse.getDownVolume() + barData.getDownVolume());
		entry.addMarketDataEntry(mdResponse);
		return evictionAnswer;
		
	}
}
