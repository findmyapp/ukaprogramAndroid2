package no.uka.findmyapp.ukeprogram.adapters;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.utils.Constants;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalenderAdapter extends BaseAdapter
{
	Context myContext;

	public CalenderAdapter(Context _MyContext)
	{
		myContext = _MyContext;
	}

	@Override
	public int getCount() 
	{
		/* Set the number of element we want on the grid */
		return 29;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View myView = convertView;
		int uKAstartDate = 6;
		int uKAendDate = 30;
//		Day day = (int) getItemId(position);

		if ( convertView == null )
		{
			/*we define the view that will display on the grid*/
			//Inflate the layout
			Context context;
			context = parent.getContext();
			//LayoutInflater li = LayoutInflater.from(context);
			LayoutInflater li = (LayoutInflater)context.getSystemService      (Context.LAYOUT_INFLATER_SERVICE);
			//LayoutInflater li = getLayoutInflater();
			myView = li.inflate(R.layout.calender_adapter, null);	
		}
		
		TextView tv = (TextView) myView.findViewById(R.id.calendar_text);
		// Add The Text!!!

		if (Constants.DEBUG) Log.i("myView",tv.getId() + " Rid = " + R.id.calendar_text);
		tv.setText("" + getDates(uKAstartDate, uKAendDate)[position]);//+ position );
		
		//add 3 to pos to get the date
		if (3 + position <  uKAstartDate | position+3 > uKAendDate){
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
	
	public int[] getDates(int uKAstartDate, int uKAendDate){
		int[] days = new int[29];
		for(int i = 3; i<=31;i++){
			days[i-3] = i;
		}
		return days;
	}
}
