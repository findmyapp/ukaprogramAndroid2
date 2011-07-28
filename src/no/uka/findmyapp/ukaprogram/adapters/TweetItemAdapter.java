package no.uka.findmyapp.ukaprogram.adapters;

import java.util.ArrayList;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.models.Tweet;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetItemAdapter extends ArrayAdapter<Tweet> {
	private ArrayList<Tweet> tweets;
	private Activity activity;
//	public ImageManager imageManager;

	public TweetItemAdapter(Activity a, int textViewResourceId, ArrayList<Tweet> tweets) {
		super(a, textViewResourceId, tweets);
		this.tweets = tweets;
		activity = a;

//		imageManager = 
//			new ImageManager(activity.getApplicationContext());
	}

	public static class ViewHolder{
		public TextView username;
		public TextView message;
		public ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {		
			LayoutInflater vi = 
				(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.tweetitem, null);
			holder = new ViewHolder();
			holder.username = (TextView) v.findViewById(R.id.tweetitem_username);
			holder.message = (TextView) v.findViewById(R.id.tweetitem_message);
			holder.image = (ImageView) v.findViewById(R.id.tweetitem_avatar);
			v.setTag(holder);
		}
		else
			holder=(ViewHolder)v.getTag();

		final Tweet tweet = tweets.get(position);
		if (tweet != null) {
			holder.username.setText(tweet.username);
			holder.message.setText(tweet.message);
			holder.image.setTag(tweet.image_url);
//			imageManager.displayImage(tweet.image_url, activity, holder.image);
		}
		return v;
	}
	
	
}