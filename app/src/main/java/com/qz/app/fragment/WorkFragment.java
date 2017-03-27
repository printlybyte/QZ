package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.utils.FragmentManager;

/**
 * 工作首页
 * Created by Administrator on 2015/6/26.
 */
public class WorkFragment extends BaseFragment {
    private LinearLayout renwubt;
    private LinearLayout shenpibt;
    private LinearLayout kaoqinbt;
    private LinearLayout huibaobt;


    @Override
    public void initViews(ViewGroup rootView) {
        renwubt = (LinearLayout) rootView.findViewById(R.id.renwu_click);
        shenpibt = (LinearLayout) rootView.findViewById(R.id.shenpi_click);
        kaoqinbt = (LinearLayout) rootView.findViewById(R.id.kaoqin_click);
        huibaobt = (LinearLayout) rootView.findViewById(R.id.huibao_click);

        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.renwu_click:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(),RelaselistFragment.class.getName()));
                        break;
                    case R.id.shenpi_click:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(),ShenpilistFragment.class.getName()));
                        break;
                    case R.id.kaoqin_click:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(),KaoqinFragment.class.getName()));
                        break;
                    case R.id.huibao_click:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(),HuibaoFragment.class.getName()));
                        break;
                }

            }
        };
        renwubt.setOnClickListener(clicListener);
        shenpibt.setOnClickListener(clicListener);
        kaoqinbt.setOnClickListener(clicListener);
        huibaobt.setOnClickListener(clicListener);
        initTitledView("工作");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    public void setViews(View rootView) {

    }

    public void initViewWithEntity() {


    }

}
