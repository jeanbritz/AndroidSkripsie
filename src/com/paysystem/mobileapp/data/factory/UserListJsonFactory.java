package com.paysystem.mobileapp.data.factory;


import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.foxykeep.datadroid.exception.DataException;
import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.data.model.User;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;

import java.util.ArrayList;

public final class UserListJsonFactory {

    private static final String TAG = UserListJsonFactory.class.getSimpleName();

    private UserListJsonFactory() {
        // No public constructor
    }

    public static Bundle parseResult(String wsResponse) throws DataException {
    	User user = new User();
    	
        try {
        		
        		JSONArray jsonUser =  new JSONArray(wsResponse);
        		JSONObject jsonUserObj = jsonUser.getJSONObject(0); 
                user.first_name = jsonUserObj.getString("first_name");
                user.last_name = jsonUserObj.getString(JSONTag.USER_LIST_ELEM_USER_LAST_NAME);
                user.email = jsonUserObj.getString(JSONTag.USER_LIST_ELEM_USER_EMAIL);
                user.contactnumber = jsonUserObj.getString(JSONTag.USER_LIST_ELEM_USER_CONTACTNUMBER);
                user.acct_balance = jsonUserObj.getString(JSONTag.USER_LIST_ELEM_USER_ACCT_BALANCE);
                user.acct_available = jsonUserObj.getString(JSONTag.USER_LIST_ELEM_USER_ACCT_AVAILABLE);
            	
            
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            throw new DataException(e);
               
        }
        Bundle bundle = new Bundle(); 
        bundle.putParcelable(paySystemRequestFactory.BUNDLE_EXTRA_AUTHENTICATION_RESULT, user);
        return bundle;
    }
}
