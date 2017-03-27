package com.qz.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.FragmentstateAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.DepEntity;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.view.PagerSlidingTabStrip;
import com.qz.app.view.PopWinWithList;

import java.util.ArrayList;

/**
 * "工作汇报"
 * Created by Administrator on 2015/6/26.
 */
public class HuibaoFragment extends BaseFragment implements View.OnClickListener {

    private TextView releasebt;
    private com.qz.app.view.PagerSlidingTabStrip tablayout;
    private android.support.v4.view.ViewPager viewpager;
    private PagerSlidingTabStrip tabLayout;
    private ViewPager viewPager;
    public static DepAndEmp.Userjson showemp;
    public static DepEntity.Children selectedDept;
    private int currentIndex;
    //    public static
    public ArrayList<BaseFragment> fragments = new ArrayList<>();

    @Override
    public void initViews(ViewGroup rootView) {
        leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        releasebt = (TextView) rootView.findViewById(R.id.release_click);
        tablayout = (com.qz.app.view.PagerSlidingTabStrip) rootView.findViewById(R.id.tablayout);
        viewpager = (android.support.v4.view.ViewPager) rootView.findViewById(R.id.viewpager);
        initTitledView("工作汇报");
        releasebt.setOnClickListener(this);


        Bundle bundle1 = new Bundle();
        bundle1.putInt(Constant.HUIBAO_TYPE, Constant.MY_HUIBAO);
        HuiBaoItemFragment myhuibaoFragment = (HuiBaoItemFragment) BaseFragment.getInstance(getActivity(), HuiBaoItemFragment.class.getName(), bundle1);
        myhuibaoFragment.setContent((ViewGroup) rootView.findViewById(R.id.contenter));
        fragments.add(myhuibaoFragment);

        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constant.HUIBAO_TYPE, Constant.TO_ME_HUIBAO);
        HuiBaoItemFragment tomehuibaoFragment = (HuiBaoItemFragment) BaseFragment.getInstance(getActivity(), HuiBaoItemFragment.class.getName(), bundle2);
        tomehuibaoFragment.setContent((ViewGroup) rootView.findViewById(R.id.contenter));
        fragments.add(tomehuibaoFragment);


        Bundle bundle3 = new Bundle();
        bundle3.putInt(Constant.HUIBAO_TYPE, Constant.CHAOSONG_ME_HUIBAO);
        HuiBaoItemFragment shaosongmenhuibaoFragment = (HuiBaoItemFragment) BaseFragment.getInstance(getActivity(), HuiBaoItemFragment.class.getName(), bundle3);
        shaosongmenhuibaoFragment.setContent((ViewGroup) rootView.findViewById(R.id.contenter));
        fragments.add(shaosongmenhuibaoFragment);

        tabLayout = (PagerSlidingTabStrip) rootView.findViewById(R.id.tablayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        String titles[] = new String[]{"我的汇报", "汇报我的", "抄送我的"};
        FragmentstateAdapter adapter = new FragmentstateAdapter(getFragmentManager(), titles, null, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        setTabsValue(tabLayout);
        tablayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        tabs.setIndicatorColor(getResources().getColor(R.color.blue));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        // tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        tabs.setSelectedTextColor(getResources().getColor(
                R.color.blue));
        tabs.setTabPaddingLeftRight(CommonUtils.getDpDementions(1));
        tabs.setDividerPadding(1);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
        tabs.setDividerPadding(0);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_huibao;
    }

    @Override
    public void setViews(View rootView) {
        ((HuiBaoItemFragment) fragments.get(currentIndex)).setFilterData(showemp, selectedDept);
        showemp = null;
        selectedDept = null;

    }

    public void initViewWithEntity() {


    }

    @Override
    public void onClick(View v) {
        if (v == releasebt) {
            showMore(releasebt);
        }
    }

    public void showMore(View morView) {
        ViewGroup popview = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.popwin_huibao, null);
        final PopWinWithList popWinWithList = new PopWinWithList(getActivity(), popview);
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                switch (v.getId()) {
                    case R.id.ribao_click:
                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_RIBAO);
                        break;
                    case R.id.zhoubao_click:

                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_ZHOUBAO);
                        break;
                    case R.id.yuebao_click:

                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_YUEBAO);
                        break;
                    case R.id.yeji_click:
                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_YEJI);
                        break;
                }
                popWinWithList.hide();
                FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), PublichuibaoFragment.class.getName(), bundle));
            }
        };
        popview.findViewById(R.id.ribao_click).setOnClickListener(clicListener);
        popview.findViewById(R.id.zhoubao_click).setOnClickListener(clicListener);
        popview.findViewById(R.id.yuebao_click).setOnClickListener(clicListener);
        popview.findViewById(R.id.yeji_click).setOnClickListener(clicListener);
        popWinWithList.show(morView);
    }


}
