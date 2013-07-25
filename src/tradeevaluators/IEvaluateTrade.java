package tradeevaluators;

public interface IEvaluateTrade {
	public boolean evaluate(double signalPrice, double marketPrice, String direction);
}
