package com.checkplease;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SearchMapView extends MapActivity {

	/** Called when the activity is first created. */
	private Cursor cursor;
	private MapView mapView=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    CheckPleaseDatabaseAdapter dbHelper = new CheckPleaseDatabaseAdapter(this);
		dbHelper.open();
	    setContentView(R.layout.searchmapview);
	    MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    // TODO Auto-generated method stub
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.fastfood);
	    CustomItemizedOverlay itemizedoverlay = new CustomItemizedOverlay(drawable, this);
	    //MapLocationViewer mapLocationViewer = new MapLocationViewer(this);
	    //MapLocationOverlay itemizedoverlay = new MapLocationOverlay(mapLocationViewer);
	    
	    
	    cursor  = dbHelper.fetchStores();
	    if (cursor != null) {
			cursor.moveToFirst();
		}
 		startManagingCursor(cursor);
 		//Boolean x = cursor.moveToFirst();
 		//Toast.makeText(this,cursor.getString(cursor.getColumnIndex("Item_Name")) + " \n" + cursor.getString(cursor.getColumnIndex("Store_Name")) +" \n" + " \n" , Toast.LENGTH_LONG).show();
	 	//cursor.getCount()
	 	int longi, lati;
 		GeoPoint point = null;
 		OverlayItem overlayitem;
	 	while(!cursor.isAfterLast())
	 	{
			Log.w("LATITIDE",Integer.toString(cursor.getInt(cursor.getColumnIndex("Store_Latitude")) ));
			Log.w("LONGITUDE",Integer.toString(cursor.getInt(cursor.getColumnIndex("Store_Longitude")) ));
			lati = cursor.getInt(cursor.getColumnIndex("Store_Latitude"));
			longi =  cursor.getInt(cursor.getColumnIndex("Store_Longitude"));
		    point = new GeoPoint(longi, lati);
		    overlayitem = new OverlayItem(point, cursor.getString(cursor.getColumnIndex("Store_Name")), cursor.getString(cursor.getColumnIndex("Store_Address")));
		    itemizedoverlay.addOverlay(overlayitem);
		  
	    	cursor.moveToNext();
	 	}
	 	
	 	//cursor.deactivate();
	 	/*
	    
	    Log.w("LATITIDE",Integer.toString(cursor.getInt(cursor.getColumnIndex("Store_Latitude")) ));
		Log.w("LONGITUDE",Integer.toString(cursor.getInt(cursor.getColumnIndex("Store_Longitude")) ));
		int lat1 = cursor.getInt(cursor.getColumnIndex("Store_Latitude"));
		int long1 =  cursor.getInt(cursor.getColumnIndex("Store_Longitude"));
		GeoPoint point = new GeoPoint(long1, lat1);
		//GeoPoint point = new GeoPoint(cursor.getInt(cursor.getColumnIndex("Store_Latitude")), cursor.getInt(cursor.getColumnIndex("Store_Longitude")));
	    OverlayItem overlayitem = new OverlayItem(point, "Chipotle", "Here i am");
	    itemizedoverlay.addOverlay(overlayitem);
	    cursor.moveToNext();
	    //GeoPoint point2 = new GeoPoint(cursor.getInt(cursor.getColumnIndex("Store_Latitude")), cursor.getInt(cursor.getColumnIndex("Store_Longitude")));
	    GeoPoint point2 = new GeoPoint(37868366, -122259050);
	    Log.w("LATITIDE",Integer.toString(cursor.getInt(cursor.getColumnIndex("Store_Latitude")) ));
		Log.w("LONGITUDE",Integer.toString(cursor.getInt(cursor.getColumnIndex("Store_Longitude")) ));
	    OverlayItem overlayitem2 = new OverlayItem(point2, "Mac Donalds", "Bad Hussein");
	    
	    itemizedoverlay.addOverlay(overlayitem2);
	    */
	    mapOverlays.add(itemizedoverlay);
	    MapController mapController = mapView.getController();
	    mapController.animateTo(point);
	    mapController.setZoom(15);
	    
	 }
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.search_menus, menu);
	
	// Enable List and disable Map
	MenuItem m = menu.findItem(R.id.list);
	m.setVisible(true);
	
	m = menu.findItem(R.id.map);
	m.setVisible(false);
	
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	Intent i = new Intent(SearchMapView.this, SearchListView.class);
	    startActivity(i);
    
	return true;
    }


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    protected void onPause() {
		super.onPause();
		
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

/*
public class InnerCustomItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private Drawable marker=null;
    private PopupPanel panel=new PopupPanel(R.layout.popup);
    
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
	
	class PopupPanel {
	    View popup;
	    boolean isVisible=false;
	    
	    PopupPanel(int layout) {
	      ViewGroup parent=(ViewGroup)mapView.getParent();

	      popup=getLayoutInflater().inflate(layout, parent, false);
	                  
	      popup.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	          hide();
	        }
	      });
	    }
	    
	    View getView() {
	      return(popup);
	    }
	    
	    void show(boolean alignTop) {
	      RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(
	            RelativeLayout.LayoutParams.WRAP_CONTENT,
	            RelativeLayout.LayoutParams.WRAP_CONTENT
	      );
	      
	      if (alignTop) {
	        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	        lp.setMargins(0, 20, 0, 0);
	      }
	      else {
	        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        lp.setMargins(0, 0, 0, 60);
	      }
	      
	      hide();
	      
	      ((ViewGroup)mapView.getParent()).addView(popup, lp);
	      isVisible=true;
	    }
	    
	    void hide() {
	      if (isVisible) {
	        isVisible=false;
	        ((ViewGroup)popup.getParent()).removeView(popup);
	      }
	    }
	  }
}
*/
}
