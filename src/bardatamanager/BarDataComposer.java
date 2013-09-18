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
		
		for(int i = barDataList.size() - 1; i >= -(barDataList.size() % barChunks); i -= (barChunks)){
			CompositeBarData composition = new CompositeBarData();
			for(int k = i - (barChunks - 1) >= 0 ? i - (barChunks - 1) : 0; k <= i; k++){
				composition.addBarData(barDataList.get(k));
			}
			answer.add(composition);
		}
		return answer;
	}
	
	public static void main(String[] args) throws TimeIntervalNotDivisibleByTenException{
		BarDataComposer composer = new BarDataComposer();
		List<BarData> originalBarData = new ArrayList<BarData>(10);
		BarData barData1 = new BarData();
		BarData barData2 = new BarData();
		BarData barData3 = new BarData();
		barData1.setClose(1.0);
		barData1.setOpen(1.0);
		barData1.setHigh(1.0);
		barData1.setLow(1.0);
		
		barData2.setClose(2.0);
		barData2.setOpen(2.0);
		barData2.setHigh(2.0);
		barData2.setLow(2.0);
		
		barData3.setClose(3.0);
		barData3.setOpen(3.0);
		barData3.setHigh(3.0);
		barData3.setLow(3.0);
		
		
		originalBarData.add(barData1);
		
		List<BarData> composedList = composer.composeList(originalBarData, 10000);
		System.out.println(composedList.get(0).getOpen());
		System.out.println(composedList.get(0).getClose());
		System.out.println(composedList.get(0).getHigh());
		System.out.println(composedList.get(0).getLow());
		System.out.println("\n");
		
		originalBarData = new ArrayList<BarData>(10);
		originalBarData.add(barData1);
		originalBarData.add(barData2);
		
		composedList = composer.composeList(originalBarData, 10000);
		System.out.println(composedList.get(0).getOpen());
		System.out.println(composedList.get(0).getClose());
		System.out.println(composedList.get(0).getHigh());
		System.out.println(composedList.get(0).getLow());
		System.out.println("\n");
		
		originalBarData = new ArrayList<BarData>(10);
		originalBarData.add(barData2);
		originalBarData.add(barData1);
		
		composedList = composer.composeList(originalBarData, 10000);
		System.out.println(composedList.get(0).getOpen());
		System.out.println(composedList.get(0).getClose());
		System.out.println(composedList.get(0).getHigh());
		System.out.println(composedList.get(0).getLow());
		System.out.println("\n");
		
		originalBarData = new ArrayList<BarData>(10);
		originalBarData.add(barData1);
		originalBarData.add(barData2);
		originalBarData.add(barData3);
		
		composedList = composer.composeList(originalBarData, 20000);
		System.out.println(composedList.get(0).getOpen());
		System.out.println(composedList.get(0).getClose());
		System.out.println(composedList.get(0).getHigh());
		System.out.println(composedList.get(0).getLow());
		System.out.println("\n");
		
		System.out.println(composedList.get(1).getOpen());
		System.out.println(composedList.get(1).getClose());
		System.out.println(composedList.get(1).getHigh());
		System.out.println(composedList.get(1).getLow());
		System.out.println("\n");
		
		composedList = composer.composeList(originalBarData, 30000);
		System.out.println(composedList.get(0).getOpen());
		System.out.println(composedList.get(0).getClose());
		System.out.println(composedList.get(0).getHigh());
		System.out.println(composedList.get(0).getLow());
		System.out.println("\n");
	}
		
		
		
		
		
		
	
		
}