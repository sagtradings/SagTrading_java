package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import bo.BarData;

import dao.BarDataDAO;

public class TestDao {
	public static void main(String[] args){
		BarDataDAO selector = new BarDataDAO();
		List<BarData> barData = selector.getAllBarData();
//		for(int i = 0, n = barData.size(); i < n; i++){
//			if(barData.get(i).getDay() != null){
//				System.out.println(barData.get(i).getDay().toString());
//			
//			}
//		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
		try {
			BarData answer = selector.getHighestBarData("IF1309", formatter.parse("2013-08-07 11:29:03"), 3);
			System.out.println(answer);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
