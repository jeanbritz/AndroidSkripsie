package com.paysystem.mobileapp.config;

public final class WSConfig {

    private WSConfig() {
        // No public constructor
    }

    public static final String DB_TABLE_PREFIX = "paysystem_";
    
    public static final String ROOT_URL = "http://ml.sun.ac.za:8000/";
    
    
    public static final String API_URL = ROOT_URL + "api/";
    public static final String USER_URL = API_URL + "jibritz/";
    public static final String JSON_FORMAT_PARAMETER = "?format=json";
    
    // Authentication WS
    public static final String WS_AUTHENTICATION_URL = ROOT_URL + "/accounts/login/";
    
    // UserList WS
    public static final String WS_USER_LIST_URL_JSON = API_URL + "users" + JSON_FORMAT_PARAMETER;
    

    // ClaimsList WS
    public static final String WS_CLAIM_LIST_URL_JSON = USER_URL + "claims" + JSON_FORMAT_PARAMETER;
    
    // TransactionList WS
    public static final String WS_TRANSACTION_LIST_URL_JSON = USER_URL + "transactions" + JSON_FORMAT_PARAMETER;
   

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

    // CityList WS
    private static final String WS_REQUEST_TYPES_URL_BASE = ROOT_URL + "requesttype/";

    public static final String WS_REQUEST_TYPES_GET = WS_REQUEST_TYPES_URL_BASE + "get.php";
    public static final String WS_REQUEST_TYPES_POST = WS_REQUEST_TYPES_URL_BASE + "post.php";
    public static final String WS_REQUEST_TYPES_PUT = WS_REQUEST_TYPES_URL_BASE + "put.php";
    public static final String WS_REQUEST_TYPES_DELETE = WS_REQUEST_TYPES_URL_BASE + "delete.php";

    public static final String WS_REQUEST_TYPE_NUMBER = "number";
    **/
}
