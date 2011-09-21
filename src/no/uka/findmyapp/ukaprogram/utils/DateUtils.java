package no.uka.findmyapp.ukaprogram.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.util.Log;

public class DateUtils {

	private static final SimpleDateFormat standardDateFormat = new SimpleDateFormat(
			"dd.MM");
	private static final SimpleDateFormat standardTimeFormat = new SimpleDateFormat(
			"hh:mm");
	private static final SimpleDateFormat standardDayFormat = new SimpleDateFormat(
			"dd");
	public static final String MONDAY = "man";
	public static final String TUESDAY = "tirs";
	public static final String WEDNESDAY = "ons";
	public static final String THURSDAY = "torsdag";
	public static final String FRIDAY = "fre";
	public static final String SATURDAY = "lør";
	public static final String SUNDAY = "søn";
	
	public static final String MONDAY_LONG = "mandag";
	public static final String TUESDAY_LONG = "tirsdag";
	public static final String WEDNESDAY_LONG = "onsdag";
	public static final String THURSDAY_LONG = "torsdag";
	public static final String FRIDAY_LONG = "fredag";
	public static final String SATURDAY_LONG = "lørdag";
	public static final String SUNDAY_LONG = "søndag";

	public String getCustomDateFormatFromTimestamp(String dateFormat,
			long timestamp) {
		if (timestamp == 0)
			return null;
		else
			return new SimpleDateFormat(dateFormat).format(new Date(timestamp));
	}

	public String getDateStringFromTimestamp(long timestamp) {
		if (timestamp == 0)
			return null;
		else
			return standardDateFormat.format(new Date(timestamp));
	}

	public String getTimeFromTimestamp(long timestamp) {
		if (timestamp == 0)
			return null;
		else
			return standardTimeFormat.format(new Date(timestamp));
	}

	public int getDayIntFromTimestamp(long timestamp) {
		if (timestamp == 0)
			return 0;
		else {
			return new Integer(standardDayFormat.format(new Date(timestamp)));
		}
	}

	public String getWeekdayNameFromTimestamp(long timestamp) {
		if (timestamp == 0)
			return null;
		else
			return getShortWeekDayName(getDayIntFromTimestamp(timestamp));
	}

	private String getShortWeekDayName(int dayOfMonth) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		
		case 1:
			return MONDAY;
		case 2:
			return TUESDAY;
		case 3:
			return WEDNESDAY;
		case 4:
			return THURSDAY;
		case 5:
			return FRIDAY;
		case 6:
			return SATURDAY;
		case 7:
			return SUNDAY;
		default:
			return null;
		}
	}

	private String getLongWeekDayName(int dayOfMonth) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return MONDAY_LONG;
		case Calendar.TUESDAY:
			return TUESDAY;
		case Calendar.WEDNESDAY:
			return WEDNESDAY;
		case Calendar.THURSDAY:
			return THURSDAY;
		case Calendar.FRIDAY:
			return FRIDAY;
		case Calendar.SATURDAY:
			return SATURDAY;
		case Calendar.SUNDAY:
			return SUNDAY;
		default:
			return null;
		}
	}
	
	public String getWeekdayLongFromTimestamp(long timestamp) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timestamp);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		Log.d("DAY OF WEEK:", String.valueOf(dayOfWeek));
		
		switch (dayOfWeek) {
		
		case Calendar.MONDAY:
			return MONDAY_LONG;
		case Calendar.TUESDAY:
			return TUESDAY_LONG;
		case Calendar.WEDNESDAY:
			return WEDNESDAY_LONG;
		case Calendar.THURSDAY:
			return THURSDAY_LONG;
		case Calendar.FRIDAY:
			return FRIDAY_LONG;
		case Calendar.SATURDAY:
			return SATURDAY_LONG;
		case Calendar.SUNDAY:
			return SUNDAY_LONG;
		default:
			return null;
		}
	}

	public String getWeekdayFromTimestamp(long timestamp) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timestamp);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		Log.d("DAY OF WEEK:", String.valueOf(dayOfWeek));
		
		switch (dayOfWeek) {
		
		case Calendar.MONDAY:
			return MONDAY;
		case Calendar.TUESDAY:
			return TUESDAY;
		case Calendar.WEDNESDAY:
			return WEDNESDAY;
		case Calendar.THURSDAY:
			return THURSDAY;
		case Calendar.FRIDAY:
			return FRIDAY;
		case Calendar.SATURDAY:
			return SATURDAY;
		case Calendar.SUNDAY:
			return SUNDAY;
		default:
			return null;
		}
	}
}
