package com.paysystem.mobileapp.data.factory;

import com.foxykeep.datadroid.exception.DataException;
import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.data.model.NFCDevice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class NFCDeviceAddEditFactory {

    private static final String TAG = NFCDeviceAddEditFactory.class.getSimpleName();

    private NFCDeviceAddEditFactory() {
        // No public constructor
    }

    public static NFCDevice parseResult(String wsResponse) throws DataException {
    	NFCDevice nfcdevice = new NFCDevice();

        try {
        	Log.i(TAG, wsResponse);
        	
        	JSONObject jsonNFCDeviceArray =  new JSONObject(wsResponse);
            
        	nfcdevice.id = jsonNFCDeviceArray.getInt(JSONTag.NFCDEVICE_CRUD_ADD_EDIT_ELEM_ID);
        	nfcdevice.username = jsonNFCDeviceArray.getString(JSONTag.NFCDEVICE_CRUD_ADD_EDIT_ELEM_USERNAME);
        	nfcdevice.uid = jsonNFCDeviceArray.getString(JSONTag.NFCDEVICE_CRUD_ADD_EDIT_ELEM_UID);
           
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            throw new DataException(e);
        }

        return nfcdevice;
    }

}

