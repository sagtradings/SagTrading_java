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
			day.setTime(mdResponse.getMillisecConversionTime() - mdResponse.getMillisecConversionTime() % entry.getEvictionTime());
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
	
	public static void main(String[] args){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Date date1 = cal1.getTime();
		Date date2 = cal2.getTime();
		
		cal1.set(Calendar.YEAR, 2013);
		cal1.set(Calendar.MONTH, Calendar.JANUARY);
		cal1.set(Calendar.DATE, 1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		
		cal2.set(Calendar.YEAR, 2013);
		cal2.set(Calendar.MONTH, Calendar.JANUARY);
		cal2.set(Calendar.DATE, 1);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.SECOND, 11);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		
		BarDataManager manager = new BarDataManager();
		MarketDataResponse mdResponse1 = new MarketDataResponse();
		mdResponse1.setInstrumentId("IF1309");
		mdResponse1.setMillisecConversionTime(cal1.getTimeInMillis());
		
		MarketDataResponse mdResponse2 = new MarketDataResponse();
		mdResponse2.setMillisecConversionTime(cal2.getTimeInMillis());
		mdResponse2.setInstrumentId("IF1309");
		
		try {
			manager.initializeEntry("IF1309", 10000);
			manager.sendMarketData(mdResponse1);
			BarData barData = manager.sendMarketData(mdResponse2);
			System.out.println(barData);
		} catch (EntryNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
