/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.android.rest.client.RestServiceException;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.core.ServiceModel;
import no.uka.findmyapp.android.rest.datamodels.enums.HttpType;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.models.CustomParameter;
import android.content.Context;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class UserFeedback.
 */
public class UserFeedback
{
	
	/** The Constant debug. */
	private static final String debug = "UserFeedback";
	
	/** The Constant CUSTOM_PARAMETER_URL. */
	private static final String CUSTOM_PARAMETER_URL = 
		"http://findmyapp.net/findmyapp/locations/??/userreports?parname=??&noe=??";
	
	/** The service helper. */
	private static RestServiceHelper serviceHelper = 
			RestServiceHelper.getInstance(); 
	
	/** The m context. */
	private Context mContext; 

	/**
	 * Instantiates a new user feedback.
	 *
	 * @param context the context
	 */
	public UserFeedback (Context context){
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
	 * Gets the custom parameter data from location.
	 *
	 * @param params the params
	 * @param broadcastIdentifier the broadcast identifier
	 * @return the custom parameter data from location
	 */
	public void getCustomParameterDataFromLocation(String[] params, 
			String broadcastIdentifier) {
		
		ServiceModel serviceModel = createCustomParameterServiceModel(
				HttpType.GET, CustomParameter.class, null, broadcastIdentifier);
		
		serviceModel.setParameters(params);
		
		serviceHelper.callStartService(mContext, serviceModel);
	}
	
	/**
	 * Creates the custom parameter service model.
	 *
	 * @param httpType the http type
	 * @param returnType the return type
	 * @param params the params
	 * @param broadcastIdentifier the broadcast identifier
	 * @return the service model
	 */
	private ServiceModel createCustomParameterServiceModel(HttpType httpType, 
			Class returnType, String params, String broadcastIdentifier) {
		
		ServiceModel serviceModel = null;
		try {
			params = null; 
			serviceModel = new ServiceModel(
					new URI(CUSTOM_PARAMETER_URL),
					httpType, 
					returnType, 
					params, 
					null, 
					broadcastIdentifier);
		}
		catch (URISyntaxException e) {
			Log.e(debug, e.getMessage());
			Toaster.shoutLong(mContext, e.getMessage());
		}
		
		return serviceModel; 
	}
}
