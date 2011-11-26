package com.checkplease;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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


public class Login extends Activity {
    /** Called when the activity is first created. */
	private Button loginButton;
	private TextView forgotPassword;
	private EditText username;
	private EditText password;
	private TextView newUser;
	private final String TAG = "LoginActivity";
	private Editable userNameString;
	private Editable passwordString;
	private UserLoginTask mAuthTask = null;
	protected boolean mRequestNewAccount = false;
	/** Keep track of the progress dialog so we can dismiss it */
    private ProgressDialog mProgressDialog = null;
    public static final String USER_DATA = "user_data";
    private Toast toast;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        loginButton = (Button) findViewById(R.id.login_main);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        
        newUser = (TextView) findViewById(R.id.newUser);
        
        
        Log.i(TAG, "user cancelling authentication");
        //GOES TO HOME SCREEN
        loginButton.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
            	cancelProgress();
            	userNameString = username.getText();
            	passwordString = password.getText();
            	
            	//CharSequence text = "Username or Password field is empty";
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                //Toast toast = Toast.makeText(context, text, duration);
                toast = Toast.makeText(context, R.string.login_activity_loginfail_text_both, duration);
                //toast.makeText(context, text, duration);
                if(userNameString.toString().contentEquals("") || passwordString.toString().contentEquals(""))
            	{
            		//toast.show();
            	}
            	else
            	{
            		showProgress();
            		String user = NetworkUtilities.authenticate(userNameString.toString(), passwordString.toString());
            		finishLogin();
            		
            		
        	        Pattern p = Pattern.compile("\\d{3}+");
        	        Matcher m = p.matcher(user);
        	        boolean fail = m.matches();
        	        // Our task is complete, so clear it out
        	        //mAuthTask = null;
        	        Log.i(TAG, "onAuthenticationResult(" + fail + ")");
        	        // Hide the progress dialog
        	        hideProgress();

        	        if (!fail) 
        	        {

        	        	SharedPreferences usersettings = getSharedPreferences(USER_DATA, 0);
        	            SharedPreferences.Editor editor = usersettings.edit();
        	            //{"id":"java.lang.Long","version":"java.lang.Long","username":"java.lang.String","password":"java.lang.String",
        	            //"email":"java.lang.String","firstName":"java.lang.String","lastName":"java.lang.String","balance":"java.lang.Double"}
        	    		try {
        	    				JSONObject object = (JSONObject) new JSONTokener(user).nextValue();
        	    				{
        	    					editor.putLong(getString(R.string.user_id),Long.parseLong(object.getString("id")));
        	    					editor.putLong(getString(R.string.version),Long.parseLong(object.getString("version")));
        	    					editor.putString(getString(R.string.username),object.getString("username"));
        	    					editor.putString(getString(R.string.password),object.getString("password"));
        	    					editor.putString(getString(R.string.email),object.getString("email"));
        	    					editor.putString(getString(R.string.firstname),object.getString("firstName"));
        	    					editor.putString(getString(R.string.lastname),object.getString("lastName"));
        	    					editor.putFloat(getString(R.string.balance),Float.parseFloat(object.getString("balance")));
        	    					
        	    					
        	    					
        	    					
        	    					//commit changes
        	    					editor.commit();
        	    				}
        	            //editor.putBoolean("silentMode", mSilentMode);
        	        	finishLogin();
        	        	} catch (JSONException e) {
        	    			// TODO Auto-generated catch block
        	    			e.printStackTrace();
        	    		}
        	          		

        	        	
        	        }
        	        else 
        	        {
        	        	//if(authToken.contentEquals("204"))
        	        	{
        	        		cancelProgress();
        	        		toast.setText(R.string.login_activity_loginfail_text_both);// = Toast.makeText(context, R.string.login_activity_loginfail_text_both, duration);
        	            	toast.show();
        		            Log.e(TAG, "onAuthenticationResult: failed to authenticate");
        		            //onAuthenticationCancel();
        		            //finish();
        	        	}
        	        	
        	        	//Context context = getApplicationContext();
                    	//CharSequence text = "Username or Password field is empty";

        	        	

        	        }

            		/*mAuthTask = new UserLoginTask();
                    mAuthTask.execute();
					*/
            		//VERIFY USERNAME AND PASSWORD FROM SERVER
	            	/*
                    Intent i;
	            	i = new Intent(Login.this, HomeScreen.class);
	                startActivity(i);
	                */
            	}
            }
          });
                //NEW USER GOES TO CREATE USER VIEW
        newUser.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent i;
            	i = new Intent(Login.this, CreateUser.class);
            	startActivity(i);
            	
            }
          });
        //FORGOT PASSWORD GOES TO A WEB PAGE
        forgotPassword.setOnClickListener( new View.OnClickListener()
        {
			public void onClick(View v)
			{
				Uri uri = Uri.parse( "http://www.google.com" );
				startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
			}
        });
    }
	

	@Override
    protected void onPause() {
		super.onPause();
		hideProgress();
	
	}
	@Override
	protected void onResume() {
		super.onResume();
		cancelProgress();
	}
	/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save away the original text, so we still have it if the activity
        // needs to be killed while paused.
        outState.putString(ORIGINAL_CONTENT, mOriginalContent);
    }
    */
	protected Dialog onCreateDialog(int id, Bundle args) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.ui_activity_authenticating));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Log.i(TAG, "user cancelling authentication");
                if (mAuthTask != null) {
                    mAuthTask.cancel(true);
                }
            }
        });
        // We save off the progress dialog in a field so that we can dismiss
        // it later. We can't just call dismissDialog(0) because the system
        // can lose track of our dialog if there's an orientation change.
        mProgressDialog = dialog;
        return dialog;
    }
	 
    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication. The button is configured to call
     * handleLogin() in the layout XML.
     *
     * @param view The Submit button for which this method is invoked
     */
	/*
    public void handleLogin(View view) {
        if (mRequestNewAccount) {
            mUsername = mUsernameEdit.getText().toString();
        }
        mPassword = mPasswordEdit.getText().toString();
        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
            mMessage.setText(getMessage());
        } else {
            // Show a progress dialog, and kick off a background task to perform
            // the user login attempt.
            showProgress();
            mAuthTask = new UserLoginTask();
            mAuthTask.execute();
        }
    }

	*/
    private void showProgress() {
        showDialog(0);
    }
	
    /**
     * Hides the progress UI for a lengthy operation.
     */
	
    private void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    
    private void cancelProgress(){
    	if(mProgressDialog != null){
    		mProgressDialog.cancel();
    		mProgressDialog = null;
    	}
    }
    
    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     *
     * @param result the confirmCredentials result.
     */
    /*
    private void finishConfirmCredentials(boolean result) {
        Log.i(TAG, "finishConfirmCredentials()");
        final Account account = new Account(mUsername, Constants.ACCOUNT_TYPE);
        mAccountManager.setPassword(account, mPassword);
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
    */

    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. We store the
     * authToken that's returned from the server as the 'password' for this
     * account - so we're never storing the user's actual password locally.
     *
     * @param result the confirmCredentials result.
     */
    /*
    private void finishLogin(String authToken) {

        Log.i(TAG, "finishLogin()");
        final Account account = new Account(mUsername, Constants.ACCOUNT_TYPE);
        if (mRequestNewAccount) {
            mAccountManager.addAccountExplicitly(account, mPassword, null);
            // Set contacts sync for this account.
            ContentResolver.setSyncAutomatically(account, ContactsContract.AUTHORITY, true);
        } else {
            mAccountManager.setPassword(account, mPassword);
        }
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
    */
	
    private void finishLogin()
    {
    	
    	Intent i;
    	//SharedPreferences usersettings = getSharedPreferences(USER_DATA, 0);
    	//Log.i("item.getString(\"first_name\")", usersettings.getString("first_name",""));
    	//Log.i("item.getString(\"last_name\")", usersettings.getString("last_name",""));
		i = new Intent(Login.this, HomeScreen.class);
		cancelProgress();
		startActivity(i);
			
      		

    }
    
    
	 public void onAuthenticationResult(String authToken) {

		 	//finishLogin();
	        //boolean success = ((authToken != null) && (authToken.length() > 0));
	        
	        Pattern p = Pattern.compile("\\d{3}+");
	        Matcher m = p.matcher(authToken);
	        boolean fail = m.matches();
	        // Our task is complete, so clear it out
	        mAuthTask = null;
	        Log.i(TAG, "onAuthenticationResult(" + fail + ")");
	        // Hide the progress dialog
	        hideProgress();
	        finishLogin();
	        /*
	        if (!fail) 
	        {

	        	SharedPreferences usersettings = getSharedPreferences(USER_DATA, 0);
	            SharedPreferences.Editor editor = usersettings.edit();
	            //{"version":"0","balance":"30.0","first_name":"Todorin","last_name":"Radev","password":"admin","user_name":"slayter"}\n
	    		try {
	    				JSONObject object = (JSONObject) new JSONTokener(authToken).nextValue();
	    				{
	    					editor.putLong("version",Long.parseLong(object.getString("version")));
	    					editor.putFloat("balance",Float.parseFloat(object.getString("balance")));
	    					editor.putString("first_name",object.getString("first_name"));
	    					editor.putString("last_name",object.getString("last_name"));
	    					editor.putString("user_name",object.getString("user_name"));
	    					editor.putString("password",object.getString("password"));
	    					Log.i("item.getString(\"first_name\")", object.getString("first_name"));
	    					Log.i("item.getString(\"last_name\")", object.getString("last_name"));
	    					//commit changes
	    					editor.commit();
	    				}
	            //editor.putBoolean("silentMode", mSilentMode);
	        	finishLogin();
	        	} catch (JSONException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	          		

	        	
	        }
	        else 
	        {
	        	//if(authToken.contentEquals("204"))
	        	{
	        		toast.setText(R.string.login_activity_loginfail_text_both);// = Toast.makeText(context, R.string.login_activity_loginfail_text_both, duration);
	            	toast.show();
		            Log.e(TAG, "onAuthenticationResult: failed to authenticate");
		            //onAuthenticationCancel();
		            //finish();
	        	}
	        	
	        	//Context context = getApplicationContext();
            	//CharSequence text = "Username or Password field is empty";

	        	

	        }
	         */	        
	    }
	 

	    public void onAuthenticationCancel() {
	        Log.i(TAG, "onAuthenticationCancel()");

	        // Hide the progress dialog
	        //hideProgress();
	        //cancelProgress();
	        // Our task is complete, so clear it out
	        mAuthTask = null;

	       
	    }
	    
	    
	 public class UserLoginTask extends AsyncTask<Void, Void, String> {

			@Override
	        protected String doInBackground(Void... params) {
	            // We do the actual work of authenticating the user
	            // in the NetworkUtilities class.
	            try {
	            	/*
	            	 * Username = slayter 
	            	 * Password = admin
	            	 */
	                return NetworkUtilities.authenticate(userNameString.toString(), passwordString.toString());
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