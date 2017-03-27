package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.qz.app.MainActivity;
import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.utils.FragmentManager;

/**
 * Created by Administrator on 2015/6/26.
 */
public class WorkpageFragment extends BaseFragment {


    private ViewGroup appclick;
    @Override
    public void initViews(ViewGroup rootView) {
        appclick = (ViewGroup) rootView.findViewById(R.id.appclick);
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_workpage;
    }

    @Override
    public void setViews(View rootView) {

        ((MainActivity)getActivity()).showBottom();
        appclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(),AppFragment.class.getName()));
            }
        });
    }
}
