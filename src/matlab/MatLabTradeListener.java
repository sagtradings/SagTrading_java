package matlab;

import java.util.EventListener;

public interface MatLabTradeListener extends EventListener {
	public void matLabOnRtnOrderEvent(MatLabOnRtnOrderEvent event);
	public void matLabOnRtnTradeEvent(MatLabOnRtnTradeEvent event);
	public void matLabOnRtnErrorEvent(MatLabOnRtnErrorEvent event);
	public void matLabOnLoginEvent(MatLabOnLoginEvent event);
}
