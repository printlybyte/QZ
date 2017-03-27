package com.qz.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;

import java.util.HashMap;

/**
 * Created by du on 2017/2/22.
 */

public class BaseRecycleViewAdapter extends RecyclerView.Adapter {

    private ContentViewHolder viewHolder;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void init(){


    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        HashMap<Integer,View> viewHashMap=new HashMap<>();
        public ContentViewHolder(View itemView) {
            super(itemView);
        }


    }


}
