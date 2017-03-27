package com.qz.app.filter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;


import com.qz.app.R;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.utils.GlideUtils;

import java.util.List;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List list = null;
    private Context mContext;
    private boolean showCheckButton = false;
    private boolean singleChoice;

    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List list, boolean showcheckButton) {
        this.list = list;
        this.showCheckButton = showcheckButton;
        notifyDataSetChanged();
    }


    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     */
    public void updateListView(boolean showcheckButton) {
        this.showCheckButton = showcheckButton;
        notifyDataSetChanged();
    }

    public void setSingleChoice(boolean b) {
        this.showCheckButton = b;
        singleChoice = b;
        notifyDataSetChanged();
    }
    public void setMultChoice(){
        this.showCheckButton = true;
    }


    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position) instanceof SortModel ? 0 : 1;
    }

    //两个样式 返回2
    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    public View getView(final int position, View view, ViewGroup arg2) {

        if (getItemViewType(position) == 0) {
            ViewHolder viewHolder = null;
            final SortModel mContent = (SortModel) list.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_employee, null);
                viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
                viewHolder.tvTitle = (TextView) view.findViewById(R.id.employeename_data);
                viewHolder.headimg = (ImageView) view.findViewById(R.id.headimg);
                viewHolder.selectimg = (ImageView) view.findViewById(R.id.selectimg);
                viewHolder.line = (ImageView) view.findViewById(R.id.line);
                viewHolder.depart_data = (TextView) view.findViewById(R.id.depart_data);
                viewHolder.job_data = (TextView) view.findViewById(R.id.job_data);
                viewHolder.divimg = (ImageView) view.findViewById(R.id.divimg);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            //根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);
            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            viewHolder.line.setVisibility(View.VISIBLE);
            if (position == getPositionForSection(section)) {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(mContent.getSortLetters());
                viewHolder.line.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.tvLetter.setVisibility(View.GONE);
            }
            viewHolder.tvTitle.setText(mContent.getName());
            viewHolder.depart_data.setText(mContent.userjson.deptname);
            viewHolder.job_data.setText(mContent.userjson.ranking);

            if(null == mContent.userjson||TextUtils.isEmpty(mContent.userjson.ranking)){

                viewHolder.divimg.setVisibility(View.GONE);
            } else{
                viewHolder.divimg.setVisibility(View.VISIBLE);
            }
            if (showCheckButton) {
                final ImageView imag = viewHolder.selectimg;
                viewHolder.selectimg.setVisibility(View.VISIBLE);
                if (mContent.ischecked) {
                    imag.setImageResource(R.drawable.selected_icon);
                } else {
                    imag.setImageResource(R.drawable.select_normal);
                }
                viewHolder.selectimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContent.ischecked = !mContent.ischecked;
                        if (mContent.ischecked) {
                            imag.setImageResource(R.drawable.selected_icon);
                            if(singleChoice) {
                                resetChoice(position);
                            }
                        } else {
                            imag.setImageResource(R.drawable.select_normal);
                        }
                    }
                });
            } else {
                viewHolder.selectimg.setVisibility(View.GONE);
            }

                GlideUtils.setRoundImage(mContext, mContent.userjson.face, R.drawable.default_head, R.drawable.default_head, viewHolder.headimg);

        } else if (getItemViewType(position) == 1) {
            ViewHolder2 viewHolder2 = null;
            if (view == null) {
                viewHolder2 = new ViewHolder2();
                view = LayoutInflater.from(mContext).inflate(R.layout.item_dept, null);
                viewHolder2.imageView = (ImageView) view.findViewById(R.id.departHead);

                viewHolder2.deptName = (TextView) view.findViewById(R.id.depart_name);
                viewHolder2.line = (ImageView) view.findViewById(R.id.line);
                view.setTag(viewHolder2);
            } else {
                viewHolder2 = (ViewHolder2) view.getTag();
            }
            DepAndEmp.Deptjson deptjson = (DepAndEmp.Deptjson) list.get(position);
            viewHolder2.deptName.setText(deptjson.name);
            viewHolder2.line.setVisibility(View.VISIBLE);
            if (deptjson.name.equals("无部门")) {
                viewHolder2.imageView.setImageResource(R.drawable.nodep_icon);
            } else if (deptjson.name.equals("离职员工")) {
                viewHolder2.imageView.setImageResource(R.drawable.dep_leave);
            }else {
                viewHolder2.imageView.setImageResource(R.drawable.defaultdep);
            }
            if(position == 0){
                viewHolder2.line.setVisibility(View.INVISIBLE);
            }


        }
        return view;

    }

    final static class ViewHolder2 {
        ImageView imageView;
        ImageView line;
        TextView deptName;
    }
    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView headimg;
        ImageView selectimg;
        ImageView line;
        TextView depart_data;
        TextView job_data;
        ImageView divimg;

    }

    private void resetChoice(int pos) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof SortModel) {
                if (i != pos) {
                    ((SortModel) list.get(i)).ischecked = false;
                }

            }
        }
        notifyDataSetChanged();
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return ((SortModel) list.get(position)).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {

        for (int i = 0; i < getCount(); i++) {
            if (list.get(i) instanceof SortModel) {
                String sortStr = ((SortModel) list.get(i)).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}