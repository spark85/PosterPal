package com.david.ozersky.posterpal;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.david.ozersky.posterpal.adapter.JobsOnMarketAdapter;
import com.david.ozersky.posterpal.adapter.MyJobsAdapter;
import com.david.ozersky.posterpal.object.FlyerItemizedOverlay;
import com.david.ozersky.posterpal.object.Job;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyJobsActivity extends MapActivity {

	private MapView mapview;
	private ListView listview_jobs;
	private FlyerItemizedOverlay overlays;
	private PosterPalApp posterApp;
	private MyJobsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_my_jobs);
		
		init();
		
	}

	private void init() {
		
		posterApp = (PosterPalApp) getApplicationContext();
		overlays = posterApp.getOverlays();
		mapview = (MapView) findViewById(R.id.mapview);
		mapview.getController().setCenter(getPoint(40.829121, -74.19128));
		mapview.getController().setZoom(17);
		mapview.setBuiltInZoomControls(true);
		
		listview_jobs  =  (ListView) findViewById(R.id.listview_jobs);
		adapter = new MyJobsAdapter(this, posterApp.getJobsFromMarket());
		listview_jobs.setAdapter(adapter);
	
	}

	private GeoPoint getPoint(double d, double e) {
		
		return (new GeoPoint((int) (d*100000.0),(int) (e*100000.0)));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeJobsFromMapView(Job job) {
		
		FlyerItemizedOverlay job_overlay = job.getOverlays();
		ArrayList<OverlayItem> item_overlays = job_overlay.getOverlays();
		for (int i = 0; i < item_overlays.size(); i++) {
			mapview.getOverlays().remove(item_overlays.get(i));
		}
		
	}

	public void addJobsToMapView(Job job) {
		// TODO Auto-generated method stub
		
	}

	public void makeOtherJobsVisible(Job job) {
		// TODO Auto-generated method stub
		
	}

	public void makeAllOtherJobsInvisible(Job job) {
		// TODO Auto-generated method stub
		
	}

}
