package no.uka.findmyapp.ukaprogram.activities;

import java.net.URI;
import java.net.URISyntaxException;

import no.uka.findmyapp.android.library.R.color;
import no.uka.findmyapp.android.rest.client.IntentMessages;
import no.uka.findmyapp.android.rest.client.RestServiceHelper;
import no.uka.findmyapp.android.rest.client.UkappsServices;
import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.constants.ServiceDataFormat;
import no.uka.findmyapp.android.rest.datamodels.core.ServiceModel;
import no.uka.findmyapp.android.rest.datamodels.enums.HttpType;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.ColorUtils;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;



public class Main extends PopupMenuActivity implements OnClickListener {
	private static final String debug = "Main";

	private static RestServiceHelper serviceHelper = RestServiceHelper.getInstance(); 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		updateEvents();
	}

	private void initMenu() {
		setContentView(R.layout.main_menu);
		
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
		
		program.setBackgroundResource(R.drawable.mainbuttonprogram);
		favorites.setBackgroundResource(R.drawable.mainbuttonfav);
		artists.setBackgroundResource(R.drawable.mainbuttonartister);
		places.setBackgroundResource(R.drawable.mainbuttonsteder);
		places.setHighlightColor(R.color.uka_pink);
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
					IntentMessages.BROADCAST_INTENT_TOKEN,
					null);
			
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
			if (intent.getAction().equals(IntentMessages.BROADCAST_INTENT_TOKEN)) {
				initMenu();
			}
		}
	}
}

