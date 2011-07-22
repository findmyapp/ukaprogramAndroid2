package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.LocationListCursorAdapter;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

public class LocationListActivity extends ListActivity implements OnClickListener {
	
	private final static String debug = "EventListActivity";
	public final static String ITEM_CLICKED = "clicked";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.location_list);

		Cursor locationCursor = this.managedQuery(LocationContract.LOCATION_CONTENT_URI,
				null, null, null, null);
		

		this.setListAdapter(new LocationListCursorAdapter(this, locationCursor));
	}

	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor c = (Cursor) l.getItemAtPosition(position);

		UkaEvent event = EventDatabase.getInstance().getEventFromCursor(c);
		Log.v(debug, event.toString());

		Intent intent = new Intent(this, EventDetailsActivity.class);
		intent.putExtra(ITEM_CLICKED, event);

		startActivity(intent);
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
