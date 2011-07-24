package no.uka.findmyapp.ukaprogram.activities.lists;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.activities.EventDetailsActivity;
import no.uka.findmyapp.ukaprogram.adapters.ConsertListCursorAdapter;
import no.uka.findmyapp.ukaprogram.mapper.UkaEventMapper;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class FavouritesListActivity extends ListActivity {
	private final static String debug = "FavouritesListListActivity";
	
	private final static String CONCERT = "'konsert'";
	private Cursor eventCursor;

	private final static String ORDER_BY = UkaEventContract.TITLE + " desc";
	public final static String ITEM_CLICKED = "clicked";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.concert_list);
		
		Log.v(debug, "Inside onCreate");
		
		Log.v(debug, "UkaEventContract.EVENT_TYPE fieldname = " + UkaEventContract.EVENT_TYPE );
		Log.v(debug, "CONCERT = " + CONCERT );
		Log.v(debug, "WHERE " + UkaEventContract.CANCELED + " = 1" );

		
		String selection = UkaEventContract.CANCELED + " = 1";
		
		eventCursor = this.managedQuery(UkaEventContract.EVENT_CONTENT_URI, null, selection, null, ORDER_BY);
		this.setListAdapter(new ConsertListCursorAdapter(this, eventCursor));
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor c = (Cursor) l.getItemAtPosition(position);

		UkaEvent event = UkaEventMapper.getUkaEventFromCursor(c);
		Log.v(debug, event.toString());

		Intent intent = new Intent(this, EventDetailsActivity.class); 
		intent.putExtra(ITEM_CLICKED, event);

		startActivity(intent);
	}
}