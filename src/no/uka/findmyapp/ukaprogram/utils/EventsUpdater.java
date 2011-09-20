package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.ukaprogram.providers.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.restlibrary.RestServiceException;
import no.uka.findmyapp.ukaprogram.restlibrary.UkappsServices;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class EventsUpdater extends Updater {
	private static final String debug = "EventsUpdater";
	
	public EventsUpdater(Context c) {
		super(c);
	}

	public void updateEvents() {
		Log.v(debug, "updateEvents called");
		try {
			Log.v(debug, "isOnline " + isOnline());
			if (isOnline()) {
				update();
			} else {
				Log.e(debug, "No internet connection!\nPlease connect to the internet and restart the application");
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
			URI contentUri = new URI(UkaEventContract.EVENT_CONTENT_URI.toString());
<<<<<<< HEAD
			Log.v(debug, "ContentUri: " + contentUri.toString());
=======
>>>>>>> 465cdc7f7e2860411d150fb5889ac13522d54980
			serviceHelper.callStartService(this.context, UkappsServices.GET_ALL_UKAEVENTS, contentUri, new String[] {"uka11"});
		}  catch (RestServiceException e) {
			Log.e(debug, e.getMessage());
		} catch (URISyntaxException e) {
			Log.e(debug, e.getMessage());
		}
	}
}
