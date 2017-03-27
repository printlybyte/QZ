package com.qz.app.view.swipemenulistview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseAdapter;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepEntity;
import com.qz.app.fragment.DepFragment_add;
import com.qz.app.fragment.DepinfoFragment;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends android.widget.BaseAdapter {

    final int INVALID_ID = -1;
    public boolean showSelectItem = true;
    public int swapitemPos;
    private Context context;
    private LayoutInflater inflater;
    private List entitys;

    private DepinfoFragment depinfoFragment;

    HashMap<DepEntity.Children, Integer> mIdMap = new HashMap<DepEntity.Children, Integer>();

    public StableArrayAdapter(Context context, List<DepEntity.Children> objects, DepinfoFragment depinfoFragment) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.entitys = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
        this.depinfoFragment = depinfoFragment;
    }

    @Override
    public int getCount() {
        return null == entitys ? 0 : entitys.size();
    }

    @Override
    public Object getItem(int position) {
        return entitys.get(position);
    }


    @Override
    public long getItemId(int position) {

        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        DepEntity.Children item = (DepEntity.Children) getItem(position);
        return mIdMap.get(item);
    }

    public int getLayoutId() {
        return R.layout.item_depinfo;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.depName, R.id.showdetail_click, R.id.changpos, R.id.content};
    }

    public void initViews(ViewHolder vh, int position) {

//        final DepEntity.Children entity = (DepEntity.Children) entitys.get(position);
//        ((TextView) vh.getView(R.id.depName)).setText(entity.name);
//
//        if (swapitemPos == position && !showSelectItem) {
//            vh.getView(R.id.content).setVisibility(View.INVISIBLE);
//        } else {
//            vh.getView(R.id.content).setVisibility(View.VISIBLE);
//        }
//        vh.getView(R.id.showdetail_click).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putInt(Constant.ADD_FROM, Constant.ADD_FROM_SHOW);
//                bundle.putSerializable("children", entity);
//                FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, DepFragment_add.class.getName(), bundle));
//            }
//        });
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder vh = null;
        final DepEntity.Children entity = (DepEntity.Children) getItem(position);
//        if(null ==convertView){
        vh = new ViewHolder();
        View convertView = inflater.inflate(R.layout.item_depinfo, null);
        vh.name = (TextView) convertView.findViewById(R.id.depName);
        vh.showdetail = (ImageView) convertView.findViewById(R.id.showdetail_click);
        vh.content = convertView.findViewById(R.id.content);
        vh.showdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.ADD_FROM, Constant.ADD_FROM_SHOW);
                bundle.putSerializable("children", entity);
                String parentName = "";
                try {
                    parentName = entity.parentDepName;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bundle.putString(Constant.DEP_PARENT_NAME, parentName);
                FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, DepFragment_add.class.getName(), bundle));
            }
        });
        if (swapitemPos == position && !showSelectItem) {
            vh.content.setVisibility(View.INVISIBLE);
        } else {
            vh.content.setVisibility(View.VISIBLE);
        }
        vh.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != entity.children && entity.children.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.DEP_ID, entity.id + "");
                    bundle.putString(Constant.DEP_PARENT_NAME, entity.parentDepName);
                    FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, DepinfoFragment.class.getName(), bundle));
                }
            }
        });
        vh.name.setText(entity.name);
        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public View content;
        public ImageView showdetail;
    }

}
