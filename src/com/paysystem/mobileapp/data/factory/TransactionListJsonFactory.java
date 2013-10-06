package com.paysystem.mobileapp.data.factory;

import com.foxykeep.datadroid.exception.DataException;
import com.paysystem.mobileapp.config.JSONTag;
import com.paysystem.mobileapp.data.model.Transaction;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class TransactionListJsonFactory {

    private static final String TAG = TransactionListJsonFactory.class.getSimpleName();

    private TransactionListJsonFactory() {
        // No public constructor
    }

    public static ArrayList<Transaction> parseResult(String wsResponse) throws DataException {
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

        try {
        	Log.i(TAG, "Parsing response to JSON Array");
        	JSONArray jsonTransactionArray =  new JSONArray(wsResponse);
            
            int size = jsonTransactionArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsonTransaction = jsonTransactionArray.getJSONObject(i);
                
                Transaction transaction = new Transaction();
                                                        
                	transaction.id = jsonTransaction.getInt(JSONTag.TRANSACTION_LIST_ELEM_ID);
                	transaction.invoice_id = jsonTransaction.getInt(JSONTag.TRANSACTION_LIST_ELEM_INVOICE_ID);
                	transaction.username = jsonTransaction.getString(JSONTag.TRANSACTION_LIST_ELEM_USERNAME);
                	transaction.processed_date = jsonTransaction.getString(JSONTag.TRANSACTION_LIST_ELEM_PROCESSED_DATE);
                	transaction.amount = jsonTransaction.getInt(JSONTag.TRANSACTION_LIST_ELEM_AMOUNT);
                	transaction.debit_credit = jsonTransaction.getString(JSONTag.TRANSACTION_LIST_ELEM_DEBIT_CREDIT);
                   
                	transactionList.add(transaction);
               
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            throw new DataException(e);
        }

        return transactionList;
    }
}

