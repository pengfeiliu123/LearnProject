package com.lpf.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liupengfei on 17/2/8.
 */

public class Fish implements Parcelable {

    protected Fish(Parcel in) {
    }

    public static final Creator<Fish> CREATOR = new Creator<Fish>() {
        @Override
        public Fish createFromParcel(Parcel in) {
            return new Fish(in);
        }

        @Override
        public Fish[] newArray(int size) {
            return new Fish[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
