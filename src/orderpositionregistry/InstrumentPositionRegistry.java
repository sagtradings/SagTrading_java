package orderpositionregistry;

import java.util.HashMap;
import java.util.Map;

public class InstrumentPositionRegistry {
	private static InstrumentPositionRegistry instance;
	private Map<String, Double> positions = new HashMap<String, Double>(10);
	public static synchronized InstrumentPositionRegistry getInstance(){
		if(instance == null){
			instance = new InstrumentPositionRegistry();
		}
		return instance;
	}
	
	private InstrumentPositionRegistry(){
		
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
