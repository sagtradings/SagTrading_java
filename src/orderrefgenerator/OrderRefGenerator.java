package orderrefgenerator;

public class OrderRefGenerator {
	private int currentRef = 0;
	private static OrderRefGenerator instance;
	private static final int REF_LENGTH = 11;
	
	public static OrderRefGenerator getInstance(){
		if(instance == null){
			instance = new OrderRefGenerator();
		}
		return instance;
	}
	
	public void setInitialValue(int currentRef){
		this.currentRef = currentRef;
	}
	
	private OrderRefGenerator(){};
	
	public synchronized String getNextRef(){
		String convertedRef = Integer.toString(++currentRef);
		StringBuilder answer = new StringBuilder();
		int prePendBuff = REF_LENGTH - convertedRef.length();
		for(int i = 0, n = prePendBuff; i < n; i++){
			answer.append('0');
		}
		answer.append(convertedRef);
		return answer.toString();
	}
	

}
