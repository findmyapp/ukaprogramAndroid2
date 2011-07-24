/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

// TODO: Auto-generated Javadoc
/**
 * The Class PlacesActivity.
 */
public class PlacesActivity extends Activity
{
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places);
		
		ProgressBar pbSjekking = (ProgressBar) findViewById(R.id.places_progressbar_sjekking);
		pbSjekking.setProgress(70);
		
		ProgressBar pbDansing = (ProgressBar) findViewById(R.id.places_progressbar_dansing);
		pbDansing.setProgress(64);
		
		ProgressBar pbPrating = (ProgressBar) findViewById(R.id.places_progressbar_prating);
		pbPrating.setProgress(5);
		
		ProgressBar pbStemning = (ProgressBar) findViewById(R.id.places_progressbar_stemning);
		pbStemning.setProgress(98);
	}
}
