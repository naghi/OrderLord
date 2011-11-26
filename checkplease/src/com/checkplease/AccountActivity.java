package com.checkplease;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class AccountActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    addPreferencesFromResource(R.xml.user_prefs);
	}

}
