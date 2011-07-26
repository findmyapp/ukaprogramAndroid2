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

public class CalendarGalleryAdapter extends BaseAdapter implements OnItemClickListener{
	 int mGalleryItemBackground;
	    private Context mContext;
	    private final static String debug = "CalendarGalleryAdapter";
	    private Integer[] mDateItemsIds = {
	    		
	    		 
	    };

	    public CalendarGalleryAdapter(Context c) {
	        mContext = c;
	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.DateItemCarousel);
	        mGalleryItemBackground = attr.getResourceId(
	                R.styleable.DateItemCarousel_android_galleryItemBackground, 0);
	        attr.recycle();
	    }

	    public int getCount() {
	        return (ApplicationConstants.UKA_END_DATE - ApplicationConstants.UKA_START_DATE + 1); //mDateItemsIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {

	    	View test =  View.inflate(mContext, R.layout.date_item, null);
	    	LinearLayout layout = null;
	    	test.setLayoutParams(new Gallery.LayoutParams(150, 100));
	    	test.setBackgroundResource(mGalleryItemBackground);
	        return initTextViews(test, position);
	    }
	    
	    public View initTextViews(View test, int position){
	    	TextView dayNumber;
	    	TextView weekday;
	    	int day = position + ApplicationConstants.UKA_START_DATE;
	    	
	    	dayNumber = (TextView) test.findViewById(R.id.date_item_day_number);
	    	weekday = (TextView) test.findViewById(R.id.date_item_weekday);
	    	
	    	dayNumber.setText(String.valueOf(day));
	    	weekday.setText(DateUtils.getShortWeekDayName(day));
	    	return test;
	    }


		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Log.v(debug, "YO!");
			// TODO Auto-generated method stub
			
		}
}
