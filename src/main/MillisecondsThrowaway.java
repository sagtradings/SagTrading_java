package main;

import java.util.Calendar;

public class MillisecondsThrowaway {
	public static void main(String[] args){
		
		long startTime = System.currentTimeMillis();
		startTime = (startTime + (long)(startTime % 86400000L));
		startTime = startTime - (System.currentTimeMillis() % (24L * 60*60*1000));
		long yesterday = startTime - 86400000;
		
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTime().toString());
		//c.setTimeInMillis(startTime);
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		System.out.println(c.getTime().toString());
		for(int i = 0, n = 10000000; i < n; i++){
			int d = (int)((System.currentTimeMillis() - c.getTimeInMillis()) / 1000.0);
			System.out.println(d);
		}
		System.out.println("total time: " + (System.currentTimeMillis() - startTime));
	}
}
