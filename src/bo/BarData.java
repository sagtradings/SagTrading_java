package bo;

public class BarData {
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

    private double high;
    private double low;
    private double open;
    private double close;
    private double upVolume;

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

    private double downVolume;
}
