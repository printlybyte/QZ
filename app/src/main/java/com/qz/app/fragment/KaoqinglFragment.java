package com.qz.app.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

/**
 * 考勤管理
 * Created by Administrator on 2015/6/26.
 */
public class KaoqinglFragment extends BaseFragment implements View.OnClickListener {
    private ImageView leftimg;
    private TextView title;
    private TextView new_click;
    private ListView showlist;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();

                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if(null!=msg.obj){
                        message= (String) msg.obj;
                    } else {
                        message= getString(R.string.fail_getdata);
                    }
                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }

        }
    };



    @Override
    public void initViews(ViewGroup rootView) {
        leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        new_click = (TextView) rootView.findViewById(R.id.new_click);
        showlist = (ListView) rootView.findViewById(R.id.listview);
        new_click.setOnClickListener(this);
        initTitledView("考勤管理");
        getKaoQin();
    }

    @Override
    public void setViews(View rootView) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqingl;
    }

    public void initViewWithEntity() {


        new_click.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == new_click) {

            FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), NewkqFragment.class.getName()));
        }

    }

    private void getKaoQin(){
        Message message = new Message();
        message.setTarget(handler);
        API.getKaoQin(message);
    }

}
