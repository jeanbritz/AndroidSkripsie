package com.paysystem.mobileapp.data.operation;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService;
import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.config.WSConfig;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;

public final class UserDetailsOperation implements RequestService.Operation {

    public static final String PARAM_METHOD = "com.paysystem.mobileapp.extra.method";
    public static final String PARAM_NUMBER = "com.paysystem.mobileapp.extra.number";

    @Override
    public Bundle execute(Context context, Request request)
            throws ConnectionException, DataException, CustomRequestException {
        String url;
        NetworkConnection.Method method;

        switch (request.getInt(PARAM_METHOD)) {
            case 0: // GET
                url = WSConfig.WS_USER_DETAIL_REQUEST;
                method = NetworkConnection.Method.GET;
                break;
            case 1: // POST
                url = WSConfig.WS_USER_DETAIL_REQUEST;
                method = NetworkConnection.Method.POST;
                break;
            case 2: // PUT
                // TODO for now hack to keep it as a GET method as my server doesn't accept PUT
                // requests
                url = WSConfig.WS_USER_DETAIL_REQUEST;
                method = NetworkConnection.Method.PUT;

                break;
            
            default:
                throw new IllegalArgumentException("Unknown method: "
                        + request.getInt(PARAM_METHOD));
        }

        NetworkConnection connection = new NetworkConnection(context, url);
        connection.setMethod(method);
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair(WSConfig.WS_REQUEST_TYPE_NUMBER,
                request.getIntAsString(PARAM_NUMBER)));
        connection.setParameters(params);
        NetworkConnection.ConnectionResult result = connection.execute();

        // Parse the result
        Bundle bundle = new Bundle();
        try {
            JSONObject root = new JSONObject(result.body);
            //bundle.putInt(paySystemRequestFactory.BUNDLE_EXTRA_SQUARE,
             //       root.getInt(JSONTag.REQUEST_TYPE_VALUE));
        } catch (JSONException e) {
            throw new DataException(e);
        }

        return bundle;
    }
}

