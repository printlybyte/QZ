package com.qz.app.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qz.app.App;
import com.qz.app.R;
import com.qz.app.adapter.DateArrayAdapter;
import com.qz.app.adapter.DateNumericAdapter;
import com.qz.app.view.kankan.wheel.widget.OnWheelChangedListener;
import com.qz.app.view.kankan.wheel.widget.WheelView;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择框, 设置若干参数
 *
 * @author C.L.Wang
 */
public class DateScrollerDialog extends DialogFragment implements View.OnClickListener {


    private TimerDialogWheel listener;
    private View view;
    private String[] dates;
    private boolean showHour = false;

    // 实例化参数, 传入数据
    public static DateScrollerDialog newInstance() {
        DateScrollerDialog dateScrollerDialog = new DateScrollerDialog();
        return dateScrollerDialog;
    }

    public static DateScrollerDialog newInstance(boolean b) {
        DateScrollerDialog dateScrollerDialog = new DateScrollerDialog(b);
        return dateScrollerDialog;
    }

    private DateScrollerDialog() {

    }

    private DateScrollerDialog(boolean showhour) {
        this.showHour = showhour;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getActivity().getWindow();
        // 隐藏软键盘
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Dialog的位置置底
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.BOTTOM);
        }
    }

    public WheelView month;
    public WheelView year;
    public WheelView day;
    public WheelView hour;

    public void setDate(String[] dates) {
        this.dates = dates;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_anim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); // 后退键取消
        dialog.setCanceledOnTouchOutside(true); // 点击外面被取消
        LayoutInflater inflater = LayoutInflater.from(getContext());

        view = inflater.inflate(R.layout.timepicker_layout, null);
        dialog.setContentView(view); // 设置View

        Calendar calendar = Calendar.getInstance();
        month = (WheelView) view.findViewById(R.id.month);
        year = (WheelView) view.findViewById(R.id.year);
        day = (WheelView) view.findViewById(R.id.day);
        hour = (WheelView) view.findViewById(R.id.hour);
        if (showHour) {
            hour.setVisibility(View.VISIBLE);
        } else {
            hour.setVisibility(View.GONE);
        }
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day, hour);
            }
        };

        // month
        if (null == dates) {
            dates = new String[4];
        }
        int curYear = calendar.get(Calendar.YEAR);
        if (!TextUtils.isEmpty(dates[0])) {
            curYear = Integer.parseInt(dates[0]);
        }


        year.setViewAdapter(new DateNumericAdapter(getActivity(), 1900, 2050, curYear - 1900));
        year.setCurrentItem(curYear - 1900);
        year.addChangingListener(listener);


        int curMonth = calendar.get(Calendar.MONTH);
        if (!TextUtils.isEmpty(dates[1])) {
            if (dates[1].startsWith("0")) {
                dates[1] = dates[1].substring(1, 2);
            }
            curMonth = Integer.parseInt(dates[1]) - 1;
        }
        String months[] = new String[]{"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12"};
        month.setViewAdapter(new DateArrayAdapter(getActivity(), months, curMonth));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);


        //day
        updateDays(year, month, day, hour);

        initView("日期选择");

        return dialog;
    }

    public void show(android.support.v4.app.FragmentManager manager, String tag, String[] date) {
        super.show(manager, tag);
        if (null == date) {
            date = new String[4];
        }

    }

    void updateDays(WheelView year, WheelView month, WheelView day, WheelView hour) {
        Calendar calendar = Calendar.getInstance();
        int curDay = calendar.get(Calendar.DAY_OF_MONTH) - 1;


        if (!TextUtils.isEmpty(dates[2])) {
            if (dates[2].startsWith("0")) {
                dates[2] = dates[2].substring(1, 2);
            }
            curDay = Integer.parseInt(dates[2]) - 1;
        }
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(getActivity(), 1, maxDays, curDay));
        int curPos = Math.min(maxDays, curDay);
        day.setCurrentItem(curPos);

        int currenthour = new Date().getHours();
        if (currenthour == 0) {
            currenthour = 24;
        }
        currenthour = currenthour - 1;
        if (dates.length == 4) {
            if (!TextUtils.isEmpty(dates[3])) {
                if (dates[3].startsWith("0")) {
                    dates[3] = dates[3].substring(1, 2);
                }
                currenthour = Integer.parseInt(dates[3]) - 1;
            }
        }
        hour.setViewAdapter(new DateNumericAdapter(getActivity(), 1, 24, currenthour));
        hour.setCurrentItem(currenthour);
    }

    public void setListener(TimerDialogWheel listener) {
        this.listener = listener;
    }

    /**
     * 初始化视图
     *
     * @return 当前视图
     */
    private View initView(String titletext) {

        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(this); // 设置取消按钮
        TextView sure = (TextView) view.findViewById(R.id.tv_sure);
        sure.setOnClickListener(this); // 设置确认按钮
        TextView title = (TextView) view.findViewById(R.id.tv_title);

        // 设置顶部栏
        View toolbar = view.findViewById(R.id.toolbar); // 头部视图
        toolbar.setBackgroundResource(R.color.transparent);
        title.setText(titletext); // 设置文字
        cancel.setText("取消");
        sure.setText("确定");
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancel) {
            if (null != listener) {
                listener.onCancelClick();
            }
            dismiss(); // 取消
        } else if (i == R.id.tv_sure) {
            if (null != listener) {
                listener.onOkclick((year.getCurrentItem() + 1900) + "", month.getCurrentItem() + 1 + "", day.getCurrentItem() + 1 + "", hour.getCurrentItem() + 1 + "");
            }
            dismiss();
        }

    }

    public static void showTimer(final FragmentManager fragmentManager, final TextView textView, final boolean setbg) {

        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
            @Override
            public void onOkclick(String year, String month, String day, String hour) {
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
                if (setbg) {
                    textView.setBackgroundResource(R.drawable.choice_selected);
                    textView.setTextColor(App.mContext.getResources().getColor(R.color.blue));
                }
            }

            @Override
            public void onCancelClick() {

            }
        });
        String incomme_str = textView.getText().toString();
        if (!TextUtils.isEmpty(incomme_str)) {
            String[] strs = incomme_str.split("-");
            dateDialog2.setDate(strs);
        } else {
            dateDialog2.setDate(null);
        }
        dateDialog2.show(fragmentManager, "DateScrollerDialog");

    }

    public static void showTimer(final FragmentManager fragmentManager, final TextView textView) {
        showTimer(fragmentManager, textView, false);
    }


    public interface TimerDialogWheel {
        void onOkclick(String year, String month, String day, String hour);

        void onCancelClick();
    }

}
