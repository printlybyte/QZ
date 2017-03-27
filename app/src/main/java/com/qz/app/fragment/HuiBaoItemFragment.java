package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.qz.app.adapter.Selection3Adapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.control.FragmentXlistViewController;
import com.qz.app.control.HuibaoListBiz;
import com.qz.app.control.RenwuListBiz;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.DepEntity;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.entity.Selection_item;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;
import com.qz.app.view.DateScrollerDialog;
import com.qz.app.view.xListView.XListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/26.
 */
public class HuiBaoItemFragment extends BaseFragment {
    private TextView filter1_click;
    private TextView filterbt;
    private FrameLayout search_condition;
    private int selectNum = -1;
    private int searchCheckNum;
    private View timeCheckView;
    private View filterVew;
    private HuibaoListBiz biz;
    private String keword;
    private ViewGroup rootView;
    private int thetype = 0;

    private ViewGroup activityRoot;
    private View filterlayout;
    private int type;
    private String starttime, endtime;
    private DepAndEmp.Userjson filterMan;
    private DepEntity.Children deptName;
    private  TextView filterDeptName,filteremp_name;
//    private

    @Override
    public void initViews(ViewGroup rootView) {
        type = getArguments().getInt(Constant.HUIBAO_TYPE);
        this.rootView = rootView;
        filter1_click = (TextView) rootView.findViewById(R.id.filter1_click);
        filterlayout = rootView.findViewById(R.id.filterlayout);
        filterbt = (TextView) rootView.findViewById(R.id.filter_click);
        search_condition = (FrameLayout) rootView.findViewById(R.id.search_condition);
        biz = new HuibaoListBiz((XListView) rootView.findViewById(R.id.xlistview), this, getClass().hashCode());
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
                    case R.id.filter1_click:
                        if (search_condition.getChildCount() > 0) {
                            search_condition.removeAllViews();
                        }
                        if (selectNum == 0) {
                            resetTagView(-1);
                            selectNum = -1;
                            return;
                        }
                        search_condition.addView(getFilter1());
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
                        content.addView(getFilter2());
                        resetTagView(1);
                        break;
                }
            }
        };
        filter1_click.setOnClickListener(clicListener);
        filterbt.setOnClickListener(clicListener);
        super.initView();
        getData("");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_huibaoitem;
    }


    public void setFilterData(DepAndEmp.Userjson filterMan,DepEntity.Children selectedDept){
        if(null!=filterMan) {
            this.filterMan = filterMan;
        }
        if(null!=selectedDept) {
            this.deptName =selectedDept;
        }
        if(null!=this.filterMan) {
            filteremp_name.setText(this.filterMan.name);
        }
        if(null!=deptName) {
            filterDeptName.setText(this.deptName.name);
        }

    }


    @Override
    public void setViews(View rootView) {
        L.v("HuiBaoItemFragment","setViewssetViewssetViews");
    }

