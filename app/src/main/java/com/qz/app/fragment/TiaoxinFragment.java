package com.qz.app.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiFragment;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;

/**
 * Created by Administrator on 2015/6/26.
 */
public class TiaoxinFragment extends BaseShenpiFragment {
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private RelativeLayout stime_click;
    private EditText name_data;
    private RelativeLayout etime_click;
    private EditText gangweiName_data;
    private EditText money_data;
    private EditText newmoney_data;
    private TextView time_data;
    private EditText reason;
    private android.support.v7.widget.RecyclerView listimgs;
    private android.support.v7.widget.RecyclerView memberlist;
    private android.support.v7.widget.RecyclerView copylist;
    ;

    @Override
    public void initViews(ViewGroup rootView) {
                super.initViews(rootView);
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);

        finish = (TextView) rootView.findViewById(R.id.finish);
        stime_click = (RelativeLayout) rootView.findViewById(R.id.stime_click);
        name_data = (EditText) rootView.findViewById(R.id.name_data);
        etime_click = (RelativeLayout) rootView.findViewById(R.id.etime_click);
        gangweiName_data = (EditText) rootView.findViewById(R.id.gangweiName_data);
        money_data = (EditText) rootView.findViewById(R.id.money_data);
        newmoney_data = (EditText) rootView.findViewById(R.id.newmoney_data);
        time_data = (TextView) rootView.findViewById(R.id.time_data);
        reason = (EditText) rootView.findViewById(R.id.reason);
        leftimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });
        rootView.findViewById(R.id.tiaoxintime_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QZutils.showTimer(time_data,getFragmentManager());
            }
        });
    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tiaoxin;
    }

    public void initViewWithEntity() {
//        name_data.setText(entity.name_data);
//        gangweiName_data.setText(entity.gangweiName_data);
//        money_data.setText(entity.money_data);
//        newmoney_data.setText(entity.newmoney_data);
//        totaltime_data.setText(entity.totaltime);
//        name_data = name_data.getText().toString();
//        gangweiName_data = gangweiName_data.getText().toString();
//        money_data = money_data.getText().toString();
//        newmoney_data = newmoney_data.getText().toString();
//        totaltime = totaltime_data.getText().toString();
//        stime_click.setOnClickListener(clicListener);
//        etime_click.setOnClickListener(clicListener);
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
