package com.checkplease;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.checkplease.Login.UserLoginTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class CreateUser extends Activity {

	Button createAccountButton;
	private EditText email;
	private EditText username;
	private EditText password;
	private EditText balance;
	private EditText repassword;
	private EditText firstName;
	private EditText lastName;
	private CreateUserTask mCreateUserTask = null;
	Editable emailString; 
	Editable passwordString; 
	Editable firstNameString; 
	Editable lastNameString; 
	Editable repasswordString;
	private final String TAG = "CreateUserActivity";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);
        createAccountButton = (Button) findViewById(R.id.create_account);
        firstName = (EditText) findViewById(R.id.fname);
        lastName = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email_cu);
        balance = (EditText) findViewById(R.id.balance_cu);
        username = (EditText) findViewById(R.id.username_cu);
        password = (EditText) findViewById(R.id.password_cu);
        repassword = (EditText) findViewById(R.id.repassword_cu);
        
        createAccountButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) 
            {
            	//DO CLIENT SIDE VALIDATION
            	//SEND INFO TO SERVER
            	//IF SUCCESSFUL
            	
            	mCreateUserTask = new CreateUserTask();
                mCreateUserTask.execute();
        
            }
          });
        
	}
     
	  
	public void finishCreateUser(String user)
	{
    	
    	Intent i;
    	i = new Intent(CreateUser.this, HomeScreen.class);
    	startActivity(i);
    	
		
	}
	 public void onAuthenticationResult(String authToken) {

		 Pattern p = Pattern.compile("\\d{3}+");
	        Matcher m = p.matcher(authToken);
	        boolean fail = m.matches();
	        Log.i(TAG, "onAuthenticationResult(" + fail + ")");

	        // Our task is complete, so clear it out
	        mCreateUserTask = null;

	        // Hide the progress dialog
	        //hideProgress();

	        if (!fail) 
	        {
	        	finishCreateUser(authToken);
	        	
	        }
	        else 
	        {
	        	
	        	int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                //Toast toast = Toast.makeText(context, text, duration);
                Toast toast = Toast.makeText(context, "Creating User Failed", duration);
                //toast.makeText(context, text, duration);
        		toast.show();
	            Log.e(TAG, "onAuthenticationResult: failed to authenticate");
	            //onAuthenticationCancel();
	            //finish();
		        	
	        		
	        }
	        	//Context context = getApplicationContext();
            	//CharSequence text = "Username or Password field is empty";

	        	

	        
	    }
	 

	    public void onAuthenticationCancel() {
	        Log.i(TAG, "onAuthenticationCancel()");

	        // Hide the progress dialog
	        //hideProgress();
	        //cancelProgress();
	        // Our task is complete, so clear it out
	        mCreateUserTask = null;

	       
	    }
		    
	 public class CreateUserTask extends AsyncTask<Void, Void, String> {

			@Override
	        protected String doInBackground(Void... params) {
	            // We do the actual work of authenticating the user
	            // in the NetworkUtilities class.
	            try {
	            	/*
	            	 * Username = slayter 
	            	 * Password = admin
	            	 */

	            	Editable emailString = email.getText();
	            	Editable passwordString = password.getText();
	            	Editable firstNameString = firstName.getText();
	            	Editable lastNameString = lastName.getText();
	            	Editable userNameString = username.getText();
	            	Editable balanceString = balance.getText();
	            	
	                return NetworkUtilities.createUser(emailString.toString(), passwordString.toString(), firstNameString.toString(), lastNameString.toString(), userNameString.toString(), balanceString.toString());
	            	//return NetworkUtilities.getChipotleRestaurant();
	            } catch (Exception ex) {
	                Log.e(TAG, "UserLoginTask.doInBackground: failed to authenticate");
	                Log.i(TAG, ex.toString());
	                return null;
	            }
	        }

	        @Override
	        protected void onPostExecute(final String authToken) {
	            // On a successful authentication, call back into the Activity to
	            // communicate the authToken (or null for an error).
	            onAuthenticationResult(authToken);
	        }

	        @Override
	        protected void onCancelled() {
	            // If the action was canceled (by the user clicking the cancel
	            // button in the progress dialog), then call back into the
	            // activity to let it know.
	            onAuthenticationCancel();
	        }
	    }

}
