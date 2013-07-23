package orderrepository;

public class AtMarketOrderBucket extends OrderBucket {
	private double signalPrice;

	public double getSignalPrice() {
		return signalPrice;
	}

	public void setSignalPrice(double signalPrice) {
		this.signalPrice = signalPrice;
	}
}
