package orderrefgenerator;

public class MessageIDGenerator {
	private int currentValue = 0;
	private static MessageIDGenerator instance;
	
	public static MessageIDGenerator getInstance(){
		if(instance == null){
			instance = new MessageIDGenerator();
		}
		return instance;
	}
	
	private MessageIDGenerator(){}
	public int getNextID(){
		return currentValue++;
	}
	
	
}
