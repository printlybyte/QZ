package com.qz.app.utils;

import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.qz.app.adapter.DateNumericAdapter;
import com.qz.app.fragment.SelectionDialogFragment;
import com.qz.app.view.DateScrollerDialog;
import com.qz.app.view.kankan.wheel.widget.WheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by du on 2017/2/24.
 */

public class QZutils {
    public static String cutTimer(String time) {
        if (TextUtils.isEmpty(time) || time.length() <= 10) {
            return time;
        }
        return time.substring(0, 10);
    }

    public static String cutTimeWithoutYear2(String time) {
        if (TextUtils.isEmpty(time) || time.length() <= 11) {
            return time;
        }
        time = time.substring(5, 16);
        return time;
    }

    public static String cutTimeWithoutYear3(String time) {
        if (TextUtils.isEmpty(time) || time.length() <= 11) {
            return time;
        }
        time = time.substring(5, 10);
        return time;
    }



    public static String cutTimeWithoutYear(String time) {
        if (TextUtils.isEmpty(time) || time.length() <= 11) {
            return time;
        }
        if (!time.contains("-")) {
            return time;
        }
        time = time.substring(5,16);
        time = time.replaceFirst("-", "月");
        time = time.replaceFirst("-", "日");
        return time;
    }


    public static String cutTimerWtihOutYear(String time) {
        if (TextUtils.isEmpty(time) || time.length() <= 5) {
            return time;
        }
        return time.substring(5, 10);
    }

    public static String setPinYinDate(String string) {
        if (TextUtils.isEmpty(string) || !string.contains("-")) {
            return "";
        }
        String strs[] = string.split("-");
        if (strs.length >= 3) {
            return strs[0] + "年" + strs[1] + "月" + strs[2] + "日";
        }
        return "";
    }

    public static String setPinYinDateWithSecond(String stringsource) {
        String string = cutTimer(stringsource);
        if (TextUtils.isEmpty(string) || !string.contains("-")) {
            return "";
        }
        String strs[] = string.split("-");
        if (strs.length >= 3) {
            return strs[0] + "年" + strs[1] + "月" + strs[2] + "日  " + stringsource.substring(10, 15);
        }
        return "";
    }


//    public static void showTimerDialog(final TextView textView, android.support.v4.app.FragmentManager manager) {
//        DateScrollerDialog birthdayDialog = DateScrollerDialog.newInstance();
//        birthdayDialog.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day,String hour) {
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
//        String birthday_str = textView.getText().toString();
//        if (!TextUtils.isEmpty(birthday_str)) {
//            String[] strs = birthday_str.split("-");
//            birthdayDialog.setDate(strs);
//        } else {
//            birthdayDialog.setDate(null);
//        }
//        birthdayDialog.show(manager, "DateScrollerDialog");
//    }

    public static void showPicSelection(FragmentManager manager, int type) {

        SelectionDialogFragment selectiondialog = new SelectionDialogFragment();
        selectiondialog.show(manager, "");

    }

    public static String getData(String data) {
        String time = cutTimeWithoutYear(cutTimeWithoutYear3(data));
        time = time +" " +getDayWithWeek();
        return time;

    }

    public static String getDayWithWeek() {
        Calendar calendar = Calendar.getInstance();
        int curDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String daystr = curDay + "";
        switch (curDay) {
            case 0:
                daystr = "周日";
                break;
            case 1:
                daystr = "周一";
                break;
            case 2:
                daystr = "周二";
                break;
            case 3:
                daystr = "周三";
                break;
            case 4:
                daystr = "周四";
                break;
            case 5:
                daystr = "周五";
                break;
            case 6:
                daystr = "周六";
                break;


        }

        return daystr;
    }

    public static String replacePYTOline(String incomme_str) {
        return     incomme_str.replace("年","-").replace("月","-").replace("日","-").replace("时","");
    }

    public static String replacePYTOlineWithTime(String incomme_str) {
        return     incomme_str.replace("年","-").replace("月","-").replace("日"," ").replace("时",":00:00");
    }

    public static long getTime(String time){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
            return date.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    public static void showTimer(final TextView textView,FragmentManager fm) {
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
