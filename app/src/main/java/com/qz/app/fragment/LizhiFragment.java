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
public class LizhiFragment extends BaseShenpiFragment{
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private RelativeLayout money_click;
    private EditText name_data;
    private RelativeLayout jiekuan_click;
    private TextView jobName_data;
    private RelativeLayout rzhtime_click;
    private TextView rzhtime_data;
    private RelativeLayout sqlzhtime_click;
    private TextView sqlzhtime_data;
    private EditText reason;

    ;

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        finish = (TextView) rootView.findViewById(R.id.finish);
        money_click = (RelativeLayout) rootView.findViewById(R.id.money_click);
        name_data = (EditText) rootView.findViewById(R.id.name_data);
        jiekuan_click = (RelativeLayout) rootView.findViewById(R.id.jiekuan_click);
        jobName_data = (TextView) rootView.findViewById(R.id.jobName_data);
        rzhtime_click = (RelativeLayout) rootView.findViewById(R.id.rzhtime_click);
        rzhtime_data = (TextView) rootView.findViewById(R.id.rzhtime_data);
        sqlzhtime_click = (RelativeLayout) rootView.findViewById(R.id.sqlzhtime_click);
        sqlzhtime_data = (TextView) rootView.findViewById(R.id.sqlzhtime_data);
        reason = (EditText) rootView.findViewById(R.id.reason);
        leftimg.setOnClickListener(this);
        sqlzhtime_click.setOnClickListener(this);
        rzhtime_click.setOnClickListener(this);

    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v == leftimg){
            FragmentManager.popFragment(getActivity());
        }else if(v == sqlzhtime_click){
            QZutils.showTimer(sqlzhtime_data,getFragmentManager());
        }else if(v==rzhtime_click){
            QZutils.showTimer(rzhtime_data,getFragmentManager());
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lizhi;
    }

    public void initViewWithEntity() {
//        name_data.setText(entity.name_data);
//        jobName_data.setText(entity.jobName);
//        rzhtime_data.setText(entity.rzhtime);
//        sqlzhtime_data.setText(entity.sqlzhtime);
//
//        name_data = name_data.getText().toString();
//        jobName = jobName_data.getText().toString();
//        rzhtime = rzhtime_data.getText().toString();
//        sqlzhtime = sqlzhtime_data.getText().toString();
//
//        money_click.setOnClickListener(clicListener);
//        jiekuan_click.setOnClickListener(clicListener);
//        zftime_click.setOnClickListener(clicListener);
//        hktime_click.setOnClickListener(clicListener);


    }

//
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
