package matlab;

import java.util.EventObject;

public class MatlabOnOrderCycleCompleteEvent extends EventObject{
	public MatlabOnOrderCycleCompleteEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	private String orderRef;
	// 0 = exitOrder, 1 = stopLossOrder, 2 = CANCELED
	private int completionType;
	public String getOrderRef() {
		return orderRef;
	}
	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}
	public int getCompletionType() {
		return completionType;
	}
	public void setCompletionType(int completionType) {
		this.completionType = completionType;
	}

}
