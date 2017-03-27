package com.qz.app.adapter;

import java.util.List;

import com.qz.app.base.BaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.qz.app.R;
//import com.qz.app.entity.;

public class KaoqinAdapter extends BaseAdapter {

    public KaoqinAdapter(Context context, List<? extends Object> data) {
        super(context, data);
        // TODO Auto-generated constructor stub
    }

    public int getLayoutId() {
        return R.layout.item_kaoqinlist;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.depName, R.id.day_data, R.id.time_data, R.id.tips_data,};
    }

    public void initViews(ViewHolder vh, int position) {
//        item = () entitys.get(position);
//        ((TextView) vh.getView(R.id.day_data)).setText(item.day);
//        ((TextView) vh.getView(R.id.time_data)).setText(item.time);
//        ((TextView) vh.getView(R.id.tips_data)).setText(item.tips);


    }


}
