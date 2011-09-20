package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.providers.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class EventListCursorAdapter extends CursorAdapter implements
		OnClickListener {
	private static final String debug = "EventListCursorAdapter";

	private Context context;
	private Cursor cursor;
	private final LayoutInflater inflater;

	public EventListCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		this.context = context;
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
			t.setText(du.getCustomDateFormatFromTimestamp("HH:mm",
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME)));

			t = (TextView) eventView.findViewById(R.id.listItemWeekday);
			String showingEvent = du
					.getWeekdayNameFromTimestamp(getLongFromTableColumn(UkaEventContract.SHOWING_TIME));
			t.setText(showingEvent);

			t = (TextView) eventView.findViewById(R.id.listItemDayNumber);
			t.setText(du.getCustomDateFormatFromTimestamp("dd",
					getLongFromTableColumn(UkaEventContract.SHOWING_TIME)));

			t = (TextView) eventView.findViewById(R.id.listItemPlace);
			t.setText(getStringFromTableColumn(UkaEventContract.PLACE_STRING));

			CheckBox cb = (CheckBox) eventView
					.findViewById(R.id.listItemAttending);
			cb.setButtonDrawable(R.drawable.favorites_button);
			cb.setChecked(getBooleanFromTableColumn(UkaEventContract.FAVOURITE));

			cb.setOnClickListener(new FavoriteButtonListener(
					this.cursor.getInt(this.cursor
							.getColumnIndex(UkaEventContract.ID))));

		} catch (Exception e) {
			Log.v(debug, "Exception: " + e.getMessage());
		}
	}

	private class FavoriteButtonListener implements OnClickListener {

		private int id;

		public FavoriteButtonListener(int id) {
			this.id = id;
		}

		@Override
		public void onClick(View paramView) {
			CheckBox boxClicked = (CheckBox) paramView;
			
			boolean isChecked = boxClicked.isChecked();
			
			ContentValues values = new ContentValues();
			values.put(UkaEventContract.FAVOURITE, isChecked);
			ContentResolver cr = context.getContentResolver();
			int updated = cr.update(UkaEventContract.EVENT_CONTENT_URI, values,
					UkaEventContract.ID + " = ?", new String[] { "" + id });
			
			Log.v(debug, "Updated rows: " + updated);
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = this.inflater.inflate(R.layout.event_list_item,
				parent, false);
		return view;
	}

	private String getStringFromTableColumn(String tableColumnName) {
		return this.cursor.getString(this.cursor
				.getColumnIndex(tableColumnName));
	}

	private long getLongFromTableColumn(String tableColumnName) {
		return this.cursor.getLong(this.cursor.getColumnIndex(tableColumnName));
	}

	private boolean getBooleanFromTableColumn(String tableColumnName) {
		if (this.cursor.getInt(this.cursor.getColumnIndex(tableColumnName)) == 1)
			return true;
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
