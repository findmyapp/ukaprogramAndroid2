/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.restlibrary;

import java.util.Date;

import no.uka.findmyapp.ukaprogram.models.Credentials;
import no.uka.findmyapp.ukaprogram.models.ServiceModel;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class RestIntentService.
 */

public class RestIntentService extends IntentService {
    
    /** The Constant debug. */
    private static final String debug = "RestIntentService";
    
    /** The Constant SERVICE_NAME. */
    private static final String SERVICE_NAME = "RestIntentService";
    
    /** The rest processor. */
    private RestProcessor restProcessor;

    /**
     * Instantiates a new rest intent service.
     */
    public RestIntentService() {
        super(SERVICE_NAME);
    }

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
    	Log.v(debug,"Inside onHandleIntent");
        Log.v(debug, "" + new Date() + ", In onHandleIntent for thread id = " 
        		+ Thread.currentThread().getId());

        Bundle bundle = intent.getExtras();
        
        Log.v(debug, "onHandleIntent bundle recived");
        
        ServiceModel sm = (ServiceModel)
        	bundle.get(IntentMessages.SERVICE_MODEL_PACKAGE);
        
        Credentials credentials = (Credentials) 
        	bundle.get(IntentMessages.CREDENTIALS_PACKAGE);
        
        createRestProcessor(credentials);
        
        String userToken = "";
        if(bundle.containsKey(IntentMessages.USER_TOKEN_PACKAGE)) {
            userToken = (String) bundle.get(IntentMessages.USER_TOKEN_PACKAGE);
        }
		
		Log.v(debug, "onHandleIntent: Sending " + sm + " to the rest processor");
		
		restProcessor.callRest(sm, userToken);
		
        Log.v(debug, "" + new Date() + ", This thread is waked up.");
	}
	
	/**
	 * Creates the rest processor.
	 *
	 * @param credentials the credentials
	 */
	private void createRestProcessor(Credentials credentials) {
		restProcessor = new RestProcessor(getApplicationContext(), credentials);
	}
	
	/* (non-Javadoc)
	 * @see android.app.IntentService#onCreate()
	 */
	@Override
    public void onCreate() {
    	super.onCreate();
    	Log.v(debug,"onCreate: service started");
    }
	
    /* (non-Javadoc)
     * @see android.app.IntentService#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	super.onStartCommand(intent, flags, startId);
        Log.i(debug, "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        //TODO THINK THIS WORKS....
        return START_STICKY;
    }

    /* (non-Javadoc)
     * @see android.app.IntentService#onDestroy()
     */
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
}
