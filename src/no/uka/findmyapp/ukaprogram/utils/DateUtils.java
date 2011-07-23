package no.uka.findmyapp.ukaprogram.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtils.
 */
public class DateUtils {
	
	/** The Constant debug. */
	private static final String debug = "DateUtils";
	
	/** The Constant standardDateFormat. */
	private static final SimpleDateFormat standardDateFormat = 
		new SimpleDateFormat("dd.MM");
	
	/** The Constant standardTimeFormat. */
	private static final SimpleDateFormat standardTimeFormat = 
		new SimpleDateFormat("hh:mm");
	
	/** The Constant standardDayFormat. */
	private static final SimpleDateFormat standardDayFormat = 
		new SimpleDateFormat("dd"); 
	
	/** The Constant MONDAY. */
	public static final String MONDAY = "man";
	
	/** The Constant TUESDAY. */
	public static final String TUESDAY = "tirs";
	
	/** The Constant WEDNESDAY. */
	public static final String WEDNESDAY = "ons";
	
	/** The Constant THURSDAY. */
	public static final String THURSDAY = "torsdag";
	
	/** The Constant FRIDAY. */
	public static final String FRIDAY = "fre";
	
	/** The Constant SATURDAY. */
	public static final String SATURDAY = "lør";
	
	/** The Constant SUNDAG. */
	public static final String SUNDAY = "søn"; 
	
	/** The constructed date format. */
	private String constructedDateFormat; 
	
	/**
	 * Gets the custom date format from timestamp.
	 *
	 * @param dateFormat the date format
	 * @param timestamp the timestamp
	 * @return the custom date format from timestamp
	 */
	public String getCustomDateFormatFromTimestamp(String dateFormat, long timestamp) {
		if(timestamp == 0) return null; 
		else return new SimpleDateFormat(dateFormat).format(new Date(timestamp));
	}

	/**
	 * Gets the date string from timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the date string from timestamp
	 */
	public String getDateStringFromTimestamp(long timestamp) {
		if(timestamp == 0) return null;
		else return standardDateFormat.format(new Date(timestamp)); 
	}
	
	/**
	 * Gets the time from timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the time from timestamp
	 */
	public String getTimeFromTimestamp(long timestamp) {
		if(timestamp == 0) return null; 
		else return standardTimeFormat.format(new Date(timestamp)); 
	}
	
	/**
	 * Gets the day int from timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the day int from timestamp
	 */
	public int getDayIntFromTimestamp(long timestamp) {
		if(timestamp == 0) return 0;
		else {
			Log.v(debug, "getCustomDay " + new Integer(standardDayFormat.format(new Date(timestamp))));
			return new Integer(standardDayFormat.format(new Date(timestamp)));  
		}
	}
	
	/**
	 * Gets the weekday name from timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the weekday name from timestamp
	 */
	public String getWeekdayNameFromTimestamp(long timestamp) {
		if(timestamp == 0) return null; 
		else return getShortWeekDayName(getDayIntFromTimestamp(timestamp));
	}
	
	/**
	 * Gets the short week day name.
	 *
	 * @param dayOfMonth the day of month
	 * @return the short week day name
	 */
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
}
