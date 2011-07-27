/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class EventListCursorAdapter.
 */
public class EventListCursorAdapter extends CursorAdapter implements OnClickListener
{
	/** The Constant debug. */
	private static final String debug = "EventListCursorAdapter";

	/** The m cursor. */
	private Cursor mCursor;
	
	/** The inflater. */
	private final LayoutInflater inflater;
	
	/**
	 * Instantiates a new event list cursor adapter.
	 *
	 * @param context the context
	 * @param cursor the cursor
	 */
	public EventListCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		Log.v(debug, "Inside EventListCursorAdapter constructor");
		this.inflater = LayoutInflater.from(context);
		this.mCursor = cursor;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View eventView, Context context, Cursor cursor) {
		try {
			TextView t = (TextView) eventView.findViewById(R.id.listItemTitle);
			t.setText(getStringFromTableColumn(UkaEventContract.TITLE));
			
			t = (TextView) eventView.findViewById(R.id.listItemEventTime);
			t.setText(DateUtils.getTimeFromTimestamp(
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME)));
			
			t = (TextView) eventView.findViewById(R.id.listItemWeekday); 
			String showingEvent = DateUtils.getWeekdayNameFromTimestamp(
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME));
			t.setText(showingEvent);
			
			t = (TextView) eventView.findViewById(R.id.listItemDayNumber);
			t.setText(DateUtils.getCustomDateFormatFromTimestamp("dd",
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME)));
			
			
			
			t = (TextView) eventView.findViewById(R.id.listItemPlace);
			t.setText(getStringFromTableColumn(UkaEventContract.PLACE));
			
			CheckBox cb = (CheckBox) eventView.findViewById(R.id.listItemAttending);
			cb.setButtonDrawable(R.drawable.favorites_button);
			cb.setChecked(getBooleanFromTableColumn(UkaEventContract.FAVOURITE));
			cb.setClickable(false);
			//TODO setClickable true, implement onclick listener
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
		final View view = this.inflater.inflate(R.layout.event_list_item, parent, false);
		return view;
	}

	/**
	 * Gets the string from table column.
	 *
	 * @param tableColumnName the table column name
	 * @return the string from table column
	 */
	private String getStringFromTableColumn(String tableColumnName) {
		return mCursor.getString(mCursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the long from table column.
	 *
	 * @param tableColumnName the table column name
	 * @return the long from table column
	 */
	private long getLongFromTableColumn(String tableColumnName) {
		return mCursor.getLong(mCursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the boolean from table column.
	 *
	 * @param tableColumnName the table column name
	 * @return the boolean from table column
	 */
	private boolean getBooleanFromTableColumn(String tableColumnName) {
		if(mCursor.getInt(mCursor.getColumnIndex(tableColumnName)) == 1) return true;
		return false; 
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
