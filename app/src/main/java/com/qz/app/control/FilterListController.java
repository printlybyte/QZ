package com.qz.app.control;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.TransDataEntity;
import com.qz.app.filter.Hanyu;
import com.qz.app.filter.PinyinComparator;
import com.qz.app.filter.SideBar;
import com.qz.app.filter.SortAdapter;
import com.qz.app.filter.SortModel;
import com.qz.app.fragment.DeplistFragment;
import com.qz.app.fragment.EdittaskFragment;
import com.qz.app.fragment.EmpdetailFragment;
import com.qz.app.fragment.EmplistFragment;
import com.qz.app.fragment.EmployeesFragment;
import com.qz.app.fragment.HuibaoFragment;
import com.qz.app.fragment.QingjiaFragment;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FilterListController {

    private static final String TAG = "FilterListController";
    private ViewGroup rootlayout, loading;
    private PinyinComparator pinyinComparator;
    private SideBar sideBar;
    private TextView dialog;

    private SortAdapter adapter;
    private ListView sortListView;
    private Context context;
    private List SourceDateList;
    private SortModel selectedItem;
    private int selectedPos;
    private int DATA_TYPE = 0;
    private boolean singleChoice;
    public static final int GET_LIST = 3;
    private Fragment fragment;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:
                    showWating();
                    break;
                case API.REQUEST_SUCCESS:
                    dismissWatting();
                    DepAndEmp depAndEmp = (DepAndEmp) msg.obj;
                    filledData(depAndEmp);
                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if (null != msg.obj) {
                        message = (String) msg.obj;
                    } else {
                        message = Resources.getSystem().getString(R.string.fail_getdata);
                    }
                    CommonUtils.showToast(message);
                    dismissWatting();
                    break;
            }
        }
    };

    public FilterListController(Context context, Fragment fragment, ViewGroup rootlayout) {
        this.context = context;
        this.rootlayout = rootlayout;
        loading = (ViewGroup) rootlayout.findViewById(R.id.waitinglayout);
        this.fragment = fragment;
        initViews();
    }


    public void updateAdapter(boolean b) {
        if (null != adapter) {
            adapter.updateListView(b);
        }
    }

    public List getList() {
        return SourceDateList;
    }

    private void initViews() {
        // 实例化汉字转拼音类
        // characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) rootlayout.findViewById(R.id.sidrbar);
        dialog = (TextView) rootlayout.findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                if (null != adapter && !TextUtils.isEmpty(s)) {
                    int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        sortListView.setSelection(position);
                    }
                }
            }
        });

        sortListView = (ListView) rootlayout.findViewById(R.id.filterlist);
        final int type = fragment.getArguments().getInt(Constant.EMP_FROM);
        sortListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (SourceDateList.get(position) instanceof DepAndEmp.Deptjson) {
                    DepAndEmp.Deptjson deptjson = (DepAndEmp.Deptjson) SourceDateList.get(position);
                    if (deptjson.is_has == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.DEP_NAME, deptjson.name);
                        bundle.putString(Constant.DEP_ID, deptjson.id + "");
                        switch (type) {
                            case Constant.EMP_FROM_HR:
                                bundle.putInt(Constant.EMPLIST_FROM, Constant.EMPLIST_FROM_NORMAL);
                                break;
                            default:
                                bundle.putInt(Constant.EMPLIST_FROM, type);
                                break;
                        }
                        try {
                            bundle.putSerializable(Constant.EMP_TRANSDATA, fragment.getArguments().getSerializable(Constant.EMP_TRANSDATA));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, DeplistFragment.class.getName(), bundle));
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.DEP_NAME, deptjson.name);
                        bundle.putString(Constant.DEP_ID, deptjson.id + "");
                        switch (type) {

                            case Constant.EMP_FROM_HR:
                                bundle.putInt(Constant.EMPLIST_FROM, Constant.EMPLIST_FROM_NORMAL);
                                break;
                            default:
                                bundle.putInt(Constant.EMPLIST_FROM, type);
                                break;

                        }

                        try {
                            bundle.putSerializable(Constant.EMP_TRANSDATA, fragment.getArguments().getSerializable(Constant.EMP_TRANSDATA));
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                        FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmplistFragment.class.getName(), bundle));
                    }
                } else if (SourceDateList.get(position) instanceof SortModel) {
                    switch (type) {
                        case Constant.EMP_FROM_SELECT_CHARGEMAN:
                            EdittaskFragment.chargeman = (DepAndEmp.Userjson) ((SortModel) SourceDateList.get(position)).userjson;
                            FragmentManager.popFragment(fragment.getActivity());
                            return;
                        case Constant.SHENPI_JIAOJIE:
                            QingjiaFragment.jiaojieren = (DepAndEmp.Userjson) ((SortModel) SourceDateList.get(position)).userjson;
                            FragmentManager.popFragment(fragment.getActivity());
                            return;

                        case Constant.TO_ME_HUIBAO:
                        case Constant.CHAOSONG_ME_HUIBAO:
                            HuibaoFragment.showemp = (DepAndEmp.Userjson) ((SortModel) SourceDateList.get(position)).userjson;
                            FragmentManager.popFragment(fragment.getActivity());
                            return;
                        case Constant.EMP_FROM_SELECT_SHENPI:
                        case Constant.SHENPI_SHENPIREN:
                        case Constant.SHENPI_CHAOSONG:
                            ((SortModel) SourceDateList.get(position)).ischecked = !((SortModel) SourceDateList.get(position)).ischecked;
                            adapter.notifyDataSetChanged();
                            return;
                        case Constant.EMP_FROM_HR:
                            DepAndEmp.Userjson userjson = (DepAndEmp.Userjson) ((SortModel) SourceDateList.get(position)).userjson;
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.ENP_DETAIL, userjson.id + "");
                            bundle.putInt(Constant.EMP_DETAIL_FROM, Constant.EMP_DETAIL_FROM_SHOW);
                            FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmpdetailFragment.class.getName(), bundle));
                            return;
                    }
                    try {
                        TransDataEntity transDataEntity = (TransDataEntity) fragment.getArguments().getSerializable(Constant.EMP_TRANSDATA);
                        if(transDataEntity.MuiltChoice){
                            ((SortModel) SourceDateList.get(position)).ischecked = !((SortModel) SourceDateList.get(position)).ischecked;
                            adapter.notifyDataSetChanged();
                        }
                    }catch (Exception e){

                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void setSingleChoice(boolean b) {
        singleChoice = b;
//        adapter.setSingleChoice(true);
    }


    /**
     * 为ListView填充数据
     *
     * @return
     */
    public List<SortModel> filledData(DepAndEmp depAndEmp) {
        if (null == depAndEmp) {
            return null;
        }
        List<DepAndEmp.Userjson> list = depAndEmp.data.userjson;
        List<SortModel> mSortList = new ArrayList<SortModel>();
        Hanyu hanyu = new Hanyu();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                SortModel sortModel = new SortModel();
//			FindItem item = list.get(i);
//			sortModel.setItem(item);
                DepAndEmp.Userjson item = list.get(i);
                sortModel.userjson = item;
                String name1 = item.name;

                if (TextUtils.isEmpty(name1)) {
                    name1 = "#";
                }
                sortModel.setName(name1);
                // 汉字转换成拼音
                String pinyin = "";
                if (!TextUtils.isEmpty(name1)) {
                    pinyin = hanyu.getStringPinYin(name1);
                }
                String sortString = " ";
                if (!TextUtils.isEmpty(pinyin)) {
                    sortString = pinyin.substring(0, 1).toUpperCase();
                }

                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }
                mSortList.add(sortModel);
            }

            SourceDateList = mSortList;
        }
        if (null == SourceDateList) {
            SourceDateList = new ArrayList<>();
        }
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        if (SourceDateList.size() > 0) {
            SourceDateList.add(0, getLeaveEmp());
            SourceDateList.add(0, getNoDep());
            SourceDateList.addAll(0, depAndEmp.data.deptjson);

        } else {
            SourceDateList.add(0, getLeaveEmp());
            SourceDateList.add(0, getNoDep());
            SourceDateList.addAll(depAndEmp.data.deptjson);
        }

        L.v(TAG, "depAndEmp.data.deptjson", depAndEmp.data.deptjson.size());
        adapter = new SortAdapter(context, SourceDateList);
        sortListView.setAdapter(adapter);
        adapter.setSingleChoice(false);
        final int type = fragment.getArguments().getInt(Constant.EMP_FROM);

        switch (type) {
            case Constant.EMP_FROM_SELECT_TASK_MEMBER:
            case Constant.SHENPI_JIAOJIE:
                adapter.setSingleChoice(false);
                adapter.updateListView(false);
                return mSortList;
            case Constant.EMP_FROM_SELECT_SHENPI:
            case Constant.SHENPI_SHENPIREN:
            case Constant.SHENPI_CHAOSONG:
                adapter.updateListView(true);
                return mSortList ;

        }
         try {
             TransDataEntity dataEntity = (TransDataEntity) fragment.getArguments().getSerializable(Constant.EMP_TRANSDATA);
             adapter.setSingleChoice(dataEntity.singleChoice);
             adapter.updateListView(dataEntity.MuiltChoice);
         }catch (Exception e){
             e.printStackTrace();
         }

        return mSortList;

    }

