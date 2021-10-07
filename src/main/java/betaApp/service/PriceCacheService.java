package betaApp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import betaApp.dto.PriceCacheKey;
import betaApp.dto.TickerPrice;

public class PriceCacheService {

	static ConcurrentHashMap<PriceCacheKey, Double> priceMap = new ConcurrentHashMap<PriceCacheKey, Double>();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public static Optional<Double> getPrice(String ticker, LocalDate date) {
		PriceCacheKey key = new PriceCacheKey(ticker, getDateAsString(date));
		return  Optional.ofNullable(priceMap.get(key));
	}
	
	public static boolean addPricesToCache(List<TickerPrice> prices) {
		try {
			if(prices==null || prices.size()==0) {
				return false;
			}
			prices.stream().forEach(price -> {
				PriceCacheKey key = new PriceCacheKey(price.getTicker(), price.getDate());
				addToCache(key, price.getPrice());
			});
			System.out.println("Prices loaded to cache, No of Records " + priceMap.size());
			return true;
			
		} catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	private static void addToCache(PriceCacheKey key, Double val) {
		priceMap.put(key, val);
	}
	
	private static String getDateAsString(LocalDate date) {
		return  date.format(formatter);
	}
}
