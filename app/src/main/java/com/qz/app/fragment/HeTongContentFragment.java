package com.qz.app.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.Hetong;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

/**
 * Created by Administrator on 2015/6/26.
 */
public class HeTongContentFragment extends BaseFragment implements View.OnClickListener {
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private EditText content_data;
    private Hetong strs;

    @Override
    public void initViews(ViewGroup rootView) {

        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        finish = (TextView) rootView.findViewById(R.id.finish);
        content_data = (EditText) rootView.findViewById(R.id.content_data);
        leftimg.setOnClickListener(this);
        finish.setOnClickListener(this);
        int type = getArguments().getInt(Constant.HETONG_FROM);
        switch (type) {
            case Constant.HT_NEW:
                strs = new Hetong();
                break;
            case Constant.HT_SHOW:
                strs = (Hetong) getArguments().getSerializable(Constant.HT_CONTENT);
                content_data.setText(strs.content);
                break;
        }


    }

    @Override
    public void setViews(View rootView) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hetongcontent;
    }

    public void initViewWithEntity() {
//        content_data.setText(entity.content_data);
//
//        content_data=content_data.getText().toString();

    }

    @Override
    public void onClick(View v) {
        if (v == leftimg) {
            FragmentManager.popFragment(getActivity());
        } else if (v == finish) {
            String content = content_data.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                strs.content = content;
                HeTongFragment.hetongContent =strs;
                FragmentManager.popFragment(getActivity());
            } else {
                CommonUtils.showToast("请输入内容");
            }
        }
    }
}
