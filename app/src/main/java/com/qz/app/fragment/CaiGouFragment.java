package com.qz.app.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.CGmxAdapter;
import com.qz.app.adapter.ChucahiAdapter;
import com.qz.app.base.BaseAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.CaiGouMx;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class CaiGouFragment extends BaseShenpiListFragment {

    public static String cgType;
    public static String payType;
    private String reasonstr;
    private TextView reason;
    private TextView cgtype, paytype_data;
    private ArrayList<CaiGouMx> cgList = new ArrayList<>();
    public static CaiGouMx caiGouMx;
    private CGmxAdapter adapter;
    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        cgType = null;
        payType = null;
        caiGouMx = null;
        setTitle("采购");
        rootView.findViewById(R.id.leftimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });

        listView.addHeaderView(getHeadView());
    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);
        if(!TextUtils.isEmpty(cgType)) {
            cgtype.setText(cgType);
            cgType = null;
        }
        if(!TextUtils.isEmpty(payType)) {
            paytype_data.setText(payType);
            payType = null;
        }
        if(null!=caiGouMx){
            cgList.add(caiGouMx);
            notifyListData();
            caiGouMx = null;
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
        if(null==adapter) {
            adapter = new CGmxAdapter(getContext(), cgList);
        }
        return adapter;
    }

    @Override
    protected View getFootView() {
        final View inflater = super.inflater.inflate(R.layout.view_caigoufoot, null);
        View addMore = inflater.findViewById(R.id.addmorelayout);
        paytype_data = (TextView) inflater.findViewById(R.id.paytype_data);
        inflater.findViewById(R.id.paytype_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.PUBLICK_SHENPITYPE, Constant.PAY_TYPE);
                bundle.putString(Constant.PUBLICK_SHENPITYPE_CURRENT_NAME, ((TextView) inflater.findViewById(R.id.paytype_data)).getText().toString());
                FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), SelectionFragment.class.getName(), bundle));
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Object item = new Object();
//                listdata.add(item);
//                notifyListData();
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.CGMXTYPE,Constant.CGMXTYPE_FROM_NEW);
                FragmentManager.addStackFragment(getActivity(),BaseFragment.getInstance(getActivity(),CgmxFragment.class.getName(),bundle));
            }
        });
        return inflater;
    }

    private View getHeadView() {
        final View headView = inflater.inflate(R.layout.head_caigou, null);
        reason = (TextView) headView.findViewById(R.id.reason_data);
        cgtype = (TextView) headView.findViewById(R.id.cgtype_data);
        headView.findViewById(R.id.committime_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               QZutils.showTimer((TextView)headView.findViewById(R.id.committime_data),getFragmentManager());
            }
        });
        headView.findViewById(R.id.cgtype_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = ((TextView) headView.findViewById(R.id.cgtype_data)).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.PUBLICK_SHENPITYPE_CURRENT_NAME, type);
                bundle.putInt(Constant.PUBLICK_SHENPITYPE, Constant.CGTYPE_SELECTION);
                FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), SelectionFragment.class.getName(), bundle));
            }
        });
        return headView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cgList.clear();
    }
//    public void showTimer(final TextView textView) {
//        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
//        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day,String hour) {
//                if (!TextUtils.isEmpty(day) && day.length() == 1) {
//                    day = "0" + day;
//                }
//                textView.setText(year + "-" + month + "-" + day);
//            }
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
