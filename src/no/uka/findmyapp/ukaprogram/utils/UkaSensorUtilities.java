/**
 * 
 */
package no.uka.findmyapp.ukaprogram.utils;

import no.uka.findmyapp.android.rest.client.RestServiceException;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.datamodels.models.Temperature;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.Context;
import android.util.Log;

/**
 * @author torstein.barkve
 *
 */
public class UkaSensorUtilities
{
	private static final String debug = "UkaSensorUtilities";
	
	private RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	
	private Context mContext;
	
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
	
	public Temperature getTemperatureFromLocation(String locationStringId) {
	/*	serviceHelper.callStartService(
				mContext, 
				UkappsServices.GET_TEMPERATURE_
				DATA, 
				null, 
				new String{});
		*/
		return null; 
	}
	
	
}
