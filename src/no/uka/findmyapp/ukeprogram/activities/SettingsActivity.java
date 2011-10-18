package no.uka.findmyapp.ukeprogram.activities;

import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.restlibrary.RestTask;
import no.uka.findmyapp.ukeprogram.utils.Constants;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private String positionPrivacySetting;
    private String eventPrivacySetting;
    private String moneyPrivacySetting;
    private String mediaPrivacySetting;
    
    private ProgressDialog loadingDialog;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings);
		
		loadingDialog = ProgressDialog.show(this, "", "Henter innstillinger...", true);
		
		getSettings();
	}
	
	private void updateRadioButtons()
	{
		// Called after fetching privacy settings from server. 
		RadioButton friends, anyone, onlyme;
	
		anyone = (RadioButton) findViewById(R.id.position_radio0);
		friends = (RadioButton) findViewById(R.id.position_radio1);
		onlyme = (RadioButton) findViewById(R.id.position_radio2);
		
		if (positionPrivacySetting.equals("FRIENDS")) {
			anyone.setChecked(false);
			friends.setChecked(true);
			onlyme.setChecked(false);
		} else if (positionPrivacySetting.equals("ONLY_ME")) {
			anyone.setChecked(false);
			friends.setChecked(false);
			onlyme.setChecked(true);			
		} else if (positionPrivacySetting.equals("ANYONE")) {
			anyone.setChecked(true);
			friends.setChecked(false);
			onlyme.setChecked(false);			
		}
		
		anyone = (RadioButton) findViewById(R.id.event_radio0);
		friends = (RadioButton) findViewById(R.id.event_radio1);
		onlyme = (RadioButton) findViewById(R.id.event_radio2);
		
		if (eventPrivacySetting.equals("FRIENDS")) {
			anyone.setChecked(false);
			friends.setChecked(true);
			onlyme.setChecked(false);
		} else if (eventPrivacySetting.equals("ONLY_ME")) {
			anyone.setChecked(false);
			friends.setChecked(false);
			onlyme.setChecked(true);			
		} else if (eventPrivacySetting.equals("ANYONE")) {
			anyone.setChecked(true);
			friends.setChecked(false);
			onlyme.setChecked(false);			
		}
		
		anyone = (RadioButton) findViewById(R.id.money_radio0);
		friends = (RadioButton) findViewById(R.id.money_radio1);
		onlyme = (RadioButton) findViewById(R.id.money_radio2);
		
		if (moneyPrivacySetting.equals("FRIENDS")) {
			anyone.setChecked(false);
			friends.setChecked(true);
			onlyme.setChecked(false);
		} else if (moneyPrivacySetting.equals("ONLY_ME")) {
			anyone.setChecked(false);
			friends.setChecked(false);
			onlyme.setChecked(true);			
		} else if (moneyPrivacySetting.equals("ANYONE")) {
			anyone.setChecked(true);
			friends.setChecked(false);
			onlyme.setChecked(false);			
		}
		
		anyone = (RadioButton) findViewById(R.id.media_radio0);
		friends = (RadioButton) findViewById(R.id.media_radio1);
		onlyme = (RadioButton) findViewById(R.id.media_radio2);
		
		if (mediaPrivacySetting.equals("FRIENDS")) {
			anyone.setChecked(false);
			friends.setChecked(true);
			onlyme.setChecked(false);
		} else if (mediaPrivacySetting.equals("ONLY_ME")) {
			anyone.setChecked(false);
			friends.setChecked(false);
			onlyme.setChecked(true);			
		} else if (mediaPrivacySetting.equals("ANYONE")) {
			anyone.setChecked(true);
			friends.setChecked(false);
			onlyme.setChecked(false);			
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		// This method is called when the user checks one of the radio buttons
		if (group.getId() == R.id.position_group) {
			if (checkedId == R.id.position_radio0) {
				setSetting("positionPrivacySetting", "ANYONE");
			} else if (checkedId == R.id.position_radio1) {
				setSetting("positionPrivacySetting", "FRIENDS");
			} else if (checkedId == R.id.position_radio2) {
				setSetting("positionPrivacySetting", "ONLY_ME");
			}
		} else if (group.getId() == R.id.event_group) {
			if (checkedId == R.id.event_radio0) {
				setSetting("eventsPrivacySetting", "ANYONE");
			} else if (checkedId == R.id.event_radio1) {
				setSetting("eventsPrivacySetting", "FRIENDS");
			} else if (checkedId == R.id.event_radio2) {
				setSetting("eventsPrivacySetting", "ONLY_ME");
			}
		} else if (group.getId() == R.id.money_group) {
			if (checkedId == R.id.money_radio0) {
				setSetting("moneyPrivacySetting", "ANYONE");
			} else if (checkedId == R.id.money_radio1) {
				setSetting("moneyPrivacySetting", "FRIENDS");
			} else if (checkedId == R.id.money_radio2) {
				setSetting("moneyPrivacySetting", "ONLY_ME");
			}
		} else if (group.getId() == R.id.media_group) {
			if (checkedId == R.id.media_radio0) {
				setSetting("mediaPrivacySetting", "ANYONE");
			} else if (checkedId == R.id.media_radio1) {
				setSetting("mediaPrivacySetting", "FRIENDS");
			} else if (checkedId == R.id.media_radio2) {
				setSetting("mediaPrivacySetting", "ONLY_ME");
			}
		}
	}

	
	// Posts new privacy settings to Findmyapp
	private void setSetting(String privacySetting, String parameter)
	{		
		String setting = "";
		if (parameter.equals("ANYONE")) {
			setting = "1";
		} else if (parameter.equals("FRIENDS")) {
			setting = "2";
		} else if (parameter.equals("ONLY_ME")) {
			setting = "3";
		}
		
		loadingDialog = ProgressDialog.show(this, "", "Oppdaterer...", true);
		
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
		params.add(new BasicNameValuePair(privacySetting, setting));
		String uri = "http://findmyapp.net/findmyapp/users/me/privacy";
		
		int method = RestTask.POST;
						
		RestTask task = new RestTask(this, uri, method, params, true) {
			
			@Override
		    public void restComplete(String response) { 
				Toast.makeText(SettingsActivity.this, "Innstilling oppdatert", Toast.LENGTH_SHORT).show();
				loadingDialog.dismiss();
		    }
			
		    @Override 
		    public void restFailed(String failMsg, int code) { 
		    	if (Constants.DEBUG) Log.e("RestTask", "Couldn't update attending info: " + failMsg);
		    	Toast.makeText(SettingsActivity.this, "Klarte ikke oppdatere innstillinger.", Toast.LENGTH_SHORT).show();
		    	loadingDialog.dismiss();
		    }
		};
		
		task.execute();		
	}
	
	// Retrieves current privacy settings from FindMyApp
	private void getSettings()
	{
		if (Constants.DEBUG) Log.d("RestTask", "Fetching privacy settings");
	
	    SharedPreferences savedSession = getSharedPreferences(RestTask.FMA_SESSION, Context.MODE_PRIVATE);
	    String token = savedSession.getString(RestTask.USER_TOKEN, "");
	    if (token.equals("")) {
	    	if (Constants.DEBUG) Log.d("RestTask", "Not authenticated. ");
	    	return;
	    }
	
	    if (Constants.DEBUG) Log.d("RestTask", "UserToken restored: " + token);
		
	    List<NameValuePair> params = new ArrayList<NameValuePair>(); 
	    
		params.add(new BasicNameValuePair("token", token));
		String uri = "http://findmyapp.net/findmyapp/users/me/privacy/";
		
		RestTask task = new RestTask(this, uri, RestTask.GET, params, true) {
			
			@Override
		    public void restComplete(String response) { 
	        
		        try {
			        JSONObject in = (JSONObject)JSONValue.parse(response);
			        positionPrivacySetting = (String)in.get("positionPrivacySetting");
			        eventPrivacySetting = (String)in.get("eventsPrivacySetting");
			        moneyPrivacySetting = (String)in.get("moneyPrivacySetting");
			        mediaPrivacySetting = (String)in.get("mediaPrivacySetting");
			        updateRadioButtons();
				} catch (Exception e) {
					if (Constants.DEBUG) Log.e("RestTask", "error: " + e.getMessage());
					return;
				}
		        
		        RadioGroup rg = (RadioGroup)findViewById(R.id.position_group);
		        
				rg.setOnCheckedChangeListener(SettingsActivity.this);
				rg = (RadioGroup)findViewById(R.id.event_group);
				rg.setOnCheckedChangeListener(SettingsActivity.this);
				rg = (RadioGroup)findViewById(R.id.money_group);
				rg.setOnCheckedChangeListener(SettingsActivity.this);
				rg = (RadioGroup)findViewById(R.id.media_group);
				rg.setOnCheckedChangeListener(SettingsActivity.this);
				loadingDialog.dismiss();
				
		    }
			
		    @Override 
		    public void restFailed(String failMsg, int code) { 
		    	if (Constants.DEBUG) Log.e("RestTask", "Failure: " + failMsg);
		    	Toast.makeText(SettingsActivity.this, "Kunne ikke laste innstillinger", Toast.LENGTH_SHORT).show();
		    	loadingDialog.dismiss();
		    }
		};
		
		task.execute();
	}
}