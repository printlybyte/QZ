package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;
import com.qz.app.view.swipemenulistview.DynamicListView;
import com.qz.app.view.swipemenulistview.StableArrayAdapter;
import com.qz.app.view.swipemenulistview.SwipeMenu;
import com.qz.app.view.swipemenulistview.SwipeMenuCreator;
import com.qz.app.view.swipemenulistview.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class DepinfoFragment extends BaseFragment {
    private static final int DEP_DEL = 10;
    private com.qz.app.view.swipemenulistview.DynamicListView swipelist;
    private final String TAG = "DepinfoFragment";
    private DepEntity entity = null;
    private StableArrayAdapter adapter;
    CommAlertDialog dialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.arg1 == DEP_DEL) {
                switch (msg.what) {
                    case API.REQUEST_BEGIN:

                        break;
                    case API.REQUEST_SUCCESS:
                        clearWaiting();
                        try {
                            entity.rows.remove(msg.arg2);
                            adapter.notifyDataSetChanged();
                            if (null != dialog) {
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            CommonUtils.showToast("删除失败，请重试");
                        }
                        break;
                    case API.REQUEST_FAIL:
                        String message = "";
                        if (null != msg.obj) {
                            message = (String) msg.obj;
                        } else {
                            message = "删除失败，请重试";
                        }
                        CommonUtils.showToast(message);
                        clearWaiting();
                        break;
                }
            } else {
                switch (msg.what) {
                    case API.REQUEST_BEGIN:

                        break;
                    case API.REQUEST_SUCCESS:
                        clearWaiting();
                        entity = (DepEntity) msg.obj;
                        initViewWithEntity((List<DepEntity.Children>) entity.rows);
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


        }
    };

    @Override
    public void initViews(ViewGroup rootView) {
        initTitledView("部门信息");
        swipelist = (com.qz.app.view.swipemenulistview.DynamicListView) rootView.findViewById(R.id.swipelist);
        rootView.findViewById(R.id.more_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more();
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(R.color.del_refuse);
                // set item width
                deleteItem.setWidth(CommonUtils.getDpDementions(70));
                // set a icon
                deleteItem.setTitle("删除");
                deleteItem.setTitleColor(getResources().getColor(R.color.white));
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        swipelist.setMenuCreator(creator);
        swipelist.setOnMenuItemClickListener(new DynamicListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                L.v(TAG, "onMenuItemClick", position + ":", index + "");
                delDep(position);
                return true;
            }
        });
//        swipelist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                L.v(TAG,"onMenuItemClick",position+":",index+"");
//                delDep(position);
//                return true;
//            }
//        });
//        boolean fromfather =false;
//        try {
//            fromfather= getArguments().getBoolean(Constant.DEP_GO_CHILD);
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//        if(fromfather){
//            initViewWithEntity((List<DepEntity.Children>) getArguments().getSerializable(Constant.DEP_CHILD_DATA));
//        }else {
        getDeptList();
//        }
//        swipelist.setAdapter(new DeplistAdapter(getActivity(),));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_depinfo;
    }

    @Override
    public void setViews(View rootView) {

    }

    public void initViewWithEntity(List<DepEntity.Children> list) {
        adapter = new StableArrayAdapter(getActivity(), list, this);

        swipelist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        swipelist.setSwipeEnabled(true);
        ArrayList<Boolean> enabled = new ArrayList<Boolean>();
        String parent = "";
        try {
            parent = getArguments().getString(Constant.DEP_PARENT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (DepEntity.Children item : list) {
            item.parentDepName =parent;
            enabled.add(true);
        }
        swipelist.setCheeseList((ArrayList<DepEntity.Children>) list);
        swipelist.setAdapter(adapter, enabled);
//        swipelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }

    public void getDeptList() {
        Message message = new Message();
        message.setTarget(handler);
        String id = "0";
        try {
            if (!TextUtils.isEmpty(getArguments().getString(Constant.DEP_ID))) {
                id = getArguments().getString(Constant.DEP_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        API.getDepDatas(message, id);

    }

    public void more() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.ADD_FROM, Constant.ADD_FROM_ADD);
        FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), DepFragment_add.class.getName(), bundle));
    }

    public void delDep(final int pos) {
        try {

            final DepEntity.Children child = entity.rows.get(pos);
            if (null == child) {
                return;
            }
            if (null != child.children && child.children.size() > 0) {
                dialog = new CommAlertDialog(getActivity());
                dialog.setTitleTv("删除提示");
                dialog.setContentInfo("本部门下还有" + child.children.size() + "个子部门，您确定要删除此部门吗？");
                dialog.setButtonsListener(new DialogButtonsListener() {
                    @Override
                    public void onOKClick(Objects objects) {
                        Message message = new Message();
                        message.setTarget(handler);
                        message.arg1 = DEP_DEL;
                        message.arg2 = pos;
                        API.delDep(message, child.id + "");
                    }

                    @Override
                    public void onCancleClick(Objects objects) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return;
            }

            dialog = new CommAlertDialog(getActivity());
            dialog.setTitleTv("删除提示");
            dialog.setContentInfo("您确定要删除此部门吗？");
            dialog.setButtonsListener(new DialogButtonsListener() {
                @Override
                public void onOKClick(Objects objects) {
                    Message message = new Message();
                    message.setTarget(handler);
                    message.arg1 = DEP_DEL;
                    message.arg2 = pos;
                    API.delDep(message, child.id + "");
                }

                @Override
                public void onCancleClick(Objects objects) {
                    dialog.dismiss();
                }
            });
            dialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
