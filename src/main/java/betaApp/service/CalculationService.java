package betaApp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import betaApp.dto.BetaRequest;
import betaApp.utility.DateUtils;

public class CalculationService {
	
	public void calcBeta(BetaRequest request) {
		LocalDate startDt = DateUtils.getDateFromString(request.getStartDate());
		LocalDate endDt = DateUtils.getDateFromString(request.getEndDate());
		List<LocalDate> eligbleDates = DateUtils.countBusinessDaysBetweenDates(startDt,endDt,  Optional.empty());
		System.out.println(eligbleDates);
		
		for(LocalDate eDate : eligbleDates) {
			LocalDate priorDate = DateUtils.subtractBusinessDays(eDate, request.getBetaDurationDays(),Optional.empty());
			System.out.println(priorDate + " for date " + eDate);
		}
	}

}
