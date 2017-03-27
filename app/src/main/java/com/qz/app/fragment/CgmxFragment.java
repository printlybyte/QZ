package com.qz.app.fragment;

import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.CaiGouMx;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/6/26.
 */
public class CgmxFragment extends BaseFragment implements View.OnClickListener{

    private TextView finish,backImg;
    private EditText name_data;
    private EditText guige_data;
    private EditText num_data;
    private EditText price_data;
    private TextView total_data;
    ;

    @Override
    public void initViews(ViewGroup rootView) {

        finish = (TextView) rootView.findViewById(R.id.finish);
        backImg = (TextView) rootView.findViewById(R.id.leftimg);
        name_data = (EditText) rootView.findViewById(R.id.name_data);
        guige_data = (EditText) rootView.findViewById(R.id.guige_data);
        num_data = (EditText) rootView.findViewById(R.id.num_data);
        price_data = (EditText) rootView.findViewById(R.id.price_data);
        total_data = (TextView) rootView.findViewById(R.id.total_data);
        final int num = getArguments().getInt(Constant.CGMXNUM);
        setTitle("采购明细(" + num + ")");
       int type =  getArguments().getInt(Constant.CGMXTYPE, Constant.CGMXTYPE_FROM_NEW);
        switch (type) {
            case Constant.CGMXTYPE_FROM_NEW:

                break;
            case Constant.CGMXTYPE_FROM_SHOW:
                initViewWithEntity();
                break;


        }

        num_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String num = num_data.getText().toString();
                String price = price_data.getText().toString();
                if(!TextUtils.isEmpty(price)&&!TextUtils.isEmpty(num)){
                    String  total ="";
                    if(price.contains(".")||num.contains(".")){
                        DecimalFormat df = new DecimalFormat(".00");
                        total =  df.format((Float.parseFloat(price)*Float.parseFloat(num)));
                    }else {
                        total =(Integer.parseInt(price)*Integer.parseInt(num))+"";
                    }
                    total_data.setText(total);
                }
            }
        });

        price_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String num = num_data.getText().toString();
                String price = price_data.getText().toString();
                if(!TextUtils.isEmpty(price)&&!TextUtils.isEmpty(num)){
                    String  total ="";
                    if(price.contains(".")||num.contains(".")){
                        DecimalFormat df = new DecimalFormat(".00");
                        total =  df.format((Float.parseFloat(price)*Float.parseFloat(num)));
                    }else {
                        total =(Integer.parseInt(price)*Integer.parseInt(num))+"";
                    }
                    total_data.setText(total);
                }
            }
        });


        finish.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    @Override
    public void setViews(View rootView) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cgmx;
    }

    public void initViewWithEntity() {
//        name_data.setText(entity.name_data);
//        guige_data.setText(entity.guige_data);
//        num_data.setText(entity.num_data);
//        price_data.setText(entity.price_data);
//        total_data.setText(entity.total);
    }

    public void finish() {
        CaiGouMx mx = new CaiGouMx();
        mx.name = name_data.getText().toString();
        mx.guige = guige_data.getText().toString();
        mx.num = num_data.getText().toString();
        mx.price = price_data.getText().toString();
        mx.total = total_data.getText().toString();
        CaiGouFragment.caiGouMx = mx;
    }
    @Override
    public void onClick(View v) {
        if(v == finish){
            finish();
            com.qz.app.utils.FragmentManager.popFragment(getActivity());
        }else if(v == backImg) {
            com.qz.app.utils.FragmentManager.popFragment(getActivity());
        }
    }
}
