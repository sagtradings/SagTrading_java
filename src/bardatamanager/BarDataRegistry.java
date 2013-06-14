package bardatamanager;

import java.util.Hashtable;
import java.util.Map;

public class BarDataRegistry {

	
	private Map<String, BarDataEntry> barDataEntries = new Hashtable<String, BarDataEntry>(10);
	
	
	
	public BarDataRegistry(){
		
	}
	
	public void addEntry(String key, BarDataEntry value){
		barDataEntries.put(key, value);
	}
	
	public BarDataEntry getEntry(String key){
		return barDataEntries.get(key);
	}
	
	public BarDataEntry removeEntry(String key){
		BarDataEntry answer = barDataEntries.get(key);
		barDataEntries.remove(key);
		return answer;
	}
}
