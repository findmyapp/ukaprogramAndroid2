package no.uka.findmyapp.ukeprogram.activities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.models.UkaEvent;
import no.uka.findmyapp.ukeprogram.utils.DateUtils;
import no.uka.findmyapp.ukeprogram.utils.ImageCache;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FriendListActivity extends Activity {

	private ArrayList<String> friends;
	private UkaEvent selectedEvent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friends_list);
		 
		Bundle bundle = getIntent().getExtras();
		friends = bundle.getStringArrayList("friends");
		selectedEvent = (UkaEvent)bundle.get("event");
		
		TextView headertext = (TextView)findViewById(R.id.friends_header_text);
		headertext.setText(selectedEvent.getTitle());
		
		TextView timeAndPlace = (TextView)findViewById(R.id.friend_event_timeAndPlace);
		timeAndPlace.setText(createTimeAndPlaceText(selectedEvent));
		
		TextView ageLimitAndPrice = (TextView)findViewById(R.id.friend_event_AgeLimitAndPrice);
		ageLimitAndPrice.setText(createAgeLimitAndPriceText(selectedEvent));

		ImageView typePicture = (ImageView)findViewById(R.id.friend_event_typePicture);
		if (selectedEvent.getEventType().equals("Konsert")) {
			typePicture.setImageResource(R.drawable.eventicon_konsert);
		} else if (selectedEvent.getEventType().equals("Revy og teater")) {
			typePicture.setImageResource(R.drawable.eventicon_revy);
		} else if (selectedEvent.getEventType().equals("Kurs og events")) {
			typePicture.setImageResource(R.drawable.eventicon_kurs);
		} else if (selectedEvent.getEventType().equals("Fest og moro")) {
			typePicture.setImageResource(R.drawable.eventicon_fest);
		} else {

		}
		
		ListView lv = (ListView)findViewById(R.id.friend_listview);
		lv.setAdapter(new FriendListAdapter(this, R.layout.friend_list_item, friends));
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
	
    private class FriendListAdapter extends ArrayAdapter<String> { //--CloneChangeRequired
        
    	private ArrayList<String> mList; //--CloneChangeRequired
 
        public FriendListAdapter(Context context, int textViewResourceId, ArrayList<String> list) { //--CloneChangeRequired
            super(context, textViewResourceId, list);
            this.mList = list;
        }
 
        public View getView(int position, View convertView, ViewGroup parent) {
            
        	View view = convertView;
        	ImageView iv;
        	
        	try {
                if (view == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = vi.inflate(R.layout.friend_list_item, null); //--CloneChangeRequired(list_item)
                }
                
                final String s = (String) mList.get(position); //--CloneChangeRequired
                
                String[] words = s.split(":");
                
                ((TextView) view.findViewById(R.id.friend_item_name)).setText(words[0]);
                
                iv = (ImageView)view.findViewById(R.id.friend_image);
                String url = "http://graph.facebook.com/" + words[1] + "/picture";
                ImageDownloader downloader = new ImageDownloader(iv);
                downloader.execute(url);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
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
	    	return ic.loadSync(url, FriendListActivity.this);
	    }

	    @Override
	    protected void onPostExecute(Drawable result) {
	        if (isCancelled()) {
	            result = null;
	        }
	        
	        if (imageViewReference != null) {
	            ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	            	if (result == null) {
	            		imageView.setImageResource(R.drawable.placeholder_generic);
	            	} else {
	            		imageView.setImageDrawable(result);
	            	}
	            }
	        }
	    }

	    @Override
	    protected void onPreExecute() {
	    	// Do nothing
	    }
	}
}
