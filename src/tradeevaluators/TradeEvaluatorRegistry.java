package tradeevaluators;

import java.util.HashMap;
import java.util.Map;

import orderrepository.AtLimitBucket;
import orderrepository.AtMarketOrderBucket;
import orderrepository.AtStopBucket;
import orderrepository.OrderBucket;

public class TradeEvaluatorRegistry {
	private static Map<Class<?>, IEvaluateTrade> evaluatorMap = new HashMap<Class<?>, IEvaluateTrade>(10);
	static{
		evaluatorMap.put(OrderBucket.class, new AtPriceEvaluator());
		evaluatorMap.put(AtLimitBucket.class, new AtLimitEvaluator());
		evaluatorMap.put(AtStopBucket.class, new AtStopEvaluator());
		evaluatorMap.put(AtMarketOrderBucket.class, new AtMarketEvaluator());
	}
	
	public static IEvaluateTrade getEvaluator(Class<?> clazz){
		IEvaluateTrade answer = evaluatorMap.get(clazz);
		if(answer == null){
			return new AtPriceEvaluator();
		}
		return answer;
	}
}
