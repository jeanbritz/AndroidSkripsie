package com.paysystem.mobileapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

import com.paysystem.mobileapp.config.SharedPrefsConfig;

public final class UserManager {

	private UserManager() {
		
	}
	
	public static String getUserId(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(
                SharedPrefsConfig.SHARED_PREFS_FILENAME,
                Context.MODE_PRIVATE);
        String userId = sharedPrefs.getString(SharedPrefsConfig.SHARED_PREFS_USER_ID, null);

        if (userId == null) {
            userId = UUID.randomUUID().toString();
            sharedPrefs.edit().putString(SharedPrefsConfig.SHARED_PREFS_USER_ID, userId).commit();
        }

        return userId;
    }
	
	public static String getAuthHeader(Context context){
		
		SharedPreferences sharedPrefs = context.getSharedPreferences(
                SharedPrefsConfig.SHARED_PREFS_FILENAME,
                Context.MODE_PRIVATE);
		return null;
		
	}
	
}
