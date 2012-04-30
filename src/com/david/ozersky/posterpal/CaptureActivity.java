package com.david.ozersky.posterpal;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ca.mobicartel.axcess.location.LocationStalker;
import ca.mobicartel.axcess.location.LocationStalker.OnLocationFoundListener;

import com.david.ozersky.posterpal.object.Flyer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CaptureActivity extends Activity implements OnClickListener, OnLocationFoundListener {

	private Button btn_scan;
	private String contents;
	private double latitude;
	private double longitude;
	private LocationStalker stalker;
	private Location location;
	private ParseObject flyer;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		init();

	}


	@Override
	protected void onPause() {

		stalker.stopStalking();

		super.onPause();
	}



	private void init() {

		Parse.initialize(this, "C9xVQE9eRXX6qOAp3XVJC9URrttunNL3FmaSpSRu", "ZlQC9mZnLEoqu2y9FvgNi7wUhKZyXu0XPqfOw4ks"); 

		stalker = new LocationStalker(this);
		stalker.setOnLocationFoundListener(this);
		stalker.startStalking();

		btn_scan = (Button) findViewById(R.id.btn_scan);
		btn_scan.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case (R.id.btn_scan): 
		IntentIntegrator zxing = new IntentIntegrator(this);
		zxing.initiateScan(IntentIntegrator.QR_CODE_TYPES);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (result != null) {
			contents = result.getContents();
			if (contents != null) {		
				Toast.makeText(this, contents, Toast.LENGTH_SHORT).show();
				AsyncParseQuery query = new AsyncParseQuery();
				query.execute(contents);
			} else {
				AlertDialog dialog = new AlertDialog.Builder(this).create();
				dialog.setMessage("Scan Failed");
				dialog.show();
			}

		}

	}


	//	private void createdTestData(String contents2) {
	//
	//		//Get the current location.  Will be checked in Query//
	//		location = stalker.getQuickLocationUpdate();
	//		latitude = location.getLatitude();
	//		longitude = location.getLongitude();
	//
	//		ParseGeoPoint geopoint = new ParseGeoPoint(latitude, longitude);
	//
	//		ParseObject trail = new ParseObject("Flyer");
	//		trail.put("location", geopoint);
	//		trail.put("FlyerStatus", "Unposted");
	//		trail.saveInBackground();
	//
	//	}


	private void postResult(ParseObject flyer) {
		
		ParseGeoPoint desiredPoint = (ParseGeoPoint) flyer.getParseGeoPoint("location");
		ParseGeoPoint capturedAtPoint = new ParseGeoPoint(40.82921, -79.19128); 

		if (desiredPoint.distanceInKilometersTo(capturedAtPoint) >= 1/4) {			
			Toast.makeText(this, "Sorry, this flyer is outside of the posting area.  Check the areas for this job and try again.", Toast.LENGTH_SHORT).show(); 
		} else {
			Toast.makeText(this, "Great job, you're one step closer to completing this job", Toast.LENGTH_SHORT).show();
		}
		
			flyer.put("FlyerStatus", "Posted");
			flyer.saveInBackground();
		
			/*
			flyer.fetchIfNeededInBackground(new GetCallback() {
				@Override
				public void done(ParseObject flyerFromServer, com.parse.ParseException e) {
					if (e == null) {
						flyerFromServer.put("FlyerStatus", "Posted");
						flyerFromServer.saveInBackground();
					} else {
						Log.d("PosterPalActivity", "Error trying to update FlyerStatus on server" + e.getMessage());
					}

				}
			});
			*/
		 
	}


	public void setFlyer(ParseObject parse) {
		this.flyer = parse;

	}


	@Override
	public void onLocationFound(Location location) {
		// TODO Auto-generated method stub

	}

	private class AsyncParseQuery extends AsyncTask<String, Integer, ParseObject> {

		private ParseObject job;
		private ParseObject user;

		@Override
		protected ParseObject doInBackground(String... params) {

			ParseQuery queryFlyer = null;
			ParseQuery queryJob = null;
			ParseQuery queryUser = null;

			try {
				
				queryFlyer = new ParseQuery("Flyer");
				queryJob = new ParseQuery("Job");
				queryUser = new ParseQuery("User");
				
				flyer = queryFlyer.get(contents);
				job = queryJob.getFirst();
				user = queryUser.getFirst();
				
				Log.d("PosterPalActivity", "queryFlyer " + queryFlyer.toString());
				Log.d("PosterPalActivity", "flyer id " + flyer.getObjectId());
				Log.d("PosterPalActivity", "queryJob " + queryJob.toString());
				Log.d("PosterPalActivity", "job id" + job.getObjectId());
				Log.d("PosterPalActivity", "queryUser " + queryUser.toString());
				Log.d("PosterPalActivity", "user id" + user.getObjectId());
				
			} catch (Exception parse) {
				Log.d("PosterPalActivity", parse.getMessage());
			}
			
			return flyer;
		}

		protected void onProgressUpdate(Integer...progress){
			
		}

		protected void onPostExecute(ParseObject parse) {
			postResult(parse);
		}



	}


}


