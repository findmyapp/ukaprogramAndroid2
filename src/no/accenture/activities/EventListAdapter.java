package no.accenture.activities;

import java.util.List;

import no.accenture.model.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EventListAdapter extends ArrayAdapter<Event> {
	private int textViewResourceId;

	public EventListAdapter(Context context, int textViewResourceId, List<Event> items) {	
		super(context, textViewResourceId, items);
		this.textViewResourceId = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		LinearLayout eventView = null;
		Event event = getItem(position);

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

		TextView title = (TextView) eventView.findViewById(R.id.title);
		TextView body = (TextView) eventView.findViewById(R.id.place);
		TextView eventType = (TextView) eventView.findViewById(R.id.eventType);

		title.setText(event.getTitle());
		body.setText(event.getPlace());
		eventType.setText(event.getEventType());

		return eventView;
	}
}
