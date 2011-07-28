package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;

import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import android.content.Context;
import android.util.Log;

public class LocationUpdater extends Updater 
{
	private static final String debug = "LocationUpdater";
	
	public LocationUpdater(Context c) {
		super(c); 
	}
	
	public void updateLocations() {
		Log.v(debug, "updateEvents called");
		try { update(UkappsServices.GET_ALL_LOCATIONS, new URI(LocationContract.LOCATION_CONTENT_URI.toString()), null ); } 
		catch (Exception e) {
			Log.e(debug, "updateEvents exception caught " + e.getMessage());
			Toaster.shoutLong(mContext, e.getMessage());
		} 
	}
}
