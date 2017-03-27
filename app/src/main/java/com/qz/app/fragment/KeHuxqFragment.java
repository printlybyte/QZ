package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;


/**
 * Created by 易超然 on 2017/3/18.
 */

public class KeHuxqFragment extends BaseFragment {
    private ImageView leftimg;
    private TextView title;
    private ImageView moretbut;

    @Override
    public void initViews(ViewGroup rootView) {
        initTitledView("客户");

    }

    @Override
    public int getLayoutId() {
        return R.layout.feagment_kehuxq;
    }

    @Override
    public void setViews(View rootView) {

    }
}
