package com.qz.app.fragment;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;
import com.qz.app.view.SwitchButton;

/**
 * 创建考勤
 * Created by Administrator on 2015/6/26.
 */
public class NewkqFragment extends BaseFragment implements View.OnClickListener {
    private TextView cancel_click;
    private TextView title;
    private TextView finsh_click;
    private TextView sbtime_data;
    private com.qz.app.view.SwitchButton sbswitchbutton;
    private TextView xbtime_data;
    private com.qz.app.view.SwitchButton xbswitchbutton;
    private TextView time_data;
    private TextView kqbm_data;
    private TextView peoplename_data;
    private LinearLayout addpx_click;
    private ImageView delimg;
    private TextView kqname_data;
    private TextView kqplace_data;
    private TextView piancha_data;
    private com.qz.app.view.SwitchButton zdqdswitchbutton;
    public static String weeks;
    private View xbtime_click, sbtime_click;

    @Override
    public void initViews(ViewGroup rootView) {
        cancel_click = (TextView) rootView.findViewById(R.id.cancel_click);
        title = (TextView) rootView.findViewById(R.id.title);
        finsh_click = (TextView) rootView.findViewById(R.id.finsh_click);
        sbtime_data = (TextView) rootView.findViewById(R.id.sbtime_data);
        sbswitchbutton = (com.qz.app.view.SwitchButton) rootView.findViewById(R.id.sbswitchbutton);
        xbtime_data = (TextView) rootView.findViewById(R.id.xbtime_data);
        xbswitchbutton = (com.qz.app.view.SwitchButton) rootView.findViewById(R.id.xbswitchbutton);
        time_data = (TextView) rootView.findViewById(R.id.time_data);
        kqbm_data = (TextView) rootView.findViewById(R.id.kqbm_data);
        peoplename_data = (TextView) rootView.findViewById(R.id.peoplename_data);
        addpx_click = (LinearLayout) rootView.findViewById(R.id.addpx_click);
        delimg = (ImageView) rootView.findViewById(R.id.delimg);
        kqname_data = (TextView) rootView.findViewById(R.id.kqname_data);
        kqplace_data = (TextView) rootView.findViewById(R.id.kqplace_data);
        piancha_data = (TextView) rootView.findViewById(R.id.piancha_data);
        xbtime_click = rootView.findViewById(R.id.xbtime_click);
        sbtime_click = rootView.findViewById(R.id.sbtime_click);

        xbtime_click.setOnClickListener(this);
        sbtime_click.setOnClickListener(this);

        zdqdswitchbutton = (com.qz.app.view.SwitchButton) rootView.findViewById(R.id.zdqdswitchbutton);
        zdqdswitchbutton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        sbswitchbutton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                showTimer(sbtime_data,getFragmentManager());
            }
        });
        xbswitchbutton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked) {
                    xbtime_data.setText("18:00");
                    showTimer(xbtime_data, getFragmentManager());
                }else {
                    xbtime_data.setText("");
                }
            }
        });


    }

    @Override
    public void setViews(View rootView) {


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newkq;
    }

    public void initViewWithEntity() {
//        sbtime_data.setText(entity.sbtime);
//        xbtime_data.setText(entity.xbtime);
//        time_data.setText(entity.time);
//        kqbm_data.setText(entity.kqbm);
//        peoplename_data.setText(entity.peoplename);
//        kqname_data.setText(entity.kqname);
//        kqplace_data.setText(entity.kqplace);
//        piancha_data.setText(entity.piancha);
//
//        sbtime = sbtime_data.getText().toString();
//        xbtime = xbtime_data.getText().toString();
//        time = time_data.getText().toString();
//        kqbm = kqbm_data.getText().toString();
//        peoplename = peoplename_data.getText().toString();
//        kqname = kqname_data.getText().toString();
//        kqplace = kqplace_data.getText().toString();
//        piancha = piancha_data.getText().toString();
//
//        cancel_click.setOnClickListener(clicListener);
//        finsh_click.setOnClickListener(clicListener);
//        addpx_click.setOnClickListener(clicListener);


    }

    @Override
    public void onClick(View v) {

    }

    public  void showTimer(final TextView textView,FragmentManager fm) {
        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
            @Override
            public void onOkclick(String year, String month, String day,String hour) {
                if (!TextUtils.isEmpty(day) && day.length() == 1) {
                    day = "0" + day;
                }
                if (!TextUtils.isEmpty(month) && month.length() == 1) {
                    month = "0" + month;
                }
                if (!TextUtils.isEmpty(hour) && hour.length() == 1) {
                    hour = "0" + hour;
                }

                textView.setText(year + "-" + month + "-" + day);
            }
            @Override
            public void onCancelClick() {
                if(textView==sbtime_data){
                    sbswitchbutton.toggle();
                }else if(textView == xbtime_data){
                    xbswitchbutton.toggle();
                }

            }
        });
        String incomme_str = textView.getText().toString();
        if (!TextUtils.isEmpty(incomme_str)) {
            String[] strs = incomme_str.split("-");
            dateDialog2.setDate(strs);
        } else {
            dateDialog2.setDate(null);
        }
        dateDialog2.show(fm, "DateScrollerDialog");
    }


}
