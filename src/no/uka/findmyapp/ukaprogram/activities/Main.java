package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class Main extends Activity implements OnClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_menu);
		initView();
	}

	private void initView() {
		Button favorites = (Button) findViewById(R.id.favoritter);
		Button program = (Button) findViewById(R.id.program);

		Button settings = (Button) findViewById(R.id.innstillinger);
		Button loginbutton = (Button) findViewById(R.id.login);
		
		favorites.setOnClickListener(this);
		program.setOnClickListener(this);
		settings.setOnClickListener(this);
		loginbutton.setOnClickListener(this);
		
		program.setBackgroundResource(R.drawable.mainmenu_program_low);
		favorites.setBackgroundResource(R.drawable.mainmenu_favorites_low);
		settings.setBackgroundResource(R.drawable.mainmenu_settings_low);
		loginbutton.setBackgroundResource(R.drawable.mainmenu_login_low);
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
			Log.d("haba haba", "ASDFASDF");
			Toast.makeText(this, "Login pressed.", Toast.LENGTH_SHORT).show();
			break;
		case (R.id.innstillinger):
			Toast.makeText(this, "Innstillinger pressed", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
