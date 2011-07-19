package no.uka.findmyapp.ukaprogram.activities;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.constants.ServiceDataFormat;
import no.uka.findmyapp.android.rest.datamodels.core.ServiceModel;
import no.uka.findmyapp.android.rest.datamodels.enums.HttpType;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class Client extends TabActivity{
	private static final String debug = "Client";
	private static final String STARTUP_REQUEST_TOKEN = "startup";
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		updateEvents();

	} // end onCreate()
	private void initTabHost(){
		Intent i = new Intent(this, EventListActivity.class); 
		startActivity(i);
	}
	public void updateEvents(){

		try {
			EventDatabase.getInstance().clearEventTable(getContentResolver());
			
	        ReciveIntent intentReceiver = new ReciveIntent();
			IntentFilter intentFilter = new IntentFilter(IntentMessages.BROADCAST_INTENT_TOKEN);

			registerReceiver(intentReceiver, intentFilter); 
			Handler handler = new Handler();
			
			serviceHelper.callStartService(this, UkappsServices.UKAEVENTS); 
			ServiceModel sm = new ServiceModel(
					new URI("http://findmyapp.net/findmyapp/program/uka11/events"),
					HttpType.GET, 
					ServiceDataFormat.JSON, 
					UkaEvent.class, 
					null, 
					UkaEventContract.EVENT_CONTENT_URI, 
					STARTUP_REQUEST_TOKEN,
					null);
			Log.v(debug, "intentReceiver released");
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public class ReciveIntent extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.w("BroadcastIntentDebug", "---------");

			if (intent.getAction().equals(IntentMessages.BROADCAST_INTENT_TOKEN)) {
				Log.w("BroadcastIntentDebug", "---------");
				initTabHost(); 
			}
		}
	}
    
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();   
		inflater.inflate(R.menu.main_menu, menu);   
		return true;
	}
/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {  
		// Handle item selection  
		switch (item.getItemId()) {   
		case R.id.calendar:  
			openCalendar();   
			return true;
		case R.id.fiveNext:  
			openFiveNext(); 
			return true;
		case R.id.fullList:  
			openFullList(); 
			return true;
		default:  
			return super.onOptionsItemSelected(item); 
		}
	}

	public void openCalendar(){
		Intent intent;

		intent = new Intent().setClass(this, CalendarActivity.class);
		startActivity(intent);

	}
	public void openFiveNext(){
		Intent intent;

		intent = new Intent().setClass(this, FiveNextActivity.class);
		startActivity(intent);
	}
	public void openFullList(){
		Intent intent;

		intent = new Intent().setClass(this, EventListActivity.class);
		startActivity(intent);
	}
	*/
}