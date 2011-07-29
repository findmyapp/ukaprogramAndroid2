/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
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

// TODO: Auto-generated Javadoc
/**
 * The Class FacebookAuthentication.
 */
public class FacebookAuthentication extends Activity
{
	
	/** The Constant debug. */
	public static final String debug = "FacebookAuthentication";
	
	/** The m facebook. */
	private Facebook mFacebook;
	
	/** The m async runner. */
	private AsyncFacebookRunner mAsyncRunner;
	
	/** The m handler. */
	private Handler mHandler = new Handler();
	
	/** The m header. */
	private TextView mHeader; 

 
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
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
	
	/**
	 * Authorize.
	 */
	protected void authorize() {
		mFacebook.authorize(this, new LoginDialogListener()); 
	}
	
	/**
	 * The listener interface for receiving loginDialog events.
	 * The class that is interested in processing a loginDialog
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addLoginDialogListener<code> method. When
	 * the loginDialog event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see LoginDialogEvent
	 */
	private class LoginDialogListener implements DialogListener {
		 
		/* (non-Javadoc)
		 * @see com.facebook.android.Facebook.DialogListener#onComplete(android.os.Bundle)
		 */
		public void onComplete(Bundle values) {
			//mHeader.setText(getResources().getString(R.string.facebookAuthentication_loggedIn));
		}
 
		/* (non-Javadoc)
		 * @see com.facebook.android.Facebook.DialogListener#onFacebookError(com.facebook.android.FacebookError)
		 */
		@Override
		public void onFacebookError(FacebookError e) {
		}
 
		/* (non-Javadoc)
		 * @see com.facebook.android.Facebook.DialogListener#onCancel()
		 */
		@Override
		public void onCancel() {
		}

		/* (non-Javadoc)
		 * @see com.facebook.android.Facebook.DialogListener#onError(com.facebook.android.DialogError)
		 */
		@Override
		public void onError(DialogError arg0) {
		}
	}
	
	/**
	 * The listener interface for receiving logout events.
	 * The class that is interested in processing a logout
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addLogoutListener<code> method. When
	 * the logout event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see LogoutEvent
	 */
	private class LogoutListener implements RequestListener {
		
		/* (non-Javadoc)
		 * @see com.facebook.android.AsyncFacebookRunner.RequestListener#onComplete(java.lang.String, java.lang.Object)
		 */
		@Override
		public void onComplete(String response, Object state) {
			// Dispatch on its own thread
			mHandler.post(new Runnable() {
				public void run() {
					mHeader.setText(getResources().getString(R.string.facebookAuthentication_loggedOut));
				}
			});
		}
		
		/* (non-Javadoc)
		 * @see com.facebook.android.AsyncFacebookRunner.RequestListener#onIOException(java.io.IOException, java.lang.Object)
		 */
		@Override
		public void onIOException(IOException e, Object state) {
		}
 
		/* (non-Javadoc)
		 * @see com.facebook.android.AsyncFacebookRunner.RequestListener#onFileNotFoundException(java.io.FileNotFoundException, java.lang.Object)
		 */
		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
		}
 
		/* (non-Javadoc)
		 * @see com.facebook.android.AsyncFacebookRunner.RequestListener#onMalformedURLException(java.net.MalformedURLException, java.lang.Object)
		 */
		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
		}
 
		/* (non-Javadoc)
		 * @see com.facebook.android.AsyncFacebookRunner.RequestListener#onFacebookError(com.facebook.android.FacebookError, java.lang.Object)
		 */
		@Override
		public void onFacebookError(FacebookError e, Object state) {
		}
	}
 
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
}
