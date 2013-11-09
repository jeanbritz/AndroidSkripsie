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
import com.paysystem.mobileapp.data.factory.NFCDeviceAddEditFactory;
import com.paysystem.mobileapp.data.model.NFCDevice;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;

public class CrudSyncNfcDeviceAddEditOperation implements Operation {

	public static final String PARAM_USER_ID = "com.foxykeep.datadroidpoc.extra.userId";
    public static final String PARAM_NFCDEVICE = "com.foxykeep.datadroidpoc.extra.phone";

    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_USER_ID = "user_id";
    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_ID = "id";
    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_NAME = "uid";
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
        HashMap<String, String> params = new HashMap<String, String>();
        NFCDevice nfcdevice = (NFCDevice) request.getParcelable(PARAM_NFCDEVICE);
        params.put(WSConfig.WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_USER_ID,
                request.getString(PARAM_USER_ID));
        params.put(WSConfig.WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_ID, String.valueOf(nfcdevice.id));
        params.put(WSConfig.WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_USERNAME, nfcdevice.username);
        params.put(WSConfig.WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_UID, nfcdevice.uid);
       

        NetworkConnection networkConnection = new NetworkConnection(context,
                WSConfig.WS_CRUD_NFCDEVICE_ADD_EDIT_URL);
        networkConnection.setParameters(params);
        ConnectionResult result = networkConnection.execute();

        NFCDevice serverPhone = NFCDeviceAddEditFactory.parseResult(result.body);

        Bundle bundle = new Bundle();
        bundle.putParcelable(paySystemRequestFactory.BUNDLE_EXTRA_NFCDEVICE_ADD_EDIT_DATA, serverPhone);
        return bundle;
    }
	
}
