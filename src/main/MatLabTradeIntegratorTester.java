package main;

import ctpapiinterface.MatlabTradeIntegrator;

public class MatLabTradeIntegratorTester {
	public static void main(String[] args){
		MatlabTradeIntegrator integrator1 = new MatlabTradeIntegrator(0, "c:\\mddata\\rawdata.csv");
		MatlabTradeIntegrator integrator2 = new MatlabTradeIntegrator(0, "c:\\mddata\\rawdata.csv");
		integrator1.requestLogin("", "", "");
		integrator2.requestLogin("", "", "");
		integrator1.sendOrder("IF1309", "0", 0, 10000, 10, 6000000);
		integrator2.sendOrder("IF1309", "0", 0, 10000, 5, 6000000);
		
	}
}
