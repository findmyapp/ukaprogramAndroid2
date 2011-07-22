package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.EventsUpdater;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class Main extends PopupMenuActivity implements OnClickListener, OnGesturePerformedListener {
	private static final String debug = "Main";

	private GestureLibrary gestureLib; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_menu);
		gestureInit();
		initView();
	}


	private void initView() {
		Button favorites = (Button) findViewById(R.id.favoritter);
		Button program = (Button) findViewById(R.id.program);
		Button artists = (Button) findViewById(R.id.konserter);
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


	private void gestureInit() {
		GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
		View inflate = getLayoutInflater().inflate(R.layout.main_menu, null);
		gestureOverlayView.addView(inflate);
		gestureOverlayView.addOnGesturePerformedListener(this);
		gestureOverlayView.setGestureVisible(false);
		this.gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!gestureLib.load()) {
			finish();
		}
		setContentView(gestureOverlayView);
	}

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = this.gestureLib.recognize(gesture);
		for (Prediction prediction : predictions) {
			if (prediction.score > 3.0) {
				if(prediction.name.equals("delete")) {
					EventDatabase.getInstance().clearEventTable(getContentResolver());
				}
				else if(prediction.name.equals("update")) {
					EventsUpdater eu = new EventsUpdater(getApplicationContext());
					eu.updateEvents(); 
				}
			}
		}
	}
	
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case (R.id.program):
			intent = new Intent().setClass(this, EventListActivity.class);
			startActivity(intent);
			break;
		case (R.id.update):
			EventsUpdater eu = new EventsUpdater(getApplicationContext()); 
			eu.updateEvents();
			break;
		case (R.id.favoritter):
			intent = new Intent().setClass(this, FavoritesListActivity.class);
			startActivity(intent);
			break; 
		case (R.id.konserter):
			intent = new Intent().setClass(this, ConcertListActivity.class);
			startActivity(intent);
			break;
		case (R.id.steder):
			intent = new Intent().setClass(this, PlacesActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}


