package com.qz.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.entity.Documentdetail;
import com.qz.app.entity.WorkItem;
import com.qz.app.utils.GlideUtils;

import java.util.List;

/**
 * Created by du on 2017/2/22.
 */

public class ReaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List datas;
    private LayoutInflater inflater;
    public ReaderAdapter(Context context, List<Documentdetail.ReadMan> list) {
        this.context = context;
        this.datas = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ReaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_peoplehead, null);
        ReaderViewHolder vh = new ReaderViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Documentdetail.ReadMan readMan = (Documentdetail.ReadMan) datas.get(position);
        ((ReaderViewHolder)holder).name.setText(readMan.name);
        String face = "";
        if(!TextUtils.isEmpty(readMan.idface)){
            face=readMan.idface;
        }else {
            face =readMan.face;
        }
          GlideUtils.setRoundImage(context,face,R.drawable.default_head,R.drawable.default_head,((ReaderViewHolder)holder).icon);
    }

    @Override
    public int getItemCount() {
        return null ==datas?0:datas.size();
    }

    class ReaderViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;
        public ReaderViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.uname_data);
            icon= (ImageView) itemView.findViewById(R.id.peopleface);
        }
    }

}
