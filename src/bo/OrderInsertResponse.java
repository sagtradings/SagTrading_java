package bo;

public class OrderInsertResponse {
	private String	brokerID;
	private String	investorID;
	private String	instrumentID;
	private String	orderRef;
	private String	userID;
	private String	orderPriceType;
	private String	direction;
	private String	combOffsetFlag;
	private String	combHedgeFlag;
	private double	limitPrice;
	private int	volumeTotalOriginal;
	private String	timeCondition;
	private String	gtdDate;
	private String	volumeCondition;
	private int	minVolume;
	private String	contingentCondition;
	private double	stopPrice;
	private String	forceCloseReason;
	private int	autoSuspend;
	private String	businessUnit;
	private int	requestID;
	private String	orderLocalID;
	private String	exchangeID;
	private String	participantID;
	private String	clientID;
	private String	exchangeInstID;
	private String	traderID;
	private int	installID;
	private String	orderSubmitStatus;
	private int	notifySequence;
	private String	tradingDay;
	private int	settlementID;
	private String	orderSysID;
	private String	orderSource;
	private String	orderStatus;
	private String	orderType;
	private int	volumeTraded;
	private int	volumeTotal;
	private String	insertDate;
	private String	insertTime;
	private String	activeTime;
	private String	suspendTime;
	private String	updateTime;
	private String	cancelTime;
	private String	activeTraderID;
	private String	clearingPartID;
	private int	sequenceNo;
	private int	frontID;
	private int	sessionID;
	private String	userProductInfo;
	private String	statusMsg;
	private int	userForceClose;
	private String	activeUserID;
	private int	brokerOrderSeq;
	private String	relativeOrderSysID;
	private int	zceTotalTradedVolume;
	private int	swapOrder;
	private long creationTimeStamp;
	
