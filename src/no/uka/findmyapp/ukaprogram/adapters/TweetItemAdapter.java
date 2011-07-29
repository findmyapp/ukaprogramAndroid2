/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.models.Tweet;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class TweetItemAdapter.
 */
public class TweetItemAdapter extends ArrayAdapter<Tweet> {
	
	/** The tweets. */
	private ArrayList<Tweet> tweets;
	
	/** The activity. */
	private Activity activity;
	
	/** The image manager. */
	public ImageManager imageManager;

	/**
	 * Instantiates a new tweet item adapter.
	 *
	 * @param a the a
	 * @param textViewResourceId the text view resource id
	 * @param tweets the tweets
	 */
	public TweetItemAdapter(Activity a, int textViewResourceId, ArrayList<Tweet> tweets) {
		super(a, textViewResourceId, tweets);
		this.tweets = tweets;
		activity = a;

		imageManager = 
			new ImageManager(activity.getApplicationContext());
	}

	/**
	 * The Class ViewHolder.
	 */
	public static class ViewHolder{
		
		/** The username. */
		public TextView username;
		
		/** The message. */
		public TextView message;
		
		/** The image. */
		public ImageView image;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
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
		//	imageManager.displayImage(tweet.image_url, activity, holder.image);
		}
		return v;
	}
	
	/**
	 * The Class ImageManager.
	 */
	public class ImageManager {
		  
  		/** The image map. */
  		private HashMap<String, Bitmap> imageMap = new HashMap<String, Bitmap>();
		  
  		/** The cache dir. */
  		private File cacheDir;

		  /**
  		 * Instantiates a new image manager.
  		 *
  		 * @param context the context
  		 */
  		public ImageManager(Context context) {
		    // Make background thread low priority, to avoid affecting UI performance
			 Thread imageLoaderThread = new Thread();
		    imageLoaderThread.setPriority(Thread.NORM_PRIORITY-1);

		    // Find the dir to save cached images
		    String sdState = android.os.Environment.getExternalStorageState();
		    if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
		      File sdDir = android.os.Environment.getExternalStorageDirectory();   
		      cacheDir = new File(sdDir,"data/twitter");
		    }
		    else
		      cacheDir = context.getCacheDir();

		    if(!cacheDir.exists())
		      cacheDir.mkdirs();
		  }
		}
}
