package bardatamanager;

import java.util.ArrayList;
import java.util.List;

import bo.BarData;
import bo.CompositeBarData;

public class BarDataComposer {
	public List<BarData> composeList(List<BarData> barDataList, long masterBarTime) throws TimeIntervalNotDivisibleByTenException{
		List<BarData> answer = new ArrayList<BarData>(10);
		if(masterBarTime % 10000 != 0){
			throw new TimeIntervalNotDivisibleByTenException("Time interval is not divisible by 10");
		}
		int barChunks = (int) (masterBarTime / 10000);
		
		for(int i = barDataList.size() - 1; i >= 0; i -= barChunks){
			CompositeBarData composition = new CompositeBarData();
			for(int k = i - barChunks; k <= i; k++){
				composition.addBarData(barDataList.get(k));
			}
			answer.add(composition);
		}
		return answer;
	}
}