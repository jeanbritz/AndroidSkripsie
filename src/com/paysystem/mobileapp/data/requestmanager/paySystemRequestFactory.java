package com.paysystem.mobileapp.data.requestmanager;

import com.foxykeep.datadroid.requestmanager.Request;
import com.paysystem.mobileapp.data.model.NFCDevice;
import com.paysystem.mobileapp.data.operation.AuthenticationOperation;
import com.paysystem.mobileapp.data.operation.ClaimListOperation;
import com.paysystem.mobileapp.data.operation.CrudSyncNfcDeviceAddEditOperation;
import com.paysystem.mobileapp.data.operation.CrudSyncNfcDeviceDeleteOperation;
import com.paysystem.mobileapp.data.operation.CrudSyncNfcDeviceListOperation;

/**
 * Class used to create the {@link Request}s.
 *
 * @author Foxykeep
 */
public final class paySystemRequestFactory {

    // Request types
    public static final int REQUEST_TYPE_CLAIM_LIST = 0;
    public static final int REQUEST_TYPE_TRANSACTION_LIST = 1;
    public static final int REQUEST_TYPE_INVOICE_LIST = 2;
    public static final int REQUEST_TYPE_AUTHENTICATION = 3;
    public static final int REQUEST_TYPE_CUSTOM_REQUEST_EXCEPTION = 4;

    public static final int REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_LIST = 10;
    public static final int REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_DELETE = 11;
    public static final int REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_ADD = 12;
    public static final int REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_EDIT = 13;

  //  public static final int REQUEST_TYPE_RSS_FEED = 20;

   // public static final int REQUEST_TYPE_COMPUTE_SQUARE = 30;

    // Response data
   // public static final String BUNDLE_EXTRA_CITY_LIST =
   //         "com.foxykeep.datadroidpoc.extra.cityList";
    public static final String BUNDLE_EXTRA_NFCDEVICE_LIST =
            "com.mobileapp.paySystem.extra.nfcdeviceList";
    public static final String BUNDLE_EXTRA_NFCDEVICE_DELETE_DATA =
            "com.paysystem.mobileapp.extra.nfcdeviceDeleteData";
    public static final String BUNDLE_EXTRA_NFCDEVICE_ADD_EDIT_DATA =
            "com.mobileapp.paySystem.extra.nfcdeviceAddEditData";
    
    public static final String BUNDLE_EXTRA_AUTHENTICATION_RESULT =
           "com.paysystem.mobileapp.extra.authenticationResult";
    
   // public static final String BUNDLE_EXTRA_RSS_FEED_DATA =
   //         "com.foxykeep.datadroidpoc.extra.rssFeed";
  //  public static final String BUNDLE_EXTRA_SQUARE =
   //         "com.foxykeep.datadroidpoc.extra.square";
   // public static final String BUNDLE_EXTRA_ERROR_MESSAGE =
   //         "com.foxykeep.datadroidpoc.extra.errorMessage";

    private paySystemRequestFactory() {
        // no public constructor
    }

    /**
     * Create the request to get the list of persons and save it in the database.
     *
     * @param returnFormat 0 for XML, 1 for JSON.
     * @return The request.
     */
    public static Request getClaimListRequest() {
        Request request = new Request(REQUEST_TYPE_CLAIM_LIST);
        return request;
    }
    
    public static Request getTransactionListRequest() {
    	Request request = new Request(REQUEST_TYPE_TRANSACTION_LIST);
		return request;
    }
    public static Request getInvoiceListRequest() {
    	Request request = new Request(REQUEST_TYPE_INVOICE_LIST);
		return request;
    }
    
    /**
     * Create the request to get the list of cities and save it in the memory provider.
     *
     * @return The request.
     */
   /* public static Request getCityListRequest() {
        Request request = new Request(REQUEST_TYPE_CITY_LIST);
        request.setMemoryCacheEnabled(true);
        return request;
    }*/

