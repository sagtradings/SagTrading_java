package orderpositionregistry;

import java.util.HashMap;
import java.util.Map;

import bo.TradeRequest;

public class LocalInstrumentPositionRegistry {

	private Map<String, Double> positions = new HashMap<String, Double>(10);
	private Map<Integer, IVolumeCalculator> volumeCalculators = new HashMap<Integer, IVolumeCalculator>(10);
	
	public LocalInstrumentPositionRegistry(){
		volumeCalculators.put(0, new BuyCalculator());
		volumeCalculators.put(1, new ExitShortCalculator());
		volumeCalculators.put(2, new ReverseToBuyCalculator());
		volumeCalculators.put(3, new ExitLongCalculator());
	}
	
	public double getPosition(String instrument){
		Double answer = positions.get(instrument);
		if(answer == null){
			return 0;
		}
		return answer;
	}
	
	public void putPosition(String instrument, Double position){
		positions.put(instrument, position);
	}
	
	public IVolumeCalculator getCalculator(int orderDirection){
		IVolumeCalculator answer = volumeCalculators.get(orderDirection);
		if(answer == null){
			return new IVolumeCalculator(){

				@Override
				public double computeVolume(TradeRequest request,
						double currentPosition) {
					// TODO Auto-generated method stub
					return 0;
				}
				
			};
		}
		return answer;
	}
}
