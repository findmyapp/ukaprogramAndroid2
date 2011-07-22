package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.EventsUpdater;
import no.uka.findmyapp.ukaprogram.utils.LocationUpdater;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class StartupLoader extends Activity {
	private static final String debug = "StartupLoader";

	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 

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
		LocationUpdater lu = new LocationUpdater(getApplicationContext());

		eu.initUpdate(); 
		lu.updateLocations();
	}
	
}
