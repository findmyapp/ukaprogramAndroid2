package no.uka.findmyapp.ukaprogram.utils;

import java.net.URISyntaxException;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.ukaprogram.activities.EventDetailsActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class EventsUpdater {
	private static final String debug = "EventsUpdater";
	
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	private Context context; 
	
	public EventsUpdater(Context c) {
		this.context = c; 
	}
	
	public void initUpdate() {
		startupUpdate(); 
	}
	
	public void updateEvents() {
		try {
			update();
		} catch (UpdateException e) {
			Log.v(debug, "startupUpdate exception caught " + e.getException().getMessage());
			Toast t = Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_LONG);
			t.show(); 
		} 
	}
	
	private void update() throws UpdateException{
		try {		
			serviceHelper.callStartService(this.context, UkappsServices.UKAEVENTS); 
		} catch (URISyntaxException e) {
			throw new UpdateException(UpdateException.URI_SYNTAX_EXCEPTION, e); 
		} catch (IllegalAccessException e) {
			throw new UpdateException(UpdateException.ILLEGAL_ACCESS_EXCEPTION, e); 
		} catch (InstantiationException e) {
			throw new UpdateException(UpdateException.INSTANTIATION_EXCEPTION, e); 
		}
	}
	
	private void startupUpdate() {
        ReciveIntent intentReceiver = new ReciveIntent();
		IntentFilter intentFilter = new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN);
		
		context.registerReceiver(intentReceiver, intentFilter); 
		
		try {
			update();
		} catch (UpdateException e) {
			Log.v(debug, "startupUpdate exception caught " + e.getException().getMessage());
			Toast t = Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_LONG);
			t.show(); 
		} 
	}
	
	private class ReciveIntent extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(IntentMessages.BROADCAST_INTENT_TOKEN)) {
				Intent i = new Intent(context, EventDetailsActivity.class); 
				context.startActivity(i);
			}
		}
	}
	
	private static class UpdateException extends Exception {
		private static final long serialVersionUID = 1042598607999418184L;
		public static final String ILLEGAL_ACCESS_EXCEPTION = 
			"Illegal access exception caught during update";
		public static final String URI_SYNTAX_EXCEPTION = 
			"URI syntax exception caught during update";
		public static final String INSTANTIATION_EXCEPTION = 
			"Instantiation exception caught during update";
		
		private Exception e; 
		
		public UpdateException(String errorMessage, Exception e) {
			super(errorMessage);
			this.e = e; 
		}
		
		public Exception getException() { return this.e; }
	}
}
