/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.activities;

import java.util.ArrayList;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.activities.lists.EventListActivity;
import no.uka.findmyapp.ukaprogram.activities.lists.LocationListActivity;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.utils.EventsUpdater;
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

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main extends PopupMenuActivity implements OnClickListener, OnGesturePerformedListener 
{
	/** The Constant debug. */
	@SuppressWarnings("unused")
	private static final String debug = "Main";

	/** The gesture lib. */
	private GestureLibrary mGestureLib; 
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_menu);
		
		/* Initializing the gesture layer */
		gestureInit();
		
		/* Initializing the view*/
		initView();
	}

	/**
	 * Inits the view.
	 */
	private void initView() {
		Button menuButton = (Button) findViewById(R.id.mainMenu_news);
		menuButton.setBackgroundResource(R.drawable.mainbutton_news);
		menuButton.setOnClickListener(this);
		
		menuButton = (Button) findViewById(R.id.mainMenu_program);
		menuButton.setOnClickListener(this);
		menuButton.setBackgroundResource(R.drawable.mainbutton_program);
		
		menuButton = (Button) findViewById(R.id.mainMenu_places);
		menuButton.setOnClickListener(this);
		menuButton.setBackgroundResource(R.drawable.mainbutton_places);
		
	
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.mainMenu_program):
			gotoActivity(EventListActivity.class);
			break;
		case (R.id.mainMenu_news):
			//gotoActivity(FavouritesListActivity.class);
			gotoActivity(NewsActivity.class);
			break; 
		case (R.id.mainMenu_places):
			gotoActivity(LocationListActivity.class);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Goto activity.
	 *
	 * @param <T> the generic type
	 * @param activity the activity
	 */
	public <T> void gotoActivity(Class<T> activity) {
		Intent intent = new Intent().setClass(this, activity);
		startActivity(intent);
	}
	
	/**
	 * Gesture init.
	 */
	private void gestureInit() {
		GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
		View inflate = getLayoutInflater().inflate(R.layout.main_menu, null);
		gestureOverlayView.addView(inflate);
		gestureOverlayView.addOnGesturePerformedListener(this);
		gestureOverlayView.setGestureVisible(false);
		mGestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!mGestureLib.load()) {
			finish();
		}
		setContentView(gestureOverlayView);
	}

	/* (non-Javadoc)
	 * @see android.gesture.GestureOverlayView.OnGesturePerformedListener#onGesturePerformed(android.gesture.GestureOverlayView, android.gesture.Gesture)
	 */
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mGestureLib.recognize(gesture);
		for (Prediction prediction : predictions) {
			if (prediction.score > 3.0) {
				if(prediction.name.equals(ApplicationConstants.GESTURE_DELETE)) {
					EventsUpdater eu = new EventsUpdater(getApplicationContext());
					eu.clearEventTable(getContentResolver());
				}
				else if(prediction.name.equals(ApplicationConstants.GESTURE_UPDATE)) {
					EventsUpdater eu = new EventsUpdater(getApplicationContext());
					eu.updateEvents(); 
				}
			}
		}
	}
}