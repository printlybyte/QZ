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
import com.qz.app.entity.WorkItem;

import java.util.List;

/**
 * Created by du on 2017/2/17.
 */

public class WorkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List datas;
    private LayoutInflater inflater;

    private RecyclItemClickListener itemClickListener;

    public interface RecyclItemClickListener {

        void onItemClick(WorkItem item);
    }

    public void setOnListItemClickListener(RecyclItemClickListener listItemClickListener) {
        this.itemClickListener = listItemClickListener;

    }

    public WorkAdapter(Context context, List<WorkItem> list) {
        this.context = context;
        this.datas = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_works, null);
        WorkViewHolder vh = new WorkViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final WorkItem item = (WorkItem) datas.get(position);
        ((WorkViewHolder) holder).icon.setImageResource(item.img);
        ((WorkViewHolder) holder).name.setText(item.text);
        if (!TextUtils.isEmpty(item.fragmentName)) {
            ((WorkViewHolder) holder).contentRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(item);
                }
            });
            ((WorkViewHolder) holder).contentRoot.setEnabled(true
            );
        } else {
            ((WorkViewHolder) holder).contentRoot.setEnabled(false);
        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class WorkViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;
        private View contentRoot;

        public WorkViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            icon = (ImageView) itemView.findViewById(R.id.sign_icon);
            contentRoot = itemView.findViewById(R.id.itemroot);
        }
    }


}
