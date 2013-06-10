package matlab;

import bo.MarketDataResponse;

public class MatLabTickEvent extends java.util.EventObject{
	
	
	public MatLabTickEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public MatLabTickEvent(Object source, MarketDataResponse delegate){
		super(source);
		this.delegate = delegate;
	}
	private MarketDataResponse delegate;
	
	private String actionDay;
	private double askPrice1;
	private double askPrice2;
	private double askPrice3;
	private double askPrice4;
	private double askPrice5;
	
	private int askVolume1;
	private int askVolume2;
	private int askVolume3;
	private int askVolume4;
	private int askVolume5;
	
	private double averagePrice;
	
	private double bidPrice1;
	private double bidPrice2;
	private double bidPrice3;
	private double bidPrice4;
	private double bidPrice5;
	
	private int bidVolume1;
	private int bidVolume2;
	private int bidVolume3;
	private int bidVolume4;
	private int bidVolume5;

	private double closePrice;

	private double currDelta;
	private String exchangeID;
	
	private String exchangeInstId;
	private double highestPrice;
	private String instrumentId;
	
	private double lastPrice;
	private double lowerLimitPrice;
	private double lowestPrice;
	private double openInterest;
	private double openPrice;
	private double preClosePrice;
	private double preDelta;
	private double preOpenInterest;
	private double preSettlementPrice;
	private double settlementPrice;
	private String tradingDay;
	private double turnOver;
	private int updateMillisec;
	private String updateTime;
	private double upperLimitPrice;
	
	private long timeOfEvent;
	
	private int volume;
	
	private double upVolume;
	private double downVolume;

