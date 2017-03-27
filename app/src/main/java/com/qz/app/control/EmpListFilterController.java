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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.SearchDemp;
import com.qz.app.entity.TransDataEntity;
import com.qz.app.filter.Hanyu;
import com.qz.app.filter.PinyinComparator;
import com.qz.app.filter.SearchSortAdapter;
import com.qz.app.filter.SideBar;
import com.qz.app.filter.SortModel;
import com.qz.app.fragment.EdittaskFragment;
import com.qz.app.fragment.EmpdetailFragment;
import com.qz.app.fragment.EmployeesFragment;
import com.qz.app.fragment.HuibaoFragment;
import com.qz.app.fragment.PublichuibaoFragment;
import com.qz.app.fragment.QingjiaFragment;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EmpListFilterController {

    private static final String TAG = "FilterListController";
    private ViewGroup rootlayout, loading;
    private PinyinComparator pinyinComparator;
    private SideBar sideBar;
    private TextView dialog;

    private SearchSortAdapter adapter;
    private ListView sortListView;
    private Context context;
    private List SourceDateList;
    private SortModel selectedItem;
    private int selectedPos;
    private int DATA_TYPE = 0;
    private View showlist;
    public static final int GET_LIST = 3;
    private boolean singleChoice;
    private boolean showcheckBut;
    private Fragment fragment;
    private String title;
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
                    SearchDemp searchDemp = (SearchDemp) msg.obj;
                    filledData(searchDemp);
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

    public EmpListFilterController(Context context, Fragment fragment, ViewGroup rootlayout, String title) {
        this.context = context;
        this.rootlayout = rootlayout;
        loading = (ViewGroup) rootlayout.findViewById(R.id.waitinglayout);
        showlist = rootlayout.findViewById(R.id.showlist);
        showlist.setVisibility(View.INVISIBLE);
        this.fragment = fragment;
        this.title = title;
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
        sortListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SearchDemp.TRows item = ((SortModel) SourceDateList.get(position)).rows;
                int type = fragment.getArguments().getInt(Constant.EMPLIST_FROM);
                switch (type) {

                    case Constant.EMP_FROM_SELECT_CHARGEMAN:

                        DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
                        userjson.id = item.id;
                        userjson.face = item.face;
                        userjson.name = item.name;
                        EdittaskFragment.chargeman =userjson;
                        FragmentManager.popFragmentTo(fragment.getActivity(), EmployeesFragment.class.getName());
                        return;
                    case Constant.SHENPI_JIAOJIE:
                        DepAndEmp.Userjson userjson2 = new DepAndEmp.Userjson();
                        userjson2.id = item.id;
                        userjson2.face = item.face;
                        userjson2.name = item.name;
                        QingjiaFragment.jiaojieren =userjson2;
                        FragmentManager.popFragmentTo(fragment.getActivity(), EmployeesFragment.class.getName());
                        return;
                    case Constant.CHAOSONG_ME_HUIBAO:
                    case Constant.TO_ME_HUIBAO:
                        DepAndEmp.Userjson userjson3 = new DepAndEmp.Userjson();
                        userjson3.id = item.id;
                        userjson3.face = item.face;
                        userjson3.name = item.name;
                        HuibaoFragment.showemp =userjson3;
                        FragmentManager.popFragmentTo(fragment.getActivity(), EmployeesFragment.class.getName());
                        return;


                    case Constant.EMP_FROM_SELECT_SHENPI:
                    case Constant.SHENPI_SHENPIREN:
                    case Constant.SHENPI_CHAOSONG:
                        ((SortModel) SourceDateList.get(position)).ischecked = !((SortModel) SourceDateList.get(position)).ischecked;
                        adapter.notifyDataSetChanged();
                        return;

                    case Constant.EMPLIST_FROM_NORMAL:
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.EMP_DETAIL_FROM, Constant.EMP_DETAIL_FROM_SHOW);
                        bundle.putString(Constant.ENP_DETAIL, item.id + "");
                        FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmpdetailFragment.class.getName(), bundle));
                        return;
                }

                try {
                    TransDataEntity dataEntity = (TransDataEntity)fragment.getArguments().getSerializable(Constant.EMP_TRANSDATA);
                    if (dataEntity.MuiltChoice) {
                        ((SortModel) SourceDateList.get(position)).ischecked = !((SortModel) SourceDateList.get(position)).ischecked;
                        adapter.notifyDataSetChanged();
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.EMP_DETAIL_FROM, Constant.EMP_DETAIL_FROM_SHOW);
                        bundle.putString(Constant.ENP_DETAIL, item.id + "");
                        FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmpdetailFragment.class.getName(), bundle));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }



    /**
     * 为ListView填充数据
     *
     * @return
     */
    public List<SortModel> filledData(SearchDemp searchDemp) {
        if (null == searchDemp) {
            return null;
        }
        List<SearchDemp.TRows> list = searchDemp.rows;
        List<SortModel> mSortList = new ArrayList<SortModel>();
        Hanyu hanyu = new Hanyu();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SortModel sortModel = new SortModel();
                sortModel.rows = list.get(i);
                String name1 = list.get(i).name;
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
        if (SourceDateList.size() <= 0) {
            showlist.setVisibility(View.INVISIBLE);
        } else {
            showlist.setVisibility(View.VISIBLE);
        }
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SearchSortAdapter(context, SourceDateList);

        int type =fragment.getArguments().getInt(Constant.EMPLIST_FROM);
        switch (type){

            case Constant.EMP_FROM_SELECT_CHARGEMAN:
            case Constant.SHENPI_JIAOJIE:
                adapter.setSingleChoice(false);
                break;
            case Constant.EMPLIST_FROM_NORMAL:

                break;
            case Constant.EMP_FROM_SELECT_SHENPI:
            case Constant.SHENPI_SHENPIREN:
            case Constant.SHENPI_CHAOSONG:
                adapter.setShowCheckButton(true);
                break;
        }
        try {
            TransDataEntity dataEntity = (TransDataEntity) fragment.getArguments().getSerializable(Constant.EMP_TRANSDATA);
            adapter.setSingleChoice(dataEntity.singleChoice);
            adapter.setShowCheckButton(dataEntity.MuiltChoice);
        }catch (Exception e){
        }


        sortListView.setAdapter(adapter);
        return mSortList;

    }

    public void getEmpDatas(String depid) {
        if (null != adapter){
            adapter.clearData();
    }
        Message msg = new Message();
        msg.arg1 = GET_LIST;
        msg.setTarget(mHandler);
        if (title.equals("无部门")) {
            API.getNoDepEmp(msg);
        } else if (title.equals("离职员工")) {
            API.leaveEmpList(msg);
        } else {

            API.searchEmp(msg, null, depid);
        }

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

    public void setShowCheckButton(boolean b) {
        showcheckBut = b;
    }

}
