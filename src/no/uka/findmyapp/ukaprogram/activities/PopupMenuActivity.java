/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * The Class PopupMenuActivity.
 */
public abstract class PopupMenuActivity extends Activity 
{
	
	/** The Constant debug. */
	@SuppressWarnings("unused")
	private static final String debug = "PopupMenuActivity";
	
	/** The m selected event. */
	private UkaEvent mSelectedEvent;
	
	/**
	 * Gets the selected event.
	 *
	 * @return the selected event
	 */
	public UkaEvent getSelectedEvent() {
		return mSelectedEvent;
	}

	/**
	 * Sets the selected event.
	 *
	 * @param selectedEvent the new selected event
	 */
	public void setSelectedEvent(UkaEvent selectedEvent) {
		this.mSelectedEvent = selectedEvent;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(R.string.setting_menu_item);
		return super.onCreateOptionsMenu(menu);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class); 
		intent.putExtra("previous_context", this.getClass());
		
		if(this.getClass().equals(EventDetailsActivity.class)) {
			intent.putExtra("selectedEvent", mSelectedEvent);
		}
		
		startActivity(intent);
		return true;
	}
}
