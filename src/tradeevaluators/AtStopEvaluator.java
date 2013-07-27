package tradeevaluators;


public class AtStopEvaluator implements IEvaluateTrade {

	@Override
	public boolean evaluate(double signalPrice, double marketPrice, String direction) {
		if("0".equals(direction) && marketPrice >= signalPrice){
			return true;
		}
		
		else if("1".equals(direction) && marketPrice <= signalPrice){
			return true;
		}
		return false;
	}

}
