package com.checkplease;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private Drawable marker=null;
    
	public CustomItemizedOverlay(Drawable defaultMarker, Context context) {
		this(defaultMarker);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public CustomItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		
		// TODO Auto-generated constructor stub
	}

	public void addOverlay(OverlayItem overlay){
		mOverlays.add(overlay);
		populate();
		
	}
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index){
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
	/*
	@Override
	public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow) {

		// Again get our screen coordinate
		int[] selDestinationOffset = new int[2];
		mapView.getProjection().getPointXY(selectedMapLocation.getPoint(), selDestinationOffset);
		
		// Setup the info window with the right size & location
		int INFO_WINDOW_WIDTH = 125;
		int INFO_WINDOW_HEIGHT = 25;
		RectF infoWindowRect = new RectF(0,0,INFO_WINDOW_WIDTH,INFO_WINDOW_HEIGHT);
		int infoWindowOffsetX = selDestinationOffset[0]-INFO_WINDOW_WIDTH/2;
		int infoWindowOffsetY = selDestinationOffset[1]-INFO_WINDOW_HEIGHT-bubbleIcon.height();
		infoWindowRect.offset(infoWindowOffsetX,infoWindowOffsetY);

		// Draw inner info window
		canvas.drawRoundRect(infoWindowRect, 5, 5, getInnerPaint());

		// Draw border for info window
		canvas.drawRoundRect(infoWindowRect, 5, 5, getBorderPaint());

		// Draw the MapLocation’s name
		int TEXT_OFFSET_X = 10;
		int TEXT_OFFSET_Y = 15;
		canvas.drawText(selectedMapLocation.getName(),infoWindowOffsetX+TEXT_OFFSET_X,infoWindowOffsetY+TEXT_OFFSET_Y,getTextPaint());

		}
*/
}
