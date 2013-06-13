package matlab;

import java.util.EventObject;

import bo.LoginResponse;

public class MatLabOnLoginEvent extends EventObject {
	private LoginResponse delegate;
	private long creationTimeStamp;
	private int maxOrder;
	public int getMaxOrder() {
		return delegate.getMaxOrder();
	}


	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public MatLabOnLoginEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public MatLabOnLoginEvent(Object source, LoginResponse delegate){
		super(source);
		this.delegate = delegate;
		this.creationTimeStamp = System.currentTimeMillis();
		
	}

}
