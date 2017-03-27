package com.qz.app.adapter;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.entity.NetTask;
import com.qz.app.utils.GlideUtils;
import com.qz.app.utils.QZutils;

public class RenwuAdapter extends com.qz.app.base.BaseAdapter {

    public RenwuAdapter(Context context, List<? extends Object> data) {
        super(context, data);
    }

    public int getLayoutId() {
        return R.layout.item_tasklist;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.userhead, R.id.name_data, R.id.time_data, R.id.endtime_data, R.id.childtask_data, R.id.taskstate,R.id.title_data,R.id.line};
    }

    public void initViews(ViewHolder vh, int position) {
        NetTask.Rows item = (NetTask.Rows) entitys.get(position);
        ((TextView) vh.getView(R.id.name_data)).setText(item.username);
        ((TextView) vh.getView(R.id.time_data)).setText(QZutils.cutTimer(item.updatedate));
        ((TextView) vh.getView(R.id.title_data)).setText(item.title);
        ((TextView) vh.getView(R.id.endtime_data)).setText(QZutils.cutTimerWtihOutYear(item.enddate));
        ((TextView) vh.getView(R.id.childtask_data)).setText(item.task_num + "个子任务");
        ((TextView) vh.getView(R.id.taskstate)).setText(item.status_name);
        setTextColor(((TextView) vh.getView(R.id.taskstate)),Integer.parseInt(item.status));
        GlideUtils.setRoundImage(context,item.img,R.drawable.default_head,R.drawable.default_head, (ImageView) vh.getView(R.id.userhead));
        if(position == 0){
            ((ImageView) vh.getView(R.id.line)).setVisibility(View.GONE);
        }else {
            ((ImageView) vh.getView(R.id.line)).setVisibility(View.VISIBLE);
        }
    }
    private void setTextColor(TextView view,int status) {
        switch (status) {
            case 0:
                view.setTextColor(context.getResources().getColor(R.color.disable));
                break;
            case 1:
                view.setTextColor(context.getResources().getColor(R.color.finish));
                break;
            case 2:
                view.setTextColor(context.getResources().getColor(R.color.wait));
                break;

        }
    }




}
