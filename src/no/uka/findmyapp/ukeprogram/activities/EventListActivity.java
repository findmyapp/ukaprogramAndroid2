package no.uka.findmyapp.ukeprogram.activities;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.adapters.EventListCursorAdapter;
import no.uka.findmyapp.ukeprogram.models.UkaEvent;
import no.uka.findmyapp.ukeprogram.providers.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukeprogram.utils.Constants;
import no.uka.findmyapp.ukeprogram.utils.DateUtils;
import no.uka.findmyapp.ukeprogram.wrapper.EventDatabase;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EventListActivity extends ListActivity implements OnClickListener {

	private final static String debug = "EventListActivity";
	
	private final static String KONSERT = "'Konsert'";
	private final static String REVY = "'Revy og teater'";
	private final static String KURS = "'Kurs og events'";
	private final static String FEST = "'Fest og moro'";

	private Cursor eventCursor;

	private final static String ORDER_BY = UkaEventContract.SHOWING_TIME
			+ " asc";
	public final static String ITEM_CLICKED = "clicked";

	public static final int DATE_WAS_CHOSEN = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_list);

		initView();
	}

	private void initView() {
		Button categoryButton = (Button) findViewById(R.id.event_list_category_button);
		Button calendarButton = (Button) findViewById(R.id.event_list_calendar_button);
		Drawable drawable = getResources()
				.getDrawable(R.drawable.katbuttonalle);
		String whereStatement = getResources().getString(R.string.alle);

		categoryButton.setOnClickListener(this);
		calendarButton.setOnClickListener(this);

		refresh(drawable, R.color.alle, null, whereStatement);
	}

	/*
	 * Starts the EventDetailsActivity with selected event
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor c = (Cursor) l.getItemAtPosition(position);
		
		UkaEvent event = EventDatabase.getInstance().getEventFromCursor(c);
		if (Constants.DEBUG) Log.v(debug, event.toString());

		Intent intent = new Intent(this, EventDetailsActivity.class);
		intent.putExtra(ITEM_CLICKED, event);

		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.event_list_category_button:
			registerForContextMenu(v);
			v.setLongClickable(false);
			openContextMenu(v);
			break;

		case R.id.event_list_calendar_button:
			Intent intent = new Intent().setClass(this, CalendarActivity.class);
			startActivityForResult(intent, DATE_WAS_CHOSEN);
			break;
		default:
			break;
		}
	}
	
	/*
	 *  This method runs if a date is selected in the calendar-activity.
	 *  Displays all events on the selected date in the listview
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == DATE_WAS_CHOSEN) {
			Bundle bundle = data.getExtras();
			int selectedDateInCalendar = bundle.getInt(CalendarActivity.SELECTED_DATE);
			String whereStatement = createWhereStatementForEventsOnDay(selectedDateInCalendar);
			Drawable drawable = getResources().getDrawable(R.drawable.katbuttonalle);
			int color = R.color.alle;
			String categoryName = selectedDateInCalendar + getResources().getString(R.string.spesifikk_dato);
			
			if (Constants.DEBUG) Log.v(debug, "Wherestatement length: " + whereStatement.length());
			
			refresh(drawable, color, whereStatement, categoryName);
		}
	}

	/*  
	 * Creates a WHERE-clause for the SQL-query. Chains all event-ids in a OR-
	 * expression. If no events matches the date it returns a WHERE-clause that returns
	 * an empty cursor results. 
	 */
	private String createWhereStatementForEventsOnDay(int selectedDateInCalendar) {
		DateUtils dateUtil = new DateUtils();
		StringBuilder sb = new StringBuilder();

		eventCursor = this.managedQuery(UkaEventContract.EVENT_CONTENT_URI, null, null, null, ORDER_BY);
		eventCursor.moveToFirst();
		
		int eventsFound = 0;
		
		while (eventCursor.isAfterLast() == false) {
			long eventTimestamp = eventCursor.getLong(eventCursor.getColumnIndex(UkaEventContract.SHOWING_TIME));
			int day = dateUtil.getDayIntFromTimestamp(eventTimestamp);
			
			if (day == selectedDateInCalendar) {
				if (eventsFound > 0) 
					sb.append(" OR ");
				
				int eventId = eventCursor.getInt(eventCursor.getColumnIndex(UkaEventContract.ID));
				sb.append(UkaEventContract.ID + " = " + eventId);
				eventsFound++;
			}
			eventCursor.moveToNext();
		}
		
		if (eventsFound == 0) {
			sb.append(UkaEventContract.ID + " = " + Integer.MIN_VALUE);
		}
		
		if (Constants.DEBUG) Log.v(debug, "Returning where clause: " + sb.toString());
		
		return sb.toString();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.category_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return setUpdateSettings(item);
	}

	private boolean setUpdateSettings(MenuItem item) {
		Drawable drawable;
		int color;
		String whereStatement;
		String categoryName;
		switch (item.getItemId()) {
		case R.id.category_menu_alle:
			drawable = getResources().getDrawable(R.drawable.katbuttonalle);
			color = R.color.alle;
			categoryName = getResources().getString(R.string.alle);
			whereStatement = null;
			refresh(drawable, color, whereStatement, categoryName);
			return true;

		case R.id.category_menu_fest:
			whereStatement = UkaEventContract.EVENT_TYPE + " = " + FEST;
			drawable = getResources().getDrawable(R.drawable.katbuttonfest);
			color = R.color.fest;
			categoryName = getResources().getString(R.string.fest);
			refresh(drawable, color, whereStatement, categoryName);
			return true;

		case R.id.category_menu_konsert:
			whereStatement = UkaEventContract.EVENT_TYPE + " = " + KONSERT;
			drawable = getResources().getDrawable(R.drawable.katbuttonkonsert);
			color = R.color.konsert;
			categoryName = getResources().getString(R.string.konsert);
			refresh(drawable, color, whereStatement, categoryName);
			return true;

		case R.id.category_menu_kurs:
			whereStatement = UkaEventContract.EVENT_TYPE + " = " + KURS;
			drawable = getResources().getDrawable(R.drawable.katbuttonkurs);
			color = R.color.kurs;
			categoryName = getResources().getString(R.string.kurs);
			refresh(drawable, color, whereStatement, categoryName);
			return true;

		case R.id.category_menu_revy:
			whereStatement = UkaEventContract.EVENT_TYPE + " = " + REVY;
			drawable = getResources().getDrawable(R.drawable.katbuttonrevy);
			color = R.color.revy;
			categoryName = getResources().getString(R.string.revy);
			refresh(drawable, color, whereStatement, categoryName);
			return true;

		default:
			return false;
		}
	}

	private void refresh(Drawable drawable, int color, String whereStatement,
			String categoryName) {
		if (Constants.DEBUG) Log.v(debug, "Refreshing page");
		
		Button categoryButton = (Button) findViewById(R.id.event_list_category_button);
		LinearLayout line = (LinearLayout) findViewById(R.id.event_list_line);
		line.setBackgroundColor(getResources().getColor(color));
		categoryButton.setBackgroundDrawable(drawable);
		categoryButton.setText(categoryName);
		
		eventCursor = this.managedQuery(UkaEventContract.EVENT_CONTENT_URI,
				null, whereStatement, null, ORDER_BY);

		this.setListAdapter(new EventListCursorAdapter(this, eventCursor));
	}

}