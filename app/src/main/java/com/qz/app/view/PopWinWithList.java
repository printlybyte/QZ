package com.qz.app.view;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qz.app.base.BaseActivity;
import com.qz.app.utils.CommonUtils;

/**
 * Created by du on 2017/2/15.
 */

public class PopWinWithList {


    private PopupWindow  popupWindow;
    private int width;

    public PopWinWithList(FragmentActivity activity, ViewGroup popview) {

        popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        width = CommonUtils.getDpDementions(100);
        popupWindow.setWidth(width);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);



        popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_MENU) {

                    if (getPopWinState()) {
                        popupWindow.dismiss();
                        return true;
                    }
                }

                return false;
            }
        });

    }



    public boolean getPopWinState() {

        return popupWindow.isShowing();
    }

    public void hide() {
        if (null != popupWindow) {
            popupWindow.dismiss();

        }
    }
    public void show(View anchor) {
        if (null != popupWindow) {

            popupWindow.showAsDropDown(anchor,
                     -CommonUtils.getDpDementions(45), 0);

        }
    }
}
