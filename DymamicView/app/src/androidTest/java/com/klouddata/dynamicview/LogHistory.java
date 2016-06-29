package com.klouddata.dynamicview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vivekm on 6/29/2016.
 */
public class LogHistory implements Parcelable {
    protected LogHistory(Parcel in) {
    }

    protected LogHistory() {
    }

    public static final Creator<LogHistory> CREATOR = new Creator<LogHistory>() {
        @Override
        public LogHistory createFromParcel(Parcel in) {
            return new LogHistory(in);
        }

        @Override
        public LogHistory[] newArray(int size) {
            return new LogHistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public void addEntry(String str, long l) {

    }

}
