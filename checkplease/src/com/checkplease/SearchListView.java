package com.checkplease;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchListView extends Activity implements OnItemClickListener {
	private ListView searchList;
	//private String[] lv_arr;//={"Chipotle","MacDonalds","Burger King","Subway", "Naan n' Curry", "IHop", "Denny's", "CREME", "Thai Basil", "Panda Express"};
	private ArrayList<String> lv_arr = new ArrayList<String>();
	/** Called when the activity is first created. */
	private Cursor cursor;
	private CheckPleaseDatabaseAdapter dbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    dbHelper = new CheckPleaseDatabaseAdapter(this);
		dbHelper.open();
	    setContentView(R.layout.searchlistview);
//	    String stores = NetworkUtilities.getStores();
	    String stores = "" +
	    		"stores:" +
	    		"[ " +
	    		"   {   " +
	    		"       \"id\":2," +
	    		"       \"version\":0," +
	    		"       \"username\":\"Subway002\"," +
	    		"       \"password\":\"12wholeInches\"," +
	    		"       \"name\":\"Subway\"," +
	    		"       \"address\":\"1444 Shattuck Place, Berkeley, CA\"," +
	    		"       \"phoneNumber\":\"(510) 526-3086\"," +
	    		"       \"pictureLink\":\"http://j.mp/No_Image_Available\"," +
	    		"       \"role\":\"user\"" +
	    		"    }," +
	    		"    {" +
	    		"       \"id\":3," +
	    		"       \"version\":0," +
	    		"       \"username\":\"username1\"," +
	    		"       \"password\":\"password1\"," +
	    		"       \"name\":\"name1\"," +
	    		"       \"address\":\"address1\"," +
	    		"       \"phoneNumber\":\"(510) 555-5555\"," +
	    		"       \"pictureLink\":\"http://j.mp/No_Image_Available\"," +
	    		"       \"role\":\"user\"" +
	    		"    }" +
	    		"]";
	    try {
	    JSONObject jsonResponse = new JSONObject(stores);
	    
	    JSONArray jsonArray = (JSONArray) jsonResponse.getJSONArray("stores");
	    JSONObject o;
	    for (int i = 0; i < jsonArray.length(); i++) {
	       
				o  = (JSONObject) jsonArray.get(i);
				lv_arr.add(o.getString("store_name"));

				dbHelper.createStore(Integer.parseInt(o.getString("id")), Integer.parseInt(o.getString("version")),
						((int) (Double.parseDouble(o.getString("latitude"))*1E6)), o.getString("link_to_pic"), 
						((int) (Double.parseDouble(o.getString("longitude"))*1E6)), o.getString("store_address"),
						o.getString("store_name"));
	        
	    }
	    } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // TODO Auto-generated method stub
	    searchList=(ListView)findViewById(R.id.restaurantlistview);
	    // By using setAdpater method in listview we an add string array in list.
	    searchList.setOnItemClickListener(this);
	    searchList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));
	}
	@Override
    protected void onPause() {
		super.onPause();
		
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
/*	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}*/
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "You clicked " + lv_arr[arg2] ,Toast.LENGTH_LONG).show();
		
	
		String items = NetworkUtilities.getStores();
		try {
		    JSONObject jsonResponse = new JSONObject(items);
		    
		    JSONArray jsonArray = (JSONArray) jsonResponse.getJSONArray("item");
		    JSONObject o;
		    for (int i = 0; i < jsonArray.length(); i++) {
		       
				o  = (JSONObject) jsonArray.get(i);
				
				//Integer server_id, Integer version, Integer etp, String item_name, String link_to_pic, Double price, 
				//Integer store_id, String Store_Name) {
				dbHelper.createItem(Integer.parseInt(o.getString("id")), Integer.parseInt(o.getString("version")),Integer.parseInt(o.getString("item_etp")), o.getString("item_name"),o.getString("link_to_pic"), Double.parseDouble(o.getString("price")), Integer.parseInt(o.getString("store_id")),o.getString("store_name"));
		    }
	    } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		//startManagingCursor(cursor);
		//cursor  = dbHelper.fetchItemsInRestaurant(lv_arr.get(arg2));
		//cursor  = dbHelper.fetchItemsInRestaurant("Chipotle");
		
		//Boolean x = cursor.moveToFirst();
		//Toast.makeText(this,cursor.getString(cursor.getColumnIndex("Item_Name")) + " \n" + cursor.getString(cursor.getColumnIndex("Store_Name")) +" \n" + " \n" , Toast.LENGTH_LONG).show();
		//cursor.close();
		Intent i = new Intent(SearchListView.this, Restaurant_Items_Activity.class);
		startActivity(i);
		
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_menus, menu);
		
		// Enable Map and disable List
		MenuItem m = menu.findItem(R.id.map);
		m.setVisible(true);
		
		m = menu.findItem(R.id.list);
		m.setVisible(false);
		
		return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	    Intent i = new Intent(SearchListView.this, SearchMapView.class);
	    startActivity(i);
		return true;
    }


}
