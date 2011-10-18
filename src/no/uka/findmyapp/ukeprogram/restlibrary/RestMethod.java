/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukeprogram.restlibrary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import no.uka.findmyapp.ukeprogram.models.Credentials;
import no.uka.findmyapp.ukeprogram.utils.Constants;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;




// TODO: Auto-generated Javadoc
/**
 * The Class RestMethod.
 */
public class RestMethod 
{
	
	/** The Constant debug. */
	private final static String debug = "RestMethod";
	
	/** The HTT p_ statu s_ ok. */
	private final int HTTP_STATUS_OK = 200;
	
	/** The HTT p_ statu s_ no t_ modified. */
	private final int HTTP_STATUS_NOT_MODIFIED = 304;
	
	/** The HTT p_ statu s_ ba d_ request. */
	private final int HTTP_STATUS_BAD_REQUEST = 400; 
	
	/** The HTT p_ statu s_ unauthorized. */
	private final int HTTP_STATUS_UNAUTHORIZED = 401; 
	
	/** The HTT p_ statu s_ forbidden. */
	private final int HTTP_STATUS_FORBIDDEN = 403; 
	
	/** The HTT p_ statu s_ no t_ found. */
	private final int HTTP_STATUS_NOT_FOUND = 404; 
	
	/** The HTT p_ statu s_ timeout. */
	private final int HTTP_STATUS_TIMEOUT = 408;
	
	/** The HTT p_ statu s_ interna l_ serve r_ error. */
	private final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500; 
	
	/** The UNHANDLE d_ statu s_ code. */
	private final int UNHANDLED_STATUS_CODE = 666; 
	
	/** The Constant REQUEST_TOKEN_ENDPOINT_URL. */
	private static final String REQUEST_TOKEN_ENDPOINT_URL = "http://findmyapp.net/findmyapp/oauth/request_token";
	
	/** The Constant ACCESS_TOKEN_ENDPOINT_URL. */
	private static final String ACCESS_TOKEN_ENDPOINT_URL = "http://findmyapp.net/findmyapp/oauth/access_token";
	
	/** The Constant AUTHORIZE_WEBSITE_URL. */
	private static final String AUTHORIZE_WEBSITE_URL = "http://findmyapp.net/findmyapp/oauth/authorize";

	/** The Constant sDataFormat. */
	private static final String sDataFormat = "application/json";
	
	/** The Constant CHARSET. */
	private static final String CHARSET = "UTF-8"; 
	
	/** The m useragent. */
	private String mUseragent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0"; 
	
	
	/** The m stream buffer. */
	private static byte[] mStreamBuffer = new byte[512];
	
	/** The m uri. */
	private URI mUri; 
	
	/** The m client. */
	private HttpClient mClient; 
	
	/** The m provider. */
	private OAuthProvider mProvider;
	
	/** The m consumer. */
	private OAuthConsumer mConsumer;
	
	/** The m o auth key. */
	private String mOAuthKey; 
	
	/** The m o auth secret. */
	private String mOAuthSecret; 
	
	/**
	 * Instantiates a new rest method.
	 *
	 * @param credentials the credentials
	 */
	public RestMethod(Credentials credentials) {

		mProvider = new CommonsHttpOAuthProvider(
                REQUEST_TOKEN_ENDPOINT_URL, 
                ACCESS_TOKEN_ENDPOINT_URL,
                AUTHORIZE_WEBSITE_URL);

        mConsumer = new CommonsHttpOAuthConsumer(credentials.getKey(), credentials.getSecret());      
        if (Constants.DEBUG) Log.v(debug, "API key: " + credentials.getKey() + " API-secret: " + credentials.getSecret());
	}
	
	/**
	 * Instantiates a new rest method.
	 *
	 * @param uri the uri
	 */
	public RestMethod() {
		
	}

	/**
	 * Gets the useragent.
	 *
	 * @return the useragent
	 */
	public String getUseragent() {
		return mUseragent;
	}

	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public URI getUri() {
		return mUri;
	}

