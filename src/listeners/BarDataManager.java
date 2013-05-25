package listeners;

import javax.swing.JOptionPane;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class BarDataManager implements UpdateListener {

	private long startTime = System.currentTimeMillis();
	public void update(EventBean[] arg0, EventBean[] arg1) {
		//JOptionPane.showMessageDialog(null, arg0.length);
		System.out.println("elapsed time: " + (System.currentTimeMillis() - startTime)); 
		for(int k = 0, n = arg0.length; k < n; k++){
			System.out.println(" arg0["+k+"]instrumentId: " +arg0[k].get("instrumentId") +" minPrice: " + arg0[k].get("minPrice") + " maxPrice: " + arg0[k].get("maxPrice") + " closePrice: " + arg0[k].get("closePrice") + "openPrice: " + arg0[k].get("openPrice") + " upVolume: " + arg0[k].get("upVolume") + " downVolume: " + arg0[k].get("downVolume"));
		}
		startTime = System.currentTimeMillis();

	}

}
