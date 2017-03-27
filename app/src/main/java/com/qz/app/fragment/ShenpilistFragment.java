package com.qz.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.qz.app.R;
import com.qz.app.adapter.FragmentstateAdapter;
import com.qz.app.adapter.ShenpitypeiconAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.Selection_item;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by du on 2017/2/22.
 */

public class ShenpilistFragment extends BaseFragment implements View.OnClickListener {

    public PagerSlidingTabStrip tabLayout;
    public ViewPager viewPager;
    private ViewGroup contenter;
    private FragmentstateAdapter adapter;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public void initViews(ViewGroup rootView) {
        initTitledView("审批");

        contenter = (ViewGroup) rootView.findViewById(R.id.contenter);
        rootView.findViewById(R.id.public_click).setOnClickListener(this);
        rootView.findViewById(R.id.set_click).setOnClickListener(this);

        Bundle bundle = new Bundle();
        ViewGroup contenter = (ViewGroup) rootView.findViewById(R.id.contenter);
        bundle.putInt(Constant.SHENPI_TYPE, Constant.SHENPI_TYPE_FABU);
        ShenpiItemFragment fabu = (ShenpiItemFragment) BaseFragment.getInstance(getActivity(), ShenpiItemFragment.class.getName());
        fabu.setArguments(bundle);
        fabu.setContent(contenter);

        Bundle zhixingbundle = new Bundle();
        zhixingbundle.putInt(Constant.SHENPI_TYPE, Constant.SHENPI_TYPE_DAIWO);
        ShenpiItemFragment zhixng = (ShenpiItemFragment) BaseFragment.getInstance(getActivity(), ShenpiItemFragment.class.getName());
        zhixng.setArguments(zhixingbundle);
        zhixng.setContent(contenter);

        Bundle shenpi = new Bundle();
        shenpi.putInt(Constant.SHENPI_TYPE, Constant.SHENPI_TYPE_SHENPI);
        ShenpiItemFragment shenpiFragment = (ShenpiItemFragment) BaseFragment.getInstance(getActivity(), ShenpiItemFragment.class.getName());
        shenpiFragment.setArguments(shenpi);
        shenpiFragment.setContent(contenter);

        Bundle chaosong = new Bundle();
        chaosong.putInt(Constant.SHENPI_TYPE, Constant.SHENPI_TYPE_CHAOSONG);
        ShenpiItemFragment chaosongFragment = (ShenpiItemFragment) BaseFragment.getInstance(getActivity(), ShenpiItemFragment.class.getName());
        chaosongFragment.setArguments(chaosong);
        chaosongFragment.setContent(contenter);

        fragments.add(fabu);
        fragments.add(zhixng);
        fragments.add(shenpiFragment);
        fragments.add(chaosongFragment);
        tabLayout = (PagerSlidingTabStrip) rootView.findViewById(R.id.tablayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        String titles[] = new String[]{"我发起的", "待我审批", "我审批的", "抄送我的"};
        adapter = new FragmentstateAdapter(getFragmentManager(), titles, null, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        setTabsValue(tabLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shenpi;
    }

    @Override
    public void setViews(View rootView) {
//        adapter.updateFragments(getFragmentManager(), (ArrayList<BaseFragment>) fragments);

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

    public ViewGroup getRootView() {
        return rootView;
    }


    public View getTypeList() {
        int ids[] = new int[]{R.drawable.qingjia_icon, R.drawable.baoxiao_icon, R.drawable.chuchai_icon, R.drawable.jiekuan_icon, R.drawable.zhaopin_icon, R.drawable.lizhi_icon, R.drawable.zhuanzheng_icon, R.drawable.gongzhang, R.drawable.hetong_icon, R.drawable.waichu_icon, R.drawable.tiaoxin_icon, R.drawable.caigou_icon, R.drawable.fukuan_icon, R.drawable.lingyong, R.drawable.putongshenpi_icon};
        String types[] = getResources().getStringArray(R.array.shenpitypes2);
        View typeview = inflater.inflate(R.layout.view_gride_selection, null);

        GridView selections = (GridView) typeview.findViewById(R.id.selections);
        List<Selection_item> items = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            Selection_item item = new Selection_item();
            item.selectionName = types[i];
            item.imgId = ids[i];
            items.add(item);
        }
        ShenpitypeiconAdapter adapter = new ShenpitypeiconAdapter(getContext(), items);
        selections.setAdapter(adapter);
        selections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), QingjiaFragment.class.getName()));
                        break;
                    case 1:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), BaoxiaoFragment.class.getName()));
                        break;
                    case 2:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), ChuchaiFragment.class.getName()));
                        break;
                    case 3:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), JiekuanFragment.class.getName()));
                        break;
                    case 4:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), ZhaoPinFragment.class.getName()));
                        break;
                    case 5:

                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), LizhiFragment.class.getName()));
                        break;
                    case 6:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), ZhuanzhengFragment.class.getName()));
                        break;
                    case 7:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), GongzhangFragment.class.getName()));
                        break;
                    case 8:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), HeTongFragment.class.getName()));
                        break;
                    case 9:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), WaichuFragment.class.getName()));
                        break;
                    case 10:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), TiaoxinFragment.class.getName()));
                        break;
                    case 11:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), CaiGouFragment.class.getName()));
                        break;
                    case 12:

                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), FukuanFragment.class.getName()));
                        break;
                    case 13:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), WPlYFragment.class.getName()));
                        break;
                    case 14:

                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), PtshenheFragment.class.getName()));
                        break;
                }


            }
        });
        return typeview;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.set_click) {

        } else if (v.getId() == R.id.public_click) {
            contenter.addView(getTypeList());
            contenter.findViewById(R.id.closeicon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contenter.removeAllViews();
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (contenter.getChildCount() > 0) {
            contenter.removeAllViews();
            return true;
        }
        return false;
    }
}
