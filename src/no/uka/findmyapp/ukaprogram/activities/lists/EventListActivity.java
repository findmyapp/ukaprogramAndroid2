/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities.lists;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.activities.CalendarActivity;
import no.uka.findmyapp.ukaprogram.activities.EventDetailsActivity;
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
import android.widget.LinearLayout;
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_list);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			moveCursorToDate(mEventCursor, bundle.getInt(CalendarActivity.SELECTED_DATE));
		}
		//initView();
		addDateScroll();
	}
	
	private void addDateScroll(){
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
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
		Log.v(debug, "TESTTETETET");
	    // Handle item selection
		String selection = UkaEventContract.EVENT_TYPE + " = ";
		switch (item.getItemId()) {
			case R.id.category_menu_all:
				setHorizontalRulingLinesColor(R.color.categorySelected_all);
				selection = null;
				refreshList(selection);
				return true;
	
			case R.id.category_menu_party:
				setHorizontalRulingLinesColor(R.color.categorySelected_party);
				selection = UkaEventContract.EVENT_TYPE + " = "
				+ ApplicationConstants.CATEGORY_PARTY;
				refreshList(selection);
				return true;
	
			case R.id.category_menu_concert:
				setHorizontalRulingLinesColor(R.color.categorySelected_concert);
				selection = UkaEventContract.EVENT_TYPE + " = "
				+ ApplicationConstants.CATEGORY_CONCERT;
				refreshList(selection);
				return true; 
	
			case R.id.category_menu_lecture:
				setHorizontalRulingLinesColor(R.color.categorySelected_lecture);
				selection = UkaEventContract.EVENT_TYPE + " = " 
				+ ApplicationConstants.CATEGORY_LECTURE;
				refreshList(selection);
				return true; 
	
			case R.id.category_menu_revue:
				setHorizontalRulingLinesColor(R.color.categorySelected_revue);
				selection = UkaEventContract.EVENT_TYPE + " = " 
				+ ApplicationConstants.CATEGORY_REVUE ;
				refreshList(selection);
				return true; 

			default:
				return false;
		}
	}

	/**
	 * Sets the horizontal ruling lines color.
	 *
	 * @param colorId the new horizontal ruling lines color
	 */
	private void setHorizontalRulingLinesColor(int colorId) {
		LinearLayout line = (LinearLayout) findViewById(R.id.eventList_HorizontalRulingHeader);
		line.setBackgroundColor(getResources().getColor(colorId));
		
		line = (LinearLayout) findViewById(R.id.eventList_HorizontalRulingFooter);
		line.setBackgroundColor(getResources().getColor(colorId));
		
		//HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.eventList_horizontalScrollView);
		//hsv.setBackgroundColor(getResources().getColor(colorId));
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
	
		setListAdapter(new EventListCursorAdapter(this, mEventCursor));
	}

	//TODO FIX moveCursorToDate, CalendarActivity or EventListActivity?
	/**
	 * Move cursor to date.
	 *
	 * @param cursor the cursor
	 * @param date the date
	 */
	private void moveCursorToDate(Cursor cursor, int date)  {
		boolean dateFound = false; 
		
		cursor.moveToFirst();
		while (!dateFound && cursor.moveToNext()) {
			int i = DateUtils.getDayIntFromTimestamp(cursor.getLong(
					cursor.getColumnIndex(UkaEventContract.SHOWING_TIME))); 
			if(i == date) dateFound = true;
			cursor.moveToNext();
		}
		
		if (dateFound){
			this.setSelection(cursor.getPosition());
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), CalendarActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.category_menu, menu);
	    return true;
	}
}