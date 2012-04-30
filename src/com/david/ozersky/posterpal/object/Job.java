package com.david.ozersky.posterpal.object;

import java.util.ArrayList;

import android.content.Context;

import com.david.ozersky.posterpal.PosterPalApp;
import com.parse.ParseObject;

public class Job {

	
	private String jobId;
	private String compensation;
	private String status;
	private String assignedTo;
	private String description;
	private ArrayList<Flyer> flyers;
	private FlyerItemizedOverlay overlays;
	private Context ctx;
	
	private static ArrayList<Job> myJobsList = new ArrayList<Job>();
	private static ArrayList<Job> marketJobsList = new ArrayList<Job>();
	private static ArrayList<Job> allJobsList = new ArrayList<Job>();
	
	public static ArrayList<Job> getAllJobsList() {
		return allJobsList;
	}

	public static void setAllJobsList(ArrayList<Job> allJobsList) {
		Job.allJobsList = allJobsList;
	}

	public static ArrayList<Job> getMyJobsList() {
		return myJobsList;
	}

	public static void setMyJobsList(ArrayList<Job> myJobsList) {
		Job.myJobsList = myJobsList;
	}

	public static ArrayList<Job> getMarketJobsList() {
		return marketJobsList;
	}

	public static void setMarketJobsList(ArrayList<Job> marketJobsList) {
		Job.marketJobsList = marketJobsList;
	}

	public Job(ParseObject jobParseObject) {
		this.jobId = jobParseObject.getObjectId();

	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void addFlyer(Flyer flyer) {
		flyers.add(flyer);
		overlays.add(flyer);
	}

	public ArrayList<Flyer> getFlyers() {
		return flyers;
	}

	public FlyerItemizedOverlay getOverlays() {
		return overlays;
	}

	public String getCompensation() {
		return compensation;
	}

	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// This code might not be neccesary if API calls work properly
	//
	//	public static ArrayList<Job> generateDummyData() {
	//		jobslist = new ArrayList<Job>();
	//		
	//		for (int i=0; i < 10; i++) {
	//			
	//			Job job = new Job(null);
	//			job.
	//			
	//			jobslist.add(job);
	//		}
	//		
	//		return jobslist;
	//	}

	public static Job getJobFromId(String id) {

		for(int i = 0; i < allJobsList.size(); i++) {

			if (((Job)allJobsList.get(i)).getJobId() == id) {
				return allJobsList.get(i);
			}
		}


		return null;
	}

	public static void addJob(Job job) {

		allJobsList.add(job);

	}

}
