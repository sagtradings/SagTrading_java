package tradeevaluators;


public class AtLimitEvaluator implements IEvaluateTrade {

	@Override
	public boolean evaluate(double signalPrice, double marketPrice, String direction) {
		// Buy 0 Sell 1
		
		// TODO Auto-generated method stub
		if("0".equals(direction) && marketPrice <= signalPrice){
			return true;
		}
		
		else if("1".equals(direction) && marketPrice >= signalPrice){
			return true;
		}
		return false;
	}

}
