package nativeinterfaces;

import java.util.ArrayList;
import java.util.List;

import listeners.DefaultCTPListener;
import bo.LoginResponse;
import bo.OrderActionRequest;
import bo.OrderInsertResponse;
import bo.SettlementResponse;
import bo.TradeDataResponse;
import bo.TradeRequest;

public class MockTradingInterface extends TradingNativeInterface {
	private List<DefaultCTPListener> listeners = new ArrayList<DefaultCTPListener>(10);
	@Override
	public void sendLoginMessage(String brokerId, String password, String investorId) {
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
		
		TradeDataResponse mockTradeInsert = new TradeDataResponse();
		mockTradeInsert.setBrokerID(brokerId);
		mockTradeInsert.setBrokerOrderSeq(request.getRequestID());
		mockTradeInsert.setBusinessUnit(request.getBusinessUnit());
		mockTradeInsert.setClearingPartID("");
		mockTradeInsert.setClientID("");
		mockTradeInsert.setDirection(request.getDirection());
		mockTradeInsert.setExchangeID("");
		mockTradeInsert.setExchangeInstID("");

		mockTradeInsert.setHedgeFlag("");
		mockTradeInsert.setInstrumentID(request.getInstrumentID());
		mockTradeInsert.setInvestorID(investorId);
		mockTradeInsert.setOffsetFlag("");
		mockTradeInsert.setOrderLocalID("");
		mockTradeInsert.setOrderRef(request.getOrderRef());
		mockTradeInsert.setOrderSysID("");
		mockTradeInsert.setParticipantID("");
		mockTradeInsert.setPrice(request.getLimitPrice());
		mockTradeInsert.setPriceSource("");
		mockTradeInsert.setSequenceNo(0);
		mockTradeInsert.setSettlementID(0);
		mockTradeInsert.setTradeDate("");
		mockTradeInsert.setTradeID("");
		mockTradeInsert.setTradeSource("");
		mockTradeInsert.setTradeTime("");
		mockTradeInsert.setTradeType("");
		mockTradeInsert.setTradingDay("");
		mockTradeInsert.setTradingRole("");
		mockTradeInsert.setUserID(brokerId);
		mockTradeInsert.setVolume(0);
		
		for(int i = 0, n = listeners.size(); i < n ; i++){
			listeners.get(i).onRtnOrder(mockOrderInsert);
			listeners.get(i).onRtnTradingData(mockTradeInsert);
		}
	}

	@Override
	public void sendOrderAction(String brokerId, String password,
			String investorId, OrderActionRequest request) {
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

}
