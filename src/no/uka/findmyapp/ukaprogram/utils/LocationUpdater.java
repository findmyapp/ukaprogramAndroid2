/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;

import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import android.content.Context;
import android.util.Log;

/**
 * The Class LocationUpdater.
 */
public class LocationUpdater extends Updater 
{
	
	/** The Constant debug. */
	private static final String debug = "LocationUpdater";
	
	/**
	 * Instantiates a new location updater.
	 *
	 * @param c the c
	 */
	public LocationUpdater(Context c) {
		super(c); 
	}
	
	/**
	 * Update locations.
	 */
	public void updateLocations() {
		Log.v(debug, "updateEvents called");
		try { 
			update(
					UkappsServices.GET_ALL_LOCATIONS, 
					new URI(LocationContract.LOCATION_CONTENT_URI.toString()), 
					null ); 
		} 
		catch (Exception e) {
			Log.e(debug, "updateEvents exception caught " + e.getMessage());
			Toaster.shoutLong(mContext, e.getMessage());
		} 
	}
	
	/* 
	 * 	
	public void softUpdate() {
		if(tableExistsAndNotEmpty() == true) {}
		else updateLocations();
	}

	private boolean tableExistsAndNotEmpty( ) {
		Cursor eventCursor = mContext.getContentResolver()
			.query(UkaEventContract.EVENT_CONTENT_URI, null, null, null, null);
		if(eventCursor.getCount() > 0) {
			return true; 
		}
		return false;
	}
	
	 */
}
