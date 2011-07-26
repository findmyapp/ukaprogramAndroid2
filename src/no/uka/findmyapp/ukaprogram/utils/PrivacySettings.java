/**
 * 
 */
package no.uka.findmyapp.ukaprogram.utils;

import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author torstein.barkve
 *
 */
public class PrivacySettings
{

	private static class UserPrivacyDatabaseHelper extends SQLiteOpenHelper {
		
		/** The Constant CREATE_TABLE. */
		private static final String CREATE_TABLE = 
			  "CREATE TABLE " + ApplicationConstants.USER_PRICVAY_TABLE_NAME + " ("	
			  + ApplicationConstants.USER_PRICVAY_COLUMN_ID + " INTEGER PRIMARY KEY, "
			  + ApplicationConstants.USER_PRICVAY_COLUMN_USER_PRIVACY_ID + " INTEGER, "
			  + ApplicationConstants.USER_PRICVAY_COLUMN_MONEY + "INTEGER,  "
			  + ApplicationConstants.USER_PRICVAY_COLUMN_EVENTS + "INTEGER,  "
			  + ApplicationConstants.USER_PRICVAY_COLUMN_MEDIA + "INTEGER,  "
			  + ApplicationConstants.USER_PRICVAY_COLUMN_POSITION + "INTEGER"
			  + ");";
		
		/** The Constant DROP_TABLE_QUERY. */
		private static final String DROP_TABLE_QUERY = 
			"DROP TABLE IF EXISTS " + ApplicationConstants.FAVOURITE_TABLE_NAME + ";";
		
		/**
		 * Instantiates a new favourite database helper.
		 *
		 * @param context the context
		 */
		public UserPrivacyDatabaseHelper(Context context) {
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
