package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.qz.app.MainActivity;
import com.qz.app.R;
import com.qz.app.base.BaseFragment;

/**
 * Created by Administrator on 2015/6/26.
 */
public class MypageFragment extends BaseFragment {
    @Override
    public void initViews(ViewGroup rootView) {
       
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mypage;
    }

    @Override
    public void setViews(View rootView) {
        ((MainActivity)getActivity()).showBottom();
    }

}
