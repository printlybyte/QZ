package com.qz.app.adapter;

import java.util.List;

import com.qz.app.base.BaseAdapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import com.qz.app.R;
import com.qz.app.entity.WPLY;
//import com.qz.app.entity.;

public class WplyAdapter extends BaseAdapter{

	public WplyAdapter(Context context, List<? extends Object> data) {
		super(context, data);
		// TODO Auto-generated constructor stub
	}

	public  int getLayoutId(){
		return  R.layout.item_wply;
	}
    public  int[]getConvertItemIds(){
		return new int[]{R.id.yongtu_data,R.id.name_data,R.id.num_data,R.id.typename,R.id.delbut};
    }
    public  void initViews(ViewHolder vh, final int position){
		final  WPLY item =(WPLY)entitys.get(position);
		((EditText)vh.getView(R.id.yongtu_data)).setText(item.yongtu);
		((EditText)vh.getView(R.id.name_data)).setText(item.name);
		((EditText)vh.getView(R.id.num_data)).setText(item.num);
		((TextView)vh.getView(R.id.typename)).setText("物品明细("+(position+1)+")");


		vh.getView(R.id.delbut).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				entitys.remove(position);
				notifyDataSetChanged();
			}
		});

		((EditText)vh.getView(R.id.yongtu_data)).addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
					item.yongtu = s.toString();
			}
		});

		((EditText)vh.getView(R.id.name_data)).addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				item.name = s.toString();
			}
		});
		((EditText)vh.getView(R.id.num_data)).addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				item.num = s.toString();
			}
		});


    }

	
}
