package com.qz.app.adapter;

import java.util.List;

import com.qz.app.base.BaseAdapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.entity.Baoxiao;
//import com.qz.app.entity.;

public class BaoxiaoAdapter extends BaseAdapter {

    public BaoxiaoAdapter(Context context, List<? extends Object> data) {
        super(context, data);
        // TODO Auto-generated constructor stub
    }

    public int getLayoutId() {
        return R.layout.item_baoxiao;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.typename, R.id.delbut, R.id.money_data, R.id.stime_click, R.id.stime_data, R.id.mingxcontent_data};
    }

    public void initViews(ViewHolder vh, final int position) {
        final Baoxiao item = (Baoxiao) entitys.get(position);
        ((TextView) vh.getView(R.id.typename)).setText("报销明细(" + (position + 1) + ")");

        final TextView jiner = ((TextView) vh.getView(R.id.money_data));
        final TextView jktype = ((TextView) vh.getView(R.id.stime_data));
        final TextView jkmingxi = ((TextView) vh.getView(R.id.mingxcontent_data));

        vh.getView(R.id.delbut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entitys.remove(position);
                notifyDataSetChanged();
            }
        });
        jiner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                item.money = jiner.getText().toString();
            }
        });
        jktype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.type = jktype.getText().toString();
            }
        });

        jkmingxi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.reason = jkmingxi.getText().toString();
            }
        });
    }


}
