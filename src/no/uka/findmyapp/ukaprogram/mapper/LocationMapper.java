package no.uka.findmyapp.ukaprogram.mapper;

import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import no.uka.findmyapp.android.rest.datamodels.models.Location;
import no.uka.findmyapp.ukaprogram.utils.CursorTools;
import android.database.Cursor;
import android.util.Log;

public class LocationMapper {
	private static final String debug = "LocationMapper";
	public static Location getLocationFromCursor(Cursor c){
		CursorTools ct = new CursorTools();
		Location location = new Location();
		
		Log.v(debug, "Location name: " + ct.getStringFromTableColumn(c, LocationContract.LOCATION_NAME));
		location.setLocationId(ct.getIntFromTableColumn(c, LocationContract.LOCATION_ID));
		location.setLocationName(ct.getStringFromTableColumn(c, LocationContract.LOCATION_NAME));
		location.setLocationStringId(ct.getStringFromTableColumn(c, LocationContract.LOCATION_STRING_ID));
		
		return location;
	}
}
