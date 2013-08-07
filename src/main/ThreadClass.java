package main;
import ctpapiinterface.EventMatlabIntegrator;



	public class ThreadClass implements Runnable{

		@Override
		public void run() {
			EventMatlabIntegrator integrator = new EventMatlabIntegrator();
			integrator.requestLogin("1013", "123321", "00000008");
			integrator.subscribeMarketData("IF1309");
			integrator.subscribeBarData("IF1309", 10000);
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}