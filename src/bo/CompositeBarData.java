package bo;

import java.util.ArrayList;
import java.util.List;


public class CompositeBarData extends BarData {
	private List<BarData> innerBarData = new ArrayList<BarData>(10);
	public void addBarData(BarData barData){
		innerBarData.add(barData);
	}
	@Override
	public double getHigh() {
		double highest = Double.MIN_VALUE;
		for(int i = 0, n = innerBarData.size(); i < n; i++){
			if(highest < innerBarData.get(i).getHigh()){
				highest = innerBarData.get(i).getHigh();
			}
		}
		return highest;
	}
	@Override
	public double getLow() {
		double lowest = Double.MAX_VALUE;
		for(int i = 0, n = innerBarData.size(); i < n; i++){
			if(lowest > innerBarData.get(i).getLow()){
				lowest = innerBarData.get(i).getLow();
			}
		}
		return lowest;
	}
	@Override
	public double getOpen() {
		return innerBarData.get(0).getOpen();
	}
	@Override
	public double getClose() {
		return innerBarData.get(innerBarData.size() - 1).getClose();
	}
}
