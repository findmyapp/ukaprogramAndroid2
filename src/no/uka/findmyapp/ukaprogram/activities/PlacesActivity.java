package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class PlacesActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places);
		
		ProgressBar pbSjekking = (ProgressBar) findViewById(R.id.places_progressbar_sjekking);
		ProgressBar pbDansing = (ProgressBar) findViewById(R.id.places_progressbar_dansing);
		ProgressBar pbPrating = (ProgressBar) findViewById(R.id.places_progressbar_prating);
		ProgressBar pbStemning = (ProgressBar) findViewById(R.id.places_progressbar_stemning);
		pbSjekking.setProgress(70);
		pbDansing.setProgress(64);
		pbPrating.setProgress(5);
		pbStemning.setProgress(98);
		
	}

}
