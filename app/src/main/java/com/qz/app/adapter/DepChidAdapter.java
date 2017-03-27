package com.qz.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepEntity;
import com.qz.app.fragment.EmplistFragment;
import com.qz.app.fragment.EmployeesFragment;
import com.qz.app.utils.FragmentManager;

public class DepChidAdapter extends com.qz.app.base.BaseAdapter {


    private boolean multchoice;
    private Fragment fragment;

    public DepChidAdapter(Context context, java.util.List data, Fragment fragment) {
        super(context, data);
        this.fragment = fragment;
    }

    public int getLayoutId() {
        return R.layout.item_depchild;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.checkimg_click, R.id.empname_data, R.id.showEmp_click, R.id.contentlayout};
    }


    public void initViews(ViewHolder vh, final int position) {
        final DepEntity.Children item = (DepEntity.Children) entitys.get(position);
        if (multchoice) {
            final ImageView img = (ImageView) vh.getView(R.id.checkimg_click);
            img.setVisibility(View.VISIBLE);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    item.isSlected = !item.isSlected;
                    if (item.isSlected) {
                        img.setImageResource(R.drawable.selected_icon);
                    } else {
                        img.setImageResource(R.drawable.select_normal);
                    }
                }
            });
        } else {
            vh.getView(R.id.checkimg_click).setVisibility(View.GONE);
        }
        ((TextView) vh.getView(R.id.empname_data)).setText(item.name);
        vh.getView(R.id.contentlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DEP_ID, item.id + "");
                bundle.putString(Constant.DEP_NAME, item.name + "");

                FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmplistFragment.class.getName(), bundle));
            }
        });
//        item = () entitys.get(position);
//        ((TextView) vh.getView(R.id.empname_data)).setText(item.empname);
    }


    /**
     * 是不是多选部门
     *
     * @param b
     */
    public void setMultchoice(boolean b) {
        multchoice = b;
    }

//    public void resetData(int postion){
//        for(int a=0;a<entitys.size();a++){
//            if(a!=postion) {
//                ((DepEntity.Children) entitys.get(a)).isSlected = false;
//            }
//        }
//    }


}
