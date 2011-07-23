	package no.uka.findmyapp.ukaprogram.wrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;


public class EventDatabase {
	private static final String debug = "EventDatabase";
	
	private static EventDatabase INSTANCE; 	
	
	private EventDatabase() {}	

	public static EventDatabase getInstance() {
		if(INSTANCE == null) {		
			INSTANCE = new EventDatabase();	
			return INSTANCE; 		
		}		
		return INSTANCE;	
	}

	public ArrayList<UkaEvent> getAllEvents(ContentResolver contentResolver){
		Log.v(debug, "Inside getAllEvents");
		UkaEvent ukaEvent;
		ArrayList<UkaEvent> eventList = new ArrayList<UkaEvent>();
		Cursor cursor = contentResolver.query(UkaEventContract.EVENT_CONTENT_URI, null, 		
				null, 			
				null, UkaEventContract.SHOWING_TIME);	
		Log.v(debug , "getAllEvents cursor toString " + cursor.toString());
		cursor.moveToFirst();
		while (cursor.moveToNext()) {	
			ukaEvent = getEventFromCursor(cursor);
			eventList.add(ukaEvent);
			cursor.moveToNext();	
			Log.v(debug, "Printing from While: " + ukaEvent.toString());
		}
		Log.v(debug, "Done inserting: " + eventList.toString());
		return eventList;
	}

	public ArrayList<UkaEvent> getEventsInPeriod(ContentResolver contentResolver, Timestamp fromTime, Timestamp toTime){
		ContentValues contentValues = new ContentValues();
		UkaEvent ukaEvent;
		ArrayList<UkaEvent> eventList = new ArrayList<UkaEvent>();
		Log.v(debug, "Timestamp = " + fromTime + " to " + toTime);
		Cursor cursor = contentResolver.query(UkaEventContract.EVENT_CONTENT_URI, 
				null, 		
				null, 			
				null, 
				UkaEventContract.SHOWING_TIME);
		if(cursor != null){
			Log.v(debug, "Cursor = " + cursor);
			cursor.moveToFirst();
			while (cursor.moveToNext()) {	
				ukaEvent = getEventFromCursor(cursor);
				eventList.add(ukaEvent);
				cursor.moveToNext();	
				Log.v("EventDatabase", "Printing from While: " + ukaEvent.toString());
			}
		}
		return eventList;
	}

	public ArrayList<UkaEvent> getNextEventsFromCategory(ContentResolver contentResolver, int numberOfEvents, String eventType){
		Log.v(debug, "Inside getNextEventsFromCategory");
		ContentValues contentValues = new ContentValues();
		UkaEvent ukaEvent;
		Date now = new Date();
		ArrayList<UkaEvent> eventList = new ArrayList<UkaEvent>();
		Cursor cursor = contentResolver.query(UkaEventContract.EVENT_CONTENT_URI, null, 		
				UkaEventContract.EVENT_TYPE + "=" + eventType + " AND " + UkaEventContract.SHOWING_TIME + " > " + now.toString(), 			
				null, UkaEventContract.SHOWING_TIME);		


		cursor.moveToFirst();
		while (cursor != null) {	
			ukaEvent = getEventFromCursor(cursor);
			Log.v(debug, cursor.toString());
			eventList.add(ukaEvent);
			cursor.moveToNext();	
		}

		return eventList;
	}

	public ArrayList<UkaEvent> getEventsFromPlace(ContentResolver contentResolver, String place){
		UkaEvent ukaEvent;
		Date now = new Date();
		ArrayList<UkaEvent> eventList = new ArrayList<UkaEvent>();
		Cursor cursor = contentResolver.query(UkaEventContract.EVENT_CONTENT_URI, null, 		
				UkaEventContract.PLACE + "=" + place + " AND " + UkaEventContract.SHOWING_TIME + " > " + now.toString(), 			
				null, UkaEventContract.SHOWING_TIME);		


		cursor.moveToFirst();
		while (cursor != null) {	
			ukaEvent = getEventFromCursor(cursor);
			eventList.add(ukaEvent);
			cursor.moveToNext();	
		}

		return eventList;
	}
	
	public UkaEvent getEventFromCursor(Cursor cursor) {
		Log.v(debug, "Inside getEventFromCursor");
		UkaEvent ukaEvent = new UkaEvent();
		ukaEvent.setAgeLimit(cursor.getInt(cursor.getColumnIndex(UkaEventContract.AGE_LIMIT)));			
//		ukaEvent.setCanceled((boolean) cursor.getInt(cursor.getColumnIndex(UkaEventContract.CANCELED)));
		ukaEvent.setEventType(cursor.getString(cursor.getColumnIndex(UkaEventContract.EVENT_TYPE)));
		ukaEvent.setId(cursor.getInt(cursor.getColumnIndex(UkaEventContract.ID)));
		ukaEvent.setEventId((cursor.getInt(cursor.getColumnIndex(UkaEventContract.EVENT_ID))));
		ukaEvent.setText(cursor.getString(cursor.getColumnIndex(UkaEventContract.TEXT)));
		ukaEvent.setShowingTime((cursor.getLong(cursor.getColumnIndex(UkaEventContract.SHOWING_TIME))));
		ukaEvent.setPlace(cursor.getString(cursor.getColumnIndex(UkaEventContract.PLACE)));
		ukaEvent.setTitle(cursor.getString(cursor.getColumnIndex(UkaEventContract.TITLE)));
		ukaEvent.setFavourite(cursor.getInt(cursor.getColumnIndex(UkaEventContract.CANCELED)));
		
		return ukaEvent;
	}

	public void clearEventTable(ContentResolver cr){
		Log.v(debug, "clearEventTable " + cr.toString());
		cr.delete(UkaEventContract.EVENT_CONTENT_URI, null, null);
	}
}

