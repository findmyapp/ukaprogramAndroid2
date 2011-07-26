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
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class EventListActivity.
 */
public class EventListActivity extends ListActivity implements OnClickListener
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
		
		HorizontalScrollView sv = (HorizontalScrollView) findViewById(R.id.eventList_horizontalScrollView);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			moveCursorToDate(mEventCursor, bundle.getInt(CalendarActivity.SELECTED_DATE));
		}
		
		initView();
	}

	/**
	 * Inits the view.
	 */
	private void initView() {
		//Initializing category buttons
		// All button
		buttonInit(R.id.categorymenu_all, R.drawable.categorybutton_all, 
				R.string.category_all, this);
		
		// Concert button
		buttonInit(R.id.categorymenu_concert, R.drawable.categorybutton_concert, 
				R.string.category_concert, this);

		// Revue button
		buttonInit(R.id.categorymenu_revue, R.drawable.categorybutton_revue, 
				R.string.category_revue, this);
		
		// Lecture button
		buttonInit(R.id.categorymenu_lecture, R.drawable.categorybutton_lecture, 
				R.string.category_lecture, this);
		
		// Party button
		buttonInit(R.id.categorymenu_party, R.drawable.categorybutton_party, 
				R.string.category_party, this);
		
		// Favourites button
		//TODO create favourite button background
		buttonInit(R.id.categorymenu_favourites, R.drawable.categorybutton_revue, 
				R.string.category_favourites, this);
		
		// Sets the header line color
		setHorizontalRulingLinesColor(R.color.categorySelected_all);
	
		// Shows all events, null selection criteria
		refreshList(null);
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
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor c = (Cursor) l.getItemAtPosition(position);

		UkaEvent event = UkaEventMapper.getUkaEventFromCursor(c);
		Log.v(debug, event.toString());
		
		Intent intent = new Intent(this, EventDetailsActivity.class); 
		intent.putExtra(ApplicationConstants.LIST_ITEM_CLICKED_SIGNAL, event);

		startActivity(intent);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		String selection = UkaEventContract.EVENT_TYPE + " = ";
		switch (v.getId()) {
			case R.id.categorymenu_all:
				setHorizontalRulingLinesColor(R.color.categorySelected_all);
				selection = null;
				refreshList(selection);
				break; 
	
			case R.id.categorymenu_party:
				setHorizontalRulingLinesColor(R.color.categorySelected_party);
				selection = UkaEventContract.EVENT_TYPE + " = " + ApplicationConstants.CATEGORY_PARTY;
				refreshList(selection);
				break; 
	
			case R.id.categorymenu_concert:
				setHorizontalRulingLinesColor(R.color.categorySelected_concert);
				selection = UkaEventContract.EVENT_TYPE + " = " + ApplicationConstants.CATEGORY_CONCERT;
				refreshList(selection);
				break; 
	
			case R.id.categorymenu_lecture:
				setHorizontalRulingLinesColor(R.color.categorySelected_lecture);
				selection = UkaEventContract.EVENT_TYPE + " = " + ApplicationConstants.CATEGORY_LECTURE;
				refreshList(selection);
				break; 
	
			case R.id.categorymenu_revue:
				setHorizontalRulingLinesColor(R.color.categorySelected_revue);
				selection = UkaEventContract.EVENT_TYPE + " = " + ApplicationConstants.CATEGORY_REVUE ;
				refreshList(selection);
				break; 

			default:
				break;
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
		
		HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.eventList_horizontalScrollView);
		hsv.setBackgroundColor(getResources().getColor(colorId));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
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
		mEventCursor = this.managedQuery(UkaEventContract.EVENT_CONTENT_URI, null, selection, null, ORDER_BY);
	
		Log.v(debug, "refreshList: Setting list adapter");
		Log.v(debug, "refreshList:cursor " + mEventCursor);
		while (mEventCursor.moveToNext()) {
			Log.v(debug, "refreshingList count cursor: " + mEventCursor.getCount());
			String columnNames = ""; 
			for (int i = 0; i < mEventCursor.getColumnCount(); i++) {
				columnNames += "  " + mEventCursor.getColumnName(i);
			}
			Log.v(debug, "refreshingList fields count " + mEventCursor.getColumnCount());
			Log.v(debug, "refreshingList fields in cursor " + columnNames);
		}
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

}