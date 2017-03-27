package com.qz.app.filter;

import android.content.Context;
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

public class SearchSortAdapter extends BaseAdapter implements SectionIndexer {
    private List list = null;
    private Context mContext;
    private boolean showCheckButton = false;
    private boolean singleChoice;

    public SearchSortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void clearData() {
        if (null != list) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List list, boolean showcheckButton) {
        this.showCheckButton = showcheckButton;
        this.list = list;
        notifyDataSetChanged();

    }
    public void updateListView(boolean showcheckButton) {
        this.showCheckButton = showcheckButton;
        notifyDataSetChanged();
    }


    public void setShowCheckButton(boolean b) {
        showCheckButton = b;
        notifyDataSetChanged();
    }

    public void setSingleChoice(boolean b) {
         showCheckButton = b;
        this.singleChoice = b;
        notifyDataSetChanged();
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


    public View getView(final int position, View view, ViewGroup arg2) {

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
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        if (showCheckButton) {
            final ImageView imag = viewHolder.selectimg;
            viewHolder.selectimg.setVisibility(View.VISIBLE);
            if (mContent.ischecked) {
                imag.setImageResource(R.drawable.selected_icon);
            } else {
                imag.setImageResource(R.drawable.select_normal);
            }
            imag.setOnClickListener(new View.OnClickListener() {
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
        GlideUtils.setRoundImage(mContext, mContent.rows.face, R.drawable.default_head, R.drawable.default_head, viewHolder.headimg);
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        viewHolder.tvTitle.setText(((SortModel) list.get(position)).getName());
        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView headimg;
        ImageView selectimg;
        ImageView line;
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