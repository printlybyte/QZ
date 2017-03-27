package com.qz.app.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.utils.FragmentManager;

/**
 * Created by 易超然 on 2017/3/18.
 */

public class XiaoshouFragment extends BaseFragment {
    private LinearLayout kehubt;
    private LinearLayout xsjihuibt;
    private LinearLayout xsbaifangbt;
    private LinearLayout xsbaojiadanbt;
    private LinearLayout xsdingdanbt;
    private LinearLayout shouhoudanbt;
    private LinearLayout xstuihuodanbt;


    @Override
    public void initViews(ViewGroup rootView) {
        kehubt = (LinearLayout) rootView.findViewById(R.id.kehu_chick);
        xsjihuibt = (LinearLayout) rootView.findViewById(R.id.xiaoshoujihui_click);
        xsbaifangbt = (LinearLayout) rootView.findViewById(R.id.baifangjihui_click);
        xsbaojiadanbt = (LinearLayout) rootView.findViewById(R.id.xiaoshoubaojiadan_click);
        xsdingdanbt = (LinearLayout) rootView.findViewById(R.id.xiaoshoudingdan_click);
        shouhoudanbt = (LinearLayout) rootView.findViewById(R.id.shouhoudan_click);
        xstuihuodanbt = (LinearLayout) rootView.findViewById(R.id.xiaoshoutuihuodan_click);
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.kehu_chick:
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(),KeHuxqFragment.class.getName()));
                        break;
                    case R.id.xiaoshoujihui_click:
                        Toast.makeText(getContext(), "sdfsd", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.baifangjihui_click:
                        break;
                    case R.id.xiaoshoubaojiadan_click:
                        break;
                    case R.id.xiaoshoudingdan_click:
                        break;
                    case R.id.shouhoudan_click:
                        break;
                    case R.id.xiaoshoutuihuodan_click:
                        break;
                }

            }
        };


        kehubt.setOnClickListener(clicListener);
        xsjihuibt.setOnClickListener(clicListener);
        xsbaifangbt.setOnClickListener(clicListener);
        xsbaojiadanbt.setOnClickListener(clicListener);
        xsdingdanbt.setOnClickListener(clicListener);
        shouhoudanbt.setOnClickListener(clicListener);
        xstuihuodanbt.setOnClickListener(clicListener);
        initTitledView("销售");


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xiaoshou;
    }

    @Override
    public void setViews(View rootView) {

    }
}
