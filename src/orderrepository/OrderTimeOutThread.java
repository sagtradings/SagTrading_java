package orderrepository;

import java.util.List;

import nativeinterfaces.InterfaceNotCreatedException;
import nativeinterfaces.NativeInterfaceFactory;
import nativeinterfaces.TradingNativeInterface;
import bo.OrderActionRequest;
import factories.OrderActionFactory;

public class OrderTimeOutThread implements Runnable{

	@Override
	public void run() {


		while(true){
			try {
				long currentTime = System.currentTimeMillis();
				List<OrderBucket> buckets = OrderRepository.getInstance().getAllBucketsByState(OrderBucket.orderStates.INITIAL_REQUEST);
				for(int i = 0, n = buckets.size(); i < n; i++){
					OrderBucket orderBucket = buckets.get(i);
					if(currentTime - orderBucket.getCreationTime() >= orderBucket.getTimeOut()){
						OrderActionFactory factory = new OrderActionFactory();
						OrderActionRequest request = factory.createOrderActionRequest(orderBucket.getInitialRequest().getInstrumentID(), orderBucket.getInitialRequest().getOrderRef());
						orderBucket.setOrderState(OrderBucket.orderStates.ORDER_TIMEOUT);
						try {
							TradingNativeInterface tradeInterface = NativeInterfaceFactory.getInstance().getTradingInterface();
							tradeInterface.sendOrderAction("1013", "123321", "00000008", request);
						} catch (InterfaceNotCreatedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
