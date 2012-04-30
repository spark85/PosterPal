package com.david.ozersky.posterpal;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.david.ozersky.posterpal.api.PosterPalApi;
import com.david.ozersky.posterpal.object.Flyer;
import com.david.ozersky.posterpal.object.FlyerItemizedOverlay;
import com.david.ozersky.posterpal.object.Job;
import com.david.ozersky.posterpal.object.User;
import com.parse.ParseObject;

public class PosterPalApp extends Application {

	private User user;
	private FlyerItemizedOverlay overlays;
	private ArrayList<Flyer> flyers;
	private ArrayList<Job> jobs;
	private PosterPalApi api;

	@Override
	public void onCreate() {
		super.onCreate();

		api = new PosterPalApi(this);
		user = new User(); 
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public FlyerItemizedOverlay getOverlays() {
		return overlays;
	}

	public ArrayList<Job> getJobsFromMarket() {

		return Job.getMarketJobsList();
	}

	public ArrayList<Job> getMyJobsList() {

		return Job.getMyJobsList();
	}

	public void setMarketJobsList(ArrayList<ParseObject> marketJobsListParse) {

		for (int i = 0; i < marketJobsListParse.size(); i++) {
			Job jobToAdd = new Job(marketJobsListParse.get(i));
			Job.getMarketJobsList().add(jobToAdd);
		}
	}

	public void setMyJobsList(ArrayList<ParseObject> myJobsList) {

		for (int i = 0; i < myJobsList.size(); i++) {
			Job jobToAdd = new Job(myJobsList.get(i));
			Job.getMyJobsList().add(jobToAdd);
		}
	}

	public ArrayList<Flyer> getFlyersForJob(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMyId() {
		// TODO Auto-generated method stub
		return user.getId();
	}

	public String getMyCity() {
		// TODO Auto-generated method stub
		return user.getCity();
	}


}
