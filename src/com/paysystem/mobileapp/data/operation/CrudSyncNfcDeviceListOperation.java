package com.paysystem.mobileapp.data.operation;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;
import com.paysystem.mobileapp.config.WSConfig;
import com.paysystem.mobileapp.data.factory.InvoiceListJsonFactory;
import com.paysystem.mobileapp.data.factory.NFCDeviceListFactory;
import com.paysystem.mobileapp.data.model.Invoice;
import com.paysystem.mobileapp.data.model.NFCDevice;
import com.paysystem.mobileapp.data.provider.paySystemProvider;
import com.paysystem.mobileapp.data.provider.paySystemContent.Invoices;
import com.paysystem.mobileapp.data.requestmanager.paySystemRequestFactory;

public final class CrudSyncNfcDeviceListOperation implements Operation {

	public static final String PARAM_USER_ID = "com.foxykeep.datadroidpoc.extra.userId";

    @Override
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(WSConfig.WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_USER_ID,
                request.getString(PARAM_USER_ID));

        NetworkConnection networkConnection = new NetworkConnection(context,
                WSConfig.WS_CRUD_NFCDEVICE_LIST_URL);
        networkConnection.setParameters(params);

        ConnectionResult result = networkConnection.execute();

        ArrayList<NFCDevice> nfcdeviceList = NFCDeviceListFactory.parseResult(result.body);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(paySystemRequestFactory.BUNDLE_EXTRA_NFCDEVICE_LIST, nfcdeviceList);
        return bundle;
    }
}
