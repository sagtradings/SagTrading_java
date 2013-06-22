package orderrepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import bo.TradeRequest;

public class OrderRepository {
	

	
	private static  OrderRepository instance;
	
	private OrderRepository(){}
	
	public static OrderRepository getInstance(){
		if(instance == null){
			instance = new OrderRepository();
		}
		return instance;
	}
	
	private Map<String, List<OrderBucket>> activeOrders = new HashMap<String, List<OrderBucket>>(10);
	public  void addOrderBucket(String instrument, OrderBucket bucket) throws IncompleteBucketException{
		if(bucket == null){
			throw new IncompleteBucketException("Order bucket can not be null");
		}

		if(bucket.getInitialRequest() == null){
			throw new IncompleteBucketException("No Initial Order for bucket");
		}
		List<OrderBucket> currentList = activeOrders.get(instrument);
		if(currentList == null){
			currentList = new Vector<OrderBucket>(10);
		}
		currentList.add(bucket);
		activeOrders.put(instrument, currentList);
	}
	
	
	public void removeOrder(String instrument, String originatingRef){
		List<OrderBucket> orderList = activeOrders.get(instrument);
		for(int i = 0, n = orderList.size(); i < n; i++){
			if(orderList.get(i).getInitialRequest().getOrderRef().equals(instrument)){
				orderList.remove(i);
			}
		}
	}
	
	public synchronized List<OrderBucket> getOrderBuckets(String instrument){
		return activeOrders.get(instrument);
	}
	
	public synchronized OrderBucket getBucketForOrigOrder(String orderRef){
		Iterator<String> itt = activeOrders.keySet().iterator();
		while(itt.hasNext()){
			List<OrderBucket> subList = activeOrders.get(itt.next());
			for(int i = 0, n = subList.size(); i < n; i++){
				if(subList.get(i).getInitialRequest().getOrderRef().equals(orderRef)){
					return subList.get(i);
				}
			}
		}
		return null;
	}
	
	public synchronized OrderBucket getBucketForExitOrder(String orderRef){
		Iterator<String> itt = activeOrders.keySet().iterator();
		while(itt.hasNext()){
			List<OrderBucket> subList = activeOrders.get(itt.next());
			for(int i = 0, n = subList.size(); i < n; i++){
				if(subList.get(i).getExitRequest().getOrderRef().equals(orderRef)){
					return subList.get(i);
				}
			}
		}
		return null;
	}
	
	public synchronized OrderBucket getBucketForStopLossOrder(String orderRef){
		Iterator<String> itt = activeOrders.keySet().iterator();
		while(itt.hasNext()){
			List<OrderBucket> subList = activeOrders.get(itt.next());
			for(int i = 0, n = subList.size(); i < n; i++){
				if(subList.get(i).getStopLossRequest().getOrderRef().equals(orderRef)){
					return subList.get(i);
				}
			}
		}
		return null;
	}
	
	public synchronized List<OrderBucket> searchBucketsOnState(String instrument, OrderBucket.orderStates state){
		List<OrderBucket> answer = new ArrayList<OrderBucket>(10);
		List<OrderBucket> subList = activeOrders.get(instrument);
		if(subList == null){
			return answer;
		}
		for(int i = 0, n = subList.size(); i < n; i++){
			if(subList.get(i).getOrderState() == state){
				answer.add(subList.get(i));
			}
		}
		return answer;
	}
	
	public synchronized OrderBucket searchBucket(String orderRef){
		Iterator<List<OrderBucket>> itt = activeOrders.values().iterator();
		while(itt.hasNext()){
			List<OrderBucket> buckets = itt.next();
			for(int i = 0, n = buckets.size(); i < n; i++){
				TradeRequest init = buckets.get(i).getInitialRequest();
				TradeRequest exit = buckets.get(i).getExitRequest();
				TradeRequest stop = buckets.get(i).getStopLossRequest();
				if(init.getOrderRef().equals(orderRef) || exit.getOrderRef().equals(orderRef) || stop.getOrderRef().equals(orderRef)){
					return buckets.get(i);
				}
			}
		}
		return null;
	}
	
	public synchronized List<OrderBucket> getAllBucketsByState(OrderBucket.orderStates state){
		List<OrderBucket> answer = new ArrayList<OrderBucket>(10);
		Iterator<String> itt = activeOrders.keySet().iterator();
		while(itt.hasNext()){
			String key = itt.next();
			List<OrderBucket> subList = searchBucketsOnState(key, state);
			for(int i = 0, n = subList.size(); i < n; i++){
				answer.add(subList.get(i));
			}
		}
		return answer;
	}
}
