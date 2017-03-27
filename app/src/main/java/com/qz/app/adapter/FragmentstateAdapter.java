package com.qz.app.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.qz.app.base.BaseFragment;

import java.util.List;

/**
 * Created by win7 on 2016/11/4.
 */

public class FragmentstateAdapter extends FragmentStatePagerAdapter {
    private List list = null;
    private List<BaseFragment> fragments;
    private String titles[];

    public <E> FragmentstateAdapter(FragmentManager fm, String[] titles, List<E> list, List<BaseFragment> fragments) {
        super(fm);
        this.titles = titles;
        this.list = list;
        this.fragments = fragments;
    }

//    public <E> FragmentstateAdapter(FragmentManager fm, List<E> list, List<Fragment> fragments) {
//        this(fm, null, list, fragments);
//    }
//
//    public FragmentstateAdapter(FragmentManager fm, List<Fragment> fragments) {
//        this(fm, null, fragments);
//    }
    @Override
    public BaseFragment getItem(int position) {

        return (BaseFragment) fragments.get(position);


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