    /**
     * Create the request to get the list of cities and save it in the memory provider.
     *
     * @return The request.
     */
    /*public static Request getCityList2Request() {
        Request request = new Request(REQUEST_TYPE_CITY_LIST_2);
        request.setMemoryCacheEnabled(true);
        return request;
    }*/

    /**
     * Create the request to the authentication webservice.
     * <p>
     * The returned message will be either a greeting if the authentication worked or a message
     * telling you to authenticate otherwise.
     *
     * @param withAuthenticate Whether the request will be made with the authentication data or not.
     * @return The request.
     */
    public static Request authenticationRequest(boolean withAuthenticate) {
        Request request = new Request(REQUEST_TYPE_AUTHENTICATION);
        request.put(AuthenticationOperation.PARAM_WITH_AUTHENTICATE, withAuthenticate);
        request.setMemoryCacheEnabled(true);
        return request;
    }

    /**
     * Create the request to get the list of cities and save it in the memory provider.
     * <p>
     * Depending on the withException parameter, a {@link CustomRequestException} will be triggered
     * or not.
     *
     * @param withException Whether to trigger the {@link CustomRequestException} or not.
     * @return The request.
     */
  /*  public static Request getCityListExceptionRequest(boolean withException) {
        Request request = new Request(REQUEST_TYPE_CUSTOM_REQUEST_EXCEPTION);
        request.put(CustomRequestExceptionOperation.PARAM_WITH_EXCEPTION, withException);
        request.setMemoryCacheEnabled(true);
        return request;
    }*/

    /**
     * Create the request to get the list of nfcdevices synchronously and save it in the memory.
     *
     * @param userId the id of the user (generated by the application).
     * @return The request.
     */
    public static Request getSyncNfcDeviceListRequest(String userId) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_LIST);
        request.setMemoryCacheEnabled(true);
        request.put(CrudSyncNfcDeviceListOperation.PARAM_USER_ID, userId);
        return request;
    }
   
    /**
     * Create the request to delete a nfcdevice synchronously.
     *
     * @param userId the id of the user (generated by the application).
     * @param id the entry id.
     * @return The request.
     */
    public static Request deleteSyncNfcDeviceRequest(String userId, String nfcdeviceIdList) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_DELETE);
        request.setMemoryCacheEnabled(true);
        request.put(CrudSyncNfcDeviceDeleteOperation.PARAM_USER_ID, userId);
        request.put(CrudSyncNfcDeviceDeleteOperation.PARAM_NFCDEVICE_ID_LIST, nfcdeviceIdList);
        return request;
    }
    
    /**
     * Create the request to add a nfcdevice synchronously.
     *
     * @param userId the id of the user (generated by the application).
     * @param id the entry id.
     * @param username the user who uses it.
     * @param uid the uid number of the nfc device.
     * @return The request.
     */
   public static Request addSyncNfcDeviceRequest(String userId, String username,
            String uid) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_ADD);
        return addEditSyncNfcDeviceRequest(request, userId, -1, username, uid);
    }

    /**
     * Create the request to edit a nfcdevice synchronously.
     *
     * @param userId the id of the user (generated by the application).
     * @param id the entry id.
     * @param username the user who uses it.
     * @param uid the uid number of the nfc device.
     * 
     * @return The request.
     */
    public static Request editSyncNfcDeviceRequest(String userId, int id, String username,
            String uid) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_NFCDEVICE_EDIT);
        return addEditSyncNfcDeviceRequest(request, userId, id, username, uid);
    }

    private static Request addEditSyncNfcDeviceRequest(Request request, String userId,
            int id, String username, String uid) {
        request.setMemoryCacheEnabled(true);
        request.put(CrudSyncNfcDeviceAddEditOperation.PARAM_USER_ID, userId);

        NFCDevice nfcdevice = new NFCDevice();
        nfcdevice.id = id;
        nfcdevice.username = username;
        nfcdevice.uid = uid;
        
        request.put(CrudSyncNfcDeviceAddEditOperation.PARAM_NFCDEVICE, nfcdevice);
        return request;
    }
    

}
