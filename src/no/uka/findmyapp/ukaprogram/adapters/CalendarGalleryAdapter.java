/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.adapters;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarGalleryAdapter.
 */
public class CalendarGalleryAdapter extends BaseAdapter{
	 
 	/** The m gallery item background. */
 	int mGalleryItemBackground;
	    
    	/** The m context. */
    	private Context mContext;
	    
    	/** The Constant debug. */
    	private final static String debug = "CalendarGalleryAdapter";

	    /**
    	 * Instantiates a new calendar gallery adapter.
    	 *
    	 * @param c the c
    	 */
    	public CalendarGalleryAdapter(Context c) {
	        mContext = c;
	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.DateItemCarousel);
	        /*mGalleryItemBackground = attr.getResourceId(
	                R.styleable.DateItemCarousel_android_galleryItemBackground, 0);*/
	        attr.recycle();
	    }

	    /* (non-Javadoc)
    	 * @see android.widget.Adapter#getCount()
    	 */
    	public int getCount() {
	    	//Returns number of days + 2
	        return (ApplicationConstants.UKA_END_DATE - ApplicationConstants.UKA_START_DATE + 3); 
	    }

	    /* (non-Javadoc)
    	 * @see android.widget.Adapter#getItem(int)
    	 */
    	public Object getItem(int position) {
	        return position;
	    }

	    /* (non-Javadoc)
    	 * @see android.widget.Adapter#getItemId(int)
    	 */
    	public long getItemId(int position) {
	        return position;
	    }

	    /* (non-Javadoc)
    	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
    	 */
    	public View getView(int position, View convertView, ViewGroup parent) {

	    	View test =  View.inflate(mContext, R.layout.date_item, null);
	    	LinearLayout layout = null;
	    	test.setLayoutParams(new Gallery.LayoutParams(150, 100));
	    	test.setBackgroundResource(mGalleryItemBackground);
	        return initTextViews(test, position);
	    }
	    
	    /**
    	 * Inits the text views.
    	 *
    	 * @param test the test
    	 * @param position the position
    	 * @return the view
    	 */
    	public View initTextViews(View test, int position){
	    	TextView dayNumber;
	    	TextView weekday;
	    	int day = position + ApplicationConstants.UKA_START_DATE - 1;
	    	dayNumber = (TextView) test.findViewById(R.id.date_item_day_number);
	    	weekday = (TextView) test.findViewById(R.id.date_item_weekday);
	    	
	    	if (position == 0 || position == getCount()-1){
		    	dayNumber.setText(R.string.dayGallery_all);
	    	}
	    	else{
		    	dayNumber.setText(String.valueOf(day));
		    	weekday.setText(DateUtils.getShortWeekDayName(day));
	    	}
	    	
	    	return test;
	    }
}
