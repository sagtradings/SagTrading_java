package bo;

public class TradeInsertResponse {
	private long creationTimeStamp;
	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}
	private String	brokerID;
	private String	investorID;
	private String	instrumentID;
	private String	orderRef;
	private String	userID;
	private String	exchangeID;
	private String	tradeID;
	private String	direction;
	private String	orderSysID;
	private String	participantID;
	private String	clientID;
	private String	tradingRole;
	private String	exchangeInstID;
	private String	offsetFlag;
	private String	hedgeFlag;
	private double	price;
	private int	volume;
	private String	tradeDate;
	private String	tradeTime;
	private String	tradeType;
	private String	priceSource;
	private String	traderID;
	private String	orderLocalID;
	private String	clearingPartID;
	private String	businessUnit;
	private String	sequenceNo;
	private String	tradingDay;
	private int	settlementID;
	private int	brokerOrderSeq;
	private String	tradeSource;
	
	public TradeInsertResponse(){
		this.creationTimeStamp = System.currentTimeMillis();
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
	public String getExchangeID() {
		return exchangeID;
	}
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
	public String getTradeID() {
		return tradeID;
	}
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getOrderSysID() {
		return orderSysID;
	}
	public void setOrderSysID(String orderSysID) {
		this.orderSysID = orderSysID;
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
	public String getTradingRole() {
		return tradingRole;
	}
	public void setTradingRole(String tradingRole) {
		this.tradingRole = tradingRole;
	}
	public String getExchangeInstID() {
		return exchangeInstID;
	}
	public void setExchangeInstID(String exchangeInstID) {
		this.exchangeInstID = exchangeInstID;
	}
	public String getOffsetFlag() {
		return offsetFlag;
	}
	public void setOffsetFlag(String offsetFlag) {
		this.offsetFlag = offsetFlag;
	}
	public String getHedgeFlag() {
		return hedgeFlag;
	}
	public void setHedgeFlag(String hedgeFlag) {
		this.hedgeFlag = hedgeFlag;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getPriceSource() {
		return priceSource;
	}
	public void setPriceSource(String priceSource) {
		this.priceSource = priceSource;
	}
	public String getTraderID() {
		return traderID;
	}
	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}
	public String getOrderLocalID() {
		return orderLocalID;
	}
	public void setOrderLocalID(String orderLocalID) {
		this.orderLocalID = orderLocalID;
	}
	public String getClearingPartID() {
		return clearingPartID;
	}
	public void setClearingPartID(String clearingPartID) {
		this.clearingPartID = clearingPartID;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
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
	public int getBrokerOrderSeq() {
		return brokerOrderSeq;
	}
	public void setBrokerOrderSeq(int brokerOrderSeq) {
		this.brokerOrderSeq = brokerOrderSeq;
	}
	public String getTradeSource() {
		return tradeSource;
	}
	public void setTradeSource(String tradeSource) {
		this.tradeSource = tradeSource;
	}
	
	
}
