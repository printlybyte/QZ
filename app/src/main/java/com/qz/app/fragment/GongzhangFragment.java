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
public class GongzhangFragment extends BaseShenpiFragment {
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private RelativeLayout type_click;
    private TextView type_data;
    private RelativeLayout use_click;
    private TextView usetime_data;
    private RelativeLayout return_click;
    private TextView returntime_data;
    private RelativeLayout hktime_click;
    private EditText hktime_data;
    private EditText num_data;
    private EditText reason;
    private android.support.v7.widget.RecyclerView memberlist;
    private android.support.v7.widget.RecyclerView copylist;
    ;

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        finish = (TextView) rootView.findViewById(R.id.finish);
        type_click = (RelativeLayout) rootView.findViewById(R.id.type_click);
        type_data = (TextView) rootView.findViewById(R.id.type_data);
        use_click = (RelativeLayout) rootView.findViewById(R.id.use_click);
        usetime_data = (TextView) rootView.findViewById(R.id.usetime_data);
        return_click = (RelativeLayout) rootView.findViewById(R.id.return_click);
        returntime_data = (TextView) rootView.findViewById(R.id.returntime_data);
        hktime_click = (RelativeLayout) rootView.findViewById(R.id.hktime_click);
        hktime_data = (EditText) rootView.findViewById(R.id.hktime_data);
        num_data = (EditText) rootView.findViewById(R.id.num_data);
        reason = (EditText) rootView.findViewById(R.id.reason);
        leftimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });


        type_click.setOnClickListener(this);
        use_click.setOnClickListener(this);
        return_click.setOnClickListener(this);

    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gongzhang;
    }

    public void initViewWithEntity() {
//        type_data.setText(entity.type);
//        usetime_data.setText(entity.usetime);
//        returntime_data.setText(entity.returntime);
//        hktime_data.setText(entity.hktime_data);
//        num_data.setText(entity.num_data);
//
//        type = type_data.getText().toString();
//        usetime = usetime_data.getText().toString();
//        returntime = returntime_data.getText().toString();
//        hktime_data = hktime_data.getText().toString();
//        num_data = num_data.getText().toString();
//
//        type_click.setOnClickListener(clicListener);
//        use_click.setOnClickListener(clicListener);
//        return_click.setOnClickListener(clicListener);
//        hktime_click.setOnClickListener(clicListener);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == type_click) {

        } else if (v == return_click) {
            QZutils.showTimer(returntime_data,getFragmentManager());
        } else if (v == use_click) {

            QZutils.showTimer(usetime_data,getFragmentManager());
        }
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
