package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.qz.app.R;
import com.qz.app.adapter.WplyAdapter;
import com.qz.app.adapter.ZhaoPinAdapter;
import com.qz.app.base.BaseAdapter;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.entity.WPLY;
import com.qz.app.entity.ZhaoPin;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ZhaoPinFragment extends BaseShenpiListFragment {

    private ArrayList<ZhaoPin> listdata = new ArrayList<>();

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        setTitle("招聘");
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
        return R.layout.fragment_zhaopin;
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
        return new ZhaoPinAdapter(getContext(), listdata);
    }

    @Override
    protected View getFootView() {
        View inflater = super.inflater.inflate(R.layout.view_zhaopinfoot, null);
        View addMore = inflater.findViewById(R.id.addmorelayout);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZhaoPin item = new ZhaoPin();
                listdata.add(item);
                notifyListData();
            }
        });
        return inflater;
    }


}
