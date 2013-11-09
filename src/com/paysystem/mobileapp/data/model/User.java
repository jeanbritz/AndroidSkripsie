package com.paysystem.mobileapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class User implements Parcelable {

    public int id;
    public String username;
    public String first_name;
    public String last_name;
    public String email;
    public String groups;
    
    // extended fields
    public String contactnumber;
    public String acct_balance;
    public String acct_available;

    public User() {
    }

    // Parcelable management
    private User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        groups = in.readString();
        
        // extended fields
        contactnumber = in.readString();
        acct_balance = in.readString();
        acct_available = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(groups);
        
        dest.writeString(contactnumber);
        dest.writeString(acct_balance);
        dest.writeString(acct_available);
        
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}