package matlab;

import bo.ErrorResult;

public class MatlabOnRtnErrorEvent extends java.util.EventObject{

	
	private ErrorResult delegate;
	
	public MatlabOnRtnErrorEvent(Object source, ErrorResult delegate){
		super(source);
		this.delegate = delegate;
	}
	
	public int getErrorId() {
		return delegate.getErrorId();
	}

	public void setErrorId(int errorId) {
		delegate.setErrorId(errorId);
	}

	public String getErrorMessage() {
		return delegate.getErrorMessage();
	}

	public void setErrorMessage(String errorMessage) {
		delegate.setErrorMessage(errorMessage);
	}

	public MatlabOnRtnErrorEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
