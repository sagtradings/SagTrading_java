package orderpositionregistry;

import bo.TradeRequest;

public class ReverseToBuyCalculator implements IVolumeCalculator {

	@Override
	public double computeVolume(TradeRequest request, double currentPosition) {
		if(currentPosition == 0){
			return request.getVolumeTotalOriginal();
		}
		if(currentPosition == 0){
			return 0;
		}
		if(currentPosition > 0){
			return request.getVolumeTotalOriginal() + currentPosition;
		}
		return 0;
	}

}
