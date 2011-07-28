/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestMethod.HTTPStatusException;
import no.uka.findmyapp.android.rest.datamodels.enums.PrivacySetting;
import no.uka.findmyapp.android.rest.datamodels.models.UserPrivacy;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.EventsUpdater;
import no.uka.findmyapp.ukaprogram.utils.PrivacySettings;
import no.uka.findmyapp.ukaprogram.utils.Toaster;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

// TODO: Auto-generated Javadoc
/**
 * The Class StartupLoader.
 */
public class StartupLoader extends Activity {
	
	/** The Constant debug. */
	private static final String debug = "StartupLoader";

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.v(debug, "loading splash screen");
		setContentView(R.layout.splash);
		Log.v(debug, "Loading UkaProgram");
		
		EventsUpdater eu = new EventsUpdater(getApplicationContext());
			
		if(eu.eventsDatabaseNotEmtpy()) {
			startMainActivity();
		}
		else {
			setupBroadCastReciver(); 
			eu.updateEvents(); 
		}
	}
	
	/**
	 * Setup broad cast reciver.
	 */
	private void setupBroadCastReciver() {
        ReciveIntent intentReceiver = new ReciveIntent();
		IntentFilter intentFilter = new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN);
		intentFilter.addAction(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION);
		
		getApplicationContext().registerReceiver(intentReceiver, intentFilter); 
	}
	
	/**
	 * Start main activity.
	 */
	private void startMainActivity() {
		Log.v(debug, "startMainActivity called");
		Intent i = new Intent(this, Main.class); 
		startActivity(i);
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
				Log.v(debug, "Intent recieved, starting Main activity");
				startMainActivity();
			}
			else if(intent.getAction().equals(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION)) {
				HTTPStatusException e = (HTTPStatusException) 
						intent.getSerializableExtra(IntentMessages.BROADCAST_RETURN_PAYLOAD_ID);
				Toaster.shoutLong(getApplicationContext(), e.getMessage());
			}
		}
	}
}
