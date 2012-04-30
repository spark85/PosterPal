package com.david.ozersky.posterpal.object;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.parse.ParseObject;

public class Flyer {
	
	private GeoPoint flyer_geopoint;
	private String jobId;
	private String flyerId;
	private String status;
	
	public Flyer(String objectId, GeoPoint loc, String FlyerStatus) {

		this.flyerId = objectId;
		this.flyer_geopoint = loc;
		this.status = status;

		
	}
	
	public Flyer(ParseObject flyer) {
		// TODO Auto-generated constructor stub
	}

	public GeoPoint getGeoPoint() {
		return flyer_geopoint;
	}

	public String getFlyerId() {
		return flyerId;
	}

	public void setFlyerId(String objectId) {
		this.flyerId = objectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
}
