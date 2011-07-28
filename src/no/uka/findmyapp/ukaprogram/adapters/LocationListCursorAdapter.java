/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.CursorTools;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class LocationListCursorAdapter.
 */
public class LocationListCursorAdapter extends CursorAdapter 
{
	
	/** The Constant debug. */
	private static final String debug = "LocationListCursorAdapter";
	
	/** The m inflater. */
	private final LayoutInflater mInflater;
	
	/** The m cursor. */
	private Cursor mCursor;

	/**
	 * Instantiates a new location list cursor adapter.
	 *
	 * @param context the context
	 * @param c the c
	 */
	public LocationListCursorAdapter(Context context, Cursor c) {
		super(context, c);
		this.mInflater = LayoutInflater.from(context);
		this.mCursor = c;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View eventView, Context context, Cursor cursor) {		
		try {
			TextView t = (TextView) eventView.findViewById(R.id.locationListItem_title);
			t.setText(CursorTools.getStringFromTableColumn(mCursor, LocationContract.LOCATION_NAME));
			
			t = (TextView) eventView.findViewById(R.id.locationList_happeningNow);
			t.setText("Fest!");
			
		}
		catch(Exception e) {
			Log.v(debug, "Exception: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = mInflater.inflate(R.layout.location_list_item, parent, false);
		return view;
	}
}
