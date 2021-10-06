package betaApp.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils {
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public static List<LocalDate> countBusinessDaysBetweenDates(final LocalDate startDate, 
	        final LocalDate endDate,
	        final Optional<List<LocalDate>> holidays) 
	{
	    if (startDate == null || endDate == null) {
	        throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetweenDates (" + startDate
	                + "," + endDate + "," + holidays + ")");
	    }
	 
	    Predicate<LocalDate> isHoliday = date -> holidays.isPresent() && holidays.get().contains(date);
	 
	    Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
	            || date.getDayOfWeek() == DayOfWeek.SUNDAY;
	 
	    long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
	 
	    return Stream.iterate(startDate, date -> date.plusDays(1))
	            .limit(daysBetween +1)
	            .filter(isHoliday.or(isWeekend).negate())
	            .collect(Collectors.toList());
	}
	
	public static LocalDate getDateFromString(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
     //   System.out.println(localDate);  //default, print ISO_LOCAL_DATE
        return localDate;

	}
	
	public static LocalDate subtractBusinessDays(LocalDate localDate, int days, Optional<List<LocalDate>> holidays) {
		if (localDate == null || days <= 0 || holidays == null) {
			throw new IllegalArgumentException("Invalid method argument(s) " + "to subtractBusinessDays(" + localDate
					+ "," + days + "," + holidays + ")");
		}

		Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;

		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;

		LocalDate result = localDate;
		while (days > 0) {
			result = result.minusDays(1);
			if (isHoliday.or(isWeekend).negate().test(result)) {
				days--;
				//System.out.println(result);
			}
		}
		return result;
	}
	
	public static List<LocalDate> getPreviousBusinessDays(LocalDate localDate, int days, Optional<List<LocalDate>> holidays) {
		if (localDate == null || days <= 0 || holidays == null) {
			throw new IllegalArgumentException("Invalid method argument(s) " + "to subtractBusinessDays(" + localDate
					+ "," + days + "," + holidays + ")");
		}
		
		List<LocalDate> results = new ArrayList<>();

		Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;

		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;

		LocalDate result = localDate;
		while (days > 0) {
			result = result.minusDays(1);
			if (isHoliday.or(isWeekend).negate().test(result)) {
				days--;
				results.add(result);
			}
		}
		return results;
	}

}

