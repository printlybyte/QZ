package com.qz.app.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.constant.Constant;
import com.qz.app.control.FilterListController;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.HuibaoEntity;
import com.qz.app.entity.TransDataEntity;
import com.qz.app.filter.SortModel;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class EmployeesFragment extends BaseFragment implements View.OnClickListener {


    //    private ImageView moreimg;
    private ImageView searchimg;
    private TextView movetv_click;
    FilterListController fcontrol;
    private TextView cancel;
    //    public static int PAGETYPE = 1; //1=部门成员,2成员转移
    private int pageType;

    @Override
    public void initViews(ViewGroup rootView) {
        fcontrol = new FilterListController(getActivity(), this, (ViewGroup) rootView);
        pageType = getArguments().getInt(Constant.EMP_FROM,0);
        switch (pageType) {
            case Constant.EMP_FROM_SELECT_CHARGEMAN:
            case Constant.SHENPI_JIAOJIE:
                fcontrol.setSingleChoice(true);
                break;
        }
        searchimg = (ImageView) rootView.findViewById(R.id.search_img);
        movetv_click = (TextView) rootView.findViewById(R.id.movetv_click);
        cancel = (TextView) rootView.findViewById(R.id.cancel);
//        moreimg.setOnClickListener(this);
        cancel.setOnClickListener(this);
        searchimg.setOnClickListener(this);
        movetv_click.setOnClickListener(this);
        initTitledView("部门员工");
        switch (pageType) {
            case Constant.EMP_FROM_SELECT_CHARGEMAN:
            case Constant.SHENPI_JIAOJIE:
            case Constant.CHAOSONG_ME_HUIBAO:
            case Constant.TO_ME_HUIBAO:
                initTitledView("成员");
                movetv_click.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                leftimg.setVisibility(View.INVISIBLE);
                return;
            case Constant.EMP_FROM_SELECT_SHENPI:
            case Constant.SHENPI_SHENPIREN:
            case Constant.SHENPI_CHAOSONG:
            case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
            case Constant.EMP_FROM_SELECT_HUIBAOTO:
                fcontrol.updateAdapter(true);
                movetv_click.setText("确定");
                initTitledView("成员");
                cancel.setVisibility(View.VISIBLE);
                leftimg.setVisibility(View.INVISIBLE);
                movetv_click.setVisibility(View.VISIBLE);
                return;
        }



        try {
            TransDataEntity dataEntity = (TransDataEntity) getArguments().getSerializable(Constant.EMP_TRANSDATA);
            if (dataEntity.MuiltChoice) {
                fcontrol.updateAdapter(true);
                movetv_click.setText("确定");
                initTitledView("成员");
                cancel.setVisibility(View.VISIBLE);
                leftimg.setVisibility(View.INVISIBLE);
                movetv_click.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {


        }



    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_employees;
    }

    @Override
    public void setViews(View rootView) {
//        moreimg = (ImageView) rootView.findViewById(R.id.more_img);
        fcontrol.getdatas();
    }

    private boolean getCheckitem(ArrayList<DepAndEmp.Userjson> returnlist) {
        List list = fcontrol.getList();
        if (null != list && list.size() > 0) {
            for (int a = 0; a < list.size(); a++) {
                if (list.get(a) instanceof SortModel) {
                    if (((SortModel) list.get(a)).ischecked) {
                        returnlist.add(((SortModel) list.get(a)).userjson);
                    }
                }
            }
        }
        if (returnlist.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == searchimg) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.SEARCH_TYPE, pageType);
            try {
                bundle.putSerializable(Constant.EMP_TRANSDATA, getArguments().getSerializable(Constant.EMP_TRANSDATA));
            } catch (Exception e) {
                e.printStackTrace();

            }
            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), SearchFragment.class.getName(), bundle));
        } else if (v == movetv_click) {
            ArrayList<DepAndEmp.Userjson> datalist = new ArrayList<>();
            if (getCheckitem(datalist)) {
                int type = getArguments().getInt(Constant.EMP_FROM,0);
                switch (type) {
                    case Constant.EMP_FROM_SELECT_CHARGEMAN:
                        EdittaskFragment.chargeman = datalist.get(0);
                        FragmentManager.popFragment(getActivity());
                        return;
                    case Constant.EMP_FROM_SELECT_SHENPI:
                        ShenpiyijianFragment.memebers.addAll(datalist);
                        FragmentManager.popFragment(getActivity());
                        return;
                    case Constant.SHENPI_SHENPIREN:
                        QingjiaFragment.memberlistdata.addAll(datalist);
                        FragmentManager.popFragment(getActivity());
                        return;
                    case Constant.SHENPI_CHAOSONG:
                        QingjiaFragment.copylistdata.addAll(datalist);
                        FragmentManager.popFragment(getActivity());
                        return;
                }
                    try {
                        TransDataEntity dataEntity = (TransDataEntity) getArguments().getSerializable(Constant.EMP_TRANSDATA);
                        switch (dataEntity.type) {
                            case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
                                PublichuibaoFragment.staticchaosonglist.addAll(datalist);
                                BaseShenpiListFragment.copylistdata.addAll(datalist);
                                FragmentManager.popFragment(getActivity());
                                break;
                            case Constant.EMP_FROM_SELECT_HUIBAOTO:
                                PublichuibaoFragment.staticsendTolist.addAll(datalist);
                                BaseShenpiListFragment.memberlistdata.addAll(datalist);
                                FragmentManager.popFragment(getActivity());
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            } else {
                CommonUtils.showToast("请选择成员");
            }
        } else if (v == cancel) {
            FragmentManager.popFragment(getActivity());
//            final int type = getArguments().getInt(Constant.EMP_FROM);
//            switch (type) {
//                case Constant.EMP_FROM_DEP_CREAT:
//                case Constant.EMP_FROM_CAHRGE_SELECT:
//                case Constant.EMP_FROM_CREAT_SELECT:
//                case Constant.EMP_FROM_SELECT_CHARGEMAN:
//                case Constant.EMP_FROM_SELECT_TASK_MEMBER:
//                case Constant.SHENPI_SHENPIREN:
//                case Constant.SHENPI_CHAOSONG:
//                case Constant.SHENPI_JIAOJIE:
//                    break;
//            }
//            else {
//                initTitledView("部门员工");
//                fcontrol.updateAdapter(false);
//                movetv_click.setVisibility(View.INVISIBLE);
//                moreimg.setVisibility(View.VISIBLE);
//                cancel.setVisibility(View.INVISIBLE);
//                EmployeesFragment.super.leftimg.setVisibility(View.VISIBLE);
////                PAGETYPE = 1;
//            }
        }
    }
//    public void showMore(View morView) {
//        ViewGroup popview = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.popwin_emp, null);
//        final PopWinWithList popWinWithList = new PopWinWithList(getActivity(), popview);
//        View.OnClickListener clicListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.addemp_click:
//                        popWinWithList.hide();
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(Constant.EMP_DETAIL_FROM, Constant.EMP_DETAIL_FROM_ADD);
//                        FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EmpdetailFragment.class.getName(), bundle));
//                        break;
//                    case R.id.adddep_click:
////                        bundle.getInt(Constant.ADD_FROM) == Constant.ADD_FROM_ADD
//                        popWinWithList.hide();
//                        Bundle bundle2 = new Bundle();
//                        bundle2.putInt(Constant.ADD_FROM, Constant.ADD_FROM_ADD);
//                        FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), DepFragment_add.class.getName(), bundle2));
//                        break;
//                    case R.id.depimfo_click:
//                        popWinWithList.hide();
//                        FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), DepinfoFragment.class.getName()));
//                        break;
//                    case R.id.moveemp_click:
//                        fcontrol.updateAdapter(true);
//                        popWinWithList.hide();
//                        movetv_click.setVisibility(View.VISIBLE);
//                        moreimg.setVisibility(View.INVISIBLE);
//                        cancel.setVisibility(View.VISIBLE);
//                        EmployeesFragment.super.leftimg.setVisibility(View.INVISIBLE);
//                        setTitle("成员转移");
//                        PAGETYPE = 2;
//                        break;
//                }
//            }
//        };
//        popview.findViewById(R.id.addemp_click).setOnClickListener(clicListener);
//        popview.findViewById(R.id.adddep_click).setOnClickListener(clicListener);
//        popview.findViewById(R.id.depimfo_click).setOnClickListener(clicListener);
//        popview.findViewById(R.id.moveemp_click).setOnClickListener(clicListener);
//        popWinWithList.show(morView);
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (PAGETYPE == 2) {
//            initTitledView("部门员工");
//            fcontrol.updateAdapter(false);
//            movetv_click.setVisibility(View.INVISIBLE);
//            moreimg.setVisibility(View.VISIBLE);
//            cancel.setVisibility(View.INVISIBLE);
//            leftimg.setVisibility(View.VISIBLE);
//            PAGETYPE = 1;
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }
}
