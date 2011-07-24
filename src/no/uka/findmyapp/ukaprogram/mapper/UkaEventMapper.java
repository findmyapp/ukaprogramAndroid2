	package no.uka.findmyapp.ukaprogram.mapper;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import android.database.Cursor;
import android.util.Log;


public class UkaEventMapper {
	private static final String debug = "EventDatabase";
		
	public static UkaEvent getUkaEventFromCursor(Cursor cursor) {
		Log.v(debug, "Inside getEventFromCursor");
		UkaEvent ukaEvent = new UkaEvent(); 
		ukaEvent.setId(getIntFromTableColumn(cursor, UkaEventContract.ID));
		ukaEvent.setEventId(getIntFromTableColumn(cursor, UkaEventContract.EVENT_ID));
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
	
	public static String getStringFromTableColumn(Cursor cursor, String tableColumnName) {
		Log.v(debug, "getStringFromTableColumn tableColumnName " + tableColumnName + "" +
				cursor.getString(cursor.getColumnIndex(tableColumnName)));
		return cursor.getString(cursor.getColumnIndex(tableColumnName));
	}
	
	public static long getLongFromTableColumn(Cursor cursor, String tableColumnName) {
		Log.v(debug, "getLongFromTableColumn tableColumnName " + tableColumnName + "" +
				cursor.getLong(cursor.getColumnIndex(tableColumnName)));
		return cursor.getLong(cursor.getColumnIndex(tableColumnName));
	}
	
	public static int getIntFromTableColumn(Cursor cursor, String tableColumnName) {
		Log.v(debug, "getIntFromTableColumn tableColumnName " + tableColumnName + "" +
				cursor.getInt(cursor.getColumnIndex(tableColumnName)));
		return cursor.getInt(cursor.getColumnIndex(tableColumnName));
	}
	
	public static boolean getBooleanFromTableColumn(Cursor cursor, String tableColumnName) {
		Log.v(debug, "getBoolFromTableColumn tableColumnName " + tableColumnName);
		int bool = cursor.getInt(cursor.getColumnIndex(tableColumnName));
		boolean bbb = (bool==1) ? true : false; 
		Log.v(debug, "getBoolFromTableColumn tableColumnName " + bbb);
		return (bool==1) ? true : false; 
	}
}

