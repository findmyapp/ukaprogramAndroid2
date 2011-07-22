package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
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
	private String TAG = "EventDetails";
	private ArrayAdapter<String> friendAdapter;
	private ListView friendListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_details);
		
		Log.v(debug, debug + " onCreate()");
		
		Bundle bundle = getIntent().getExtras(); 
		Log.v(debug, "Bundle toString " + bundle.toString());
		
		if (bundle.getSerializable(EventListActivity.ITEM_CLICKED) != null) {
			selectedEvent = (UkaEvent) bundle.getSerializable(EventListActivity.ITEM_CLICKED);
			populateView(selectedEvent);
		}
		else{
			String exception = "Exception: empty bundle!";
			Toast t = Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_LONG);
			t.show();
		}
	}
	
	public void populateView(UkaEvent selectedEvent){
		DateUtils du = new DateUtils(); 

		Button friendsButton = (Button) findViewById(R.id.detailedEventFriendsOnEventButton);
		friendsButton.setOnClickListener(this); 
		
		friendList = new ArrayList<String>();
		super.setSelectedEvent(selectedEvent);
		Log.v(debug, "selectedEvent " + selectedEvent.toString());

		TextView ageLimit = (TextView) findViewById(R.id.detailedEventAgeLimit);
		TextView price = (TextView) findViewById(R.id.detailedEventPrice);
		TextView title = (TextView) findViewById(R.id.detailedEventTitle);
		TextView timeAndPlace = (TextView) findViewById(R.id.detailedEventTimeAndPlace);
		TextView description = (TextView) findViewById(R.id.detailedEventDescription);
		TextView headerTitle = (TextView) findViewById(R.id.event_details_header_title);
		CheckBox favorites = (CheckBox) findViewById(R.id.event_details_favorites);
		favorites.setOnCheckedChangeListener(this);
		
		favorites.setButtonDrawable(R.drawable.favorites_button);

		timeAndPlace.setText(	
			du.getWeekdayNameFromTimestamp(selectedEvent.getShowingTime()) + " " 
			+ du.getCustomDateFormatFromTimestamp("dd E MMM.", selectedEvent.getShowingTime()) + " " 
			+ du.getTimeFromTimestamp(selectedEvent.getShowingTime()) + ", " 
			+ selectedEvent.getPlace());
		
		title.setText(selectedEvent.getTitle());
		headerTitle.setText(selectedEvent.getTitle());
		description.setText(selectedEvent.getText());
		ageLimit.setText("Aldersgrense: " + selectedEvent.getAgeLimit() + " år");
		if(selectedEvent.isFree()){
			price.setText("Gratis");
		}
		
		if(selectedEvent.isFavourite()) {
			favorites.setChecked(true);
		}
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

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked) {
			Toast t = Toast.makeText(getApplicationContext(), selectedEvent.getTitle() + " is added as favourite", Toast.LENGTH_SHORT);
			t.show(); 

			ContentValues values = new ContentValues();
			values.put(UkaEventContract.FAVOURITE, 1);
			
			String where = UkaEventContract.EVENT_ID + " = '" + selectedEvent.getEventId() + "'"; 
			t = Toast.makeText(getApplicationContext(), where, Toast.LENGTH_SHORT);
			t.show(); 
			
			
			int rowsAffected = getContentResolver().update(UkaEventContract.EVENT_CONTENT_URI, values, where, null);
			
			t = Toast.makeText(getApplicationContext(), rowsAffected + "", Toast.LENGTH_SHORT);
			t.show(); 
			//this.setListAdapter(new EventListCursorAdapter(this, eventCursor));
		}
		else {
			Toast t = Toast.makeText(getApplicationContext(), selectedEvent.getTitle() + " is removed as favourite", Toast.LENGTH_SHORT);
			t.show(); 
		}

	}
}