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
public class FukuanFragment extends BaseShenpiFragment {
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private RelativeLayout type_click;
    private EditText money_data;
    private RelativeLayout paytype_click;
    private TextView paytype_data;
    private RelativeLayout paytime_click;
    private TextView paytime_data;
    private RelativeLayout hktime_click;
    private EditText payName_data;
    private EditText bankName_data;
    private EditText cardnum_data;
    private EditText reason;

    public static String payType;

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        finish = (TextView) rootView.findViewById(R.id.finish);
        type_click = (RelativeLayout) rootView.findViewById(R.id.type_click);
        money_data = (EditText) rootView.findViewById(R.id.money_data);
        paytype_click = (RelativeLayout) rootView.findViewById(R.id.paytype_click);
        paytype_data = (TextView) rootView.findViewById(R.id.paytype_data);
        paytime_click = (RelativeLayout) rootView.findViewById(R.id.paytime_click);
        paytime_data = (TextView) rootView.findViewById(R.id.paytime_data);
        hktime_click = (RelativeLayout) rootView.findViewById(R.id.hktime_click);
        payName_data = (EditText) rootView.findViewById(R.id.payName_data);
        bankName_data = (EditText) rootView.findViewById(R.id.bankName_data);
        cardnum_data = (EditText) rootView.findViewById(R.id.cardnum_data);
        reason = (EditText) rootView.findViewById(R.id.reason);
        leftimg.setOnClickListener(this);
        finish.setOnClickListener(this);
        paytype_click.setOnClickListener(this);
        paytime_click.setOnClickListener(this);
    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);
        if (!TextUtils.isEmpty(payType)) {
            paytype_data.setText(payType);
            payType = null;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fukuan;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == leftimg) {
            FragmentManager.popFragment(getActivity());
        } else if (v == paytype_click) {
            String type = paytype_data.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.PUBLICK_SHENPITYPE_CURRENT_NAME, type);
            bundle.putInt(Constant.PUBLICK_SHENPITYPE, Constant.FUKUANTYPE_SELECTION);
            FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), SelectionFragment.class.getName(), bundle));
        } else if (v == paytime_click) {
            QZutils.showTimer(paytime_data,getFragmentManager());
        }
    }

    public void initViewWithEntity() {
//        money_data.setText(entity.money_data);
//paytype_data.setText(entity.paytype);
//paytime_data.setText(entity.paytime);
//payName_data.setText(entity.payName_data);
//bankName_data.setText(entity.bankName_data);
//cardnum_data.setText(entity.cardnum_data);
//
//money_data=money_data.getText().toString();
//paytype=paytype_data.getText().toString();
//paytime=paytime_data.getText().toString();
//payName_data=payName_data.getText().toString();
//bankName_data=bankName_data.getText().toString();
//cardnum_data=cardnum_data.getText().toString();
//
//type_click.setOnClickListener(clicListener);
//hktime_click.setOnClickListener(clicListener);
    }

//    public void showTimer(final TextView textView) {
//        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
//        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day, String hour) {
//                if (!TextUtils.isEmpty(day) && day.length() == 1) {
//                    day = "0" + day;
//                }
//                textView.setText(year + "-" + month + "-" + day);
//            }
//
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
