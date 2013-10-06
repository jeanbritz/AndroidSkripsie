package com.paysystem.mobileapp.data.factory;
/*

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.data.model.User;

import java.util.ArrayList;

public final class UserListJsonFactory {

    private static final String TAG = UserListJsonFactory.class.getSimpleName();

    private UserListJsonFactory() {
        // No public constructor
    }

    public static Bundle parseResult(String wsResponse) throws DataException {
        ArrayList<User> userList = new ArrayList<User>();

        try {
            JSONObject parser = new JSONObject(wsResponse);
            JSONObject jsonRoot = parser.getJSONObject(JSONTag.USER_LIST_ELEM_USERS);
            JSONArray jsonPersonArray = jsonRoot.getJSONArray(JSONTag.USER_LIST_ELEM_USER);
            int size = jsonPersonArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonPerson = jsonPersonArray.getJSONObject(i);
                User user = new User();

                user.first_name = jsonPerson.getString(JSONTag.USER_LIST_ELEM_USER_FIRST_NAME);
                user.last_name = jsonPerson.getString(JSONTag.USER_LIST_ELEM_USER_LAST_NAME);
                user.email = jsonPerson.getString(JSONTag.USER_LIST_ELEM_USER_EMAIL);
                user.contactnumber = jsonPerson.getString(JSONTag.USER_LIST_ELEM_USER_CONTACTNUMBER);
                user.acct_balance = jsonPerson.getString(JSONTag.USER_LIST_ELEM_USER_ACCT_BALANCE);
                user.acct_available = jsonPerson.getString(JSONTag.USER_LIST_ELEM_USER_ACCT_AVAILABLE);
            	userList.add(user);
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            throw new DataException(e);
        }

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PoCRequestFactory.BUNDLE_EXTRA_CITY_LIST, userList);
        return bundle;
    }
}
*/