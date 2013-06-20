package matlab;


public interface MatlabEventListener extends java.util.EventListener {
    public void barDataEvent(BarDataEvent event);
    public void marketDataEvent(MarketDataEvent event);
    public void matLabOnLoginEvent(MatlabOnLoginEvent event);
    public void matLabOnSubscribeDataEvent(MatlabOnSubscribeDataEvent event);
 }
