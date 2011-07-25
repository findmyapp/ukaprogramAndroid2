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

public class PlacesActivity extends Activity implements OnClickListener{
	
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	private static final String debug = "PlacesActivity";
	public static final String PLACE = "Place";
	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places);
		
		Button shareComment = (Button) findViewById(R.id.places_share);
		Button report = (Button) findViewById(R.id.places_report);
		
		report.setOnClickListener(this);
		shareComment.setOnClickListener(this);
		
		setupPlaceSpecificViews();
		setReportParamenters(getDansing(), getSjekking(), getStemning(), getPrating());
	}

	private String getReportedComment() {
		String reportedComment;
		EditText comment = (EditText) findViewById(R.id.places_comment);
		reportedComment =  comment.getText().toString();
		return reportedComment;
	}
	

	
	private void setReportParamenters(float dansing, float sjekking,
			float stemning, float prating) {
		RatingBar pbSjekking = (RatingBar) findViewById(R.id.places_progressbar_sjekking);
		RatingBar pbDansing = (RatingBar) findViewById(R.id.places_progressbar_dansing);
		RatingBar pbPrating = (RatingBar) findViewById(R.id.places_progressbar_prating);
		RatingBar pbStemning = (RatingBar) findViewById(R.id.places_progressbar_stemning);
		
		pbSjekking.setIsIndicator(true);
		pbDansing.setIsIndicator(true);
		pbPrating.setIsIndicator(true);
		pbStemning.setIsIndicator(true);
		
		
		pbSjekking.setRating(sjekking);
		pbDansing.setRating(dansing);
		pbPrating.setRating(prating);
		pbStemning.setRating(stemning);
	}

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
	
	private void setupPlaceSpecificViews(){
		ImageView map = (ImageView) findViewById(R.id.places_map);
		TextView place_name = (TextView) findViewById(R.id.places_name);
		TextView next_event = (TextView) findViewById(R.id.places_playing_next);
		TextView prev_event = (TextView) findViewById(R.id.places_last_played);
		
		//TODO: add method to inflate views
	}
	
	private ArrayList<Float> getReport(){
		ArrayList<Float> list = new ArrayList<Float>();
		
		RatingBar dansing = (RatingBar) findViewById(R.id.places_progressbar_dansing_reported);
		RatingBar sjekking = (RatingBar) findViewById(R.id.places_progressbar_sjekking_reported);
		RatingBar prating = (RatingBar) findViewById(R.id.places_progressbar_prating_reported);
		RatingBar stemning = (RatingBar) findViewById(R.id.places_progressbar_stemning_reported);
		
		list.add(stemning.getRating());
		list.add(dansing.getRating());
		list.add(prating.getRating());
		list.add(sjekking.getRating());
		
		return list;
	}
	
	private int getDansing(){
		return 2;
	}
	
	private int getStemning(){
		return 3;
	}
	
	private int getPrating(){
		return 5;
	}
	
	private int getSjekking(){
		return 1;
	}
	private void sendReport(ArrayList<Float> report){
		//report contains reported float value of stemning, dansing, prating and sjekking in that order, index 0-3
		Log.v(debug, "Reported values: " + report.toString());
	}
	private void sendComment(String comment){
		//TODO: implement sharing
		Log.v(debug, "Comment to share: " + comment);
	}
}

