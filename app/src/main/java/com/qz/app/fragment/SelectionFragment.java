package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
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
public class SelectionFragment extends BaseFragment {
    private ImageView leftimg;
    private TextView title;
    private ListView list;
    private int type;

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
        type = getArguments().getInt(Constant.PUBLICK_SHENPITYPE);
        switch (type) {
            case Constant.PUBLICK_SHENPITYPE_QINGJIA:
                initTitledView("请假");
                break;
            case Constant.CGTYPE_SELECTION:
                initTitledView("采购类型");
                break;
            case Constant.PAY_TYPE:
                initTitledView("支付方式");
                break;
        }


        String currentSelection = getArguments().getString(Constant.PUBLICK_SHENPITYPE_CURRENT_NAME);
        switch (type) {
            case Constant.PUBLICK_SHENPITYPE_QINGJIA:
                setListData(currentSelection, R.array.qingjiatype);

                break;

            case Constant.CGTYPE_SELECTION:
                setListData(currentSelection, R.array.cgtype);

                break;
            case Constant.PAY_TYPE:
                final String paytype[] = new String[]{"汇款", "现金"};
                setListData(currentSelection, paytype);
                break;
            case Constant.JIEKUANTYPE_SELECTION:
            case Constant.FUKUANTYPE_SELECTION:
                setListData(currentSelection, R.array.jiekuantype);

                break;

        }

    }

    private void setListData(String currentSelection, int id) {
        final String caigoutype[] = getResources().getStringArray(id);
        setListData(currentSelection, caigoutype);

    }

    private void setListData(String currentSelection, final String[] types) {
        List<Selection_item> cgitems = new ArrayList<>();
        for (int a = 0; a < types.length; a++) {
            Selection_item selection_item = new Selection_item();
            selection_item.selectionName = types[a];
            if (types[a].equals(currentSelection)) {
                selection_item.isSelected = true;
            }
            cgitems.add(selection_item);
        }
        SingleChoiceAdapter cgadapter = new SingleChoiceAdapter(getContext(), cgitems);
        list.setAdapter(cgadapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (type) {

                    case Constant.CGTYPE_SELECTION:
                        CaiGouFragment.cgType = types[position];

                        break;

                    case Constant.PUBLICK_SHENPITYPE_QINGJIA:
                                QingjiaFragment.staticqjkind = types[position];

                        break;

                    case Constant.JIEKUANTYPE_SELECTION:
                    JiekuanFragment.jiekuanType = types[position];

                        break;
                    case Constant.PAY_TYPE:
                        CaiGouFragment.payType = types[position];

                        break;
                    case Constant.FUKUANTYPE_SELECTION:
                        FukuanFragment.payType = types[position];

                        break;

                }
                FragmentManager.popFragment(getActivity());
            }
        });

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_selection;
    }

    @Override
    public void setViews(View rootView) {


    }

    public void initViewWithEntity() {


    }
}
