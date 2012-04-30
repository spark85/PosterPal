package com.david.ozersky.posterpal;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.david.ozersky.posterpal.adapter.JobsOnMarketAdapter;
import com.david.ozersky.posterpal.object.Job;

public class MarketListActivity extends Activity {
	
	private LinearLayout layout_list;
	private ListView job_listview;
	private ArrayList<Job> jobslist;
	private JobsOnMarketAdapter adapter;
	private PosterPalApp app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
		
		init();
		
	}

	private void init() {
		
		app = ((PosterPalApp) getApplicationContext());
		
		jobslist = app.getJobsFromMarket();
		
		adapter = new JobsOnMarketAdapter(this, jobslist); 
		job_listview.setAdapter(adapter);
		
	}

	
	
}
