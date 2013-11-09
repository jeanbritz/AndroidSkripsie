package com.paysystem.mobileapp.data.operation;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;
import com.paysystem.mobileapp.config.WSConfig;
import com.paysystem.mobileapp.data.factory.NFCDeviceDeleteFactory;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;

public class CrudSyncNfcDeviceDeleteOperation implements Operation {

	public static final String PARAM_USER_ID = "com.mobileapp.paySystem.extra.userId";
    public static final String PARAM_NFCDEVICE_ID_LIST = "com.mobileapp.paySystem.extra.nfcdeviceIdList";

    @Override
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(WSConfig.WS_CRUD_NFCDEVICE_DELETE_PROPERTY_USER_UDID,
                request.getString(PARAM_USER_ID));
        params.put(WSConfig.WS_CRUD_NFCDEVICE_DELETE_PROPERTY_IDS,
                request.getString(PARAM_NFCDEVICE_ID_LIST));

        NetworkConnection networkConnection = new NetworkConnection(context,
                WSConfig.WS_CRUD_NFCDEVICE_DELETE_URL);
        networkConnection.setParameters(params);
        ConnectionResult result = networkConnection.execute();

        long[] deletedPhoneIdArray = NFCDeviceDeleteFactory.parseResult(result.body);

        Bundle bundle = new Bundle();
        bundle.putLongArray(paySystemRequestFactory.BUNDLE_EXTRA_NFCDEVICE_DELETE_DATA,
                deletedPhoneIdArray);
        return bundle;
    }
	
}
