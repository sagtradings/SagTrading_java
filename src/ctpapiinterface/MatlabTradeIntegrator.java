package ctpapiinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import listeners.DefaultCTPListener;
import matlab.MatlabOnLoginEvent;
import matlab.MatlabOnOrderActionEvent;
import matlab.MatlabOnOrderCycleCompleteEvent;
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
import orderpositionregistry.IVolumeCalculator;
import orderpositionregistry.InstrumentPositionRegistry;
import orderpositionregistry.LocalInstrumentPositionRegistry;
import orderrefgenerator.MessageIDGenerator;
import orderrefgenerator.OrderRefGenerator;
import orderrepository.ISignablePrice;
import orderrepository.IncompleteBucketException;
import orderrepository.OrderBucket;
import orderrepository.OrderRepository;
import orderrepository.OrderTimeOutThread;
import properties.PropertiesManager;
import tradeevaluators.IEvaluateTrade;
import tradeevaluators.TradeEvaluatorRegistry;
import tradeloggers.CsvLogger;
import tradeloggers.OrderActionRequestLogger;
import tradeloggers.OrderFilledLogger;
import tradeloggers.OrderRequestLogger;
import tradeloggers.TradeDataResponseLogger;
import bo.ErrorResult;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.TradeDataResponse;
import bo.TradeRequest;
import factories.OrderActionFactory;
import factories.OrderBucketFactory;
import factories.TradeRequestFactory;

public class MatlabTradeIntegrator {
	private  OrderRepository orderRepository = OrderRepository.getInstance();

	
	private static final String tradeConnectionURL = PropertiesManager.getInstance().getProperty("tradedataurl");
	private static final String marketDataURL = PropertiesManager.getInstance().getProperty("marketdataurl");
	private MarketDataNativeInterface marketDataNativeInterface;
	private static final String INVESTOR_ID = "00000008";
	private static final String PASS = "123321";
	private TradingNativeInterface tradingNativeInterface;
	private static final String BROKER_ID = "1013";
	private LocalInstrumentPositionRegistry localInstrumentPositionRegistry = new LocalInstrumentPositionRegistry();
	private CsvLogger actionRequestLogger = new OrderActionRequestLogger();
	private CsvLogger actionResponseLogger = new OrderActionRequestLogger();
	private CsvLogger tradeRequestLogger = new OrderRequestLogger();
	private CsvLogger tradeAcceptedLogger = new OrderFilledLogger();
	private CsvLogger tradeFilledLogger = new TradeDataResponseLogger();
	
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
	
	public double getPosition(String instrument){
		Double answer = InstrumentPositionRegistry.getInstance().getPosition(instrument);
		if(answer == null){
			return 0;
		}
		return answer;
	}
	
	public double getLocalPosition(String instrument){
		Double answer = localInstrumentPositionRegistry.getPosition(instrument);
		if(answer == null){
			return 0;
		}
		return answer;
	}
	
	public OrderBucket.orderStates getOrderStatus(String orderRef){
		OrderBucket bucket = orderRepository.getBucketForOrigOrder(orderRef);
		if(bucket == null){
			return null;
		}
		return bucket.getOrderState();
	}
	
