package nativeinterfaces;

public class NativeInterfaceFactory {
	private MarketDataNativeInterface mdInterface;
	private TradingNativeInterface tradeInterface;
	private static NativeInterfaceFactory instance = new NativeInterfaceFactory();
	
	public static NativeInterfaceFactory getInstance(){
		return instance;
	}
	
	private NativeInterfaceFactory(){}
	
	
	
	public MarketDataNativeInterface createMDInterface(int debugFlag, String dataPath){
		if(debugFlag == 0){
			mdInterface = new MarketDataNativeInterface();
		}
		
		else{
			mdInterface = new MockMDNativeInterface(dataPath);
			((MockMDNativeInterface)(mdInterface)).initiateTimer();
		}
		
		return mdInterface;
	}
	
	public TradingNativeInterface createTradingInterface(int debugFlag) throws InterfaceNotCreatedException{
		TradingNativeInterface answer;
		if(debugFlag == 0){
			answer = new TradingNativeInterface();
		}
		else{
			if(mdInterface == null){
				throw new InterfaceNotCreatedException("Mock trader can not be created because the mock trader has not been created");
			}
			if(! (mdInterface instanceof MockMDNativeInterface)){
				throw new InterfaceNotCreatedException("Mock Trader can not be created because market data is not compatible try using debug flag 1 for createMDInterface ");
			}
			answer = new MockTradingInterface((MockMDNativeInterface) mdInterface);
		}
			
		
		return answer;
	}
	
	public MarketDataNativeInterface getMDInterface() throws InterfaceNotCreatedException{
		if(mdInterface == null){
			throw new InterfaceNotCreatedException("The market data interface has not been initialized in the factory");
		}
		return mdInterface;
	}
	
	public TradingNativeInterface getTradingInterface() throws InterfaceNotCreatedException{
		if(tradeInterface == null){
			throw new InterfaceNotCreatedException("The market data interface has not been initialized");
		}
		return tradeInterface;
	}
}