/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import no.uka.findmyapp.ukaprogram.utils.FavouriteUtils;
import no.uka.findmyapp.ukaprogram.utils.NetworkUtils;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class EventDetailsActivity.
 */
public class EventDetailsActivity extends Activity implements OnClickListener, 
OnCheckedChangeListener 
{
	private static final int BITMAP_COMPRESSION_PERCENT = 40;
	/** The Constant debug. */
	private static final String debug = "EventsDetailsActivity";

	/** The m selected event. */
	private UkaEvent mSelectedEvent; 

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_details);

		Bundle bundle = getIntent().getExtras(); 
		Log.v(debug, "Bundle toString " + bundle.toString());

		if(bundle.getSerializable(ApplicationConstants.LIST_ITEM_CLICKED_SIGNAL) != null) {
			mSelectedEvent = (UkaEvent) bundle.getSerializable(
					ApplicationConstants.LIST_ITEM_CLICKED_SIGNAL);

			populateView(mSelectedEvent);
		}
		else{
			// Toast empty bundle exception
			showToast(getResources().getString(R.string.exception_emptyBundle));
		}
	}

	/**
	 * Populate view.
	 *
	 * @param selectedEvent the selected event
	 */
	public void populateView(UkaEvent selectedEvent){
		Log.v(debug, "populateView: selectedEvent " + selectedEvent.toString());
		

		Button friendsButton = (Button) findViewById(R.id.detailedEventFriendsOnEventButton);
		friendsButton.setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.detailedEventTitle);
		title.setText(selectedEvent.getTitle());

		TextView timeAndPlace = (TextView) findViewById(R.id.detailedEventTimeAndPlace);
		timeAndPlace.setText(	
				DateUtils.getWeekdayNameFromTimestamp(selectedEvent.getShowingTime()) + " " 
				+ DateUtils.getCustomDateFormatFromTimestamp("dd E MMM.", selectedEvent.getShowingTime()) + " " 
				+ DateUtils.getTimeFromTimestamp(selectedEvent.getShowingTime()) + ", " 
				+ selectedEvent.getPlace());

		
		/*Date today = new Date();
		long now = today.getTime();
		TextView timeAndPlace = (TextView) findViewById(R.id.detailedEventTimeAndPlace);
		timeAndPlace.setText(	
				DateUtils.getWeekdayNameFromTimestamp(now) + " " 
				+ DateUtils.getCustomDateFormatFromTimestamp("dd E MMM.", now) + " " 
				+ DateUtils.getTimeFromTimestamp(now) + ", " 
				+ selectedEvent.getPlace());
		*/
		
		TextView headerTitle = (TextView) findViewById(R.id.event_details_header_title);
		headerTitle.setText(selectedEvent.getTitle());

		TextView description = (TextView) findViewById(R.id.detailedEventDescription);
		description.setText(selectedEvent.getText());

		TextView ageLimit = (TextView) findViewById(R.id.detailedEventAgeLimit);
		ageLimit.setText(getResources().getString(R.string.eventDetailedActivity_agelimit)
				+ selectedEvent.getAgeLimit() 
				+ getResources().getString(R.string.eventDetailedActivity_year));

		TextView price = (TextView) findViewById(R.id.detailedEventPrice);

		ImageView eventImage = (ImageView) findViewById(R.id.event_details_picture);

		try {
			OutputStream os = null;
			URL imageURL = new URL(ApplicationConstants.UKA_PATH + selectedEvent.getImage());
			String filename = getFileNameFromPath(selectedEvent.getImage());
			if (filename.length() == 0 || !fileExist(filename) && NetworkUtils.isOnline(this.getApplicationContext())){
				eventImage.setImageResource(R.drawable.default_artist_image);
			}
			else
			{
				if (fileExist(filename)){
					eventImage.setImageBitmap(loadBitmap(filename));
				}
				else
				{
					Bitmap eventBitmap = BitmapFactory.decodeStream(imageURL.openConnection() .getInputStream()); 
					eventImage.setImageBitmap(eventBitmap);
					saveBitmap(eventBitmap, filename, imageURL);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		Log.v(debug, "Event URI image: " + ApplicationConstants.UKA_PATH + selectedEvent.getImage());
		if(selectedEvent.isFree()){
			price.setText(getResources().getString(R.string.eventDetailedActivity_free));
		}

		CheckBox favorites = (CheckBox) findViewById(R.id.event_details_favorites);
		favorites.setButtonDrawable(R.drawable.favorites_button);
		if(selectedEvent.isFavourite()) favorites.setChecked(true); 
		favorites.setOnCheckedChangeListener(this);	
	}
	private boolean fileExist(String filename){
		File file = new File(getExternalCacheDir(), filename);
		if (file != null) {
			Log.v(debug, "FILE EXISTS: " + file.exists());
			return file.exists();
		}
		Log.v(debug, "FILE EXISTS: return false"); 
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		FavouriteUtils fu = new FavouriteUtils(getApplicationContext());
		String info;

		if(isChecked) {
			fu.changeFavouriteFlag(mSelectedEvent.getId(), true);
			info = mSelectedEvent.getTitle() + getResources().getString(R.string.toast_isAddedAsFavourite);
		}
		else {			
			fu.changeFavouriteFlag(mSelectedEvent.getId(), false);
			info = mSelectedEvent.getTitle() + getResources().getString(R.string.toast_isRemovedAsFavourite);
		}

		showToast(info); 
	}

	/**
	 * Show toast.
	 *
	 * @param info the info
	 */
	private void showToast(String info) {
		Toast t = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT);
		t.show();
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v ) {
		// TODO Auto-generated method stub
	}
	public String getFileNameFromPath(String path){
		String test = path;
		String[] arr = test.split("/");
		return arr[arr.length-1];
	}
	public void saveBitmap(Bitmap bitmap, String filename, URL imageURL){
	    // Create a path where we will place our private file on external
	    // storage.
	    File file = new File(getExternalCacheDir(), filename);
	    Bitmap image = null;
	    try {
			image = BitmapFactory.decodeStream(imageURL.openConnection() .getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	    try {
	        OutputStream os = new FileOutputStream(file);
	        image.compress(Bitmap.CompressFormat.PNG, BITMAP_COMPRESSION_PERCENT, os);
	        os.close();
	    } catch (IOException e) {
	        // Unable to create file, likely because external storage is
	        // not currently mounted.
	        Log.w("ExternalStorage", "Error writing " + file, e);
	    }
	}	
	private Bitmap loadBitmap(String filename){
	    File fileUri = new File(getExternalCacheDir(), filename);
		Bitmap bm = BitmapFactory.decodeFile(fileUri.toString());
		return bm;
	}
	void deleteExternalStoragePrivateFile() {
		// Get path for the file on external storage.  If external
		// storage is not currently mounted this will fail.
		File file = new File(getExternalFilesDir(null), "DemoFile.jpg");
		if (file != null) {
			file.delete();
		}
	}
}
