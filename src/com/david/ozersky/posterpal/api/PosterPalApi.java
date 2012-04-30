package com.david.ozersky.posterpal.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

import com.david.ozersky.posterpal.PosterPalApp;
import com.david.ozersky.posterpal.object.Job;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery;

public class PosterPalApi {

	private ArrayList<Job> myJobs;
	private ArrayList<Job> jobsOnMarketList;
	private ParseQuery query;
	private Context ctx;
	private SharedPreferences sharedPref;
	private Editor editor;
	private String myId;
	private String city;
	private PosterPalApp app;


	public PosterPalApi(PosterPalApp app) {

		Parse.initialize(app, "C9xVQE9eRXX6qOAp3XVJC9URrttunNL3FmaSpSRu", "ZlQC9mZnLEoqu2y9FvgNi7wUhKZyXu0XPqfOw4ks"); 

		this.app = app;
		this.myId = app.getMyId();
		this.city = app.getMyCity();
		jobsOnMarketList = new ArrayList<Job>();
		myJobs = new ArrayList<Job>();

	}


	public void getJobs() {

		AsyncParseQueryMarketJob asyncQuery = new AsyncParseQueryMarketJob();
		asyncQuery.execute(myId);

	}

	public void getFlyers(ArrayList <Job> joblist) {

//		AsyncParseFlyers asyncFlyersQuery = new AsyncParseFlyers();

		String[] jobIds = new String [joblist.size()];

		for (int i = 0; i < joblist.size(); i++) {
			jobIds[i] = (joblist.get(i).getJobId());
		}

//		asyncFlyersQuery.execute(jobIds);
	}

	private void createJobArrayFromDatabase() {
		// TODO Auto-generated method stub

	}

	private class AsyncParseQueryMarketJob extends AsyncTask<String, Integer, HashMap<String, Object>> {

		private HashMap<String, Object> map;

		private ParseObject jobMarketParse;
		private ParseObject jobMyParse;
		private ParseObject userParse;
		private ParseObject flyerParse;

		private ParseQuery queryFlyer = null;
		private ParseQuery queryMarketJob = null;
		private ParseQuery querymyJob = null;
		private ParseQuery queryUser = null;

		private ArrayList<ParseObject> jobsMarketParse;
		private ArrayList<ParseObject> jobsMyParse;
		private ParseQuery queryMyJob;

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {


			try {

				ArrayList<Job> jobsOnMarket = new ArrayList<Job>();
				ArrayList<Job> myJobs = new ArrayList<Job>();

				queryFlyer = new ParseQuery("Flyer"); 
				queryMarketJob = new ParseQuery("Job");
				queryMyJob = new ParseQuery("Job");
				queryUser = new ParseQuery("User");

				queryMarketJob.whereContains("AssignedTo", "Nobody");
				queryMarketJob.whereContains("City", "Toronto");

				jobsMarketParse = (ArrayList<ParseObject>) queryMarketJob.find();

				queryMyJob.whereContains("AssignedTo", myId);
				jobsMyParse = (ArrayList<ParseObject>) queryMyJob.find();

				//Get the Flyers for each job 

				String jobId;

				for(Iterator iter = (Iterator<ParseObject>) jobsMarketParse.iterator(); iter.hasNext();) {
					jobId = ((ParseObject) iter.next()).getObjectId();
					queryFlyer.whereContains("JobId", jobId);

				}


				map.put("MarketJobs", jobsMarketParse);
				map.put("MyJobs", jobsMyParse);

				Log.d("PosterPalActivity", "queryFlyer " + queryFlyer.toString());
				Log.d("PosterPalActivity", "queryJob " + queryMarketJob.toString());
				Log.d("PosterPalActivity", "queryUser " + queryUser.toString());

			} catch (Exception parse) {
				Log.d("PosterPalActivity", parse.getMessage());
			}

			return map;
		}

		protected void onProgressUpdate(Integer...progress){

		}

		protected void onPostExecute(HashMap<String, Object> map) {

			app.setMarketJobsList((ArrayList <ParseObject>) map.get("MarketJobs"));
			app.setMyJobsList((ArrayList<ParseObject>) map.get("MyJobs"));

		}

	}


	/**
	 * This AsyncTask task will retrieve flyers either in an individuals job, or those that are still on the market
	 * (not assigned to anybody).  First I will try to pull all of the data in one AsyncTask.  Will consider using a Thread instead. 
	 *
	 */
	/*
	private class AsyncParseFlyers extends AsyncTask<String, Integer, HashMap<String,Object>> {

		private ParseObject flyerParse;
		private ParseQuery queryFlyer = null;
		private List<ParseObject> flyersListParse;


		@Override
		protected HashMap<String, Object> doInBackground(String... params) {

			HashMap <String, Object> hash = new HashMap <String, Object>();

			try {

				for (int i =0; i < params.length; i++) {

					queryFlyer = new ParseQuery("Flyer");
					queryFlyer.whereContains("JobId", params[i]);
					flyersListParse =	queryFlyer.find();
					hash.put(params[i], flyersListParse);
				}

			} catch (Exception parse) {
				Log.d("PosterPalActivity", parse.getMessage());
			}

			return hash;
		}

		protected void onProgressUpdate(Integer...progress){

		}

		protected void onPostExecute(HashMap <String, Object> map) {

			//This code loops through the set of keys (jobId's), and adds the 
			//correct group of flyers to each job. 
			Set<String> jobKeySet = map.keySet();


			for (Iterator<String> iter = jobKeySet.iterator(); iter.hasNext();) {
				if(iter.next().equals()) {

				}
			}
		}

	}
````*/
}




