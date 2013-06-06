package orderrepository;

import bo.TradeRequest;

public class OrderBucket {
	
	public static enum orderStates{
		INITIAL_REQUEST, 
		EXIT_REQUEST, 
		CYCLE_COMPLETED,
		STOP_LOSS_COMPLETED}
	
	private TradeRequest initialRequest;
	private TradeRequest exitRequest;
	private TradeRequest stopLossRequest;
	private orderStates orderState;
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
