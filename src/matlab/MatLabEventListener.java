package matlab;


public interface MatLabEventListener extends java.util.EventListener {
    public void matLabEvent(MatLabEvent event);
    public void matLabTickEvent(MatLabTickEvent event);
 }
