package matlab;

import bo.BarData;

public class BarDataEvent extends java.util.EventObject {
    private static final long serialVersionUID = 1L;

    
    public double closePrice,openPrice, downVolume, highPrice, lowPrice, upVolume;
    public String instrumentId, timeStamp;
    public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public BarDataEvent(Object obj, BarData barData) {
        super(obj);
        this.closePrice = barData.getClose();
        this.openPrice = barData.getOpen();
        this.downVolume = barData.getDownVolume();
        this.highPrice = barData.getHigh();
        this.lowPrice = barData.getLow();
        this.upVolume = barData.getUpVolume();
        this.instrumentId = barData.getInstrumentId();
        this.timeStamp = barData.getTimestamp();
        

    }
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getDownVolume() {
		return downVolume;
	}
	public void setDownVolume(double downVolume) {
		this.downVolume = downVolume;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getUpVolume() {
		return upVolume;
	}
	public void setUpVolume(double upVolume) {
		this.upVolume = upVolume;
	}

    
}