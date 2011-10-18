package no.uka.findmyapp.ukeprogram.activities;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.utils.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class StartupLoader extends Activity {
	private static final int SPLASH_SCREEN_TIME = 1200;

	private static final String debug = "StartupLoader";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		if (Constants.DEBUG) Log.v(debug, "loading splash screen");
		setContentView(R.layout.splash);
		if (Constants.DEBUG) Log.v(debug, "Loading UkaProgram");

		WaitAndStart w = new WaitAndStart();
		w.execute((String[])null);
	}

	private class WaitAndStart extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				Thread.sleep(SPLASH_SCREEN_TIME);
			} catch (Exception e) {
			}
			
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			startMainActivity();
		}
	
	}

	private void startMainActivity() {
		if (Constants.DEBUG) Log.v(debug, "startMainActivity called");
		Intent i = new Intent(this, Main.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}
}
