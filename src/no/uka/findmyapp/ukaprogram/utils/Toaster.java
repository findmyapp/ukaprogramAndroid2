/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * The Class Toaster.
 */
public class Toaster
{
	
	/**
	 * Shout long.
	 *
	 * @param context the context
	 * @param message the message
	 */
	public static void shoutLong(Context context, String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
		t.show(); 
	}
	
	/**
	 * Shout short.
	 *
	 * @param context the context
	 * @param message the message
	 */
	public static void  shoutShort(Context context, String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		t.show(); 
	}
}
