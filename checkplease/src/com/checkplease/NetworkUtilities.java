package com.checkplease;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.codehaus.jettison.json.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Provides utility methods for communicating with the server.
 */
final public class NetworkUtilities {
    /** The tag used to log to adb console. */
    private static final String TAG = "NetworkUtilities";
    /** POST parameter name for the user's account name */
    public static final String PARAM_USERNAME = "username";
    /** POST parameter name for the user's password */
    public static final String PARAM_PASSWORD = "password";
    /** POST parameter name for the user's authentication token */
    public static final String PARAM_FIRST_NAME = "firstName";
    /** POST parameter name for the user's authentication token */
    public static final String PARAM_LAST_NAME = "lastName";
    /** POST parameter name for the user's authentication token */
    public static final String PARAM_AUTH_TOKEN = "authtoken";
    /** POST parameter name for the user's authentication token */
    public static final String PARAM_EMAIL = "email";
    /** POST parameter name for the user's authentication token */
    public static final String PARAM_BALANCE = "balance";
  
    /** POST parameter name for the client's last-known sync state */
    public static final String PARAM_SYNC_STATE = "syncstate";
    /** POST parameter name for the sending client-edited contact info */
    public static final String PARAM_CONTACTS_DATA = "contacts";
    /** Timeout (in ms) we specify for each http request */
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;
    /** Base URL for the v2 Sample Sync Service */
    //public static final String BASE_URL = "http://10.10.66.130:8080/API/rest";
//    public static final String BASE_URL = "http://107.20.135.212:12080/API";
    public static final String BASE_URL = "http://107.20.135.212:12080/APIv2/call";
//    public static final String BASE_URL = "http://136.152.36.13:8080/APIv2/call";
//    /APIv2/call/customer
    //http://107.20.135.212:12080/API/rest/Chipotle
    /** URI for authentication service */
    public static final String AUTH_URI = BASE_URL + "/customer";
    /** URI for Chiptole Restaurant service */
    public static final String CHIPOTLE_RESTAURANT_URI = BASE_URL + "/examples/items";
    /** URI for Stores service */
    public static final String STORES_URI = BASE_URL + "/examples/stores";
    /** URI for Login service */
    public static final String LOGIN_URI = BASE_URL + "/customer/";
    /** URI for Create User service */
    private NetworkUtilities() {
    }

