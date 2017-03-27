package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qz.app.R;
import com.qz.app.adapter.ChucahiAdapter;
import com.qz.app.adapter.WplyAdapter;
import com.qz.app.base.BaseAdapter;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.entity.WPLY;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/26.
 */
public class WPlYFragment extends BaseShenpiListFragment {

    private ArrayList<WPLY> listdata = new ArrayList<>();

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        setTitle("物品领用");
        rootView.findViewById(R.id.leftimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });
    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wply;
    }

    public void initViewWithEntity() {

    }

    @Override
    public void onClick(View v) {
        if (v == finish) {


        }
    }

    @Override
    protected BaseAdapter getAdapter() {
        return new WplyAdapter(getContext(), listdata);
    }

    @Override
    protected View getFootView() {
        View inflater = super.inflater.inflate(R.layout.view_wplyfoot, null);
        View addMore = inflater.findViewById(R.id.addmorelayout);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WPLY item = new WPLY();
                listdata.add(item);
                notifyListData();
            }
        });
        return inflater;
    }


}
