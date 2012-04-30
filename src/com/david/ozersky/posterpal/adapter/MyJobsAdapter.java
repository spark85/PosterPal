package com.david.ozersky.posterpal.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.david.ozersky.posterpal.MyJobsActivity;
import com.david.ozersky.posterpal.PosterPalApp;
import com.david.ozersky.posterpal.R;
import com.david.ozersky.posterpal.object.Flyer;
import com.david.ozersky.posterpal.object.Job;

public class MyJobsAdapter extends BaseAdapter {

	private MyJobsActivity my_jobs_activity;
	private ArrayList<Flyer> flyers;
	private ArrayList<Flyer> temp;
	private ArrayList<Job> jobs;
	private LayoutInflater inflater;
	private ArrayList<Integer> viewIds;
	private PosterPalApp posterApp;
	
	public MyJobsAdapter(MyJobsActivity my_jobs_activity, ArrayList<Job> jobs) {
		
		this.jobs = jobs;
		this.my_jobs_activity = my_jobs_activity;
		inflater = (LayoutInflater) my_jobs_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		posterApp =((PosterPalApp) my_jobs_activity.getApplicationContext());
		
		
		for (int i = 0; i < jobs.size(); i++) {
			temp = jobs.get(i).getFlyers();
			for (int j = 0; j < temp.size(); j++) {
				flyers.add(temp.get(j));
			}
		}
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return flyers.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//Question about reinflated views from Adapter -- can we catch view that were already inflated
		convertView = (View) inflater.inflate(R.layout.row_my_jobs, null);
		
		final CheckBox chkbox_view_job = (CheckBox) convertView.findViewById(R.id.chkbox_view_job);
		final CheckBox chkbox_view_only_job = (CheckBox) convertView.findViewById(R.id.chkbox_view_only_job);

		//Initially, all jobs are visible
		chkbox_view_job.setChecked(true);
		
		OnCheckedChangeListener chk_listener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (buttonView.isChecked() && !isChecked) {
					my_jobs_activity.removeJobsFromMapView(jobs.get(position));
				}
				if (!buttonView.isChecked() && isChecked) {
					my_jobs_activity.addJobsToMapView(jobs.get(position));
				}
			}
			
		};
		
		OnCheckedChangeListener chk_only_listener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (buttonView.isChecked() && !isChecked) {
					my_jobs_activity.makeOtherJobsVisible(jobs.get(position));
				}
				if (!buttonView.isChecked() && isChecked) {
					my_jobs_activity.makeAllOtherJobsInvisible(jobs.get(position));
				}
			}
		
		};
		
		chkbox_view_job.setOnCheckedChangeListener(chk_listener);
//		chkbox_view_only_job.setOnCheckedChangeListener(chk_only_listener);
		return convertView;
	}

}
