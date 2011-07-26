package no.uka.findmyapp.ukaprogram.activities.lists;

import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.LocationListCursorAdapter;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class LocationListActivity extends ListActivity implements OnClickListener {
	
	@SuppressWarnings("unused")
	private final static String debug = "EventListActivity";

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
