package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.DepEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;

import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class DepFragment_add extends BaseFragment implements View.OnClickListener {
    private TextView leftTv;
    private TextView title;
    private TextView rightTv;
    private RelativeLayout deplayout;
    private TextView name_tip;
    private EditText name_data;
    private RelativeLayout fatherlayout;
    private TextView father_tip;
    private TextView dept_data;
    private ImageView sele_dep;
    private RelativeLayout managerlayout;
    private TextView manager_tip;
    private TextView head_data;
    private ImageView sel_manager;
    public static DepEntity.Children selectedChid;
    public static DepAndEmp.Userjson selecteduser;
    public ImageView backImg;
    public Bundle bundle;
    public DepEntity.Children children;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    selectedChid = null;
                    selecteduser = null;
                    if (bundle.getInt(Constant.ADD_FROM) == Constant.ADD_FROM_ADD) {
                        showDialog();
                    } else {

                        title.setText("查看");
                        sel_manager.setVisibility(View.INVISIBLE);
                        sele_dep.setVisibility(View.INVISIBLE);
                        name_data.setEnabled(false);
                        backImg.setVisibility(View.VISIBLE);
                        rightTv.setText("编辑");
                        leftTv.setVisibility(View.INVISIBLE);
                        CommonUtils.showToast("添加成功!");
                    }
                    break;
                case API.REQUEST_FAIL:
                    selectedChid = null;
                    selecteduser = null;
                    String message = "";
                    if (null != msg.obj) {
                        message = (String) msg.obj;
                    } else {
                        message = getString(R.string.fail_getdata);
                    }
                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }
        }
    };

    private void resetView() {
        name_data.setText("");
        dept_data.setText("");
        head_data.setText("");
    }
    @Override
    public void initViews(ViewGroup rootView) {
        selectedChid = null;
        selecteduser = null;
        backImg = (ImageView) rootView.findViewById(R.id.leftimg);
        leftTv = (TextView) rootView.findViewById(R.id.leftTv);
        title = (TextView) rootView.findViewById(R.id.title);
        rightTv = (TextView) rootView.findViewById(R.id.rightTv);
        deplayout = (RelativeLayout) rootView.findViewById(R.id.deplayout);
        name_tip = (TextView) rootView.findViewById(R.id.name_tip);
        name_data = (EditText) rootView.findViewById(R.id.name_data);
        fatherlayout = (RelativeLayout) rootView.findViewById(R.id.fatherlayout);
        father_tip = (TextView) rootView.findViewById(R.id.father_tip);
        dept_data = (TextView) rootView.findViewById(R.id.dept_data);
        sele_dep = (ImageView) rootView.findViewById(R.id.sele_dep);
        managerlayout = (RelativeLayout) rootView.findViewById(R.id.managerlayout);
        manager_tip = (TextView) rootView.findViewById(R.id.manager_tip);
        head_data = (TextView) rootView.findViewById(R.id.head_data);
        sel_manager = (ImageView) rootView.findViewById(R.id.sel_manager);
        String titletext = "部门添加";
        bundle = getArguments();
        if (null != bundle) {
            if (bundle.getInt(Constant.ADD_FROM) == Constant.ADD_FROM_SHOW) {
                titletext = "查看";
                sel_manager.setVisibility(View.INVISIBLE);
                sele_dep.setVisibility(View.INVISIBLE);
                name_data.setEnabled(false);
                backImg.setVisibility(View.VISIBLE);
                rightTv.setText("编辑");
                leftTv.setVisibility(View.INVISIBLE);
                children = (DepEntity.Children) getArguments().getSerializable("children");
                fatherlayout.setEnabled(false);
                managerlayout.setEnabled(false);
                String depname = "";
                try {
                    depname = bundle.getString(Constant.DEP_PARENT_NAME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dept_data.setText(depname);
                name_data.setText(children.name);
                head_data.setText(children.headman);
            } else if (bundle.getInt(Constant.ADD_FROM) == Constant.ADD_FROM_ADD) {
                sel_manager.setVisibility(View.VISIBLE);
                sele_dep.setVisibility(View.VISIBLE);
                backImg.setVisibility(View.INVISIBLE);
                leftTv.setVisibility(View.VISIBLE);
                name_data.setEnabled(true);
            }
        }
        title.setText(titletext);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_dep;
    }

    @Override
    public void setViews(View rootView) {

        backImg.setOnClickListener(this);
        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        fatherlayout.setOnClickListener(this);
        managerlayout.setOnClickListener(this);


        try {
            if (null != selectedChid) {
                dept_data.setText(selectedChid.name);
            }
            if (null != selecteduser) {
                head_data.setText(selecteduser.name);
            }
//            DepEntity.Children children= (DepEntity.Children) getArguments().getSerializable("dep");
//            L.v("TAG","setViews",children.name);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        if (v == leftTv) {

            if (bundle.getInt(Constant.ADD_FROM) == Constant.ADD_FROM_ADD) {
                FragmentManager.popFragment(getActivity());
                return;
            }
            rightTv.setText("编辑");
            title.setText("查看");
            leftTv.setVisibility(View.INVISIBLE);
            backImg.setVisibility(View.VISIBLE);
            sele_dep.setVisibility(View.INVISIBLE);
            sel_manager.setVisibility(View.INVISIBLE);
            fatherlayout.setEnabled(false);
            managerlayout.setEnabled(false);
            name_data.setEnabled(false);
        } else if (v == rightTv) {

            if (bundle.getInt(Constant.ADD_FROM) == Constant.ADD_FROM_SHOW) {
                if (title.getText().toString().equals("查看")) {
                    title.setText("编辑");
                    rightTv.setText("完成");

                    sele_dep.setVisibility(View.VISIBLE);
                    sel_manager.setVisibility(View.VISIBLE);
                    leftTv.setVisibility(View.VISIBLE);
                    backImg.setVisibility(View.INVISIBLE);
                    fatherlayout.setEnabled(true);
                    managerlayout.setEnabled(true);
                    name_data.setEnabled(true);
                } else if (title.getText().toString().equals("编辑")) {
                    addDep();
                }

            } else if (bundle.getInt(Constant.ADD_FROM) == Constant.ADD_FROM_ADD) {
                addDep();
            }
        } else if (v == backImg) {
            FragmentManager.popFragment(getActivity());
        }
//        else if (v == fatherlayout) {
////            Bundle bundle = new Bundle();
////            bundle.putInt(Constant.DEP_FROM, Constant.DEP_FROM_SELECT_CREAT);
////            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), MoveFragment_emp.class.getName(), bundle));
//        } else if (v == managerlayout) {
////            Bundle bundle = new Bundle();
////            bundle.putInt(Constant.EMP_FROM, Constant.EMP_FROM_DEP_CREAT);
////            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EmployeesFragment.class.getName(), bundle));
//        }
    }

    public void addDep() {
        Message msg = new Message();
        msg.setTarget(mHandler);
        String name = name_data.getText().toString();
        String dep = dept_data.getText().toString();
        String manager = manager_tip.getText().toString();


        if (TextUtils.isEmpty(name)) {
            CommonUtils.showToast("请输入部门名称");
            return;
        }
        if (TextUtils.isEmpty(dep)) {
            CommonUtils.showToast("请选择上级部门");
            return;
        }
        if (TextUtils.isEmpty(manager)) {
            CommonUtils.showToast("请选择管理员");
            return;
        }
        String id = "";
        String depId = selectedChid.id + "";
        String headid = selecteduser.id;
        String sort = "";
        showWaiting();
        if (null != children) {
            id = children.id + "";
            sort = children.sort + "";
        }

        API.addDEP(msg, id, name, depId, headid, sort);
    }

    public void showDialog() {
        final CommAlertDialog dialog = new CommAlertDialog(getActivity());
        dialog.setContentInfo("添加成功!");
        dialog.setButtonColor(R.color.blue);
        dialog.setCannelBtnName("关闭");
        dialog.setOkBtnName("继续添加");
        dialog.hidtitle();
        dialog.setButtonsListener(new DialogButtonsListener() {
            @Override
            public void onOKClick(Objects objects) {
                selectedChid = null;
                selecteduser = null;
                resetView();
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


}
