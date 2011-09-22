package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.restlibrary.IntentMessages;
import no.uka.findmyapp.ukaprogram.utils.EventsUpdater;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


public class Main extends Activity implements OnClickListener {
	
	
	private BroadcastReceiver receiver;
	protected ProgressDialog loadingDialog; // Displays when download program.
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_menu);
		initView();
		
		
		// This listener receives a notification when the program is loaded after the refresh-button is pressed, then dismisses the
		// loading dialog.
		IntentFilter filter = new IntentFilter();
		filter.addAction(IntentMessages.BROADCAST_INTENT_TOKEN);
		
		receiver = new BroadcastReceiver() {
			@Override
		    public void onReceive(Context context, Intent intent) {
				loadingDialog.dismiss();
		    }
		 };

		 registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	private void initView() {
		Button favorites = (Button) findViewById(R.id.favoritter);
		Button program = (Button) findViewById(R.id.program);

		Button settings = (Button) findViewById(R.id.innstillinger);
		Button loginbutton = (Button) findViewById(R.id.login);
		
		FrameLayout refresh = (FrameLayout) findViewById(R.id.refresh);
		
		favorites.setOnClickListener(this);
		program.setOnClickListener(this);
		settings.setOnClickListener(this);
		loginbutton.setOnClickListener(this);
		refresh.setOnClickListener(this);
		
		settings.setVisibility(View.GONE);
		loginbutton.setVisibility(View.GONE);
		
		program.setBackgroundResource(R.drawable.mainmenu_program);
		favorites.setBackgroundResource(R.drawable.mainmenu_favorites);
		settings.setBackgroundResource(R.drawable.mainmenu_settings);
		loginbutton.setBackgroundResource(R.drawable.mainmenu_login);
		//refreshButton.setBackgroundResource(R.drawable.refreshicon);
	}
	
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case (R.id.program):
			intent = new Intent().setClass(this, EventListActivity.class);
			startActivity(intent);
			break;
		case (R.id.favoritter):
			intent = new Intent().setClass(this, FavoritesListActivity.class);
			startActivity(intent);
			break; 
		case (R.id.login):
			Log.d("haba haba", "ASDFASDF");
			Toast.makeText(this, "Login pressed.", Toast.LENGTH_SHORT).show();
			break;
		case (R.id.innstillinger):
			Toast.makeText(this, "Innstillinger pressed", Toast.LENGTH_SHORT).show();
			break;
		case (R.id.refresh):
			this.updateEvents();
		default:
			break;
		}
	}
	
	public void updateEvents()
	{
		EventsUpdater eu = new EventsUpdater(getApplicationContext());
		eu.updateEvents();
		loadingDialog = ProgressDialog.show(Main.this, "", "Oppdaterer program. Vennligst vent..", true);
	}
}
