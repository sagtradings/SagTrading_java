package nativeinterfaces;

import java.util.ArrayList;
import java.util.List;

import listeners.DefaultCTPListener;
import bo.LoginResponse;
import bo.MarketDataResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.SettlementResponse;
import bo.TradeDataResponse;
import bo.TradeRequest;

public class MockTradingInterface extends TradingNativeInterface {
	private List<DefaultCTPListener> listeners = new ArrayList<DefaultCTPListener>(10);
	private MockMDNativeInterface mdInterface;
	private List<TradeRequest> tradeQueue = new ArrayList<TradeRequest>(10);
	
	private class ICmdListener extends DefaultCTPListener{

		@Override
		public void onRtnDepthMarketData(MarketDataResponse response) {
			List<TradeRequest> newList = new ArrayList<TradeRequest>(tradeQueue.size());
			List<TradeDataResponse> confirmedTrades = new ArrayList<TradeDataResponse>(tradeQueue.size());
			for(int i = 0, n = tradeQueue.size(); i < n; i++){
				TradeRequest tradeRequest = tradeQueue.get(0);
				TradeDataResponse mockTradeInsert = new TradeDataResponse();
				mockTradeInsert.setBrokerID(tradeRequest.getBrokerID());
				mockTradeInsert.setVolume(tradeRequest.getVolumeTotalOriginal());
				mockTradeInsert.setBusinessUnit(tradeRequest.getBusinessUnit());
				mockTradeInsert.setDirection(tradeRequest.getDirection());
				mockTradeInsert.setInstrumentID(tradeRequest.getInstrumentID());
				mockTradeInsert.setInvestorID(tradeRequest.getInvestorID());
				mockTradeInsert.setOrderRef(tradeRequest.getOrderRef());
				mockTradeInsert.setPrice(response.getLastPrice());
				mockTradeInsert.setUserID(tradeRequest.getUserID());
				if(tradeRequest.getLimitPrice() > response.getLastPrice() && "0".equals(tradeRequest.getDirection())){
					confirmedTrades.add(mockTradeInsert);
				}
				else if(tradeRequest.getLimitPrice() < response.getLastPrice() && "1".equals(tradeRequest.getDirection())){
					confirmedTrades.add(mockTradeInsert);
				}
				else{
					newList.add(tradeRequest);
				}
				tradeQueue.remove(0);
			}
			tradeQueue = newList;
			for(int i = 0, n = confirmedTrades.size(); i < n; i++){
				for(int j = 0, k = listeners.size(); j < k; j++){
					listeners.get(j).onRtnTradingData(confirmedTrades.get(i));
				}
			}
			
		}
		
	}
	
	public MockTradingInterface(MockMDNativeInterface mdInterface){
		this.mdInterface = mdInterface;
	}
	
	@Override
	public void sendLoginMessage(String brokerId, String password, String investorId, String url) {
		mdInterface.subscribeListener(new ICmdListener());
		mdInterface.initiateTimer();
		LoginResponse mockResponse = new LoginResponse();
		mockResponse.setMaxOrder(1);
		
		for(int i = 0, n = listeners.size(); i < n; i++){
			
			listeners.get(i).onRspUserLogin(mockResponse);
		}
	}

	@Override
	public void sendTradeRequest(String brokerId, String password, String investorId, TradeRequest request) {
		OrderInsertResponse mockOrderInsert = new OrderInsertResponse();
		mockOrderInsert.setActiveTime("");
		mockOrderInsert.setActiveTraderID(investorId);
		mockOrderInsert.setAutoSuspend(0);
		mockOrderInsert.setBrokerID(brokerId);
		mockOrderInsert.setBrokerOrderSeq(request.getRequestID());
		mockOrderInsert.setBusinessUnit(request.getBusinessUnit());
		mockOrderInsert.setCancelTime("");
		mockOrderInsert.setClearingPartID("");
		mockOrderInsert.setClientID("");
		mockOrderInsert.setCombHedgeFlag(request.getCombHedgeFlag());
		mockOrderInsert.setCombOffsetFlag(request.getCombOffsetFlag());
		mockOrderInsert.setContingentCondition(request.getContingentCondition());
		mockOrderInsert.setDirection(request.getDirection());
		mockOrderInsert.setExchangeID("");
		mockOrderInsert.setExchangeInstID("");
		mockOrderInsert.setForceCloseReason(request.getForceCloseReason());
		mockOrderInsert.setFrontID(0);
		mockOrderInsert.setGtdDate(request.getGtdDate());
		mockOrderInsert.setInsertDate("");
		mockOrderInsert.setInsertTime("");
		mockOrderInsert.setInstallID(0);
		mockOrderInsert.setInstrumentID(request.getInstrumentID());
		mockOrderInsert.setInvestorID(investorId);
		mockOrderInsert.setLimitPrice(request.getLimitPrice());
		mockOrderInsert.setMinVolume(request.getMinVolume());
		mockOrderInsert.setNotifySequence(0);
		mockOrderInsert.setOrderLocalID("");
		mockOrderInsert.setOrderPriceType(request.getOrderPriceType());
		mockOrderInsert.setOrderRef(request.getOrderRef());
		mockOrderInsert.setOrderSource("");
		mockOrderInsert.setOrderStatus("");
		mockOrderInsert.setOrderSubmitStatus("");
		mockOrderInsert.setOrderSysID("");
		mockOrderInsert.setOrderType("");
		mockOrderInsert.setParticipantID("");
		mockOrderInsert.setRelativeOrderSysID("");
		mockOrderInsert.setRequestID(request.getRequestID());
		mockOrderInsert.setSequenceNo(request.getRequestID());
		mockOrderInsert.setSessionID(0);
		mockOrderInsert.setSettlementID(0);
		mockOrderInsert.setStatusMsg("");
		mockOrderInsert.setStopPrice(request.getStopPrice());
		mockOrderInsert.setSuspendTime("");
		mockOrderInsert.setSwapOrder(0);
		tradeQueue.add(request);

		
		for(int i = 0, n = listeners.size(); i < n ; i++){
			listeners.get(i).onRtnOrder(mockOrderInsert);
		}
	}

	@Override
	public void sendOrderAction(String brokerId, String password,
			String investorId, OrderActionRequest request) {
		for(int i = 0, n = tradeQueue.size(); i < n; i++){
			if(tradeQueue.get(i).getOrderRef().equals(request.getOrderRef())){
				tradeQueue.remove(i);
				break;
			}
		}
		for(int i = 0, n = listeners.size(); i < n; i++){
			listeners.get(i).onOrderActionResponse(request);
		}
	}

	@Override
	public void subscribeListener(DefaultCTPListener obj) {
		listeners.add(obj);
	}

	@Override
	public void unSubscribeListener(DefaultCTPListener obj) {
		// TODO Auto-generated method stub
		super.unSubscribeListener(obj);
	}

	@Override
	public void sendSettlementReqest(String brokerID, String userID) {
		SettlementResponse mockResponse = new SettlementResponse();
		mockResponse.setBrokerID(brokerID);
		mockResponse.setConfirmDate("");
		mockResponse.setConfirmTime("");
		mockResponse.setInvestorID(userID);
		for(int i = 0, n = listeners.size(); i < n; i++){
			listeners.get(i).onSettlementResponse(mockResponse);
		}
	}
	
	public void kill(){
		
	}

}
