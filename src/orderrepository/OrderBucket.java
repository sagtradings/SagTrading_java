package orderrepository;

import bo.TradeRequest;

public class OrderBucket {
	
	public static enum orderStates{
		WAITING,
		INITIAL_REQUEST, 
		EXIT_REQUEST, 
		CYCLE_COMPLETED,
		STOP_LOSS_COMPLETED,
		ORDER_TIMEOUT,
		CANCELLED_BY_TRADER}
	
	private TradeRequest initialRequest;
	private TradeRequest exitRequest;
	private TradeRequest stopLossRequest;
	private orderStates orderState;
	private long timeOut;
	private long creationTime;
	private int orderDirection;
	
	public int getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(int orderDirection) {
		this.orderDirection = orderDirection;
	}

	public OrderBucket(){
		this.creationTime = System.currentTimeMillis();
	}
	
	public long getCreationTime() {
		return creationTime;
	}

	public long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	public TradeRequest getInitialRequest() {
		return initialRequest;
	}
	public void setInitialRequest(TradeRequest initialRequest) {
		this.initialRequest = initialRequest;
	}
	public TradeRequest getExitRequest() {
		return exitRequest;
	}
	public void setExitRequest(TradeRequest exitRequest) {
		this.exitRequest = exitRequest;
	}
	public TradeRequest getStopLossRequest() {
		return stopLossRequest;
	}
	public void setStopLossRequest(TradeRequest stopLossRequest) {
		this.stopLossRequest = stopLossRequest;
	}
	public orderStates getOrderState() {
		return orderState;
	}
	public void setOrderState(orderStates orderState) {
		this.orderState = orderState;
	}
	
}
