package orderpositionregistry;

import bo.TradeRequest;

public class ExitLongCalculator implements IVolumeCalculator {

	@Override
	public double computeVolume(TradeRequest request, double currentPosition) {
		if(currentPosition <= 0){
			return 0;
		}
		if(currentPosition > 0){
			return request.getVolumeTotalOriginal();
		}
		return 0;
	}

}
