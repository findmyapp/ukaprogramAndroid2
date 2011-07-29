/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities.lists;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.activities.EventDetailsActivity;
import no.uka.findmyapp.ukaprogram.adapters.ConsertListCursorAdapter;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.mapper.UkaEventMapper;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcertListActivity.
 */
public class ConcertListActivity extends ListActivity 
{	
	
	/** The Constant debug. */
	@SuppressWarnings("unused")
	private final static String debug = "ConcertListActivity";
	
	/** The m event cursor. */
	private Cursor mEventCursor;

	/** The Constant ORDER_BY. */
	private final static String ORDER_BY = UkaEventContract.TITLE + " desc";


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		String selection = UkaEventContract.EVENT_TYPE + " = " + ApplicationConstants.CATEGORY_CONCERT;
		
		mEventCursor = this.managedQuery(UkaEventContract.EVENT_CONTENT_URI, null, selection, null, ORDER_BY);
		this.setListAdapter(new ConsertListCursorAdapter(this, mEventCursor));
	}


	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
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
}