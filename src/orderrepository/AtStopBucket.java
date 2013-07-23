package orderrepository;

public class AtStopBucket extends OrderBucket {
	private double signalPrice;

	public double getSignalPrice() {
		return signalPrice;
	}

	public void setSignalPrice(double signalPrice) {
		this.signalPrice = signalPrice;
	}
}
