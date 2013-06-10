package matlab;

import java.util.ArrayList;
import java.util.List;

import listeners.DefaultCTPListener;
import listeners.StopLossListener;
import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import orderrepository.IncompleteBucketException;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;
import bo.ErrorResult;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderInsertResponse;
import bo.TradeDataResponse;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class MatLabTradeIntegrator {
	static{
		System.loadLibrary("CTPDLL");
		System.loadLibrary("CTPTRADEDLL");
		
	}
	
	public MatLabTradeIntegrator(){
		String timerContext = "create context SegmentedByInstrument partition by  instrumentID from bo.TradeRequest, instrumentId from bo.MarketDataResponse";
		String listenerStmt = "context SegmentedByInstrument select a.instrumentID, b.lastPrice from pattern[every a=bo.TradeRequest -> b=bo.MarketDataResponse(b.lastPrice <= a.cutOffPrice)]";
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		epService.getEPAdministrator().createEPL(timerContext);
		EPStatement statement = epService.getEPAdministrator().createEPL(listenerStmt);
		statement.addListener(new StopLossListener());
	}
	
	private List<MatLabTradeListener> matLabListeners = new ArrayList<MatLabTradeListener>(10);
	private class ICMatLabTradeListener extends DefaultCTPListener{

		




		@Override
		public void onRspError(ErrorResult errorRslt) {
			MatLabOnRtnErrorEvent errorEvent = new MatLabOnRtnErrorEvent(null, errorRslt);
			notifyMatLabOnRtnErrorEvent(errorEvent);
		}

		@Override
		public void onRtnOrder(OrderInsertResponse response) {
			MatLabOnRtnOrderEvent orderEvent = new MatLabOnRtnOrderEvent(null, response);
			notifyMatLabOnRtnOrderEvent(orderEvent);
		}

		@Override
		public void onRtnTradingData(TradeDataResponse response) {
			
			String instrument = response.getInstrumentID();
			OrderRepository repository = OrderRepository.getInstance();
			OrderBucket bucket = repository.getOrderBucket(instrument);
			EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
			EPRuntime runtime = epService.getEPRuntime();
			if(bucket.getOrderState() == OrderBucket.orderStates.INITIAL_REQUEST){
				bucket.setOrderState(OrderBucket.orderStates.EXIT_REQUEST);
				new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
				new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getExitRequest());
				runtime.sendEvent(bucket.getExitRequest());
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST){
				bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
				new MarketDataNativeInterface().sendUnsubscribeQuoteRequest(new String[]{instrument});
			}
			
			MatLabOnRtnTradeEvent tradeEvent = new MatLabOnRtnTradeEvent(null, response);
			notifyMatLabOnRtnTradeEvent(tradeEvent);
		}

		@Override
		public void onRspUserLogin(LoginResponse loginResponse) {
			loginResponse.getMaxOrder();
		}
		
	}
	
	private static class ICMarketDataListener extends DefaultCTPListener{

		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {
			// TODO Auto-generated method stub
			super.onRtnDepthMarketData(response);
		}
		
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		new TradingNativeInterface().sendLoginMessage(brokerId, password, investorId);
		new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId);
		new TradingNativeInterface().subscribeListener(new ICMatLabTradeListener());
		new MarketDataNativeInterface().subscribeListener(new ICMarketDataListener());
	}
	
	public void sendOrder(String brokerId, String password, String investorId, String instrumentId, OrderBucket orderBucket){
		try {
			orderBucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
			OrderRepository.getInstance().addOrderBucket(instrumentId, orderBucket);
			new TradingNativeInterface().sendTradeRequest(brokerId, password, investorId, orderBucket.getInitialRequest());
			
		} catch (IncompleteBucketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addMatLabTradeListener(MatLabTradeListener tradeListener){
		matLabListeners.add(tradeListener);
	}
	
	public void removeMatLabTradeListener(MatLabTradeListener tradeListener){
		matLabListeners.remove(tradeListener);
	}
	
	public void notifyMatLabOnRtnErrorEvent(MatLabOnRtnErrorEvent errorEvent){
		for(int i = 0, n = matLabListeners.size(); i  < n; i++){
			matLabListeners.get(i).matLabOnRtnErrorEvent(errorEvent);
		}
	}
	
	public void notifyMatLabOnRtnOrderEvent(MatLabOnRtnOrderEvent orderEvent){
		for(int i = 0, n = matLabListeners.size(); i < n; i++){
			matLabListeners.get(i).matLabOnRtnOrderEvent(orderEvent);
		}
	}
	
	public void notifyMatLabOnRtnTradeEvent(MatLabOnRtnTradeEvent tradeEvent){
		for(int i = 0, n = matLabListeners.size(); i < n; i++){
			matLabListeners.get(i).matLabOnRtnTradeEvent(tradeEvent);
		}
	}
	
}
