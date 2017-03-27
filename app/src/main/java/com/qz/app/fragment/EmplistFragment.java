package com.qz.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.constant.Constant;
import com.qz.app.control.EmpListFilterController;
import com.qz.app.control.SearchFilterListController;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.TransDataEntity;
import com.qz.app.filter.SortModel;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class EmplistFragment extends BaseFragment implements View.OnClickListener {

    EmpListFilterController econtroller;

    @Override
    public void initViews(ViewGroup rootView) {
        String title = "";

        Bundle bundle = getArguments();
        try {
            String depid = bundle.getString(Constant.DEP_ID);
            title = bundle.getString(Constant.DEP_NAME);

            int type = bundle.getInt(Constant.EMPLIST_FROM);
            switch (type) {
                case Constant.EMP_FROM_SELECT_TASK_MEMBER:
                case Constant.EMP_FROM_SELECT_CHARGEMAN:
                    rootView.findViewById(R.id.rightTv).setVisibility(View.GONE);
                    rootView.findViewById(R.id.rightTv).setOnClickListener(this);
                    break;
                case Constant.EMP_FROM_SELECT_SHENPI:
                case Constant.SHENPI_CHAOSONG:
                case Constant.SHENPI_SHENPIREN:
                    rootView.findViewById(R.id.rightTv).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.rightTv).setOnClickListener(this);
                    break;
            }
            try {

                TransDataEntity dataEntity = (TransDataEntity) getArguments().getSerializable(Constant.EMP_TRANSDATA);
                if (dataEntity.MuiltChoice) {
                    rootView.findViewById(R.id.rightTv).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.rightTv).setOnClickListener(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            econtroller = new EmpListFilterController(getActivity(), this, rootView, title);
            econtroller.getEmpDatas(depid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        initTitledView(title);


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_emplist;
    }

    @Override
    public void setViews(View rootView) {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rightTv) {
            Bundle bundle = getArguments();
            ArrayList<DepAndEmp.Userjson> list = new ArrayList();
            if (getCheckitem(list)) {

                int type = bundle.getInt(Constant.EMPLIST_FROM);
                switch (type) {
                    case Constant.EMP_FROM_SELECT_CHARGEMAN:
                        EdittaskFragment.chargeman = list.get(0);
                        FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                        break;
                    case Constant.EMP_FROM_SELECT_SHENPI:
                        ShenpiyijianFragment.memebers.addAll(list);
                        FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                        break;
                    case Constant.SHENPI_SHENPIREN:
                        QingjiaFragment.memberlistdata.addAll(list);
                        FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                        break;
                    case Constant.SHENPI_CHAOSONG:
                        QingjiaFragment.copylistdata.addAll(list);
                        FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                        break;
                }
                try {
                    TransDataEntity dataEntity = (TransDataEntity) getArguments().getSerializable(Constant.EMP_TRANSDATA);
                    switch (dataEntity.type) {
                        case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
                            PublichuibaoFragment.staticchaosonglist.addAll(list);
                            BaseShenpiListFragment.copylistdata.addAll(list);
                            FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                            break;
                        case Constant.EMP_FROM_SELECT_HUIBAOTO:
                            PublichuibaoFragment.staticsendTolist.addAll(list);
                            BaseShenpiListFragment.memberlistdata.addAll(list);
                            FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                CommonUtils.showToast("请选择成员");
            }
        }
    }

    private boolean getCheckitem(ArrayList<DepAndEmp.Userjson> returnlist) {
        List list = econtroller.getList();
        if (null != list && list.size() > 0) {
            for (int a = 0; a < list.size(); a++) {
                if (list.get(a) instanceof SortModel) {
                    if (((SortModel) list.get(a)).ischecked) {
                        DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
                        userjson.id = ((SortModel) list.get(a)).rows.id;
                        userjson.name = ((SortModel) list.get(a)).rows.name;
                        userjson.face = ((SortModel) list.get(a)).rows.face;
                        returnlist.add(userjson);
                    }
                }
            }
        }
        if (returnlist.size() > 0) {
            return true;
        }
        return false;
    }
}
