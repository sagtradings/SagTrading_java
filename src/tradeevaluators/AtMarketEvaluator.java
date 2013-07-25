package tradeevaluators;

public class AtMarketEvaluator implements IEvaluateTrade {

	@Override
	public boolean evaluate(double signalPrice, double marketPrice, String direction) {
		return true;
	}

}
