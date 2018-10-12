package Helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
	/**
	 * add a days to a date
	 * @param date the state day 
	 * @param days number of the to remove 
	 * @return the new Date
	 */
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);			
		return cal.getTime();
	}
	/**
	 * removes a days to a date
	 * @param date the state day 
	 * @param days number of the to remove 
	 * @return the new Date
	 */
	public static Date subtractDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
				
		return cal.getTime();
	}
}
