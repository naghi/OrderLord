package com.checkplease;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Restaurant_Items_Activity extends ListActivity {

	private Cursor cursor;
	private CheckPleaseDatabaseAdapter dbHelper;
	private List<Item> li;
	private DownloadImageTask dt = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /*ArrayAdapter<Item> adapter = new MyArrayAdapter(this,
				getModel());
				*/
		//setListAdapter(adapter);
	    
		
	    // TODO Auto-generated method stub
//	    ScrollView sv = new ScrollView(this);
//	    LinearLayout ll = new LinearLayout(this);
//	    ll.setOrientation(LinearLayout.VERTICAL);
//	    sv.addView(ll);
//	    TextView tv = new TextView(this);
//	    ImageView iv = new ImageView(this);
	    
//	    tv.setText("CHIPOTLE MENU ");
	    
//	    ll.addView(tv);
	    //tv.
 		//cursor  = dbHelper.fetchItemsInRestaurant(lv_arr.get(arg2));
		dt = new DownloadImageTask();
		dt.execute("something");
		this.setListAdapter(new MyArrayAdapter(this, li));
 		
	}
	private List<Item> getModel() {
		List<Item> list = new ArrayList<Item>();
		
		dbHelper = new CheckPleaseDatabaseAdapter(this);
		dbHelper.open();
		cursor  = dbHelper.fetchItemsInRestaurant("Chipotle");
 		startManagingCursor(cursor);
 		Double d;
 		String s1, s2;
 		while(!cursor.isAfterLast())
	 	{
	 		/*
	 		CheckBox cb = new CheckBox(this);
	    	cb.setText(cursor.getString(cursor.getColumnIndex("Item_Name")));
	    	ll.addView(cb);
	    	*/
	    	/*
	 		iv.setImageBitmap(NetworkUtilities.getImage(cursor.getString(cursor.getColumnIndex("Link_to_pic"))));
	 		ll.addView(iv);
	 		*/
 			
 			s1 = cursor.getString(cursor.getColumnIndex("Item_Name"));
 			d = cursor.getDouble(cursor.getColumnIndex("Price"));
 			s2 = cursor.getString(cursor.getColumnIndex("Link_to_pic"));
 			
 			list.add(get(s1, NetworkUtilities.getImage(s2),	d));
 			cursor.moveToNext();
 			/*
 			foodImages.add(NetworkUtilities.getImage(cursor.getString(cursor.getColumnIndex("Link_to_pic"))));
 			names.add(cursor.getString(cursor.getColumnIndex("Item_Name")));
 			names.add(cursor.getString(cursor.getColumnIndex("Item_Price")));
	    	cursor.moveToNext();
	    	*/
	 	}
	
		return list;
	}

	private Item get(String s, Bitmap foodImage, Double price) {
		return new Item(s, foodImage, price);
	}
	@Override
    protected void onPause() {
		super.onPause();
		
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	private class DownloadImageTask extends AsyncTask<String, Void, List<Item>> {
	    /** The system calls this to perform work in a worker thread and
	      * delivers it the parameters given to AsyncTask.execute() */
	    protected List<Item> doInBackground(String... urls) {
	        return getModel();//loadImageFromNetwork(urls[0]);
	    }
	    
	    /** The system calls this to perform work in the UI thread and delivers
	      * the result from doInBackground() */
	    protected void onPostExecute(List<Item> result) {
	    	li.addAll(result);//s = result; //  mImageView.setImageBitmap(result);
	    }
	}

}
