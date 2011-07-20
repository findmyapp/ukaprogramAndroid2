package no.uka.findmyapp.ukaprogram.activities;

import java.sql.Timestamp;
import java.util.ArrayList;

import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.DayListAdapter;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
/*
public class DateActivity extends Activity {

	DayListAdapter dayAdapter;
	ArrayList<UkaEvent> eventsArrayList;
	ListView eventListView;
	EventDatabase eventDatabase = EventDatabase.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("h","YOU ARE IN THE DATE ACTIVITY!");
		setContentView(R.layout.event_list_for_day);
		//TextView tv = (TextView) findViewById(R.id.date_list_date);
		Bundle extras = getIntent().getExtras();
		int selectedDay = extras.getInt("SelectedDate");
		//get the selected date
		//tv.setText((extras.getInt("SelectedDate"))+ "");

		eventsArrayList = new ArrayList<UkaEvent>();
		eventListView = (ListView) findViewById(R.id.event_list_for_day_eventListView);
		dayAdapter = new DayListAdapter(this, R.layout.date_list_adapter, eventsArrayList);
		TextView header= (TextView) findViewById(R.id.event_list_for_day_day);

		//finds the weekday and sets the header
		
		Time time = new Time();
		time.set(0,0,12,selectedDay, 9, 2011);
		time.normalize(true);
		Log.i("DATE ACTIVIT", selectedDay + " IS THE SEL DAY");
		try {
			header.setText(new DateUtils().getShortWeekDayName(time.weekDay) +". " + selectedDay + ". Okt");
		} catch (DateUtilsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		eventListView.setAdapter(dayAdapter);


		eventsArrayList.clear();
		Timestamp fromTime = new Timestamp(111, 9, selectedDay, 00,00,00,00);
		Timestamp toTime = new Timestamp(111, 9, selectedDay, 23,59,59,00);
		eventsArrayList.addAll(eventDatabase.getEventsInPeriod(getContentResolver(), fromTime, toTime));
		dayAdapter.notifyDataSetChanged();
		

		
		eventListView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				//viewContact.setClass(getApplicationContext(), ContactDetailsActivity.class);
				/**
					databaseHandler.open();
					databaseHandler.removePerson(contactArrayList.get(position).getId());
					databaseHandler.close();
					populateList();
				 
				Intent viewEvent = new Intent();
				viewEvent.setClass(getApplicationContext(), EventDetailsActivity.class);
				viewEvent.putExtra("SelectedEvent", eventsArrayList.get(position));///////
				startActivity(viewEvent); 
			}
		});

	}

}
*/
