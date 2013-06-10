package matlab;

import orderrepository.OrderBucket;
import listeners.DefaultCTPListener;

public class MatLabTradeIntegrator {
	static{
		System.loadLibrary("CTPDLL");
		System.loadLibrary("CTPTRADEDLL");
		
	}
	
	private static class ICMatLabTradeListener extends DefaultCTPListener{
		
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		
	}
	
	public void sendOrder(String instrumentId, OrderBucket orderBucket){
		
	}
	
	public void addMatLabTradeListener(MatLabTradeListener tradeListener){
		
	}
	
	public void removeMatLabTradeListener(MatLabTradeListener tradeListener){
		
	}
	
	
}
