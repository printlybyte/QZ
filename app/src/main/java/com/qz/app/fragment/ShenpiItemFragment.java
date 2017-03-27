package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qz.app.MainActivity;
import com.qz.app.R;
import com.qz.app.adapter.ItemClickListener;
import com.qz.app.adapter.Selection2Adapter;
import com.qz.app.adapter.SelectionAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.control.FragmentXlistViewController;
import com.qz.app.control.RenwuListBiz;
import com.qz.app.control.ShenpiListBiz;
import com.qz.app.entity.Selection_item;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.DateScrollerDialog;
import com.qz.app.view.xListView.XListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ShenpiItemFragment extends BaseFragment {
    private TextView releasetimebt;
    private TextView filterbt;
    private TextView searchbt;
    private FrameLayout search_condition;
    private int selectNum = -1;
    private int searchCheckNum;
    private View typeCheckView;
    private View filterVew;
    private ShenpiListBiz biz;
    private String keword;
    private ViewGroup rootView;
    private int statefilter = 2;
    private ViewGroup activityRoot;
    private View filterlayout;
    private int type;


    private int flow_id = 0;
    private int status = 3;
    private String time = "created_at";

    private String time_temp = "created_at";
    private int status_temp = 3;

    private String start_time_temp;
    private String start_time;
    private String end_time_temp;
    private String end_time;

//    private ;

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case API.REQUEST_BEGIN:
//
//                    break;
//                case API.REQUEST_SUCCESS:
//                    clearWaiting();
//
//
//                    break;
//                case API.REQUEST_FAIL:
//                    String message = "";
//                    CommonUtils.showToast(message);
//                    clearWaiting();
//                    break;
//
//            }
//
//        }
//    };

    @Override
    public void initViews(ViewGroup rootView) {
        type = getArguments().getInt(Constant.SHENPI_TYPE);
        this.rootView = rootView;
        releasetimebt = (TextView) rootView.findViewById(R.id.releasetime_click);
        filterlayout = rootView.findViewById(R.id.filterlayout);
        filterbt = (TextView) rootView.findViewById(R.id.filter_click);
        searchbt = (TextView) rootView.findViewById(R.id.search_click);
        search_condition = (FrameLayout) rootView.findViewById(R.id.search_condition);
        biz = new ShenpiListBiz((XListView) rootView.findViewById(R.id.xlistview), this, getClass().hashCode());
        activityRoot = (ViewGroup) ((MainActivity) getActivity()).findViewById(R.id.fragmentlayout);
//        switch (type) {
//            case Constant.RENWU_TYPE_FABU:
//                filterlayout.setVisibility(View.VISIBLE);
//                break;
//            case Constant.RENWU_TYPE_ZHIXING:
//                filterlayout.setVisibility(View.GONE);
//                break;
//        }
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.releasetime_click:

                        if (search_condition.getChildCount() > 0) {
                            search_condition.removeAllViews();
                        }
                        if (selectNum == 0) {
                            selectNum = -1;
                            resetTagView(-1);
                            return;
                        }
                        search_condition.addView(getTypeFilter());
                        resetTagView(0);
                        break;
                    case R.id.filter_click:
                        if (search_condition.getChildCount() > 0) {
                            search_condition.removeAllViews();
                        }
                        if (selectNum == 1) {
                            selectNum = -1;
                            resetTagView(-1);
                            return;
                        }
                        content.addView(getFilter());
//                        showfilterVew();
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
                            case Constant.SHENPI_TYPE_FABU:
                                bundle.putInt(Constant.SHENPI_SEARCH_FROM, Constant.SHENPI_SEARCH_FROM_FABU);
                                break;
                            case Constant.SHENPI_TYPE_DAIWO:
                                bundle.putInt(Constant.SHENPI_SEARCH_FROM, Constant.RENWU_SEARCH_FROM_DAIWO);
                                break;
                            case Constant.SHENPI_TYPE_SHENPI:
                                bundle.putInt(Constant.SHENPI_SEARCH_FROM, Constant.RENWU_SEARCH_FROM_SHNEPI);
                                break;
                            case Constant.SHENPI_TYPE_CHAOSONG:
                                bundle.putInt(Constant.SHENPI_SEARCH_FROM, Constant.RENWU_SEARCH_FROM_CHAOSONG);
                                break;
                        }
                        FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), ShenpiSearchFragment.class.getName(), bundle));
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
            textView.setTextColor(getResources().getColor(R.color.commtext));
            textView.setBackgroundResource(R.drawable.transparent);
        }

    }


    public View getTypeFilter() {
        if (null == typeCheckView) {
            typeCheckView = inflater.inflate(R.layout.view_shenpitype, null);
            String types[] = getResources().getStringArray(R.array.shenpitypes);
            int typenum[] = getResources().getIntArray(R.array.shenpitypenums);
            List<Selection_item> items = new ArrayList<>();
            for (int i = 0; i < types.length; i++) {
                Selection_item item = new Selection_item();
                item.selectionName = types[i];
                item.selectioNum = typenum[i];
                if (i == 0) {
                    item.isSelected = true;
                }
                items.add(item);
            }
            GridView gridView = (GridView) typeCheckView.findViewById(R.id.selections);
            SelectionAdapter adapter = new SelectionAdapter(getContext(), items);
            gridView.setAdapter(adapter);
            adapter.setOnitemClickListener(new ItemClickListener() {
                @Override
                public void onclick(Object object) {
                    flow_id = ((Selection_item) object).selectioNum;
                    getData("");
                    resetTagView(-1);
                    search_condition.removeAllViews();
                }
            });

            typeCheckView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTagView(-1);
                    search_condition.removeAllViews();
                }
            });

        }

        return typeCheckView;
    }

    public View getFilter() {
        if (null == filterVew) {

            switch (type) {
                case Constant.SHENPI_TYPE_FABU:
                case Constant.SHENPI_TYPE_SHENPI:
                case Constant.SHENPI_TYPE_CHAOSONG:
                    final ArrayList<Selection_item> list = new ArrayList<>();
                    filterVew = inflater.inflate(R.layout.view_shenpi_filter, null);
                    GridView filterChoice = (GridView) filterVew.findViewById(R.id.filterchoice);
                    final Selection_item item = new Selection_item();
                    item.selectionName = "全部";
                    item.isSelected = true;
                    item.selectioNum = 3;
                    list.add(item);
                    Selection_item item1 = new Selection_item();
                    item1.selectionName = "审批中";
                    item1.selectioNum = 0;
                    list.add(item1);
                    Selection_item item2 = new Selection_item();
                    item2.selectionName = "审批通过";
                    item2.selectioNum = 1;
                    list.add(item2);
                    Selection_item item3 = new Selection_item();
                    item3.selectionName = "审批拒绝";
                    item3.selectioNum = 2;
                    list.add(item3);

                    final Selection2Adapter adapter = new Selection2Adapter(getContext(), list);
                    filterChoice.setAdapter(adapter);
                    adapter.setOnitemClickListener(new ItemClickListener() {
                        @Override
                        public void onclick(Object object) {
                            status_temp = ((Selection_item) object).selectioNum;
                        }
                    });

                    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                if (buttonView.getId() == R.id.radio4) {
                                    time_temp = "created_at";
                                } else if (buttonView.getId() == R.id.radio5) {
                                    time_temp = "finished_at";
                                }

                            }
                        }
                    };
                    final RadioButton radioButton1 = (RadioButton) filterVew.findViewById(R.id.radio4);
                    final RadioButton radioButton2 = (RadioButton) filterVew.findViewById(R.id.radio5);
                    radioButton1.setOnCheckedChangeListener(onCheckedChangeListener);
                    radioButton2.setOnCheckedChangeListener(onCheckedChangeListener);

                    filterVew.findViewById(R.id.reset_click).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            time_temp = "created_at";
                            status_temp = 3;
                            status = 3;
                            time = "created_at";
                            radioButton1.setChecked(true);
                            adapter.restSelectionAndUpdate(0);
                            content.removeAllViews();
                            resetTagView(-1);
                        }
                    });

                    filterVew.findViewById(R.id.ok_click).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            time = time_temp;
                            status = status_temp;
                            getData("");
                            content.removeAllViews();
                            resetTagView(-1);
                        }
                    });
                    filterVew.findViewById(R.id.leftimg).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            content.removeAllViews();
                            switch (status) {
                                case 0:
                                    adapter.restSelectionAndUpdate(1);
                                    break;

                                case 1:
                                    adapter.restSelectionAndUpdate(2);
                                    break;
                                case 2:
                                    adapter.restSelectionAndUpdate(3);
                                    break;
                                case 3:
                                    adapter.restSelectionAndUpdate(0);
                                    break;
                            }
                            status_temp = status;
                            time_temp = time;
                            if ("created_at".equals(time)) {
                                radioButton1.setChecked(true);
                            } else if ("finished_at".equals(time)) {
                                radioButton2.setChecked(true);
                            }
                            getData("");
                            content.removeAllViews();
                            resetTagView(-1);
                        }
                    });


                    break;
                case Constant.SHENPI_TYPE_DAIWO:
                    filterVew = inflater.inflate(R.layout.view_daiwoshenpifilter, null);
                    ((TextView) filterVew.findViewById(R.id.starttime)).setText(start_time);
                    ((TextView) filterVew.findViewById(R.id.endtime)).setText(end_time);

                    filterVew.findViewById(R.id.startlayout).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           QZutils.showTimer((TextView) filterVew.findViewById(R.id.starttime),getFragmentManager());
                        }
                    });
                    filterVew.findViewById(R.id.endlayout).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            QZutils.showTimer((TextView) filterVew.findViewById(R.id.endtime),getFragmentManager());

                        }
                    });

                    filterVew.findViewById(R.id.reset_click).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((TextView) filterVew.findViewById(R.id.starttime)).setText("");
                            ((TextView) filterVew.findViewById(R.id.endtime)).setText("");
                            ((TextView)filterVew.findViewById(R.id.starttime)).setTextColor(getResources().getColor(R.color.disable));
                            ((TextView)filterVew.findViewById(R.id.endtime)).setTextColor(getResources().getColor(R.color.disable));
                            filterVew.findViewById(R.id.starttime).setBackgroundResource(R.drawable.choice_normal);
                            filterVew.findViewById(R.id.endtime).setBackgroundResource(R.drawable.choice_normal);
                            start_time = "";
                            end_time = "";
                            start_time_temp = "";
                            end_time_temp = "";
                            getData("");
                            content.removeAllViews();
                            resetTagView(-1);
                        }
                    });
                    filterVew.findViewById(R.id.ok_click).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            start_time_temp = ((TextView) filterVew.findViewById(R.id.starttime)).getText().toString();
                            end_time_temp = ((TextView) filterVew.findViewById(R.id.endtime)).getText().toString();
                            start_time = start_time_temp;
                            end_time = end_time_temp;
                            getData("");
                            content.removeAllViews();
                            resetTagView(-1);
                        }
                    });
                    filterVew.findViewById(R.id.leftimg).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            content.removeAllViews();
                            resetTagView(-1);
                        }
                    });

                    break;
            }

        }
        return filterVew;
    }

    public void getData(String keystr) {
        keword = keystr;
        biz.getList(FragmentXlistViewController.REFRESH, keword, flow_id, time, status, getClass().hashCode(), type,start_time,end_time);
    }

    @Override
    public void getListData(Handler handler, int type, int listSign) {

        biz.getList(type, keword, flow_id, time, status, getClass().hashCode(), this.type,start_time,end_time);
    }

