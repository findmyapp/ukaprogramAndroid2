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
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

<<<<<<< HEAD
public class Main extends Activity implements OnClickListener{
=======
public class Client extends TabActivity{
	private static final String debug = "Client";
>>>>>>> 3cc891bb560066a3426d0a86ed930b50fe07a8d5
	private static final String STARTUP_REQUEST_TOKEN = "startup";
	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
<<<<<<< HEAD
		updateEvents();

	} // end onCreate()
	private void initTabHost(){
		Intent i = new Intent(this, EventListActivity.class); 
		startActivity(i);
=======
		
		Button favorites = (Button) findViewById(R.id.favoritter);
		Button program = (Button) findViewById(R.id.program);
		Button artists = (Button) findViewById(R.id.artister);
		Button places = (Button) findViewById(R.id.steder);
		Button update = (Button) findViewById(R.id.update);
		
		favorites.setOnClickListener(this);
		program.setOnClickListener(this);
		artists.setOnClickListener(this);
		places.setOnClickListener(this);
		update.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case (R.id.program):
			intent = new Intent().setClass(this, EventListActivity.class);
			startActivity(intent);
			break;
		case (R.id.update):{
			updateEvents();
			break;
		}
		case (R.id.artister):{
			break;
		}
		case (R.id.steder):{
			break;
		}

		default:
			break;
		}
>>>>>>> dac1291bd67dcb18755d749ad320b239a8fe3bc8
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
<<<<<<< HEAD
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
=======

				if (intent.getAction().equals(IntentMessages.BROADCAST_INTENT_TOKEN)) {
					Log.w("BroadcastIntentDebug", "---------"); 
				}
			}
		}
}
>>>>>>> dac1291bd67dcb18755d749ad320b239a8fe3bc8
