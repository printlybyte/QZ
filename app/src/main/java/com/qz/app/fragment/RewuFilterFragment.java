package com.qz.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.utils.FragmentManager;

/**
 * Created by du on 2017/2/24.
 */

public class RewuFilterFragment extends BaseFragment {

    private ImageView leftimg;
    private TextView title;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private RadioButton radio5;
    private TextView create_person_data;
    private TextView chargmen_data;
    private TextView resetbt;
    private TextView okbt;
    public static DepAndEmp.Userjson chargeUseritem;
    public static DepAndEmp.Userjson createUseritem;


    @Override
    public void initViews(ViewGroup rootView) {


        leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        radio1 = (RadioButton) rootView.findViewById(R.id.radio1);
        radio2 = (RadioButton) rootView.findViewById(R.id.radio2);
        radio3 = (RadioButton) rootView.findViewById(R.id.radio3);
        radio4 = (RadioButton) rootView.findViewById(R.id.radio4);
        radio5 = (RadioButton) rootView.findViewById(R.id.radio5);
        create_person_data = (TextView) rootView.findViewById(R.id.create_person_data);
        chargmen_data = (TextView) rootView.findViewById(R.id.chargmen_data);
        resetbt = (TextView) rootView.findViewById(R.id.reset_click);
        okbt = (TextView) rootView.findViewById(R.id.ok_click);
        int sornum = getArguments().getInt(Constant.SORTNUM);
        int filterNum = getArguments().getInt(Constant.FILTER_NUM);
        setFilterCheck(filterNum);
        setSortCheck(sornum);

        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.reset_click:
                        FragmentManager.popFragment(getActivity());
                        break;
                    case R.id.ok_click:

                        break;
                    case R.id.leftimg:
                        FragmentManager.popFragment(getActivity());
                        break;
                }
            }
        };
        rootView.findViewById(R.id.reset_click).setOnClickListener(clicListener);
        rootView.findViewById(R.id.ok_click).setOnClickListener(clicListener);
        rootView.findViewById(R.id.create_person_data).setOnClickListener(clicListener);
        rootView.findViewById(R.id.chargmen_data).setOnClickListener(clicListener);
    }

    public int getFilterNum() {

        if (radio4.isChecked()) {
            return 0;
        } else if (radio5.isChecked()) {
            return 1;
        }
        return -1;
    }


    public int getChceckNum() {
        if (radio1.isChecked()) {
            return 0;
        } else if (radio2.isChecked()) {
            return 1;
        } else if (radio3.isChecked()) {
            return 2;
        }

        return -1;
    }


    public void setFilterCheck(int num) {
        switch (num) {
            case 0:
                radio4.setChecked(true);
                radio5.setChecked(false);
                break;
            case 1:
                radio4.setChecked(false);
                radio5.setChecked(true);
                break;

        }

    }

    public void setSortCheck(int num) {
        switch (num) {
            case 0:
                radio1.setChecked(true);
                radio2.setChecked(false);
                radio3.setChecked(false);
                break;
            case 1:
                radio1.setChecked(false);
                radio2.setChecked(true);
                radio3.setChecked(false);
                break;
            case 2:
                radio1.setChecked(false);
                radio2.setChecked(false);
                radio3.setChecked(true);
                break;
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.view_renwufilter;
    }

    @Override
    public void setViews(View rootView) {
        if (null != createUseritem) {
            create_person_data.setText(createUseritem.name);
        }
        if (null != chargeUseritem) {
            chargmen_data.setText(chargeUseritem.name);
        }


    }
}
