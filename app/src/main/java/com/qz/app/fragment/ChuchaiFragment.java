package com.qz.app.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qz.app.R;
import com.qz.app.adapter.ChucahiAdapter;
import com.qz.app.base.BaseAdapter;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ChuchaiFragment extends BaseShenpiListFragment {

    private ListView listView;
    private ArrayList<ShenpiDetail.Content_json> listdata = new ArrayList<>();

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        setTitle("出差");
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
        return R.layout.fragment_chuchai;
    }

    public void initViewWithEntity() {

    }

    @Override
    public void onClick(View v) {
        if (v == finish) {
            for (int i = 0; i < listdata.size(); i++) {
                ShenpiDetail.Content_json item = listdata.get(i);


            }


        }
    }

    @Override
    protected BaseAdapter getAdapter() {
        return new ChucahiAdapter(getContext(), listdata, getFragmentManager());
    }

    @Override
    protected View getFootView() {
        View inflater = super.inflater.inflate(R.layout.view_chuchaifoot, null);
        View addMore = inflater.findViewById(R.id.addmorelayout);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShenpiDetail.Content_json item = new ShenpiDetail.Content_json();
                listdata.add(item);
                notifyListData();
            }
        });
        return inflater;
    }


}
