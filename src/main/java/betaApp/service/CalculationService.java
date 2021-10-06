package betaApp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import betaApp.dto.BetaRequest;
import betaApp.dto.TickerPrice;
import betaApp.utility.DateUtils;
import betaApp.utility.MathUtils;

public class CalculationService {
	
	public List<Double> calcBeta(BetaRequest request) {
		LocalDate startDt = DateUtils.getDateFromString(request.getStartDate());
		LocalDate endDt = DateUtils.getDateFromString(request.getEndDate());
		List<LocalDate> eligbleDates = DateUtils.countBusinessDaysBetweenDates(startDt,endDt,  Optional.empty());
		System.out.println(eligbleDates);
		
		List<Double> betas = new ArrayList<>();
		
		for(LocalDate eDate : eligbleDates) {
			Double val = getBetaCoefficient(request.getTicker(), request.getBaselineTicker(), eDate, request.getBetaDurationDays() );
			betas.add(val);
			//LocalDate priorDate = DateUtils.subtractBusinessDays(eDate, request.getBetaDurationDays(),Optional.empty());
			System.out.println("beta for date " +  eDate + " is " + val);
		}
		return betas;
	}
	
	private Double getReturnForTicker(String ticker, LocalDate date) {
		try {
			String currkey = TickerPrice.getKey(ticker, date);
			Optional<Double> currPrice =  PriceCacheService.getPrice(currkey);
			
			LocalDate priorDate = DateUtils.subtractBusinessDays(date,1,Optional.empty());
			String priorkey = TickerPrice.getKey(ticker, priorDate);
			Optional<Double> priorPrice =  PriceCacheService.getPrice(priorkey);
			
			Double p1 = currPrice.isPresent() ? currPrice.get() : 100;
			Double p0 = priorPrice.isPresent() ? priorPrice.get() : 100;
			
			//handle  if p0 is  zero based on requirement
			if(p0 ==0) {
				return 1.00;
			}
			Double val = Math.log(1 + (p1 - p0)/p0);
			return val;

		} catch(Exception ex) {
			System.out.println(ex);
			return 0.00;
		}
	}
	
	private List<Double> getTimeSeriesReturn(String ticker, LocalDate baseDate, int noOfDays) {
		List<Double> returns = new ArrayList<>();
		List<LocalDate> dates = DateUtils.getPreviousBusinessDays(baseDate, noOfDays, Optional.empty() );
		for(LocalDate dt : dates) {
			returns.add(getReturnForTicker(ticker, dt));
		}
		return returns;
	}
	
	private Double getBetaCoefficient(String ticker, String baselineTicker, LocalDate baseDate, int noOfDays) {
		Double coVar = getCovariance(ticker, baselineTicker,baseDate, noOfDays  );
		Double var = getVariance(ticker, baseDate, noOfDays);
		
		if(var !=0) {
			return (double)Math.round((coVar/var) * 10000d) / 10000d;  //round to 4 decimal digits
		} else { //handle this case as per req
			return 0.00;
		}
		
	}
	private Double getCovariance(String ticker, String baselineTicker, LocalDate baseDate, int noOfDays) {
		
		List<Double> returnsTicker = getTimeSeriesReturn(ticker, baseDate, noOfDays);
		List<Double> returnsBaselineTicker = getTimeSeriesReturn(baselineTicker, baseDate, noOfDays);
		Double avgReturnTicker = MathUtils.getAverage(returnsTicker);
		Double avgReturnBaselineTicker = MathUtils.getAverage(returnsBaselineTicker);
		
		Double sum =0.00;
		for(int i =0; i < returnsTicker.size(); i++) {
			sum = sum + ((returnsTicker.get(i) - avgReturnTicker) * (returnsBaselineTicker.get(i) -avgReturnBaselineTicker));
		}		

		//handle zero or one days based on req;
		if(noOfDays < 2) {
			return 0.00;
		}
		return sum/(noOfDays -1);
		
	}

	private Double getVariance(String ticker, LocalDate baseDate, int noOfDays) {
		List<Double> returns = getTimeSeriesReturn(ticker, baseDate, noOfDays);
		Double avg = MathUtils.getAverage(returns);
		
		Double sum =0.00;
		for(Double val : returns) {
			sum = sum + ((val - avg) * (val -avg));
		}
		
		//handle zero or one days based on req;
		if(noOfDays < 2) {
			return 0.00;
		}
		return sum/(noOfDays -1);
		
		
	}
}
