package com.qz.app.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiFragment;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ZhuanzhengFragment extends BaseShenpiFragment {
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private RelativeLayout money_click;
    private EditText name_data;
    private RelativeLayout jiekuan_click;
    private EditText jobName_data;
    private RelativeLayout ruzhitime_click;
    private TextView zftime_data;
    private RelativeLayout hktime_click,zhuanzhengtime_click;
    private TextView hktime_data;
    private EditText reason;
    private android.support.v7.widget.RecyclerView memberlist;
    private android.support.v7.widget.RecyclerView copylist;
    ;

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        finish = (TextView) rootView.findViewById(R.id.finish);
        money_click = (RelativeLayout) rootView.findViewById(R.id.money_click);
        name_data = (EditText) rootView.findViewById(R.id.name_data);
        jiekuan_click = (RelativeLayout) rootView.findViewById(R.id.jiekuan_click);
        jobName_data = (EditText) rootView.findViewById(R.id.jobName_data);
        ruzhitime_click = (RelativeLayout) rootView.findViewById(R.id.zftime_click);
        zftime_data = (TextView) rootView.findViewById(R.id.zftime_data);
        zhuanzhengtime_click = (RelativeLayout) rootView.findViewById(R.id.hktime_click);
        hktime_data = (TextView) rootView.findViewById(R.id.hktime_data);
        reason = (EditText) rootView.findViewById(R.id.reason);

        leftimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });

        ruzhitime_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QZutils.showTimer(zftime_data,getFragmentManager());
            }
        });
        zhuanzhengtime_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QZutils.showTimer(hktime_data,getFragmentManager());
            }
        });

    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhuanzheng;
    }

    public void initViewWithEntity() {
//        name_data.setText(entity.name_data);
//        jobName_data.setText(entity.jobName_data);
//        zftime_data.setText(entity.zftime);
//        hktime_data.setText(entity.hktime);
//
//        name_data = name_data.getText().toString();
//        jobName_data = jobName_data.getText().toString();
//        zftime = zftime_data.getText().toString();
//        hktime = hktime_data.getText().toString();
//
//        money_click.setOnClickListener(clicListener);
//        jiekuan_click.setOnClickListener(clicListener);
//        zftime_click.setOnClickListener(clicListener);
//        hktime_click.setOnClickListener(clicListener);


    }

//    public void showTimer(final TextView textView) {
//        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
//        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day,String hour) {
//                if (!TextUtils.isEmpty(day) && day.length() == 1) {
//                    day = "0" + day;
//                }
//                textView.setText(year + "-" + month + "-" + day);
//            }
//            @Override
//            public void onCancelClick() {
//
//            }
//        });
//        String incomme_str = textView.getText().toString();
//        if (!TextUtils.isEmpty(incomme_str)) {
//            String[] strs = incomme_str.split("-");
//            dateDialog2.setDate(strs);
//        } else {
//            dateDialog2.setDate(null);
//        }
//        dateDialog2.show(getFragmentManager(), "DateScrollerDialog");
//    }

}
