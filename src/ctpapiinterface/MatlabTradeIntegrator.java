package ctpapiinterface;

import java.util.ArrayList;
import java.util.List;

import listeners.DefaultCTPListener;
import listeners.StopLossListener;
import matlab.MatlabOnLoginEvent;
import matlab.MatlabOnRtnErrorEvent;
import matlab.MatlabOnRtnOrderEvent;
import matlab.MatlabOnRtnTradeEvent;
import matlab.MatlabTradeListener;
import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import orderrefgenerator.MessageIDGenerator;
import orderrefgenerator.OrderRefGenerator;
import orderrepository.IncompleteBucketException;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;
import orderrepository.OrderTimeOutThread;
import bo.ErrorResult;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.TradeDataResponse;
import bo.TradeRequest;


import factories.OrderActionFactory;
import factories.TradeRequestFactory;

public class MatlabTradeIntegrator {
	static{
		System.loadLibrary("CTPDLL");
		System.loadLibrary("CTPTRADEDLL");
		
	}
	

	
	public MatlabTradeIntegrator(){
		new Thread(new OrderTimeOutThread()).start();
	}
	
	public void sendSettlement(){
		new TradingNativeInterface().sendSettlementReqest("1013", "00000008");
	}
	
	public String sendOrderSet(String insturment, String initSide, double initPrice, double initSize, long timeOut, double tgp, double tlp){
		String initOrderRef = OrderRefGenerator.getInstance().getNextRef();
		String exitOrderRef = OrderRefGenerator.getInstance().getNextRef();
		String stopLossOrderRef = OrderRefGenerator.getInstance().getNextRef();
		
		TradeRequestFactory factory = new TradeRequestFactory();
		TradeRequest initialRequest = factory.createRequest(insturment, initPrice, initSide);
		initialRequest.setOrderRef(initOrderRef);
		initialRequest.setOriginatingOrderRef(initOrderRef);
		initialRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		OrderBucket bucket = new OrderBucket();
		bucket.setTimeOut(timeOut);
		bucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
		bucket.setInitialRequest(initialRequest);
		
		TradeRequest exitRequest = factory.createRequest(insturment, initPrice + tgp, "0".equals(initSide) ? "1" : "0" );
		exitRequest.setOrderRef(exitOrderRef);
		exitRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		exitRequest.setOriginatingOrderRef(initOrderRef);
		bucket.setExitRequest(exitRequest);
		
		TradeRequest stopRequest = factory.createRequest(insturment, initPrice - tlp, "0".equals(initSide) ? "1" : "0");
		stopRequest.setOrderRef(stopLossOrderRef);
		stopRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		stopRequest.setOriginatingOrderRef(initOrderRef);
		stopRequest.setCutOffPrice(initPrice - tlp);
		bucket.setStopLossRequest(stopRequest);
		
		try {
			OrderRepository.getInstance().addOrderBucket(insturment, bucket);
			new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getInitialRequest());
			new MarketDataNativeInterface().sendQuoteRequest(new String[]{insturment});
		} catch (IncompleteBucketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return initOrderRef;
		
	}
	
	public String sendSingleOrder(String direction, String  instrument, double price){
		String nextOrder = OrderRefGenerator.getInstance().getNextRef();
		TradeRequestFactory factory = new TradeRequestFactory();
		TradeRequest request = factory.createRequest(instrument, price, direction);
		request.setOrderRef(nextOrder);
		request.setOriginatingOrderRef(nextOrder);
		request.setRequestID(MessageIDGenerator.getInstance().getNextID());
		OrderBucket bucket = new OrderBucket();
		bucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
		bucket.setInitialRequest(request);
		try {
			OrderRepository.getInstance().addOrderBucket(instrument, bucket);
			new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getInitialRequest());
			new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
		} catch (IncompleteBucketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextOrder;
	}
	

	
	private List<MatlabTradeListener> matLabListeners = new ArrayList<MatlabTradeListener>(10);
	private class ICMatLabTradeListener extends DefaultCTPListener{

		




		@Override
		public void onOrderActionResponse(OrderActionRequest initiatingAction) {
			// TODO Auto-generated method stub
			super.onOrderActionResponse(initiatingAction);
		}

		@Override
		public void onRspError(ErrorResult errorRslt) {
			MatlabOnRtnErrorEvent errorEvent = new MatlabOnRtnErrorEvent(this, errorRslt);
			notifyMatlabOnRtnErrorEvent(errorEvent);
		}

