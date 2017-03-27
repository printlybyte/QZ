package com.qz.app.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class WaichuFragment extends BaseShenpiFragment {
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private TextView stime_data;
    private TextView etime_data;
    private TextView totaltime_data;
    private EditText reason;
    private View stime_click, etime_click;

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        finish = (TextView) rootView.findViewById(R.id.finish);
        stime_data = (TextView) rootView.findViewById(R.id.stime_data);
        etime_data = (TextView) rootView.findViewById(R.id.etime_data);
        totaltime_data = (TextView) rootView.findViewById(R.id.totaltime_data);
        reason = (EditText) rootView.findViewById(R.id.reason);
        stime_click = rootView.findViewById(R.id.stime_click);
        etime_click = rootView.findViewById(R.id.etime_click);
        stime_click.setOnClickListener(this);
        etime_click.setOnClickListener(this);
        leftimg.setOnClickListener(this);

        etime_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String stimestr = stime_data.getText().toString();
                String etimestr = etime_data.getText().toString();

                if (!TextUtils.isEmpty(stimestr) && !TextUtils.isEmpty(etimestr)) {
                    long hour =   QZutils.getTime(QZutils.replacePYTOlineWithTime(etimestr))-QZutils.getTime(QZutils.replacePYTOlineWithTime(stimestr));
                    totaltime_data.setText(hour/1000/60/60 + "");
                }

            }
        });

        stime_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String stimestr = stime_data.getText().toString();
                String etimestr = etime_data.getText().toString();

                if (!TextUtils.isEmpty(stimestr) && !TextUtils.isEmpty(etimestr)) {
                    long hour = QZutils.getTime(QZutils.replacePYTOlineWithTime(stimestr)) - QZutils.getTime(QZutils.replacePYTOlineWithTime(etimestr));
                    totaltime_data.setText(hour + "");
                }

            }
        });

    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_waichu;
    }

    public void initViewWithEntity() {
//        stime_data.setText(entity.stime);
//etime_data.setText(entity.etime);
//totaltime_data.setText(entity.totaltime);
//
//stime=stime_data.getText().toString();
//etime=etime_data.getText().toString();
//totaltime=totaltime_data.getText().toString();


    }

//    public void showTimer(final TextView textView) {
//        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance(true);
//        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day, String hour) {
//                if (!TextUtils.isEmpty(day) && day.length() == 1) {
//                    day = "0" + day;
//                }
//                if (!TextUtils.isEmpty(month) && month.length() == 1) {
//                    month = "0" + month;
//                }
//                if (!TextUtils.isEmpty(hour) && hour.length() == 1) {
//                    hour = "0" + hour;
//                }
//
//                textView.setText(year + "年" + month + "月" + day + "日" + hour + "时");
//            }
//
//            @Override
//            public void onCancelClick() {
//
//            }
//        });
//        String incomme_str = textView.getText().toString();
//        if (!TextUtils.isEmpty(incomme_str)) {
//            incomme_str = QZutils.replacePYTOline(incomme_str);
//            String[] strs = incomme_str.split("-");
//            dateDialog2.setDate(strs);
//        } else {
//            dateDialog2.setDate(null);
//        }
//        dateDialog2.show(getFragmentManager(), "DateScrollerDialog");
//    }

    @Override
    public void onClick(View v) {
        if (v == stime_click) {
           QZutils.showTimer(stime_data,getFragmentManager());
        } else if (v == etime_click) {
            QZutils.showTimer(etime_data,getFragmentManager());
        }else if(v == leftimg) {
            FragmentManager.popFragment(getActivity());
        }
    }

}
