package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.ConsertListCursorAdapter;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcertListActivity.
 */
public class ConcertListActivity extends ListActivity {	
	
	/** The Constant debug. */
	private final static String debug = "ConcertListActivity";
	
	/** The Constant CONCERT. */
	private final static String CONCERT = "'konsert'";
	
	/** The event cursor. */
	private Cursor eventCursor;

	/** The Constant ORDER_BY. */
	private final static String ORDER_BY = UkaEventContract.TITLE + " desc";
	
	/** The Constant ITEM_CLICKED. */
	public final static String ITEM_CLICKED = "clicked";

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.concert_list);
		
		Log.v(debug, "Inside onCreate");
		
		Log.v(debug, "UkaEventContract.EVENT_TYPE fieldname = " + UkaEventContract.EVENT_TYPE );
		Log.v(debug, "CONCERT = " + CONCERT );
		Log.v(debug, "WHERE " + UkaEventContract.EVENT_TYPE + " = " + CONCERT);

		
		String selection = UkaEventContract.EVENT_TYPE + " = " + CONCERT;
		
		eventCursor = this.managedQuery(UkaEventContract.EVENT_CONTENT_URI, null, selection, null, ORDER_BY);
		this.setListAdapter(new ConsertListCursorAdapter(this, eventCursor));
	}


	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor c = (Cursor) l.getItemAtPosition(position);

		UkaEvent event = EventDatabase.getInstance().getEventFromCursor(c);
		Log.v(debug, event.toString());

		Intent intent = new Intent(this, EventDetailsActivity.class); 
		intent.putExtra(ITEM_CLICKED, event);

		startActivity(intent);
	}
}