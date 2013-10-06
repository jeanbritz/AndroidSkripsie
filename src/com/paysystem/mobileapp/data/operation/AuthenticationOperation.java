package com.paysystem.mobileapp.data.operation;

import android.content.Context;
import android.os.Bundle;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;

import com.paysystem.mobileapp.config.WSConfig;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;

import org.apache.http.auth.UsernamePasswordCredentials;

public class AuthenticationOperation implements Operation {

    public static final String PARAM_WITH_AUTHENTICATE =
            "com.foxykeep.datadroidpoc.extra.authenticate";

    private static final String LOGIN = "jibritz";
    private static final String PASSWD = "Ovjsa!-23"; // Worst password ever \o/

    @Override
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
        NetworkConnection networkConnection = new NetworkConnection(context,
                WSConfig.WS_AUTHENTICATION_URL);
        if (request.getBoolean(PARAM_WITH_AUTHENTICATE)) {
            networkConnection.setCredentials(new UsernamePasswordCredentials(LOGIN, PASSWD));
        }
        ConnectionResult result = networkConnection.execute();

        Bundle bundle = new Bundle();
        bundle.putString(paySystemRequestFactory.BUNDLE_EXTRA_AUTHENTICATION_RESULT, result.body);
        return bundle;
    }

}
