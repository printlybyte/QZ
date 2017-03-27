package com.qz.app.adapter;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.base.BaseAdapter;
import com.qz.app.entity.Selection_item;

public class SelectionAdapter extends BaseAdapter {


    public ItemClickListener itemClickListener;

    public SelectionAdapter(Context context, List<? extends Object> data) {
        super(context, data);

    }

    public int getLayoutId() {
        return R.layout.item_selection;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.selection,};
    }

    public void initViews(ViewHolder vh, final int position) {
        Selection_item item = (Selection_item) entitys.get(position);
        ((TextView) vh.getView(R.id.selection)).setText(item.selectionName);

        ((TextView) vh.getView(R.id.selection)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restEntity(position);
                notifyDataSetChanged();
                if(null!=itemClickListener) {
                    itemClickListener.onclick(entitys.get(position));
                }
            }
        });
        if(item.isSelected) {
          ((TextView) vh.getView(R.id.selection)).setTextColor(context.getResources().getColor(R.color.blue));
            ((TextView) vh.getView(R.id.selection)).setBackgroundResource(R.drawable.round_choice_selected);
        }else {
            ((TextView) vh.getView(R.id.selection)).setTextColor(context.getResources().getColor(R.color.disable));
            ((TextView) vh.getView(R.id.selection)).setBackgroundResource(R.drawable.round_choice_normal);
        }


    }

    public void restEntity(int postion) {

        for (int i = 0; i < entitys.size(); i++) {
            if (i != postion) {
                ((Selection_item) entitys.get(i)).isSelected = false;
            } else {
                ((Selection_item) entitys.get(i)).isSelected = true;
            }
        }
    }

    public void setOnitemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
