/**
 * 
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
import no.uka.findmyapp.ukaprogram.model.CustomParameter;
import android.content.Context;
import android.util.Log;

/**
 * @author torstein.barkve
 *
 */
public class UserFeedback
{
	private static final String debug = "UserFeedback";
	
	private static final String CUSTOM_PARAMETER_URL = 
		"http://findmyapp.net/findmyapp/locations/??/userreports?parname=??&noe=??";
	
	private static RestServiceHelper serviceHelper = 
			RestServiceHelper.getInstance(); 
	
	private Context mContext; 

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
	
	public void getCustomParameterDataFromLocation(String[] params, 
			String broadcastIdentifier) {
		
		ServiceModel serviceModel = createCustomParameterServiceModel(
				HttpType.GET, CustomParameter.class, null, broadcastIdentifier);
		
		serviceModel.setParameters(params);
		
		serviceHelper.callStartService(mContext, serviceModel);
	}
	
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
