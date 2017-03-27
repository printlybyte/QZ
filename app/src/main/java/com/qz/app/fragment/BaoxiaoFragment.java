package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qz.app.R;
import com.qz.app.adapter.BaoxiaoAdapter;
import com.qz.app.adapter.ChucahiAdapter;
import com.qz.app.base.BaseAdapter;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.entity.Baoxiao;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/26.
 */
public class BaoxiaoFragment extends BaseShenpiListFragment {

    private ListView listView;
    private ArrayList<Baoxiao> listdata = new ArrayList<>();

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        setTitle("报销");
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
        return R.layout.fragment_baoxiao;
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
        return new BaoxiaoAdapter(getContext(), listdata);
    }

    @Override
    protected View getFootView() {
        View inflater = super.inflater.inflate(R.layout.view_baoxiaofoot, null);
        View addMore = inflater.findViewById(R.id.addmorelayout);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Baoxiao item = new Baoxiao();
                listdata.add(item);
                notifyListData();
            }
        });
        return inflater;
    }


}
