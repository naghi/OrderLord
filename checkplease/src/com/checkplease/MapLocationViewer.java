package com.checkplease;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.google.android.maps.MapView;

public class MapLocationViewer extends LinearLayout {

	private MapLocationOverlay overlay;
	
    //  Known latitude/longitude coordinates that we'll be using.
    private List<MapLocation> mapLocations;
    
    private MapView mapView;
    
	public MapLocationViewer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MapLocationViewer(Context context) {
		super(context);
		init();
	}

	public void init() {		

		setOrientation(VERTICAL);
		setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));

		mapView = new MapView(getContext(),"0DUEIIn35xtmfWC2DXprK5kqNF-aEaNgRJ4ONxw");
		mapView.setEnabled(true);
		mapView.setClickable(true);
		addView(mapView);

		overlay = new MapLocationOverlay(this);
		mapView.getOverlays().add(overlay);

    	mapView.getController().setZoom(14);
    	mapView.getController().setCenter(getMapLocations().get(0).getPoint());
	}
	
	public List<MapLocation> getMapLocations() {
		if (mapLocations == null) {
			mapLocations = new ArrayList<MapLocation>();
			mapLocations.add(new MapLocation("North Beach",37.799800872802734,-122.40699768066406));
			mapLocations.add(new MapLocation("China Town",37.792598724365234,-122.40599822998047));
			mapLocations.add(new MapLocation("Fisherman's Wharf",37.80910110473633,-122.41600036621094));
			mapLocations.add(new MapLocation("Financial District",37.79410171508789,-122.4010009765625));
		}
		return mapLocations;
	}

	public MapView getMapView() {
		return mapView;
	}
}
