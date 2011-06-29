package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.models.Event;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


//private ContactListAdapter contactAdapter;
//private List<Contact> contactsArraylist;

public class EventListActivity extends Activity {
	EventListAdapter eventAdapter;
	ArrayList<Event> eventsArrayList;
	ListView eventListView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_list);

		eventsArrayList = new ArrayList<Event>();
		eventListView = (ListView) findViewById(R.id.eventListView);
		eventAdapter = new EventListAdapter(this, R.layout.list_adapter, eventsArrayList);
		eventListView.setAdapter(eventAdapter);
		////////
		populateListView();
	} // end onCreate()

	public void onResume() {
		super.onResume();
	}	
	private void populateListView() {
		eventsArrayList.clear();

		Event kaizers = new Event();
		kaizers.setTitle("Kaizers Orchestra");
		kaizers.setPlace("Dødens dal");	
		kaizers.setEventType("Konsert");
		eventsArrayList.add(kaizers);

		Event oktoberfest = new Event();
		oktoberfest.setTitle("Oktoberfest");
		oktoberfest.setPlace("Dødens dal");
		oktoberfest.setEventType("Fest");
		eventsArrayList.add(oktoberfest);

		eventListView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				//viewContact.setClass(getApplicationContext(), ContactDetailsActivity.class);
				/**
					databaseHandler.open();
					databaseHandler.removePerson(contactArrayList.get(position).getId());
					databaseHandler.close();
					populateList();
				 */
				Intent viewEvent = new Intent();
				viewEvent.setClass(getApplicationContext(), EventDetailsActivity.class);
				viewEvent.putExtra("SelectedEvent", eventsArrayList.get(position));///////
				startActivity(viewEvent); 
			}
		});

	} 

}

