package com.qz.app.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qz.app.R;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public abstract class BaseAdapter extends android.widget.BaseAdapter {

	protected Context context;
	protected List entitys;
	protected LayoutInflater mInflater;

	public BaseAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	public BaseAdapter(Context context, List data) {
		this(context);
		setList(data);
	}

	public void setList(List data) {
		this.entitys = data;
	}

	public List getList() {
		return entitys;
	}

	@Override
	public int getCount() {
		return null == entitys ? 0 : entitys.size();
	}

	@Override
	public Object getItem(int position) {
		return null == entitys ? null : entitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (null == convertView) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(getLayoutId(), null);
			int[] ids = getConvertItemIds();
			if (null == ids) {
				return null;
			}
			for (int a = 0; a < ids.length; a++) {
				vh.addView(convertView, ids[a]);
			}
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		if(null!=vh.getView(R.id.line)) {
			if (position == 0) {

				vh.getView(R.id.line).setVisibility(View.GONE);
			} else {
				vh.getView(R.id.line).setVisibility(View.VISIBLE);
			}
		}


		initViews(vh, position);
		return convertView;
	}

	public void addItem(Object item){
		entitys.add(item);
		notifyDataSetChanged();
	}

	public abstract int getLayoutId();

	public abstract int[] getConvertItemIds();

	public abstract void initViews(ViewHolder vh, int position);

	protected class ViewHolder {
		SparseArray<View> viewArray = new SparseArray<View>();

		public void addView(View convertView, int id) {
			if (null == convertView) {
				return;
			}
			View contentView = convertView.findViewById(id);
			viewArray.put(id, contentView);
		}

		public View getView(int id) {
			return  viewArray.get(id);
		}
	}

	public void cleanData() {
		if (null != entitys) {
			entitys.clear();
			notifyDataSetChanged();
		}
	}


}
