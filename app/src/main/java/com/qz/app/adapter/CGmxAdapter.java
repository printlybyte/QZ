package com.qz.app.adapter;

import java.util.List;

import com.qz.app.base.BaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
//import com.qz.app.entity.;

public class CGmxAdapter extends BaseAdapter {

    public CGmxAdapter(Context context, List<? extends Object> data) {
        super(context, data);
        // TODO Auto-generated constructor stub
    }

    public int getLayoutId() {
        return R.layout.item_caigoumx;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.mingxi_click, R.id.mingxiName,};
    }

    public void initViews(ViewHolder vh, int position) {
//		 item =()entitys.get(position);
        ((TextView)vh.getView(R.id.mingxiName)).setText("采购明细("+(position+1)+")");

    }


}
