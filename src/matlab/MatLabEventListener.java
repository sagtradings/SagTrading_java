package matlab;


public interface MatLabEventListener extends java.util.EventListener {
    public void barDataEvent(BarDataEvent event);
    public void marketDataEvent(MarketDataEvent event);
    public void matLabOnLoginEvent(MatLabOnLoginEvent event);
    public void matLabOnSubscribeDataEvent(MatLabOnSubscribeDataEvent event);
 }
