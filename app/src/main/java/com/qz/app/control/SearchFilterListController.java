package com.qz.app.control;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.qz.app.R;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.SearchDemp;
import com.qz.app.entity.TransDataEntity;
import com.qz.app.filter.PinyinComparator;
import com.qz.app.filter.SearchSortAdapter;
import com.qz.app.filter.SortModel;
import com.qz.app.fragment.EdittaskFragment;
import com.qz.app.fragment.EmpdetailFragment;
import com.qz.app.fragment.EmployeesFragment;
import com.qz.app.fragment.HuibaoFragment;
import com.qz.app.fragment.QingjiaFragment;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.DepCompare;
import com.qz.app.utils.FragmentManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchFilterListController {

    private static final String TAG = "FilterListController";
    private ViewGroup rootlayout, loading;
    private PinyinComparator pinyinComparator;
//    private SideBar sideBar;
//    private TextView dialog;

    private SearchSortAdapter adapter;
    private ListView sortListView;
    private Context context;
    private List SourceDateList;
    private SortModel selectedItem;
    private int selectedPos;
    private int DATA_TYPE = 0;
    private LinearLayout searchNoInfo;
    public static final int GET_LIST = 3;
    private View listContent;
    private boolean singleChoice;
    private boolean showcheckBut;

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
                    SearchDemp depAndEmp = (SearchDemp) msg.obj;
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

    private Fragment fm;

    public SearchFilterListController(Context context, Fragment fm, ViewGroup rootlayout) {
        this.context = context;
        this.rootlayout = rootlayout;
        loading = (ViewGroup) rootlayout.findViewById(R.id.waitinglayout);
        this.fm = fm;
        initViews();
    }


    public void clearList() {
        if (null != SourceDateList) {
            SourceDateList.clear();
        }
        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }

    }

    public List<SortModel> getList() {
        return SourceDateList;
    }

    private void initViews() {
        // 实例化汉字转拼音类
        // characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
//        sideBar = (SideBar) rootlayout.findViewById(R.id.sidrbar);
//        dialog = (TextView) rootlayout.findViewById(R.id.dialog);
        listContent = rootlayout.findViewById(R.id.contentlist);
        listContent.setVisibility(View.INVISIBLE);
//        sideBar.setTextView(dialog);
        searchNoInfo = (LinearLayout) rootlayout.findViewById(R.id.search_noinfo);
        // 设置右侧触摸监听
//        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                // 该字母首次出现的位置
//                if (null != adapter && !TextUtils.isEmpty(s)) {
//                    int position = adapter.getPositionForSection(s.charAt(0));
//                    if (position != -1) {
//                        sortListView.setSelection(position);
//                    }
//                }
//            }
//        });
        sortListView = (ListView) rootlayout.findViewById(R.id.filterlist);
        sortListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DepAndEmp.Userjson userjson = (DepAndEmp.Userjson) ((SortModel) SourceDateList.get(position)).userjson;
//                searchType = getArguments().getInt();
                int type = fm.getArguments().getInt(Constant.SEARCH_TYPE);

                switch (type) {
                    case Constant.EMP_FROM_SELECT_CHARGEMAN:
                        EdittaskFragment.chargeman = userjson;
                        FragmentManager.popFragmentTo(fm.getActivity(), EmployeesFragment.class.getName());
                        break;
                    case Constant.EMP_FROM_SELECT_SHENPI:
                    case Constant.SHENPI_SHENPIREN:
                    case Constant.SHENPI_CHAOSONG:
                    case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
                    case Constant.EMP_FROM_SELECT_HUIBAOTO:
                        ((SortModel) SourceDateList.get(position)).ischecked = !((SortModel) SourceDateList.get(position)).ischecked;
                        adapter.notifyDataSetChanged();
                        return;
                    case Constant.SHENPI_JIAOJIE:
                        QingjiaFragment.jiaojieren = userjson;
                        FragmentManager.popFragmentTo(fm.getActivity(), EmployeesFragment.class.getName());
                        return;
                    case Constant.TO_ME_HUIBAO:
                    case Constant.CHAOSONG_ME_HUIBAO:
                        HuibaoFragment.showemp = userjson;
                        FragmentManager.popFragmentTo(fm.getActivity(), EmployeesFragment.class.getName());
                        return;
                    case Constant.EMP_FROM_HR:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.ENP_DETAIL, userjson.id + "");
                        bundle.putInt(Constant.EMP_DETAIL_FROM, Constant.EMP_DETAIL_FROM_SHOW);
                        FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmpdetailFragment.class.getName(), bundle));
                        return;
                }
                try {
                    TransDataEntity transDataEntity = (TransDataEntity) fm.getArguments().getSerializable(Constant.EMP_TRANSDATA);
                      if(transDataEntity.MuiltChoice){
                          ((SortModel) SourceDateList.get(position)).ischecked = !((SortModel) SourceDateList.get(position)).ischecked;
                          adapter.notifyDataSetChanged();
                          return;
                      }
                      if(transDataEntity.singleChoice){
                        switch (transDataEntity.type){


                        }
                          return;
                      }
//                    Bundle bundle = new Bundle();
//                    bundle.putString(Constant.ENP_DETAIL, userjson.id + "");
//                    bundle.putInt(Constant.EMP_DETAIL_FROM, Constant.EMP_DETAIL_FROM_SHOW);
//                    FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, EmpdetailFragment.class.getName(), bundle));
                }catch (Exception e) {


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
        if (null == searchDemp || null == searchDemp.rows || searchDemp.rows.size() <= 0) {
            showSearch_tip();
            return null;
        }
        List<SearchDemp.TRows> list = searchDemp.rows;
        List<SortModel> mSortList = new ArrayList<SortModel>();
//        Hanyu hanyu = new Hanyu();
        Collections.sort(list, new DepCompare());

        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SortModel sortModel = new SortModel();
                SearchDemp.TRows item = list.get(i);
                DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
                userjson.name = item.name;
                userjson.id = item.id;
                userjson.face = item.face;
                sortModel.userjson = userjson;
                sortModel.rows = item;
                sortModel.setName(item.name);
                if (!mSortList.contains(item.deptname)) {
                    sortModel.setSortLetters(item.deptname);
                }
                mSortList.add(sortModel);
            }
        }