	/**
	 * Sets the uri.
	 *
	 * @param uri the new uri
	 */
	public void setUri(URI uri) {
		mUri = uri;
	}

	/**
	 * Sets the useragent.
	 *
	 * @param useragent the new useragent
	 */
	public void setUseragent(String useragent) {
		mUseragent = useragent;
	}

	/**
	 * Gets the.
	 *
	 * @return the string
	 * @throws HTTPStatusException the hTTP status exception
	 */
	public String get() throws HTTPStatusException {
		HttpGet request = new HttpGet(mUri);
		if (Constants.DEBUG) Log.v(debug, "get(). mUri: " + mUri);
		HttpRequestBase requestBase = setRequestHeaders(request, mUseragent);
		
		return executeGet(requestBase);
	}
	
	/**
	 * Post.
	 *
	 * @param data the data
	 * @return the string
	 * @throws HTTPStatusException the hTTP status exception
	 */
	public String post(String data) throws HTTPStatusException {
		HttpPost post = new HttpPost(this.mUri);
		setPostHeaders(post, mUseragent);
		
		return executePost(post, data); 
	}
	
	/**
	 * Execute get.
	 *
	 * @param request the request
	 * @return the string
	 * @throws HTTPStatusException the hTTP status exception
	 */
	private String executeGet(HttpRequestBase request) throws HTTPStatusException {
			
		this.mClient = new DefaultHttpClient();
	
			// TODO: Hvorfor m� den signes n�r man ikke trenger � authes?

			
			HttpResponse response;
			
			try {
				response = this.mClient.execute(request);
			} catch (ClientProtocolException e) {
				if (Constants.DEBUG) Log.e(debug, "ClientProtocolExc: " + e.getMessage());
				return ""; 
			} catch (IOException e) {
				if (Constants.DEBUG) Log.e(debug, "IOExc: " + e.getMessage());
				return ""; 
			}
			
			// Check if server response is valid
			StatusLine status = response.getStatusLine();
			
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				this.throwHttpStatusException(status.getStatusCode());
			} 
	
			// Pull content stream from response
			HttpEntity entity = response.getEntity();
			InputStream inputStream;
			try {
				inputStream = entity.getContent();
			} catch (IllegalStateException e) {
				if (Constants.DEBUG) Log.e(debug, e.getMessage());
				return ""; 
			} catch (IOException e) {
				if (Constants.DEBUG) Log.e(debug, e.getMessage());
				return ""; 
			}
	
			ByteArrayOutputStream content = new ByteArrayOutputStream();
	
			// Read response into a buffered stream
			int readBytes = 0;
			try {
				while ((readBytes = inputStream.read(mStreamBuffer)) != -1) {
					content.write(mStreamBuffer, 0, readBytes);
				}
			} catch (IOException e) {
				if (Constants.DEBUG) Log.e(debug, e.getMessage());
				return ""; 
			}
	
