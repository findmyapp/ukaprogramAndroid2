package no.uka.findmyapp.ukeprogram.activities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.facebook.SessionStore;
import no.uka.findmyapp.ukeprogram.models.UkaEvent;
import no.uka.findmyapp.ukeprogram.providers.UkaEvents.UkaEventContract;
import no.uka.findmyapp.ukeprogram.restlibrary.RestTask;
import no.uka.findmyapp.ukeprogram.restlibrary.RestUtils;
import no.uka.findmyapp.ukeprogram.utils.Constants;
import no.uka.findmyapp.ukeprogram.utils.DateUtils;
import no.uka.findmyapp.ukeprogram.utils.ImageCache;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.Facebook;

public class EventDetailsActivity extends Activity implements OnClickListener {
	private static final String debug = "EventsDetailsActivity";
	
	private static final String IMAGE_URL_PREFIX = "http://www.uka.no/";

	private UkaEvent selectedEvent; 
	private ImageView eventImageView;
	private Facebook mFacebook;
	private boolean isLoggedIn = false;
	
	
	private final int LOADING = -1;
	private final int ERROR = -2;
	
	private int numberOfAttendingFriends = LOADING;
	private ArrayList<String> friendsAttending;
	private boolean attending = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_details);

		Bundle bundle = getIntent().getExtras(); 
		
		mFacebook = new Facebook(getString(R.string.facebook_appkey));
		if (SessionStore.restore(mFacebook, this))
		{
			isLoggedIn = true;
		} else {
			RestUtils.resetToken(this);
		}
		
		if (bundle.getSerializable(EventListActivity.ITEM_CLICKED) != null) {
			selectedEvent = (UkaEvent) bundle.getSerializable(EventListActivity.ITEM_CLICKED);
			populateView();
		} else {
			String exception = "Error";
			Toast toast = Toast.makeText(getApplicationContext(), exception, Toast.LENGTH_LONG);
			toast.show();
		}
		
		if (isLoggedIn) {
			if (RestUtils.hasToken(this)) {
				getFriends();
				getAttending();
			} else {
				RestUtils.authFindMyApp(this, mFacebook.getAccessToken());
				getFriends();
				getAttending();
			}
		}
		

	}
	    
	public void populateView(){
		if (Constants.DEBUG) Log.v(debug, "selectedEvent " + selectedEvent.toString());
		
		// Get Views
		TextView title = (TextView) findViewById(R.id.detailedEventTitle);
		TextView timeAndPlace = (TextView) findViewById(R.id.detailedEventTimeAndPlace);
		TextView description = (TextView) findViewById(R.id.detailedEventDescription);
		TextView headerTitle = (TextView) findViewById(R.id.event_details_header_title);
		TextView ageLimitAndPrice = (TextView) findViewById(R.id.detailedEventAgeLimitAndPrice);
		
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
		
		
		
		title.setText(createTitleText());
		ageLimitAndPrice.setText(createAgeLimitAndPriceText(selectedEvent));
		description.setText(selectedEvent.getLead());
		Log.v(debug, "EVENT TYPE:" + selectedEvent.getEventType());
		
		ImageView typePicture = (ImageView)findViewById(R.id.event_details_typePicture);
		if (selectedEvent.getEventType().equals("Konsert")) {
			typePicture.setImageResource(R.drawable.eventicon_konsert);
			eventImageView.setImageResource(R.drawable.placeholder_konsert);
		} else if (selectedEvent.getEventType().equals("Revy og teater")) {
			typePicture.setImageResource(R.drawable.eventicon_revy);
			eventImageView.setImageResource(R.drawable.placeholder_revy);
		} else if (selectedEvent.getEventType().equals("Kurs og events")) {
			typePicture.setImageResource(R.drawable.eventicon_kurs);
			eventImageView.setImageResource(R.drawable.placeholder_kurs);
		} else if (selectedEvent.getEventType().equals("Fest og moro")) {
			typePicture.setImageResource(R.drawable.eventicon_fest);
			eventImageView.setImageResource(R.drawable.placeholder_fest);
		} else {
			if (Constants.DEBUG) Log.d(debug, "No category found for event.");
			eventImageView.setImageResource(R.drawable.placeholder_konsert);
		}

		if(selectedEvent.isFavourite()) {
			favorites.setChecked(true);
		}
		
		loadImage();
		
		updateFacebookField();
	}
	
	private void updateFacebookField()
	{
		Button facebookButton = (Button)findViewById(R.id.event_facebookfriends_button);
		ProgressBar facebookLoading = (ProgressBar)findViewById(R.id.event_facebookloading);
		TextView facebookFriendsTextView = (TextView)findViewById(R.id.event_facebookfriends_text);
		
		
		Button attendenceButton = (Button)findViewById(R.id.event_attendence_button);
		TextView attendenceText = (TextView)findViewById(R.id.event_attendence_text);
				
		if (isLoggedIn) {
			
			facebookButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent();
					i.setClass(EventDetailsActivity.this, FriendListActivity.class);
					i.putExtra("event", selectedEvent);
					i.putStringArrayListExtra("friends", friendsAttending);
					startActivity(i);
				}
			});
			
			if (numberOfAttendingFriends == LOADING) {
				facebookLoading.setVisibility(View.VISIBLE);
				facebookFriendsTextView.setVisibility(View.GONE);
				facebookButton.setClickable(false);
			} else if (numberOfAttendingFriends == ERROR) {
				facebookLoading.setVisibility(View.GONE);
				facebookFriendsTextView.setVisibility(View.VISIBLE);
				facebookFriendsTextView.setText("Feil ved innlasting");
				facebookButton.setClickable(false);
			} else {
				facebookLoading.setVisibility(View.GONE);
				facebookFriendsTextView.setVisibility(View.VISIBLE);
				if (numberOfAttendingFriends == 0) {
					facebookButton.setClickable(false);
					facebookFriendsTextView.setText("Ingen venner kommer");
				} else if (numberOfAttendingFriends == 1) {
					facebookButton.setClickable(true);
					facebookFriendsTextView.setText(numberOfAttendingFriends + " venn kommer");
				} else {
					facebookButton.setClickable(true);
					facebookFriendsTextView.setText(numberOfAttendingFriends + " venner kommer");
				}
			}
					
		} else {
			facebookFriendsTextView.setText("Ikke pålogget");
			facebookLoading.setVisibility(View.GONE);
			attendenceButton.setVisibility(View.GONE);
			attendenceText.setVisibility(View.GONE);
			ProgressBar pb = (ProgressBar)findViewById(R.id.event_attendingloading);
			pb.setVisibility(View.GONE);
		}
	}
	
	private void updateAttendingField()
	{
		Button attendenceButton = (Button)findViewById(R.id.event_attendence_button);
		TextView attendenceText = (TextView)findViewById(R.id.event_attendence_text);
		
		if (attending)
		{	
			attendenceButton.setBackgroundResource(R.drawable.check);
			attendenceText.setText("Jeg skal.");
			
		} else {
			attendenceButton.setBackgroundResource(R.drawable.uncheck);
			attendenceText.setText("Jeg skal ikke.");
		}
		
		attendenceButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAttending();
			}
		});
		
		TextView tv = (TextView)findViewById(R.id.event_attendence_text);
		tv.setVisibility(View.VISIBLE);
		ProgressBar pb = (ProgressBar)findViewById(R.id.event_attendingloading);
		pb.setVisibility(View.GONE);
	}
	
	private String createAgeLimitAndPriceText(UkaEvent selectedEvent)
	{
		String ageLimitAndPriceText = "";
		if (selectedEvent.getAgeLimit() == 0) {
			ageLimitAndPriceText += "Ingen aldersgrense | ";
		} else {
			ageLimitAndPriceText += "Aldersgrense: " + selectedEvent.getAgeLimit() + " år | ";
		}
		
		if (selectedEvent.getLowestPrice() == 0){
			ageLimitAndPriceText += "Gratis";
		} else {
			ageLimitAndPriceText += "Pris: ";
			ageLimitAndPriceText += selectedEvent.getLowestPrice();
			ageLimitAndPriceText += " kr";
		}
		
		return ageLimitAndPriceText;
	}

	private String createTitleText() {
		String titleText = selectedEvent.getTitle();
		titleText += selectedEvent.isCanceled() ? " (AVLYST!)" : "";
		return titleText;
	}

	private void loadImage() {
		ImageDownloader downloader = new ImageDownloader(eventImageView);
		String uri = "";
		
		if (selectedEvent.getImage().startsWith("/"))
		{
			uri = IMAGE_URL_PREFIX + selectedEvent.getImage();
		} else {
			uri = selectedEvent.getImage();
		}
		
		downloader.execute(uri);
	}

	private String createTimeAndPlaceText(UkaEvent selectedEvent) {
		String timeAndPlaceText = "";
		DateUtils du = new DateUtils(); 
		timeAndPlaceText += selectedEvent.getPlaceString() + " | ";
		timeAndPlaceText += du.getWeekdayFromTimestamp(selectedEvent.getShowingTime()) + " ";
		timeAndPlaceText += du.getCustomDateFormatFromTimestamp("dd.MM", selectedEvent.getShowingTime()) + " | Kl. " +
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
	
	private class ImageDownloader extends AsyncTask<String, Void, Drawable> {

	    private String url;
	    private final WeakReference<ImageView> imageViewReference;

	    public ImageDownloader(ImageView imageView) {
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }

	    @Override
	    protected Drawable doInBackground(String... params) {
	    	
	    	url = params[0];
	    	ImageCache ic = ImageCache.getInstance();
	    	return ic.loadSync(url, EventDetailsActivity.this);
	    }

	    @Override
	    protected void onPostExecute(Drawable result) {
	    	
	    	if (Constants.DEBUG) Log.d(debug, "On post execute");
	        if (isCancelled()) {
	            result = null;
	        }
	        
	        if (imageViewReference != null) {
	            ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	            	if (result == null) {
	            		if (Constants.DEBUG) Log.d(debug, "No image downloaded. Inserting placeholder.");
	            		if (selectedEvent.getEventType().equals("Konsert")) {
	            			imageView.setImageResource(R.drawable.placeholder_konsert);
	            		} else if (selectedEvent.getEventType().equals("Revy og teater")) {
	            			imageView.setImageResource(R.drawable.placeholder_revy);
	            		} else if (selectedEvent.getEventType().equals("Kurs og events")) {
	            			imageView.setImageResource(R.drawable.placeholder_kurs);
	            		} else if (selectedEvent.getEventType().equals("Fest og moro")) {
	            			imageView.setImageResource(R.drawable.placeholder_fest);
	            		} else {
	            			imageView.setImageResource(R.drawable.placeholder_generic);
	            		}
	            		
	            	} else {
	            		imageView.setImageDrawable(result);
	            	}
	            	
	            	imageView.setVisibility(View.VISIBLE);
	            	RelativeLayout pb = (RelativeLayout)findViewById(R.id.event_imageloading_layout);
	            	pb.setVisibility(View.GONE);
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
		
		if (Constants.DEBUG) Log.v(debug, "Updated rows: " + updated);
	}
	
    private void getFriends()
    {
    	if (Constants.DEBUG) Log.d("RestTask", "Fetching friends");

        SharedPreferences savedSession = getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE);
        String token = savedSession.getString(RestTask.USER_TOKEN, "");
        if (token.equals("")) {
        	if (Constants.DEBUG) Log.d("RestTask", "Not authenticated. ");
        	return;
        }

        if (Constants.DEBUG) Log.d("RestTask", "UserToken restored: " + token);
		
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
        
		params.add(new BasicNameValuePair("token", token));
		String uri = "http://findmyapp.net/findmyapp/events/" + String.valueOf(selectedEvent.getId()) + "/friends";
		
		RestTask task = new RestTask(this, uri, RestTask.GET, params, true) {
			
			@Override
		    public void restComplete(String response) { 
				if (Constants.DEBUG) Log.d("RestTask", "getFriends response: " + response);
		        	        
		        try {
			        JSONArray ar = (JSONArray)JSONValue.parse(response);
			        friendsAttending = new ArrayList<String>();
			        numberOfAttendingFriends = ar.size();
			        
			        for (int i = 0; i < ar.size(); i++) {
			        	try {
					        JSONObject in = (JSONObject)ar.get(i);	
					        String name = (String)in.get("fullName");
					        Long fbid = (Long)in.get("facebookUserId");
					        String attendee = name+":"+fbid;
					        friendsAttending.add(attendee);
						} catch (Exception e) {
							if (Constants.DEBUG) Log.e("RestTask", "Error: " + e.getMessage());
						}
			        }
			        
				} catch (Exception e) {
					if (Constants.DEBUG) Log.e("RestTask", "error: " + e.getMessage());
					return;
				}
		        
		        updateFacebookField();
		    }
			
		    @Override 
		    public void restFailed(String failMsg, int code) { 
		    	if (Constants.DEBUG) Log.e("RestTask", "getFriends failed: " + failMsg);
		    	numberOfAttendingFriends = ERROR;
		    	updateFacebookField();
		    }
		};
		
		task.execute();
    }
    
    private void getAttending()
    {
    	
    	if (Constants.DEBUG) Log.d("RestTask", "Fetching attendence");

        SharedPreferences savedSession = getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE);
        String token = savedSession.getString(RestTask.USER_TOKEN, "");
        if (token.equals("")) {
        	if (Constants.DEBUG) Log.d("RestTask", "Not authenticated. ");
        	return;
        }

        if (Constants.DEBUG) Log.d("RestTask", "UserToken restored: " + token);
		
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
        
		params.add(new BasicNameValuePair("token", token));
		String uri = "http://findmyapp.net/findmyapp/users/me/events/";
		
		RestTask task = new RestTask(this, uri, RestTask.GET, params, true) {
			
			@Override
		    public void restComplete(String response) { 
	        
		        try {
			        JSONArray ar = (JSONArray)JSONValue.parse(response);
			        
			        for (int i = 0; i < ar.size(); i++) {
			        	try {
					        JSONObject in = (JSONObject)ar.get(i);	
					        Long id = (Long)in.get("id");
					        if (id == selectedEvent.getId()) {
					        	attending = true;
					        	break;
					        }
					        	
						} catch (Exception e) {
							if (Constants.DEBUG) Log.e("RestTask", "Something went wrong:" + e.getMessage());
						}
			        }
			        
				} catch (Exception e) {
					if (Constants.DEBUG) Log.e("RestTask", "error: " + e.getMessage());
					return;
				}
		        
		        updateAttendingField();
		        
		    }
			
		    @Override 
		    public void restFailed(String failMsg, int code) { 
		    	if (Constants.DEBUG) Log.e("RestTask", "Failure: " + failMsg);
		    	updateAttendingField();
		    }
		};
		
		task.execute();
    }
    
    private void setAttending()
    {
    	if (Constants.DEBUG) Log.d("RestTask", "Setting attendence");

        SharedPreferences savedSession = getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE);
        String token = savedSession.getString(RestTask.USER_TOKEN, "");
        if (token.equals("")) {
        	if (Constants.DEBUG) Log.d("RestTask", "Not authenticated. ");
        	return;
        }

        if (Constants.DEBUG) Log.d("RestTask", "UserToken restored: " + token);
		
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
        
		params.add(new BasicNameValuePair("token", token));
		String uri = "http://findmyapp.net/findmyapp/users/me/events/" + selectedEvent.getId();
		
		int method = RestTask.POST;
		
		if (attending)
		{
			method = RestTask.DELETE;
		}
		
		TextView tv = (TextView)findViewById(R.id.event_attendence_text);
		tv.setVisibility(View.GONE);
		ProgressBar pb = (ProgressBar)findViewById(R.id.event_attendingloading);
		pb.setVisibility(View.VISIBLE);
		
		RestTask task = new RestTask(this, uri, method, params, true) {
			
			@Override
		    public void restComplete(String response) { 
				attending = !attending;
				updateAttendingField();
		    }
			
		    @Override 
		    public void restFailed(String failMsg, int code) { 
		    	if (Constants.DEBUG) Log.e("RestTask", "Couldn't update attending info: " + failMsg);
		    	Toast.makeText(EventDetailsActivity.this, "Klarte ikke oppdatere status.", Toast.LENGTH_SHORT).show();
		    	updateAttendingField();
		    	
		    }
		};
		
		task.execute();
    	
    }
}