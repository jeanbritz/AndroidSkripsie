package com.paysystem.mobileapp.data.model;

import android.content.ContentValues;

import com.paysystem.mobileapp.data.provider.paySystemContent.Invoices;

public final class Invoice {

	
    public int id;
    public int user;
    public int amount_payable;
    public String issued_date;
   

    public Invoice() {
    
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Invoices.Columns.ID.getName(), id);
        cv.put(Invoices.Columns.USER.getName(), user);
        cv.put(Invoices.Columns.AMOUNT_PAYABLE.getName(), amount_payable);
        cv.put(Invoices.Columns.ISSUED_DATE.getName(), issued_date);
        return cv;
    }
}