//			FindItem item = list.get(i);
//			sortModel.setItem(item);
////
////
//                sortModel.rows = list.get(i);
//                DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
//                userjson.id =  sortModel.rows.id;
//                userjson.name =  sortModel.rows.name;
//                sortModel.userjson = userjson;
//                String name1 = list.get(i).name;
////			if (type == FilterManagerConsultantActivity.CONSULTANT_DATA) {
////				name1 = item.consultantName;
////			} else if (type == FilterManagerConsultantActivity.MANAGER_DATA) {
////				name1 = item.managerName;
////			}
//                if (TextUtils.isEmpty(name1)) {
//                    name1 = "#";
//                }
//                sortModel.setName(name1);
//                // 汉字转换成拼音
//                String pinyin = "";
//                if (!TextUtils.isEmpty(name1)) {
//                    pinyin = hanyu.getStringPinYin(name1);
//                }
//                String sortString = " ";
//                if (!TextUtils.isEmpty(pinyin)) {
//                    sortString = pinyin.substring(0, 1).toUpperCase();
//                }
//
//                // 正则表达式，判断首字母是否是英文字母
//                if (sortString.matches("[A-Z]")) {
//                    sortModel.setSortLetters(sortString.toUpperCase());
//                } else {
//                    sortModel.setSortLetters("#");
//                }
//                mSortList.add(sortModel);
//            }
//
        SourceDateList = mSortList;
//        } else {
//            showSearch_tip();
//        }
        if (null == SourceDateList) {
            SourceDateList = new ArrayList<>();
        }
        if (SourceDateList.size() > 0) {
            listContent.setVisibility(View.VISIBLE);
        } else {
            listContent.setVisibility(View.INVISIBLE);
        }
//        // 根据a-z进行排序源数据
//        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SearchSortAdapter(context, SourceDateList);


        int searchType = fm.getArguments().getInt(Constant.SEARCH_TYPE);
        switch (searchType) {
//            case Constant.SEARCH_FROM_ADD_DEP:
//            case Constant.EMP_FROM_CAHRGE_SELECT:
//            case Constant.EMP_FROM_CREAT_SELECT:
//            case Constant.EMP_FROM_SELECT_CHARGEMAN:
//                adapter.setSingleChoice(singleChoice);
//                break;
            case Constant.EMP_FROM_SELECT_TASK_MEMBER:
            case Constant.EMP_FROM_SELECT_SHENPI:
            case Constant.SHENPI_CHAOSONG:
            case Constant.SHENPI_SHENPIREN:
            case Constant.EMP_FROM_SELECT_COPYTOHUIBAO:
            case Constant.EMP_FROM_SELECT_HUIBAOTO:
                adapter.setShowCheckButton(true);
                break;
        }
        sortListView.setAdapter(adapter);
        return mSortList;
    }

    public void searchDatas(String content) {
        hideSearch_tip();
        if (null != adapter) {
            adapter.clearData();
        }
        Message msg = new Message();
        msg.arg1 = GET_LIST;
        msg.setTarget(mHandler);
        API.searchEmp(msg, content, null);
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


    public void showSearch_tip() {

        searchNoInfo.setVisibility(View.VISIBLE);

    }

    public void hideSearch_tip() {
        searchNoInfo.setVisibility(View.INVISIBLE);
    }

    public void setSingleChoice(boolean b) {
        showcheckBut = b;
        singleChoice = b;
    }

    public void setShowCheckButton(boolean b) {
        showcheckBut = b;
    }


}
