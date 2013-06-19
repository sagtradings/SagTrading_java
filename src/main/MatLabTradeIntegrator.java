package main;

import java.util.ArrayList;
import java.util.List;

import listeners.DefaultCTPListener;
import listeners.StopLossListener;
import matlab.MatLabOnLoginEvent;
import matlab.MatLabOnRtnErrorEvent;
import matlab.MatLabOnRtnOrderEvent;
import matlab.MatLabOnRtnTradeEvent;
import matlab.MatLabTradeListener;
import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.TradingNativeInterface;
import orderrefgenerator.MessageIDGenerator;
import orderrefgenerator.OrderRefGenerator;
import orderrepository.IncompleteBucketException;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;
import bo.ErrorResult;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderInsertResponse;
import bo.TradeDataResponse;
import bo.TradeRequest;


import factories.TradeRequestFactory;

public class MatLabTradeIntegrator {
	static{
		System.loadLibrary("CTPDLL");
		System.loadLibrary("CTPTRADEDLL");
		
	}
	

	
	public MatLabTradeIntegrator(){

	}
	
	public void sendSettlement(){
		new TradingNativeInterface().sendSettlementReqest("1013", "00000008");
	}
	
	public String sendInitialOrder(String direction, String  instrument, double price){
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
	
	public void setClosingOrders(String instrument, String originalOrderRef, double tgp, double stp){
		TradeRequestFactory factory = new TradeRequestFactory();
		OrderBucket orderBucket = OrderRepository.getInstance().getOrderBucket(instrument, originalOrderRef);
		TradeRequest initialOrder = orderBucket.getInitialRequest();
	
		double origPrice = initialOrder.getLimitPrice();
		
		TradeRequest exitRequest = factory.createRequest(instrument, origPrice + tgp, "1");
		TradeRequest stopLossRequest = factory.createRequest(instrument, origPrice - stp, "1");
		exitRequest.setOrderRef(OrderRefGenerator.getInstance().getNextRef());
		exitRequest.setOriginatingOrderRef(originalOrderRef);
		exitRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		
		stopLossRequest.setOrderRef(OrderRefGenerator.getInstance().getNextRef());
		stopLossRequest.setOriginatingOrderRef(originalOrderRef);
		stopLossRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		stopLossRequest.setCutOffPrice(origPrice - stp);
		
		orderBucket.setExitRequest(exitRequest);
		orderBucket.setStopLossRequest(stopLossRequest);
	}
	
	private List<MatLabTradeListener> matLabListeners = new ArrayList<MatLabTradeListener>(10);
	private class ICMatLabTradeListener extends DefaultCTPListener{

		




		@Override
		public void onRspError(ErrorResult errorRslt) {
			MatLabOnRtnErrorEvent errorEvent = new MatLabOnRtnErrorEvent(this, errorRslt);
			notifyMatLabOnRtnErrorEvent(errorEvent);
		}

		@Override
		public void onRtnOrder(OrderInsertResponse response) {
			MatLabOnRtnOrderEvent orderEvent = new MatLabOnRtnOrderEvent(this, response);
			notifyMatLabOnRtnOrderEvent(orderEvent);
		}

		@Override
		public void onRtnTradingData(TradeDataResponse response) {
			
			String instrument = response.getInstrumentID();
			OrderRepository repository = OrderRepository.getInstance();
			String originatingOrderRef = response.getOrderRef();
			OrderBucket bucket = repository.getOrderBucket(instrument, originatingOrderRef);
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
			
			MatLabOnRtnTradeEvent tradeEvent = new MatLabOnRtnTradeEvent(this, response);
			notifyMatLabOnRtnTradeEvent(tradeEvent);
		}

		@Override
		public void onRspUserLogin(LoginResponse loginResponse) {
			OrderRefGenerator.getInstance().setInitialValue(loginResponse.getMaxOrder());
			notifyMatLabOnLoginEvent(new MatLabOnLoginEvent(loginResponse));
		}
		
	}
	
	private static class ICMarketDataListener extends DefaultCTPListener{

		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {
			String insturment = response.getInstrumentId();
			List<OrderBucket> relatedOrders = OrderRepository.getInstance().getOrdersForInsturment(insturment);
			for(int i = 0, n = relatedOrders.size(); i < n; i++){
				OrderBucket bucket = relatedOrders.get(i);
				if(bucket.getStopLossRequest() != null && bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST && bucket.getStopLossRequest().getCutOffPrice() >= response.getLastPrice()){
					bucket.setOrderState(OrderBucket.orderStates.STOP_LOSS_COMPLETED);
					try {
						OrderRepository.getInstance().addOrderBucket(insturment, bucket.getStopLossRequest().getOrderRef(), bucket);
					} catch (IncompleteBucketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
	
	public void notifyMatLabOnLoginEvent(MatLabOnLoginEvent loginEvent){
		for(int i = 0, n = matLabListeners.size(); i  < n; i++){
			matLabListeners.get(i).matLabOnLoginEvent(loginEvent);
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
