package com.qz.app.adapter;

import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.base.BaseAdapter;
import com.qz.app.entity.NetTask;
import com.qz.app.entity.ShenPiEntity;
import com.qz.app.utils.QZutils;


public class ShenpiAdapter extends BaseAdapter {

    public ShenpiAdapter(Context context, List<? extends Object> data) {
        super(context, data);
        // TODO Auto-generated constructor stub
    }

    public int getLayoutId() {
        return R.layout.item_shenpilist;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.title_data, R.id.time_data, R.id.conent_data, R.id.status_data, R.id.persion_data,R.id.line};
    }

    public void initViews(ViewHolder vh, int position) {
        ShenPiEntity.Rows item = (ShenPiEntity.Rows) entitys.get(position);
        ((TextView) vh.getView(R.id.title_data)).setText(item.title);
        ((TextView) vh.getView(R.id.time_data)).setText(QZutils.cutTimer(item.updated_at));
        if(position==0) {
             vh.getView(R.id.line).setVisibility(View.GONE);
        }else{
            vh.getView(R.id.line).setVisibility(View.VISIBLE);
        }


        if(null!=item.content_json) {
            ((TextView) vh.getView(R.id.conent_data)).setText(item.content_json.explain);
        }else {
            ((TextView) vh.getView(R.id.conent_data)).setText("");
        }
        String  statusstr ="";
        int color = context.getResources().getColor(R.color.disable);
        if("0".equals(item.current_status)|| TextUtils.isEmpty(item.current_status)){
            if("0".equals(item.current_check)) {
                statusstr = "审批中";
            }
            color = context.getResources().getColor(R.color.wait);
        } else if("1".equals(item.current_status)){
            if("0".equals(item.current_check)) {
                statusstr = "审批中";
                color = context.getResources().getColor(R.color.wait);
            }else if("1".equals(item.current_check)) {
                statusstr = "审批通过";
                color = context.getResources().getColor(R.color.finish);
            }else if("2".equals(item.current_check)) {
            statusstr = "审批拒绝";
                color = context.getResources().getColor(R.color.del_refuse);
        }
        }
        ((TextView) vh.getView(R.id.status_data)).setText(statusstr);
        ((TextView) vh.getView(R.id.status_data)).setTextColor(color);

        ((TextView) vh.getView(R.id.persion_data)).setText("发起人："+item.uname);
    }

}
