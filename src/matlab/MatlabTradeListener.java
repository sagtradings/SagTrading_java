package matlab;

import java.util.EventListener;

public interface MatlabTradeListener extends EventListener {
	public void matlabOnRtnOrderEvent(MatlabOnRtnOrderEvent event);
	public void matlabOnRtnTradeEvent(MatlabOnRtnTradeEvent event);
	public void matlabOnRtnErrorEvent(MatlabOnRtnErrorEvent event);
	public void matlabOnLoginEvent(MatlabOnLoginEvent event);
}