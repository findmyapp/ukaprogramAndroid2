package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener {
	
	private static final String debug = "SettingsActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		Log.v(debug, "getIntent.getClass " + getIntent().getClass().getCanonicalName());
		
		TextView positionText = (TextView) findViewById(R.id.positionSettingText);
		TextView eventText = (TextView) findViewById(R.id.eventSettingText);
		TextView moneyText = (TextView) findViewById(R.id.moneySettingText);
		TextView mediaText = (TextView) findViewById(R.id.mediaSettingText);
		
		positionText.setText("Posisjon:");
		eventText.setText("Arrangement:");
		moneyText.setText("Penger:");
		mediaText.setText("Media:");
		
		Spinner positionSpinner = (Spinner) findViewById(R.id.positionSettingSpinner);
		Spinner eventSpinner = (Spinner) findViewById(R.id.eventSettingSpinner);
		Spinner moneySpinner = (Spinner) findViewById(R.id.moneySettingSpinner);
		Spinner mediaSpinner = (Spinner) findViewById(R.id.mediaSettingSpinner);
		
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.setting_alternatives, android.R.layout.simple_spinner_item);
	    
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    positionSpinner.setAdapter(adapter);
	    eventSpinner.setAdapter(adapter);
	    moneySpinner.setAdapter(adapter);
	    mediaSpinner.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// Save settings
		Intent i = new Intent(this, getCallingActivity().getClass());	
		startActivity(i);
	}

}
