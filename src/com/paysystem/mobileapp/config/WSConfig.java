package com.paysystem.mobileapp.config;

public final class WSConfig {

    private WSConfig() {
        // No public constructor
    }

    
    public static final String DB_TABLE_PREFIX = "paysystem_";
    
    public static final String ROOT_URL = "http://ml.sun.ac.za:8000/";
    
    
    public static final String API_URL = ROOT_URL + "api/";
    public static final String API_USER_URL = API_URL + "jibritz/";
    public static final String JSON_FORMAT_PARAMETER = "?format=json";
    
    // Authentication WS (e.g. http://ml.sun.ac.za:8000/api/login/
    public static final String WS_AUTHENTICATION_URL = API_URL + "login/";
    
    // UserList WS
    public static final String WS_USER_LIST_URL_JSON = API_URL + "users" + JSON_FORMAT_PARAMETER;
    

    // ClaimsList WS
    public static final String WS_CLAIM_LIST_URL_JSON = API_USER_URL + "claims" + JSON_FORMAT_PARAMETER;
    
    // TransactionList WS
    public static final String WS_TRANSACTION_LIST_URL_JSON = API_USER_URL + "transactions" + JSON_FORMAT_PARAMETER;
   
    // NFCDeviceList WS
    public static final String WS_CRUD_NFCDEVICE_LIST_URL = API_USER_URL + "nfcdevices" + JSON_FORMAT_PARAMETER;
    
    // NFCDeviceAddEdit WS
    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_URL = API_USER_URL + "nfcdevices";
    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_USER_ID = "user_id";
    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_ID = "id";
    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_USERNAME = "username";
    public static final String WS_CRUD_NFCDEVICE_ADD_EDIT_PROPERTY_UID = "uid";
     
    // NfcDeviceDelete WS
    public static final String WS_CRUD_NFCDEVICE_DELETE_URL = API_URL + "invoices/";

    public static final String WS_CRUD_NFCDEVICE_DELETE_PROPERTY_USER_UDID = "user_id";
    public static final String WS_CRUD_NFCDEVICE_DELETE_PROPERTY_IDS = "ids";
    
    
    // InvoiceList WS
    public static final String WS_INVOICE_LIST_URL_JSON = API_USER_URL + "invoices" + JSON_FORMAT_PARAMETER;
    
    //NFCDeviceRequestTypes
    public static final String WS_NFCDEVICES_REQUEST_TYPES_GET = API_USER_URL + "nfcdevices";
    public static final String WS_NFCDEVICES_REQUEST_TYPES_POST = API_USER_URL + "nfcdevices";
    public static final String WS_NFCDEVICES_REQUEST_TYPES_PUT = API_URL + "nfcdevices";
    public static final String WS_REQUEST_TYPES_DELETE = API_URL + "nfcdevices";

    /**
    // CrudPhoneList WS
    public static final String WS_CRUD_PHONE_LIST_URL = ROOT_URL + "crud/list.php";

    public static final String WS_CRUD_PHONE_LIST_PROPERTY_USER_UDID = "userUdid";

    // CrudPhoneDelete WS
    public static final String WS_CRUD_PHONE_DELETE_URL = ROOT_URL + "crud/delete.php";

    public static final String WS_CRUD_PHONE_DELETE_PROPERTY_USER_UDID = "userUdid";
    public static final String WS_CRUD_PHONE_DELETE_PROPERTY_IDS = "ids";

    // CrudPhoneAddEdit WS
    public static final String WS_CRUD_PHONE_ADD_EDIT_URL = ROOT_URL + "crud/addedit.php";

    public static final String WS_CRUD_PHONE_ADD_EDIT_PROPERTY_USER_UDID = "userUdid";
    public static final String WS_CRUD_PHONE_ADD_EDIT_PROPERTY_ID = "id";
    public static final String WS_CRUD_PHONE_ADD_EDIT_PROPERTY_NAME = "name";
    public static final String WS_CRUD_PHONE_ADD_EDIT_PROPERTY_MANUFACTURER = "manufacturer";
    public static final String WS_CRUD_PHONE_ADD_EDIT_PROPERTY_ANDROID_VERSION = "androidVersion";
    public static final String WS_CRUD_PHONE_ADD_EDIT_PROPERTY_SCREEN_SIZE = "screenSize";
    public static final String WS_CRUD_PHONE_ADD_EDIT_PROPERTY_PRICE = "price";
**/
    // UserDetails WS
    private static final String WS_REQUEST_TYPES_URL_BASE = ROOT_URL + "requesttype/";

    public static final String WS_USER_DETAIL_REQUEST = API_URL + "jibritz?format=json";
    

    public static final String WS_REQUEST_TYPE_NUMBER = "number";
    
}
