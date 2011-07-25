/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import no.uka.findmyapp.android.rest.client.IntentMessages;
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
import android.util.Log;
import android.widget.Toast;

/**
 * The Class EventsUpdater.
 */
public class EventsUpdater 
{
	/** The Constant debug. */
	private static final String debug = "EventsUpdater";
	
	/** The service helper. */
	private static RestServiceHelper serviceHelper 
		= RestServiceHelper.getInstance(); 
	
	/** The context. */
	private Context mContext; 
	
	/**
	 * Instantiates a new events updater.
	 *
	 * @param context the c
	 */
	public EventsUpdater(Context context) {
		this.mContext = context; 
	}
	
	/**
	 * Events database not emtpy.
	 *
	 * @return true, if successful
	 */
	public boolean eventsDatabaseNotEmtpy() {
		Log.v(debug, "inside eventsDatabaseNotEmtpy");
		Cursor eventCursor = mContext.getContentResolver()
			.query(UkaEventContract.EVENT_CONTENT_URI, null, null, null, null);
		if(eventCursor.getCount() > 0) {
			return true; 
		}
		return false; 
	}
	
	/**
	 * Update events.
	 */
	public void updateEvents() {
		Log.v(debug, "updateEvents called");
		try {
			Log.v(debug, "isOnline " + isOnline());
			if(isOnline()) { 
				update(); 
			}
			else { 
				Log.v(debug, "No internet connection!");
				throw new UpdateException(UpdateException.NO_CONNECTION_EXCEPTION); 
			}	
		} catch (UpdateException e) {
			Log.e(debug, "updateEvents exception caught " + e.getException().getMessage());
			Toast t = Toast.makeText(this.mContext, e.getMessage(), Toast.LENGTH_LONG);
			t.show(); 
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
		Log.v(debug, "clearEventTable " + contentResolver.toString());
		contentResolver.delete(UkaEventContract.EVENT_CONTENT_URI, null, null);
	}
	
	/**
	 * Update.
	 *
	 * @throws UpdateException the update exception
	 */
	private void update() throws UpdateException{
		Log.v(debug, "update called");
		try {
			clearEventTable(mContext.getContentResolver());
			
			// Setup reciver that update favourite flags
			setupBroadCastReciver();
			serviceHelper.callStartService(this.mContext, UkappsServices.UKAEVENTS, new URI(UkaEventContract.EVENT_CONTENT_URI.toString()), new String[] {"uka11"}); 
		} catch (URISyntaxException e) {
			throw new UpdateException(UpdateException.URI_SYNTAX_EXCEPTION, e); 
		} catch (IllegalAccessException e) {
			throw new UpdateException(UpdateException.ILLEGAL_ACCESS_EXCEPTION, e); 
		} catch (InstantiationException e) {
			throw new UpdateException(UpdateException.INSTANTIATION_EXCEPTION, e); 
		}
	}
	
	/**
	 * Checks if is online.
	 *
	 * @return true, if is online
	 */
	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	private void setupBroadCastReciver() {
        ReciveIntent intentReceiver = new ReciveIntent();
		IntentFilter intentFilter = new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN);
		
		mContext.getApplicationContext().registerReceiver(intentReceiver, intentFilter); 
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
		}
	}
	
	/**
	 * The Class UpdateException.
	 */
	private static class UpdateException extends Exception {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1042598607999418184L;
		
		/** The Constant ILLEGAL_ACCESS_EXCEPTION. */
		public static final String ILLEGAL_ACCESS_EXCEPTION = 
			"Illegal access exception caught during update";
		
		/** The Constant URI_SYNTAX_EXCEPTION. */
		public static final String URI_SYNTAX_EXCEPTION = 
			"URI syntax exception caught during update";
		
		/** The Constant INSTANTIATION_EXCEPTION. */
		public static final String INSTANTIATION_EXCEPTION = 
			"Instantiation exception caught during update";
		
		/** The Constant NO_CONNECTION_EXCEPTION. */
		public static final String NO_CONNECTION_EXCEPTION  = 
			"No internet connection available!";
		
		/** The e. */
		private Exception e; 
		
		/**
		 * Instantiates a new update exception.
		 *
		 * @param errorMessage the error message
		 */
		public UpdateException(String errorMessage) {
			super(errorMessage);
			e = new Exception(errorMessage); 
		}
		
		/**
		 * Instantiates a new update exception.
		 *
		 * @param errorMessage the error message
		 * @param e the exception
		 */
		public UpdateException(String errorMessage, Exception e) {
			super(errorMessage);
			this.e = e; 
		}
		
		/**
		 * Gets the exception.
		 *
		 * @return the exception
		 */
		public Exception getException() { return this.e; }
	}
}
