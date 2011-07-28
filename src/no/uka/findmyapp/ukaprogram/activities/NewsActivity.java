package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.TweetItemAdapter;
import no.uka.findmyapp.ukaprogram.models.Tweet;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class NewsActivity extends Activity {

	public Bitmap placeholder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet_list);
		ArrayList<Tweet> tweets = null;
		try {
			tweets = getTweets("UKA", 1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ListView listView = (ListView) findViewById(R.id.tweet_list_listview);

		listView.setAdapter(new TweetItemAdapter(this, R.layout.tweet_list,
				tweets));
	}

	public ArrayList<Tweet> getTweets(String searchTerm, int page)
			throws JSONException {
		String noLang = "no";
		String enLang = "en";
		String seLang = "se";
		String searchUrl = "http://search.twitter.com/search.json?tag=UKA11";// + "&rpp=100&page=" + page + "&lang=" + noLang;

		ArrayList<Tweet> tweets = new ArrayList<Tweet>();

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(searchUrl);	

		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		String responseBody = null;
		try {
			responseBody = client.execute(get, responseHandler);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(responseBody);
			Log.e("Object: obj", ""+obj);
			jsonObject = (JSONObject) obj;

		} catch (Exception ex) {
			Log.v("TEST - parsing to json", "Exception: " + ex.getMessage());
		}

		JSONArray arr = null;

		try {
			Object j = jsonObject.get("results");
			Log.e("jsonObject: j", ""+j);
			arr = (JSONArray) j;
		} catch (Exception ex) {
			Log.v("TEST - parsing to array", "Exception: " + ex.getMessage());
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