package com.paysystem.mobileapp.data.model;

import com.paysystem.mobileapp.data.provider.paySystemContent.NFCDevices;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;


public final class NFCDevice implements Parcelable{

	
    public int id;
    public String username;
    public String uid;

    public NFCDevice() {
    
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(NFCDevices.Columns.ID.getName(), id);
        cv.put(NFCDevices.Columns.USER.getName(), username);
        cv.put(NFCDevices.Columns.UID.getName(), uid);
        
        return cv;
    }
    
    
    private NFCDevice(Parcel in) {
        id = in.readInt();
        username = in.readString();
        uid = in.readString();
        
    }
    
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(uid);
        
		
	}
	public static final Parcelable.Creator<NFCDevice> CREATOR = new Parcelable.Creator<NFCDevice>() {
        public NFCDevice createFromParcel(Parcel in) {
            return new NFCDevice(in);
        }

        public NFCDevice[] newArray(int size) {
            return new NFCDevice[size];
        }
    };
}