	public String getActionDay() {
		return delegate.getActionDay();
	}
	public void setActionDay(String actionDay) {
		delegate.setActionDay(actionDay);
	}
	public double getAskPrice1() {
		return delegate.getAskPrice1();
	}
	public void setAskPrice1(double askPrice1) {
		delegate.setAskPrice1(askPrice1);
	}
	public double getAskPrice2() {
		return delegate.getAskPrice2();
	}
	public void setAskPrice2(double askPrice2) {
		delegate.setAskPrice2(askPrice2);
	}
	public double getAskPrice3() {
		return delegate.getAskPrice3();
	}
	public void setAskPrice3(double askPrice3) {
		delegate.setAskPrice3(askPrice3);
	}
	public double getAskPrice4() {
		return delegate.getAskPrice4();
	}
	public void setAskPrice4(double askPrice4) {
		delegate.setAskPrice4(askPrice4);
	}
	public double getAskPrice5() {
		return delegate.getAskPrice5();
	}
	public void setAskPrice5(double askPrice5) {
		delegate.setAskPrice5(askPrice5);
	}
	public int getAskVolume1() {
		return delegate.getAskVolume1();
	}
	public void setAskVolume1(int askVolume1) {
		delegate.setAskVolume1(askVolume1);
	}
	public int getAskVolume2() {
		return delegate.getAskVolume2();
	}
	public void setAskVolume2(int askVolume2) {
		delegate.setAskVolume2(askVolume2);
	}
	public int getAskVolume3() {
		return delegate.getAskVolume3();
	}
	public void setAskVolume3(int askVolume3) {
		delegate.setAskVolume3(askVolume3);
	}
	public int getAskVolume4() {
		return delegate.getAskVolume4();
	}
	public void setAskVolume4(int askVolume4) {
		delegate.setAskVolume4(askVolume4);
	}
	public int getAskVolume5() {
		return delegate.getAskVolume5();
	}
	public void setAskVolume5(int askVolume5) {
		delegate.setAskVolume5(askVolume5);
	}
	public double getAveragePrice() {
		return delegate.getAveragePrice();
	}
	public void setAveragePrice(double averagePrice) {
		delegate.setAveragePrice(averagePrice);
	}
	public double getBidPrice1() {
		return delegate.getBidPrice1();
	}
	public void setBidPrice1(double bidPrice1) {
		delegate.setBidPrice1(bidPrice1);
	}
	public double getBidPrice2() {
		return delegate.getBidPrice2();
	}
	public void setBidPrice2(double bidPrice2) {
		delegate.setBidPrice2(bidPrice2);
	}
	public double getBidPrice3() {
		return delegate.getBidPrice3();
	}
	public void setBidPrice3(double bidPrice3) {
		delegate.setBidPrice3(bidPrice3);
	}
	public double getBidPrice4() {
		return delegate.getBidPrice4();
	}
	public void setBidPrice4(double bidPrice4) {
		delegate.setBidPrice4(bidPrice4);
	}
	public double getBidPrice5() {
		return delegate.getBidPrice5();
	}
	public void setBidPrice5(double bidPrice5) {
		delegate.setBidPrice5(bidPrice5);
	}
	public int getBidVolume1() {
		return delegate.getBidVolume1();
	}
	public void setBidVolume1(int bidVolume1) {
		delegate.setBidVolume1(bidVolume1);
	}
	public int getBidVolume2() {
		return delegate.getBidVolume2();
	}
	public void setBidVolume2(int bidVolume2) {
		delegate.setBidVolume2(bidVolume2);
	}
	public int getBidVolume3() {
		return delegate.getBidVolume3();
	}
	public void setBidVolume3(int bidVolume3) {
		delegate.setBidVolume3(bidVolume3);
	}
	public int getBidVolume4() {
		return delegate.getBidVolume4();
	}
	public void setBidVolume4(int bidVolume4) {
		delegate.setBidVolume4(bidVolume4);
	}
	public int getBidVolume5() {
		return delegate.getBidVolume5();
	}
	public void setBidVolume5(int bidVolume5) {
		delegate.setBidVolume5(bidVolume5);
	}
	public double getClosePrice() {
		return delegate.getClosePrice();
	}
	public void setClosePrice(double closePrice) {
		delegate.setClosePrice(closePrice);
	}
	public double getCurrDelta() {
		return delegate.getCurrDelta();
	}
	public void setCurrDelta(double currDelta) {
		delegate.setCurrDelta(currDelta);
	}
	public String getExchangeID() {
		return delegate.getExchangeID();
	}
	public void setExchangeID(String exchangeID) {
		delegate.setExchangeID(exchangeID);
	}
	public String getExchangeInstId() {
		return delegate.getExchangeInstId();
	}
	public void setExchangeInstId(String exchangeInstId) {
		delegate.setExchangeInstId(exchangeInstId);
	}
	public double getHighestPrice() {
		return delegate.getHighestPrice();
	}
	public void setHighestPrice(double highestPrice) {
		delegate.setHighestPrice(highestPrice);
	}
	public String getInstrumentId() {
		return delegate.getInstrumentId();
	}
	public void setInstrumentId(String instrumentId) {
		delegate.setInstrumentId(instrumentId);
	}
	public double getLastPrice() {
		return delegate.getLastPrice();
	}
	public void setLastPrice(double lastPrice) {
		delegate.setLastPrice(lastPrice);
	}
	public double getLowerLimitPrice() {
		return delegate.getLowerLimitPrice();
	}
	public void setLowerLimitPrice(double lowerLimitPrice) {
		delegate.setLowerLimitPrice(lowerLimitPrice);
	}
	public double getLowestPrice() {
		return delegate.getLowestPrice();
	}
	public void setLowestPrice(double lowestPrice) {
		delegate.setLowestPrice(lowestPrice);
	}
	public double getOpenInterest() {
		return delegate.getOpenInterest();
	}
	public void setOpenInterest(double openInterest) {
		delegate.setOpenInterest(openInterest);
	}
	public double getOpenPrice() {
		return delegate.getOpenPrice();
	}
	public void setOpenPrice(double openPrice) {
		delegate.setOpenPrice(openPrice);
	}
	public double getPreClosePrice() {
		return delegate.getPreClosePrice();
	}
	public void setPreClosePrice(double preClosePrice) {
		delegate.setPreClosePrice(preClosePrice);
	}
	public double getPreDelta() {
		return delegate.getPreDelta();
	}
	public void setPreDelta(double preDelta) {
		delegate.setPreDelta(preDelta);
	}
	public double getPreOpenInterest() {
		return delegate.getPreOpenInterest();
	}
	public void setPreOpenInterest(double preOpenInterest) {
		delegate.setPreOpenInterest(preOpenInterest);
	}
	public double getPreSettlementPrice() {
		return delegate.getPreSettlementPrice();
	}
	public void setPreSettlementPrice(double preSettlementPrice) {
		delegate.setPreSettlementPrice(preSettlementPrice);
	}
	public double getSettlementPrice() {
		return delegate.getSettlementPrice();
	}
	public void setSettlementPrice(double settlementPrice) {
		delegate.setSettlementPrice(settlementPrice);
	}
	public String getTradingDay() {
		return delegate.getTradingDay();
	}
	public void setTradingDay(String tradingDay) {
		delegate.setTradingDay(tradingDay);
	}
	public double getTurnOver() {
		return delegate.getTurnOver();
	}
	public void setTurnOver(double turnOver) {
		delegate.setTurnOver(turnOver);
	}
	public int getUpdateMillisec() {
		return delegate.getUpdateMillisec();
	}
	public void setUpdateMillisec(int updateMillisec) {
		delegate.setUpdateMillisec(updateMillisec);
	}
	public String getUpdateTime() {
		return delegate.getUpdateTime();
	}
	public void setUpdateTime(String updateTime) {
		delegate.setUpdateTime(updateTime);
	}
	public double getUpperLimitPrice() {
		return delegate.getUpperLimitPrice();
	}
	public void setUpperLimitPrice(double upperLimitPrice) {
		delegate.setUpperLimitPrice(upperLimitPrice);
	}
	public long getTimeOfEvent() {
		return delegate.getTimeOfEvent();
	}
	public void setTimeOfEvent(long timeOfEvent) {
		delegate.setTimeOfEvent(timeOfEvent);
	}
	public int getVolume() {
		return delegate.getVolume();
	}
	public void setVolume(int volume) {
		delegate.setVolume(volume);
	}
	public double getUpVolume() {
		return delegate.getUpVolume();
	}
	public void setUpVolume(double upVolume) {
		delegate.setUpVolume(upVolume);
	}
	public double getDownVolume() {
		return delegate.getDownVolume();
	}
	public void setDownVolume(double downVolume) {
		delegate.setDownVolume(downVolume);
	}
	
}
