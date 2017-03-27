package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.UploadShenpiImgAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.GlideUtils;
import com.qz.app.utils.QZutils;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ShenpidetailFragment extends BaseFragment implements View.OnClickListener {
    private ImageView head_img, status_img;
    private TextView name_data;
    private TextView time_data;
    private TextView typename_data, commtext, rightbutton;
    private LinearLayout infotitle, flowcontent;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    setView((ShenpiDetail) msg.obj);
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
    };
    View shenpitypelayout,tixinglayout;

    @Override
    public void initViews(ViewGroup rootView) {
        head_img = (ImageView) rootView.findViewById(R.id.head_img);
        status_img = (ImageView) rootView.findViewById(R.id.status_img);
        name_data = (TextView) rootView.findViewById(R.id.name_data);
        time_data = (TextView) rootView.findViewById(R.id.time_data);
        typename_data = (TextView) rootView.findViewById(R.id.typename_data);
        commtext = (TextView) rootView.findViewById(R.id.commtext);
        rightbutton = (TextView) rootView.findViewById(R.id.rightbutton);
        infotitle = (LinearLayout) rootView.findViewById(R.id.infotitle);
        flowcontent = (LinearLayout) rootView.findViewById(R.id.flowcontent);
         shenpitypelayout = rootView.findViewById(R.id.shenpibuts);
         tixinglayout = rootView.findViewById(R.id.tixingbuts);
        int shenpitype = getArguments().getInt(Constant.SHENPI_TYPE);
        rightbutton.setVisibility(View.GONE);
        switch (shenpitype) {

            case Constant.SHENPI_TYPE_FABU:
                shenpitypelayout.setVisibility(View.GONE);
                tixinglayout.setVisibility(View.VISIBLE);
                rightbutton.setVisibility(View.VISIBLE);
                break;
            case Constant.SHENPI_TYPE_DAIWO:
            case Constant.SHENPI_TYPE_CHAOSONG:
                shenpitypelayout.setVisibility(View.VISIBLE);
                tixinglayout.setVisibility(View.GONE);

                break;
            case Constant.SHENPI_TYPE_SHENPI:
                shenpitypelayout.setVisibility(View.GONE);
                tixinglayout.setVisibility(View.GONE);
                break;
        }

        rootView.findViewById(R.id.shenpi_passbut).setOnClickListener(this);
        rootView.findViewById(R.id.shenpi_refusebut).setOnClickListener(this);
        rootView.findViewById(R.id.shenpi_chehuibut).setOnClickListener(this);
        rootView.findViewById(R.id.shenpi_tixingbut).setOnClickListener(this);
        rightbutton.setOnClickListener(this);
        initTitledView("");
        initView();
        getDetail();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shenpidetail;
    }

    @Override
    public void setViews(View rootView) {

    }

    public void initViewWithEntity() {
//        nametv.setText(entity.nametv);
//        timetv.setText(entity.timetv);
//        typenametv.setText(entity.typenametv);
//
//        name_data = nametv.getText();
//        time_data = timetv.getText();
//        typename_data = typenametv.getText();


    }

    public void getDetail() {
        Message message = new Message();
        message.setTarget(handler);
        try {
            String id = getArguments().getString(Constant.SHENPI_ID);
            API.getShenPiDetail(message, id);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    int step = 0;

    public void setView(ShenpiDetail shenpiDetail) {
        step = 0;
        String types[] = getResources().getStringArray(R.array.shenpitypes2);
        int typenum[] = getResources().getIntArray(R.array.shenpitypes2Num);
        HashMap<String, String> typesmap = new HashMap<String, String>();

        for (int i = 0; i < types.length; i++) {
            typesmap.put(typenum[i] + "", types[i]);
        }
        setTitle(typesmap.get(shenpiDetail.flow_id) + "详情");
        GlideUtils.setRoundImage(getContext(), shenpiDetail.imgurl, R.drawable.default_head, R.drawable.default_head, head_img);
        name_data.setText(shenpiDetail.uname);
        time_data.setText("发布于" + QZutils.setPinYinDateWithSecond(shenpiDetail.created_at));
        typename_data.setText(shenpiDetail.title);
        if (null != shenpiDetail.datalist && shenpiDetail.datalist.size() > 0) {
            String content = "";
            for (int a = 0; a < shenpiDetail.datalist.size(); a++) {
                ShenpiDetail.Datalist item = shenpiDetail.datalist.get(a);
                content = content + item.title + ":" + item.value + "\n";
            }
            commtext.setText(content);
        }

        if (null != shenpiDetail.flowlog) {
            String pretype = "";
            String[] nums = getResources().getStringArray(R.array.hanziNum);
            boolean hasrefuse = false;
            for (int a = 0; a < shenpiDetail.flowlog.size(); a++) {
                ShenpiDetail.Flowlog log = shenpiDetail.flowlog.get(a);
                pretype = log.thetype;
                View item = inflater.inflate(R.layout.item_shenpiflow, null);
                final View bglayout = item.findViewById(R.id.bglayout);
                TextView status = (TextView) item.findViewById(R.id.status);
                TextView name = (TextView) item.findViewById(R.id.name);
                TextView stepname = (TextView) item.findViewById(R.id.stepname);
                TextView time = (TextView) item.findViewById(R.id.time);
                ImageView statusicon = (ImageView) item.findViewById(R.id.statusicon);
                ImageView head = (ImageView) item.findViewById(R.id.head);
                GlideUtils.setRoundImage(getContext(), log.userface, R.drawable.default_head, R.drawable.default_head, head);
                name.setText(log.name);
                time.setText(QZutils.cutTimeWithoutYear(log.created_at));
                status.setText(log.thetype_name);

                if ("2".equals(pretype)) {
                    hasrefuse = true;
                    stepname.setText("步骤" + nums[step]);
                    step += 1;
                    statusicon.setImageResource(R.drawable.shenpi_refuse);
                    status.setTextColor(getResources().getColor(R.color.del_refuse));
                    bglayout.setBackgroundResource(R.drawable.commentbg);
                } else if ("1".equals(pretype)) {
                    stepname.setText("步骤" + nums[step]);
                    statusicon.setImageResource(R.drawable.shenpi_pass_icon);
                    step += 1;
                    bglayout.setBackgroundResource(R.drawable.commentbg);
                } else if ("0".equals(pretype)) {
                    stepname.setText("发起人");
                    status.setVisibility(View.GONE);
                    statusicon.setImageResource(R.drawable.shenpi_pinglun_faqi);
                    bglayout.setBackgroundResource(R.drawable.commentbg);
                } else if ("3".equals(pretype)) {
                    status.setTextColor(getResources().getColor(R.color.sign_tag));
                    statusicon.setImageResource(R.drawable.shenpi_pinglun_faqi);
                    bglayout.setBackgroundResource(R.drawable.commentbg);
                } else {
                    //暂时定为进行中
                    stepname.setText("步骤" + nums[step]);
                    status.setTextColor(getResources().getColor(R.color.wait));
                    statusicon.setImageResource(R.drawable.shenpi_ing);
                    bglayout.setBackgroundResource(R.drawable.commentbg);
                    step += 1;
                    bglayout.setBackgroundResource(R.drawable.commentbg);
                }
                if (!TextUtils.isEmpty(shenpiDetail.imgurl)) {
                    String[] imges = null;
                    if (shenpiDetail.imgurl.contains(",")) {
                        imges = shenpiDetail.imgurl.split(",");
                    } else {
                        imges = new String[]{shenpiDetail.imgurl};
                    }
                    List<LocalFileEntity> imgdatas = new ArrayList<>();
                    for (int m = 0; m < imges.length; m++) {
                        LocalFileEntity itemEntity = new LocalFileEntity();
                        itemEntity.fromNet = true;
                        itemEntity.url = imges[m];
                        imgdatas.add(itemEntity);
                    }
                    UploadShenpiImgAdapter adapter = new UploadShenpiImgAdapter(getContext(), this, (ArrayList) imgdatas);
                    RecyclerView listimgs = (RecyclerView) item.findViewById(R.id.images);
                    GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
                    listimgs.setLayoutManager(gridLayoutManager2);
                }
                flowcontent.addView(item, 0);
            }
            status_img.setVisibility(View.GONE);
            if ("1".equals(shenpiDetail.status)) {
                status_img.setVisibility(View.VISIBLE);
                shenpitypelayout.setVisibility(View.GONE);
                if (hasrefuse) {
                    status_img.setImageResource(R.drawable.task_refuse);
                    ((TextView) rootView.findViewById(R.id.shenpi_tixingbut)).setText("重新修改");
                } else {
                    tixinglayout.setVisibility(View.GONE);
                    status_img.setImageResource(R.drawable.shenpi_pass);
                }
            }
            View begin = inflater.inflate(R.layout.view_shenpi_begin, null);
            flowcontent.addView(begin);


        }
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();

        switch (v.getId()) {
            case R.id.shenpi_chehuibut:
                String strs = ((TextView) rootView.findViewById(R.id.shenpi_tixingbut)).getText().toString();
                if (strs.equals("提醒")) {
                    final CommAlertDialog dialog = new CommAlertDialog(getContext());
                    dialog.setTitleTv("提示");
                    dialog.setContentInfo("确定要撤回吗?");
                    dialog.show();
                    dialog.setButtonsListener(new DialogButtonsListener() {
                        @Override
                        public void onOKClick(Objects objects) {
//                           finishTask(-1, detailData.id);
                            dialog.dismiss();
                        }
                        @Override
                        public void onCancleClick(Objects objects) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;
            case R.id.shenpi_tixingbut:

                break;
            case R.id.shenpi_passbut:
                bundle.putString(Constant.SHENPI_ID, getArguments().getString(Constant.SHENPI_ID));
                bundle.putInt(Constant.SHENPI_DEAL_TYPE, Constant.SHENPI_DEAL_TYPE_PASS);
                FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), ShenpiyijianFragment.class.getName(), bundle));
                break;
            case R.id.shenpi_refusebut:
                bundle.putString(Constant.SHENPI_ID, getArguments().getString(Constant.SHENPI_ID));
                bundle.putInt(Constant.SHENPI_DEAL_TYPE, Constant.SHENPI_DEAL_TYPE_REFUSE);
                FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), ShenpiyijianFragment.class.getName(), bundle));
                break;


        }

    }


//    public void dealshenpi(String id,int check_type,String copy_list,){
//
//
//
//    }

}
