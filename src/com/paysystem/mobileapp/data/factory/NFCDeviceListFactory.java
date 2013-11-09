package com.paysystem.mobileapp.data.factory;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.foxykeep.datadroid.exception.DataException;
import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.data.model.NFCDevice;

public class NFCDeviceListFactory {

	 private static final String TAG = NFCDeviceListFactory.class.getSimpleName();

	    private NFCDeviceListFactory() {
	        // No public constructor
	    }

	    public static ArrayList<NFCDevice> parseResult(String wsResponse) throws DataException {
	        ArrayList<NFCDevice> invoiceList = new ArrayList<NFCDevice>();

	        try {
	        	JSONArray jsonNFCDeviceArray =  new JSONArray(wsResponse);
	            
	            int size = jsonNFCDeviceArray.length();
	            for (int i = 0; i < size; i++) {
	                JSONObject jsonInvoice = jsonNFCDeviceArray.getJSONObject(i);
	                
	                NFCDevice nfcdevice = new NFCDevice();
	                
	                nfcdevice.id = jsonInvoice.getInt(JSONTag.NFCDEVICE_CRUD_LIST_ELEM_ID);
	                nfcdevice.username = jsonInvoice.getString(JSONTag.NFCDEVICE_CRUD_LIST_ELEM_USERNAME);
	                nfcdevice.uid = jsonInvoice.getString(JSONTag.NFCDEVICE_CRUD_LIST_ELEM_UID);
	                invoiceList.add(nfcdevice);
	               
	            }
	        } catch (JSONException e) {
	            Log.e(TAG, "JSONException", e);
	            throw new DataException(e);
	        }

	        return invoiceList;
	    }
	
}
