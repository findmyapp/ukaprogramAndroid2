package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.adapters.CalenderAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CalendarActivity extends Activity 
{
	GridView calenderGrid;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);

		/*Here we setContentView() to calendar.xml, get the GridView and then fill it with the 
	                   ImageAdapter class that extend from BaseAdapter */

		calenderGrid = (GridView)findViewById(R.id.MyGrid);
		calenderGrid.setAdapter(new CalenderAdapter(this));
		
		calenderGrid.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				//viewContact.setClass(getApplicationContext(), ContactDetailsActivity.class);
				/**
					databaseHandler.open();
					databaseHandler.removePerson(contactArrayList.get(position).getId());
					databaseHandler.close();
					populateList();
				 */
				Intent viewDate = new Intent();
				viewDate.setClass(getApplicationContext(), DateActivity.class);
				Log.i("e","date pressed was: " + (position + 3));
				viewDate.putExtra("SelectedDate", (position + 3));///////
				startActivity(viewDate); 
			}
		});
	}
}


//	public class ImageAdapter extends BaseAdapter
//	{
//		Context myContext;
//
//		public ImageAdapter(Context _MyContext)
//		{
//			myContext = _MyContext;
//		}
//
//		@Override
//		public int getCount() 
//		{
//			/* Set the number of element we want on the grid */
//			return getDates().length;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) 
//		{
//			View myView = convertView;
//	//		Day day = (int) getItemId(position);
//
//			if ( convertView == null )
//			{
//				/*we define the view that will display on the grid*/
//				//Inflate the layout
//				LayoutInflater li = getLayoutInflater();
//				myView = li.inflate(R.layout.calendar_adapter, null);	
//			}
//			
//			TextView tv = (TextView) myView.findViewById(R.id.calendar_text);
//			// Add The Text!!!
//
//			Log.i("myView",tv.getId() + " Rid = " + R.id.calendar_text);
//			tv.setText("" + getDates()[position]);//+ position );
//			
//			return myView;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//		
//		public int[] getDates(){
//			int[] days = new int[31];
//			for(int i = 1; i<32;i++){
//				days[i-1] = i;
//				}
//			return days;
//		}
//	}


