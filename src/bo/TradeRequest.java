package bo;

public class TradeRequest {
	private String	brokerID = "";
	private String	investorID = "";
	private String	instrumentID = "";
	private String	orderRef = "";
	private String	userID = "";
	private String	orderPriceType = "";
	private String	direction = "";
	private String	combOffsetFlag = "";
	private String	combHedgeFlag = "";
	private double	limitPrice;
	private int	volumeTotalOriginal;
	private String	timeCondition = "";
	private String	gtdDate = "";
	private String	volumeCondition = "";
	private int	minVolume ;
	private String	contingentCondition = "";
	private double	stopPrice;
	private String	forceCloseReason = "";
	private int	autoSuspend;
	private String	businessUnit = "";
	private int	requestID;
	private int	userForceClose;
	private int	swapOrder;
	
	public TradeRequest(){
		
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
		minVolume = minVolume;
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
	public int getUserForceClose() {
		return userForceClose;
	}
	public void setUserForceClose(int userForceClose) {
		this.userForceClose = userForceClose;
	}
	public int getSwapOrder() {
		return swapOrder;
	}
	public void setSwapOrder(int swapOrder) {
		this.swapOrder = swapOrder;
	}
	
	
}
