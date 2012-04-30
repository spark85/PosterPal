package com.david.ozersky.posterpal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SplashActivity extends Activity implements OnClickListener {

	private Button btn_splash_login;
	private Button btn_splash_sign_up;
	private Button btn_splash_quick_capture;
	private SharedPreferences sharedPref;
	private Editor editor;
	private PosterPalApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		init();

	}

	private void init() {

		btn_splash_login = (Button)  findViewById(R.id.btn_splash_login);
		btn_splash_sign_up = (Button) findViewById(R.id.btn_splash_sign_up);
		btn_splash_quick_capture =  (Button) findViewById(R.id.btn_splash_quick_capture);

		btn_splash_login.setOnClickListener(this);
		btn_splash_sign_up.setOnClickListener(this);
		btn_splash_quick_capture.setOnClickListener(this);

		sharedPref = getSharedPreferences("accountInformation", 0);
		
		//Adding dummy data.  Please remove code adding user information.  The user will have to sign up
		//to get an id.
		editor = sharedPref.edit();
		editor.putString("userId", "nljwGsFAII");
		editor.putString("city", "Toronto");
		
		app = ((PosterPalApp) getApplicationContext());
		

	}

	@Override
	public void onClick(View v) {

		switch(v.getId()) {
		case(R.id.btn_splash_login):
			if (sharedPref.contains("userId")) {
				String id = sharedPref.getString("userId", "default id");
				Intent login = new Intent(this, MarketListActivity.class);
				login.putExtra("userId", id);
				startActivity(login);
			} else {
				Toast.makeText(this, "No account is attached to this phone. Please create an account first", Toast.LENGTH_SHORT).show();
			}
		break;
		case(R.id.btn_splash_sign_up):
			break;
		case(R.id.btn_splash_quick_capture):
			Intent capture = new Intent(this, CaptureActivity.class);
			startActivity(capture);
		break;
		}
	}

}
