package main;

import bardatamanager.TimeIntervalNotDivisibleByTenException;
import ctpapiinterface.EventMatlabIntegrator;



	public class ThreadClass implements Runnable{

		@Override
		public void run() {
			EventMatlabIntegrator integrator = new EventMatlabIntegrator();
			integrator.requestLogin("1013", "123321", "00000008");
			integrator.subscribeMarketData("IF1309");
			try {
				integrator.subscribeBarData("IF1309", 20000);
				
			} catch (TimeIntervalNotDivisibleByTenException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(true){
				
				try {
					System.out.println(integrator.getLowest("open", 4));
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}