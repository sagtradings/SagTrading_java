package main;

import bardatamanager.TimeIntervalNotDivisibleByTenException;
import matlab.BarDataEvent;
import matlab.MarketDataEvent;
import matlab.MatlabEventListener;
import matlab.MatlabOnLoginEvent;
import matlab.MatlabOnSubscribeDataEvent;
import ctpapiinterface.EventMatlabIntegrator;

public class LogginInTest {
	static{
		System.loadLibrary("CTPDLL");
	}
	private static class ICListener implements MatlabEventListener{

		@Override
		public void barDataEvent(BarDataEvent event) {
			System.out.println("BarData " + event.getInstrumentId() + ":");
			System.out.println("\tOpen Price: " + event.getOpenPrice() + " Close Price: " + event.getClosePrice());
			
		}

		@Override
		public void marketDataEvent(MarketDataEvent event) {
			System.out.println("The current price of " + event.getInstrumentId() + " is: " + event.getLastPrice());
			
		}

		@Override
		public void matlabOnLoginEvent(MatlabOnLoginEvent event) {
			System.out.println("successfully logged in");
			
		}

		@Override
		public void matlabOnSubscribeDataEvent(MatlabOnSubscribeDataEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private static class ICRunnable implements Runnable{
		
		@Override
		public void run() {
			EventMatlabIntegrator integrator = new EventMatlabIntegrator();
			integrator.addMatlabEventListener(new ICListener());
			integrator.initialize();
			integrator.requestLogin("1013", "123321", "00000008");
			integrator.subscribeMarketData("IF1309");
			
			try {
				integrator.subscribeBarData("IF1309", 10000);
				// infinite loop to keep gathering market data
				while(true){ 
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeIntervalNotDivisibleByTenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String args[]){
		new Thread(new ICRunnable()).start();
	}
}
