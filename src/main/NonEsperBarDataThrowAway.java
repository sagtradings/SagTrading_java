package main;

import bardatamanager.BarDataManager;
import bardatamanager.EntryNotInitializedException;
import bo.BarData;
import bo.MarketDataResponse;

public class NonEsperBarDataThrowAway {
	public static void main(String[] args){
		BarDataManager instance = BarDataManager.getInstance();
		instance.initializeEntry("1", 1000);
		MarketDataResponse response1 = new MarketDataResponse();
		MarketDataResponse response2 = new MarketDataResponse();
		MarketDataResponse response3 = new MarketDataResponse();
		MarketDataResponse response4 = new MarketDataResponse();
		response1.setInstrumentId("1");
		response1.setLastPrice(3);
		response2.setInstrumentId("1");
		response2.setLastPrice(5);
		response3.setInstrumentId("1");
	    response3.setLastPrice(1);
	    response4.setInstrumentId("1");
	    response4.setLastPrice(5000);
		
		
		response1.setMillisecConversionTime(0);
		response2.setMillisecConversionTime(500);
		response3.setMillisecConversionTime(999);
		response4.setMillisecConversionTime(1001);
		try {
			BarData data = instance.sendMarketData(response1);
			data = instance.sendMarketData(response2);
			data = instance.sendMarketData(response3);
			data = instance.sendMarketData(response4);
			System.out.println("done");
			
		} catch (EntryNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
