package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.CommentAdapter;
import com.qz.app.adapter.UploadImgAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.Commitlist;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.entity.TaskDetailEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.GlideUtils;
import com.qz.app.utils.QZutils;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class RenwudetailFragment extends BaseFragment implements View.OnClickListener {

    private static final int GET_DETAIL = 20;
    private static final int FINISHTASK = 21;
    private CommentAdapter commentAdapter;
    private TextView rightbutton;
    private ListView listview;
    private List<Commitlist> commentEntitys;


    private ImageView headimg;
    private TextView name_data;
    private TextView time_data;
    private TextView title_data;
    private TextView chargemane_data;
    private TextView endtime_data;
    private TextView outtime_data;
    private android.support.v7.widget.RecyclerView imgsgride;
    private TextView addfile_data;
    private TextView commentNum;
    private TaskDetailEntity.Data detailData;
    private boolean subtaskHasNotFinish;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    switch (msg.arg1) {
                        case GET_DETAIL:
                            setDetailView((TaskDetailEntity) msg.obj);
                            break;
                        case FINISHTASK:
                            if (msg.arg2 == -1) {
                                CommonUtils.showToast("任务已完成");
                                statusimg.setImageResource(R.drawable.task_pass);
                                statusimg.setVisibility(View.VISIBLE);
                                finishtask.setVisibility(View.GONE);
                                addchild.setVisibility(View.GONE);
                                rightbutton.setVisibility(View.GONE);
                                break;
                            }
                            try {
                                CommonUtils.showToast("子任务已完成");
                                ((ImageView) subtasklayout.getChildAt(msg.arg2).findViewById(R.id.selection)).setImageResource(R.drawable.oksubtask_icon);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    switch (msg.arg1) {
                        case GET_DETAIL:
                            if (null != msg.obj) {
                                message = (String) msg.obj;
                            } else {
                                message = getString(R.string.fail_getdata);
                            }
                            break;
                        case FINISHTASK:
                            message = "完成任务失败";
                            break;
                    }
                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }

        }
    };
    private View addfile_layout;
    private ViewGroup subtasklayout, finishtask, addchild, discuss_click;
    private ImageView statusimg;


    @Override
    public void initViews(ViewGroup rootView) {
        initTitledView("任务详情");
        initView();
        rightbutton = (TextView) rootView.findViewById(R.id.rightbutton);
        listview = (ListView) rootView.findViewById(R.id.listview);
        finishtask = (ViewGroup) rootView.findViewById(R.id.finishtask);
        addchild = (ViewGroup) rootView.findViewById(R.id.addchild);
        discuss_click = (ViewGroup) rootView.findViewById(R.id.discuss_click);
        finishtask.setOnClickListener(this);
        addchild.setOnClickListener(this);


        commentEntitys = new ArrayList();
        commentAdapter = new CommentAdapter(getActivity(), commentEntitys);

        listview.setAdapter(commentAdapter);
        listview.addHeaderView(getViewhead());
//        xListView = (XListView) rootView.headView.findViewById(R.id.xlistview);
        rightbutton.setOnClickListener(this);
        discuss_click.setOnClickListener(this);
//        TaskDetailCommentlistBiz biz = new TaskDetailCommentlistBiz(xListView,this,getClass().hashCode());
//        xListView.addHeaderView(getViewhead());


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_renwudetail;
    }

    @Override
    public void setViews(View rootView) {
        getDetail();
    }

    private View getViewhead() {
        View headView = inflater.inflate(R.layout.head_taskdetail, null);
        headimg = (ImageView) headView.findViewById(R.id.headimg);
        statusimg = (ImageView) headView.findViewById(R.id.statusimg);
        name_data = (TextView) headView.findViewById(R.id.name_data);
        time_data = (TextView) headView.findViewById(R.id.time_data);
        title_data = (TextView) headView.findViewById(R.id.title_data);
        chargemane_data = (TextView) headView.findViewById(R.id.chargemane_data);
        endtime_data = (TextView) headView.findViewById(R.id.endtime_data);
        outtime_data = (TextView) headView.findViewById(R.id.outtime_data);
        imgsgride = (android.support.v7.widget.RecyclerView) headView.findViewById(R.id.imgsgride);
        addfile_data = (TextView) headView.findViewById(R.id.addfile_data);
        addfile_layout = headView.findViewById(R.id.addfile_layout);
        commentNum = (TextView) headView.findViewById(R.id.comment_num);
        subtasklayout = (ViewGroup) headView.findViewById(R.id.subtasklayout);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        imgsgride.setLayoutManager(gridLayoutManager2);

        return headView;
    }

    private void getDetail() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg1 = GET_DETAIL;
        String id = "";

        id = getArguments().getString(Constant.TASK_ID);
        if (!TextUtils.isEmpty(id)) {
            showWaiting();
            API.getTaskDetail(message, id);
        }
    }

    public void setDetailView(TaskDetailEntity entity) {
        if (null == entity) {
            return;
        }
        detailData = entity.data;
        if (null != entity.commitlist) {
            commentEntitys.clear();
            commentEntitys.addAll(entity.commitlist);
            commentAdapter.notifyDataSetChanged();
            commentNum.setText("评论(" + entity.commitlist.size() + ")");
        }
        subtasklayout.setVisibility(View.GONE);
        subtasklayout.removeAllViews();
        if (null != entity.subtask) {
            if (entity.subtask.size() > 0) {
                subtasklayout.setVisibility(View.VISIBLE);
                addSubtask(subtasklayout, entity.subtask);
            }
        }
        statusimg.setVisibility(View.GONE);
        if ("1".equals(detailData.status)) {
            statusimg.setImageResource(R.drawable.task_pass);
            statusimg.setVisibility(View.VISIBLE);
            finishtask.setVisibility(View.GONE);
            addchild.setVisibility(View.GONE);
            rightbutton.setVisibility(View.GONE);
        } else  {
            statusimg.setVisibility(View.GONE);
            rightbutton.setVisibility(View.VISIBLE);
        }
        TaskDetailEntity.Data data = entity.data;
        GlideUtils.setRoundImage(getContext(), data.img, R.drawable.default_head, R.drawable.default_head, headimg);
        name_data.setText(data.creater);
        time_data.setText(data.updatedate);
        title_data.setText(data.title);
        endtime_data.setText("截止日期:" + QZutils.setPinYinDate(QZutils.cutTimer(data.enddate)));
        chargemane_data.setText("负责人:" + data.liable_person);

        if (TextUtils.isEmpty(data.filename)) {
            addfile_layout.setVisibility(View.GONE);
        } else {
            addfile_data.setText("共有" + data.filesize + "个附件");
        }
        if (!"1".equals(data.status)) {
            long outtime = caculateOuttime(QZutils.cutTimer(data.enddate));
            if (outtime > 0) {
                outtime_data.setText("过期" + outtime + "天");
            }
        }
//        chargemane_data.setText(data.);
        if (!TextUtils.isEmpty(data.thumbpath) && data.thumbpath.endsWith(",")) {
            data.thumbpath = data.thumbpath.substring(0, data.thumbpath.length() - 1);

        }
        if (!TextUtils.isEmpty(data.thumbpath)) {
            String strs[] = data.thumbpath.split(",");
            ArrayList<LocalFileEntity> items = new ArrayList<>();

            for (int i = 0; i < strs.length; i++) {
                LocalFileEntity item = new LocalFileEntity();
                item.url = strs[i];
                items.add(item);
                item.fromNet = true;
            }
            UploadImgAdapter uploadImgAdapter = new UploadImgAdapter(getContext(), this, items);
            imgsgride.setAdapter(uploadImgAdapter);
        }
    }

    public long caculateOuttime(String data) {
        if (TextUtils.isEmpty(data)) {
            return -1;
        }
        Calendar calend = Calendar.getInstance();
        long now = calend.getTime().getTime();

        String dates[] = data.split("-");
        calend.set(Integer.parseInt(dates[0]), getInt(dates[1]), getInt(dates[2]));
        long endtime = calend.getTime().getTime();
        long time = now - endtime;
        if (time > 0) {
            return time = time / (24 * 60 * 60 * 1000);
        }
        return -1;
    }

    private int getInt(String num) {
        if (num.startsWith("0")) {
            num = num.substring(1);
        }
        return Integer.parseInt(num);
    }


    @Override
    public void onClick(View v) {
        if (v == rightbutton) {
            String text = rightbutton.getText().toString();
//            if ("编辑".equals(text)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.TASK_DETAIL, detailData);
            bundle.putInt(Constant.EDIT_TYPE, Constant.TASK_EDIT);
            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EdittaskFragment.class.getName(), bundle));
        } else if (v == finishtask) {
            String info = "是否将此任务标记完成?";
            if (subtaskHasNotFinish) {
                info = "还有未完成的子任务确定要完成吗?";
            }
            final CommAlertDialog dialog = new CommAlertDialog(getContext());
            dialog.setTitleTv("提示");
            dialog.setContentInfo(info);
            dialog.show();
            dialog.setButtonsListener(new DialogButtonsListener() {
                @Override
                public void onOKClick(Objects objects) {
                    finishTask(-1, detailData.id);
                    dialog.dismiss();
                }

                @Override
                public void onCancleClick(Objects objects) {
                    dialog.dismiss();
                }
            });
        } else if (v == addchild) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TASK_PARENT_ID, detailData.id);
            bundle.putInt(Constant.EDIT_TYPE, Constant.TASK_CREAT);
            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EdittaskFragment.class.getName(), bundle));
        } else if (v == discuss_click) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TASK_ID, detailData.id);
            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), TaskdiscussFragment.class.getName(), bundle));
        }
    }

    private void addSubtask(ViewGroup fatherView, List<TaskDetailEntity.Data> list) {
        for (int i = 0; i < list.size(); i++) {
            final TaskDetailEntity.Data entity = list.get(i);
            View view = inflater.inflate(R.layout.item_subtask, null);
            final ImageView checkView = (ImageView) view.findViewById(R.id.selection);
            final int temp = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.TASK_ID, entity.id);
                    bundle.putInt(Constant.EDIT_TYPE, Constant.TASK_GET_SUBTASK);
                    FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EdittaskFragment.class.getName(), bundle));
                }
            });
            if (!"1".equals(entity.status)) {
                checkView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CommAlertDialog dialog = new CommAlertDialog(getContext());
                        dialog.setTitleTv("提示");
                        dialog.setContentInfo("是否要完成此子任务");
                        dialog.show();
                        dialog.setButtonsListener(new DialogButtonsListener() {
                            @Override
                            public void onOKClick(Objects objects) {
                                checkView.setImageResource(R.drawable.selected_icon);
                                finishTask(temp, entity.id);
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancleClick(Objects objects) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                subtaskHasNotFinish = true;
            } else {
                checkView.setImageResource(R.drawable.oksubtask_icon);
                ((TextView) view.findViewById(R.id.taskname)).setTextColor(getResources().getColor(R.color.disable));
            }
            if (null != entity) {
                ((TextView) view.findViewById(R.id.taskname)).setText(entity.title);
                ((TextView) view.findViewById(R.id.person_time)).setText(entity.liable_person + "   " + QZutils.cutTimerWtihOutYear(entity.updatedate) + "    " + "");
            }
            fatherView.addView(view);
        }
    }

    public void finishTask(int postion, String id) {
        Message message = new Message();
        message.arg1 = FINISHTASK;
        message.arg2 = postion;
        message.setTarget(handler);
        API.finshTask(message, id);
    }

}
