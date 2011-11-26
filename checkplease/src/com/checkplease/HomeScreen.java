package com.checkplease;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeScreen extends Activity implements OnClickListener{

	ImageButton cartButton;
	ImageButton searchButton;
	ImageButton scheduleButton;
	ImageButton accountButton;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        ImageButton searchButton = (ImageButton)findViewById(R.id.search);
        ImageButton cartButton = (ImageButton)findViewById(R.id.cart);
        ImageButton accountButton = (ImageButton)findViewById(R.id.account);
        ImageButton scheduleButton = (ImageButton)findViewById(R.id.schedule);
        searchButton.setOnClickListener(this);
        cartButton.setOnClickListener(this);
        scheduleButton.setOnClickListener(this);
        accountButton.setOnClickListener(this);
        //searchButton = (ImageButton) findViewById(R.id.search);
        //Goes to Search Screen
/*
        CheckPleaseDatabaseAdapter dbHelper = new CheckPleaseDatabaseAdapter(this);
		dbHelper.open();
  */  		
        
        

	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
    	//i = new Intent(HomeScreen.this, SearchTabWidget.class);
        
		switch(v.getId())
		{
			case R.id.search:
				i = new Intent(HomeScreen.this, SearchListView.class);
				startActivity(i);
				break;
			case R.id.cart:
				i = new Intent(HomeScreen.this, CartActivity.class);
				startActivity(i);
				break;
			case R.id.account:
		        showDialog(0);
				break;
			case R.id.schedule:
				i = new Intent(HomeScreen.this, ScheduleActivity.class);
				startActivity(i);
				break;
		}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
		/*
		Context mContext = getApplicationContext();
		Dialog dialog = new Dialog(mContext);

		dialog.setContentView(R.layout.password_dialog);
		dialog.setTitle("Enter Password:");
        Button EnterButton = (Button) findViewById(R.id.enter);
        Button CancelButton = (Button) findViewById(R.id.cancel);
        
        EnterButton.setOnClickListener( new View.OnClickListener()
        {
			public void onClick(View v)
			{
				
				Intent i = new Intent(HomeScreen.this, AccountActivity.class);
				startActivity(i);
			}
        });
		
        CancelButton.setOnClickListener( new View.OnClickListener()
        {
			public void onClick(View v)
			{
				Uri uri = Uri.parse( "http://www.google.com" );
				startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
			}
        });
        // We save off the progress dialog in a field so that we can dismiss
        // it later. We can't just call dismissDialog(0) because the system
        // can lose track of our dialog if there's an orientation change.
        return dialog;
        */
		
		    // This example shows how to add a custom layout to an AlertDialog
            LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.password_dialog, null);
            
            return new AlertDialog.Builder(HomeScreen.this)
                .setTitle("Enter PAssword:")
                .setView(textEntryView)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	
                    	//If Validate Account is true, Then Open screen
                    	Intent i = new Intent(HomeScreen.this, AccountActivity.class);
        				startActivity(i);
                        /* User clicked OK so do some stuff */
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	dialog.cancel();
                        /* User clicked cancel so do some stuff */
                    }
                })
                .create();
		
            
            /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Enter Message: ")
		       .setCancelable(false).
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                MyActivity.this.finish();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
				
		AlertDialog alert = builder.create();
		
		AlertDialog.Builder builder;
		AlertDialog alertDialog;

		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.password_dialog,
				 	(ViewGroup) findViewById(android.R.id.content).getRootView());

		TextView text = (TextView) layout.findViewById(R.id.password_prompt);
		text.setText("Enter Password: ");

		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		alertDialog = builder.create();*/
    }

}
