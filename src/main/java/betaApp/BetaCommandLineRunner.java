package betaApp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import betaApp.dto.TickerPrice;
import betaApp.service.PriceCacheService;
import betaApp.service.PriceFileParserService;

@Component
public class BetaCommandLineRunner implements CommandLineRunner {

	@Autowired
	PriceFileParserService priceFileParserService;
	
	@Override
	public void run(String... args) throws Exception {
		loadPricesAndCache();
	}
	
	private void loadPricesAndCache() {
		List<TickerPrice> prices=  priceFileParserService.getAllClosingPrices();
		PriceCacheService.addPricesToCache(prices);
	}
}