		@Override
		public void onRtnOrder(OrderInsertResponse response) {
			MatlabOnRtnOrderEvent orderEvent = new MatlabOnRtnOrderEvent(this, response);
			notifyMatlabOnRtnOrderEvent(orderEvent);
		}

		@Override
		public void onRtnTradingData(TradeDataResponse response) {
			
			String instrument = response.getInstrumentID();
			OrderRepository repository = OrderRepository.getInstance();
			String originatingOrderRef = response.getOrderRef();
			
			OrderBucket bucket = repository.searchBucket(originatingOrderRef);
			MatlabOnRtnTradeEvent tradeEvent = new MatlabOnRtnTradeEvent(this, response);
			notifyMatlabOnRtnTradeEvent(tradeEvent);
			if(bucket == null){
				return;
			}
			if(bucket.getOrderState() == OrderBucket.orderStates.INITIAL_REQUEST && bucket.getExitRequest() == null){
				bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.INITIAL_REQUEST){
				bucket.setOrderState(OrderBucket.orderStates.EXIT_REQUEST);
				new MarketDataNativeInterface().sendQuoteRequest(new String[]{instrument});
				new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getExitRequest());
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST){
				bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
				//new MarketDataNativeInterface().sendUnsubscribeQuoteRequest(new String[]{instrument});
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.STOP_LOSS_COMPLETED){
				OrderActionFactory factory = new OrderActionFactory();
				OrderActionRequest request = factory.createOrderActionRequest(response.getInstrumentID(), bucket.getExitRequest().getOrderRef());
				new TradingNativeInterface().sendOrderAction("1013", "123321", "00000008", request);
			}
			

		}

		@Override
		public void onRspUserLogin(LoginResponse loginResponse) {
			OrderRefGenerator.getInstance().setInitialValue(loginResponse.getMaxOrder());
			notifyMatlabOnLoginEvent(new MatlabOnLoginEvent(loginResponse));
		}
		
	}
	
	private static class ICMarketDataListener extends DefaultCTPListener{

		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {
			String insturment = response.getInstrumentId();
			List<OrderBucket> relatedOrders = OrderRepository.getInstance().searchBucketsOnState(insturment, OrderBucket.orderStates.EXIT_REQUEST);
			for(int i = 0, n = relatedOrders.size(); i < n; i++){
				OrderBucket bucket = relatedOrders.get(i);
				if(bucket.getStopLossRequest() != null &&  bucket.getStopLossRequest().getCutOffPrice() >= response.getLastPrice()){
					bucket.setOrderState(OrderBucket.orderStates.STOP_LOSS_COMPLETED);
					bucket.getStopLossRequest().setLimitPrice(response.getLastPrice());
					new TradingNativeInterface().sendTradeRequest("1013", "123321", "00000008", bucket.getStopLossRequest());
					
				}
			}
		}
		
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		new TradingNativeInterface().subscribeListener(new ICMatLabTradeListener());
		new MarketDataNativeInterface().subscribeListener(new ICMarketDataListener());
		new TradingNativeInterface().sendLoginMessage(brokerId, password, investorId);
		new MarketDataNativeInterface().sendLoginMessage(brokerId, password, investorId);

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
	
	public void addMatlabTradeListener(MatlabTradeListener tradeListener){
		matLabListeners.add(tradeListener);
	}
	
	public void removeMatlabTradeListener(MatlabTradeListener tradeListener){
		matLabListeners.remove(tradeListener);
	}
	
	public void notifyMatlabOnRtnErrorEvent(MatlabOnRtnErrorEvent errorEvent){
		for(int i = 0, n = matLabListeners.size(); i  < n; i++){
			matLabListeners.get(i).matlabOnRtnErrorEvent(errorEvent);
		}
	}
	
	public void notifyMatlabOnLoginEvent(MatlabOnLoginEvent loginEvent){
		for(int i = 0, n = matLabListeners.size(); i  < n; i++){
			matLabListeners.get(i).matlabOnLoginEvent(loginEvent);
		}
	}
	
	public void notifyMatlabOnRtnOrderEvent(MatlabOnRtnOrderEvent orderEvent){
		for(int i = 0, n = matLabListeners.size(); i < n; i++){
			matLabListeners.get(i).matlabOnRtnOrderEvent(orderEvent);
		}
	}
	
	public void notifyMatlabOnRtnTradeEvent(MatlabOnRtnTradeEvent tradeEvent){
		for(int i = 0, n = matLabListeners.size(); i < n; i++){
			matLabListeners.get(i).matlabOnRtnTradeEvent(tradeEvent);
		}
	}
	
}
