package betaApp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import betaApp.dto.TickerPrice;

@Service
public class PriceFileParserService {
	
	@Value("${closingPrices.fileName}")
	private String priceFileName;
	
	public List<TickerPrice> getAllClosingPrices() {
		try {
			if(!checkPricesFile()) {
				System.out.println("closing prices file missing, add prices.txt file in current dir");
				return Collections.emptyList();
			}
			return parsePriceFile(this.priceFileName);
		} catch(Exception ex) {
			System.out.println(ex);
			return Collections.emptyList();
		}
	}
	
	private boolean checkPricesFile() {
		Path path = Paths.get(priceFileName);
		return Files.exists(path);
	}
	
	private  List<TickerPrice> parsePriceFile(String filePath) throws FileNotFoundException, IOException {
		
		File priceFile = new File(filePath);
		List<TickerPrice> priceList = new ArrayList<TickerPrice>();
		System.out.println("Parseing prices file started...");

		try(BufferedReader br = new BufferedReader(new FileReader(priceFile)) ) {
			String line;
			while((line = br.readLine()) != null) {
				 String [] data = line.split(",");
				 if(data.length ==3) {
					 TickerPrice pt = new TickerPrice(data[0], data[1], Double.parseDouble(data[2]) );
					 priceList.add(pt);
				 }
			}
		}
		return priceList;
	}
}
