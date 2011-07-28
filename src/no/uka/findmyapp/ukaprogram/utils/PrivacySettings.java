/**
 * 
 */
package no.uka.findmyapp.ukaprogram.utils;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.enums.PrivacySetting;
import no.uka.findmyapp.android.rest.datamodels.models.UserPrivacy;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.mapper.PrivacySettingsMapper;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author torstein.barkve
 *
 */
public class PrivacySettings
{
	private static final String debug = "PrivacySettings";
	private Context mContext; 
	/** The m content resolver. */
	private ContentResolver mContentResolver; 
	
	public PrivacySettings(Context context){
		mContext = context;
		mContentResolver = context.getContentResolver();
	}
	
	public void changePrivacySettings(UserPrivacy settings) {
		Log.v(debug, "inside changePrivacySettings");
		ContentValues values = new ContentValues();
				
		values.put(ApplicationConstants.USER_PRIVACY_COLUMN_USER_PRIVACY_ID, 
				settings.getUserPrivacyId());
		values.put(ApplicationConstants.USER_PRIVACY_COLUMN_EVENTS,
				PrivacySetting.toInt(settings.getEventsPrivacySetting()));
		values.put(ApplicationConstants.USER_PRIVACY_COLUMN_MEDIA,
				PrivacySetting.toInt(settings.getMediaPrivacySetting()));
		values.put(ApplicationConstants.USER_PRIVACY_COLUMN_MONEY,
				PrivacySetting.toInt(settings.getMoneyPrivacySetting()));
		values.put(ApplicationConstants.USER_PRIVACY_COLUMN_POSITION, 
				PrivacySetting.toInt(settings.getPositionPrivacySetting()));
		
		//store the privacy settings to database
		setPrivacySettings(values);
	
	}
	public UserPrivacy getPrivacySettings(){
		UkaProgramDatabaseHelper mDatabaseHelper = new UkaProgramDatabaseHelper(mContext);
		Log.v(debug, "getPrivacySettings: databasehelper " + mDatabaseHelper.toString());
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		Cursor cursor = db.query(ApplicationConstants.USER_PRIVACY_TABLE_NAME, 
				null, null, null, null, null, null);
		if(cursor.getCount()==0){
			db.close(); 
			cursor.close();
			Log.v(debug, "getPrivacySettings: The db is empty!");
			return null;
		}else{
		cursor.moveToFirst();
		UserPrivacy userprivacy = PrivacySettingsMapper.getUserPrivacyUkaEventFromCursor(cursor);
		Log.v(debug, "getPrivacySettings: The following data is stored in db:  " + userprivacy.toString());
		db.close(); 
		cursor.close();
		return userprivacy;
		}
	}

	
	private void setPrivacySettings(ContentValues values) {
		UkaProgramDatabaseHelper mDatabaseHelper = 
				new UkaProgramDatabaseHelper(mContext);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		if(privacySettingsDatabaseNoteEmpty()){
			db.delete(ApplicationConstants.USER_PRIVACY_TABLE_NAME,null,null);
			db.insert(ApplicationConstants.USER_PRIVACY_TABLE_NAME,null,values);
			db.close();
		}else{
			db.insert(ApplicationConstants.USER_PRIVACY_TABLE_NAME,null,values);
			db.close();
		}
	}	
	
	public boolean privacySettingsDatabaseNoteEmpty() {
		Log.v(debug, "privacySettingsDatabaseNoteEmpty: checking");
		UkaProgramDatabaseHelper mDatabaseHelper = new UkaProgramDatabaseHelper(mContext);
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		Cursor privacyCursor = 
				db.query(ApplicationConstants.USER_PRIVACY_TABLE_NAME, 
				null, null, null, null, null, null);
		if(privacyCursor.getCount() > 0) {
			db.close();
			return true; 
		}
		db.close();
		return false; 
	}	
}