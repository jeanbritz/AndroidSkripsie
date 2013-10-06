package com.paysystem.mobileapp.data.model;

import com.paysystem.mobileapp.data.provider.paySystemContent.Transactions;

import android.content.ContentValues;


public final class Transaction {

	
    public int id;
    public int invoice_id;
    public String username;
    public String processed_date;
    public int amount;
    public String debit_credit;
    
    public Transaction() {
    
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Transactions.Columns.ID.getName(), id);
        cv.put(Transactions.Columns.INVOICE_ID.getName(), invoice_id);
        cv.put(Transactions.Columns.USERNAME.getName(), username);
        cv.put(Transactions.Columns.PROCESSED_DATE.getName(), processed_date);
        cv.put(Transactions.Columns.AMOUNT.getName(), amount);
        cv.put(Transactions.Columns.DEBIT_CREDIT.getName(), debit_credit);
        return cv;
    }
}


