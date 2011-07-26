/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.io.Closeable;
import java.util.ArrayList;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class FavouriteUtils.
 *
 * @author torstein.barkve
 */
public class FavouriteUtils
{
	private static final String debug = "FavouriteUtils";
	
	private Context mContext; 
	
	/** The m content resolver. */
	private ContentResolver mContentResolver; 

	private FavouriteDatabaseHelper mDatabaseHelper; 
	
	/**
	 * Instantiates a new favourite utils.
	 *
	 * @param contentResolver the content resolver
	 */
	public FavouriteUtils(Context context) {
		mContext = context; 
		mContentResolver = context.getContentResolver();
	}
	
	/**
	 * Change favourite flag.
	 *
	 * @param id the id
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
	
	public ArrayList<Integer> getAllFavourites() {		
		Log.v(debug, "Inside getAllFavourites");
		FavouriteDatabaseHelper mDatabaseHelper = new FavouriteDatabaseHelper(mContext);
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase(); 
		Cursor cursor = db.query(
				ApplicationConstants.FAVOURITE_TABLE_NAME, null, null, null, null, null, null);
		ArrayList<Integer> eventIds = new ArrayList<Integer>(); 
		
		while(cursor.moveToNext())  {
			eventIds.add(cursor.getInt(cursor.getColumnIndex(ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID)));
			Log.v(debug, "eventsIds: " + eventIds.toArray().toString());
		}
		db.close(); 
	
		return eventIds; 
	}
	
	/**
	 * Change uka events table.
	 *
	 * @param values the values
	 * @param id the id
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
	 */
	private void updatePersistentFavouriteTable(int eventId, boolean flag) {
		if(flag) addPeristentFavourite(getFavouriteContentValues(eventId, flag));
		else removePeristentFavourite(eventId);
	}
	
	private void addPeristentFavourite(ContentValues values) {
		FavouriteDatabaseHelper mDatabaseHelper = new FavouriteDatabaseHelper(mContext);
		Log.v(debug, "addPersistentFavourite: values" + values.toString());
		Log.v(debug, "addPersistentFavourite: databasehelper " + mDatabaseHelper.toString());
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		Log.v(debug, "addPersistentFavourite: checking");
		db.insert(ApplicationConstants.FAVOURITE_TABLE_NAME, null, values);
		db.close(); 
	}
	
	private void removePeristentFavourite(int eventId) {
		String[] whereArgs = new String[] {String.valueOf(eventId)}; 
		FavouriteDatabaseHelper mDatabaseHelper = new FavouriteDatabaseHelper(mContext);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		db.delete(ApplicationConstants.FAVOURITE_TABLE_NAME, 
				ApplicationConstants.FAVOURITE_TABLE_WHERE_CLAUSE_EVENT_ID_IS_FAVOURITE,
				whereArgs);
		db.close(); 
	}
	
	private ContentValues getFavouriteContentValues(int eventId, boolean flag) {
		Log.v(debug, "getFavouriteContentValues eventId" + eventId + " flag " + flag);
		ContentValues values = new ContentValues(); 
		values.put(ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID, eventId);
		values.put(ApplicationConstants.FAVOURITE_TABLE_COLUMN_IS_FAVOURITE, flag);
		
		Log.v(debug, "getFavouriteContentValues " + values.toString());
		return values;
	}
	
	/**
	 * The Class FavouriteDatabaseHelper.
	 */
	private static class FavouriteDatabaseHelper extends SQLiteOpenHelper {
		
		/** The Constant CREATE_TABLE. */
		private static final String CREATE_TABLE = 
			  "CREATE TABLE " + ApplicationConstants.FAVOURITE_TABLE_NAME + " ("	
			  + ApplicationConstants.FAVOURITE_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, "
			  + ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID + " INTEGER, "
			  + ApplicationConstants.FAVOURITE_TABLE_COLUMN_IS_FAVOURITE + " BOOLEAN "
			  + ");";
		
		/** The Constant DROP_TABLE_QUERY. */
		private static final String DROP_TABLE_QUERY = 
			"DROP TABLE IF EXISTS " + ApplicationConstants.FAVOURITE_TABLE_NAME + ";";
		
		/**
		 * Instantiates a new favourite database helper.
		 *
		 * @param context the context
		 */
		public FavouriteDatabaseHelper(Context context) {
			super(context, ApplicationConstants.DATABASE_NAME, 
					null, ApplicationConstants.DATABASE_VERSION);
		}
		
		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
		 */
		@Override
		public void onCreate(SQLiteDatabase db ) {
			db.execSQL(CREATE_TABLE);
		}

		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ) {
	        db.execSQL(DROP_TABLE_QUERY);
	        onCreate(db);
		}
	}
}
