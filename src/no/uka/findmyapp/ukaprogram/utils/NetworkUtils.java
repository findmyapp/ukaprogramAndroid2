/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkUtils.
 */
public class NetworkUtils 
{
	
	/**
	 * Checks if is online.
	 *
	 * @param mContext the m context
	 * @return true, if is online
	 */
	public static final boolean isOnline(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) 
			mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
