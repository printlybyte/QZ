package com.qz.app.fragment;

import android.os.Bundle;
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
import com.qz.app.base.BaseShenpiListFragment;
import com.qz.app.constant.Constant;
import com.qz.app.control.SearchFilterListController;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.TransDataEntity;
import com.qz.app.filter.SortModel;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by du on 2017/2/15.
 */

public class SearchFragment extends BaseFragment {

    private EditText searchContentEdit;
    private int searchType;
    private ImageView delimg;
    @Override
    public void initViews(ViewGroup rootView) {
        searchType = getArguments().getInt(Constant.SEARCH_TYPE);
        rootView.findViewById(R.id.leftimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.popFragment(getActivity());
            }
        });
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }
    @Override
    public void setViews(View rootView) {
        final SearchFilterListController controller = new SearchFilterListController(getContext(), this, (ViewGroup) rootView);
        final TextView cancel_click = (TextView) rootView.findViewById(R.id.cancel_click);
        ImageView leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        delimg = (ImageView) rootView.findViewById(R.id.delimg);
        switch (searchType) {
            case Constant.EMP_FROM_SELECT_CHARGEMAN:
            case Constant.SHENPI_JIAOJIE:
            case Constant.CHAOSONG_ME_HUIBAO:
            case Constant.TO_ME_HUIBAO:
                controller.setSingleChoice(false);
                cancel_click.setVisibility(View.GONE);
                leftimg.setVisibility(View.VISIBLE);
                break;
            case Constant.EMP_FROM_HR:
                leftimg.setVisibility(View.GONE);
                cancel_click.setText("取消");
                break;
            case Constant.EMP_FROM_SELECT_SHENPI:
            case Constant.SHENPI_CHAOSONG:
            case Constant.SHENPI_SHENPIREN:
            case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
            case Constant.EMP_FROM_SELECT_HUIBAOTO:
            case Constant.EMP_FROM_SELECT_TASK_MEMBER:
                controller.setShowCheckButton(true);
                cancel_click.setText("选择");
                leftimg.setVisibility(View.VISIBLE);
                break;
        }
        searchContentEdit = (EditText) rootView.findViewById(R.id.search_content_data);
        searchContentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String content = searchContentEdit.getText().toString();
                controller.clearList();
                if (TextUtils.isEmpty(content)) {
                    delimg.setVisibility(View.INVISIBLE);
                    return;
                }
                delimg.setVisibility(View.VISIBLE);
                controller.searchDatas(content);
            }
        });
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cancel_click:
                        ArrayList<DepAndEmp.Userjson> userlist = new ArrayList<>();

                        switch (searchType) {
                            case Constant.EMP_FROM_HR:
                                FragmentManager.popFragment(getActivity());
                                return;
                            case Constant.EMP_FROM_SELECT_CHARGEMAN:
                                if (getCheckitem(controller, userlist)) {
                                    EdittaskFragment.chargeman = userlist.get(0);
                                    if (null == EdittaskFragment.chargeman) {
                                        CommonUtils.showToast("请选择成员");
                                        return;
                                    }
                                }
                                FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                                return;
                            case Constant.EMP_FROM_SELECT_TASK_MEMBER:
                                if (getCheckitem(controller, userlist)) {
                                    EdittaskFragment.memebers.addAll(userlist);
                                    if (null == EdittaskFragment.memebers || EdittaskFragment.memebers.size() <= 0) {
                                        CommonUtils.showToast("请选择成员");
                                        return;
                                    }
                                }
                                FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                                return;
                            case Constant.EMP_FROM_SELECT_SHENPI:
                                if (getCheckitem(controller, userlist)) {
                                    ShenpiyijianFragment.memebers.addAll(userlist);
                                    if (null == ShenpiyijianFragment.memebers || ShenpiyijianFragment.memebers.size() <= 0) {
                                        CommonUtils.showToast("请选择成员");
                                        return;
                                    }
                                }
                                FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                                return;
                            case Constant.SHENPI_CHAOSONG:
                                if (getCheckitem(controller, userlist)) {
                                    QingjiaFragment.copylistdata.addAll(userlist);
                                    if (null == QingjiaFragment.copylistdata || QingjiaFragment.copylistdata.size() <= 0) {
                                        CommonUtils.showToast("请选择成员");
                                        return;
                                    }
                                }
                                FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                                return;
                            case Constant.SHENPI_SHENPIREN:
                                if (getCheckitem(controller, userlist)) {
                                    QingjiaFragment.memberlistdata.addAll(userlist);
                                    if (null == QingjiaFragment.memberlistdata || QingjiaFragment.memberlistdata.size() <= 0) {
                                        CommonUtils.showToast("请选择成员");
                                        return;
                                    }
                                }
                                FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                                return;
//                            case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
//                                if (getCheckitem(controller, userlist)) {
//                                    PublichuibaoFragment.staticchaosonglist.addAll(userlist);
//                                    if (null == PublichuibaoFragment.staticchaosonglist || PublichuibaoFragment.staticchaosonglist.size() <= 0) {
//                                        CommonUtils.showToast("请选择成员");
//                                        return;
//                                    }
//                                }
//                                FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
//                                return;
//                            case Constant.EMP_FROM_SELECT_HUIBAOTO:
//                                if (getCheckitem(controller, userlist)) {
//                                    PublichuibaoFragment.staticsendTolist.addAll(userlist);
//                                    if (null == PublichuibaoFragment.staticsendTolist || PublichuibaoFragment.staticsendTolist.size() <= 0) {
//                                        CommonUtils.showToast("请选择成员");
//                                        return;
//                                    }
//                                }
//                                FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
//                                return;
                        }
                        try {
                            TransDataEntity dataEntity = (TransDataEntity) getArguments().getSerializable(Constant.EMP_TRANSDATA);
                            if(dataEntity.MuiltChoice&&getCheckitem(controller, userlist)) {
                                switch (dataEntity.type) {
                                    case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
                                        PublichuibaoFragment.staticchaosonglist.addAll(userlist);
                                        BaseShenpiListFragment.copylistdata.addAll(userlist);
                                        FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                                        break;
                                    case Constant.EMP_FROM_SELECT_HUIBAOTO:
                                        PublichuibaoFragment.staticsendTolist.addAll(userlist);
                                        BaseShenpiListFragment.memberlistdata.addAll(userlist);
                                        FragmentManager.popFragmentTo(getActivity(), EmployeesFragment.class.getName());
                                        break;
                                }
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.delimg:
                        searchContentEdit.setText("");
                        delimg.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
        delimg.setOnClickListener(clicListener);
        cancel_click.setOnClickListener(clicListener);
    }
    private boolean getCheckitem(SearchFilterListController control, ArrayList<DepAndEmp.Userjson> returnlist) {
        List list = control.getList();
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

}
