package com.qz.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatDialogFragment;

import com.qz.app.App;
import com.qz.app.R;


/**
 * Created by win7 on 2017/1/21.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FragmentDialog_v7 extends AppCompatDialogFragment {

    public static FragmentDialog_v7 newInstance(String message, int requestCode) {
        return newInstance(R.string.ok, R.string.cancel,message,requestCode,null);
    }
    //授权专用
    public static FragmentDialog_v7 newInstance(String message, int requestCode,  String[] permissions) {
              return newInstance(R.string.ok,R.string.cancel,message,requestCode,permissions);
    }


    public static FragmentDialog_v7 newInstance(int positiveButton, int negativeButton,
             String message, int requestCode,  String[] permissions) {
        FragmentDialog_v7 dialogFragment = (FragmentDialog_v7) AppCompatDialogFragment.instantiate(App.mContext, FragmentDialog_v7.class.getName());
        // Initialize configuration as arguments
        DialogConfig config = new DialogConfig(
                positiveButton, negativeButton, message, requestCode, permissions);
        dialogFragment.setArguments(config.toBundle());
        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       //这里获取acitvity传来的接口
    }
    @Override
    public void onDetach() {
        super.onDetach();

    }
}
