package matlab;

import java.util.EventObject;

import bo.LoginResponse;

public class MatlabOnLoginEvent extends EventObject {
	private LoginResponse delegate;
	private long creationTimeStamp;
	public int getMaxOrder() {
		return delegate.getMaxOrder();
	}


	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public MatlabOnLoginEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public MatlabOnLoginEvent(Object source, LoginResponse delegate){
		super(source);
		this.delegate = delegate;
		this.creationTimeStamp = System.currentTimeMillis();
		
	}

}
