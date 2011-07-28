/**
 * 
 */
package no.uka.findmyapp.ukaprogram.activities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestMethod.HTTPStatusException;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.model.CustomParameter;
import no.uka.findmyapp.ukaprogram.utils.Toaster;
import no.uka.findmyapp.ukaprogram.utils.UserFeedback;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * @author torstein.barkve
 *
 */
public class Feedback extends Activity
{
	private static final String debug = "Feedback";
	
	protected String mBroadcastIdentifier; 
	
	protected String mPayloadId;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mBroadcastIdentifier = "commentsReceived";
		
		setupReceiver(); 
		UserFeedback uf = new UserFeedback(getApplicationContext());
		
		// Request parameters location - comments - limitResult
		String[] params = new String[] {"10", "comment", "20"}; 
		uf.getCustomParameterDataFromLocation(params, IntentMessages.BROADCAST_INTENT_TOKEN);
	}
	
	private void setupReceiver() {
		Log.v(debug, "setupReceiver: inside");
		CustomParameterReciver intentReceiver = 
				new CustomParameterReciver();
		
		IntentFilter intentFilter = 
				new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN);	
		intentFilter.addAction(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION);
		
		getApplicationContext().registerReceiver(intentReceiver, intentFilter); 
	}
	
	private void handleResult(Intent i) {
		Toaster.shoutLong(getApplicationContext(), i.toString());
		if(i.getAction().equals(IntentMessages.BROADCAST_RETURN_PAYLOAD_ID)) {
			Toaster.shoutLong(getApplicationContext(), "Has extras " + i.getExtras().toString());
			
			GsonBuilder gs = new GsonBuilder(); 
			
			CustomParameter customParamter = new CustomParameter(); 
		}
	}
	
	/**
	 * The Class ReciveIntent.
	 */
	private class CustomParameterReciver extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(IntentMessages.BROADCAST_INTENT_TOKEN)) {
				handleResult(intent);
			}
			else if(intent.getAction().equals(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION)) {
				HTTPStatusException e = (HTTPStatusException) 
						intent.getSerializableExtra(IntentMessages.BROADCAST_RETURN_PAYLOAD_ID);
				Toaster.shoutLong(getApplicationContext(), e.getMessage());
			}
		}
	}
}
