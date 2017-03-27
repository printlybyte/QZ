package com.qz.app.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.Selection_item;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/26.
 */
public class PianchaFragment extends BaseFragment implements View.OnClickListener{
    private ImageView leftimg;
    private TextView title;
    private ListView list;
    private TextView zdy_data;
    private View zdy_click;

    @Override
    public void initViews(ViewGroup rootView) {

        list = (ListView) rootView.findViewById(R.id.list);
        zdy_data = (TextView) rootView.findViewById(R.id.zdy_data);
        zdy_click = rootView.findViewById(R.id.zdylayout);
        zdy_click.setOnClickListener(this);
        initTitledView("允许偏差");
        String[] strs = getResources().getStringArray(R.id.piancha_data);
        String piancha = getArguments().getString(Constant.PIANCHA,"");
        int type = getArguments().getInt(Constant.PIANCHA_TYPE);

        ArrayList<Selection_item> items = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            Selection_item item = new Selection_item();
            item.selectionName = strs[i];
            item.selectioNum = i;
            if (!TextUtils.isEmpty(piancha)&&type == Constant.PIANCHA_TYPE_SELECT&&piancha.equals(strs[i])) {
                item.isSelected = true;
            }
            items.add(item);
        }
        if(type == Constant.PIANCHA_TYPE_ZDY) {
            zdy_data.setText(piancha);
        }
    }

    @Override
    public void setViews(View rootView) {


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_piancha;
    }

    public void initViewWithEntity() {
//            zdy_click.setOnClickListener(clicListener);
    }

    @Override
    public void onClick(View v) {
        if(v == zdy_click) {

        }


    }
}
