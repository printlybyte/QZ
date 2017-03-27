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
import com.qz.app.entity.Selection_item;

public class ShenpitypeiconAdapter extends com.qz.app.base.BaseAdapter{

	public ShenpitypeiconAdapter(Context context, List<? extends Object> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	public  int getLayoutId(){
		return  R.layout.item_shenpitype;
	}
    public  int[]getConvertItemIds(){
		return new int[]{R.id.typeicon,R.id.typename};
    }
    public  void initViews(ViewHolder vh,int position){
		Selection_item item =(Selection_item)entitys.get(position);
		((TextView)vh.getView(R.id.typename)).setText(item.selectionName);
		((ImageView)vh.getView(R.id.typeicon)).setImageResource(item.imgId);
    }

	
}
