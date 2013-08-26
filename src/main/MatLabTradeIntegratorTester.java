package main;

import matlab.MatlabOnLoginEvent;
import matlab.MatlabOnOrderActionEvent;
import matlab.MatlabOnOrderCycleCompleteEvent;
import matlab.MatlabOnRtnErrorEvent;
import matlab.MatlabOnRtnOrderEvent;
import matlab.MatlabOnRtnTradeEvent;
import matlab.MatlabTradeListener;
import ctpapiinterface.MatlabTradeIntegrator;

public class MatLabTradeIntegratorTester {
	
	static{
		System.loadLibrary("CTPDLL");
		System.loadLibrary("CTPTRADEDLL");
	}
	
	private static class ICListener implements MatlabTradeListener{

		@Override
		public void matlabOnRtnOrderEvent(MatlabOnRtnOrderEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void matlabOnRtnTradeEvent(MatlabOnRtnTradeEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void matlabOnRtnErrorEvent(MatlabOnRtnErrorEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void matlabOnLoginEvent(MatlabOnLoginEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void matlabOnOrderActionEvent(MatlabOnOrderActionEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void matlabOnCycleCompleted(MatlabOnOrderCycleCompleteEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}

	
	public static void main(String[] args){
		MatlabTradeIntegrator integrator1 = new MatlabTradeIntegrator(0, "c:\\mddata\\rawdata.csv");
		integrator1.addMatlabTradeListener(new ICListener());
		integrator1.requestLogin("1013", "123321", "00000008");
		integrator1.sendSettlement();
		integrator1.sendOrder("IF1309", 0, 0, 2300, 1, 60000);
		
	}
}
