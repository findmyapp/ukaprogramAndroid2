package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.android.library.R;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public abstract class PopupMenuActivity extends Activity {
	
	private static final String debug = "PopupMenuActivity";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(R.string.setting_menu_item);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class); 
		intent.putExtra("previous_context", this.getClass());
		startActivity(intent);
		return true;
	}
}
