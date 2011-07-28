package no.uka.findmyapp.ukaprogram.utils;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.android.rest.client.RestServiceException;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Updater 
{
	private static final String debug = "Updater";
	
	protected static RestServiceHelper serviceHelper = 
		RestServiceHelper.getInstance();
	
	protected Context mContext; 

	protected Updater(Context c) {
		Log.v(debug, "Creating updater!");
		mContext = c; 
		try {
			serviceHelper.setCredentials(ApplicationConstants.FINDMYAPP_API_KEY, 
					ApplicationConstants.FINDMYAPP_API_SECRET);
		}
		catch (RestServiceException e) {
			e.printStackTrace();
			Log.e(debug, e.getMessage());
		}
	}
	
	protected boolean readyState() {
		return NetworkUtils.isOnline(mContext);
	}
	
	protected void update(UkappsServices ukappsService, URI contentProvider, String[] parameters) {
		try {
			Log.v(debug, "update called");
			Log.v(debug, "update readystate: " + readyState());
			if(readyState()) {		
				try {
					serviceHelper.callStartService(mContext, ukappsService, contentProvider, parameters); 
				}
				catch (Exception e) {
					throw e; 
				}
			}
		} catch (UpdateException e) {
			Log.e(debug, "updateEvents exception caught " 
					+ e.getException().getMessage());
			Toaster.shoutLong(mContext, e.getMessage());
		} catch (Exception e) {
			Toaster.shoutLong(mContext, e.getMessage());
		}
	}
	
	/**
	 * The Class UpdateException.
	 */
	protected static class UpdateException extends Exception {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1042598607999418184L;
		
		/** The Constant ILLEGAL_ACCESS_EXCEPTION. */
		public static final String ILLEGAL_ACCESS_EXCEPTION = 
			"Illegal access exception caught during update";
		
		/** The Constant URI_SYNTAX_EXCEPTION. */
		public static final String URI_SYNTAX_EXCEPTION = 
			"URI syntax exception caught during update";
		
		/** The Constant INSTANTIATION_EXCEPTION. */
		public static final String INSTANTIATION_EXCEPTION = 
			"Instantiation exception caught during update";
		
		/** The Constant NO_CONNECTION_EXCEPTION. */
		public static final String NO_CONNECTION_EXCEPTION  = 
			"No internet connection available!";
		
		/** The e. */
		private Exception e; 
		
		/**
		 * Instantiates a new update exception.
		 *
		 * @param errorMessage the error message
		 */
		public UpdateException(String errorMessage) {
			super(errorMessage);
			e = new Exception(errorMessage); 
		}
		
		/**
		 * Instantiates a new update exception.
		 *
		 * @param errorMessage the error message
		 * @param e the exception
		 */
		public UpdateException(String errorMessage, Exception e) {
			super(errorMessage);
			this.e = e; 
		}
		
		/**
		 * Gets the exception.
		 *
		 * @return the exception
		 */
		public Exception getException() { return this.e; }
	}
}
