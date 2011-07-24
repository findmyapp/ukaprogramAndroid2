/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class CalenderAdapter.
 */
public class CalenderAdapter extends BaseAdapter
{	
	
	/** The s uka year. */
	private static int sUkaYear = 2011; 
	
	/** The s uka month. */
	private static int sUkaMonth = 10; 
	
	/** The s uka start date. */
	private static int sUkaStartDate = 6;
	
	/** The s uka end date. */
	private static int sUkaEndDate = 30;
	
	/** The s uka start day. */
	private static int sUkaStartDay = 3; 
	
	/** The m context. */
	private Context mContext;

	/**
	 * Instantiates a new calender adapter.
	 *
	 * @param context the context
	 */
	public CalenderAdapter(Context context) { mContext = context; }

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() { return sUkaEndDate - sUkaStartDate; }

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
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

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Gets the dates.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the dates
	 */
	public int[] getDates(int startDate, int endDate){
		int[] days = new int[endDate];
		for(int i = startDate; i<=endDate; i++){
			Log.v("counter", "i: " + i + " startDate = " + startDate + " stopdate = " + endDate); 
			days[i] = i;
		}
		return days;
	}
}
