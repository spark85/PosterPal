package com.david.ozersky.posterpal.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.david.ozersky.posterpal.R;
import com.david.ozersky.posterpal.object.Job;

public class JobsOnMarketAdapter extends BaseAdapter {

	private Context ctx;
	private ArrayList<Job> jobs;
	private LayoutInflater inflater;
	
	public JobsOnMarketAdapter(Context ctx, ArrayList<Job> jobs) {
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.jobs = jobs;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jobs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return jobs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	 convertView = inflater.inflate(R.layout.row_job, null);
	
	  LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout_deals_main_container);
	  TextView txt_description = (TextView) convertView.findViewById(R.id.row_description);
	  TextView txt_order = (TextView) convertView.findViewById(R.id.txt_order);
	  TextView txt_deadline = (TextView) convertView.findViewById(R.id.txt_deadline);
	 	  
	  txt_description.setText(jobs.get(position).getDescription());
	  
	  return convertView;
	}
	
	
	

}
