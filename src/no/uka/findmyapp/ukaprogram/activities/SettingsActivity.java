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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingsActivity.
 */
public class SettingsActivity extends Activity implements OnClickListener {
	
	/** The Constant debug. */
	private static final String debug = "SettingsActivity";
	
	/** The previous_class. */
	@SuppressWarnings("rawtypes")
	private Class previous_class;
	
	/** The selected event. */
	private UkaEvent selectedEvent;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		Log.v(debug, "getIntent.getClass " + getIntent().getClass().getCanonicalName());

	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.setting_alternatives, android.R.layout.simple_spinner_item);
	    
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		Spinner positionSpinner = (Spinner) findViewById(R.id.positionSetting_spinner);
	    positionSpinner.setAdapter(adapter);
	    
		Spinner eventSpinner = (Spinner) findViewById(R.id.eventSetting_spinner);
	    eventSpinner.setAdapter(adapter);
	    
		Spinner moneySpinner = (Spinner) findViewById(R.id.moneySetting_spinner);
	    moneySpinner.setAdapter(adapter);
	    
		Spinner mediaSpinner = (Spinner) findViewById(R.id.mediaSetting_spinner);
	    mediaSpinner.setAdapter(adapter);
	    
	    Bundle bundle = getIntent().getExtras();
	    
	    if(bundle != null) {
	    	previous_class = (Class) bundle.getSerializable("previous_context");
	    	if(previous_class.equals(EventDetailsActivity.class)) {
	    		selectedEvent = (UkaEvent) bundle.getSerializable("selectedEvent");
	    	}
	    }
	    
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Log.v(debug, "Submit button clicked..");
		// Save settings
		Intent i = new Intent(this, previous_class);
		if(previous_class.equals(EventDetailsActivity.class)) {
			i.putExtra("selectedEvent", selectedEvent);
		}
		startActivity(i);
	}

}
