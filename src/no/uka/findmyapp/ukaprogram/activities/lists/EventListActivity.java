/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities.lists;

import java.util.Date;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.activities.EventDetailsActivity;
import no.uka.findmyapp.ukaprogram.adapters.AllEventListCursorAdapter;
import no.uka.findmyapp.ukaprogram.adapters.CalendarGalleryAdapter;
import no.uka.findmyapp.ukaprogram.adapters.EventListCursorAdapter;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.mapper.UkaEventMapper;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class EventListActivity.
 */
public class EventListActivity extends ListActivity
{	
	
	/** The Constant debug. */
	private final static String debug = "EventListActivity";
	
	/** The Constant ORDER_BY. */
	private final static String ORDER_BY = UkaEventContract.SHOWING_TIME + " asc";
	
	/** The m event cursor. */
	private Cursor mEventCursor;

	/** The gallery. */
	private Gallery gallery; 
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_list);

		addDateScroll();
		moveGalleryToCurrentDate();
	}
	
	/**
	 * Adds the date scroll.
	 */
	private void addDateScroll(){
		gallery = (Gallery) findViewById(R.id.gallery);
	    gallery.setAdapter(new CalendarGalleryAdapter(this));
	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	        	String selection;
	        	TextView dayNumberTV = (TextView) v.findViewById(R.id.date_item_day_number);
	        	if(isInteger(dayNumberTV.getText().toString())){
	        		int day = Integer.valueOf(dayNumberTV.getText().toString());
		            selection = UkaEventContract.SHOWING_TIME + " > " + DateUtils.getTimestampFromDayNumber(day) + " AND " + UkaEventContract.SHOWING_TIME + " < " + DateUtils.getTimestampFromDayNumber(day +1);
		            Log.v(debug, "Where statement: " + selection);
	        	}
	        	else{
		            selection = null;
	        	}
	            refreshList(selection);
	        }
	    });
	    gallery.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView parent, View v,
					int arg2, long arg3) {
				TextView dayNumberTV = (TextView) v.findViewById(R.id.date_item_day_number);
				String selection;
				if(isInteger(dayNumberTV.getText().toString())){
	        		int day = Integer.valueOf(dayNumberTV.getText().toString());
		            selection = UkaEventContract.SHOWING_TIME + " > " + DateUtils.getTimestampFromDayNumber(day) + " AND " + UkaEventContract.SHOWING_TIME + " < " + DateUtils.getTimestampFromDayNumber(day +1);
		            Log.v(debug, "Where statement: " + selection);
	        	}
	        	else{
		            selection = null;
	        	}
	            refreshList(selection);
			}

			@Override
			public void onNothingSelected(AdapterView parent) {
				// TODO Auto-generated method stub
			}
	    });
	}
	
	/**
	 * Checks if is integer.
	 *
	 * @param string the string
	 * @return true, if is integer
	 */
	public boolean isInteger(String string) {
	    try {
	        Integer.valueOf(string);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	/**
	 * Button init.
	 *
	 * @param viewId the view id
	 * @param drawableId the drawable id
	 * @param stringId the string id
	 * @param l the l
	 */
	private void buttonInit(int viewId, int drawableId, int stringId, OnClickListener l) {
		Button button = (Button) findViewById(viewId);
		button.setBackgroundDrawable(getResources().getDrawable(drawableId));
		button.setText(getResources().getString(stringId));
		button.setOnClickListener(l);
	}

	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, 
	 * android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor c = (Cursor) l.getItemAtPosition(position);

		UkaEvent event = UkaEventMapper.getUkaEventFromCursor(c);
		
		Intent intent = new Intent(this, EventDetailsActivity.class); 
		intent.putExtra(ApplicationConstants.LIST_ITEM_CLICKED_SIGNAL, event);

		startActivity(intent);
	}
	

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		String selection = UkaEventContract.EVENT_TYPE + " = ";
		switch (item.getItemId()) {
			case R.id.category_menu_all:
				selection = null;
				refreshList(selection);
				return true;
	
			case R.id.category_menu_party:
				selection = UkaEventContract.EVENT_TYPE + " = "
				+ ApplicationConstants.CATEGORY_PARTY;
				refreshList(selection);
				return true;
	
			case R.id.category_menu_concert:
				selection = UkaEventContract.EVENT_TYPE + " = "
				+ ApplicationConstants.CATEGORY_CONCERT;
				refreshList(selection);
				return true; 
	
			case R.id.category_menu_lecture:
				selection = UkaEventContract.EVENT_TYPE + " = " 
				+ ApplicationConstants.CATEGORY_LECTURE;
				refreshList(selection);
				return true; 
	
			case R.id.category_menu_revue:
				selection = UkaEventContract.EVENT_TYPE + " = " 
				+ ApplicationConstants.CATEGORY_REVUE ;
				refreshList(selection);
				Log.v(debug, selection);
				return true; 
				
			case R.id.category_menu_favourites:
				selection = UkaEventContract.FAVOURITE  ;
				refreshList(selection);
				Log.v(debug, selection);
				return true; 
				
			default:
				return false;
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, 
	 * android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.category_menu, menu);
	}

	/**
	 * Refresh list.
	 *
	 * @param selection the selection
	 */
	private void refreshList(String selection){
		Log.v(debug, "Refreshing page");
		mEventCursor = this.managedQuery(
				UkaEventContract.EVENT_CONTENT_URI, 
				null, 
				selection, 
				null, 
				ORDER_BY);
	
		if (selection != null){
			if (selection.indexOf(UkaEventContract.SHOWING_TIME) == -1){
				setListAdapter(new AllEventListCursorAdapter(this, mEventCursor));
			}
			else{
				setListAdapter(new EventListCursorAdapter(this, mEventCursor));
			}
		}
		else {
			setListAdapter(new AllEventListCursorAdapter(this, mEventCursor));
		}
	}
	
	/**
	 * Move gallery to current date.
	 */
	private void moveGalleryToCurrentDate(){
		Date toDay = new Date();
		if (toDay.getMonth() == ApplicationConstants.MONTH){
			Log.v(debug, "Today is day number: " + toDay.getDate());
			gallery.setSelection(toDay.getDate() - ApplicationConstants.UKA_START_DATE + 1, true);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.category_menu, menu);
	    return true;
	}
}