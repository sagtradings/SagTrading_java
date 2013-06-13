package matlab;

import java.util.EventObject;

import bo.SubscribeMarketDataResponse;

public class MatLabOnSubscribeDataEvent extends EventObject {
	private SubscribeMarketDataResponse delegate;
	private long creationTimeStamp;
	public MatLabOnSubscribeDataEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public MatLabOnSubscribeDataEvent(Object source, SubscribeMarketDataResponse delegate){
		super(source);
		this.delegate = delegate;
		this.creationTimeStamp = creationTimeStamp;
	}
	
	public int getErrorId(){
		return delegate.getErrorId();
	}
	
	public String getErrorMsg(){
		return delegate.getErrorMsg();
	}
	
	public String getSpecificInsturment(){
		return delegate.getSpecificInstrument();
	}

}
