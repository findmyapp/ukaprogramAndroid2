/**
 * 
 */
package no.uka.findmyapp.ukaprogram.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author torstein.barkve
 *
 */
public class Toaster
{
	public static void shoutLong(Context context, String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
		t.show(); 
	}
	
	public static void  shoutShort(Context context, String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		t.show(); 
	}
}
