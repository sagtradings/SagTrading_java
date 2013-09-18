package bardatamanager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import bo.BarData;
import bo.MarketDataResponse;

public class BarDataEntry {
	private BarData barDataEntry;
	private long evictionTime;
	private List<MarketDataResponse> marketData = new Vector<MarketDataResponse>(10);
	private boolean clean = true;
	public BarData getBarDataEntry() {
		return barDataEntry;
	}
	public void setBarDataEntry(BarData barDataEntry) {
		this.barDataEntry = barDataEntry;
	}
	public long getEvictionTime() {
		return evictionTime;
	}
	public void setEvictionTime(long evictionTime) {
		this.evictionTime = evictionTime;
	}
	
	public int getMarketDataCoune(){
		return marketData.size();
	}
	
	public boolean isEvictable(MarketDataResponse tick){
		if(marketData.size() == 0){
			return false;
		}
		return tick.getMillisecConversionTime() - marketData.get(0).getMillisecConversionTime() >= evictionTime; 
	}
	
	public MarketDataResponse getMarketDataEntry(int index){
		return marketData.get(index);
	}
	
	public void addMarketDataEntry(MarketDataResponse marketDataResponse){
		if(clean){
			clean = false;
			MarketDataResponse initialEntry = new MarketDataResponse();
			initialEntry.setInstrumentId(marketDataResponse.getInstrumentId());
			initialEntry.setLastPrice(marketDataResponse.getLastPrice());
			if(marketDataResponse.getMillisecConversionTime() % evictionTime != 0){
				initialEntry.setMillisecConversionTime(marketDataResponse.getMillisecConversionTime() + (evictionTime - marketDataResponse.getMillisecConversionTime() % evictionTime));
			}
			marketData.add(initialEntry);
		}
		else if(marketData.size() == 0){
			marketDataResponse.setMillisecConversionTime((long) (marketDataResponse.getMillisecConversionTime() - marketDataResponse.getMillisecConversionTime() % evictionTime));
			marketData.add(marketDataResponse);
		}
		else if(marketData.get(0).getMillisecConversionTime() <= marketDataResponse.getMillisecConversionTime()){
			marketData.add(marketDataResponse);
		}
	}
	
	public MarketDataResponse removeMarketData(int index){
		MarketDataResponse answer = marketData.get(index);
		marketData.remove(index);
		return answer;
	}
	
	public void reset(){
		marketData = new Vector<MarketDataResponse>(10);
		barDataEntry = new BarData();
		barDataEntry.setHigh(Double.MIN_VALUE);
		barDataEntry.setLow(Double.MAX_VALUE);
	}
	
	public BarData evict(){
		BarData answer = new BarData();
		answer.setOpen(marketData.get(0).getLastPrice());
		answer.setClose(marketData.get(marketData.size() - 1).getLastPrice());
		answer.setUpVolume(barDataEntry.getUpVolume());
		answer.setDownVolume(barDataEntry.getDownVolume());
		answer.setHigh(barDataEntry.getHigh());
		answer.setLow(barDataEntry.getLow());
		answer.setInstrumentId(marketData.get(0).getInstrumentId());
        answer.setDay(barDataEntry.getDay());
		return answer;
	}
	

}
