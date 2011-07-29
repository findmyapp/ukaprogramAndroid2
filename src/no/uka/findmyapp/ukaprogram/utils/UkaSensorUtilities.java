/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import no.uka.findmyapp.android.rest.client.RestServiceException;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.datamodels.models.Temperature;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.Context;
import android.util.Log;

/**
 * The Class UkaSensorUtilities.
 */
public class UkaSensorUtilities
{
	
	/** The Constant debug. */
	private static final String debug = "UkaSensorUtilities";
	
	/** The service helper. */
	private RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	
	/** The m context. */
	private Context mContext;
	
	/**
	 * Instantiates a new uka sensor utilities.
	 *
	 * @param context the context
	 */
	public UkaSensorUtilities(Context context) {
		mContext = context;
		try {
			serviceHelper.setCredentials(ApplicationConstants.FINDMYAPP_API_KEY, 
					ApplicationConstants.FINDMYAPP_API_SECRET);
		}
		catch (RestServiceException e) {
			Log.e(debug, e.getMessage()); 
		}
	}
	
	/**
	 * Gets the temperature from location.
	 *
	 * @param locationStringId the location string id
	 * @return the temperature from location
	 */
	public Temperature getTemperatureFromLocation(String locationStringId) {
		/*inti
		
		String[] params = new String[] {locationStringId};
		
		try {
			ServiceModel serviceModel = new ServiceModel(
					new URI("http://) 
					httpType, 
					returnType, 
					data, 
					contentProviderUri, 
					broadcastNotification);
			
			serviceHelper.callStartService(mContext, 
					UkappsServices.GET_TEMPERATURE_DATA_FROM_LOCATION, 
					null, 
					params);
			
			
		}
		catch (RestServiceException e) {
			Log.e(debug, e.getMessage());
			Toaster.shoutLong(mContext, e.getMessage());
		}
		*/
		return null; 
	}
	
	
}
