package no.uka.findmyapp.ukeprogram.activities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import no.uka.findmyapp.ukeprogram.R;
import no.uka.findmyapp.ukeprogram.facebook.SessionStore;
import no.uka.findmyapp.ukeprogram.restlibrary.IntentMessages;
import no.uka.findmyapp.ukeprogram.restlibrary.RestUtils;
import no.uka.findmyapp.ukeprogram.utils.Base64;
import no.uka.findmyapp.ukeprogram.utils.Constants;
import no.uka.findmyapp.ukeprogram.utils.EventsUpdater;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;


public class Main extends Activity implements OnClickListener {
	
	private BroadcastReceiver receiver;
	protected ProgressDialog loadingDialog = null; // Displays when download program.
	private Facebook mFacebook;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_menu);
		initView();
		
		// This listener receives a notification when the program is loaded after the refresh-button is pressed,
		//  then dismisses the loading dialog.
		IntentFilter filter = new IntentFilter();
		filter.addAction(IntentMessages.BROADCAST_INTENT_TOKEN);
		filter.addAction(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION);
		
		receiver = new BroadcastReceiver() {
			@Override
		    public void onReceive(Context context, Intent intent) {
				if (loadingDialog != null)
					loadingDialog.dismiss();
				if (intent.getAction().equals(IntentMessages.BROADCAST_HTTP_STATUS_EXCEPTION)) {
					Toast.makeText(context, "Feil ved nedlasting av program", Toast.LENGTH_SHORT);
				}
		    }
		 };
		 registerReceiver(receiver, filter);
		 
		 try {
			   PackageInfo info = getPackageManager().getPackageInfo("no.uka.findmyapp.ukeprogram", PackageManager.GET_SIGNATURES);
			   for (Signature signature : info.signatures) {
			        MessageDigest md = MessageDigest.getInstance("SHA");
			        md.update(signature.toByteArray());
			        Log.d("MAIN", Base64.encodeBytes(md.digest()));
			   }
			} catch (NameNotFoundException e) {

			} catch (NoSuchAlgorithmException e) {

			}		 
	}

	@Override
	protected void onResume() {
		super.onStart();
		
		 // Check if Facebook session is still valid. Set menu accordingly.
		 mFacebook = new Facebook(getString(R.string.facebook_appkey));
		 
		 if (SessionStore.restore(mFacebook, this)) {
		    if (Constants.DEBUG) Log.d("MAIN", "Facebook session valid.");
		    setIsLoggedIn();
		    RestUtils.authFindMyApp(this, mFacebook.getAccessToken());
		} else {
			if (Constants.DEBUG) Log.d("MAIN", "Facebook session invalid.");
			setIsNotLoggedIn();
			RestUtils.resetToken(this);
		}		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (loadingDialog != null)
			loadingDialog.dismiss();
		unregisterReceiver(receiver);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.menuoption_settings:
	    	if (RestUtils.hasToken(this)) {
			Intent intent = new Intent().setClass(this, SettingsActivity.class);
			startActivity(intent);
	    	} else {
	    		Toast.makeText(this, "Krever innlogging", Toast.LENGTH_SHORT).show();
	    	}
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	

	private void initView() {
		Button favorites = (Button) findViewById(R.id.favoritter);
		Button program = (Button) findViewById(R.id.program);

		Button settings = (Button) findViewById(R.id.innstillinger);
		Button loginbutton = (Button) findViewById(R.id.login);
		
		Button refresh = (Button) findViewById(R.id.update);
		
		favorites.setOnClickListener(this);
		program.setOnClickListener(this);
		settings.setOnClickListener(this);
		loginbutton.setOnClickListener(this);
		refresh.setOnClickListener(this);
	}
	
    // Edits view to reflect that a person is logged in
    private void setIsLoggedIn()
    {
        try {                    	
            Button b = (Button)findViewById(R.id.login);
            b.setBackgroundResource(R.drawable.menu_logout);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	logoutPressed();
                }
            });
                    	
		} catch (Exception e) {
			e.printStackTrace();
			
		}
        
        Button settings = (Button) findViewById(R.id.innstillinger);
        settings.setVisibility(View.GONE);
    }
    
    // Edits view to reflect that a person is not logged in    
    private void setIsNotLoggedIn()
    {
    	
        Button b = (Button)findViewById(R.id.login);
        b.setBackgroundResource(R.drawable.menu_login);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	loginPressed();
            }
        });
        
        Button settings = (Button) findViewById(R.id.innstillinger);
        settings.setVisibility(View.GONE);
        
    } 
	
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case (R.id.program):
			intent = new Intent().setClass(this, EventListActivity.class);
			startActivity(intent);
			break;
		case (R.id.favoritter):
			intent = new Intent().setClass(this, FavoritesListActivity.class);
			startActivity(intent);
			break; 
		case (R.id.login):
			Toast.makeText(this, "Login pressed.", Toast.LENGTH_SHORT).show();
			loginPressed();
			break;
		case (R.id.innstillinger):
			Toast.makeText(this, "Innstillinger pressed", Toast.LENGTH_SHORT).show();
			break;
		case (R.id.update):
			this.updateEvents();
		default:
			break;
			
		}
	}
	
	public void updateEvents()
	{
		EventsUpdater eu = new EventsUpdater(getApplicationContext());
		eu.updateEvents();
		loadingDialog = ProgressDialog.show(Main.this, "", "Oppdaterer program. Vennligst vent..", true);
	}
	
    public void loginPressed() {
        mFacebook.authorize(this, new String[]{""} , new DialogListener() {
            @Override
            public void onComplete(Bundle values) {
            	try {
            		if (Constants.DEBUG) Log.d("MAIN", "Logged in");
                    SessionStore.save(mFacebook, Main.this);
                    setIsLoggedIn();
                    RestUtils.authFindMyApp(Main.this, mFacebook.getAccessToken());
                    
				} catch (Exception e) {
					if (Constants.DEBUG) Log.d("MAIN", "Couldn't log in");
				}
            }
            	
            @Override
            public void onFacebookError(FacebookError error) {}

            @Override
            public void onError(DialogError e) {}

            @Override
            public void onCancel() {}
        });
    }
    
    public void logoutPressed() {
    	if (Constants.DEBUG) Log.d("MAIN", "Logout pressed");
    	RestUtils.resetToken(this);
    	new LogoutTask().execute((String[])null);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// We return here after logging in with Facebook - if the user has installed the facebook app. 
    	if (Constants.DEBUG) Log.d("MAIN", "onActivityResult");
    	
        super.onActivityResult(requestCode, resultCode, data);

    	mFacebook.authorizeCallback(requestCode, resultCode, data);
    	
    	// Store session for further use.
    	SessionStore.save(mFacebook, this);
    	setIsLoggedIn();
    	RestUtils.authFindMyApp(this, mFacebook.getAccessToken());
    	
    }
    
    class LogoutTask extends AsyncTask<String, Integer, Boolean> {
    	
    	@Override
    	protected Boolean doInBackground(String... params) {
    		try {
    			SessionStore.clear(Main.this);
    			mFacebook.logout(Main.this);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return true;   // Return your real result here
		  }
	  
		@Override
		protected void onPreExecute() {
			loadingDialog = ProgressDialog.show(Main.this, "", "Logger ut. Vennligst vent.", true);
		}

		@Override
		protected void onPostExecute(Boolean done) {
			setIsNotLoggedIn();
			SessionStore.clear(Main.this);
			loadingDialog.dismiss();
		}
    } 
}
