package no.uka.findmyapp.ukeprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.ukeprogram.providers.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukeprogram.restlibrary.IntentMessages;
import no.uka.findmyapp.ukeprogram.restlibrary.RestServiceException;
import no.uka.findmyapp.ukeprogram.restlibrary.UkappsServices;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class EventsUpdater extends Updater {
	private static final String debug = "EventsUpdater";
	Context mContext;
	public EventsUpdater(Context c) {
		super(c);
		mContext = c;
	}

	public void updateEvents() {
		Log.v(debug, "updateEvents called");
		try {
			if (Constants.DEBUG) Log.v(debug, "isOnline " + isOnline());
			if (isOnline()) {
				update();
			} else {
				if (Constants.DEBUG) Log.e(debug, "No internet connection!\nPlease connect to the internet and restart the application");
				throw new UpdateException(UpdateException.NO_CONNECTION_EXCEPTION);
			}	
		} catch (UpdateException e) {
			if (Constants.DEBUG) Log.e(debug, "updateEvents exception caught " + e.getException().getMessage());
			Toast t = Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_LONG);
			t.show(); 
			
			Intent broadcastedIntent = new Intent(); 
			broadcastedIntent.setAction(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION);
			mContext.sendBroadcast(broadcastedIntent);
		}
	}
	
	private void update() throws UpdateException{
		if (Constants.DEBUG) Log.v(debug, "update called");
		try {		
			if (Constants.DEBUG) Log.v(debug, "inside");
			URI contentUri = new URI(UkaEventContract.EVENT_CONTENT_URI.toString());
			serviceHelper.callStartService(this.context, UkappsServices.GET_ALL_UKAEVENTS, contentUri, new String[] {"uka11"});
			if (Constants.DEBUG) Log.d(debug, "outside");
		}  catch (RestServiceException e) {
			if (Constants.DEBUG) Log.e(debug, e.getMessage());
		} catch (URISyntaxException e) {
			if (Constants.DEBUG) Log.e(debug, e.getMessage());
		}
	}
}