			// Return result from buffered stream
			return new String(content.toByteArray());
	}
	
	/**
	 * Execute post.
	 *
	 * @param post the post
	 * @param data the data
	 * @return the string
	 * @throws HTTPStatusException the hTTP status exception
	 */
	private String executePost(HttpPost post, String data) throws HTTPStatusException {
		try{
			StringEntity entity = new StringEntity(data, CHARSET);
	    	post.setEntity(entity); 
	    	
	    	this.mClient = new DefaultHttpClient();
			try {
				mConsumer.sign(post);
			} catch (OAuthMessageSignerException e) {
				if (Constants.DEBUG) Log.e(debug, e.getMessage());
				return ""; 
			} catch (OAuthExpectationFailedException e) {
				if (Constants.DEBUG) Log.e(debug, e.getMessage());
				return ""; 
			} catch (OAuthCommunicationException e) {
				if (Constants.DEBUG) Log.e(debug, e.getMessage());
				return ""; 
			}
			
			if (Constants.DEBUG) Log.v(debug, post.getURI().toString());
	    	HttpResponse response = mClient.execute(post);

			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				this.throwHttpStatusException(status.getStatusCode());
			} 
			
            if (response != null) {
    			response.getEntity();
    			InputStream inputStream = entity.getContent();
    	
    			ByteArrayOutputStream content = new ByteArrayOutputStream();
    	
    			// Read response into a buffered stream
    			int readBytes = 0;
    			while ((readBytes = inputStream.read(mStreamBuffer)) != -1) {
    				content.write(mStreamBuffer, 0, readBytes);
    			}
    	
    			// Return result from buffered stream
    			return new String(content.toByteArray());
            }
		}
		catch (UnsupportedEncodingException e) {
			if (Constants.DEBUG) Log.e(debug, "Unsupported encoding: " + e.getMessage());
			return ""; 
		}
		catch (IOException e) {
			if (Constants.DEBUG) Log.e(debug, "IOException: " + e.getMessage());
			return ""; 
		}
		catch (HTTPStatusException e) {
			throw e; 
		}
		return null;
	}
	
	/**
	 * Sets the request headers.
	 *
	 * @param request the request
	 * @param useragent the useragent
	 * @return the http request base
	 */
	private HttpRequestBase setRequestHeaders(HttpRequestBase request, String useragent) {
		request.setHeader("Accept", sDataFormat);
		request.setHeader("Content-type", sDataFormat);
		request.setHeader("User-Agent", useragent);
		
		return request; 
	}
	
	/**
	 * Sets the post headers.
	 *
	 * @param request the request
	 * @param useragent the useragent
	 * @return the http request base
	 */
	private HttpRequestBase setPostHeaders(HttpRequestBase request, String useragent) {
		request.setHeader("User-Agent", useragent);
		request.setHeader("Accept", sDataFormat);
		request.setHeader("Content-type", sDataFormat);
		
		return request; 
	}

	/**
	 * Throw http status exception.
	 *
	 * @param statusCode the status code
	 * @throws HTTPStatusException the hTTP status exception
	 */
	private void throwHttpStatusException(int statusCode) throws HTTPStatusException{
		switch (statusCode) {
			case HTTP_STATUS_BAD_REQUEST:
				throw new HTTPStatusException(HTTP_STATUS_BAD_REQUEST, "400 Bad Request (HTTP/1.1 - RFC 2616)");
			case HTTP_STATUS_UNAUTHORIZED:
				throw new HTTPStatusException(HTTP_STATUS_UNAUTHORIZED, "401 Unauthorized (HTTP/1.0 - RFC 1945)");
			case HTTP_STATUS_FORBIDDEN:
				throw new HTTPStatusException(HTTP_STATUS_FORBIDDEN, "401 Unauthorized (HTTP/1.0 - RFC 1945)");
			case HTTP_STATUS_NOT_FOUND:
				throw new HTTPStatusException(HTTP_STATUS_NOT_FOUND, "404 Not Found (HTTP/1.0 - RFC 1945)");
			case HTTP_STATUS_TIMEOUT:
				throw new HTTPStatusException(HTTP_STATUS_TIMEOUT, "408 Request Timeout (HTTP/1.1 - RFC 2616)");
			case HTTP_STATUS_INTERNAL_SERVER_ERROR:
				throw new HTTPStatusException(HTTP_STATUS_INTERNAL_SERVER_ERROR, "500 Server Error (HTTP/1.0 - RFC 1945)");
			default:
				throw new HTTPStatusException(UNHANDLED_STATUS_CODE, "Unhandled status code: " + statusCode);
		}
	}
	
	/**
	 * The Class HTTPStatusException.
	 */
	public static class HTTPStatusException extends Exception {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 4485462910566178510L;
		
		/** The status code. */
		private int statusCode; 
		
		/**
		 * Instantiates a new hTTP status exception.
		 *
		 * @param statusCode the status code
		 * @param errorMessage the error message
		 */
		public HTTPStatusException(int statusCode, String errorMessage) {
			super(errorMessage);
			this.statusCode = statusCode; 
		}
		
		/**
		 * Gets the http status code.
		 *
		 * @return the http status code
		 */
		public int getHttpStatusCode() {
			return this.statusCode; 
		}
	}
}

