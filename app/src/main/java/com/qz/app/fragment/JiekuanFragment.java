package com.qz.app.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiFragment;
import com.qz.app.constant.Constant;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;

/**
 * Created by Administrator on 2015/6/26.
 */
public class JiekuanFragment extends BaseShenpiFragment implements View.OnClickListener {
    public static String jiekuanType;
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private RelativeLayout money_click;
    private TextView money_data;
    private RelativeLayout jiekuan_click;
    private TextView jktype_data;
    private RelativeLayout zftime_click;
    private TextView zftime_data;
    private RelativeLayout hktime_click;
    private TextView hktime_data;
    private EditText reason;

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        jiekuanType = null;
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        finish = (TextView) rootView.findViewById(R.id.finish);
        money_click = (RelativeLayout) rootView.findViewById(R.id.money_click);
        money_data = (TextView) rootView.findViewById(R.id.money_data);
        jiekuan_click = (RelativeLayout) rootView.findViewById(R.id.jiekuan_click);
        jktype_data = (TextView) rootView.findViewById(R.id.jktype_data);
        zftime_click = (RelativeLayout) rootView.findViewById(R.id.zftime_click);
        zftime_data = (TextView) rootView.findViewById(R.id.zftime_data);
        hktime_click = (RelativeLayout) rootView.findViewById(R.id.hktime_click);
        hktime_data = (TextView) rootView.findViewById(R.id.hktime_data);
        reason = (EditText) rootView.findViewById(R.id.reason);
        leftimg.setOnClickListener(this);
        jiekuan_click.setOnClickListener(this);
        zftime_click.setOnClickListener(this);
        hktime_click.setOnClickListener(this);
    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);
        if(!TextUtils.isEmpty(jiekuanType)){
            jktype_data.setText(jiekuanType);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jiekuan;
    }

    public void initViewWithEntity() {
//        money_data.setText(entity.money);
//        stime_data.setText(entity.stime);
//        etime_data.setText(entity.etime);
//        hktime_data.setText(entity.hktime);
//
//        money = money_data.getText().toString();
//        stime = stime_data.getText().toString();
//        etime = etime_data.getText().toString();
//        hktime = hktime_data.getText().toString();
//
//        stime_click.setOnClickListener(clicListener);
//        jiekuan_click.setOnClickListener(clicListener);
//        etime_click.setOnClickListener(clicListener);
//        hktime_click.setOnClickListener(clicListener);


    }

    @Override
    public void onClick(View v) {
        if (v == leftimg) {
            FragmentManager.popFragment(getActivity());
        } else if (v == jiekuan_click) {
            String type =jktype_data.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.PUBLICK_SHENPITYPE_CURRENT_NAME, type);
            bundle.putInt(Constant.PUBLICK_SHENPITYPE, Constant.JIEKUANTYPE_SELECTION);
            FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(),SelectionFragment.class.getName(),bundle));


        } else if (v == zftime_click) {
            QZutils.showTimer(zftime_data,getFragmentManager());
        } else if (v == hktime_click) {
            QZutils.showTimer(hktime_data,getFragmentManager());
        }
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
