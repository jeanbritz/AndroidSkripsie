package com.paysystem.mobileapp.data.operation;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.util.Base64;
import android.util.Log;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.network.NetworkConnection.Method;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

import com.paysystem.mobileapp.config.SharedPrefsConfig;
import com.paysystem.mobileapp.config.WSConfig;
import com.paysystem.mobileapp.data.factory.ClaimListJsonFactory;
import com.paysystem.mobileapp.data.factory.UserListJsonFactory;
import com.paysystem.mobileapp.data.model.Claim;
import com.paysystem.mobileapp.data.model.User;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;
import com.paysystem.mobileapp.util.PreferenceConnector;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class AuthenticationOperation implements Operation {

	
	ArrayList<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();
	String url = "";
	String username = "";
	String password = "";
	
	
	
	public static final String PARAM_WITH_AUTHENTICATE =
            "com.paysystem.mobileapp.extra.authenticate";
	
	
    @Override
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
    	SharedPreferences sharedPrefs = context.getSharedPreferences(
    			PARAM_WITH_AUTHENTICATE,
                Context.MODE_PRIVATE);
    	Editor editor = sharedPrefs.edit();
    	username = request.getString("username").trim();
    	password = request.getString("password").trim();
    	url = WSConfig.API_URL + username+"/";    	
    	Log.i("username",username);
    	Log.i("password", password);
    	Log.i("url",url);
        HttpUriRequest httpRequest = new HttpGet(url); // Or HttpPost(), depends on your needs
        String credentials =  username + ":" +  password;
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        httpRequest.addHeader("Authorization", "Basic " + base64EncodedCredentials);

        editor.putString("Username", username);
    	editor.putString("Authorization", "Basic " + base64EncodedCredentials);
    	editor.commit();
       
        
        HttpClient httpclient = new DefaultHttpClient();
        Bundle bundle = new Bundle();
        try {
        	HttpResponse response = httpclient.execute(httpRequest);
        	       	
        	
            bundle.putString(paySystemRequestFactory.BUNDLE_EXTRA_AUTHENTICATION_RESULT, EntityUtils.toString(response.getEntity()));
            bundle.putString(PARAM_WITH_AUTHENTICATE,"Basic " + base64EncodedCredentials);
            return bundle;
        	} catch (IOException e) {
        	//if something went badly wrong
        	}
        
       
        //bundle.putString(paySystemRequestFactory.BUNDLE_EXTRA_AUTHENTICATION_RESULT, "Error");
        return bundle;
        
        
    }
    /*
    System.out.println("Whole header!!");
    System.out.println("Headers:");
    for (String key : headerMap.keySet()) {
         System.out.print(key + " : " );
         for (String value : headerMap.get(key)) {
    	 System.out.println(value);
         }
    }
    */
}
