package factories;

import orderrepository.AtLimitBucket;
import orderrepository.AtMarketOrderBucket;
import orderrepository.AtStopBucket;
import orderrepository.OrderBucket;

public class OrderBucketFactory {
	public OrderBucket createBucket(int orderType, double signalPrice){
		if(orderType == 2){
			AtLimitBucket answer = new AtLimitBucket();
			answer.setSignalPrice(signalPrice);
			return answer;
		}
		if(orderType == 3){
			AtStopBucket answer = new AtStopBucket();
			answer.setSignalPrice(signalPrice);
			return answer;
		}
		if(signalPrice == 0 && orderType == 0){
			AtMarketOrderBucket answer = new AtMarketOrderBucket();
			return answer;
		}
		return new OrderBucket();
		
	}
}
