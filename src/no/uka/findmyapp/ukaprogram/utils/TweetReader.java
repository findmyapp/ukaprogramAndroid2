/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import java.util.ArrayList;

import no.uka.findmyapp.ukaprogram.models.Tweet;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.util.Log;

/**
 * The Class TweetReader.
 */
public class TweetReader {
	
	/**
	 * Gets the tweets.
	 *
	 * @param searchTerm the search term
	 * @param page the page
	 * @return the tweets
	 */
	public static ArrayList<Tweet> getTweets(String searchTerm, int page) {
		  String searchUrl =
		        "http://search.twitter.com/search.json?q=@"
		        + searchTerm + "&rpp=20&page=" + page;
		   
		  ArrayList<Tweet> tweets =
		        new ArrayList<Tweet>();
		   
		  HttpClient client = new  DefaultHttpClient();
		  HttpGet get = new HttpGet(searchUrl);
		       
		  ResponseHandler<String> responseHandler =
		        new BasicResponseHandler();

		  String responseBody = null;
		  try {
		    responseBody = client.execute(get, responseHandler);
		  } catch(Exception ex) {
		    ex.printStackTrace();
		  }

		  JSONObject jsonObject = null;
		  JSONParser parser=new JSONParser();
		   
		  try {
		    Object obj = parser.parse(responseBody);
		    jsonObject=(JSONObject)obj;
		  }catch(Exception ex){
		    Log.v("TEST","Exception: " + ex.getMessage());
		  }
		   
		  JSONArray arr = null;
		   
		  try {
		    Object j = jsonObject.get("results");
		    arr = (JSONArray)j;
		  } catch(Exception ex){
		    Log.v("TEST","Exception: " + ex.getMessage());
		  }

		  for(Object t : arr) {
		    Tweet tweet = new Tweet(
		      ((JSONObject)t).get("from_user").toString(),
		      ((JSONObject)t).get("text").toString(),
		      ((JSONObject)t).get("profile_image_url").toString()
		    );
		    tweets.add(tweet);
		  }
		   
		  return tweets;
		}


}
