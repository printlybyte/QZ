package com.qz.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.activity.ImagescanActivity;
import com.qz.app.constant.Constant;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.fragment.EdittaskFragment;
import com.qz.app.utils.GlideUtils;
import com.qz.app.utils.L;

import java.util.ArrayList;

import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;

/**
 * Created by du on 2017/2/22.
 */

public class UploadShenpiImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList datas;
    private LayoutInflater inflater;
    private Fragment fragment;
    private boolean editMode;

    public UploadShenpiImgAdapter(Context context, Fragment fragment, ArrayList list) {
        this.context = context;
        this.datas = list;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }
    public void setEditMode(boolean b) {
        this.editMode = b;
        notifyDataSetChanged();
    }

    @Override
    public ReaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_shenpi_comment, null);
        ReaderViewHolder vh = new ReaderViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LocalFileEntity item = (LocalFileEntity) datas.get(position);

        if (editMode&&position == datas.size() - 1) {
                ((ReaderViewHolder) holder).del_icon_click.setVisibility(View.GONE);
                ((ReaderViewHolder) holder).icon.setImageResource(R.drawable.add_rect_icon);
                ((ReaderViewHolder) holder).icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picker.from(fragment)
                                .count(9)
                                .enableCamera(true)
                                .setEngine(new GlideEngine())
//                .setEngine(new PicassoEngine())
//                .setEngine(new ImageLoaderEngine())
//                .setEngine(new CustomEngine())
                                .forResult(EdittaskFragment.REQUEST_CODE_CHOOSE);
//                    QZutils.showPicSelection(fragmentManager, Constant.TASK_DETAIL_GET_PIC);
                    }
                });
        } else {
            if(editMode) {
                ((ReaderViewHolder) holder).del_icon_click.setVisibility(View.VISIBLE);
            }else {
                ((ReaderViewHolder) holder).del_icon_click.setVisibility(View.GONE);
            }
            if (item.fromNet) {
                GlideUtils.getRoundCorImg(context, item.url+Constant.QINNUI_SMAILL_TASK, R.drawable.img_default, R.drawable.img_default, ((ReaderViewHolder) holder).icon);
            }else {
//                ((ReaderViewHolder) holder).icon.setImageURI(item.path);
                GlideUtils.getRoundCorImg(context, item.path, R.drawable.img_default, R.drawable.img_default, ((ReaderViewHolder) holder).icon);
//                GlideUtils.setLocalRoundImage2(context, item.path, R.drawable.default_head, R.drawable.default_head, ((ReaderViewHolder) holder).icon);
            }
            ((ReaderViewHolder) holder).icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    int num = 0;
                    if(editMode){
                        num = 1;
                    }
                    LocalFileEntity[] senddatas = new LocalFileEntity[datas.size()-num];
                    for(int m=0;m<datas.size()-num;m++){
                        senddatas[m]=((LocalFileEntity) datas.get(m));
                    }

                    intent.putExtra(
                            Constant.PHOTO_POS, position);
                    intent.putExtra(
                            Constant.PHOTO_PATH, senddatas);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(context, ImagescanActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        ((ReaderViewHolder) holder).del_icon_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    class ReaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private ImageView del_icon_click;

        public ReaderViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.image);
            del_icon_click = (ImageView) itemView.findViewById(R.id.del_icon_click);
        }
    }

}
