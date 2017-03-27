package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.MemberHeadAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class QingjiaFragment extends BaseFragment implements View.OnClickListener {
    private static final int SUBMIT = 10;
    private static final int GET_SET =11 ;
    private TextView canel_button;
    private TextView title;
    private TextView ok_button;
    private TextView type_data;
    private ImageView name_black;
    private TextView start_data;
    private TextView end_data;
    private TextView time_data;
    private EditText reasontext_data;
    private TextView jiaojieren_data;
    private android.support.v7.widget.RecyclerView memberlist, copylist;
    private String qjkind, stime, etime, totals, explain, jiaojieren_str;
    private MemberHeadAdapter memberAdapter, copyAdapter;
    public static ArrayList<DepAndEmp.Userjson> copylistdata = new ArrayList<>();
    public ArrayList<DepAndEmp.Userjson> localcopylistdata = new ArrayList<>();
    public static ArrayList<DepAndEmp.Userjson> memberlistdata = new ArrayList<>();
    public ArrayList<DepAndEmp.Userjson> localmemberlistdata = new ArrayList<>();
    public static DepAndEmp.Userjson jiaojieren;
    public static String staticqjkind;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:
                    showWaiting();
                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    switch (msg.arg2) {
                        case SUBMIT:
                        CommonUtils.showToast("提交成功");
                        FragmentManager.popFragment(getActivity());
                            break;
                        case GET_SET:

                            break;
                    }
                    break;
                case API.REQUEST_FAIL:

                    String message = "";

                            if (null != msg.obj) {
                                message = (String) msg.obj;
                            } else {
                                switch (msg.arg2) {
                                    case SUBMIT:
                                        message = "提交失败";
                                        break;
                                    case GET_SET:
                                        message = "获取设置失败";
                                        FragmentManager.popFragment(getActivity());
                                        break;
                                }
                            }

                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }

        }
    };


    @Override
    public void initViews(ViewGroup rootView) {
        copylistdata.clear();
        memberlistdata.clear();
        jiaojieren = null;
        canel_button = (TextView) rootView.findViewById(R.id.canel_button);
        title = (TextView) rootView.findViewById(R.id.title);
        ok_button = (TextView) rootView.findViewById(R.id.ok_button);
        type_data = (TextView) rootView.findViewById(R.id.type_data);
        name_black = (ImageView) rootView.findViewById(R.id.name_black);
        start_data = (TextView) rootView.findViewById(R.id.start_data);
        end_data = (TextView) rootView.findViewById(R.id.end_data);
        time_data = (TextView) rootView.findViewById(R.id.time_data);
        reasontext_data = (EditText) rootView.findViewById(R.id.reasontext_data);
        jiaojieren_data = (TextView) rootView.findViewById(R.id.jiaojieren_data);
        rootView.findViewById(R.id.jiaojieren_click).setOnClickListener(this);
        rootView.findViewById(R.id.end_layout).setOnClickListener(this);
        rootView.findViewById(R.id.start_layout).setOnClickListener(this);
        rootView.findViewById(R.id.type_layout).setOnClickListener(this);
        ok_button.setOnClickListener(this);
        canel_button.setOnClickListener(this);


        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        memberlist = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.memberlist);
        copylist = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.copylist);
        memberlist.setLayoutManager(gridLayoutManager1);
        copylist.setLayoutManager(gridLayoutManager2);

        DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
        userjson.name = "审批人";
        localmemberlistdata.add(userjson);

        DepAndEmp.Userjson copyuserjson = new DepAndEmp.Userjson();
        copyuserjson.name = "抄送人";
        localcopylistdata.add(copyuserjson);
        memberAdapter = new MemberHeadAdapter(getContext(), localmemberlistdata, Constant.SHENPI_SHENPIREN);
        copyAdapter = new MemberHeadAdapter(getContext(), localcopylistdata, Constant.SHENPI_CHAOSONG);
        memberlist.setAdapter(memberAdapter);
        copylist.setAdapter(copyAdapter);
        memberAdapter.setEditMode(true);
        copyAdapter.setEditMode(true);
        initView();
        getDataSet();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qingjia;
    }

    @Override
    public void setViews(View rootView) {
        if (memberlistdata.size() > 0) {
            localmemberlistdata.addAll(localmemberlistdata.size() - 1, memberlistdata);
            memberAdapter.notifyDataSetChanged();
            MeasureListHight(memberlist, localmemberlistdata);
            memberlistdata.clear();
        }
        if (copylistdata.size() > 0) {
            localcopylistdata.addAll(localcopylistdata.size() - 1, copylistdata);
            copyAdapter.notifyDataSetChanged();
            MeasureListHight(copylist, localcopylistdata);
            copylistdata.clear();
        }
        if (null != jiaojieren) {
            jiaojieren_data.setText(jiaojieren.name);
        }
        type_data.setText(staticqjkind);

    }

    public void MeasureListHight(RecyclerView recyclerView, List datas) {
        if (datas.size() > 7) {
            int num = datas.size() / 7;

            if (datas.size() % 7 != 0) {
                num += 1;
            }
            float height = num * CommonUtils.getDpDementions(73);
            recyclerView.setMinimumHeight((int) height);
        }
    }


    public boolean getData() {
        qjkind = type_data.getText().toString();
        stime = start_data.getText().toString();
        etime = end_data.getText().toString();
        totals = time_data.getText().toString();
        explain = reasontext_data.getText().toString();
        jiaojieren_str = jiaojieren_data.getText().toString();
        if (TextUtils.isEmpty(qjkind)) {
            CommonUtils.showToast("请选择请假类型");
            return false;
        }
        if (TextUtils.isEmpty(stime)) {
            CommonUtils.showToast("请选择开始时间");
            return false;
        }
        if (TextUtils.isEmpty(etime)) {
            CommonUtils.showToast("请选择结束时间");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.jiaojieren_click) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.EMP_FROM, Constant.SHENPI_JIAOJIE);
            FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), EmployeesFragment.class.getName(), bundle));
        } else if (v.getId() == R.id.ok_button) {
            if (getData()) {
                qingjia();
            }
        } else if (v.getId() == R.id.cancel) {
            FragmentManager.popFragment(getActivity());
        } else if (v.getId() == R.id.start_layout) {
            QZutils.showTimer(start_data,getFragmentManager());
        } else if (v.getId() == R.id.end_layout) {
         QZutils.showTimer(end_data,getFragmentManager());
        }else if(v.getId() == R.id.type_layout) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.PUBLICK_SHENPITYPE,Constant.PUBLICK_SHENPITYPE_QINGJIA);
            bundle.putString(Constant.PUBLICK_SHENPITYPE_CURRENT_NAME,type_data.getText().toString());
            FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(),SelectionFragment.class.getName(),bundle));
        }else if(v== canel_button){
            FragmentManager.popFragment(getActivity());
        }
    }

    public void qingjia() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg2 = SUBMIT;
        String copy_list = "";
