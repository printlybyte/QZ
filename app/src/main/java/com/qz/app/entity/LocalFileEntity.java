package com.qz.app.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by du on 2017/2/27.
 */

public class LocalFileEntity implements Parcelable{

    public String name ;
    public String url;
    public Uri path;
    public boolean fromNet;

    public LocalFileEntity(){

    }

    protected LocalFileEntity(Parcel in) {
        name = in.readString();
        url = in.readString();
        path = in.readParcelable(Uri.class.getClassLoader());
        fromNet = in.readByte() != 0;
    }

    public static final Creator<LocalFileEntity> CREATOR = new Creator<LocalFileEntity>() {
        @Override
        public LocalFileEntity createFromParcel(Parcel in) {
            return new LocalFileEntity(in);
        }

        @Override
        public LocalFileEntity[] newArray(int size) {
            return new LocalFileEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeParcelable(path, flags);
        dest.writeByte((byte) (fromNet ? 1 : 0));
    }
}
