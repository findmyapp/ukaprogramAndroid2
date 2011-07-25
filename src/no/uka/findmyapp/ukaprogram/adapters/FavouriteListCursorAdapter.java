/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.R;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ConsertListCursorAdapter.
 */
public class FavouriteListCursorAdapter extends CursorAdapter{
		
	/** The Constant debug. */
	private static final String debug = "FavouriteListCursorAdapter";

	/** The cursor. */
	private Cursor mCursor;
	
	/** The inflater. */
	private final LayoutInflater mInflater;
	
	/**
	 * Instantiates a new favourite list cursor adapter.
	 *
	 * @param context the context
	 * @param cursor the cursor
	 */
	public FavouriteListCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		mInflater = LayoutInflater.from(context);
		mCursor = cursor;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View eventView, Context context, Cursor cursor) {
		TextView t = (TextView) eventView.findViewById(R.id.favouriteListItem_title);
		Log.v(debug, "set favouriteListItemTitle " + getStringFromTableColumn(UkaEventContract.TITLE));
		
		t.setText(getStringFromTableColumn(UkaEventContract.TITLE));
	
		CheckBox cb = (CheckBox) eventView.findViewById(R.id.favouriteListItem_attending);
		cb.setButtonDrawable(R.drawable.favorites_button);
		
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.v(debug, "buttozRPhun, " + getStringFromTableColumn(UkaEventContract.TITLE));
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = this.mInflater.inflate(R.layout.favourite_list_item, parent, false);
		return view;
	}

	/**
	 * Gets the string from table column.
	 *
	 * @param tableColumnName the table column name
	 * @return the string from table column
	 */
	private String getStringFromTableColumn(String tableColumnName) {
		return this.mCursor.getString(this.mCursor.getColumnIndex(tableColumnName));
	}
}
