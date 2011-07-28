package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.TweetItemAdapter;
import no.uka.findmyapp.ukaprogram.models.Tweet;
import no.uka.findmyapp.ukaprogram.utils.TweetReader;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class NewsActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet_list);
		ArrayList<Tweet> tweets = TweetReader.getTweets("UKA", 1);



		ListView listView = (ListView) findViewById(R.id.tweet_list_listview);
		
		listView.setAdapter(new TweetItemAdapter(this, R.layout.tweet_list, tweets));
	}
}
