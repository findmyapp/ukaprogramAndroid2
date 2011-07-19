package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.EventListAdapter;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


//private ContactListAdapter contactAdapter;
//private List<Contact> contactsArraylist;

public class EventListActivity extends Activity {
	EventListAdapter eventAdapter;
	ArrayList<UkaEvent> eventsArrayList;
	ListView eventListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_list);

		eventsArrayList = new ArrayList<UkaEvent>();

		eventListView = (ListView) findViewById(R.id.eventListView);
		eventAdapter = new EventListAdapter(this, R.layout.list_adapter, eventsArrayList);
		eventListView.setAdapter(eventAdapter);
		////////
		populateListView();
		Log.v("EventListActivity", "Size of list: " + eventsArrayList.size() + "  " + eventsArrayList.toString());
		eventAdapter.notifyDataSetChanged();
	} // end onCreate()

	public void onResume() {
		super.onResume();
	}	
	private void populateListView() {
//		eventsArrayList.clear();
		EventDatabase eb = EventDatabase.getInstance();
		eventsArrayList.addAll(eb.getAllEvents(getContentResolver()));

		
			
		//eventsArrayList = eb.getAllEvents(getContentResolver());
		Log.v("EventListActivity", "Events inserted in list");


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

