/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpException;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestMethod.HTTPStatusException;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.test.IsolatedContext;
import android.util.Log;
import android.widget.Toast;

/**
 * The Class EventsUpdater.
 */
public class EventsUpdater extends Updater 
{
	/** The Constant debug. */
	private static final String debug = "EventsUpdater";
	
	/**
	 * Instantiates a new events updater.
	 *
	 * @param context the c
	 */
	public EventsUpdater(Context context) {
		super(context);
	}
	
	/**
	 * Events database not emtpy.
	 *
	 * @return true, if successful
	 */
	public boolean eventsDatabaseNotEmtpy() {
		Log.v(debug, "inside eventsDatabaseNotEmtpy");
		Log.v(debug, UkaEventContract.EVENT_CONTENT_URI.toString());
		Log.v(debug, mContext.toString());
		Cursor eventCursor = mContext.getContentResolver()
			.query(UkaEventContract.EVENT_CONTENT_URI, null, null, null, null);
		Log.v(debug, eventCursor.toString());
		if(eventCursor.getCount() > 0) {
			return true; 
		}
		return false; 
	}
	
	/**
	 * Update events.
	 * @throws URISyntaxException 
	 * @throws UpdateException 
	 */
	public void updateEvents() {
		try { 
			Log.v(debug, "updateEvents called");
			if(readyState()) { 
				clearEventTable(mContext.getContentResolver());
				setupBroadCastReciver();
				update(
					UkappsServices.UKAEVENTS, 
					new URI(UkaEventContract.EVENT_CONTENT_URI.toString()), 
					new String[] {"uka11"});
			}
		}
		catch (Exception e) {
			Toaster.shoutLong(mContext, e.getMessage());
		}
	}
	
	private void updateFavourites() {
		FavouriteUtils fu = new FavouriteUtils(mContext);
		ArrayList<Integer> eventIds = fu.getAllFavourites(); 

		for(Integer eventId : eventIds) {
			ContentValues values = new ContentValues(); 
			values.put(UkaEventContract.FAVOURITE, ApplicationConstants.IS_FAVOURITE);
			
			mContext.getContentResolver().update(
					UkaEventContract.EVENT_CONTENT_URI, 
					values, 
					UkaEventContract.ID + "=?", 
					new String[] {String.valueOf(eventId)});
		}
	}
	
	/**
	 * Clear event table.
	 *
	 * @param contentResolver the content resolver
	 */
	public void clearEventTable(ContentResolver contentResolver){
		Log.v(debug, "clearEventTable");
		contentResolver.delete(UkaEventContract.EVENT_CONTENT_URI, null, null);
	}
	
	private void setupBroadCastReciver() {
        ReciveIntent intentReceiver = new ReciveIntent();
		IntentFilter intentFilter = new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN);
		IntentFilter intentFilter2 = new IntentFilter(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION);
		
		mContext.getApplicationContext().registerReceiver(intentReceiver, intentFilter);
		mContext.getApplicationContext().registerReceiver(intentReceiver, intentFilter2);
	}
	

	/**
	 * The Class ReciveIntent.
	 */
	private class ReciveIntent extends BroadcastReceiver {
		
		/* (non-Javadoc)
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(IntentMessages.BROADCAST_INTENT_TOKEN)) {
				Log.v(debug, "Intent recieved, starting setting favourites");
				updateFavourites(); 
			}
			else if(intent.getAction().equals(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION)) {
				Log.v(debug, "Intent recieved, containin http exception");
				Bundle bundle = intent.getBundleExtra(IntentMessages.BROADCAST_RETURN_VALUE_NAME);
				HTTPStatusException exception = 
					(HTTPStatusException) bundle.getSerializable(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION);
				Toast t = Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG);
				t.show();
			}
		}
	}
}
