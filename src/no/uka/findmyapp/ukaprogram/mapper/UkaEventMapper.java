/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.mapper;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import android.database.Cursor;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class UkaEventMapper.
 */
public class UkaEventMapper 
{
	
	/** The Constant debug. */
	private static final String debug = "EventDatabase";
		
	/**
	 * Gets the uka event from cursor.
	 *
	 * @param cursor the cursor
	 * @return the uka event from cursor
	 */
	public static UkaEvent getUkaEventFromCursor(Cursor cursor) {
		Log.v(debug, "Inside getEventFromCursor");
		UkaEvent ukaEvent = new UkaEvent(); 
		ukaEvent.setId(getIntFromTableColumn(cursor, UkaEventContract.ID));
		ukaEvent.setBillingid(getIntFromTableColumn(cursor, UkaEventContract.BILLING_ID));
		ukaEvent.setEntranceId(getIntFromTableColumn(cursor, UkaEventContract.ENTRANCE_ID));
		ukaEvent.setTitle(getStringFromTableColumn(cursor, UkaEventContract.TITLE));
		ukaEvent.setLead(getStringFromTableColumn(cursor, UkaEventContract.LEAD));
		ukaEvent.setText(getStringFromTableColumn(cursor, UkaEventContract.TEXT));
		ukaEvent.setPlace(getStringFromTableColumn(cursor, UkaEventContract.PLACE));
		ukaEvent.setEventType(getStringFromTableColumn(cursor, UkaEventContract.EVENT_TYPE));
		ukaEvent.setCanceled(getBooleanFromTableColumn(cursor, UkaEventContract.CANCELED));
		ukaEvent.setPrice(getIntFromTableColumn(cursor, UkaEventContract.LOWEST_PRICE));
		ukaEvent.setFavourite(getBooleanFromTableColumn(cursor, UkaEventContract.FAVOURITE));
		ukaEvent.setAgeLimit(getIntFromTableColumn(cursor, UkaEventContract.AGE_LIMIT));			
		ukaEvent.setEventType(getStringFromTableColumn(cursor, UkaEventContract.EVENT_TYPE));
		ukaEvent.setShowingTime(getLongFromTableColumn(cursor, UkaEventContract.SHOWING_TIME));
		ukaEvent.setFree(getBooleanFromTableColumn(cursor, UkaEventContract.FREE));
		ukaEvent.setThumbnail(getStringFromTableColumn(cursor, UkaEventContract.THUMBNAIL));
		ukaEvent.setImage(getStringFromTableColumn(cursor, UkaEventContract.IMAGE));
		
		return ukaEvent;
	}
	
	/**
	 * Gets the string from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the string from table column
	 */
	public static String getStringFromTableColumn(Cursor cursor, String tableColumnName) {
		return cursor.getString(cursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the long from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the long from table column
	 */
	public static long getLongFromTableColumn(Cursor cursor, String tableColumnName) {
		return cursor.getLong(cursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the int from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the int from table column
	 */
	public static int getIntFromTableColumn(Cursor cursor, String tableColumnName) {
		return cursor.getInt(cursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the boolean from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the boolean from table column
	 */
	public static boolean getBooleanFromTableColumn(Cursor cursor, String tableColumnName) {
		int bool = cursor.getInt(cursor.getColumnIndex(tableColumnName));
		return (bool==1) ? true : false; 
	}
}

