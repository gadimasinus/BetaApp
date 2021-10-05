package betaApp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import betaApp.dto.TickerPrice;

public class PriceFileParserService {
	
	private String filePath;
	public PriceFileParserService(String fileFullPath) {
		this.filePath = fileFullPath;
	}

	public List<TickerPrice> getAllClosingPrices() {

		try {
			return parsePriceFile(this.filePath);
		} catch(Exception ex) {
			return Collections.emptyList();
		}
			
	}
	
	private  List<TickerPrice> parsePriceFile(String filePath) throws FileNotFoundException, IOException {
		
		if(filePath ==null || filePath.length()==0) {
			System.out.println("filePath is missing...");
			return Collections.EMPTY_LIST;
		}
		
		File priceFile = new File(filePath);
		List<TickerPrice> priceList = new ArrayList<TickerPrice>();
		
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
