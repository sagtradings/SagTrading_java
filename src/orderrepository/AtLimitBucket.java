package orderrepository;

public class AtLimitBucket extends OrderBucket implements ISignablePrice{
	private double signalPrice;

	public double getSignalPrice() {
		return signalPrice;
	}

	public void setSignalPrice(double signalPrice) {
		this.signalPrice = signalPrice;
	}
}