	public String sendOrderSet(String insturment, int initSide, int signalType, int signalPrice, int initSize, long timeOut, double tgp, double tlp){
		String initOrderRef = OrderRefGenerator.getInstance().getNextRef();
		String exitOrderRef = OrderRefGenerator.getInstance().getNextRef();
		String stopLossOrderRef = OrderRefGenerator.getInstance().getNextRef();
		double currentPosition = localInstrumentPositionRegistry.getPosition(insturment);
		IVolumeCalculator volumeCalculator = localInstrumentPositionRegistry.getCalculator(initSide);
		TradeRequestFactory factory = new TradeRequestFactory();
		TradeRequest initialRequest = factory.createRequest(insturment, signalPrice, initSide);
		initialRequest.setVolumeTotalOriginal((int) volumeCalculator.computeVolume(initialRequest, currentPosition));
		initialRequest.setOrderRef(initOrderRef);
		initialRequest.setOriginatingOrderRef(initOrderRef);
		initialRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		OrderBucket bucket = new OrderBucketFactory().createBucket(signalType, signalPrice);
		bucket.setOrderDirection(initSide);
		bucket.setTimeOut(timeOut);
		bucket.setOrderState(OrderBucket.orderStates.WAITING);
		bucket.setInitialRequest(initialRequest);
		
		TradeRequest exitRequest = factory.createRequest(insturment, signalPrice + tgp, initSide == 0 || initSide == 1 ? 2 : 0 );
		exitRequest.setOrderRef(exitOrderRef);
		exitRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		exitRequest.setOriginatingOrderRef(initOrderRef);
		exitRequest.setVolumeTotalOriginal(initSize);
		bucket.setExitRequest(exitRequest);
		
		TradeRequest stopRequest = factory.createRequest(insturment, signalPrice - tlp, initSide == 0 || initSide == 1 ? 2 : 0);
		stopRequest.setOrderRef(stopLossOrderRef);
		stopRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
		stopRequest.setOriginatingOrderRef(initOrderRef);
		stopRequest.setCutOffPrice(signalPrice - tlp);
		stopRequest.setVolumeTotalOriginal(initSize);
		bucket.setStopLossRequest(stopRequest);
		
		try {
			orderRepository.addOrderBucket(insturment, bucket);
			if(bucket.getClass() == OrderBucket.class){
				bucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
				tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, bucket.getInitialRequest());
				tradeRequestLogger.logObject(bucket.getInitialRequest(), "orderRequests");
			}
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
		actionRequestLogger.logObject(request, "actionRequests");
	}
	
	public String sendSingleOrder(int direction, String  instrument, double price, long timeOut){
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
			tradeRequestLogger.logObject(bucket.getInitialRequest(), "orderRequests");
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
			actionResponseLogger.logObject(initiatingAction, "actionResponses");
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
			tradeAcceptedLogger.logObject(response, "ordersAccepted");
		}

