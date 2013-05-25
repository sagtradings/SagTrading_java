package bo;

public class MarketDataResponse {
	
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
	
	public long getTimeOfEvent() {
		return timeOfEvent;
	}

	public void setTimeOfEvent(long timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}

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

	public double getUpVolume() {
		return upVolume;
	}

	public void setUpVolume(double upVolume) {
		this.upVolume = upVolume;
	}

	public double getDownVolume() {
		return downVolume;
	}

	public void setDownVolume(double downVolume) {
		this.downVolume = downVolume;
	}

	public MarketDataResponse(){
		this.timeOfEvent = System.currentTimeMillis();
	}
	
	public String getActionDay() {
		return actionDay;
	}

	public void setActionDay(String actionDay) {
		this.actionDay = actionDay;
	}

	public double getAskPrice1() {
		return askPrice1;
	}

	public void setAskPrice1(double askPrice1) {
		this.askPrice1 = askPrice1;
	}

	public double getAskPrice2() {
		return askPrice2;
	}

	public void setAskPrice2(double askPrice2) {
		this.askPrice2 = askPrice2;
	}

	public double getAskPrice3() {
		return askPrice3;
	}

	public void setAskPrice3(double askPrice3) {
		this.askPrice3 = askPrice3;
	}

	public double getAskPrice4() {
		return askPrice4;
	}

	public void setAskPrice4(double askPrice4) {
		this.askPrice4 = askPrice4;
	}

	public double getAskPrice5() {
		return askPrice5;
	}

	public void setAskPrice5(double askPrice5) {
		this.askPrice5 = askPrice5;
	}

	public int getAskVolume1() {
		return askVolume1;
	}

	public void setAskVolume1(int askVolume1) {
		this.askVolume1 = askVolume1;
	}

	public int getAskVolume2() {
		return askVolume2;
	}

	public void setAskVolume2(int askVolume2) {
		this.askVolume2 = askVolume2;
	}

	public int getAskVolume3() {
		return askVolume3;
	}

	public void setAskVolume3(int askVolume3) {
		this.askVolume3 = askVolume3;
	}

	public int getAskVolume4() {
		return askVolume4;
	}

	public void setAskVolume4(int askVolume4) {
		this.askVolume4 = askVolume4;
	}

	public int getAskVolume5() {
		return askVolume5;
	}

	public void setAskVolume5(int askVolume5) {
		this.askVolume5 = askVolume5;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public double getBidPrice1() {
		return bidPrice1;
	}

	public void setBidPrice1(double bidPrice1) {
		this.bidPrice1 = bidPrice1;
	}

	public double getBidPrice2() {
		return bidPrice2;
	}

	public void setBidPrice2(double bidPrice2) {
		this.bidPrice2 = bidPrice2;
	}

	public double getBidPrice3() {
		return bidPrice3;
	}

	public void setBidPrice3(double bidPrice3) {
		this.bidPrice3 = bidPrice3;
	}

	public double getBidPrice4() {
		return bidPrice4;
	}

	public void setBidPrice4(double bidPrice4) {
		this.bidPrice4 = bidPrice4;
	}

	public double getBidPrice5() {
		return bidPrice5;
	}

	public void setBidPrice5(double bidPrice5) {
		this.bidPrice5 = bidPrice5;
	}

	public int getBidVolume1() {
		return bidVolume1;
	}

	public void setBidVolume1(int bidVolume1) {
		this.bidVolume1 = bidVolume1;
	}

	public int getBidVolume2() {
		return bidVolume2;
	}

	public void setBidVolume2(int bidVolume2) {
		this.bidVolume2 = bidVolume2;
	}

	public int getBidVolume3() {
		return bidVolume3;
	}

	public void setBidVolume3(int bidVolume3) {
		this.bidVolume3 = bidVolume3;
	}

	public int getBidVolume4() {
		return bidVolume4;
	}

	public void setBidVolume4(int bidVolume4) {
		this.bidVolume4 = bidVolume4;
	}

	public int getBidVolume5() {
		return bidVolume5;
	}

	public void setBidVolume5(int bidVolume5) {
		this.bidVolume5 = bidVolume5;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getCurrDelta() {
		return currDelta;
	}

	public void setCurrDelta(double currDelta) {
		this.currDelta = currDelta;
	}

	public String getExchangeID() {
		return exchangeID;
	}

	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	public String getExchangeInstId() {
		return exchangeInstId;
	}

	public void setExchangeInstId(String exchangeInstId) {
		this.exchangeInstId = exchangeInstId;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getLowerLimitPrice() {
		return lowerLimitPrice;
	}

	public void setLowerLimitPrice(double lowerLimitPrice) {
		this.lowerLimitPrice = lowerLimitPrice;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public double getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(double openInterest) {
		this.openInterest = openInterest;
	}

	public double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}

	public double getPreClosePrice() {
		return preClosePrice;
	}

	public void setPreClosePrice(double preClosePrice) {
		this.preClosePrice = preClosePrice;
	}

	public double getPreDelta() {
		return preDelta;
	}

	public void setPreDelta(double preDelta) {
		this.preDelta = preDelta;
	}

	public double getPreOpenInterest() {
		return preOpenInterest;
	}

	public void setPreOpenInterest(double preOpenInterest) {
		this.preOpenInterest = preOpenInterest;
	}

	public double getPreSettlementPrice() {
		return preSettlementPrice;
	}

	public void setPreSettlementPrice(double preSettlementPrice) {
		this.preSettlementPrice = preSettlementPrice;
	}

	public double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public String getTradingDay() {
		return tradingDay;
	}

	public void setTradingDay(String tradingDay) {
		this.tradingDay = tradingDay;
	}

	public double getTurnOver() {
		return turnOver;
	}

	public void setTurnOver(double turnOver) {
		this.turnOver = turnOver;
	}

	public int getUpdateMillisec() {
		return updateMillisec;
	}

	public void setUpdateMillisec(int updateMillisec) {
		this.updateMillisec = updateMillisec;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public double getUpperLimitPrice() {
		return upperLimitPrice;
	}

	public void setUpperLimitPrice(double upperLimitPrice) {
		this.upperLimitPrice = upperLimitPrice;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	
}
