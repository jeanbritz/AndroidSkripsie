package com.paysystem.mobileapp.data.factory;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.foxykeep.datadroid.exception.DataException;
import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.data.model.Invoice;

public final class InvoiceListJsonFactory {

    private static final String TAG = InvoiceListJsonFactory.class.getSimpleName();

    private InvoiceListJsonFactory() {
        // No public constructor
    }

    public static ArrayList<Invoice> parseResult(String wsResponse) throws DataException {
        ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();

        try {
        	JSONArray jsonInvoiceArray =  new JSONArray(wsResponse);
            
            int size = jsonInvoiceArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonInvoice = jsonInvoiceArray.getJSONObject(i);
                
                Invoice invoice = new Invoice();
                
                   	invoice.id = jsonInvoice.getInt(JSONTag.INVOICE_LIST_ELEM_ID);
                	invoice.user = jsonInvoice.getInt(JSONTag.INVOICE_LIST_ELEM_USER);
                   	invoice.amount_payable = jsonInvoice.getInt(JSONTag.INVOICE_LIST_ELEM_AMOUNT_PAYABLE);
                	invoice.issued_date = jsonInvoice.getString(JSONTag.INVOICE_LIST_ELEM_ISSUED_DATE);
                	invoiceList.add(invoice);
               
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            throw new DataException(e);
        }

        return invoiceList;
    }
}