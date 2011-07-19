package no.uka.findmyapp.ukaprogram.adapters;

import java.util.List;

import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EventListAdapter extends ArrayAdapter<UkaEvent> {
	private int textViewResourceId;

	public EventListAdapter(Context context, int textViewResourceId, List<UkaEvent> items) {	
		super(context, textViewResourceId, items);
		this.textViewResourceId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		LinearLayout eventView = null;
		UkaEvent event = getItem(position);

		if(convertView == null){
			eventView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(textViewResourceId, eventView, true);
		}
		else
		{
			eventView = (LinearLayout) convertView;
		}
		Log.v("EventListAdapter", "Inside");
		TextView title = (TextView) eventView.findViewById(R.id.title);
		TextView startTime = (TextView) eventView.findViewById(R.id.time);
		TextView weekday = (TextView) eventView.findViewById(R.id.weekday);
		TextView dayNumber = (TextView) eventView.findViewById(R.id.dayNumber2);
		TextView place = (TextView) eventView.findViewById(R.id.place);
		
		title.setText(event.getTitle());
		place.setText(event.getPlace());
		startTime.setText(event.getStartTime());
		dayNumber.setText(String.valueOf(event.getDayNumber()));
		weekday.setText("tors");

		
		return eventView;
	}
}
