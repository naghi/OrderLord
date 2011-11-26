package com.checkplease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CheckPleaseDatabaseAdapter {
	// Database fields
	/*
	 * <item>
<id>0</id>
<version>0</version>
<item_etp>10</item_etp>
<item_name>Chicken burrito</item_name>
<link_to_pic>
http://www.chipotle.com/en-US/assets/images/menu/menu_burrito.png
</link_to_pic>
<price>6.5</price>
<store_id>1</store_id>
<store_name>Chipotle</store_name>
</item>
	 */
		
		private Context context;
		private SQLiteDatabase database;
		private CheckPleaseDatabaseHelper dbHelper;

		public CheckPleaseDatabaseAdapter(Context context) {
			this.context = context;
		}

		public CheckPleaseDatabaseAdapter open() throws SQLException {
			dbHelper = new CheckPleaseDatabaseHelper(context);
			database = dbHelper.getWritableDatabase();
			Log.i("CheckPleaseDatabaseAdapter", "Opened database");
			return this;
			
		}

		public void close() {
			dbHelper.close();
		}

		
		/*
		 * Create a new Item If the todo is successfully created return the new
		 * rowId for that note, otherwise return a -1 to indicate failure.
		 */
		public long createItem(Integer server_id, Integer version, Integer etp, String item_name, String link_to_pic, Double price, Integer store_id, String restaurant_name) {
			ContentValues initialValues = createContentValues(server_id, version, etp, item_name, link_to_pic, price, store_id, restaurant_name);

			return database.insert(DatabaseFields.ITEM_TABLE, null, initialValues);
		}

		/*
		 * Create a new Store If the todo is successfully created return the new
		 * rowId for that note, otherwise return a -1 to indicate failure.
		 * "CREATE TABLE Store_table (" +
			"_id INTEGER primary key autoincrement, "
			+ "Server_id INTEGER not null, "
			+ "Version INTEGER not null, "
			+ "Latitude REAL not null, "
			+ "Link_to_pic TEXT not null, "
			+ "Longitude REAL not null, "
			+ "Store_address TEXT not null, "
			+ " Store_Name TEXT not null);";
	
		 */
		public long createStore(Integer server_id, Integer version, Integer lati, String link_to_pic, Integer longi, String store_address, String store_name) {
			ContentValues initialValues = createContentValues(server_id, version, lati, link_to_pic, longi, store_address, store_name);
			
			return database.insert(DatabaseFields.STORE_TABLE, null, initialValues);
		}
		
		
		/*
		 * Update the item
		 */

		public boolean updateItem(long rowId, Integer server_id, Integer version, Integer etp, String item_name, String link_to_pic, 
				Double price, Integer store_id, String restaurant_name) { 
			ContentValues updateValues = createContentValues(server_id, version, etp, item_name, link_to_pic, 
					price, store_id, restaurant_name);

			return database.update(DatabaseFields.ITEM_TABLE, updateValues, DatabaseFields.KEY_ROWID + "="
					+ rowId, null) > 0;
		}

		
		/*
		 * Deletes item
		 */
		public boolean deleteItem(long rowId) {
			return database.delete(DatabaseFields.ITEM_TABLE, DatabaseFields.KEY_ROWID + "=" + rowId, null) > 0;
		}

		
		/**
		 * Return a Cursor over the list of all item in the database
		 * 
		 * @return Cursor over all notes
		 */

		/*
		 * 
		public static final String KEY_ROWID = "_id";
		public static final String KEY_SERVER_ROWID = "Server_id";
		public static final String KEY_VERSION = "Version";
		public static final String KEY_ITEM_ETP = "Item_etp";
		public static final String KEY_ITEM_NAME = "Item_Name";
		public static final String KEY_LINK_TO_PIC = "Link_to_pic";
		public static final String KEY_PRICE = "Price";
		public static final String KEY_STORE_ID = "Store_id";
		public static final String KEY_RESTAURANT_NAME = "Restaurant_Name";
		 */
		public Cursor fetchAllItems() {
			Cursor cursor = database.query(DatabaseFields.ITEM_TABLE, new String[] { DatabaseFields.KEY_ROWID,
					DatabaseFields.KEY_SERVER_ROWID, DatabaseFields.KEY_VERSION, DatabaseFields.KEY_ITEM_ETP, DatabaseFields.KEY_ITEM_NAME, DatabaseFields.KEY_LINK_TO_PIC, DatabaseFields.KEY_PRICE, DatabaseFields.KEY_STORE_ID, DatabaseFields.KEY_STORE_NAME  }, null, null, null,
					null, null);
			 if (cursor != null) {
					cursor.moveToFirst();
				}
				return cursor;
		}
		/*
		 * 			"_id INTEGER primary key autoincrement, "
			+ "Server_id INTEGER not null, "
			+ "Version INTEGER not null, "
			+ "Store_Latitude INTEGER not null, "
			+ "Link_to_pic TEXT not null, "
			+ "Store_Longitude INTEGER not null, "
			+ "Store_Address TEXT not null, "
			+ " Store_Name TEXT not null);";
		 */
				
		public Cursor fetchStores() {
			 Cursor cursor = database.query(DatabaseFields.STORE_TABLE, null, null, null, null,
					null, null);
			 if (cursor != null) {
					cursor.moveToFirst();
				}
				return cursor;
		}

		
		/**
		 * Return a Cursor positioned at the defined item
		 * 
		 */
		public Cursor fetchItem(long rowId) throws SQLException {
			Cursor mCursor = database.query(true, DatabaseFields.ITEM_TABLE, new String[] {DatabaseFields.KEY_ROWID,
					DatabaseFields.KEY_SERVER_ROWID, DatabaseFields.KEY_VERSION, DatabaseFields.KEY_ITEM_ETP, DatabaseFields.KEY_ITEM_NAME, DatabaseFields.KEY_LINK_TO_PIC, DatabaseFields.KEY_PRICE, DatabaseFields.KEY_STORE_ID, DatabaseFields.KEY_STORE_NAME },
					DatabaseFields.KEY_ROWID + "=" + rowId, null, null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}
		
		/**
		 * Return all items from a restaurant
		 */
		public Cursor fetchItemsInRestaurant(String restaurant_name) throws SQLException {
			Cursor mCursor = database.query(true, DatabaseFields.ITEM_TABLE, new String[] {DatabaseFields.KEY_ROWID,
					DatabaseFields.KEY_SERVER_ROWID, DatabaseFields.KEY_VERSION, DatabaseFields.KEY_ITEM_ETP, DatabaseFields.KEY_ITEM_NAME, DatabaseFields.KEY_LINK_TO_PIC, DatabaseFields.KEY_PRICE, DatabaseFields.KEY_STORE_ID, DatabaseFields.KEY_STORE_NAME},
					DatabaseFields.KEY_STORE_NAME + "=?", new String[]{restaurant_name}, null, null, null, null);
			
			//Cursor mCursor = database.rawQuery("SELECT * FROM Item_table WHERE Restaurant_Name='Chipotle'", null);
			//Cursor mCursor = database.rawQuery("SELECT * FROM Item_table", null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}
		

		private ContentValues createContentValues(Integer server_id, Integer version, Integer etp, String item_name, String link_to_pic, 
				Double price, Integer store_id, String restaurant_name) {
			ContentValues values = new ContentValues();
			values.put(DatabaseFields.KEY_SERVER_ROWID, server_id);
			values.put(DatabaseFields.KEY_VERSION, version);
			values.put(DatabaseFields.KEY_ITEM_ETP, etp);
			values.put(DatabaseFields.KEY_ITEM_NAME, item_name);
			values.put(DatabaseFields.KEY_LINK_TO_PIC, link_to_pic);
			values.put(DatabaseFields.KEY_PRICE, price);
			values.put(DatabaseFields.KEY_STORE_ID, store_id);
			values.put(DatabaseFields.KEY_STORE_NAME, restaurant_name);
			return values;
		}
		
		private ContentValues createContentValues(Integer server_id, Integer version, Integer lati, String link_to_pic, Integer longi, String store_address, String store_name) {
			ContentValues values = new ContentValues();
			values.put(DatabaseFields.KEY_SERVER_ROWID, server_id);
			values.put(DatabaseFields.KEY_VERSION, version);
			values.put(DatabaseFields.KEY_STORE_LAT, lati);
			values.put(DatabaseFields.KEY_LINK_TO_PIC, link_to_pic);
			values.put(DatabaseFields.KEY_STORE_LONG, longi);
			values.put(DatabaseFields.KEY_STORE_NAME, store_name);
			values.put(DatabaseFields.KEY_STORE_ADDRESS, store_address);
			return values;
		}
}
