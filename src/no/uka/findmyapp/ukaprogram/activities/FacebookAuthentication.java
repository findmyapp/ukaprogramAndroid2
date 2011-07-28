/**
 * 
 */
package no.uka.findmyapp.ukaprogram.activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

/**
 * @author torstein.barkve
 *
 */
public class FacebookAuthentication extends Activity
{
	public static final String debug = "FacebookAuthentication";
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;
	private Handler mHandler = new Handler();
	private TextView mHeader; 

 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		// setup the facebook session
		mFacebook = new Facebook(ApplicationConstants.UKA_PROGRAM_FACEBOOK_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		
		mHeader = (TextView) findViewById(R.layout.facebook_authentication); 
		
		if (mFacebook.isSessionValid()) {
			mHeader.setText(getResources().getText(R.string.facebookAuthentication_logout));
			AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(mFacebook);
			asyncRunner.logout(this, new LogoutListener());
		} else {
			authorize(); 
		}
		
	//	mAsyncRunner.request("me", new IDRequestListener());
 
	}
	
	protected void authorize() {
		mFacebook.authorize(this, new LoginDialogListener()); 
	}
	
	private class LoginDialogListener implements DialogListener {
		 
		public void onComplete(Bundle values) {
			//mHeader.setText(getResources().getString(R.string.facebookAuthentication_loggedIn));
		}
 
		@Override
		public void onFacebookError(FacebookError e) {
		}
 
		@Override
		public void onCancel() {
		}

		@Override
		public void onError(DialogError arg0) {
		}
	}
	
	private class LogoutListener implements RequestListener {
		@Override
		public void onComplete(String response, Object state) {
			// Dispatch on its own thread
			mHandler.post(new Runnable() {
				public void run() {
					mHeader.setText(getResources().getString(R.string.facebookAuthentication_loggedOut));
				}
			});
		}
		@Override
		public void onIOException(IOException e, Object state) {
		}
 
		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
		}
 
		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
		}
 
		@Override
		public void onFacebookError(FacebookError e, Object state) {
		}
	}
 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
}
