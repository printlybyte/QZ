package com.qz.app.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.qz.app.R;
import com.qz.app.adapter.WorkAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.entity.WorkItem;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class AppFragment extends BaseFragment {
    private RecyclerView applists;


    @Override
    public void initViews(ViewGroup rootView) {
        applists = (RecyclerView) rootView.findViewById(R.id.applists);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
//        applists.setLayoutManager(gridLayoutManager);

        final List<WorkItem> items = new ArrayList<>();
        String[] workitems = getResources().getStringArray(R.array.workitems);
        String[] fragmentName = new String[]{HrFragment.class.getName(),
                WorkFragment.class.getName(),
                XiaoshouFragment.class.getName(),
                HrFragment.class.getName(),
                HrFragment.class.getName(),
                HrFragment.class.getName(),
                HrFragment.class.getName(),
                HrFragment.class.getName(),
                HrFragment.class.getName(),
                HrFragment.class.getName(),
                "",
                ""
        };
        int[] imgs = new int[]{R.drawable.hr_icon,
                R.drawable.appwork_icon,
                R.drawable.xiaoshou_icon,
                R.drawable.contract_icon,
                R.drawable.goods_icon,
                R.drawable.procurement_icon,
                R.drawable.income_icon,
                R.drawable.borrow_icon,
                R.drawable.libs_iocn,
                R.drawable.logistics_icon,
                R.drawable.transparent,
                R.drawable.transparent


        };
        for (int a = 0; a < workitems.length; a++) {
            WorkItem item = new WorkItem();
            item.img = imgs[a];
            item.text = workitems[a];
            item.fragmentName = fragmentName[a];
            items.add(item);

        }


        WorkAdapter adapter = new WorkAdapter(getActivity(), items);
//        applists.setAdapter(adapter);
//        GrideViewAdapter gridView = new GrideViewAdapter(getActivity(),items);
//        applists.setAdapter(gridView);
//        applists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context,items.get(position).fragmentName));
//            }
//        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        applists.setAdapter(adapter);
        applists.setLayoutManager(gridLayoutManager);
        adapter.setOnListItemClickListener(new WorkAdapter.RecyclItemClickListener() {
            @Override
            public void onItemClick(WorkItem item) {
                if(TextUtils.isEmpty(item.fragmentName))
                    return;
                FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context,item.fragmentName));
            }
        });
        initTitledView("应用");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_app;
    }

    @Override
    public void setViews(View rootView) {

    }

}
