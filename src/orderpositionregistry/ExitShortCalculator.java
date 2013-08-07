package orderpositionregistry;

import bo.TradeRequest;

public class ExitShortCalculator implements IVolumeCalculator {

	@Override
	public double computeVolume(TradeRequest request, double currentPosition) {
		if(currentPosition >= 0){
			return 0;
		}
		if(currentPosition < 0){
			return request.getVolumeTotalOriginal();
		}
		if(request.getVolumeTotalOriginal() == 0){
			return currentPosition;
		}
		return 0;
	}

}
