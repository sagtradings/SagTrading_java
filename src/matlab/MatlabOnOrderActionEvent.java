package matlab;

import java.util.EventObject;

import bo.OrderActionRequest;

public class MatlabOnOrderActionEvent extends EventObject{
	private OrderActionRequest delegate;

	
	public MatlabOnOrderActionEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public MatlabOnOrderActionEvent(Object source, OrderActionRequest delegate){
		super(source);
		this.delegate = delegate;
	}
	
	public long getCreationTimeStamp() {
		return delegate.getCreationTimeStamp();
	}

	public String getBrokerID() {
		return delegate.getBrokerID();
	}

	public String getInvestorID() {
		return delegate.getInvestorID();
	}

	public int getOrderActionRef() {
		return delegate.getOrderActionRef();
	}

	public String getOrderRef() {
		return delegate.getOrderRef();
	}

	public int getRequestID() {
		return delegate.getRequestID();
	}

	public int getFrontID() {
		return delegate.getFrontID();
	}

	public int getSessionID() {
		return delegate.getSessionID();
	}

	public String getExchangeID() {
		return delegate.getExchangeID();
	}

	public String getOrderSysID() {
		return delegate.getOrderSysID();
		
	}

	public String getActionFlag() {
		return delegate.getActionFlag();
	}

	public double getLimitPrice() {
		return delegate.getLimitPrice();
	}

	public int getVolumeChange() {
		return delegate.getVolumeChange();
	}

	public String getUserID() {
		return delegate.getUserID();
	}

	public String getInstrumentID() {
		return delegate.getInstrumentID();
	}




}
