/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.rest.datamodels.enums.PrivacySetting;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.android.rest.datamodels.models.UserPrivacy;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.PrivacySettings;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingsActivity.
 */
public class SettingsActivity extends Activity implements OnClickListener,OnItemSelectedListener {
	
	/** The Constant debug. */
	private static final String debug = "SettingsActivity";
	
	/** The previous_class. */
	@SuppressWarnings("rawtypes")
	private Class previous_class;
	
	/** The selected event. */
	private UkaEvent selectedEvent;
	
	/** The usersettings. */
	private UserPrivacy usersettings;
	
	/** The ps. */
	private PrivacySettings ps;
	
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
	    //Initializing
	    usersettings = new UserPrivacy();
	    ps = new PrivacySettings(getApplicationContext());
	    
	    
		//Making spinners
		Spinner positionSpinner = (Spinner) findViewById(R.id.positionSetting_spinner);
	    positionSpinner.setAdapter(adapter);
	    
		Spinner eventSpinner = (Spinner) findViewById(R.id.eventSetting_spinner);
	    eventSpinner.setAdapter(adapter);
	    
		Spinner moneySpinner = (Spinner) findViewById(R.id.moneySetting_spinner);
	    moneySpinner.setAdapter(adapter);
	    
		Spinner mediaSpinner = (Spinner) findViewById(R.id.mediaSetting_spinner);
	    mediaSpinner.setAdapter(adapter);
	    
	    Button saveButton = (Button) findViewById(R.id.submitSettingsButton);
	    saveButton.setOnClickListener(this);
	    
	    positionSpinner.setOnItemSelectedListener(this);
	    eventSpinner.setOnItemSelectedListener(this);
	    mediaSpinner.setOnItemSelectedListener(this);
	    moneySpinner.setOnItemSelectedListener(this);
	    
	    Bundle bundle = getIntent().getExtras();
	    
	    //Checking if privacy settings already stored
	    UserPrivacy up = ps.getPrivacySettings();
	    if(up != null){
	    	eventSpinner.setSelection(PrivacySetting.toInt(up.getEventsPrivacySetting()));
	    	positionSpinner.setSelection(PrivacySetting.toInt(up.getPositionPrivacySetting()));
	    	moneySpinner.setSelection(PrivacySetting.toInt(up.getMoneyPrivacySetting()));
	    	mediaSpinner.setSelection(PrivacySetting.toInt(up.getMediaPrivacySetting()));
	    }
	    
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
		ps.changePrivacySettings(usersettings);
		Context context = getApplicationContext();
		CharSequence text = "Instillinger lagret";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView AV, View V, int position,
			long id) {
		
		switch (AV.getId()) {
		case R.id.eventSetting_spinner: 
			usersettings.setEventsPrivacySetting(PrivacySetting.getSetting(position));
			break;
		
		case R.id.positionSetting_spinner:
			usersettings.setPositionPrivacySetting(PrivacySetting.getSetting(position));
			break;
		
		case R.id.moneySetting_spinner:
			usersettings.setMoneyPrivacySetting(PrivacySetting.getSetting(position));
			break;
		
		case R.id.mediaSetting_spinner:
			usersettings.setMediaPrivacySetting(PrivacySetting.getSetting(position));
			break;
		default:
			
			break;
		}
		
		
		
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		  
		
	}

}