		@Override
		public void onRtnTradingData(TradeDataResponse response) {
			tradeFilledLogger.logObject(response, "tradesFilled");
			double currentPosition = InstrumentPositionRegistry.getInstance().getPosition(response.getInstrumentID());
			double currentLocalPosition = localInstrumentPositionRegistry.getPosition(response.getInstrumentID());
			double positionChange = "0".equals(response.getDirection()) ? -response.getVolume():response.getVolume();
			InstrumentPositionRegistry.getInstance().putPosition(response.getInstrumentID(), currentPosition + positionChange);
			localInstrumentPositionRegistry.putPosition(response.getInstrumentID(), currentLocalPosition + positionChange);
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
				MatlabOnOrderCycleCompleteEvent cycleEvent = new MatlabOnOrderCycleCompleteEvent(this);
				cycleEvent.setOrderRef(response.getOrderRef());
				cycleEvent.setCompletionType(0);
				notifyMatlabOnOrderComplete(cycleEvent);
			} 
			else if(bucket.getOrderState() == OrderBucket.orderStates.INITIAL_REQUEST){
					bucket.setOrderState(OrderBucket.orderStates.EXIT_REQUEST);
					marketDataNativeInterface.sendQuoteRequest(new String[]{instrument});
					tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, bucket.getExitRequest());
					tradeRequestLogger.logObject(bucket.getExitRequest(), "orderRequests");
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.EXIT_REQUEST){
				bucket.setOrderState(OrderBucket.orderStates.CYCLE_COMPLETED);
				MatlabOnOrderCycleCompleteEvent cycleEvent = new MatlabOnOrderCycleCompleteEvent(this);
				cycleEvent.setOrderRef(response.getOrderRef());
				cycleEvent.setCompletionType(0);
				notifyMatlabOnOrderComplete(cycleEvent);
			}
			else if(bucket.getOrderState() == OrderBucket.orderStates.STOP_LOSS_COMPLETED){
				OrderActionFactory factory = new OrderActionFactory();
				OrderActionRequest request = factory.createOrderActionRequest(response.getInstrumentID(), bucket.getExitRequest().getOrderRef());
				MatlabOnOrderCycleCompleteEvent cycleEvent = new MatlabOnOrderCycleCompleteEvent(this);
				cycleEvent.setOrderRef(response.getOrderRef());
				cycleEvent.setCompletionType(1);
				notifyMatlabOnOrderComplete(cycleEvent);
				tradingNativeInterface.sendOrderAction(BROKER_ID, PASS, INVESTOR_ID, request);
				actionRequestLogger.logObject(request, "actionRequests");
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
			List<OrderBucket> pendingBuckets = orderRepository.searchBucketsOnState(insturment, OrderBucket.orderStates.WAITING);
			for(int i = 0, n = pendingBuckets.size(); i < n; i++){
				OrderBucket pendingBucket = pendingBuckets.get(i);
				IEvaluateTrade evaluator = TradeEvaluatorRegistry.getEvaluator(pendingBucket.getClass());
				if(evaluator.evaluate((((ISignablePrice) pendingBucket).getSignalPrice()), response.getLastPrice(), pendingBucket.getInitialRequest().getDirection())){
					pendingBucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
					pendingBucket.getInitialRequest().setLimitPrice(response.getLastPrice());
					tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, pendingBucket.getInitialRequest());
					tradeRequestLogger.logObject(pendingBucket.getInitialRequest(), "orderRequests");
				}
			}
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
						tradeRequestLogger.logObject(stopLossRequest, "orderRequests");
					}
					if("1".equals(intialDirection) && stopLossRequest.getCutOffPrice() <= response.getLastPrice()){
						bucket.setOrderState(OrderBucket.orderStates.STOP_LOSS_COMPLETED);
						stopLossRequest.setLimitPrice(response.getLastPrice());
						tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, stopLossRequest);
						tradeRequestLogger.logObject(stopLossRequest, "orderRequests");
					}
					
				}
			}
		}
		
	}
	
	public void requestLogin(String brokerId, String password, String investorId){
		tradingNativeInterface.subscribeListener(new ICMatLabTradeListener());
		marketDataNativeInterface.subscribeListener(new ICMarketDataListener());
		tradingNativeInterface.sendLoginMessage(brokerId, password, investorId, tradeConnectionURL);
		marketDataNativeInterface.sendLoginMessage(brokerId, password, investorId, marketDataURL);

	}
	
	public String sendOrder(String instrumentId, int orderType, int signalType, int signalPrice,  int initSize,  long timeOut){
		long milliseconds = System.currentTimeMillis();
		String initOrderRef = OrderRefGenerator.getInstance().getNextRef();
		try {
			
			TradeRequestFactory factory = new TradeRequestFactory();
			TradeRequest initialRequest = factory.createRequest(instrumentId, signalPrice, orderType);
			double currentPosition = localInstrumentPositionRegistry.getPosition(instrumentId);
			IVolumeCalculator volumeCalculator = localInstrumentPositionRegistry.getCalculator(orderType);
			initialRequest.setVolumeTotalOriginal((int) volumeCalculator.computeVolume(initialRequest, currentPosition));
			initialRequest.setOrderRef(initOrderRef);
			initialRequest.setOriginatingOrderRef(initOrderRef);
			initialRequest.setRequestID(MessageIDGenerator.getInstance().getNextID());
			OrderBucket bucket = new OrderBucketFactory().createBucket(signalType, signalPrice);
			bucket.setOrderDirection(orderType);
			bucket.setTimeOut(timeOut);
			bucket.setOrderState(OrderBucket.orderStates.WAITING);
			bucket.setInitialRequest(initialRequest);
			orderRepository.addOrderBucket(instrumentId, bucket);
			bucket.setTimeOut(timeOut);
			if(bucket.getClass() == OrderBucket.class){
				bucket.setOrderState(OrderBucket.orderStates.INITIAL_REQUEST);
				tradingNativeInterface.sendTradeRequest(BROKER_ID, PASS, INVESTOR_ID, bucket.getInitialRequest());
				tradeRequestLogger.logObject(bucket.getInitialRequest(), "orderRequests");
			}
			
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
	
	public void notifyMatlabOnOrderComplete(MatlabOnOrderCycleCompleteEvent actionEvent){
		for(int i = 0, n = matLabListeners.size(); i < n; i++){
			matLabListeners.get(i).matlabOnCycleCompleted(actionEvent);
		}
	}
	
}
