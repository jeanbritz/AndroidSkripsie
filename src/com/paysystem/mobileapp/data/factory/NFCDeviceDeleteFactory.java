package com.paysystem.mobileapp.data.factory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.foxykeep.datadroid.exception.DataException;
import com.paysystem.mobileapp.config.JSONTag;

public class NFCDeviceDeleteFactory {

	private static final String TAG = NFCDeviceDeleteFactory.class.getSimpleName();
	
	private NFCDeviceDeleteFactory() {
        // No public constructor
    }

    public static long[] parseResult(String wsResponse) throws DataException {

        long[] deletedPhoneIdArray = null;

        try {
            JSONObject parser = new JSONObject(wsResponse);
            JSONObject jsonRoot = parser.getJSONObject(JSONTag.NFCDEVICE_DELETE_ELEM_NFCDEVICES);
            JSONArray jsonPhoneArray = jsonRoot
                    .getJSONArray(JSONTag.NFCDEVICE_DELETE_ELEM_NFCDEVICE);
            int size = jsonPhoneArray.length();

            deletedPhoneIdArray = new long[size];

            for (int i = 0; i < size; i++) {
                deletedPhoneIdArray[i] = jsonPhoneArray.getJSONObject(i).getLong(
                        JSONTag.NFCDEVICE_CRUD_DELETE_ELEM_ID);
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            throw new DataException(e);
        }

        return deletedPhoneIdArray;
    }
	
}
