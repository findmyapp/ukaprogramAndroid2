package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.ukaprogram.R;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalenderAdapter extends BaseAdapter
{	
	
	private static int sUkaYear = 2011; 
	private static int sUkaMonth = 10; 
	private static int sUkaStartDate = 6;
	private static int sUkaEndDate = 30;
	private static int sUkaStartDay = 3; 
	private Context mContext;

	public CalenderAdapter(Context context) { mContext = context; }

	@Override
	public int getCount() { return sUkaEndDate - sUkaStartDate; }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View myView = convertView;

		if(convertView == null) {
			/*we define the view that will display on the grid
			//Inflate the layout*/
			LayoutInflater li = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myView = li.inflate(R.layout.calender_adapter, null);
		}
		
		TextView tv = (TextView) myView.findViewById(R.id.calendar_text);
		// Add The Text!!!

		Log.i("myView",tv.getId() + " Rid = " + R.id.calendar_text);
		tv.setText("" + getDates(sUkaStartDate, sUkaEndDate)[position]);
		
		//add 3 to pos to get the date
		if (sUkaStartDay + position <  sUkaStartDate | position + sUkaStartDay > sUkaEndDate){
			tv.setTextColor(Color.GRAY);
		}
		return myView;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int[] getDates(int startDate, int endDate){
		int[] days = new int[endDate];
		for(int i = startDate; i<=endDate; i++){
			Log.v("counter", "i: " + i + " startDate = " + startDate + " stopdate = " + endDate); 
			days[i] = i;
		}
		return days;
	}
}
