package com.david.ozersky.posterpal.object;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class FlyerItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> list_flyers;
	private Drawable pin;
	
	public FlyerItemizedOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		
		this.pin = defaultMarker;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return list_flyers.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return list_flyers.size();
	}
	
	public void add(Flyer f) {
		GeoPoint p = f.getGeoPoint();
		list_flyers.add(new OverlayItem(p,"",""));
		
		populate();
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		
		boundCenterBottom(pin);
	}
	
	public ArrayList<OverlayItem> getOverlays() {
		return list_flyers;
	}
	
	
}