//    public void initViewWithEntity() {
//
//
//    }\

    public void resetTagView(int num) {
        selectNum = num;
        setTagSelected(false, filter1_click);
        setTagSelected(false, filterbt);
        switch (num) {
            case 0:
                setTagSelected(true, filter1_click);
                break;
            case 1:
                setTagSelected(true, filterbt);
                break;
            case 2:
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


    public View getFilter1() {
        if (null == timeCheckView) {
            timeCheckView = inflater.inflate(R.layout.view_huibaofilter1, null);
            GridView gridView = (GridView) timeCheckView.findViewById(R.id.selections);
            ArrayList<Selection_item> items = new ArrayList<>();
            String[] types = getResources().getStringArray(R.array.huibaofilter);
            for (int a = 0; a < types.length; a++) {
                Selection_item item = new Selection_item();
                if (a == 0) {
                    item.isSelected = true;
                }
                item.selectioNum = a;
                item.selectionName = types[a];
                items.add(item);
            }
            Selection3Adapter adapter = new Selection3Adapter(getContext(), items);
            gridView.setAdapter(adapter);
            adapter.setOnitemClickListener(new ItemClickListener() {
                @Override
                public void onclick(Object object) {
                    thetype = ((Selection_item) object).selectioNum;
                    search_condition.removeAllViews();
                    getData("");
                }
            });
            timeCheckView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTagView(-1);
                    search_condition.removeAllViews();
         /*   CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        switch (buttonView.getId()) {
                            case R.id.radio1:
//                                sortType = "adddt";
                                thetype = 0;
                                break;
                            case R.id.radio2:
                                thetype = 1;
//                                sortType = "updatedate";
                                break;
                            case R.id.radio3:
                                thetype = 2;
//                                sortType = "enddate";
                                break;
                            case R.id.radio4:
                                thetype = 3;
//                                sortType = "enddate";
                                break;
                            case R.id.radio5:
                                thetype = 4;
//                                sortType = "enddate";
                                break;
                        }

                        filter1_click.setText("汇报:"+buttonView.getText().toString());
                        getData("");
                        resetTagView(-1);
                        search_condition.removeAllViews();
                    }
                }
            };*/

                }
            });
           /* ((RadioButton) timeCheckView.findViewById(R.id.radio1)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) timeCheckView.findViewById(R.id.radio2)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) timeCheckView.findViewById(R.id.radio3)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) timeCheckView.findViewById(R.id.radio4)).setOnCheckedChangeListener(onCheckedChangeListener);
            ((RadioButton) timeCheckView.findViewById(R.id.radio5)).setOnCheckedChangeListener(onCheckedChangeListener);*/
        }
        return timeCheckView;
    }

    public View getFilter2() {

        if (null == filterVew) {
            filterVew = inflater.inflate(R.layout.view_huibao_filter2, null);
            ((TextView) filterVew.findViewById(R.id.starttime)).setText(starttime);
            ((TextView) filterVew.findViewById(R.id.endtime)).setText(endtime);
             filterDeptName = (TextView) filterVew.findViewById(R.id.dept_name);
             filteremp_name = (TextView) filterVew.findViewById(R.id.emp_name);

            if(type == Constant.MY_HUIBAO){
                filterVew.findViewById(R.id.otherSelection).setVisibility(View.GONE);
            }

            ((TextView) filterVew.findViewById(R.id.emp_name)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.EMP_FROM,type);

                    FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EmployeesFragment.class.getName(), bundle));
                }
            });
            ((TextView) filterVew.findViewById(R.id.dept_name)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.DEP_FROM,type);
                    FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), MoveFragment_emp.class.getName(), bundle));
                }
            });

            filterVew.findViewById(R.id.startlayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateScrollerDialog.showTimer(getFragmentManager(), (TextView) filterVew.findViewById(R.id.starttime));
                }
            });
            filterVew.findViewById(R.id.endlayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateScrollerDialog.showTimer(getFragmentManager(), (TextView) filterVew.findViewById(R.id.endtime));
                }
            });

            filterVew.findViewById(R.id.reset_click).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TextView) filterVew.findViewById(R.id.starttime)).setText("");
                    ((TextView) filterVew.findViewById(R.id.endtime)).setText("");
                    ((TextView) filterVew.findViewById(R.id.starttime)).setTextColor(getResources().getColor(R.color.disable));
                    ((TextView) filterVew.findViewById(R.id.endtime)).setTextColor(getResources().getColor(R.color.disable));
                    filterVew.findViewById(R.id.starttime).setBackgroundResource(R.drawable.choice_normal);
                    filterVew.findViewById(R.id.endtime).setBackgroundResource(R.drawable.choice_normal);
                    starttime = "";
                    endtime = "";
                    deptName = null;
                    filterMan = null;
                    filterDeptName.setText("");
                    filteremp_name.setText("");
                    getData("");
                    content.removeAllViews();
                    resetTagView(-1);
                }
            });
            filterVew.findViewById(R.id.ok_click).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starttime = ((TextView) filterVew.findViewById(R.id.starttime)).getText().toString();
                    endtime = ((TextView) filterVew.findViewById(R.id.endtime)).getText().toString();
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
        String empId = "",deptId="";
        if(null!=deptName) {
             deptId = deptName.id + "";
        }
        if(null!=filterMan) {
             empId = filterMan.id + "";
        }
        biz.getList(FragmentXlistViewController.REFRESH, keword, thetype, getClass().hashCode(), this.type, starttime, endtime,deptId,empId);
    }
    @Override
    public void getListData(Handler handler, int type, int listSign) {
        String empId = "",deptId="";
        if(null!=deptName) {
            deptId = deptName.id + "";
        }
        if(null!=filterMan) {
            empId = filterMan.id + "";
        }
        biz.getList(type, keword, thetype, getClass().hashCode(), this.type, starttime, endtime,deptId,empId);
    }


}
