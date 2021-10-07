package betaApp.utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import betaApp.service.PriceCacheService;

public class CalcUtils {
	
	public static Double calculateBetaCoefficient(String ticker, String baselineTicker, LocalDate baseDate, int noOfDays) {
		Double coVar = calculateCovariance(ticker, baselineTicker, baseDate, noOfDays);
		Double var = calculateVariance(ticker, baseDate, noOfDays);

		if (var != 0) {
			return (double) Math.round((coVar / var) * 10000d) / 10000d; // round to 4 decimal digits
		} else { // handle this case as per req
			return 0.00;
		}
	}

	public static Double calculateCovariance(String ticker, String baselineTicker, LocalDate baseDate, int noOfDays) {

		List<Double> returnsTicker = getTimeSeriesReturn(ticker, baseDate, noOfDays);
		List<Double> returnsBaselineTicker = getTimeSeriesReturn(baselineTicker, baseDate, noOfDays);
		Double avgReturnTicker = MathUtils.getAverage(returnsTicker);
		Double avgReturnBaselineTicker = MathUtils.getAverage(returnsBaselineTicker);

		Double sum = 0.00;
		for (int i = 0; i < returnsTicker.size(); i++) {
			sum = sum + ((returnsTicker.get(i) - avgReturnTicker)
					* (returnsBaselineTicker.get(i) - avgReturnBaselineTicker));
		}

		// handle zero or one days based on req;
		if (noOfDays < 2) {
			return 0.00;
		}
		return sum / (noOfDays - 1);

	}

	public static Double calculateVariance(String ticker, LocalDate baseDate, int noOfDays) {
		List<Double> returns = getTimeSeriesReturn(ticker, baseDate, noOfDays);
		Double avg = MathUtils.getAverage(returns);

		Double sum = 0.00;
		for (Double val : returns) {
			sum = sum + ((val - avg) * (val - avg));
		}

		// handle zero or one days based on req;
		if (noOfDays < 2) {
			return 0.00;
		}
		return sum / (noOfDays - 1);

	}
	
	private static Double calculateReturnForTicker(String ticker, LocalDate date) {

		Optional<Double> currPrice = PriceCacheService.getPrice(ticker, date);

		LocalDate priorDate = DateUtils.subtractBusinessDays(date, 1, Optional.empty());
		Optional<Double> priorPrice = PriceCacheService.getPrice(ticker, priorDate);

		// Assumption made, if price not found use price of 0
		Double p1 = currPrice.isPresent() ? currPrice.get() : 0;
		Double p0 = priorPrice.isPresent() ? priorPrice.get() : 0;

		// handle appropriately as per requirement
		if (p0 == 0) {
			return 1.00;
		}
		Double val = Math.log(1 + (p1 - p0) / p0);
		return val;

	}

	private static List<Double> getTimeSeriesReturn(String ticker, LocalDate baseDate, int noOfDays) {
		List<Double> returns = new ArrayList<>();
		List<LocalDate> dates = DateUtils.getPreviousBusinessDays(baseDate, noOfDays, Optional.empty());
		for (LocalDate dt : dates) {
			returns.add(calculateReturnForTicker(ticker, dt));
		}
		return returns;
	}
}
