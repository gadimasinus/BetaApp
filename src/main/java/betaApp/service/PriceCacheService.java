package betaApp.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import betaApp.dto.TickerPrice;

public class PriceCacheService {

	static ConcurrentHashMap<String, Double> priceMap = new ConcurrentHashMap<String, Double>();
	
	public static void addToCache(String key, Double val) {
		priceMap.put(key, val);
	}
	
	public static Optional<Double> getPrice(String key) {
		return  Optional.ofNullable(priceMap.get(key));
	}
	
	public static boolean addPricesToCache(List<TickerPrice> prices) {
		try {
			for(TickerPrice tp : prices) {
				addToCache(tp.getKey(), tp.getPrice());
			}
			return true;
			
		} catch(Exception ex) {
			return false;
		}
	}
}
