/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities.lists;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import no.uka.findmyapp.android.rest.datamodels.models.Location;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.activities.PlacesActivity;
import no.uka.findmyapp.ukaprogram.adapters.LocationListCursorAdapter;
import no.uka.findmyapp.ukaprogram.mapper.LocationMapper;
import no.uka.findmyapp.ukaprogram.utils.LocationUpdater;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class LocationListActivity.
 */
public class LocationListActivity extends ListActivity implements OnClickListener 
{
	
	/** The Constant debug. */
	private final static String debug = "LocationListActivity";
	
	/** The Constant SELECTED_LOCATION. */
	public final static String SELECTED_LOCATION = "selectedLocation";
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.location_list);
		
        ReceiveIntent intentReceiver = new ReceiveIntent();
		IntentFilter intentFilter = new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN);
		getApplicationContext().registerReceiver(intentReceiver, intentFilter); 
		
		LocationUpdater lu = new LocationUpdater(getApplicationContext()); 
		lu.updateLocations();
	}

	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		//Cursor c = (Cursor) l.getItemAtPosition(position);

		//UkaEvent event = UkaEventMapper.getUkaEventFromCursor(c);
		//Log.v(debug, event.toString());
		Cursor c = (Cursor) l.getItemAtPosition(position);

		Location location = LocationMapper.getLocationFromCursor(c);
		Log.v(debug, location.toString());
		Intent intent = new Intent().setClass(this, PlacesActivity.class);
		intent.putExtra(SELECTED_LOCATION, location);
		startActivity(intent);

	}
	
	/**
	 * Sets the adapter.
	 */
	protected void setAdapter() {
		Log.v(debug, "Before cursor..");
		Cursor locationCursor = managedQuery(LocationContract.LOCATION_CONTENT_URI,
				null, null, null, null);

		Log.v(debug, "After cursor..");

		this.setListAdapter(new LocationListCursorAdapter(this, locationCursor));
		
		Log.v(debug, "After adapter..");
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * The Class ReceiveIntent.
	 */
	private class ReceiveIntent extends BroadcastReceiver {
		
		/* (non-Javadoc)
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent ) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(IntentMessages.BROADCAST_INTENT_TOKEN)) {
				setAdapter();
			}
		}
	}

}
