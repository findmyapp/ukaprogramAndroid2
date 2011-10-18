package no.uka.findmyapp.ukeprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.models.UkaEvent;
import no.uka.findmyapp.ukeprogram.providers.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukeprogram.restlibrary.IntentMessages;
import no.uka.findmyapp.ukeprogram.restlibrary.RestServiceException;
import no.uka.findmyapp.ukeprogram.restlibrary.UkappsServices;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.Facebook;

public class FriendsUpdater extends Updater {
	private static final String debug = "FriendsUpdater";
	Context mContext;
	
	public FriendsUpdater(Context c) {
		super(c);
		mContext = c;
	}

	public void getFriendsForEvent(UkaEvent event, String fbtoken)
	{
		try {
			if (isOnline()) {
				update(event, fbtoken);
			} else {
				Log.e(debug, "No internet connection!\nPlease connect to the internet and restart the application");
				throw new UpdateException(UpdateException.NO_CONNECTION_EXCEPTION);
			}
			
		} catch (UpdateException e) {
			if (Constants.DEBUG) Log.e(debug, "getFriendsForEvent exception caught " + e.getException().getMessage());
			Toast t = Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_LONG);
			t.show();
		
			Intent broadcastedIntent = new Intent(); 
			
			broadcastedIntent.setAction(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION);
			mContext.sendBroadcast(broadcastedIntent);
		}
	}
		
	private void update(UkaEvent event, String fbtoken) throws UpdateException{
		if (Constants.DEBUG) Log.v(debug, "update called");
		try {		
			if (Constants.DEBUG) Log.v("RestProcessor", "inside");
			URI contentUri = new URI(UkaEventContract.EVENT_CONTENT_URI.toString());
			
			if (serviceHelper.hasUserToken())
				Log.d(debug, "We have a user token");
			
			serviceHelper.callStartService(this.context, UkappsServices.GET_FRIENDS_ATTENDING_EVENT,
					contentUri, new String[] {String.valueOf(event.getId())});
			Log.d("RestProcessor", "Service started.");
			
		}  catch (RestServiceException e) {
			if (Constants.DEBUG) Log.e(debug, e.getMessage());
		} catch (URISyntaxException e) {
			if (Constants.DEBUG) Log.e(debug, e.getMessage());
		}
	}
}
