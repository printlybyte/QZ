package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiFragment;
import com.qz.app.utils.FragmentManager;

/**
 * Created by Administrator on 2015/6/26.
 */
public class PtshenheFragment extends BaseShenpiFragment {
    private TextView leftimg;
    private TextView title;
    private TextView finish;
    private RelativeLayout money_click;
    private EditText reason_data;
    private EditText content;

    ;

    @Override
    public void initViews(ViewGroup rootView) {
        super.initViews(rootView);
        leftimg = (TextView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        finish = (TextView) rootView.findViewById(R.id.finish);
        money_click = (RelativeLayout) rootView.findViewById(R.id.money_click);
        reason_data = (EditText) rootView.findViewById(R.id.reason_data);
        content = (EditText) rootView.findViewById(R.id.content);

        leftimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });
    }

    @Override
    public void setViews(View rootView) {
        super.setViews(rootView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ptshenhe;
    }

    public void initViewWithEntity() {
//        reason_data.setText(entity.reason_data);
//        reason_data = reason_data.getText().toString();
//        money_click.setOnClickListener(clicListener);
    }
}
