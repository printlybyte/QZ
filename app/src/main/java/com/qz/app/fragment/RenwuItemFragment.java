package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qz.app.MainActivity;
import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.control.FragmentXlistViewController;
import com.qz.app.control.RenwuListBiz;
import com.qz.app.entity.TaskDetailEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;
import com.qz.app.view.xListView.XListView;

import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class RenwuItemFragment extends BaseFragment {
    private static final int FINISHTASK =10 ;
    private TextView releasetimebt;
    private TextView filterbt;
    private TextView searchbt;
    private FrameLayout search_condition;
    private int selectNum = -1;
    private int searchCheckNum;
    private View timeCheckView;
    private View filterVew;
    private RenwuListBiz biz;
    private String keword;
    private ViewGroup rootView;
    private String sortType = "adddt";
    private int statefilter =-1;
    private ViewGroup activityRoot;
    private View filterlayout;
    private int type;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();

                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                            if (null != msg.obj) {
                                message = (String) msg.obj;
                            } else {
                                message = "完成任务失败";
                            }

                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }

        }
    };

    @Override
    public void initViews(ViewGroup rootView) {
        type = getArguments().getInt(Constant.RENWU_TYPE);
        this.rootView = rootView;
        releasetimebt = (TextView) rootView.findViewById(R.id.releasetime_click);
        filterlayout = rootView.findViewById(R.id.filterlayout);
        filterbt = (TextView) rootView.findViewById(R.id.filter_click);
        searchbt = (TextView) rootView.findViewById(R.id.search_click);
        search_condition = (FrameLayout) rootView.findViewById(R.id.search_condition);
        biz = new RenwuListBiz((XListView) rootView.findViewById(R.id.xlistview), this, getClass().hashCode());
        activityRoot = (ViewGroup) ((MainActivity) getActivity()).findViewById(R.id.fragmentlayout);
        switch (type) {
            case Constant.RENWU_TYPE_FABU:
                filterlayout.setVisibility(View.VISIBLE);
                break;
            case Constant.RENWU_TYPE_ZHIXING:
                filterlayout.setVisibility(View.GONE);
                break;
        }
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.releasetime_click:

                        if (search_condition.getChildCount() > 0) {
                            search_condition.removeAllViews();
                        }
                        if (selectNum == 0) {
                            resetTagView(-1);
                            selectNum = -1;
                            return;
                        }
                        search_condition.addView(getTimeFilter());
                        resetTagView(0);
                        break;
                    case R.id.filter_click:
                        if (search_condition.getChildCount() > 0) {
                            search_condition.removeAllViews();
                        }
                        if (selectNum == 1) {
                            resetTagView(-1);
                            selectNum = -1;
                            return;
                        }
//                        showfilterVew();
                        search_condition.addView(getFilter());
                        resetTagView(1);
                        break;
                    case R.id.search_click:
                        if (search_condition.getChildCount() > 0) {
                            search_condition.removeAllViews();
                        }
                        resetTagView(-1);
                        selectNum = -1;
                        Bundle bundle = new Bundle();
                        switch (type) {
                            case Constant.RENWU_TYPE_FABU:
                                bundle.putInt(Constant.RENWU_SEARCH_FROM, Constant.RENWU_SEARCH_FROM_RELEASE);
                                break;
                            case Constant.RENWU_TYPE_ZHIXING:
                                bundle.putInt(Constant.RENWU_SEARCH_FROM, Constant.RENWU_SEARCH_FROM_ZHIXING);
                                break;
                        }
                        FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), RenwuSearchFragment.class.getName(), bundle));
                        break;
                }
            }
        };
        releasetimebt.setOnClickListener(clicListener);
        filterbt.setOnClickListener(clicListener);
        searchbt.setOnClickListener(clicListener);
        super.initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_faburenwu;
    }

    @Override
    public void setViews(View rootView) {
        getData("");
    }

//    public void initViewWithEntity() {
//
//
//    }\

    public void resetTagView(int num) {
        selectNum = num;
        setTagSelected(false, releasetimebt);
        setTagSelected(false, filterbt);
        setTagSelected(false, searchbt);
        switch (num) {
            case 0:
                setTagSelected(true, releasetimebt);
                break;
            case 1:
                setTagSelected(true, filterbt);
                break;
            case 2:
                setTagSelected(true, searchbt);
                break;
        }
    }

    public void setTagSelected(boolean b, TextView textView) {
        if (b) {
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.filterbg);
        } else {
            textView.setTextColor(getResources().getColor(R.color.disable));
            textView.setBackgroundResource(R.drawable.transparent);
        }

    }


    public View getTimeFilter() {
        if (null == timeCheckView) {
            timeCheckView = inflater.inflate(R.layout.view_sortfilter, null);

            CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        switch (buttonView.getId()) {
                            case R.id.radio1:
                                sortType = "adddt";
                                break;
                            case R.id.radio2:
                                sortType = "updatedate";
                                break;
                            case R.id.radio3:
                                sortType = "enddate";
                                break;

                        }
                        releasetimebt.setText(buttonView.getText().toString());
                        getData("");
                        resetTagView(-1);
                        search_condition.removeAllViews();
                    }
                }
            };
            timeCheckView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTagView(-1);
                    search_condition.removeAllViews();
                }
            });
            ((RadioButton) timeCheckView.findViewById(R.id.radio1)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) timeCheckView.findViewById(R.id.radio2)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) timeCheckView.findViewById(R.id.radio3)).setOnCheckedChangeListener(onCheckedChangeListener);
        }
        return timeCheckView;
    }

    public View getFilter() {
        if (null == filterVew) {
            filterVew = inflater.inflate(R.layout.view_renwufilter2, null);
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (buttonView.getId() == R.id.radio4) {
                            statefilter = 2;

                        } else if (buttonView.getId() == R.id.radio5) {
                            statefilter = 1;

                        } else if(buttonView.getId() ==R.id.radio6) {
                            statefilter = -1;
                        }
                        getData("");
                        resetTagView(-1);
                        search_condition.removeAllViews();
                    }
                }
            };
            ((RadioButton) filterVew.findViewById(R.id.radio4)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) filterVew.findViewById(R.id.radio5)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) filterVew.findViewById(R.id.radio6)).setOnCheckedChangeListener(onCheckedChangeListener);
        }
        filterVew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTagView(-1);
                search_condition.removeAllViews();
            }
        });

        return filterVew;
    }

    public void getData(String keystr) {


        keword = keystr;
        biz.getList(FragmentXlistViewController.REFRESH, keystr, sortType, statefilter, getClass().hashCode(),type);
    }

    @Override
    public void getListData(Handler handler, int type, int listSign) {
        biz.getList(type, keword, sortType, statefilter, getClass().hashCode(),this.type);
    }






}
