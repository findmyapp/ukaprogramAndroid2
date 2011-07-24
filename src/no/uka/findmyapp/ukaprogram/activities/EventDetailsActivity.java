package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailsActivity extends PopupMenuActivity implements OnClickListener, OnCheckedChangeListener {
	private static final String debug = "EventsDetailsActivity";
	
	private UkaEvent selectedEvent; 
	private ArrayList<String> friendList;
	private ArrayAdapter<String> friendAdapter;
	private ListView friendListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_details);
		
		Bundle bundle = getIntent().getExtras(); 
		Log.v(debug, "Bundle toString " + bundle.toString());
		
		if (bundle.getSerializable(ApplicationConstants.LIST_ITEM_CLICKED_SIGNAL) != null) {
			selectedEvent = (UkaEvent) bundle.getSerializable(ApplicationConstants.LIST_ITEM_CLICKED_SIGNAL);
			populateView(selectedEvent);
		}
		else{
			// Toast empty bundle exception
			showToast(getResources().getString(R.string.exception_emptyBundle));
		}
	}
	
	public void populateView(UkaEvent selectedEvent){
		Log.v(debug, "populateView: selectedEvent " + selectedEvent.toString());
		
		DateUtils du = new DateUtils(); 

		Button friendsButton = (Button) findViewById(R.id.detailedEventFriendsOnEventButton);
		friendsButton.setOnClickListener(this); 
		friendList = new ArrayList<String>();
		
		TextView title = (TextView) findViewById(R.id.detailedEventTitle);
		title.setText(selectedEvent.getTitle());
		
		TextView timeAndPlace = (TextView) findViewById(R.id.detailedEventTimeAndPlace);
		timeAndPlace.setText(	
				du.getWeekdayNameFromTimestamp(selectedEvent.getShowingTime()) + " " 
				+ du.getCustomDateFormatFromTimestamp("dd E MMM.", selectedEvent.getShowingTime()) 
				+ " " + du.getTimeFromTimestamp(selectedEvent.getShowingTime()) 
				+ ", " + selectedEvent.getPlace());
		
		TextView headerTitle = (TextView) findViewById(R.id.event_details_header_title);
		headerTitle.setText(selectedEvent.getTitle());
		
		TextView description = (TextView) findViewById(R.id.detailedEventDescription);
		description.setText(selectedEvent.getText());

		TextView ageLimit = (TextView) findViewById(R.id.detailedEventAgeLimit);
		ageLimit.setText(getResources().getString(R.string.eventDetailedActivity_agelimit)
				+ selectedEvent.getAgeLimit() 
				+ getResources().getString(R.string.eventDetailedActivity_year));
		
		TextView price = (TextView) findViewById(R.id.detailedEventPrice);
		if(selectedEvent.isFree()){
			price.setText(getResources().getString(R.string.eventDetailedActivity_free));
		}
		
		CheckBox favorites = (CheckBox) findViewById(R.id.event_details_favorites);
		favorites.setOnCheckedChangeListener(this);	
		favorites.setButtonDrawable(R.drawable.favorites_button);
		if(selectedEvent.isFavourite()) {
			favorites.setChecked(true);
		}
	
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.v(debug, "selectedEvent id " + selectedEvent.getEventId());
		String info;
		if(isChecked) {
			changeFavourite(this.selectedEvent.getEventId(), true);
			info = this.selectedEvent.getTitle() + getResources().getString(R.string.toast_isAddedAsFavourite);
		}
		else {			
			changeFavourite(this.selectedEvent.getEventId(), false);
			info = this.selectedEvent.getTitle() + getResources().getString(R.string.toast_isAddedAsFavourite);
		}
		
		showToast(info); 
	}

	private void showToast(String info ) {
		Toast t = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT);
		t.show();
	}

	private void changeFavourite(int id, boolean isFavourite) {
		ContentValues values = new ContentValues();
		values.put(UkaEventContract.CANCELED, 0);
		String where = UkaEventContract.EVENT_ID + " = '" + id + "'"; 
		getContentResolver().update(UkaEventContract.EVENT_CONTENT_URI, values, where, null);
	}
	
	public void populateFriendList() {
		 friendList.clear();
		 friendList.add("Audun");
		 friendList.add("Kaare");
		 friendList.add("Ole Christian");
	 } 

	public void showPopupWindow() {
		friendListView = new ListView(this);
		populateFriendList();
		friendAdapter = new ArrayAdapter<String>(this, R.layout.friends_list, R.id.friendsListText, friendList);
		friendListView.setAdapter(friendAdapter);
		Dialog d = new Dialog(this);
		friendListView.setBackgroundColor(Color.WHITE);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Deltagende venner");
		builder.setView(friendListView);
		d = builder.create();
		d.show();
	}

	@Override
	public void onClick(View v) {
		showPopupWindow();
	}


}