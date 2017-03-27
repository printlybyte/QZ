package com.qz.app.adapter;

import android.content.Context;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseAdapter;

import java.util.List;
//import com.qz.app.entity.;

public class HeTongAdapter extends BaseAdapter {

    public HeTongAdapter(Context context, List<? extends Object> data) {
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
        ((TextView)vh.getView(R.id.mingxiName)).setText("合同内容("+(position+1)+")");

    }


}
