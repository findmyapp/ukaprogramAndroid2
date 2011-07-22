package no.uka.findmyapp.ukaprogram.utils;

import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Updater {
	
	protected static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	protected Context context; 

	public Updater(Context c) {
		this.context = c; 
	}
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	protected static class UpdateException extends Exception {
		private static final long serialVersionUID = 1042598607999418184L;
		public static final String ILLEGAL_ACCESS_EXCEPTION = 
			"Illegal access exception caught during update";
		public static final String URI_SYNTAX_EXCEPTION = 
			"URI syntax exception caught during update";
		public static final String INSTANTIATION_EXCEPTION = 
			"Instantiation exception caught during update";
		public static final String NO_CONNECTION_EXCEPTION  = 
			"No internet connection available!";
		
		private Exception e; 
		
		public UpdateException(String errorMessage) {
			super(errorMessage);
			e = new Exception(errorMessage); 
		}
		
		public UpdateException(String errorMessage, Exception e) {
			super(errorMessage);
			this.e = e; 
		}
		
		public Exception getException() { return this.e; }
	}
}
