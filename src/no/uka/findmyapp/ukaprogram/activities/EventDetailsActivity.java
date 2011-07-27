/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import no.uka.findmyapp.ukaprogram.utils.FavouriteUtils;
import android.content.Intent;
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
public class EventDetailsActivity extends PopupMenuActivity 
	implements OnClickListener, OnCheckedChangeListener
{
	
	/** The Constant debug. */
	private static final String debug = "EventsDetailsActivity";
	
	private RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	
	private Facebook mFacebook; 
	
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
				+ selectedEvent.getPlace());
		
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
			URL imageURL = new URL(ApplicationConstants.UKA_PATH + selectedEvent.getImage());
			Bitmap eventBitmap = BitmapFactory.decodeStream(
					imageURL.openConnection() .getInputStream()); 
			eventImage.setImageBitmap(eventBitmap);
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

	/* (non-Javadoc)
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
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
		setupFacebookDialogListener();
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(debug, "Inside onActivityResult");
        mFacebook.authorizeCallback(requestCode, resultCode, data); 
    }
}