    /**
     * Configures the httpClient to connect to the URL provided.
     */
    public static HttpClient getHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        ConnManagerParams.setTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        return httpClient;
    }

    /**
     * Connects to the SampleSync test server, authenticates the provided
     * username and password.
     *
     * @param username The server account username
     * @param password The server account password
     * @return String The authentication token returned by the server (or null)
     */
    
    public static String authenticate(String username, String password) {

        final HttpResponse resp;
        String result = null;

        Log.i(TAG, "Authenticating to: " + LOGIN_URI);
        
        final HttpPost post = new HttpPost(LOGIN_URI);
        post.setHeader("Accept", "application/json");
        post.setHeader(HTTP.CONTENT_TYPE, "application/json");
        post.setHeader("User-Agent", "com.checkplease");
        
        JSONObject json = new JSONObject();
        try 
        {
        	json.put("username", "Niki510");
        	json.put("password", "BlahRahDah");
        	
        	/*
        	json.put(PARAM_USERNAME, username);
        	json.put(PARAM_PASSWORD, password);
        	*/
//        	StringEntity se  = new StringEntity("JSON: " + json.toString());
        	StringEntity se = new StringEntity(json.toString());  
        	Log.i(TAG, "JSON: " + json.toString());
            post.setEntity(se);
            resp = getHttpClient().execute(post);
        	
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
            {
            	
                InputStream istream = (resp.getEntity() != null) ? resp.getEntity().getContent()
                        : null;
                if (istream != null) 
                {
                	Log.v(TAG, "Successful authentication");
                	result = convertStreamToString(istream);
                	//return convertStreamToString(istream);
                	//BufferedReader ireader = new BufferedReader(new InputStreamReader(istream));
                    //authToken = ireader.readLine().trim();
                }
                
            }
            else
            {
                Log.e(TAG, "Error authenticating" + resp.getStatusLine());
                result = "" + resp.getStatusLine().getStatusCode();
                return result;
            }
        }
        catch (final IOException e) 
        {
            Log.e(TAG, "IOException when getting authtoken", e);
            result = "IOException when getting authtoken";
            return result;
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        finally 
        {
            Log.v(TAG, "getAuthtoken completing");
        }
		return result;
    }
    
    public static String createUser(String email, String password, String firstName, String lastName, String username, String balance) 
    {
    	final HttpResponse resp;
    	String result = null;
    
        final HttpPut put = new HttpPut(LOGIN_URI);
        put.setHeader("Accept", "application/json");
        put.setHeader(HTTP.CONTENT_TYPE, "application/json");
        put.setHeader("User-Agent", "com.checkplease");
        
        JSONObject json = new JSONObject();
        try 
        {
        	
        	json.put(PARAM_BALANCE, Double.valueOf(balance));
        	json.put(PARAM_LAST_NAME, lastName);
        	json.put(PARAM_FIRST_NAME, firstName);
        	json.put(PARAM_EMAIL, email);
        	json.put(PARAM_PASSWORD, password);
        	json.put(PARAM_USERNAME, "johnD");
        	
        	/*
        	json.put("username", "johnD");
        	json.put("password", "jd");
        	json.put("email", "jd@mail.com");
        	json.put("lastName", "doe");
        	json.put("firstName", "john");
        	json.put("balance", Double.valueOf("123.50"));
        	*/
        	
        	
        	
//        	StringEntity se  = new StringEntity("JSON: " + json.toString());
        	StringEntity se = new StringEntity(json.toString());  
        	Log.i(TAG, "JSON: " + json.toString());
        	//            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            
//          post.setEntity(se);
//        	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            put.setEntity(se);
            //resp = getHttpClient().execute(get);
            resp = getHttpClient().execute(put);
        	
            //String authToken = null;
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
            {
            	
                InputStream istream = (resp.getEntity() != null) ? resp.getEntity().getContent()
                        : null;
                if (istream != null) 
                {
                	Log.v(TAG, "Successful Create user");
                	result = convertStreamToString(istream);
                	//return convertStreamToString(istream);
                	//BufferedReader ireader = new BufferedReader(new InputStreamReader(istream));
                    //authToken = ireader.readLine().trim();
                }
                
            }
            else
            {
                Log.e(TAG, "Error creating user " + resp.getStatusLine());
                result = "" + resp.getStatusLine().getStatusCode();
                return result;
            }
        }
        catch (final IOException e) 
        {
            Log.e(TAG, "IOException when creating user", e);
            result = "IOException when creating user";
            return result;
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        finally 
        {
            Log.v(TAG, "getAuthtoken completing create user");
        }
		return result;
    }
    
    public static Bitmap getImage(String photo_url_str){
    	URL newurl;
       	Bitmap bitmap = null;
    	try {
			newurl = new URL(photo_url_str);
		
			bitmap =  BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
		 } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return bitmap;
    	/*
    	ImageView profile_photo = new ImageView(null);
    	ImageView profile_photo.setImageBitmap(bitmap);
    	*/
    }
    


    public static String getStores() {

        final HttpResponse resp;
        //final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair(PARAM_PASSWORD, password));
        String result = null;
        final HttpEntity entity;
        /*
        try {
            entity = new UrlEncodedFormEntity(params);
        } catch (final UnsupportedEncodingException e) {
            // this should never happen.
            throw new IllegalStateException(e);
        }*/
        
        Log.i(TAG, "Authenticating to: " + STORES_URI);
        final HttpGet get = new HttpGet(STORES_URI);
        //get.addHeader(entity.getContentType());
        get.addHeader("Accept", "application/json");
        /*
        final HttpPost post = new HttpPost(AUTH_URI);
        post.addHeader(entity.getContentType());
        post.setEntity(entity);
        */
        try 
        {
            resp = getHttpClient().execute(get);
            //String authToken = null;
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
            {
            	
                InputStream istream = (resp.getEntity() != null) ? resp.getEntity().getContent()
                        : null;
                if (istream != null) 
                {
                	Log.v(TAG, "Successful authentication");
                	result = convertStreamToString(istream);
                	//return convertStreamToString(istream);
                	//BufferedReader ireader = new BufferedReader(new InputStreamReader(istream));
                    //authToken = ireader.readLine().trim();
                }
                
            }
            else 
            {
                Log.e(TAG, "Error authenticating" + resp.getStatusLine());
                result = "Error authenticating CODE: " + resp.getStatusLine().getStatusCode();
                return result;
            }
        }
        catch (final IOException e) 
        {
            Log.e(TAG, "IOException when getting authtoken", e);
            result = "IOException when getting authtoken";
            return result;
        } 
        finally 
        {
            Log.v(TAG, "getAuthtoken completing");
        }
		return result;
    }

    public static String sendOrder() {

        final HttpResponse resp;
        //final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair(PARAM_PASSWORD, password));
        String result = null;
        final HttpEntity entity;
        /*
        try {
            entity = new UrlEncodedFormEntity(params);
        } catch (final UnsupportedEncodingException e) {
            // this should never happen.
            throw new IllegalStateException(e);
        }*/
        
        Log.i(TAG, "Authenticating to: " + STORES_URI);
        final HttpGet get = new HttpGet(STORES_URI);
        //get.addHeader(entity.getContentType());
        get.addHeader("Accept", "application/json");
        /*
        final HttpPost post = new HttpPost(AUTH_URI);
        post.addHeader(entity.getContentType());
        post.setEntity(entity);
        */
        try 
        {
            resp = getHttpClient().execute(get);
            //String authToken = null;
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
            {
            	
                InputStream istream = (resp.getEntity() != null) ? resp.getEntity().getContent()
                        : null;
                if (istream != null) 
                {
                	Log.v(TAG, "Successful authentication");
                	result = convertStreamToString(istream);
                	//return convertStreamToString(istream);
                	//BufferedReader ireader = new BufferedReader(new InputStreamReader(istream));
                    //authToken = ireader.readLine().trim();
                }
                
            }
            else 
            {
                Log.e(TAG, "Error authenticating" + resp.getStatusLine());
                result = "Error authenticating CODE: " + resp.getStatusLine().getStatusCode();
                return result;
            }
        }
        catch (final IOException e) 
        {
            Log.e(TAG, "IOException when getting authtoken", e);
            result = "IOException when getting authtoken";
            return result;
        } 
        finally 
        {
            Log.v(TAG, "getAuthtoken completing");
        }
		return result;
    }
    
    
 
    private static String convertStreamToString(InputStream is) {
    	 
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Perform 2-way sync with the server-side contacts. We send a request that
     * includes all the locally-dirty contacts so that the server can process
     * those changes, and we receive (and return) a list of contacts that were
     * updated on the server-side that need to be updated locally.
     *
     * @param account The account being synced
     * @param authtoken The authtoken stored in the AccountManager for this
     *            account
     * @param serverSyncState A token returned from the server on the last sync
     * @param dirtyContacts A list of the contacts to send to the server
     * @return A list of contacts that we need to update locally
     */
    /*
    public static List<RawContact> syncContacts(
            Account account, String authtoken, long serverSyncState, List<RawContact> dirtyContacts)
            throws JSONException, ParseException, IOException, AuthenticationException {
        // Convert our list of User objects into a list of JSONObject
        List<JSONObject> jsonContacts = new ArrayList<JSONObject>();
        for (RawContact rawContact : dirtyContacts) {
            jsonContacts.add(rawContact.toJSONObject());
        }

        // Create a special JSONArray of our JSON contacts
        JSONArray buffer = new JSONArray(jsonContacts);

        // Create an array that will hold the server-side contacts
        // that have been changed (returned by the server).
        final ArrayList<RawContact> serverDirtyList = new ArrayList<RawContact>();

        // Prepare our POST data
        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(PARAM_USERNAME, account.name));
        params.add(new BasicNameValuePair(PARAM_AUTH_TOKEN, authtoken));
        params.add(new BasicNameValuePair(PARAM_CONTACTS_DATA, buffer.toString()));
        if (serverSyncState > 0) {
            params.add(new BasicNameValuePair(PARAM_SYNC_STATE, Long.toString(serverSyncState)));
        }
        Log.i(TAG, params.toString());
        HttpEntity entity = new UrlEncodedFormEntity(params);

        // Send the updated friends data to the server
        Log.i(TAG, "Syncing to: " + SYNC_CONTACTS_URI);
        final HttpPost post = new HttpPost(SYNC_CONTACTS_URI);
        post.addHeader(entity.getContentType());
        post.setEntity(entity);
        final HttpResponse resp = getHttpClient().execute(post);
        final String response = EntityUtils.toString(resp.getEntity());
        if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // Our request to the server was successful - so we assume
            // that they accepted all the changes we sent up, and
            // that the response includes the contacts that we need
            // to update on our side...
            final JSONArray serverContacts = new JSONArray(response);
            Log.d(TAG, response);
            for (int i = 0; i < serverContacts.length(); i++) {
                RawContact rawContact = RawContact.valueOf(serverContacts.getJSONObject(i));
                if (rawContact != null) {
                    serverDirtyList.add(rawContact);
                }
            }
        } else {
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                Log.e(TAG, "Authentication exception in sending dirty contacts");
                throw new AuthenticationException();
            } else {
                Log.e(TAG, "Server error in sending dirty contacts: " + resp.getStatusLine());
                throw new IOException();
            }
        }

        return serverDirtyList;
    }
	*/
}