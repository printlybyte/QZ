package com.qz.app.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.CGmxAdapter;
import com.qz.app.adapter.HeTongAdapter;
import com.qz.app.base.BaseAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.CaiGouMx;
import com.qz.app.entity.Hetong;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/26.
 */
public class HeTongFragment extends BaseShenpiListFragment {

    private String reasonstr;
    private TextView reason;
    private TextView cgtype, paytype_data;
    private EditText htnum_data;
    private EditText htname_data;
    private RelativeLayout return_click;
    private TextView returntime_data;
    private EditText jfdwname_data;
    private EditText yfdwname_data;
    private ArrayList<Hetong> htList = new ArrayList<>();//
    private HeTongAdapter adapter;
    public static Hetong hetongContent;//

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        hetongContent = null;
        setTitle("合同");
        rootView.findViewById(R.id.leftimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });

        listView.addHeaderView(getHeadView());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(id!=-1) {
                    Bundle bundle = new Bundle();
                  Hetong hetong =  htList.get((int) id);
                    hetong.pos = (int)id;
                    bundle.putSerializable(Constant.HT_CONTENT,hetong);
                    bundle.putInt(Constant.HETONG_FROM, Constant.HT_SHOW);
                    FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), HeTongContentFragment.class.getName(), bundle));
                }

            }
        });
    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);
        if (null != hetongContent) {
            if(hetongContent.pos==-1){
                hetongContent.pos =htList.size();
                htList.add(hetongContent);
            }else{
                htList.set(hetongContent.pos,hetongContent);
            }
            hetongContent = null;//所有类似都会导致内存泄漏，以后再解决
            notifyListData();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chuchai;
    }

    @Override
    public void onClick(View v) {
        if (v == finish) {

        }
    }

    @Override
    protected BaseAdapter getAdapter() {
        if (null == adapter) {
            adapter = new HeTongAdapter(getContext(), htList);
        }
        return adapter;
    }

    @Override
    protected View getFootView() {
        final View inflater = super.inflater.inflate(R.layout.view_hetongfoot, null);
        View addMore = inflater.findViewById(R.id.addmorelayout);
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.HETONG_FROM, Constant.HT_NEW);
                FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), HeTongContentFragment.class.getName(), bundle));
            }
        });
        return inflater;
    }

    private View getHeadView() {
        final View headView = inflater.inflate(R.layout.head_hetong, null);
        htnum_data = (EditText) headView.findViewById(R.id.htnum_data);
        htname_data = (EditText) headView.findViewById(R.id.htname_data);
        returntime_data = (TextView) headView.findViewById(R.id.returntime_data);
        jfdwname_data = (EditText) headView.findViewById(R.id.jfdwname_data);
        yfdwname_data = (EditText) headView.findViewById(R.id.yfdwname_data);
        headView.findViewById(R.id.return_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QZutils.showTimer(returntime_data,getFragmentManager());
            }
        });
        return headView;
    }


//    public void showTimer(final TextView textView) {
//        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
//        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day, String hour) {
//                if (!TextUtils.isEmpty(day) && day.length() == 1) {
//                    day = "0" + day;
//                }
//                textView.setText(year + "-" + month + "-" + day);
//            }
//
//            @Override
//            public void onCancelClick() {
//
//            }
//        });
//        String incomme_str = textView.getText().toString();
//        if (!TextUtils.isEmpty(incomme_str)) {
//            String[] strs = incomme_str.split("-");
//            dateDialog2.setDate(strs);
//        } else {
//            dateDialog2.setDate(null);
//        }
//        dateDialog2.show(getFragmentManager(), "DateScrollerDialog");
//    }


}
