package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.R;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ConsertListCursorAdapter extends CursorAdapter{
		private static final String debug = "ConsertListCursorAdapter";

	private Cursor cursor;
	private final LayoutInflater inflater;
	
	public ConsertListCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		this.inflater = LayoutInflater.from(context);
		this.cursor = cursor;
	}
	
	@Override
	public void bindView(View eventView, Context context, Cursor cursor) {
		TextView t = (TextView) eventView.findViewById(R.id.concertListItemTitle);
		Log.v(debug, "set concertListItemTitle " + getStringFromTableColumn(UkaEventContract.TITLE));
		t.setText(getStringFromTableColumn(UkaEventContract.TITLE));
		/*
		Log.v(debug, "set concertListItemTitle " + getStringFromTableColumn(UkaEventContract.TITLE));
		t = (TextView) eventView.findViewById(R.id.concertListItemPlace);
		t.setText(getStringFromTableColumn(UkaEventContract.PLACE));
		
		CheckBox cb = (CheckBox) eventView.findViewById(R.id.concertListItemAttending);
		cb.setButtonDrawable(R.drawable.favorites_button);
		
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.v(debug, "buttozRPhun, " + getStringFromTableColumn(UkaEventContract.TITLE));
			}
		});
		
		*/
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = this.inflater.inflate(R.layout.event_list_item, parent, false);
		return view;
	}

	private String getStringFromTableColumn(String tableColumnName) {
		return this.cursor.getString(this.cursor.getColumnIndex(tableColumnName));
	}
}
