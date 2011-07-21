package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.CalenderAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CalendarActivity extends Activity 
{
	GridView calenderGrid;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calendar);

		/*Here we setContentView() to calendar.xml, get the GridView and then fill it with the 
	                   ImageAdapter class that extend from BaseAdapter */

		calenderGrid = (GridView)findViewById(R.id.MyGrid);
		calenderGrid.setAdapter(new CalenderAdapter(this));
		
		calenderGrid.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				//viewContact.setClass(getApplicationContext(), ContactDetailsActivity.class);
				/**
					databaseHandler.open();
					databaseHandler.removePerson(contactArrayList.get(position).getId());
					databaseHandler.close();
					populateList();
				 */
				Intent viewDate = new Intent();
				viewDate.setClass(getApplicationContext(), EventListActivity.class);
				Log.i("e","date pressed was: " + (position + 3));
				viewDate.putExtra("SelectedDate", (position + 3));///////
				startActivity(viewDate); 
			}
		});
	}
}


