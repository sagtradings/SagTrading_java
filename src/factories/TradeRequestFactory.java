package factories;

import bo.TradeRequest;

public class TradeRequestFactory {
	public TradeRequest createRequest(String instrument, double price, int orderSide){
		TradeRequest answer = new TradeRequest();
		answer.setDirection(orderSide == 0 || orderSide == 1 ? "0" : "1");
		answer.setOrderPriceType("2");
		answer.setCombOffsetFlag("0");
		answer.setCombHedgeFlag("1");
		answer.setLimitPrice(price);
		answer.setGtdDate("");
		answer.setVolumeCondition("1");
		answer.setMinVolume(1);
		answer.setContingentCondition("1");
		answer.setStopPrice(0);
		answer.setForceCloseReason("0");
		answer.setAutoSuspend(0);
		answer.setVolumeTotalOriginal(1);
		answer.setTimeCondition("3");
		answer.setInstrumentID(instrument);

		return answer;
	}
}
