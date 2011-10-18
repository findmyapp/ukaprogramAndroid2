/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukeprogram.restlibrary;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.ukeprogram.models.Credentials;
import no.uka.findmyapp.ukeprogram.models.HttpType;
import no.uka.findmyapp.ukeprogram.models.ServiceModel;
import no.uka.findmyapp.ukeprogram.restlibrary.RestServiceException.RestHelperException;
import no.uka.findmyapp.ukeprogram.utils.Constants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class RestServiceHelper.
 */
public class RestServiceHelper {

	/** The Constant debug. */
	private static final String debug = "RestServiceHelper";

	/** The INSTANCE. */
	private static RestServiceHelper INSTANCE;

	/** The s intent receiver. */
	private ServiceModelReceiver sIntentReceiver;

	/** The m credentials. */
	private Credentials mCredentials; 

	/** The user token. */
	private String userToken;
	
	
	/**
	 * Gets the single instance of RestServiceHelper.
	 *
	 * @return single instance of RestServiceHelper
	 */
	public static RestServiceHelper getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RestServiceHelper();
			return INSTANCE;
		}
		return INSTANCE;
	}

	/**
	 * Sets the credentials.
	 *
	 * @param key the key
	 * @param secret the secret
	 * @throws RestServiceException the rest service exception
	 */
	public void setCredentials(String key, String secret)
			throws RestServiceException {
		if (key != null && secret != null) {
			mCredentials = new Credentials(key, secret);
		} else {
			throw new RestServiceException(RestHelperException.NO_CREDENTIALS);
		}
	}

	/**
	 * Sets the user tken.
	 *
	 * @param userToken the new user token
	 */
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	

	/**
	 * Auth user.
	 *
	 * @param context the context
	 * @param fbToken the fb token
	 * @return true, if successful
	 */
	public boolean authUser(Context context, String fbToken) {
		try {
			ServiceModel model = new ServiceModel(
					new URI("http://findmyapp.net/findmyapp/auth/login?facebookToken=" + fbToken), 
					HttpType.GET, 
					String.class, 
					null, 
					null,
					IntentMessages.BROADCAST_INTENT_TOKEN_USERAUTH);

			if (sIntentReceiver == null) {
				this.registerBroadCastListenerForUserAuth(context);
				this.callStartService(context, model);
			}

		} catch (URISyntaxException e) {
			if (Constants.DEBUG) Log.e(debug, e.getMessage());
		}
		return true;
	}

	/**
	 * Call start service.
	 *
	 * @param context the context
	 * @param service the service
	 * @param contentProviderURI the content provider uri
	 * @param params the params
	 * @throws RestServiceException the rest service exception
	 */
	public void callStartService(Context context, UkappsServices service,
			URI contentProviderURI, String[] params)
			throws RestServiceException {
		
		ServiceModel sm;
		
//		if (apiCredentialsIsSet() == false) {
//			throw new RestServiceException(RestHelperException.NO_CREDENTIALS);
//		}
		
		try {
			sm = UkappServiceFactory.createServiceModel(service);
			
			if (contentProviderURI != null)
				sm.setContentProviderUri(contentProviderURI);

			sm.setParameters(params); 

			if (Constants.DEBUG) Log.v(debug, "callStartService: sm.toString " + sm.toString());
			callStartService(context, sm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Call start service.
	 *
	 * @param context the context
	 * @param serviceModel the service model
	 */
	public void callStartService(Context context, ServiceModel serviceModel) {
		if (Constants.DEBUG) Log.v(debug, "Inside callStarteService, using ServiceModel");
		
		Intent selectIntent = new Intent(context, RestIntentService.class);

		selectIntent.putExtra(IntentMessages.SERVICE_MODEL_PACKAGE,
				serviceModel);
		
		// If credentials is provided
		if (hasProvidedCredentials()) {
			selectIntent.putExtra(IntentMessages.CREDENTIALS_PACKAGE, mCredentials);
		}
				
		context.startService(selectIntent);
	}

	/**
	 * Api credentials is set.
	 *
	 * @return true, if successful
	 */
	private boolean apiCredentialsIsSet() {
		return mCredentials.isCredentialsSet(); 
	}
	
	private boolean hasProvidedCredentials() {
		return mCredentials != null;
	}
	
	/**
	 * Checks for user token.
	 *
	 * @return true, if successful
	 */
	public boolean hasUserToken() {
		return (userToken != null) ? true : false; 
	}

	/**
	 * Register broad cast listener for user auth.
	 *
	 * @param context the context
	 */
    private void registerBroadCastListenerForUserAuth(Context context) {
            if (Constants.DEBUG) Log.v(debug, "Internal: registering broadcastlistener for servicemodel");
            sIntentReceiver = new ServiceModelReceiver();
            IntentFilter intentFilter =
                    new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN_USERAUTH);
            context.registerReceiver(sIntentReceiver, intentFilter);
    }

    /**
     * Unregister broad cast listener for user auth.
     *
     * @param context the context
     */
    private void unregisterBroadCastListenerForUserAuth(Context context) {
            if (Constants.DEBUG) Log.v(debug, "Internal: unregistering broadcastlistener for servicemodel");
            context.unregisterReceiver(sIntentReceiver);
    }

	/**
	 * The Class ServiceModelReceiver.
	 */
	public class ServiceModelReceiver extends BroadcastReceiver {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.content.BroadcastReceiver#onReceive(android.content.Context,
		 * android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Constants.DEBUG) Log.v(debug, "ServiceModelReceiver: received broadcast");
			
			if (intentMessageIdentifiedByBoadcasatToken(intent)) {
				unregisterBroadCastListenerForUserAuth(context);

				Serializable obj = intent.getSerializableExtra(IntentMessages.BROADCAST_INTENT_TOKEN_USERAUTH);
				
				setUserToken((String) obj);
			}
		}

		/**
		 * Intent message identified by boadcasat token.
		 *
		 * @param intent the intent
		 * @return true, if successful
		 */
		private boolean intentMessageIdentifiedByBoadcasatToken(Intent intent) {
			return intent.getAction().equals(
					IntentMessages.BROADCAST_INTENT_TOKEN_USERAUTH);
		}
	}
}
