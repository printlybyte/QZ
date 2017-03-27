package com.qz.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.utils.FragmentManager;

/**
 * Created by Administrator on 2015/6/26.
 */
public class HrFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout departbt;
    private LinearLayout zhidubt;
    private LinearLayout costbt;
    private LinearLayout incombt;
    private LinearLayout leavebt;
    ;

    @Override
    public void initViews(ViewGroup rootView) {
        departbt = (LinearLayout) rootView.findViewById(R.id.depart_click);
        zhidubt = (LinearLayout) rootView.findViewById(R.id.zhidu_click);
        costbt = (LinearLayout) rootView.findViewById(R.id.cost_click);
        incombt = (LinearLayout) rootView.findViewById(R.id.incom_click);
        leavebt = (LinearLayout) rootView.findViewById(R.id.leave_click);
        initTitledView("HR");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hr;
    }

    @Override
    public void setViews(View rootView) {
        departbt.setOnClickListener(this);
        zhidubt.setOnClickListener(this);
        costbt.setOnClickListener(this);
        incombt.setOnClickListener(this);
        leavebt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == departbt) {
            Bundle bundle =new Bundle();
            bundle.putInt(Constant.EMP_FROM, Constant.EMP_FROM_HR);
            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(),EmployeesFragment.class.getName(),bundle));
        } else if (v == zhidubt) {

            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(),DocumentlistFragment.class.getName()));
        } else if (v == costbt) {

        }else if (v == incombt) {

        }else if (v == leavebt) {

        }
    }
}
