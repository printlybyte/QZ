package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.DepChidAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.DepEntity;
import com.qz.app.filter.SortModel;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class DeplistFragment extends BaseFragment implements View.OnClickListener {
    private ListView chiddeplist;
    private TextView rightButton;
    private DepEntity depEntity;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    depEntity = (DepEntity) msg.obj;
                    setAdapter(depEntity.rows);
                    break;
                case API.REQUEST_FAIL:
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

    @Override
    public void initViews(ViewGroup rootView) {
        rightButton = (TextView) rootView.findViewById(R.id.okbutton);
        rightButton.setOnClickListener(this);
        try {
            String title = getArguments().getString(Constant.DEP_NAME);
            initTitledView(title);
        } catch (Exception e) {
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_deplist;
    }

    @Override
    public void setViews(View rootView) {
        chiddeplist = (ListView) rootView.findViewById(R.id.chiddeplist);
        getChildDep();
    }

    public void initViewWithEntity() {


    }

    private void setAdapter(final List<DepEntity.Children> listdata) {

        DepChidAdapter adapter = new DepChidAdapter(context, listdata, this);


        chiddeplist.setAdapter(adapter);
        chiddeplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DepEntity.Children userjson = (DepEntity.Children) listdata.get(position);
                Bundle bundle = new Bundle();
                int type = getArguments().getInt(Constant.EMPLIST_FROM);
                bundle.putInt(Constant.EMPLIST_FROM, type);
                bundle.putString(Constant.DEP_ID, userjson.id + "");
                bundle.putString(Constant.DEP_NAME, userjson.name);
                bundle.putSerializable(Constant.EMP_TRANSDATA, getArguments().getSerializable(Constant.EMP_TRANSDATA));
                FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmplistFragment.class.getName(), bundle));
            }
        });
    }

    public void getChildDep() {
        try {
            String depid = getArguments().getString(Constant.DEP_ID);
            Message message = new Message();
            message.setTarget(mHandler);
            API.getDepDatas(message, depid);
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        if (v == rightButton) {
            ArrayList childlist = new ArrayList();
            if (getCheckitem(childlist)) {
            } else {
                CommonUtils.showToast("请选择成员");
            }
        }
    }


    private boolean getCheckitem(ArrayList<DepEntity.Children> returnlist) {
        List<DepEntity.Children> list = depEntity.rows;
        if (null != list && list.size() > 0) {
            for (int a = 0; a < list.size(); a++) {
                if (list.get(a).isSlected) {
                    returnlist.add(list.get(a));
                }
            }
        }
        if (returnlist.size() > 0) {
            return true;
        }
        return false;
    }
}
