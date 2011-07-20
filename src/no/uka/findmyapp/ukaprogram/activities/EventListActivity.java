package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.EventListCursorAdapter;
import no.uka.findmyapp.ukaprogram.wrapper.EventDatabase;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EventListActivity extends ListActivity implements OnClickListener{	
	private final static String debug = "EventListActivity";
	
	private final static String ORDER_BY = UkaEventContract.SHOWING_TIME + " asc";
	public final static String ITEM_CLICKED = "clicked";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.event_list);
		Cursor eventCursor = this.managedQuery(UkaEventContract.EVENT_CONTENT_URI, null, null, null, ORDER_BY);
		
		this.setListAdapter(new EventListCursorAdapter(this, eventCursor));
		Button categoryButton = (Button) findViewById(R.id.event_list_category_button);
		categoryButton.setOnClickListener(this);
		
	}

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
	
	@Override
	public void onClick(View v) {
		registerForContextMenu(v);
		v.setLongClickable(false);
		openContextMenu(v);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.category_menu, menu);
	}
}