package com.paysystem.mobileapp.config;

public final class JSONTag {

	private JSONTag() {
		 // No public constructor
	}
	
	// Users Tags
	//public static final String USER_LIST_ELEM_USERS = "";
	//public static final String USER_LIST_ELEM_USER = "";
	public static final String USER_LIST_ELEM_USER_FIRST_NAME = "first_name";
	public static final String USER_LIST_ELEM_USER_LAST_NAME = "last_name";
	public static final String USER_LIST_ELEM_USER_EMAIL = "email";
	public static final String USER_LIST_ELEM_USER_CONTACTNUMBER = "contactnumber";
	public static final String USER_LIST_ELEM_USER_ACCT_BALANCE = "acct_balance";
	public static final String USER_LIST_ELEM_USER_ACCT_AVAILABLE = "acct_available";
	
	// ClaimsList Tags
	//public static final String CLAIM_LIST_ELEM_CLAIMS = "#";
	//public static final String CLAIM_LIST_ELEM_CLAIM = "#";
	public static final String CLAIM_LIST_ELEM_ID = "id";
	public static final String CLAIM_LIST_ELEM_USERNAME = "user";
	public static final String CLAIM_LIST_ELEM_TITLE = "title";
	public static final String CLAIM_LIST_ELEM_TYPE = "type";
	public static final String CLAIM_LIST_ELEM_EXPIRY_DATE = "expiry_date";
	public static final String CLAIM_LIST_ELEM_CLAIMED = "claimed";
	public static final String CLAIM_LIST_ELEM_AMOUNT = "amount";
	
	// InvoicesList Tags
	//public static final String INVOICE_LIST_ELEM_INVOICES = "invoices";
	//public static final String INVOICE_LIST_ELEM_INVOICE = "invoice";
	public static final String INVOICE_LIST_ELEM_ID = "id";
	public static final String INVOICE_LIST_ELEM_USER = "user";
	public static final String INVOICE_LIST_ELEM_AMOUNT_PAYABLE = "amount_payable";
	public static final String INVOICE_LIST_ELEM_ISSUED_DATE = "issued_date";
	
	// TransactionsList Tags
	//public static final String TRANSACTION_LIST_ELEM_TRANSACTIONS = "transactions";
	//public static final String TRANSACTION_LIST_ELEM_TRANSACTION = "transaction";
	public static final String TRANSACTION_LIST_ELEM_ID = "id";
	public static final String TRANSACTION_LIST_ELEM_INVOICE_ID = "invoice_id";
	public static final String TRANSACTION_LIST_ELEM_USERNAME = "user";
	public static final String TRANSACTION_LIST_ELEM_PROCESSED_DATE = "processed_date";
	public static final String TRANSACTION_LIST_ELEM_AMOUNT = "amount";
	public static final String TRANSACTION_LIST_ELEM_DEBIT_CREDIT = "debit_credit";
	
	// NFCDevicesList Tags
	//public static final String NFCDEVICE_LIST_ELEM_DEVICES = "nfcdevices";
	//public static final String NFCDEVICE_LIST_ELEM_DEVICE = "nfcdevice";
	
	public static final String NFCDEVICE_CRUD_LIST_ELEM_ID = "id";
	public static final String NFCDEVICE_CRUD_LIST_ELEM_USERNAME = "username";
	public static final String NFCDEVICE_CRUD_LIST_ELEM_UID = "uid";
	
	
	// NFCDevicesDelete Tags
	public static final String NFCDEVICE_DELETE_ELEM_NFCDEVICES = "";
	public static final String NFCDEVICE_DELETE_ELEM_NFCDEVICE = "";
	public static final String NFCDEVICE_CRUD_DELETE_ELEM_ID = "id";
	
	
	// NFCDevicesAddEdit Tags
	public static final String NFCDEVICE_LIST_ELEM_DEVICES = "nfcdevices";
	public static final String NFCDEVICE_LIST_ELEM_DEVICE = "nfcdevice";
	public static final String NFCDEVICE_CRUD_ADD_EDIT_ELEM_ID = "id";
	public static final String NFCDEVICE_CRUD_ADD_EDIT_ELEM_USERNAME = "username";
	public static final String NFCDEVICE_CRUD_ADD_EDIT_ELEM_UID = "uid";
	
	// RequestTypes WS tags
    public static final String REQUEST_TYPE_VALUE = "value";
		
	
}
