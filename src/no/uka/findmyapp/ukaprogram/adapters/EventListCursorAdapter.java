package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class EventListCursorAdapter extends CursorAdapter implements OnClickListener{
		private static final String debug = "EventListCursorAdapter";

	private Cursor cursor;
	private final LayoutInflater inflater;
	
	public EventListCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		this.inflater = LayoutInflater.from(context);
		this.cursor = cursor;
	}
	
	@Override
	public void bindView(View eventView, Context context, Cursor cursor) {
		DateUtils du = new DateUtils(); 
		
		try {
			TextView t = (TextView) eventView.findViewById(R.id.listItemTitle);
			t.setText(getStringFromTableColumn(UkaEventContract.TITLE));
			
			t = (TextView) eventView.findViewById(R.id.listItemEventTime);
			t.setText(du.getTimeFromTimestamp(
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME)));
			
			t = (TextView) eventView.findViewById(R.id.listItemWeekday); 
			String showingEvent = du.getWeekdayNameFromTimestamp(
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME));
			t.setText(showingEvent);
			
			t = (TextView) eventView.findViewById(R.id.listItemDayNumber);
			t.setText(du.getCustomDateFormatFromTimestamp("dd",
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME)));
			
			t = (TextView) eventView.findViewById(R.id.listItemPlace);
			t.setText(getStringFromTableColumn(UkaEventContract.PLACE));
			
			CheckBox cb = (CheckBox) eventView.findViewById(R.id.listItemAttending);
			cb.setButtonDrawable(R.drawable.favorites_button);
			
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Log.v("onclick", "buttozRPhun, " + getStringFromTableColumn(UkaEventContract.TITLE));
				}
			});
		}
		catch(Exception e) {
			Log.v(debug, "Exception: " + e.getMessage());
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = this.inflater.inflate(R.layout.event_list_item, parent, false);
		return view;
	}

	private String getStringFromTableColumn(String tableColumnName) {
		return this.cursor.getString(this.cursor.getColumnIndex(tableColumnName));
	}
	
	private long getLongFromTableColumn(String tableColumnName) {
		return this.cursor.getLong(this.cursor.getColumnIndex(tableColumnName));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
