package com.paysystem.mobileapp.data.model;

import com.paysystem.mobileapp.data.provider.paySystemContent.Claims;
import android.content.ContentValues;


public final class Claim {

	
    public int id;
    public String username;
    public String title;
    public String type;
    public String expiry_date;
    public boolean claimed;
    public int amount;

    public Claim() {
    
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Claims.Columns.ID.getName(), id);
        cv.put(Claims.Columns.USER.getName(), username);
        cv.put(Claims.Columns.TITLE.getName(), title);
        cv.put(Claims.Columns.TYPE.getName(), type);
        cv.put(Claims.Columns.EXPIRY_DATE.getName(), expiry_date);
        cv.put(Claims.Columns.CLAIMED.getName(), claimed);
        cv.put(Claims.Columns.AMOUNT.getName(), amount);
        return cv;
    }
}

