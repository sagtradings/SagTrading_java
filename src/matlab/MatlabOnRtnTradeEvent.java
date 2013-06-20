package matlab;

import bo.TradeDataResponse;

public class MatlabOnRtnTradeEvent extends java.util.EventObject{
	private TradeDataResponse delegate;

	
	public String getBrokerID() {
		return delegate.getBrokerID();
	}

	public void setBrokerID(String brokerID) {
		delegate.setBrokerID(brokerID);
	}

	public int getBrokerOrderSeq() {
		return delegate.getBrokerOrderSeq();
	}

	public void setBrokerOrderSeq(int brokerOrderSeq) {
		delegate.setBrokerOrderSeq(brokerOrderSeq);
	}

	public String getBusinessUnit() {
		return delegate.getBusinessUnit();
	}

	public void setBusinessUnit(String businessUnit) {
		delegate.setBusinessUnit(businessUnit);
	}

	public String getClearingPartID() {
		return delegate.getClearingPartID();
	}

	public void setClearingPartID(String clearingPartID) {
		delegate.setClearingPartID(clearingPartID);
	}

	public String getClientID() {
		return delegate.getClientID();
	}

	public void setClientID(String clientID) {
		delegate.setClientID(clientID);
	}

	public String getDirection() {
		return delegate.getDirection();
	}

	public void setDirection(String direction) {
		delegate.setDirection(direction);
	}

	public String getExchangeID() {
		return delegate.getExchangeID();
	}

	public void setExchangeID(String exchangeID) {
		delegate.setExchangeID(exchangeID);
	}

	public String getExchangeInstID() {
		return delegate.getExchangeInstID();
	}

	public void setExchangeInstID(String exchangeInstID) {
		delegate.setExchangeInstID(exchangeInstID);
	}

	public String getHedgeFlag() {
		return delegate.getHedgeFlag();
	}

	public void setHedgeFlag(String hedgeFlag) {
		delegate.setHedgeFlag(hedgeFlag);
	}

	public String getInstrumentID() {
		return delegate.getInstrumentID();
	}

	public void setInstrumentID(String instrumentID) {
		delegate.setInstrumentID(instrumentID);
	}

	public String getInvestorID() {
		return delegate.getInvestorID();
	}

	public void setInvestorID(String investorID) {
		delegate.setInvestorID(investorID);
	}

	public String getOffsetFlag() {
		return delegate.getOffsetFlag();
	}

	public void setOffsetFlag(String offsetFlag) {
		delegate.setOffsetFlag(offsetFlag);
	}

	public String getOrderLocalID() {
		return delegate.getOrderLocalID();
	}

	public void setOrderLocalID(String orderLocalID) {
		delegate.setOrderLocalID(orderLocalID);
	}

	public String getOrderRef() {
		return delegate.getOrderRef();
	}

	public void setOrderRef(String orderRef) {
		delegate.setOrderRef(orderRef);
	}

	public String getOrderSysID() {
		return delegate.getOrderSysID();
	}

	public void setOrderSysID(String orderSysID) {
		delegate.setOrderSysID(orderSysID);
	}

	public String getParticipantID() {
		return delegate.getParticipantID();
	}

	public void setParticipantID(String participantID) {
		delegate.setParticipantID(participantID);
	}

	public double getPrice() {
		return delegate.getPrice();
	}

	public void setPrice(double price) {
		delegate.setPrice(price);
	}

	public String getPriceSource() {
		return delegate.getPriceSource();
	}

	public void setPriceSource(String priceSource) {
		delegate.setPriceSource(priceSource);
	}

	public int getSequenceNo() {
		return delegate.getSequenceNo();
	}

	public void setSequenceNo(int sequenceNo) {
		delegate.setSequenceNo(sequenceNo);
	}

	public int getSettlementID() {
		return delegate.getSettlementID();
	}

	public void setSettlementID(int settlementID) {
		delegate.setSettlementID(settlementID);
	}

	public String getTradeDate() {
		return delegate.getTradeDate();
	}

	public void setTradeDate(String tradeDate) {
		delegate.setTradeDate(tradeDate);
	}

	public String getTradeID() {
		return delegate.getTradeID();
	}

	public void setTradeID(String tradeID) {
		delegate.setTradeID(tradeID);
	}

	public String getTraderID() {
		return delegate.getTraderID();
	}

	public void setTraderID(String traderID) {
		delegate.setTraderID(traderID);
	}

	public String getTradeSource() {
		return delegate.getTradeSource();
	}

	public void setTradeSource(String tradeSource) {
		delegate.setTradeSource(tradeSource);
	}

	public String getTradeTime() {
		return delegate.getTradeTime();
	}

	public void setTradeTime(String tradeTime) {
		delegate.setTradeTime(tradeTime);
	}

	public String getTradeType() {
		return delegate.getTradeType();
	}

	public void setTradeType(String tradeType) {
		delegate.setTradeType(tradeType);
	}

	public String getTradingDay() {
		return delegate.getTradingDay();
	}

	public void setTradingDay(String tradingDay) {
		delegate.setTradingDay(tradingDay);
	}

	public String getTradingRole() {
		return delegate.getTradingRole();
	}

	public void setTradingRole(String tradingRole) {
		delegate.setTradingRole(tradingRole);
	}

	public String getUserID() {
		return delegate.getUserID();
	}

	public void setUserID(String userID) {
		delegate.setUserID(userID);
	}

	public int getVolume() {
		return delegate.getVolume();
	}

	public void setVolume(int volume) {
		delegate.setVolume(volume);
	}
	
	public MatlabOnRtnTradeEvent(Object source, TradeDataResponse delegate){
		super(source);
		this.delegate = delegate;
	}
	
	public MatlabOnRtnTradeEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
}
