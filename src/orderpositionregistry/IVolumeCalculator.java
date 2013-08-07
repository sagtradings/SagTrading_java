package orderpositionregistry;

import bo.TradeRequest;

public interface IVolumeCalculator {
	public double computeVolume(TradeRequest request, double currentPosition);
}
