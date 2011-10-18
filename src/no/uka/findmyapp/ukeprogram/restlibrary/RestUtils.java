package no.uka.findmyapp.ukeprogram.restlibrary;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class RestUtils {
	
	public static boolean resetToken(Context ctx)
	{
		Editor editor = ctx.getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE).edit();
        editor.putString(RestTask.USER_TOKEN, "");
        return editor.commit();		
	}
	
	public static boolean hasToken(Context ctx)
	{
        SharedPreferences savedSession = ctx.getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE);
        String token = savedSession.getString(RestTask.USER_TOKEN, "");
        if (token.equals("")) {
        	return false;
        } else {
        	return true;
        }
	}
	
    public static void authFindMyApp(Context ctx, String facebookToken)
    {
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("facebookToken", facebookToken));
		RestTask task = new RestTask(ctx, "http://findmyapp.net/findmyapp/auth/login", RestTask.GET, params, true) {
			
			@Override
		    public void restComplete(String response) { 
				
				Editor editor = mContext.getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE).edit();
		        String token = response.substring(1, response.length()-1);
		        editor.putString(RestTask.USER_TOKEN, token);
		        editor.commit();
		        Log.d("RestTask", "Got UserToken. Stored: " + token);
		    }
			
		    @Override 
		    public void restFailed(String failMsg, int code) { 
		    	Log.d("RestTask", "Failure: " + failMsg);
				Editor editor = mContext.getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE).edit();
		        editor.putString(RestTask.USER_TOKEN, "");
		        editor.commit();
		    }
		};
		
		task.execute();
    }

}
