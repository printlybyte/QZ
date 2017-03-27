package com.qz.app.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qz.app.R;
import com.qz.app.utils.FragmentManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by win7 on 2016/8/29.
 */
public class SelectionDialogFragment extends DialogFragment implements View.OnClickListener {

    public static Uri imagUrl;
    private OnButtonClickListener listener;
    public int pos;

    public static SelectionDialogFragment newInstance(OnButtonClickListener listener) {
        SelectionDialogFragment fragment = new SelectionDialogFragment();
        fragment.setListener(listener);
        return fragment;
    }


    public void setListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CustomFragmentDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_frombutton);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        dialog.findViewById(R.id.editbut).setOnClickListener(this);
        dialog.findViewById(R.id.delbut).setOnClickListener(this);
        dialog.findViewById(R.id.cancel).setOnClickListener(this);
        dialog.findViewById(R.id.rootlayout).setOnClickListener(this);
        return dialog;
    }


    public void show(android.support.v4.app.FragmentManager fragmentManager, int pos) {
        this.pos = pos;
        super.show(fragmentManager, "");
    }
    @Override
    public void onClick(View v) {
        dismiss();
        if (null != listener) {
            switch (v.getId()) {
                case R.id.editbut:
                    listener.OnEdit(pos);
                    break;
                case R.id.delbut:
                    listener.OnDel(pos);
                    break;
                case R.id.cancel:

                listener.OnCancel(pos);
                    break;
                case R.id.rootlayout:

                    break;
            }
        }


    }

    public interface OnButtonClickListener {
        void OnEdit(int pos);

        void OnDel(int pos);

        void OnCancel(int pos);

    }


}
