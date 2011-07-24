/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class FavouriteUtils.
 *
 * @author torstein.barkve
 */
public class FavouriteUtils
{
	/** The m content resolver. */
	private ContentResolver mContentResolver; 
	
	/**
	 * Instantiates a new favourite utils.
	 *
	 * @param contentResolver the content resolver
	 */
	public FavouriteUtils(ContentResolver contentResolver) {
		mContentResolver = contentResolver; 
	}
	
	/**
	 * Change favourite flag.
	 *
	 * @param id the id
	 * @param isFavourite the is favourite
	 */
	public void changeFavouriteFlag(int id, boolean isFavourite) {
		ContentValues values = new ContentValues();
		values.put(UkaEventContract.FAVOURITE, isFavourite);
		
		changeUkaEventsTable(values, id);
	}
	
	/**
	 * Change uka events table.
	 *
	 * @param values the values
	 * @param id the id
	 */
	private void changeUkaEventsTable(ContentValues values, int id) {
		String selection = UkaEventContract.EVENT_ID + " = '" + id + "'"; 
		mContentResolver.update(UkaEventContract.EVENT_CONTENT_URI, values, selection, null);
	}
	
	//TODO Implement persistant fav storing
	/**
	 * Update persistent favourite table.
	 */
	private void updatePersistentFavouriteTable() {
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
