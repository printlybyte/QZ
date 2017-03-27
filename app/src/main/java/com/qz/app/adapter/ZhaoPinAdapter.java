package com.qz.app.adapter;

import java.util.List;

import com.qz.app.base.BaseAdapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.entity.ZhaoPin;
//import com.qz.app.entity.;

public class ZhaoPinAdapter extends BaseAdapter {

    public ZhaoPinAdapter(Context context, List<? extends Object> data) {
        super(context, data);
        // TODO Auto-generated constructor stub
    }

    public int getLayoutId() {
        return R.layout.item_zhaopin;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.typename, R.id.delbut, R.id.jobname_data, R.id.stime_click, R.id.xuqiu_data, R.id.num_data, R.id.tiaoxintime_click, R.id.time_data, R.id.miaoshu_data,};
    }

    public void initViews(ViewHolder vh, int position) {
      final  ZhaoPin item = (ZhaoPin) entitys.get(position);
        ((TextView) vh.getView(R.id.typename)).setText("招聘需求("+(position+1)+")");
        ((TextView) vh.getView(R.id.time_data)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    item.time = s.toString();

                }

            }
        });
        ((EditText) vh.getView(R.id.jobname_data)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    item.jobName = s.toString();

                }

            }
        });

        ((EditText) vh.getView(R.id.xuqiu_data)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    item.xuqiu = s.toString();

                }

            }
        });
        ((EditText) vh.getView(R.id.num_data)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    item.num = s.toString();

                }

            }
        });

        ((EditText) vh.getView(R.id.miaoshu_data)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    item.miaoshu = s.toString();

                }

            }
        });


//        ((TextView) vh.getView(R.id.time_data)).setText(item.time);



    }


}
