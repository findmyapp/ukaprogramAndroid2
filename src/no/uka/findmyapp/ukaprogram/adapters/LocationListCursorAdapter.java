package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.android.rest.contracts.Location.LocationContract;
import no.uka.findmyapp.ukaprogram.R;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class LocationListCursorAdapter extends CursorAdapter {
	
	private static final String debug = "LocationListCursorAdapter";
	private final LayoutInflater inflater;
	private Cursor cursor;

	public LocationListCursorAdapter(Context context, Cursor c) {
		super(context, c);
		this.inflater = LayoutInflater.from(context);
		this.cursor = c;
	}
	
	@Override
	public void bindView(View eventView, Context context, Cursor cursor) {		
		try {
			TextView t = (TextView) eventView.findViewById(R.id.locationListItemTitle);
			t.setText(getStringFromTableColumn(LocationContract.LOCATIONNAME));
			
			t = (TextView) eventView.findViewById(R.id.locationListHappeningNow);
			t.setText("Fest!");
			
		}
		catch(Exception e) {
			Log.v(debug, "Exception: " + e.getMessage());
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = this.inflater.inflate(R.layout.location_list_item, parent, false);
		return view;
	}

	private String getStringFromTableColumn(String tableColumnName) {
		return this.cursor.getString(this.cursor.getColumnIndex(tableColumnName));
	}
	
	private long getLongFromTableColumn(String tableColumnName) {
		return this.cursor.getLong(this.cursor.getColumnIndex(tableColumnName));
	}

}
