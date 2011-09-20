package no.uka.findmyapp.ukaprogram.activities;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import no.uka.findmyapp.ukaprogram.R;
import no.uka.findmyapp.ukaprogram.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.providers.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukaprogram.utils.DateUtils;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailsActivity extends Activity implements OnClickListener {
	private static final String debug = "EventsDetailsActivity";
	
	private static final String IMAGE_URL_PREFIX = "http://www.uka.no/";

	private UkaEvent selectedEvent; 
	private ImageView eventImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_details);
		
		Log.v(debug, debug + " onCreate()");
		
		Bundle bundle = getIntent().getExtras(); 
		
		if (bundle.getSerializable(EventListActivity.ITEM_CLICKED) != null) {
			selectedEvent = (UkaEvent) bundle.getSerializable(EventListActivity.ITEM_CLICKED);
			populateView();
		} else {
			String exception = "Error";
			Toast toast = Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	public void populateView(){
		Log.v(debug, "selectedEvent " + selectedEvent.toString());
		
		// Get Views
		TextView ageLimit = (TextView) findViewById(R.id.detailedEventAgeLimit);
		TextView price = (TextView) findViewById(R.id.detailedEventPrice);
		TextView title = (TextView) findViewById(R.id.detailedEventTitle);
		TextView timeAndPlace = (TextView) findViewById(R.id.detailedEventTimeAndPlace);
		TextView description = (TextView) findViewById(R.id.detailedEventDescription);
		TextView headerTitle = (TextView) findViewById(R.id.event_details_header_title);
		
		CheckBox favorites = (CheckBox) findViewById(R.id.event_details_favorites);
		favorites.setOnClickListener(this);
		favorites.setButtonDrawable(R.drawable.favorites_button);
		favorites.setChecked(selectedEvent.isFavourite());
		
		Button eventCalendarButton = (Button) findViewById(R.id.event_calendar_button);
		String eventDay = new DateUtils().getCustomDateFormatFromTimestamp("dd", selectedEvent.getShowingTime());
		eventCalendarButton.setText(eventDay);
		eventCalendarButton.setOnClickListener(new CalendarButtonListener());
		
		headerTitle.setText(selectedEvent.getTitle());
		timeAndPlace.setText(createTimeAndPlaceText(selectedEvent));
		
		eventImageView = (ImageView) findViewById(R.id.event_details_picture);
		loadImage();

		title.setText(createTitleText());
		ageLimit.setText("Aldersgrense: " + selectedEvent.getAgeLimit() + " år");

		if (selectedEvent.isFree()){
			price.setText("Gratis");
		} else {
			price.setText("Pris: " + selectedEvent.getLowestPrice() + " kr");
		}
		
		description.setText(selectedEvent.getLead());
		
		if(selectedEvent.isFavourite()) {
			favorites.setChecked(true);
		}
	}

	private String createTitleText() {
		String titleText = selectedEvent.getTitle();
		titleText += selectedEvent.isCanceled() ? " (AVLYST!)" : "";
		return titleText;
	}

	private void loadImage() {
		ImageDownloader downloader = new ImageDownloader(eventImageView);
		downloader.execute(IMAGE_URL_PREFIX + selectedEvent.getImage());
	}

	private String createTimeAndPlaceText(UkaEvent selectedEvent) {
		String timeAndPlaceText = "";
		DateUtils du = new DateUtils(); 
		timeAndPlaceText += selectedEvent.getPlaceString() + ", ";
		timeAndPlaceText += du.getWeekdayLongFromTimestamp(selectedEvent.getShowingTime()) + " ";
		timeAndPlaceText += du.getCustomDateFormatFromTimestamp("dd.MM", selectedEvent.getShowingTime()) + " kl " +
				du.getCustomDateFormatFromTimestamp("HH:mm", selectedEvent.getShowingTime());
		
		return timeAndPlaceText;
	}
	
	private class CalendarButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("title", selectedEvent.getTitle());
			intent.putExtra("beginTime", selectedEvent.getShowingTime());
			intent.putExtra("endTime", selectedEvent.getShowingTime()+60*60*1000);
			intent.putExtra("eventLocation", selectedEvent.getPlaceString());
			startActivity(intent);
		}
		
	}
	
	public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

	    private String url;
	    private final WeakReference<ImageView> imageViewReference;

	    public ImageDownloader(ImageView imageView) {
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }

	    @Override
	    protected Bitmap doInBackground(String... params) {
	        url = params[0];
	        try {
	            return BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	            return null;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    @Override
	    protected void onPostExecute(Bitmap result) {
	        if (isCancelled()) {
	            result = null;
	        }
	        if (imageViewReference != null) {
	            ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	                imageView.setImageBitmap(result);
	            }
	        }
	    }

	    @Override
	    protected void onPreExecute() {
	    	// Do nothing
	    }
	}


	@Override
	public void onClick(View paramView) {
		CheckBox boxClicked = (CheckBox) paramView;
		
		boolean isChecked = boxClicked.isChecked();
		
		selectedEvent.setFavourite(isChecked);
		
		ContentValues values = new ContentValues();
		values.put(UkaEventContract.FAVOURITE, isChecked);
		ContentResolver cr = this.getContentResolver();
		int updated = cr.update(UkaEventContract.EVENT_CONTENT_URI, values,
				UkaEventContract.ID + " = ?", new String[] { "" + selectedEvent.getId() });
		
		Log.v(debug, "Updated rows: " + updated);
	}
}