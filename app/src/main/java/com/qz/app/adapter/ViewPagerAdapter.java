package com.qz.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import com.qz.app.utils.L;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

	private ArrayList<View> datas;
    private String []arrarys = null;
	public ViewPagerAdapter(ArrayList<View> views, String[] arrarys) {
		datas = views;
		this.arrarys = arrarys;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == datas ? 0 : datas.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return null==arrarys?"": arrarys[position];
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		L.v("LineViewPager", "instantiateItem", position);
		// TODO Auto-generated method stub
		View  v = datas.get(position);
		container.addView(v);
		 v.invalidate();
		return v;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(datas.get(position));
	}
	

}
