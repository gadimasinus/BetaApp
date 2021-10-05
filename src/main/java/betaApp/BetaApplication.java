package betaApp;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import betaApp.dto.TickerPrice;
import betaApp.service.PriceCacheService;
import betaApp.service.PriceFileParserService;

@SpringBootApplication
public class BetaApplication {

	public static void main(String [] args) {
	
		String filePath = "C:\\Personal\\Manish\\Learning\\schonfeld\\prices.txt";
		PriceFileParserService ps = new PriceFileParserService(filePath);
		List<TickerPrice> prices=  ps.getAllClosingPrices();
		System.out.println(prices);
		
		PriceCacheService.addPricesToCache(prices);
		
		SpringApplication.run(BetaApplication.class,args);
		
	}
}
