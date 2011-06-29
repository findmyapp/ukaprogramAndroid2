package no.uka.findmyapp.ukaprogram.activities;


import no.accenture.activities.R;
import no.uka.findmyapp.ukaprogram.models.Event;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetailsActivity extends Activity {

	private Event selectedEvent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		TextView band = (TextView) findViewById(R.id.band);
		band.setText("Kaizers");
		
		Bundle extras = getIntent().getExtras();
		selectedEvent = new Event();
		
		if (extras != null) {
			selectedEvent = (Event) extras.getSerializable("SelectedEvent");
			band.setText(selectedEvent.getTitle());
			/**Intent editContact = new Intent();
			editContact.putExtra("SelectedContact", selectedContact);
			
			TextView tv_firstname = (TextView) findViewById(R.id.lbl_name);
			TextView tv_phone = (TextView)findViewById(R.id.lbl_phone);
			TextView tv_mail = (TextView)findViewById(R.id.lbl_email);
			
			tv_firstname.setText(selectedContact.getFirstname() + " " + selectedContact.getLastname());
			tv_phone.setText("Phone: " + selectedContact.getPhone());
			tv_mail.setText("Mail: " + selectedContact.getEmail());
			*/
		}
		
	}
}