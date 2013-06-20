package matlab;

import java.util.EventListener;

public interface MatlabTradeListener extends EventListener {
	public void matLabOnRtnOrderEvent(MatlabOnRtnOrderEvent event);
	public void matLabOnRtnTradeEvent(MatlabOnRtnTradeEvent event);
	public void matLabOnRtnErrorEvent(MatlabOnRtnErrorEvent event);
	public void matLabOnLoginEvent(MatlabOnLoginEvent event);
}