//    public void showTimer(final TextView textView) {
//
//        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
//        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
//            @Override
//            public void onOkclick(String year, String month, String day,String hour) {
//                if (!TextUtils.isEmpty(day) && day.length() == 1) {
//                    day = "0" + day;
//                }
//                textView.setText(year + "-" + month + "-" + day);
//                textView.setBackgroundResource(R.drawable.choice_selected);
//                textView.setTextColor(getResources().getColor(R.color.blue));
//            }
//
//            @Override
//            public void onCancelClick() {
//
//            }
//        });
//        String incomme_str = textView.getText().toString();
//        if (!TextUtils.isEmpty(incomme_str)) {
//            String[] strs = incomme_str.split("-");
//            dateDialog2.setDate(strs);
//        } else {
//            dateDialog2.setDate(null);
//        }
//
//        dateDialog2.show(getFragmentManager(), "DateScrollerDialog");
//
//    }

//    public void showfilterVew(){
//        filterVew = inflater.inflate(R.layout.view_renwufilter,null);
//            if(filterVew.getVisibility() == View.VISIBLE){
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_outy);
//                filterVew.setAnimation(animation);
//                animation.startNow();
//                animation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//
//                    }
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//                        filterVew.setVisibility(View.GONE);
//                        activityRoot.removeView(filterVew);
//                    }
//                });
//            }else {
//                View.OnClickListener onClickListener = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        filterVew.setVisibility(View.GONE);
//                        activityRoot.removeView(filterVew);
//                    }
//                };
//                filterVew.findViewById(R.id.leftimg).setOnClickListener(onClickListener);
//                activityRoot.addView(filterVew);
//                filterVew.setVisibility(View.VISIBLE);
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate);
//                filterVew.setAnimation(animation);
//                animation.startNow();
//            }
//    }


}
