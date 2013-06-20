package matlab;

import bo.OrderInsertResponse;

public class MatlabOnRtnOrderEvent extends java.util.EventObject{

	private OrderInsertResponse delegate;

	
	public String getBrokerID() {
		return delegate.getBrokerID();
	}

	public void setBrokerID(String brokerID) {
		delegate.setBrokerID(brokerID);
	}

	public String getInvestorID() {
		return delegate.getInvestorID();
	}

	public void setInvestorID(String investorID) {
		delegate.setInvestorID(investorID);
	}

	public String getInstrumentID() {
		return delegate.getInstrumentID();
	}

	public void setInstrumentID(String instrumentID) {
		delegate.setInstrumentID(instrumentID);
	}

	public String getOrderRef() {
		return delegate.getOrderRef();
	}

	public void setOrderRef(String orderRef) {
		delegate.setOrderRef(orderRef);
	}

	public String getUserID() {
		return delegate.getUserID();
	}

	public void setUserID(String userID) {
		delegate.setUserID(userID);
	}

	public String getOrderPriceType() {
		return delegate.getOrderPriceType();
	}

	public void setOrderPriceType(String orderPriceType) {
		delegate.setOrderPriceType(orderPriceType);
	}

	public String getDirection() {
		return delegate.getDirection();
	}

	public void setDirection(String direction) {
		delegate.setDirection(direction);
	}

	public String getCombOffsetFlag() {
		return delegate.getCombOffsetFlag();
	}

	public void setCombOffsetFlag(String combOffsetFlag) {
		delegate.setCombOffsetFlag(combOffsetFlag);
	}

	public String getCombHedgeFlag() {
		return delegate.getCombHedgeFlag();
	}

	public void setCombHedgeFlag(String combHedgeFlag) {
		delegate.setCombHedgeFlag(combHedgeFlag);
	}

	public double getLimitPrice() {
		return delegate.getLimitPrice();
	}

	public void setLimitPrice(double limitPrice) {
		delegate.setLimitPrice(limitPrice);
	}

	public int getVolumeTotalOriginal() {
		return delegate.getVolumeTotalOriginal();
	}

	public void setVolumeTotalOriginal(int volumeTotalOriginal) {
		delegate.setVolumeTotalOriginal(volumeTotalOriginal);
	}

	public String getTimeCondition() {
		return delegate.getTimeCondition();
	}

	public void setTimeCondition(String timeCondition) {
		delegate.setTimeCondition(timeCondition);
	}

	public String getGtdDate() {
		return delegate.getGtdDate();
	}

	public void setGtdDate(String gtdDate) {
		delegate.setGtdDate(gtdDate);
	}

	public String getVolumeCondition() {
		return delegate.getVolumeCondition();
	}

	public void setVolumeCondition(String volumeCondition) {
		delegate.setVolumeCondition(volumeCondition);
	}

	public int getMinVolume() {
		return delegate.getMinVolume();
	}

	public void setMinVolume(int minVolume) {
		delegate.setMinVolume(minVolume);
	}

	public String getContingentCondition() {
		return delegate.getContingentCondition();
	}

	public void setContingentCondition(String contingentCondition) {
		delegate.setContingentCondition(contingentCondition);
	}

	public double getStopPrice() {
		return delegate.getStopPrice();
	}

	public void setStopPrice(double stopPrice) {
		delegate.setStopPrice(stopPrice);
	}

	public String getForceCloseReason() {
		return delegate.getForceCloseReason();
	}

	public void setForceCloseReason(String forceCloseReason) {
		delegate.setForceCloseReason(forceCloseReason);
	}

	public int getAutoSuspend() {
		return delegate.getAutoSuspend();
	}

	public void setAutoSuspend(int autoSuspend) {
		delegate.setAutoSuspend(autoSuspend);
	}

	public String getBusinessUnit() {
		return delegate.getBusinessUnit();
	}

	public void setBusinessUnit(String businessUnit) {
		delegate.setBusinessUnit(businessUnit);
	}

	public int getRequestID() {
		return delegate.getRequestID();
	}

	public void setRequestID(int requestID) {
		delegate.setRequestID(requestID);
	}

	public String getOrderLocalID() {
		return delegate.getOrderLocalID();
	}

	public void setOrderLocalID(String orderLocalID) {
		delegate.setOrderLocalID(orderLocalID);
	}

	public String getExchangeID() {
		return delegate.getExchangeID();
	}

	public void setExchangeID(String exchangeID) {
		delegate.setExchangeID(exchangeID);
	}

	public String getParticipantID() {
		return delegate.getParticipantID();
	}

	public void setParticipantID(String participantID) {
		delegate.setParticipantID(participantID);
	}

	public String getClientID() {
		return delegate.getClientID();
	}

	public void setClientID(String clientID) {
		delegate.setClientID(clientID);
	}

	public String getExchangeInstID() {
		return delegate.getExchangeInstID();
	}

	public void setExchangeInstID(String exchangeInstID) {
		delegate.setExchangeInstID(exchangeInstID);
	}

	public String getTraderID() {
		return delegate.getTraderID();
	}

	public void setTraderID(String traderID) {
		delegate.setTraderID(traderID);
	}

	public int getInstallID() {
		return delegate.getInstallID();
	}

	public void setInstallID(int installID) {
		delegate.setInstallID(installID);
	}

	public String getOrderSubmitStatus() {
		return delegate.getOrderSubmitStatus();
	}

	public void setOrderSubmitStatus(String orderSubmitStatus) {
		delegate.setOrderSubmitStatus(orderSubmitStatus);
	}

