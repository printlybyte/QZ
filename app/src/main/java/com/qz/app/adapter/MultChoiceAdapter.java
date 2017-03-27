package com.qz.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseAdapter;
import com.qz.app.entity.Selection_item;

import java.util.List;

public class MultChoiceAdapter extends BaseAdapter{

	public MultChoiceAdapter(Context context, List<? extends Object> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	public  int getLayoutId(){
		return  R.layout.item_singlechoice;
	}
    public  int[]getConvertItemIds(){
		return new int[]{R.id.name,R.id.selectionstatus,R.id.rootlayout,R.id.line};
    }
    public  void initViews(ViewHolder vh,int position){
		final Selection_item item =(Selection_item)entitys.get(position);
//		if(position==0) {
//			vh.getView(R.id.line).setVisibility(View.GONE);
//		 }else {
//			vh.getView(R.id.line).setVisibility(View.VISIBLE);
//		}

		((TextView)vh.getView(R.id.name)).setText(item.selectionName);
		if(item.isSelected) {
			((ImageView)vh.getView(R.id.selectionstatus)).setImageResource(R.drawable.selected_icon);
		}else {
			((ImageView)vh.getView(R.id.selectionstatus)).setImageResource(R.drawable.select_normal);
		}
		((ImageView)vh.getView(R.id.selectionstatus)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				item.isSelected = !item.isSelected;
			}
		});
    }


//	private void restData(int postion){
//		for(int i=0;i<entitys.size();i++) {
//			Selection_item item = (Selection_item) entitys.get(i);
//			if(i!=postion){
//				item.isSelected = !item.isSelected;
//			}else {
//				item.isSelected = true;
//			}
//
//		}
//	}

	
}
