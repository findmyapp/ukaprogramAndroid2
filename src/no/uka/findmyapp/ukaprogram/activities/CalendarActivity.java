package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.CalenderAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CalendarActivity extends Activity {
	public static final String SELECTED_DATE = "SelectedDate";

	private GridView calenderGrid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calendar);

		calenderGrid = (GridView) findViewById(R.id.MyGrid);
		calenderGrid.setAdapter(new CalenderAdapter(this));

		calenderGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent viewDate = new Intent();
				viewDate.putExtra(SELECTED_DATE, (position + 3));
				
				setResult(EventListActivity.DATE_WAS_CHOSEN, viewDate);
				finish();
			}
		});
	}
}
