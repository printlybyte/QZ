package io.valuesfeng.picker.engine;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import io.valuesfeng.picker.utils.PicturePickerUtils;

import static android.app.Activity.RESULT_OK;

public  class CustomEngine implements LoadEngine {

     @Override
       public void displayImage(String path, ImageView imageView) {
        Log.i("picture", path);
        }

        @Override
        public void displayCameraItem(ImageView imageView) {

        }

        @Override
        public void scrolling(GridView view) {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public CustomEngine() {

        }

        protected CustomEngine(Parcel in) {
        }

        public static final Creator<CustomEngine> CREATOR = new Creator<CustomEngine>() {
            public CustomEngine createFromParcel(Parcel source) {
                return new CustomEngine(source);
            }

            public CustomEngine[] newArray(int size) {
                return new CustomEngine[size];
            }
        };
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mSelected = PicturePickerUtils.obtainResult(data);
//            for (Uri u : mSelected) {
//                Log.i("picture", u.getPath());
//            }
//        }
//    }