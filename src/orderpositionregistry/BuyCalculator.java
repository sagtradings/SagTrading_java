package orderpositionregistry;

import bo.TradeRequest;

public class BuyCalculator implements IVolumeCalculator {

	@Override
	public double computeVolume(TradeRequest request, double currentPosition) {
		if(currentPosition == 0){
			return request.getVolumeTotalOriginal();
		}
		if(currentPosition < 0){
			return request.getVolumeTotalOriginal() + currentPosition;
		}
		if(currentPosition > 0){
			return 0;
		}
		return 0;
		
	}

}
