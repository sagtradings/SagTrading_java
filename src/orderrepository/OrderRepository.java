package orderrepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OrderRepository {
	
	private class ICBucketKey{
		private String instrumentId;
		private String originatingOrderRef;
		public ICBucketKey(String instrumentId, String originatingOrderRef) {
			super();
			this.instrumentId = instrumentId;
			this.originatingOrderRef = originatingOrderRef;
		}
		public String getInstrumentId() {
			return instrumentId;
		}
		public void setInstrumentId(String instrumentId) {
			this.instrumentId = instrumentId;
		}
		public String getOriginatingOrderRef() {
			return originatingOrderRef;
		}
		public void setOriginatingOrderRef(String originatingOrderRef) {
			this.originatingOrderRef = originatingOrderRef;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((instrumentId == null) ? 0 : instrumentId.hashCode());
			result = prime
					* result
					+ ((originatingOrderRef == null) ? 0 : originatingOrderRef
							.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ICBucketKey other = (ICBucketKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (instrumentId == null) {
				if (other.instrumentId != null)
					return false;
			} else if (!instrumentId.equals(other.instrumentId))
				return false;
			if (originatingOrderRef == null) {
				if (other.originatingOrderRef != null)
					return false;
			} else if (!originatingOrderRef.equals(other.originatingOrderRef))
				return false;
			return true;
		}
		private OrderRepository getOuterType() {
			return OrderRepository.this;
		}
	}
	
	private static  OrderRepository instance;
	
	private OrderRepository(){}
	
	public static OrderRepository getInstance(){
		if(instance == null){
			instance = new OrderRepository();
		}
		return instance;
	}
	
	private Map<ICBucketKey, OrderBucket> activeOrders = new HashMap<ICBucketKey, OrderBucket>(10);
	public void addOrderBucket(String instrument, OrderBucket bucket) throws IncompleteBucketException{
		if(bucket == null){
			throw new IncompleteBucketException("Order bucket can not be null");
		}

		if(bucket.getInitialRequest() == null){
			throw new IncompleteBucketException("No Initial Order for bucket");
		}
		
		activeOrders.put(new ICBucketKey(instrument, bucket.getInitialRequest().getOrderRef()), bucket);
	}
	
	public void addOrderBucket(String instrument, String origRef, OrderBucket bucket) throws IncompleteBucketException{
		if(bucket == null){
			throw new IncompleteBucketException("Order bucket can not be null");
		}

		if(bucket.getInitialRequest() == null){
			throw new IncompleteBucketException("No Initial Order for bucket");
		}
		
		activeOrders.put(new ICBucketKey(instrument, origRef),  bucket);
	}
	
	public void removeOrder(String instrument, String originatingRef){
		activeOrders.remove(new ICBucketKey(instrument, originatingRef));
	}
	
	public OrderBucket getOrderBucket(String instrument, String originatingRef){
		return activeOrders.get(new ICBucketKey(instrument, originatingRef));
	}
	
	public List<OrderBucket> getOrdersForInsturment(String insturment){
		Iterator<ICBucketKey> keys = activeOrders.keySet().iterator();
		List<OrderBucket> answer = new ArrayList<OrderBucket>(10);
		while(keys.hasNext()){
			ICBucketKey nextKey = keys.next();
			if(nextKey.getInstrumentId().equals(insturment)){
				answer.add(activeOrders.get(nextKey));
			}
		}
		return answer;
	}
}
