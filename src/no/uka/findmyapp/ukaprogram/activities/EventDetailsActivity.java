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

import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import no.uka.findmyapp.ukaprogram.utils.FavouriteUtils;
import no.uka.findmyapp.ukaprogram.utils.Toaster;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

// TODO: Auto-generated Javadoc
/**
 * The Class EventDetailsActivity.
 */
public class EventDetailsActivity extends PopupMenuActivity 
	implements OnClickListener, OnCheckedChangeListener
{	
	
	/** The Constant BITMAP_COMPRESSION_PERCENT. */
	private static final int BITMAP_COMPRESSION_PERCENT = 40;
	
	/** The Constant debug. */
	private static final String debug = "EventsDetailsActivity";

	/** The service helper. */
	private RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	
	/** The m facebook. */
	private Facebook mFacebook; 
	
	/** The m selected event. */
	private UkaEvent mSelectedEvent; 
	
	/** The image url. */
	private URL imageURL;
	
	/** The event bitmap. */
	private Bitmap eventBitmap;
	
	/** The filename. */
	private String filename;
	
	/** The event image. */
	private ImageView eventImage;
	
	/** The image progress bar. */
	private ProgressBar imageProgressBar;
	
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
			Toaster.shoutLong(getApplicationContext(),
					getResources().getString(R.string.exception_emptyBundle));
		}
	}

	/**
	 * Setup facebook dialog listener.
	 */
	private void setupFacebookDialogListener() {
		mFacebook = new Facebook(ApplicationConstants.UKA_PROGRAM_FACEBOOK_ID);
        mFacebook.authorize(this, new DialogListener() {
            @Override
            public void onComplete(Bundle values) {
            	//serviceHelper.facebookGetFriends(mFacebook.getAccessToken());
            }

            @Override
            public void onFacebookError(FacebookError error) {}

            @Override
            public void onError(DialogError e) {}

            @Override
            public void onCancel() {}
        });
	}
	
	
	/**
	 * Populate view.
	 *
	 * @param selectedEvent the selected event
	 */
	private void populateView(UkaEvent selectedEvent){
		Log.v(debug, "populateView: selectedEvent " + selectedEvent.toString());
		

		Button friendsButton = (Button) findViewById(R.id.detailedEventFriendsOnEventButton);
		friendsButton.setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.detailedEventTitle);
		title.setText(selectedEvent.getTitle());

		TextView timeAndPlace = (TextView) findViewById(R.id.detailedEventTimeAndPlace);
		timeAndPlace.setText(	
				DateUtils.getWeekdayNameFromTimestamp(selectedEvent.getShowingTime()) + " " 
				+ DateUtils.getCustomDateFormatFromTimestamp(
						"dd E MMM.", selectedEvent.getShowingTime()) + " " 
				+ DateUtils.getTimeFromTimestamp(selectedEvent.getShowingTime()) + ", " 
				+ selectedEvent.getPlaceString());

		
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
		
		TextView lead = (TextView) findViewById(R.id.detailedEventLead);
		lead.setText(selectedEvent.getLead());
		
		TextView description = (TextView) findViewById(R.id.detailedEventDescription);
		description.setText(selectedEvent.getText());

		TextView ageLimit = (TextView) findViewById(R.id.detailedEventAgeLimit);
		ageLimit.setText(getResources().getString(R.string.eventDetailedActivity_agelimit)
				+ selectedEvent.getAgeLimit() 
				+ getResources().getString(R.string.eventDetailedActivity_year));

		TextView price = (TextView) findViewById(R.id.detailedEventPrice);
		imageProgressBar = (ProgressBar) findViewById(R.id.event_details_image_progressbar);
		downloadEventPicture(selectedEvent);
		
		setHeaderImage(selectedEvent);
	
		if(selectedEvent.isFree()){
			price.setText(getResources().getString(R.string.eventDetailedActivity_free));
		}

		CheckBox favorites = (CheckBox) findViewById(R.id.event_details_favorites);
		favorites.setButtonDrawable(R.drawable.favorites_button);
		if(selectedEvent.isFavourite()) favorites.setChecked(true); 
		favorites.setOnCheckedChangeListener(this);	
	}
	
	/**
	 * Download event picture.
	 *
	 * @param selectedEvent the selected event
	 */
	private void downloadEventPicture(UkaEvent selectedEvent) {
		eventImage = (ImageView) findViewById(R.id.event_details_picture);
		try {
			OutputStream os = null;
			imageURL = new URL(ApplicationConstants.UKA_PATH + selectedEvent.getImage());
			filename = getFileNameFromPath(selectedEvent.getImage());
			if (filename.length() == 0){
				Log.v(debug, "Finner ikke bilde");
				eventImage.setImageResource(R.drawable.eventplaceholder);
			}
			else {
				if (fileExist(filename)){
					eventImage.setImageBitmap(loadBitmap(filename));
				}
				else {
					imageProgressBar.setVisibility(ProgressBar.VISIBLE);
					new Thread(new Runnable() {
						public void run() {
							try {
								eventBitmap = BitmapFactory.decodeStream(imageURL.openConnection() .getInputStream());
								saveBitmap(eventBitmap, filename, imageURL);
								
								runOnUiThread(new Runnable() {
									public void run() {
										eventImage.setImageBitmap(eventBitmap);
										imageProgressBar.setVisibility(ProgressBar.INVISIBLE);
									}
								});
							} 
							catch (IOException e) {
								e.printStackTrace();
							} 
						}
					}).start();
				}
			}
		} 
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v(debug, "Event URI image: " + ApplicationConstants.UKA_PATH + selectedEvent.getImage());
		}
	}
	
	/**
	 * File exist.
	 *
	 * @param filename the filename
	 * @return true, if successful
	 */
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
			info = mSelectedEvent.getTitle() + getResources().getString(
					R.string.toast_isAddedAsFavourite);
		}
		else {			
			fu.changeFavouriteFlag(mSelectedEvent.getId(), false);
			info = mSelectedEvent.getTitle() + getResources().getString(
					R.string.toast_isRemovedAsFavourite);
		}

		Toaster.shoutLong(getApplicationContext(), info);
		}

	/**
	 * Gets the file name from path.
	 *
	 * @param path the path
	 * @return the file name from path
	 */
	public String getFileNameFromPath(String path){
		String test = path;
		String[] arr = test.split("/");
		return arr[arr.length-1];
	}
	
	/**
	 * Save bitmap.
	 *
	 * @param bitmap the bitmap
	 * @param filename the filename
	 * @param imageURL the image url
	 */
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
	
	/**
	 * Load bitmap.
	 *
	 * @param filename the filename
	 * @return the bitmap
	 */
	private Bitmap loadBitmap(String filename){
	    File fileUri = new File(getExternalCacheDir(), filename);
		Bitmap bm = BitmapFactory.decodeFile(fileUri.toString());
		return bm;
	}
	
	/**
	 * Delete external storage private file.
	 */
	void deleteExternalStoragePrivateFile() {
		// Get path for the file on external storage.  If external
		// storage is not currently mounted this will fail.
		File file = new File(getExternalFilesDir(null), "DemoFile.jpg");
		if (file != null) {
			file.delete();
		}
	}
	
	/**
	 * Sets the header image.
	 *
	 * @param event the new header image
	 */
	private void setHeaderImage(UkaEvent event){
		ImageView img = (ImageView) findViewById(R.id.event_details_category_header);
		Log.v(debug, ApplicationConstants.CATEGORY_REVUE + " = " + event.getEventType());
		if (isConcert(event)){
			Log.v(debug, "inside if");
			img.setImageResource(R.drawable.headerconcert);
		}
		else if (isParty(event)){
			img.setImageResource(R.drawable.headerparty);
		}
		else if (isRevue(event)){
			img.setImageResource(R.drawable.headerrevu);
		}
		else if (isLecture(event)){
			img.setImageResource(R.drawable.headerlecture);
		}
	}

	/**
	 * Checks if is concert.
	 *
	 * @param event the event
	 * @return true, if is concert
	 */
	private boolean isConcert(UkaEvent event) {
		return ("'"+event.getEventType()+"'").equals(ApplicationConstants.CATEGORY_CONCERT);
	}

	/**
	 * Checks if is party.
	 *
	 * @param event the event
	 * @return true, if is party
	 */
	private boolean isParty(UkaEvent event) {
		return ("'"+event.getEventType()+"'").equals(ApplicationConstants.CATEGORY_PARTY);
	}

	/**
	 * Checks if is revue.
	 *
	 * @param event the event
	 * @return true, if is revue
	 */
	private boolean isRevue(UkaEvent event) {
		return ("'"+event.getEventType()+"'").equals(ApplicationConstants.CATEGORY_REVUE);
	}

	/**
	 * Checks if is lecture.
	 *
	 * @param event the event
	 * @return true, if is lecture
	 */
	private boolean isLecture(UkaEvent event) {
		return ("'"+event.getEventType()+"'").equalsIgnoreCase(ApplicationConstants.CATEGORY_LECTURE);
	}
	

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v ) {
		setupFacebookDialogListener(); 
	}
}
