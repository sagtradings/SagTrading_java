package bo;

public class OrderActionRequest {
	private long creationTimeStamp;
	private String	brokerID;
	private String	investorID;
	private int	orderActionRef;
	private String	orderRef;
	private int	requestID;
	private int	frontID;
	private int	sessionID;
	private String	exchangeID;
	private String	orderSysID;
	private String	actionFlag;
	private double	limitPrice;
	private int	volumeChange;
	private String	userID;
	private String	instrumentID;
	
	public OrderActionRequest(){
		this.creationTimeStamp = creationTimeStamp;
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
	public int getOrderActionRef() {
		return orderActionRef;
	}
	public void setOrderActionRef(int orderActionRef) {
		this.orderActionRef = orderActionRef;
	}
	public String getOrderRef() {
		return orderRef;
	}
	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
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
	public String getExchangeID() {
		return exchangeID;
	}
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
	public String getOrderSysID() {
		return orderSysID;
	}
	public void setOrderSysID(String orderSysID) {
		this.orderSysID = orderSysID;
	}
	public String getActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}
	public double getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}
	public int getVolumeChange() {
		return volumeChange;
	}
	public void setVolumeChange(int volumeChange) {
		this.volumeChange = volumeChange;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}
	
	
}
