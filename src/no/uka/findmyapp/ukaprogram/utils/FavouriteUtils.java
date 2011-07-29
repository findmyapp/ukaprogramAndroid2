/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.util.ArrayList;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class FavouriteUtils.
 */
public class FavouriteUtils
{
	
	/** The Constant debug. */
	private static final String debug = "FavouriteUtils";
	
	/** The m context. */
	private Context mContext; 
	
	/** The m content resolver. */
	private ContentResolver mContentResolver; 

	/** The m database helper. */
	private UkaProgramDatabaseHelper mDatabaseHelper; 
	
	/**
	 * Instantiates a new favourite utils.
	 *
	 * @param context the context
	 */
	public FavouriteUtils(Context context) {
		mContext = context; 
		mContentResolver = context.getContentResolver();
	}
	
	/**
	 * Change favourite flag.
	 *
	 * @param eventId the event id
	 * @param isFavourite the is favourite
	 */
	public void changeFavouriteFlag(int eventId, boolean isFavourite) {
		Log.v(debug, "inside changeFavouriteFlag");
		ContentValues values = new ContentValues();
		values.put(UkaEventContract.FAVOURITE, isFavourite);

		Log.v(debug, "values " +  values.toString());
		
		// Update the uka_event table, using the content provider
		changeUkaEventsTable(values, eventId);
		
		// Update the persistent favourite table
		updatePersistentFavouriteTable(eventId, isFavourite);
	}
	
	/**
	 * Gets the all favourites.
	 *
	 * @return the all favourites
	 */
	public ArrayList<Integer> getAllFavourites() {		
		UkaProgramDatabaseHelper mDatabaseHelper
				= new UkaProgramDatabaseHelper(mContext);

		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase(); 
		Cursor cursor = db.query(
				ApplicationConstants.FAVOURITE_TABLE_NAME,
				null, null, null, null, null, null);
		
		ArrayList<Integer> eventIds = new ArrayList<Integer>(); 
		
		while(cursor.moveToNext())  {
			eventIds.add(cursor.getInt(cursor.getColumnIndex(
					ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID)));
		}
		db.close(); 
	
		return eventIds; 
	}
	
	/**
	 * Change uka events table.
	 *
	 * @param values the values
	 * @param eventId the event id
	 */
	private void changeUkaEventsTable(ContentValues values, int eventId) {
		Log.v(debug, "changeUkaEventsTable init values " + values.toString() + " eventId " + eventId);
		String[] selectionArgs = new String[] {String.valueOf(eventId)}; 
		Log.v(debug, "selectionArgs " + selectionArgs[0]);
		mContentResolver.update(UkaEventContract.EVENT_CONTENT_URI, 
				values, 
				ApplicationConstants.FAVOURITE_TABLE_WHERE_CLAUSE_EVENT_ID_IS_FAVOURITE,
				selectionArgs);
	}
	
	//TODO Implement persistant fav storing
	/**
	 * Update persistent favourite table.
	 *
	 * @param eventId the event id
	 * @param flag the flag
	 */
	private void updatePersistentFavouriteTable(int eventId, boolean flag) {
		if(flag) addPeristentFavourite(getFavouriteContentValues(eventId, flag));
		else removePeristentFavourite(eventId);
	}
	
	/**
	 * Adds the peristent favourite.
	 *
	 * @param values the values
	 */
	private void addPeristentFavourite(ContentValues values) {
		Log.v(debug, "addPersistentFavourite: values" + values.toString());
		UkaProgramDatabaseHelper mDatabaseHelper = new UkaProgramDatabaseHelper(mContext);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		db.insert(ApplicationConstants.FAVOURITE_TABLE_NAME, null, values);
		db.close(); 
	}
	
	/**
	 * Removes the peristent favourite.
	 *
	 * @param eventId the event id
	 */
	private void removePeristentFavourite(int eventId) {
		String[] whereArgs = new String[] {String.valueOf(eventId)}; 
		UkaProgramDatabaseHelper mDatabaseHelper = new UkaProgramDatabaseHelper(mContext);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		db.delete(ApplicationConstants.FAVOURITE_TABLE_NAME, 
				ApplicationConstants.FAVOURITE_TABLE_WHERE_CLAUSE_EVENT_ID_IS_FAVOURITE,
				whereArgs);
		db.close(); 
	}
	
	/**
	 * Gets the favourite content values.
	 *
	 * @param eventId the event id
	 * @param flag the flag
	 * @return the favourite content values
	 */
	private ContentValues getFavouriteContentValues(int eventId, boolean flag) {
		ContentValues values = new ContentValues(); 
		values.put(ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID, eventId);
		values.put(ApplicationConstants.FAVOURITE_TABLE_COLUMN_IS_FAVOURITE, flag);
		
		Log.v(debug, "getFavouriteContentValues " + values.toString());
		return values;
	}
	
}
