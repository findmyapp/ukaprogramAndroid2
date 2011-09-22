package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.EventsUpdater;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class StartupLoader extends Activity {
	private static final int SPLASH_SCREEN_TIME = 1200;

	private static final String debug = "StartupLoader";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Log.v(debug, "loading splash screen");
		setContentView(R.layout.splash);
		Log.v(debug, "Loading UkaProgram");

		waitAndStartMainActivity();
	}

	private void waitAndStartMainActivity() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				startMainActivity();
			}
		}, SPLASH_SCREEN_TIME);
	}

	private void startMainActivity() {
		Log.v(debug, "startMainActivity called");
		Intent i = new Intent(this, Main.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}
}
