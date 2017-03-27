package com.qz.app.control;

import android.os.Handler;
import android.text.TextUtils;
import android.widget.BaseAdapter;


import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.PreferencesUtils;
import com.qz.app.view.xListView.XListView;

import java.util.ArrayList;

public class FragmentXlistViewController implements XListView.IXListViewListener {
	/**
	 * 刷新
	 */
	public static final int REFRESH = 20;
	/**
	 * 加载更多
	 */
	public static final int LOADMORE = 21;

	private ArrayList mArrayList;

	private XListView mXListView;

	private boolean isLoading;

	private BaseAdapter mBaseAdapter;

	private BaseFragment fragment;
	private final String timesubfix = "freshtime";
	private int listSign = -1;
	private Handler mHandler;

	public FragmentXlistViewController(BaseFragment fragment,
									   com.qz.app.base.BaseAdapter mBaseAdapter, XListView mXlistView, int sign,
									   Handler mHandler) {
		this.mHandler = mHandler;
		this.listSign = sign;
		this.mXListView = mXlistView;
		this.fragment = fragment;
		this.mBaseAdapter = mBaseAdapter;
//		mXListView.setPullLoadEnable(false);
		mBaseAdapter.getList();
		if (null != mBaseAdapter) {
			mXListView.setAdapter(mBaseAdapter);
		}
		mArrayList = (ArrayList) mBaseAdapter.getList();
		mXListView.setXListViewListener(this);
	}

	@Override
	public void onLoadMore() {
		if (isLoading)
			return;
		if (null != fragment) {
			isLoading = true;
			mXListView.setPullLoadEnable(true);
			fragment.getListData(mHandler, LOADMORE, listSign);
		} else {
			stopLoad(fragment.hashCode() + timesubfix);
		}
	}

	@Override
	public void onRefresh() {
		if (isLoading)
			return;
		if (null != fragment) {
			isLoading = true;
			mXListView.setPullLoadEnable(true);
			mXListView.setPullRefreshEnable(true);
			fragment.getListData(mHandler, REFRESH, listSign);
		}

	}

	public void setLoadState(boolean b) {
		isLoading = b;
	}

	public void stopLoad(String sign) {
		mXListView.stopLoadMore();
		mXListView.stopRefresh();
		isLoading = false;
		if (!TextUtils.isEmpty(sign)) {
			String time = PreferencesUtils.getString(sign + timesubfix);
			if (TextUtils.isEmpty(time)) {
				mXListView.setRefreshTime("刚刚");
			} else {
				mXListView.setRefreshTime(time);
			}

			PreferencesUtils.putString(sign + timesubfix,
					CommonUtils.getFormatTime(Constant.FORMAT_E));
		}
	}

	public boolean getLoadState() {
		return isLoading;
	}

	/**
	 * 追加数据
	 * 
	 * @param list
	 */
	public void addArray(ArrayList list) {
		if (null != mArrayList && null != list && list.size() > 0) {
			mArrayList.addAll(list);
		}
		mBaseAdapter.notifyDataSetChanged();
	}

	public void clearList() {
		if (null != mArrayList && null != mBaseAdapter) {
			mArrayList.clear();
			mBaseAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 刷新列表
	 * 
	 * @param list
	 */
	public void refreshArrayData(ArrayList list) {
		if (null == mArrayList) {
			mArrayList = new ArrayList();
		}

		mArrayList.clear();
		mArrayList.addAll(list);
		mBaseAdapter.notifyDataSetChanged();
	}

	public XListView getXlistView() {
		return mXListView;
	}

	public void notifyDatas() {
		if (null != mBaseAdapter)
			mBaseAdapter.notifyDataSetChanged();
	}

}
