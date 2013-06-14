package listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class BarDataManager implements UpdateListener {

	private long startTime = System.currentTimeMillis();
	Calendar startCal = Calendar.getInstance();
	Calendar endCal = Calendar.getInstance();
	public void update(EventBean[] arg0, EventBean[] arg1) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("elapsed time: " + elapsedTime); 
		for(int k = 0, n = arg0.length; k < n; k++){
			System.out.println(" arg0["+k+"]instrumentId: " +arg0[k].get("instrumentId") +" minPrice: " + arg0[k].get("minPrice") + " maxPrice: " + arg0[k].get("maxPrice") + " closePrice: " + arg0[k].get("closePrice") + "openPrice: " + arg0[k].get("openPrice") + " upVolume: " + arg0[k].get("upVolume") + " downVolume: " + arg0[k].get("downVolume"));
		}
		
		startCal.setTimeInMillis(startTime);
		endCal.setTimeInMillis(startTime + elapsedTime);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd kk:mm:s.S");
		System.out.println("startTime: " + (formatter.format(startCal.getTime())));
		System.out.println("endTime: " + (formatter.format(endCal.getTime())));
		startTime = System.currentTimeMillis();

	}

}