	public int getNotifySequence() {
		return delegate.getNotifySequence();
	}

	public void setNotifySequence(int notifySequence) {
		delegate.setNotifySequence(notifySequence);
	}

	public String getTradingDay() {
		return delegate.getTradingDay();
	}

	public void setTradingDay(String tradingDay) {
		delegate.setTradingDay(tradingDay);
	}

	public int getSettlementID() {
		return delegate.getSettlementID();
	}

	public void setSettlementID(int settlementID) {
		delegate.setSettlementID(settlementID);
	}

	public String getOrderSysID() {
		return delegate.getOrderSysID();
	}

	public void setOrderSysID(String orderSysID) {
		delegate.setOrderSysID(orderSysID);
	}

	public String getOrderSource() {
		return delegate.getOrderSource();
	}

	public void setOrderSource(String orderSource) {
		delegate.setOrderSource(orderSource);
	}

	public String getOrderStatus() {
		return delegate.getOrderStatus();
	}

	public void setOrderStatus(String orderStatus) {
		delegate.setOrderStatus(orderStatus);
	}

	public String getOrderType() {
		return delegate.getOrderType();
	}

	public void setOrderType(String orderType) {
		delegate.setOrderType(orderType);
	}

	public int getVolumeTraded() {
		return delegate.getVolumeTraded();
	}

	public void setVolumeTraded(int volumeTraded) {
		delegate.setVolumeTraded(volumeTraded);
	}

	public int getVolumeTotal() {
		return delegate.getVolumeTotal();
	}

	public void setVolumeTotal(int volumeTotal) {
		delegate.setVolumeTotal(volumeTotal);
	}

	public String getInsertDate() {
		return delegate.getInsertDate();
	}

	public void setInsertDate(String insertDate) {
		delegate.setInsertDate(insertDate);
	}

	public String getInsertTime() {
		return delegate.getInsertTime();
	}

	public void setInsertTime(String insertTime) {
		delegate.setInsertTime(insertTime);
	}

	public String getActiveTime() {
		return delegate.getActiveTime();
	}

	public void setActiveTime(String activeTime) {
		delegate.setActiveTime(activeTime);
	}

	public String getSuspendTime() {
		return delegate.getSuspendTime();
	}

	public void setSuspendTime(String suspendTime) {
		delegate.getSuspendTime();
	}

	public String getUpdateTime() {
		return delegate.getUpdateTime();
	}

	public void setUpdateTime(String updateTime) {
		delegate.setUpdateTime(updateTime);
	}

	public String getCancelTime() {
		return delegate.getCancelTime();
	}

	public void setCancelTime(String cancelTime) {
		delegate.setCancelTime(cancelTime);
	}

	public String getActiveTraderID() {
		return delegate.getActiveTraderID();
	}

	public void setActiveTraderID(String activeTraderID) {
		delegate.setActiveTraderID(activeTraderID);
	}

	public String getClearingPartID() {
		return delegate.getClearingPartID();
	}

	public void setClearingPartID(String clearingPartID) {
		delegate.setClearingPartID(clearingPartID);
	}

	public int getSequenceNo() {
		return delegate.getSequenceNo();
	}

	public void setSequenceNo(int sequenceNo) {
		delegate.setSequenceNo(sequenceNo);
	}

	public int getFrontID() {
		return delegate.getFrontID();
	}

	public void setFrontID(int frontID) {
		delegate.setFrontID(frontID);
	}

	public int getSessionID() {
		return delegate.getSessionID();
	}

	public void setSessionID(int sessionID) {
		delegate.setSessionID(sessionID);
	}

	public String getUserProductInfo() {
		return delegate.getUserProductInfo();
	}

	public void setUserProductInfo(String userProductInfo) {
		delegate.setUserProductInfo(userProductInfo);
	}

	public String getStatusMsg() {
		return delegate.getStatusMsg();
	}

	public void setStatusMsg(String statusMsg) {
		delegate.setStatusMsg(statusMsg);
	}

	public int getUserForceClose() {
		return delegate.getUserForceClose();
	}

	public void setUserForceClose(int userForceClose) {
		delegate.setUserForceClose(userForceClose);
	}

	public String getActiveUserID() {
		return delegate.getActiveUserID();
	}

	public void setActiveUserID(String activeUserID) {
		delegate.setActiveUserID(activeUserID);
	}

	public int getBrokerOrderSeq() {
		return delegate.getBrokerOrderSeq();
	}

	public void setBrokerOrderSeq(int brokerOrderSeq) {
		delegate.setBrokerOrderSeq(brokerOrderSeq);
	}

	public String getRelativeOrderSysID() {
		return delegate.getRelativeOrderSysID();
	}

	public void setRelativeOrderSysID(String relativeOrderSysID) {
		delegate.setRelativeOrderSysID(relativeOrderSysID);
	}

	public int getZceTotalTradedVolume() {
		return delegate.getZceTotalTradedVolume();
	}

	public void setZceTotalTradedVolume(int zceTotalTradedVolume) {
		delegate.setZceTotalTradedVolume(zceTotalTradedVolume);
	}

	public int getSwapOrder() {
		return delegate.getSwapOrder();
	}

	public void setSwapOrder(int swapOrder) {
		delegate.setSwapOrder(swapOrder);
	}

	public MatlabOnRtnOrderEvent(Object source, OrderInsertResponse delegate){
		super(source);
		this.delegate = delegate;
	}
	
	public MatlabOnRtnOrderEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
