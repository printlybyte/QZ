package com.qz.app.adapter;

import java.util.List;

import com.qz.app.base.BaseAdapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import com.qz.app.R;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DateScrollerDialog;
import com.qz.app.view.DialogButtonsListener;
//import com.qz.app.entity.;

public class ChucahiAdapter extends BaseAdapter {

    private FragmentManager fragmentManager;

    public ChucahiAdapter(Context context, List<? extends Object> data, FragmentManager fragmentManager) {
        super(context, data);
        this.fragmentManager = fragmentManager;
        // TODO Auto-generated constructor stub
    }

    public int getLayoutId() {
        return R.layout.item_chuchai;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.typename, R.id.delbut, R.id.postion_data, R.id.stime_click, R.id.etime_click, R.id.stime_data, R.id.etime_data,};
    }

    public void initViews(ViewHolder vh, final int position) {
        final ShenpiDetail.Content_json detail = (ShenpiDetail.Content_json) entitys.get(position);
        ((TextView) vh.getView(R.id.stime_data)).setText(detail.stime);
        ((TextView) vh.getView(R.id.etime_data)).setText(detail.etime);
        ((TextView) vh.getView(R.id.typename)).setText("出差明细（" + (position+1)+")");
        vh.getView(R.id.delbut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CommAlertDialog dialog = new CommAlertDialog(context);
                dialog.setTitleTv("提示");
                dialog.setContentInfo("是否删除此出差明细？");
                dialog.show();
                dialog.setOkBtnName("是");
                dialog.setCannelBtnName("否");
                dialog.setButtonsListener(new DialogButtonsListener() {
                    @Override
                    public void onOKClick(Objects objects) {
                        entitys.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancleClick(Objects objects) {
                        dialog.dismiss();
                    }
                });


            }
        });

        vh.getView(R.id.stime_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimer(detail, true);
            }
        });
        vh.getView(R.id.etime_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimer(detail, false);
            }
        });


    }

    public void showTimer(final ShenpiDetail.Content_json item, final boolean isStarttime) {
        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
            @Override
            public void onOkclick(String year, String month, String day,String hour) {
                if (!TextUtils.isEmpty(day) && day.length() == 1) {
                    day = "0" + day;
                }
                if (isStarttime) {
                    item.stime = year + "-" + month + "-" + day;
                    notifyDataSetChanged();
                } else {
                    item.etime = year + "-" + month + "-" + day;
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelClick() {


            }
        });
        String incomme_str = "";
        if (isStarttime) {
            incomme_str = item.stime;
        } else {
            incomme_str = item.etime;

        }
        if (!TextUtils.isEmpty(incomme_str)) {
            String[] strs = incomme_str.split("-");
            dateDialog2.setDate(strs);
        } else {
            dateDialog2.setDate(null);
        }
        dateDialog2.show(fragmentManager, "DateScrollerDialog");
    }
}
