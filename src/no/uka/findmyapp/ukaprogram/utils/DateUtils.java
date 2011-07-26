/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;

import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtils.
 */
public class DateUtils 
{
	
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
	public static final String THURSDAY = "tors";
	
	/** The Constant FRIDAY. */
	public static final String FRIDAY = "fre";
	
	/** The Constant SATURDAY. */
	public static final String SATURDAY = "lør";
	
	/** The Constant SUNDAG. */
	public static final String SUNDAY = "søn"; 
	
	/**
	 * Gets the custom date format from timestamp.
	 *
	 * @param dateFormat the date format
	 * @param timestamp the timestamp
	 * @return the custom date format from timestamp
	 */
	public static String getCustomDateFormatFromTimestamp(String dateFormat, long timestamp) {
		if(timestamp == 0) return null; 
		else return new SimpleDateFormat(dateFormat).format(new Date(timestamp));
	}

	/**
	 * Gets the date string from timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the date string from timestamp
	 */
	public static String getDateStringFromTimestamp(long timestamp) {
		if(timestamp == 0) return null;
		else return standardDateFormat.format(new Date(timestamp)); 
	}
	
	/**
	 * Gets the time from timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the time from timestamp
	 */
	public static String getTimeFromTimestamp(long timestamp) {
		if(timestamp == 0) return null; 
		else return standardTimeFormat.format(new Date(timestamp)); 
	}
	
	/**
	 * Gets the day int from timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the day int from timestamp
	 */
	public static int getDayIntFromTimestamp(long timestamp) {
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
	public static String getWeekdayNameFromTimestamp(long timestamp) {
		if(timestamp == 0) return null; 
		else return getShortWeekDayName(getDayIntFromTimestamp(timestamp));
	}
	
	public static long getTimestampFromDayNumber(int day){
		Timestamp timestamp = new Timestamp(ApplicationConstants.YEAR, ApplicationConstants.MONTH, day, 4, 0, 0, 0);
		return timestamp.getTime();
	}
	/**
	 * Gets the short week day name.
	 *
	 * @param dayOfMonth the day of month
	 * @return the short week day name
	 */
	public static String getShortWeekDayName(int dayOfMonth) {
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
