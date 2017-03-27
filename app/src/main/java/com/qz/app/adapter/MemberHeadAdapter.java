package com.qz.app.adapter;

import android.content.Context;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.TransDataEntity;
import com.qz.app.fragment.EmployeesFragment;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.GlideUtils;
import com.qz.app.utils.QZutils;

import java.util.List;

/**
 * Created by du on 2017/2/22.
 */

public class MemberHeadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean editMode;
    private int type =Constant.EMP_FROM_SELECT_TASK_MEMBER;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    CommonUtils.showToast("删除成功！");

                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if (null != msg.obj) {
                        message = (String) msg.obj;
                    } else {
                        message = "删除失败";
                    }
                    CommonUtils.showToast(message);
                    break;

            }

        }
    };
    private Context context;
    private List datas;
    private LayoutInflater inflater;

    public MemberHeadAdapter(Context context, List list,int type) {
        this.context = context;
        this.datas = list;
        inflater = LayoutInflater.from(context);
        this.type = type;

    }



    public MemberHeadAdapter(Context context, List list) {
        this.context = context;
        this.datas = list;
        inflater = LayoutInflater.from(context);

    }


    public void setEditMode(boolean b) {
        this.editMode = b;
        notifyDataSetChanged();
    }

    @Override
    public ReaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_head_with_del, null);
        ReaderViewHolder vh = new ReaderViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DepAndEmp.Userjson item = (DepAndEmp.Userjson) datas.get(position);
        ((ReaderViewHolder) holder).name.setText(item.name);
        ((ReaderViewHolder) holder).name.setVisibility(View.VISIBLE);
        if (position == datas.size() - 1) {
            ((ReaderViewHolder) holder).del_icon_click.setVisibility(View.GONE);
            if (editMode) {
//                if(datas.size()==1) {
//                    ((ReaderViewHolder) holder).name.setVisibility(View.GONE);
//                }
                ((ReaderViewHolder) holder).icon.setImageResource(R.drawable.add_member_icon);
                ((ReaderViewHolder) holder).icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.EMP_FROM,type);

                        TransDataEntity dataEntity = new TransDataEntity();
                        setTransData(dataEntity);
                        dataEntity.type = type;
                        bundle.putSerializable(Constant.EMP_TRANSDATA,dataEntity);
                        com.qz.app.utils.FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmployeesFragment.class.getName(), bundle));
//                    QZutils.showPicSelection(fragmentManager, Constant.TASK_DETAIL_GET_PIC);
                    }
                });
            }
        } else {
            if(editMode) {
                ((ReaderViewHolder) holder).del_icon_click.setVisibility(View.VISIBLE);
            }else {
                ((ReaderViewHolder) holder).del_icon_click.setVisibility(View.GONE);
            }
            ((ReaderViewHolder) holder).icon.setOnClickListener(null);
            GlideUtils.setRoundImage(context, item.face, R.drawable.default_head, R.drawable.default_head, ((ReaderViewHolder) holder).icon);
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
        private TextView name;
        private ImageView icon;
        private ImageView del_icon_click;

        public ReaderViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.uname_data);
            icon = (ImageView) itemView.findViewById(R.id.peopleface);
            del_icon_click = (ImageView) itemView.findViewById(R.id.del_icon_click);
        }
    }

    private void setTransData(TransDataEntity dataEntity){
        dataEntity.MuiltChoice = false;
        switch (type){
            case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
            case Constant.EMP_FROM_SELECT_HUIBAOTO:
                dataEntity.MuiltChoice = true;
                break;
        }



    }


//    public void delimg(String id){
//
//        Message message = new Message();
//        message.setTarget(handler);
//        API.DElUPLOADIMG(message,id);
//    }


//    public void delmember(String id){
//
//        Message message = new Message();
//        message.setTarget(handler);
//        API.DElTASKMEMBER(message,id);
//    }

}
