package com.qz.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.qz.app.base.BaseFragment;

import java.util.List;

/**
 * Created by win7 on 2016/11/4.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private List list = null;
    private List<BaseFragment> fragments;
    private String titles[];

    public <E> FragmentAdapter(FragmentManager fm, String[] titles, List<Object> list, List<BaseFragment> fragments) {
        super(fm);
        this.titles = titles;
        this.list = list;
        this.fragments = fragments;
    }

//    public <E> FragmentAdapter(FragmentManager fm, List<E> list, List<Fragment> fragments) {
//        this(fm, null, list, fragments);
//    }

//    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
//        this(fm, null, fragments);
//    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseFragment fm = (BaseFragment) super.instantiateItem(container,position);
        return fm;
    }

    @Override
    public Fragment getItem(int position) {


        return  fragments.get(position);
    }

    @Override
    public int getCount() {
        return null == fragments ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titles && titles.length > 0) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
    //    @Override
//    public long getItemId(int position) {
//        super.getItemId(position);
//        if (null != list) {
//            // 获取当前数据的hashCode
//            int hashCode = list.get(position).hashCode();
//            return hashCode;
//        }
//        System.out.println("null == list");
//        return      super.getItemId(position);
//    }


}
