package bardatamanager;

import bo.BarData;
import bo.MarketDataResponse;

public class BarDataManager {
	private BarDataRegistry registry = new BarDataRegistry();
	
	private static BarDataManager instance = new BarDataManager();
	
	public static BarDataManager getInstance(){
		return instance;
	}
	
	private BarDataManager(){
		
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
			entry.reset();
		}
		double tickPrice = mdResponse.getLastPrice();
		BarData barData = entry.getBarDataEntry();
		if(tickPrice < barData.getHigh()){
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
