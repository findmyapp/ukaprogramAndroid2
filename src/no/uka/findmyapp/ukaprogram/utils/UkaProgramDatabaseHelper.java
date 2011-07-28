package no.uka.findmyapp.ukaprogram.utils;

import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The Class FavouriteDatabaseHelper.
 */
public class UkaProgramDatabaseHelper extends SQLiteOpenHelper {
	
	/** The Constant DROP_TABLE_QUERY. */
	private static final String DROP_TABLE_QUERY = 
		"DROP TABLE IF EXISTS " + ApplicationConstants.FAVOURITE_TABLE_NAME + ";";
	
	/**
	 * Instantiates a new favourite database helper.
	 *
	 * @param context the context
	 */
	public UkaProgramDatabaseHelper(Context context) {
		super(context, ApplicationConstants.DATABASE_NAME, 
				null, ApplicationConstants.DATABASE_VERSION);
	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db ) {
		db.execSQL(ApplicationConstants.USER_PRIVACY_CREATE_TABLE);
		db.execSQL(ApplicationConstants.FAVOURITE_DATABASE_CREATE_TABLE);
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