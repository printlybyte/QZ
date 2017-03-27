package com.qz.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.utils.FragmentManager;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;
import com.qz.app.view.PopWinWithList;

import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class KaoqinFragment extends BaseFragment implements View.OnClickListener {
    private ImageView leftimg;
    private TextView title;
    private ImageView moretbut;
    private TextView time_data;
    private TextView date_data;
    private TextView jiabanbut;
    private TextView shanbantime_data;
    private TextView button1;
    private LinearLayout yqdlayout;
    private TextView sbqdtime_data;
    private TextView xbtime_data;
    private TextView button2;
    private TextView qttime_data;
    private TextView jbsbtime_data;
    private TextView button3;
    private TextView jbqdtime_data;
    private TextView jbxbtime_data;
    private TextView button4;
    private TextView jbqttime_data;
    private TextView zdqdbutton;
    private View jiabanlayout,xbqtlayout,jbqdlayout,jbqtlayout,putongshangban;
    @Override
    public void initViews(ViewGroup rootView) {
        leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        moretbut = (ImageView) rootView.findViewById(R.id.moretbut);
        time_data = (TextView) rootView.findViewById(R.id.time_data);
        date_data = (TextView) rootView.findViewById(R.id.date_data);
        jiabanbut = (TextView) rootView.findViewById(R.id.jiabanbut);
        shanbantime_data = (TextView) rootView.findViewById(R.id.shanbantime_data);
        button1 = (TextView) rootView.findViewById(R.id.button1);
        yqdlayout = (LinearLayout) rootView.findViewById(R.id.yqdlayout);
        xbqtlayout = (LinearLayout) rootView.findViewById(R.id.xbqtlayout);
        jbqdlayout = (LinearLayout) rootView.findViewById(R.id.jbqdlayout);
        jbqtlayout = (LinearLayout) rootView.findViewById(R.id.jbqtlayout);
        sbqdtime_data = (TextView) rootView.findViewById(R.id.sbqdtime_data);
        xbtime_data = (TextView) rootView.findViewById(R.id.xbtime_data);
        button2 = (TextView) rootView.findViewById(R.id.button2);
        qttime_data = (TextView) rootView.findViewById(R.id.qttime_data);
        jbsbtime_data = (TextView) rootView.findViewById(R.id.jbsbtime_data);
        button3 = (TextView) rootView.findViewById(R.id.button3);
        jbqdtime_data = (TextView) rootView.findViewById(R.id.jbqdtime_data);
        jbxbtime_data = (TextView) rootView.findViewById(R.id.jbxbtime_data);
        button4 = (TextView) rootView.findViewById(R.id.button4);
        jbqttime_data = (TextView) rootView.findViewById(R.id.jbqttime_data);
        zdqdbutton = (TextView) rootView.findViewById(R.id.zdqdbutton);
        jiabanlayout =  rootView.findViewById(R.id.jiabanlayout);
        putongshangban =  rootView.findViewById(R.id.putongshangban);

        initTitledView("考勤");

        yqdlayout.setVisibility(View.GONE);
        xbqtlayout.setVisibility(View.GONE);
        jbqdlayout.setVisibility(View.GONE);
        jbqtlayout.setVisibility(View.GONE);
        jiabanlayout.setVisibility(View.GONE);

        moretbut.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        jiabanbut.setOnClickListener(this);
        putongshangban.setOnClickListener(this);

    }

    private void setlayout(){


    }


    @Override
    public void setViews(View rootView) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqin;
    }

    public void initViewWithEntity() {
//        time_data.setText(entity.timetv);
//date_data.setText(entity.datetv);
//shanbantime_data.setText(entity.shanbantimetv);
//sbqdtime_data.setText(entity.sbqdtimetv);
//xbtime_data.setText(entity.xbtimetv);
//qttime_data.setText(entity.qttimetv);
//jbsbtime_data.setText(entity.jbsbtimetv);
//jbqdtime_data.setText(entity.jbqdtimetv);
//jbxbtime_data.setText(entity.jbxbtimetv);
//jbqttime_data.setText(entity.jbqttimetv);

//timetv=time_data.getText().toString();
//datetv=date_data.getText().toString();
//shanbantimetv=shanbantime_data.getText().toString();
//sbqdtimetv=sbqdtime_data.getText().toString();
//xbtimetv=xbtime_data.getText().toString();
//qttimetv=qttime_data.getText().toString();
//jbsbtimetv=jbsbtime_data.getText().toString();
//jbqdtimetv=jbqdtime_data.getText().toString();
//jbxbtimetv=jbxbtime_data.getText().toString();
//jbqttimetv=jbqttime_data.getText().toString();


    }

    @Override
    public void onClick(View v) {
        if (v == moretbut) {
            showMore(moretbut);
        } else if (v == button1) {

        } else if (v == button2) {

        } else if (v == button3) {

        } else if (v == button4) {

        }else if(v == jiabanbut){

            final CommAlertDialog dialog = new CommAlertDialog(getContext());
            dialog.setTitleTv("提示");
            dialog.setContentInfo("是否进入加班考勤模式？");
            dialog.show();
            dialog.setOkBtnName("是");
            dialog.setCannelBtnName("否");
            dialog.setButtonsListener(new DialogButtonsListener() {
                @Override
                public void onOKClick(Objects objects) {
                    jiabanbut.setVisibility(View.GONE);
                    jiabanlayout.setVisibility(View.VISIBLE);
                    putongshangban.setVisibility(View.GONE);
                    dialog.dismiss();
                }
                @Override
                public void onCancleClick(Objects objects) {
                    dialog.dismiss();
                }
            });

        }
    }


    public void showMore(View morView) {


        ViewGroup popview = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.popwin_kaoqin, null);
        final PopWinWithList popWinWithList = new PopWinWithList(getActivity(), popview);
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                switch (v.getId()) {


                    case R.id.guanli_click:
//                        bundle.putInt(Constant.KAOQIN_PAGE, Constant.HUIBAO_GUANLI);
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), KaoqinglFragment.class.getName(), bundle));
                        break;
                    case R.id.tongji_click:

//                        bundle.putInt(Constant.KAOQIN_PAGE, Constant.HUIBAO_TONGJI);
                        break;

                }
                popWinWithList.hide();
//                FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), PublichuibaoFragment.class.getName(), bundle));
            }
        };
        popview.findViewById(R.id.guanli_click).setOnClickListener(clicListener);
        popview.findViewById(R.id.tongji_click).setOnClickListener(clicListener);

        popWinWithList.show(morView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
