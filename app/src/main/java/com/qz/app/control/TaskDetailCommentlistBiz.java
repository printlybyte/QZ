package com.qz.app.control;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.qz.app.adapter.RenwuAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.NetTask;
import com.qz.app.http.API;
import com.qz.app.view.xListView.XListView;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailCommentlistBiz implements OnItemClickListener {
    public static int LOAD_WITH_ANIM = 7;
    private XListView listView;
    private BaseFragment baseFragment;
    private FragmentXlistViewController mListViewController;
    private int sign;
    private int currentPage;
    private ArrayList<NetTask> mArrayList;
//	private TextView showinfotext;

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case API.REQUEST_BEGIN:
                    if (msg.arg1 == LOAD_WITH_ANIM) {
                        showWaitingAnim();
                    }
                    break;

                case API.REQUEST_SUCCESS:
                    if (msg.arg1 == mListViewController.LOADMORE) {
                        setListData((NetTask) msg.obj);
                    } else if (msg.arg1 == mListViewController.REFRESH
                            || msg.arg1 == LOAD_WITH_ANIM) {
                        mArrayList.clear();
                        dissmissWaitting();
                        setListData((NetTask) msg.obj);
                    }

                    break;
                case API.REQUEST_FAIL:
                    mListViewController.stopLoad((this.hashCode() + sign) + "");
                    dissmissWaitting();
                    break;
                case API.REQUEST_NO_NETWORK:
                    listView.setPullLoadEnable(false);
                    mListViewController.stopLoad((this.hashCode() + sign) + "");
                    dissmissWaitting();
                    break;
                default:
                    break;
            }

        }
    };

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public TaskDetailCommentlistBiz(XListView listView, BaseFragment baseFragment, int sign) {
        this.listView = listView;
        this.baseFragment = baseFragment;
        mArrayList = new ArrayList<NetTask>();
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        RenwuAdapter adapter = new RenwuAdapter(baseFragment.getActivity(),
                mArrayList);
//		showinfotext = (TextView) ((View) listView.getParent())
//				.findViewById(R.id.showinfotext);
        mListViewController = new FragmentXlistViewController(
                baseFragment, adapter, listView, sign, mHandler);
        this.sign = sign;
        listView.setOnItemClickListener(this);
    }

    private void showWaitingAnim() {
        if (null != baseFragment) {
            baseFragment.showWaiting();
        }
    }

    private void dissmissWaitting() {
        if (null != baseFragment) {
            baseFragment.clearWaiting();
        }
    }

    private void setListData(NetTask entity) {
        List<NetTask.Rows> AskEntitys = null;
        if (null != entity && null != entity.rows) {
//			ContentPaserEntity<ArrayList<NetTask.Rows>> parser = (ContentPaserEntity<ArrayList<NetTask.Rows>>) entity.content;
            if (null != entity.rows) {
                AskEntitys = entity.rows;
                checkNextPage((((currentPage-1)* Constant.NET_PAGE_PER_NUM)+AskEntitys.size())+"", entity.totalCount);
            }
        }
        mListViewController.addArray((ArrayList) AskEntitys);
        mListViewController.stopLoad((this.hashCode() + sign) + "");

    }

    /**
     *
     * @param type
     *            动画（FindListBiz.LOAD_WITH_ANIM），更多，或刷新
     * @param listSign
     */
    public void getList(int type, String key,String time,int status,int listSign) {
        listView.setPullLoadEnable(true);
        Message message = new Message();
        message.arg1 = type;
        message.setTarget(mHandler);

        if (type != FragmentXlistViewController.LOADMORE) {
            currentPage = 1;
        }
        if (type == FragmentXlistViewController.LOADMORE) {
            currentPage += 1;
        }
        String pid="";
        API.getTaskList(message,key,time,status,pid,currentPage);

    }






    private boolean checkNextPage(String currentPage, String pageCount) {
        if (TextUtils.isEmpty(pageCount)) {
            listView.setPullLoadEnable(false);
            return false;
        }
        if (TextUtils.isEmpty(currentPage)) {
            listView.setPullLoadEnable(false);
            return false;
        }
        if (TextUtils.isDigitsOnly(pageCount)
                && TextUtils.isDigitsOnly(currentPage)) {
            int current = Integer.parseInt(currentPage);
            int count = Integer.parseInt(pageCount);
            if (current < count) {
                return true;
            }

        }
        listView.setPullLoadEnable(false);

        return false;

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (arg3 >= 0) {

        }
    }

    public ArrayList getList() {
        return mArrayList;
    }

}
