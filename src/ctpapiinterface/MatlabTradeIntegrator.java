package ctpapiinterface;

import java.util.ArrayList;
import java.util.List;

import listeners.DefaultCTPListener;
import matlab.MatlabOnLoginEvent;
import matlab.MatlabOnOrderActionEvent;
import matlab.MatlabOnRtnErrorEvent;
import matlab.MatlabOnRtnOrderEvent;
import matlab.MatlabOnRtnTradeEvent;
import matlab.MatlabTradeListener;
import nativeinterfaces.InterfaceNotCreatedException;
import nativeinterfaces.MarketDataNativeInterface;
import nativeinterfaces.MockMDNativeInterface;
import nativeinterfaces.MockTradingInterface;
import nativeinterfaces.NativeInterfaceFactory;
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
	private  OrderRepository orderRepository = OrderRepository.getInstance();
	private MarketDataNativeInterface marketDataNativeInterface;
	private static final String INVESTOR_ID = "00000008";
	private static final String PASS = "123321";
	private TradingNativeInterface tradingNativeInterface;
	private static final String BROKER_ID = "1013";
	static{
		System.loadLibrary("CTPDLL");
		System.loadLibrary("CTPTRADEDLL");
		
	}
	

	
	public MatlabTradeIntegrator(int debugFlag, String mockDataPath){
		marketDataNativeInterface = NativeInterfaceFactory.getInstance().createMDInterface(debugFlag, mockDataPath);
		try {
			tradingNativeInterface = NativeInterfaceFactory.getInstance().createTradingInterface(debugFlag);
		} catch (InterfaceNotCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(new OrderTimeOutThread()).start();
	}
	
	public void sendSettlement(){
		tradingNativeInterface.sendSettlementReqest(BROKER_ID, INVESTOR_ID);
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
			orderRepository.addOrderBucket(insturment, bucket);
			tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, bucket.getInitialRequest());
			marketDataNativeInterface.sendQuoteRequest(new String[]{insturment});
		} catch (IncompleteBucketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return initOrderRef;
		
	}
	
	public void cancelOrder(String orderRef){
		OrderActionFactory factory = new OrderActionFactory();
		OrderBucket bucket = orderRepository.getBucketForOrigOrder(orderRef);
		TradeRequest initialRequest = bucket.getInitialRequest();
		OrderActionRequest request = factory.createOrderActionRequest(initialRequest.getInstrumentID(), initialRequest.getOrderRef());
		bucket.setOrderState(OrderBucket.orderStates.CANCELLED_BY_TRADER);
		tradingNativeInterface.sendOrderAction(BROKER_ID, PASS, INVESTOR_ID, request);
	}
	
	public String sendSingleOrder(String direction, String  instrument, double price, long timeOut){
		String nextOrder = OrderRefGenerator.getInstance().getNextRef();
		TradeRequestFactory factory = new TradeRequestFactory();
		TradeRequest request = factory.createRequest(instrument, price, direction);
		request.setOrderRef(nextOrder);
		request.setOriginatingOrderRef(nextOrder);
		request.setRequestID(MessageIDGenerator.getInstance().getNextID());
		OrderBucket bucket = new OrderBucket();
		bucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
		bucket.setInitialRequest(request);
		bucket.setTimeOut(timeOut);
		try {
			orderRepository.addOrderBucket(instrument, bucket);
			tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, bucket.getInitialRequest());
			marketDataNativeInterface.sendQuoteRequest(new String[]{instrument});
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
			MatlabOnOrderActionEvent actionEvent = new MatlabOnOrderActionEvent(this, initiatingAction);
			notifyMatlabOnOrderActionEvent(actionEvent);
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
			OrderRepository repository = orderRepository;
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
					marketDataNativeInterface.sendQuoteRequest(new String[]{instrument});
					tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, bucket.getExitRequest());
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST){
				bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.STOP_LOSS_COMPLETED){
				OrderActionFactory factory = new OrderActionFactory();
				OrderActionRequest request = factory.createOrderActionRequest(response.getInstrumentID(), bucket.getExitRequest().getOrderRef());
				tradingNativeInterface.sendOrderAction(BROKER_ID, PASS, INVESTOR_ID, request);
			}
			
			

		}

		@Override
		public void onRspUserLogin(LoginResponse loginResponse) {
			OrderRefGenerator.getInstance().setInitialValue(loginResponse.getMaxOrder());
			notifyMatlabOnLoginEvent(new MatlabOnLoginEvent(loginResponse));
		}
		
	}
	
	private class ICMarketDataListener extends DefaultCTPListener{

		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {
			String insturment = response.getInstrumentId();
			List<OrderBucket> relatedOrders = orderRepository.searchBucketsOnState(insturment, OrderBucket.orderStates.EXIT_REQUEST);
			for(int i = 0, n = relatedOrders.size(); i < n; i++){
				OrderBucket bucket = relatedOrders.get(i);
				String intialDirection = bucket.getInitialRequest().getDirection();
				TradeRequest stopLossRequest = bucket.getStopLossRequest();
				if(stopLossRequest != null ){
					if("0".equals(intialDirection) && stopLossRequest.getCutOffPrice() >= response.getLastPrice()){
						bucket.setOrderState(OrderBucket.orderStates.STOP_LOSS_COMPLETED);
						stopLossRequest.setLimitPrice(response.getLastPrice());
						tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, stopLossRequest);
					}
					if("1".equals(intialDirection) && stopLossRequest.getCutOffPrice() <= response.getLastPrice()){
						bucket.setOrderState(OrderBucket.orderStates.STOP_LOSS_COMPLETED);
						stopLossRequest.setLimitPrice(response.getLastPrice());
						tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, stopLossRequest);
					}
					
				}
			}
		}
		
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		tradingNativeInterface.subscribeListener(new ICMatLabTradeListener());
		marketDataNativeInterface.subscribeListener(new ICMarketDataListener());
		tradingNativeInterface.sendLoginMessage(brokerId, password, investorId);
		marketDataNativeInterface.sendLoginMessage(brokerId, password, investorId);

	}
	
	public String sendOrder(String orderType, double price, String instrumentId, long timeOut){
		String initOrderRef = OrderRefGenerator.getInstance().getNextRef();
		try {
			
			TradeRequestFactory factory = new TradeRequestFactory();
			TradeRequest initialRequest = factory.createRequest(instrumentId, price, orderType);
			initialRequest.setOrderRef(initOrderRef);
			initialRequest.setOriginatingOrderRef(initOrderRef);
			initialRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
			OrderBucket bucket = new OrderBucket();
			bucket.setTimeOut(timeOut);
			bucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
			bucket.setInitialRequest(initialRequest);
			orderRepository.addOrderBucket(instrumentId, bucket);
			bucket.setTimeOut(timeOut);
			tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, bucket.getInitialRequest());
			marketDataNativeInterface.sendQuoteRequest(new String[]{instrumentId});

		} catch (IncompleteBucketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return initOrderRef;
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
	
	public void notifyMatlabOnOrderActionEvent(MatlabOnOrderActionEvent actionEvent){
		for(int i = 0, n = matLabListeners.size(); i < n; i++){
			matLabListeners.get(i).matlabOnOrderActionEvent(actionEvent);
	}
	}
	
}
