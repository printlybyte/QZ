package com.qz.app.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;

import java.util.List;

/**
 * Created by Crazyfzw on 2016/4/16.
 */
public class ShortCutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static enum ITEM_TYPE {
        ITEM_TYPE_Theme,
        ITEM_TYPE_Video
    }
    //数据集
    public List<Integer> mdatas;
    private TextView themeTitle;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_TYPE_Theme.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listtitle,parent,false);
            return new ThemeVideoHolder(view);
        }else if(viewType == ITEM_TYPE.ITEM_TYPE_Video.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gride,parent,false);
            return new VideoViewHolder(view);
        }
          return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder instanceof ThemeVideoHolder){
           themeTitle.setText("励志");
        }else if (holder instanceof VideoViewHolder){

        }
    }

    public int getItemViewType(int position){

        return 0;
    }




    @Override
    public int getItemCount() {
        return mdatas.size();
    }


    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == ITEM_TYPE.ITEM_TYPE_Theme.ordinal()
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


    public class ThemeVideoHolder extends RecyclerView.ViewHolder{

        public ThemeVideoHolder(View itemView) {
            super(itemView);
            themeTitle = (TextView) itemView.findViewById(R.id.listtitle);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public VideoViewHolder(View itemView) {
            super(itemView);
        }
    }
}

