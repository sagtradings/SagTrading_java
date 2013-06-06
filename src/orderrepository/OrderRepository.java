package orderrepository;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {
	
	private static  OrderRepository instance;
	
	private OrderRepository(){}
	
	public static OrderRepository getInstance(){
		if(instance == null){
			instance = new OrderRepository();
		}
		return instance;
	}
	
	private Map<String, OrderBucket> activeOrders = new HashMap<String, OrderBucket>(10);
	public void addOrderBucket(String instrument, OrderBucket bucket) throws IncompleteBucketException{
		if(bucket == null){
			throw new IncompleteBucketException("Order bucket can not be null");
		}
		if(bucket.getExitRequest() == null){
			throw new IncompleteBucketException("No Exit Request associated with order");
		}
		if(bucket.getInitialRequest() == null){
			throw new IncompleteBucketException("No Initial Order for bucket");
		}
		if(activeOrders.get(instrument) != null){
			throw new IncompleteBucketException("There is already an acive order for instrument: " + instrument);
		}
		
		activeOrders.put(instrument, bucket);
	}
	
	public void removeOrder(String instrument){
		activeOrders.remove(instrument);
	}
	
	public OrderBucket getOrderBucket(String instrument){
		return activeOrders.get(instrument);
	}
}