	public OrderInsertResponse(){
		this.creationTimeStamp = System.currentTimeMillis();
	}
	
	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public String getBrokerID() {
		return brokerID;
	}
	public void setBrokerID(String brokerID) {
		this.brokerID = brokerID;
	}
	public String getInvestorID() {
		return investorID;
	}
	public void setInvestorID(String investorID) {
		this.investorID = investorID;
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}
	public String getOrderRef() {
		return orderRef;
	}
	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getOrderPriceType() {
		return orderPriceType;
	}
	public void setOrderPriceType(String orderPriceType) {
		this.orderPriceType = orderPriceType;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCombOffsetFlag() {
		return combOffsetFlag;
	}
	public void setCombOffsetFlag(String combOffsetFlag) {
		this.combOffsetFlag = combOffsetFlag;
	}
	public String getCombHedgeFlag() {
		return combHedgeFlag;
	}
	public void setCombHedgeFlag(String combHedgeFlag) {
		this.combHedgeFlag = combHedgeFlag;
	}
	public double getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}
	public int getVolumeTotalOriginal() {
		return volumeTotalOriginal;
	}
	public void setVolumeTotalOriginal(int volumeTotalOriginal) {
		this.volumeTotalOriginal = volumeTotalOriginal;
	}
	public String getTimeCondition() {
		return timeCondition;
	}
	public void setTimeCondition(String timeCondition) {
		this.timeCondition = timeCondition;
	}
	public String getGtdDate() {
		return gtdDate;
	}
	public void setGtdDate(String gtdDate) {
		this.gtdDate = gtdDate;
	}
	public String getVolumeCondition() {
		return volumeCondition;
	}
	public void setVolumeCondition(String volumeCondition) {
		this.volumeCondition = volumeCondition;
	}
	public int getMinVolume() {
		return minVolume;
	}
	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}
	public String getContingentCondition() {
		return contingentCondition;
	}
	public void setContingentCondition(String contingentCondition) {
		this.contingentCondition = contingentCondition;
	}
	public double getStopPrice() {
		return stopPrice;
	}
	public void setStopPrice(double stopPrice) {
		this.stopPrice = stopPrice;
	}
	public String getForceCloseReason() {
		return forceCloseReason;
	}
	public void setForceCloseReason(String forceCloseReason) {
		this.forceCloseReason = forceCloseReason;
	}
	public int getAutoSuspend() {
		return autoSuspend;
	}
	public void setAutoSuspend(int autoSuspend) {
		this.autoSuspend = autoSuspend;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public String getOrderLocalID() {
		return orderLocalID;
	}
	public void setOrderLocalID(String orderLocalID) {
		this.orderLocalID = orderLocalID;
	}
	public String getExchangeID() {
		return exchangeID;
	}
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
	public String getParticipantID() {
		return participantID;
	}
	public void setParticipantID(String participantID) {
		this.participantID = participantID;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getExchangeInstID() {
		return exchangeInstID;
	}
	public void setExchangeInstID(String exchangeInstID) {
		this.exchangeInstID = exchangeInstID;
	}
	public String getTraderID() {
		return traderID;
	}
	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}
	public int getInstallID() {
		return installID;
	}
	public void setInstallID(int installID) {
		this.installID = installID;
	}
	public String getOrderSubmitStatus() {
		return orderSubmitStatus;
	}
	public void setOrderSubmitStatus(String orderSubmitStatus) {
		this.orderSubmitStatus = orderSubmitStatus;
	}
	public int getNotifySequence() {
		return notifySequence;
	}
	public void setNotifySequence(int notifySequence) {
		this.notifySequence = notifySequence;
	}
	public String getTradingDay() {
		return tradingDay;
	}
	public void setTradingDay(String tradingDay) {
		this.tradingDay = tradingDay;
	}
	public int getSettlementID() {
		return settlementID;
	}
	public void setSettlementID(int settlementID) {
		this.settlementID = settlementID;
	}
	public String getOrderSysID() {
		return orderSysID;
	}
	public void setOrderSysID(String orderSysID) {
		this.orderSysID = orderSysID;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getVolumeTraded() {
		return volumeTraded;
	}
	public void setVolumeTraded(int volumeTraded) {
		this.volumeTraded = volumeTraded;
	}
	public int getVolumeTotal() {
		return volumeTotal;
	}
	public void setVolumeTotal(int volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getSuspendTime() {
		return suspendTime;
	}
	public void setSuspendTime(String suspendTime) {
		this.suspendTime = suspendTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getActiveTraderID() {
		return activeTraderID;
	}
	public void setActiveTraderID(String activeTraderID) {
		this.activeTraderID = activeTraderID;
	}
	public String getClearingPartID() {
		return clearingPartID;
	}
	public void setClearingPartID(String clearingPartID) {
		this.clearingPartID = clearingPartID;
	}
	public int getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public int getFrontID() {
		return frontID;
	}
	public void setFrontID(int frontID) {
		this.frontID = frontID;
	}
	public int getSessionID() {
		return sessionID;
	}
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	public String getUserProductInfo() {
		return userProductInfo;
	}
	public void setUserProductInfo(String userProductInfo) {
		this.userProductInfo = userProductInfo;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public int getUserForceClose() {
		return userForceClose;
	}
	public void setUserForceClose(int userForceClose) {
		this.userForceClose = userForceClose;
	}
	public String getActiveUserID() {
		return activeUserID;
	}
	public void setActiveUserID(String activeUserID) {
		this.activeUserID = activeUserID;
	}
	public int getBrokerOrderSeq() {
		return brokerOrderSeq;
	}
	public void setBrokerOrderSeq(int brokerOrderSeq) {
		this.brokerOrderSeq = brokerOrderSeq;
	}
	public String getRelativeOrderSysID() {
		return relativeOrderSysID;
	}
	public void setRelativeOrderSysID(String relativeOrderSysID) {
		this.relativeOrderSysID = relativeOrderSysID;
	}
	public int getZceTotalTradedVolume() {
		return zceTotalTradedVolume;
	}
	public void setZceTotalTradedVolume(int zceTotalTradedVolume) {
		this.zceTotalTradedVolume = zceTotalTradedVolume;
	}
	public int getSwapOrder() {
		return swapOrder;
	}
	public void setSwapOrder(int swapOrder) {
		this.swapOrder = swapOrder;
	}

}
