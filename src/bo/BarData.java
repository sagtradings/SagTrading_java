package bo;

import java.util.Date;

/**
 * BarData domain
 */
public class BarData {
    private int bardata_id;
    private double high;
    private double low;
    private double open;
    private double close;
    private double upVolume;
    private String instrumentId;
    private String timestamp;
    private long creationTimeStamp;
    private double downVolume;
    private Date day;

    public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public BarData() {
        this.creationTimeStamp = System.currentTimeMillis();
    }

    public int getBardata_id() {
        return bardata_id;
    }

    public void setBardata_id(int bardata_id) {
        this.bardata_id = bardata_id;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public long getCreationTimeStamp() {
        return creationTimeStamp;
    }

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

    @Override
    public String toString() {
        return "BarData{" +
                "bardata_id=" + bardata_id +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                ", upVolume=" + upVolume +
                ", instrumentId='" + instrumentId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", creationTimeStamp=" + creationTimeStamp +
                ", downVolume=" + downVolume +
                '}';
    }
}