//        String copy_list_default = "";
        String modenum = "leave";
        String modeid = "1";
        String kind = "请假";
        String receid = "";
        if (null != jiaojieren) {
            receid = jiaojieren.id;
        }
        API.qingjia(message, modeid, modenum, kind, qjkind, stime, etime, totals, explain, receid, copy_list);
    }


    private void getDataSet(){
        Message message = new Message();
        message.arg2 = GET_SET;
        message.setTarget(handler);
        API.getShenpiFlow(message,"leave");
    }

//    public void showTimer(final TextView textView) {
//        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
//        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day,String hour) {
//                if (!TextUtils.isEmpty(day) && day.length() == 1) {
//                    day = "0" + day;
//                }
//                if (!TextUtils.isEmpty(month) && month.length() == 1) {
//                    month = "0" + month;
//                }
//                if (!TextUtils.isEmpty(hour) && hour.length() == 1) {
//                    hour = "0" + hour;
//                }
//                textView.setText(year + "-" + month + "-" + day);
//            }
//            @Override
//            public void onCancelClick() {
//
//            }
//        });
//        String incomme_str = textView.getText().toString();
//        if (!TextUtils.isEmpty(incomme_str)) {
//            String[] strs = incomme_str.split("-");
//            dateDialog2.setDate(strs);
//        } else {
//            dateDialog2.setDate(null);
//        }
//        dateDialog2.show(getFragmentManager(), "DateScrollerDialog");
//    }
}