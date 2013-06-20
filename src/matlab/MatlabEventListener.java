package matlab;


public interface MatlabEventListener extends java.util.EventListener {
    public void barDataEvent(BarDataEvent event);
    public void marketDataEvent(MarketDataEvent event);
    public void matlabOnLoginEvent(MatlabOnLoginEvent event);
    public void matlabOnSubscribeDataEvent(MatlabOnSubscribeDataEvent event);
 }
