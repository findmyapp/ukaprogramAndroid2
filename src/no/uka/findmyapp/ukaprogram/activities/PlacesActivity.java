package no.uka.findmyapp.ukaprogram.activities;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places);
		
		Button shareComment = (Button) findViewById(R.id.places_share);
		shareComment.setOnClickListener(this);
		
		Button report = (Button) findViewById(R.id.places_report);
		report.setOnClickListener(this);
	
		setupPlaceSpecificViews();
		setReportedParamenters(getDancing(), getFlirt(), getMood(), getChat());
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
	 * @param dancing the dansing
	 * @param flirt the sjekking
	 * @param mood the stemning
	 * @param chat the prating
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
	 * Setup place specific views.
	 */
	private void setupPlaceSpecificViews(){
		ImageView map = (ImageView) findViewById(R.id.places_map);
		TextView place_name = (TextView) findViewById(R.id.places_name);
		TextView next_event = (TextView) findViewById(R.id.places_playing_next);
		TextView prev_event = (TextView) findViewById(R.id.places_last_played);
		
		//TODO: add method to inflate views
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
	 * Gets the dansing.
	 *
	 * @return the dansing
	 */
	private int getDancing(){
		return 2;
	}
	
	/**
	 * Gets the stemning.
	 *
	 * @return the stemning
	 */
	private int getMood(){
		return 3;
	}
	
	/**
	 * Gets the prating.
	 *
	 * @return the prating
	 */
	private int getChat(){
		return 5;
	}
	
	/**
	 * Gets the sjekking.
	 *
	 * @return the sjekking
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

