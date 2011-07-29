/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;
import java.util.Date;

import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.Location;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.activities.lists.LocationListActivity;
import no.uka.findmyapp.ukaprogram.contstants.LocationConstants;
import no.uka.findmyapp.ukaprogram.mapper.UkaEventMapper;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class PlacesActivity.
 */
public class PlacesActivity extends Activity implements OnClickListener{

	/** The service helper. */
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 

	/** The Constant debug. */
	private static final String debug = "PlacesActivity";

	/** The Constant PLACE. */
	public static final String PLACE = "Place";

	/** The bundle. */
	private Bundle bundle;
	
	/** The event cursor. */
	private Cursor eventCursor;

	/** The location. */
	private Location location;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.places);

		bundle = getIntent().getExtras();
		Log.v(debug, "Bundle = " + (bundle.toString()) );
		location = (Location) bundle.getSerializable(LocationListActivity.SELECTED_LOCATION);
		Log.v(debug, location.toString());
		initButtons();

		setupPlaceSpecificViews(location);

		setReportedParamenters(getDancing(), getFlirt(), getMood(), getChat());
		
	}

	/**
	 * Inits the buttons.
	 */
	private void initButtons() {
		Button shareComment = (Button) findViewById(R.id.places_share);
		shareComment.setOnClickListener(this);

		Button report = (Button) findViewById(R.id.places_report);
		report.setOnClickListener(this);
	}

	/**
	 * Gets the reported comment.
	 *
	 * @return the reported comment
	 */
	private String getReportedComment() {
		String reportedComment;
		EditText comment = (EditText) findViewById(R.id.places_comment);
		reportedComment =  comment.getText().toString();
		return reportedComment;
	}



	/**
	 * Sets the reported paramenters.
	 *
	 * @param dancing the dancing
	 * @param flirt the flirt
	 * @param mood the mood
	 * @param chat the chat
	 */
	private void setReportedParamenters(float dancing, float flirt,
			float mood, float chat) {
		RatingBar pbSjekking = (RatingBar) findViewById(R.id.places_progressbar_sjekking);
		pbSjekking.setIsIndicator(true);
		pbSjekking.setRating(flirt);

		RatingBar pbDansing = (RatingBar) findViewById(R.id.places_progressbar_dansing);
		pbDansing.setIsIndicator(true);
		pbDansing.setRating(dancing);

		RatingBar pbPrating = (RatingBar) findViewById(R.id.places_progressbar_prating);
		pbPrating.setIsIndicator(true);
		pbPrating.setRating(chat);


		RatingBar pbStemning = (RatingBar) findViewById(R.id.places_progressbar_stemning);
		pbStemning.setIsIndicator(true);
		pbStemning.setRating(mood);

	}

	/* 
	 * performs action when buttons are clicked
	 */
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.places_report:
			sendReport(getReport());
			break;

		case R.id.places_share:
			sendComment(getReportedComment());
			break;

		default:
			break;
		}
	}

	/**
	 * Sets the up place specific views.
	 *
	 * @param location the new up place specific views
	 */
	private void setupPlaceSpecificViews(Location location){
		ImageView map = (ImageView) findViewById(R.id.places_map);
		TextView place_name = (TextView) findViewById(R.id.places_name);
		TextView next_event = (TextView) findViewById(R.id.places_playing_next);
		TextView prev_event = (TextView) findViewById(R.id.places_last_played);
		Log.v(debug, location.getLocationName());

		place_name.setText(location.getLocationName());

		setMap(map, location);
		setNextEvent(next_event, location);
		setPrevEvent(prev_event, location);
		//TODO: add method to inflate views
	}


	/**
	 * Sets the map.
	 *
	 * @param map the map
	 * @param location the location
	 */
	void setMap(ImageView map, Location location){	

		map.setImageResource(R.drawable.mapplaceholder);
		Log.v(debug, "Is " + location.getLocationStringId() + "  =  " + LocationConstants.EDGAR);
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.BODEGAEN)){
			map.setImageResource(R.drawable.mapbodegaen);
		}
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.DODENSDAL)){
			map.setImageResource(R.drawable.mapdodensdal);
		}	
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.EDGAR)){
			map.setImageResource(R.drawable.mapedgar);
			Log.v(debug, "YES");
		}	
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.KLUBBEN)){
			map.setImageResource(R.drawable.mapklubben);
		}	
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.KNAUS)){
			map.setImageResource(R.drawable.mapknaus);
		}	
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.LYCHE)){
			map.setImageResource(R.drawable.maplyche);
		}	
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.SELSKAPSSIDEN)){
			map.setImageResource(R.drawable.mapselskapssiden);
		}	
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.STORSALEN)){
			map.setImageResource(R.drawable.mapstorsalen);
		}	
		if (location.getLocationStringId().equalsIgnoreCase(LocationConstants.STROSSA)){
			map.setImageResource(R.drawable.mapstrossa);
		}	
	}



	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	private ArrayList<Float> getReport(){
		ArrayList<Float> list = new ArrayList<Float>();

		RatingBar dancing = (RatingBar) findViewById(R.id.places_progressbar_dansing_reported);
		list.add(dancing.getRating());

		RatingBar flirt = (RatingBar) findViewById(R.id.places_progressbar_sjekking_reported);
		list.add(flirt.getRating());

		RatingBar chat = (RatingBar) findViewById(R.id.places_progressbar_prating_reported);
		list.add(chat.getRating());

		RatingBar mood = (RatingBar) findViewById(R.id.places_progressbar_stemning_reported);
		list.add(mood.getRating());

		return list;
	}
	
	/**
	 * Sets the next event.
	 *
	 * @param tv the tv
	 * @param location the location
	 */
	private void setNextEvent(TextView tv, Location location){
		try {
			UkaEvent  event;
			String selection = (UkaEventContract.PLACE +"=? AND " +UkaEventContract.SHOWING_TIME+ "> strftime('%s','now')");
			eventCursor = this.managedQuery(
					UkaEventContract.EVENT_CONTENT_URI, 
					null, 
					selection, 
					new String[]{location.getLocationStringId()}, 
					UkaEventContract.SHOWING_TIME);
			eventCursor.moveToFirst();
			event = UkaEventMapper.getUkaEventFromCursor(eventCursor);
			tv.setText("Neste: " + event.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			tv.setText("");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the prev event.
	 *
	 * @param tv the tv
	 * @param location the location
	 */
	private void setPrevEvent(TextView tv, Location location){
		try {
			UkaEvent  event;
			String selection = (UkaEventContract.PLACE +"=? AND " +UkaEventContract.SHOWING_TIME+ "< strftime('%s','now')");
			eventCursor = this.managedQuery(
					UkaEventContract.EVENT_CONTENT_URI, 
					null, 
					selection, 
					new String[]{location.getLocationStringId()}, 
					UkaEventContract.SHOWING_TIME);
			eventCursor.moveToFirst();
			event = UkaEventMapper.getUkaEventFromCursor(eventCursor);
			tv.setText("Forrige: " + event.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			tv.setText("");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the dancing.
	 *
	 * @return the dancing
	 */
	private int getDancing(){
		return 2;
	}

	/**
	 * Gets the mood.
	 *
	 * @return the mood
	 */
	private int getMood(){
		return 3;
	}

	/**
	 * Gets the chat.
	 *
	 * @return the chat
	 */
	private int getChat(){
		return 5;
	}

	/**
	 * Gets the flirt.
	 *
	 * @return the flirt
	 */
	private int getFlirt(){
		return 1;
	}

	/**
	 * Send report.
	 *
	 * @param report the report
	 */
	private void sendReport(ArrayList<Float> report){
		//report contains reported float value of stemning, dansing, prating and sjekking in that order, index 0-3
		Log.v(debug, "Reported values: " + report.toString());
	}

	/**
	 * Send comment.
	 *
	 * @param comment the comment
	 */
	private void sendComment(String comment){
		//TODO: implement sharing
		Log.v(debug, "Comment to share: " + comment);
	}
}

