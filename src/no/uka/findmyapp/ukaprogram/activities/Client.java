package no.uka.findmyapp.ukaprogram.activities;

import no.accenture.activities.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Client extends TabActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, EventListActivity.class);
		spec = tabHost.newTabSpec("eventList").setIndicator("Events").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, FavoritesListActivity.class);
		spec = tabHost.newTabSpec("favs").setIndicator("Favorites").setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
				
	} // end onCreate()

}