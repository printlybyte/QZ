package com.qz.app.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

/**
 * Created by Administrator on 2015/6/26.
 */
public class TaskdiscussFragment extends BaseFragment implements View.OnClickListener {
    private TextView cancelbt;
    private TextView title;
    private TextView okbuttonbt;
    private EditText content;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    CommonUtils.showToast("评论成功");
                    content.setText("");
                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if (null != msg.obj) {
                        message = (String) msg.obj;
                    } else {
                        message = "评论失败";
                    }
                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }

        }
    };


    @Override
    public void initViews(ViewGroup rootView) {
        cancelbt = (TextView) rootView.findViewById(R.id.cancel_click);
        title = (TextView) rootView.findViewById(R.id.title);
        okbuttonbt = (TextView) rootView.findViewById(R.id.okbutton_click);
        content = (EditText) rootView.findViewById(R.id.content);
        cancelbt.setOnClickListener(this);
        okbuttonbt.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_taskdiscuss;
    }

    @Override
    public void setViews(View rootView) {

    }

    public void initViewWithEntity() {


    }

    @Override
    public void onClick(View v) {
        if (v == cancelbt) {
            FragmentManager.popFragment(getActivity());
        } else if (v == okbuttonbt) {
            publicData();
        }
    }

    public void publicData() {
        String contentstr = content.getText().toString();
        if (TextUtils.isEmpty(contentstr)) {

            CommonUtils.showToast("请输入内容");
            return;
        }
        Message message = new Message();
        message.setTarget(handler);
        String id = getArguments().getString(Constant.TASK_ID);
        API.DISCUSSTASK(message, contentstr, id);
    }


}
