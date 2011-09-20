package no.uka.findmyapp.ukaprogram.activities;

import no.uka.findmyapp.ukaprogram.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


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
		
		favorites.setOnClickListener(this);
		program.setOnClickListener(this);
		
		program.setBackgroundResource(R.drawable.mainbuttonprogram);
		favorites.setBackgroundResource(R.drawable.mainbuttonfav);
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
		default:
			break;
		}
	}
}
