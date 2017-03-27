package com.qz.app.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseAdapter;
import com.qz.app.entity.WorkItem;

import java.util.List;

/**
 * Created by du on 2017/2/18.
 */

public class GrideViewAdapter extends BaseAdapter {
    public GrideViewAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_works;
    }
    @Override
    public int[] getConvertItemIds() {
        return new int[]{R.id.sign_icon,R.id.name,R.id.itemroot};
    }
    @Override
    public void initViews(ViewHolder vh, int position) {
        WorkItem item = (WorkItem) entitys.get(position);
        ((TextView)vh.getView(R.id.name)).setText(item.text);
        ((ImageView)vh.getView(R.id.sign_icon)).setImageResource(item.img);
    }
}
