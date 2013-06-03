package main;

import bo.TradeRequest;
import listeners.DefaultCTPListener;
import listeners.TradeListener;
import nativeinterfaces.TradingNativeInterface;

public class TradeThrowAway {
	static{
		System.loadLibrary("CTPTRADEDLL");
		System.out.println("TRADEDLL LOADED");
	}
	public static void main(String[] args){
		TradingNativeInterface testInterface = new TradingNativeInterface();
		
		//const char  *userID = "00000008";
		//const char  *password = "123321";
		//const char  *brokerID = "1013";
		testInterface.subscribeListener(new TradeListener());
		testInterface.sendLoginMessage("1013", "123321", "00000008");
		testInterface.sendTradeRequest("1013", "123321", "00000008", new TradeRequest());
		//testInterface.sendOrderAction("1013", "123321", "00000008", null);
	}
}
