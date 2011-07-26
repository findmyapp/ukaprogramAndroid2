package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LocationUpdater extends Updater {
	public LocationUpdater(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	private static final String debug = "LocationUpdater";
	
	public void updateLocations() {
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
			Toast t = Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_LONG);
			t.show(); 
		} 
	}
	
	private void update() throws UpdateException{
		Log.v(debug, "update called");
		try {		
			Log.v(debug, "inside");
			serviceHelper.callStartService(this.context, UkappsServices.LOCATION_GET_ALL_LOCATIONS,
					new URI(LocationContract.LOCATION_CONTENT_URI.toString()), new String[] {}); 
		} catch (URISyntaxException e) {
			throw new UpdateException(UpdateException.URI_SYNTAX_EXCEPTION, e); 
		} catch (IllegalAccessException e) {
			throw new UpdateException(UpdateException.ILLEGAL_ACCESS_EXCEPTION, e); 
		} catch (InstantiationException e) {
			throw new UpdateException(UpdateException.INSTANTIATION_EXCEPTION, e); 
		}
	}
}
