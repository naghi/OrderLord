package com.checkplease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CheckPleaseDatabaseHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "checkplease_db";

	private static final int DATABASE_VERSION = 1;
	//Integer server_id, Integer version, Integer etp, String item_name, String link_to_pic, Double price, Integer store_id, String restaurant_name
	/*
	 * public static final String KEY_ROWID = "_id";
		public static final String KEY_SERVER_ROWID = "Server_id";
		public static final String KEY_VERSION = "Version";
		public static final String KEY_ITEM_ETP = "Item_etp";
		public static final String KEY_ITEM_NAME = "Item_Name";
		public static final String KEY_LINK_TO_PIC = "Link_to_pic";
		public static final String KEY_PRICE = "Price";
		public static final String KEY_STORE_ID = "Store_id";
		public static final String KEY_RESTAURANT_NAME = "Restaurant_Name";
		{"id":2,"version":0,"username":"Subway002","password":"12wholeInches","name":"Subway","address":"1444 Shattuck Place, Berkeley, CA",
		"phoneNumber":"(510) 526-3086","pictureLink":"http:\/\/j.mp\/No_Image_Available","role":"user"},
	 */
	private static final String ITEM_TABLE_CREATE = "CREATE TABLE " + DatabaseFields.ITEM_TABLE + "(" +
			DatabaseFields.KEY_ROWID + " INTEGER not null, " +
			DatabaseFields.KEY_SERVER_ROWID  + " INTEGER not null, " +
			DatabaseFields.KEY_VERSION + " INTEGER not null, " +
			DatabaseFields.KEY_ITEM_ETP + " INTEGER not null, " +
			DatabaseFields.KEY_ITEM_NAME + " TEXT not null, " +
			DatabaseFields.KEY_LINK_TO_PIC + " TEXT not null, " +
			DatabaseFields.KEY_PRICE + " DOUBLE not null, " +
			DatabaseFields.KEY_STORE_ID + " INTEGER not null, " +
			DatabaseFields.KEY_STORE_NAME + " TEXT not null);";

	private static final String STORE_TABLE_CREATE = "CREATE TABLE "+ DatabaseFields.STORE_TABLE + "(" +
			DatabaseFields.KEY_SERVER_ROWID + " INTEGER not null, " +
			DatabaseFields.KEY_VERSION + " INTEGER not null, " +
			DatabaseFields.KEY_STORE_LAT + " INTEGER not null, " +
			DatabaseFields.KEY_LINK_TO_PIC + " TEXT not null, " +
			DatabaseFields.KEY_STORE_LONG + " INTEGER not null, " + 
			DatabaseFields.KEY_STORE_ADDRESS + " TEXT not null, " + 
			DatabaseFields.KEY_STORE_NAME + " TEXT not null);";
	/*
	 * 	public static final String KEY_STORE_ID = "Store_id";
		public static final String KEY_STORE_NAME = "Store_Name";
		public static final String KEY_STORE_LAT = "Store_Latitude";
		public static final String KEY_STORE_LONG = "Store_Longitude";
		public static final String KEY_STORE_ADDRESS = "Store_Address";
	 */
	CheckPleaseDatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 *  Method is called during creation of the database
	 */
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(ITEM_TABLE_CREATE);
		db.execSQL(STORE_TABLE_CREATE);
	}

	/* 
	 * Method is called during an upgrade of the database, e.g. if you increase
	 * the database version
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		Log.w(CheckPleaseDatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(db);
		
	}
	

	

}
