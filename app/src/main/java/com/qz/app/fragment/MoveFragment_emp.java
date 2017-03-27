package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.control.GetDeptController;
import com.qz.app.entity.DepEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;

import java.util.Objects;

/**  成员转移
 * Created by Administrator on 2015/6/26.
 */
public class MoveFragment_emp extends BaseFragment {
    private ImageView leftimg;
    private TextView okbuttonbt;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                   showDialog();

                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if(null!=msg.obj){
                        message= (String) msg.obj;
                    } else {
                        message= "登录失败";
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
        okbuttonbt = (TextView) rootView.findViewById(R.id.okbutton_click);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_emp_move;
    }

    @Override
    public void setViews(View rootView) {

        final int from = getArguments().getInt(Constant.DEP_FROM);
        String title = "";
        switch (from) {
            case  Constant.TO_ME_HUIBAO:
            case  Constant.CHAOSONG_ME_HUIBAO:
                 title = "选择部门";
                 okbuttonbt.setText("完成");
                break;
        }
        initTitledView(title);
        final GetDeptController getDeptController = new GetDeptController(getActivity(), (ViewGroup) rootView, from);
        getDeptController.getDeptData("0");
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.okbutton_click:
                        switch (from) {
                            case Constant.TO_ME_HUIBAO:
                            case Constant.CHAOSONG_ME_HUIBAO:
                                DepEntity.Children children3 = getDeptController.getSelectedDep();
                                if (null == children3) {
                                    CommonUtils.showToast("请选择部门");
                                    return;
                                }
                                HuibaoFragment.selectedDept = children3;
                                FragmentManager.popFragment(getActivity());
                                break;
                        }

                        break;
                }
            }
        };
        okbuttonbt.setOnClickListener(clicListener);
    }

    public void initViewWithEntity() {

    }
    public void showDialog() {
        final CommAlertDialog dialog = new CommAlertDialog(getActivity());
        dialog.setContentInfo("添加成功!");
        dialog.setButtonColor(R.color.blue);
        dialog.setCannelBtnName("关闭");
        dialog.setOkBtnName("继续转移");
        dialog.hidtitle();
        dialog.setButtonsListener(new DialogButtonsListener() {
            @Override
            public void onOKClick(Objects objects) {
                FragmentManager.popFragment(getActivity());
                dialog.dismiss();
            }

            @Override
            public void onCancleClick(Objects objects) {
                FragmentManager.popFragment(getActivity());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

//    private void move(){
//        Message message = new Message();
//        message.setTarget(handler);
//
////        API.moveEmp(message);
//    }

}
