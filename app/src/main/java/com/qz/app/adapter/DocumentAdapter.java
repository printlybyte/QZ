package com.qz.app.adapter;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.entity.NetDocument;
import com.qz.app.utils.QZutils;


public class DocumentAdapter extends com.qz.app.base.BaseAdapter {


    public DocumentAdapter(Context context, List data) {
        super(context, data);
    }

    public int getLayoutId() {
        return R.layout.item_documentlist;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.title_data, R.id.time_data, R.id.descri_data,R.id.line};
    }

    public void initViews(ViewHolder vh, int position) {
        NetDocument.Document item = (NetDocument.Document) entitys.get(position);
        ((TextView) vh.getView(R.id.title_data)).setText(item.title);
        ((TextView) vh.getView(R.id.descri_data)).setText(item.dept_name+" · "+item.read_num+"人已阅");
        ((TextView) vh.getView(R.id.time_data)).setText(QZutils.cutTimer(item.created_at));
        if(position == 0){
            vh.getView(R.id.line).setVisibility(View.INVISIBLE);
        }else {
            vh.getView(R.id.line).setVisibility(View.VISIBLE);
        }
    }

    public void cleanData() {
        if (null != entitys) {
            entitys.clear();
            notifyDataSetChanged();
        }
    }

}
