package com.qz.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.qz.app.R;
import com.qz.app.adapter.FragmentAdapter;
import com.qz.app.adapter.FragmentstateAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;
import com.qz.app.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by du on 2017/2/22.
 */

public class RelaselistFragment extends BaseFragment {

    public PagerSlidingTabStrip tabLayout;
    public ViewPager viewPager;
    public View release_click;
    @Override
    public void initViews(ViewGroup rootView) {
        initTitledView("任务");
        tabLayout = (PagerSlidingTabStrip) rootView.findViewById(R.id.tablayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        release_click = rootView.findViewById(R.id.release_click);
        List<BaseFragment> fragments = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putInt(Constant.RENWU_TYPE,Constant.RENWU_TYPE_FABU);
        RenwuItemFragment fabu = (RenwuItemFragment) BaseFragment.getInstance(getActivity(),RenwuItemFragment.class.getName());
        fabu.setArguments(bundle);

        Bundle zhixingbundle = new Bundle();
        zhixingbundle.putInt(Constant.RENWU_TYPE,Constant.RENWU_TYPE_ZHIXING);
        RenwuItemFragment zhixng = (RenwuItemFragment) BaseFragment.getInstance(getActivity(),RenwuItemFragment.class.getName());
        zhixng.setArguments(zhixingbundle);
        fragments.add(fabu);
        fragments.add(zhixng);

        String titles[] =new String[]{"发布的","执行的"};
        FragmentstateAdapter adapter = new FragmentstateAdapter(getFragmentManager(),titles,null,fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        release_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Bundle bundle = new Bundle();
                 bundle.putInt(Constant.EDIT_TYPE, Constant.TASK_CREAT);
                FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EdittaskFragment.class.getName(), bundle));
            }
        });
        setTabsValue(tabLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_renwu;
    }

    @Override
    public void setViews(View rootView) {


    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    protected void setTabsValue(PagerSlidingTabStrip tabs) {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的'

        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight(1);
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight(CommonUtils.getDpDementions(2));
        // 设置Tab标题文字的大小
        // tabs.setTextColor(getResources().getColor(R.color.actionbar_background));
        tabs.setTextColor(getResources().getColor(R.color.commtext));
        tabs.setTextSize((int) getResources().getDimension(R.dimen.comm));
        // 设置Tab Indicator的颜色
        // tabs.setIndicatorColor(Color.parseColor("#45c01a"));
        tabs.setIndicatorColor(getResources().getColor(
                R.color.blue));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        // tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        tabs.setSelectedTextColor(getResources().getColor(
                R.color.blue));
        tabs.setTabPaddingLeftRight(CommonUtils.getDpDementions(30));
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }

    public ViewGroup getRootView(){
        return rootView;
    }
//    @Override
//    public void onDestroyView() {
//
//        if (rootView != null) {
//            L.v("ReleaseListFragment", "onDestroyViewonDestroyViewonDestroyViewonDestroyView");
//            ViewGroup parentViewGroup = (ViewGroup) rootView.getParent();
//            if (parentViewGroup != null) {
//                parentViewGroup.removeAllViews();
//            }
//            rootView = null;
//        }
//        super.onDestroyView();
//    }
}