//    public ArrayList<DepAndEmp.Userjson> getSeletedUserInfo(){
//        ArrayList<DepAndEmp.Userjson> list = new ArrayList<>();
//        for(int a=0;a<SourceDateList.size();a++) {
//            if(SourceDateList.get(a) instanceof SortModel) {
//                if(((SortModel) SourceDateList.get(a)).ischecked) {
//                    list.add(((SortModel) SourceDateList.get(a)).userjson);
//                }
//            }
//        }
//        return list;
//    }

    public void getdatas() {
        Message msg = new Message();
        msg.arg1 = GET_LIST;
        msg.setTarget(mHandler);
        API.getDepAndEmpl(msg, "0");

    }


    public void showWating() {
        if (null != loading) {
            loading.setVisibility(View.VISIBLE);
        }
    }

    public void dismissWatting() {
        if (null != loading) {
            loading.setVisibility(View.GONE);

        }
    }

    /**
     * 添加无部门
     */
    public DepAndEmp.Deptjson getNoDep() {
        DepAndEmp.Deptjson deptjson = new DepAndEmp.Deptjson();
        deptjson.id = 0;
        deptjson.name = "无部门";
        return deptjson;
    }

    /**
     * 添加无部门
     */
    public DepAndEmp.Deptjson getLeaveEmp() {
        DepAndEmp.Deptjson deptjson = new DepAndEmp.Deptjson();
        deptjson.id = 0;
        deptjson.name = "离职员工";
        return deptjson;
    }


}
