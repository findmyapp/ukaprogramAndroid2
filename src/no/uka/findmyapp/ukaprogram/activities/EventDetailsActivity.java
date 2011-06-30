package no.uka.findmyapp.ukaprogram.activities;


import no.uka.findmyapp.ukaprogram.R;
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

		Bundle extras = getIntent().getExtras();
		selectedEvent = new Event();
		
		
		if (extras != null) {
			selectedEvent = (Event) extras.getSerializable("SelectedEvent");

			TextView ageLimit = (TextView) findViewById(R.id.ageLimit);
			TextView price = (TextView) findViewById(R.id.price);
			TextView title = (TextView) findViewById(R.id.title);
			TextView time_and_place= (TextView) findViewById(R.id.time_and_place);
			TextView description = (TextView) findViewById(R.id.description);
			
			time_and_place.setText(selectedEvent.getWeekday() + " " + selectedEvent.getDayNumber()+" okt. " + selectedEvent.getStartTime() + ", " + selectedEvent.getPlace());
			title.setText(selectedEvent.getTitle());
			description.setText(selectedEvent.getDescription());
			ageLimit.setText("Aldersgrense: " + selectedEvent.getAgeLimit() + " år");
			if(selectedEvent.isFree()){
				price.setText("Gratis");
			}
			else{
				price.setText("Pris: " + selectedEvent.getPrice() +" kr");
			}

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