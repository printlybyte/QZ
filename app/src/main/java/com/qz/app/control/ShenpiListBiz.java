package com.qz.app.control;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.qz.app.adapter.ShenpiAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.ShenPiEntity;
import com.qz.app.fragment.ShenpidetailFragment;
import com.qz.app.http.API;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;
import com.qz.app.view.xListView.XListView;

import java.util.ArrayList;
import java.util.List;

public class ShenpiListBiz implements OnItemClickListener {
    public static int LOAD_WITH_ANIM = 7;
    private XListView listView;
    private BaseFragment baseFragment;
    private FragmentXlistViewController mListViewController;
    private int sign;
    private int currentPage;
    private ArrayList<ShenPiEntity.Rows> mArrayList;
//	private TextView showinfotext;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case API.REQUEST_BEGIN:
                    if (msg.arg1 == LOAD_WITH_ANIM) {
                        showWaitingAnim();
                    }
                    break;

                case API.REQUEST_SUCCESS:
                    if (msg.arg1 == mListViewController.LOADMORE) {
                        setListData((ShenPiEntity) msg.obj);
                    } else if (msg.arg1 == mListViewController.REFRESH
                            || msg.arg1 == LOAD_WITH_ANIM) {
                        mArrayList.clear();
                        dissmissWaitting();
                        setListData((ShenPiEntity) msg.obj);
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

    public ShenpiListBiz(XListView listView, BaseFragment baseFragment, int sign) {
        this.listView = listView;
        this.baseFragment = baseFragment;
        mArrayList = new ArrayList<ShenPiEntity.Rows>();
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        ShenpiAdapter adapter = new ShenpiAdapter(baseFragment.getActivity(), mArrayList);
        mListViewController = new FragmentXlistViewController(baseFragment, adapter, listView, sign, mHandler);
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

    private void setListData(ShenPiEntity entity) {
        List<ShenPiEntity.Rows> RowsEntitys = null;
        if (null != entity && null != entity.rows) {
                RowsEntitys = entity.rows;
                checkNextPage((((currentPage - 1) * Constant.NET_PAGE_PER_NUM) + RowsEntitys.size()) + "", entity.totalCount);
        }
        mListViewController.addArray((ArrayList) RowsEntitys);
        mListViewController.stopLoad((this.hashCode() + sign) + "");

    }

    /**
     * @param type     动画（FindListBiz.LOAD_WITH_ANIM），更多，或刷新
     * @param listSign
     */
    public void getList(int type, String key,int flow_id, String time, int status, int listSign, int apiType,String starttime,String endtime) {
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
        switch (apiType) {
            case Constant.SHENPI_TYPE_FABU:
                API.getMyShenPi( message,  key, flow_id,  status, time , currentPage, null);
                break;
            case Constant.SHENPI_TYPE_DAIWO:
                API.getDaiwoShenPi( message,  key,flow_id, currentPage, null,starttime, endtime);
                break;
            case Constant.SHENPI_TYPE_SHENPI:
                API.getShenPi( message,  key, flow_id,  status, time , currentPage, null);
                break;
            case Constant.SHENPI_TYPE_CHAOSONG:
                API.getChaosongShenPi( message,  key, flow_id,  status, time , currentPage, null);
                break;
        }

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
            Bundle bundle = new Bundle();
            bundle.putString(Constant.SHENPI_ID, mArrayList.get((int) arg3).id);
            bundle.putInt(Constant.SHENPI_TYPE, baseFragment.getArguments().getInt(Constant.SHENPI_TYPE));
            FragmentManager.addStackFragment(baseFragment.getActivity(), BaseFragment.getInstance(baseFragment.getActivity(), ShenpidetailFragment.class.getName(), bundle));
        }
    }
    public ArrayList getList() {
        return mArrayList;
    }

}
