package no.uka.findmyapp.ukeprogram.restlibrary;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RestTask extends AsyncTask<HttpRequestBase, Void, Void> {
	private static final String TAG = "RestTask";
	private String response;
	private String failedMsg;
	private HttpRequestBase request;
	private int statusCode = -1;
	protected Context mContext;
	
	public static final int GET = 1;
	public static final int POST = 2;
	public static final int PUT = 3;
	public static final int DELETE = 4;
	
	public static final String USER_TOKEN = "fma-user-token";
	public static final String FMA_SESSION = "fma-session";
	
	private static String getParamString(List<NameValuePair> params) throws UnsupportedEncodingException {
		String paramString = "";
		if (params.size() > 0) {
			paramString = "?"+params.get(0).getName()+"="+URLEncoder.encode(params.get(0).getValue(), "UTF-8");
			for (int i = 1; i < params.size(); i++) {
				paramString += "&"+params.get(i).getName()+"="+URLEncoder.encode(params.get(i).getValue(), "UTF-8");
			}
		}
		return paramString;
	}
	
	private static HttpRequestBase setupRequest(String URLstring, int method, HttpEntity entity) {
		switch(method) {
			case POST:
				HttpPost post = new HttpPost(URLstring);
				post.setEntity(entity);
				return post;
			default:
				HttpPut put = new HttpPut(URLstring);
				put.setEntity(entity);
				return put;
		}
	}
	
	private static void setHeaders(HttpRequestBase request, boolean secured) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		request.setHeader("Accept", "application/json");
		
		String appkey = "74d23f269be567ea3a53fd6bae26af50c3dbb8c4";
		String appsecret = "c64f47c5f16639f2547e46593606c50225b35b7f";
		
		if (secured) {
			OAuthConsumer consumer = new CommonsHttpOAuthConsumer(appkey, appsecret);
			consumer.sign(request);
		}
	}
	private static HttpRequestBase setupRequest(String URLstring, int method) {
		switch(method){
		case GET:
			return new HttpGet(URLstring);
		case POST:
			return new HttpPost(URLstring);
		case PUT:
			return new HttpPut(URLstring);
		default:
			return new HttpDelete(URLstring);
		}
	}
	private static HttpRequestBase setupRequest(String URLstring, int method, List<NameValuePair> params) throws UnsupportedEncodingException {
		switch(method) {
			case GET:
				return new HttpGet(URLstring+getParamString((List<NameValuePair>)params));
			case DELETE:
				return new HttpDelete(URLstring+getParamString((List<NameValuePair>)params));
			default:
				return setupRequest(URLstring, method, new UrlEncodedFormEntity(params, "UTF-8"));
		}
	}
	
	//START LOGIC
	
	public RestTask(Context ctx, String URLstring, int method, boolean secured) {
		Log.v(TAG, "init "+method+" to "+URLstring);
		mContext = ctx;
		try {
			request = setupRequest(URLstring, method);
			setHeaders(request, secured);
		} catch (Exception e) {
			Log.w(TAG, "init ", e);
			failedMsg = e.getMessage();
		}
	}
	
	public RestTask(Context ctx, String URLstring, int method, List<NameValuePair> params, boolean secured) {
		Log.v(TAG, "init "+method+" to "+URLstring);
		mContext = ctx;
		try {
			request = setupRequest(URLstring, method, params);
			setHeaders(request, secured);
		} catch (Exception e) {
			Log.w(TAG, "init ", e);
			failedMsg = e.getMessage();
		}
	}
	
	
	@Override
	protected Void doInBackground(HttpRequestBase... req) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(request);
			InputStream responseStream = httpResponse.getEntity().getContent();
			response = new Scanner(responseStream, "UTF-8").useDelimiter("\\A").next();
			statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				failedMsg = "Status code recieved: "+statusCode;
				Log.e(TAG, "Status code recieved: "+statusCode+" data: "+response);
			}
			Log.v(TAG, request.getURI()+": "+response);
		} catch (Exception e) {
			Log.w(TAG, "exec ", e);
			failedMsg = e.getMessage();
			statusCode = -1;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if (statusCode == 200) {
			restComplete(response);
		} else {
			restFailed(failedMsg, statusCode);
		}
	}
	public void restComplete(String response) {
		//Override to handle callbacks
	}
	public void restFailed(String failmsg, int code) {
		//Override to handle callbacks
	}
}
