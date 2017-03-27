package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.MultChoiceAdapter;
import com.qz.app.adapter.SingleChoiceAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.Selection_item;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class MultSelectionFragment extends BaseFragment {
    private ImageView leftimg;
    private TextView title;
    private ListView list;
    ;

    @Override
    public void initViews(ViewGroup rootView) {
        leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        list = (ListView) rootView.findViewById(R.id.list);
        Selection_item item = new Selection_item();
        leftimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });
        final int type = getArguments().getInt(Constant.PUBLICK_SHENPITYPE);
        switch (type) {
            case Constant.PUBLICK_SHENPITYPE_QINGJIA:
                initTitledView("请假");
                break;
        }


//        String currentSelection = getArguments().getString(Constant.PUBLICK_SHENPITYPE_CURRENT_NAME);
        switch (type) {
            case Constant.PUBLICK_SHENPITYPE_QINGJIA:
                final String weeks[] = getResources().getStringArray(R.array.weeks);
                List<Selection_item> items = new ArrayList<>();
                for (int a = 0; a < weeks.length; a++) {
                    Selection_item selection_item = new Selection_item();
                    selection_item.selectionName = weeks[a];
//                    if (weeks[a].equals(currentSelection)) {
//                        selection_item.isSelected = true;
//                    }

                    items.add(selection_item);
                }
                MultChoiceAdapter adapter = new MultChoiceAdapter(getContext(), items);
                list.setAdapter(adapter);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_multselection;
    }

    @Override
    public void setViews(View rootView) {


    }

    public void initViewWithEntity() {


    }
}
