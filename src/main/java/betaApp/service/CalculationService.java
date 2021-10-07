package betaApp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import betaApp.dto.BetaRequest;
import betaApp.utility.CalcUtils;
import betaApp.utility.DateUtils;

public class CalculationService implements ICalculationService {

	public List<Double> calcBeta(BetaRequest request) {
		LocalDate startDt = DateUtils.getDateFromString(request.getStartDate());
		LocalDate endDt = DateUtils.getDateFromString(request.getEndDate());
		List<LocalDate> eligbleDates = DateUtils.countBusinessDaysBetweenDates(startDt, endDt, Optional.empty());
		
		List<Double> betas = new ArrayList<>();

		for (LocalDate eDate : eligbleDates) {
			Double val = CalcUtils.calculateBetaCoefficient(request.getTicker(), request.getBaselineTicker(), eDate,
					request.getBetaDurationDays());
			betas.add(val);
		}
		return betas;
	}
}
