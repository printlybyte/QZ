package com.qz.app.adapter;

import java.util.List;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.entity.Commitlist;
import com.qz.app.entity.TaskDetailEntity;


public class CommentAdapter extends com.qz.app.base.BaseAdapter {

    public CommentAdapter(Context context, List<? extends Object> data) {
        super(context, data);
        // TODO Auto-generated constructor stub
    }

    public int getLayoutId() {
        return R.layout.item_comment;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.name_data, R.id.time_data, R.id.line, R.id.content_data,};
    }

    public void initViews(ViewHolder vh, int position) {

        Commitlist item = (Commitlist) entitys.get(position);
        ((TextView) vh.getView(R.id.name_data)).setText(item.name);
        if (TextUtils.isEmpty(item.date)) {
            ((TextView) vh.getView(R.id.time_data)).setText(item.adddt);
        } else {
            ((TextView) vh.getView(R.id.time_data)).setText(item.date);
        }
        ((TextView) vh.getView(R.id.content_data)).setText(item.content);
    }


}
