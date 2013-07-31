package OrderPositionRegistry;

import java.util.HashMap;
import java.util.Map;

public class LocalInstrumentPositionRegistry {

	private Map<String, Double> positions = new HashMap<String, Double>(10);

	
	public LocalInstrumentPositionRegistry(){
		
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
}
