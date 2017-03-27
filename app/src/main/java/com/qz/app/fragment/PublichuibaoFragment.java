package com.qz.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qz.app.App;
import com.qz.app.R;
import com.qz.app.adapter.MemberHeadAdapter;
import com.qz.app.adapter.UploadShenpiImgAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.HuibaoDetail;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.DataManagers;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;
import com.qz.app.utils.NetWorkUtils;
import com.qz.app.utils.QZutils;
import com.qz.app.utils.UriUtils;
import com.qz.app.view.DateScrollerDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.valuesfeng.picker.utils.PicturePickerUtils;

/**
 * Created by Administrator on 2015/6/26.
 */
public class PublichuibaoFragment extends BaseFragment implements View.OnClickListener {

    private static final int COMMIT = 2;
    private static final int GET_DETAIL = 3;
    private TextView cancelbt;
    private TextView title;
    private TextView finishbt;
    private TextView huibao_data, zongjie_data, zongjie2_data, custmernum, money, stimetip, etimetip;
    private TextView stime, etime;
    private android.support.v7.widget.RecyclerView imglist, repoartTolist, copytolist;
    private int thetype;
    public static ArrayList<DepAndEmp.Userjson> staticsendTolist = new ArrayList<>();
    public static ArrayList<DepAndEmp.Userjson> staticchaosonglist = new ArrayList<>();


    private List<DepAndEmp.Userjson> chaosonglist = new ArrayList<>();
    private List<DepAndEmp.Userjson> sendTolist = new ArrayList<>();

    private UploadShenpiImgAdapter uploadImgAdapter;
    private MemberHeadAdapter reportomemberHeadAdapter, copytomemberHeadAdapter;

    private View inputdatalayout, yejielayout;
    private String huibaoId;

    public static final int REQUEST_CODE_CHOOSE = 1;
    private List<Uri> mSelected = new ArrayList<>();
    private ArrayList<LocalFileEntity> uploadlistData = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    switch (msg.arg2) {
                        case COMMIT:
                            CommonUtils.showToast("提交成功");
                            FragmentManager.popFragment(getActivity());
                            break;
                        case GET_DETAIL:
                            setView(((HuibaoDetail) msg.obj).data);
                            break;
                    }

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

    private void setView(HuibaoDetail.Data detail) {
        if (null == detail) {
            return;
        }
        stime.setText(QZutils.cutTimer(detail.starttime));
        switch (thetype) {
            case Constant.HUIBAO_RIBAO:
                zongjie_data.setText(detail.summary);
                huibao_data.setText(detail.plan);
                break;
            case Constant.HUIBAO_YEJI:
                zongjie2_data.setText(detail.summary);
                custmernum.setText(detail.customer);
                money.setText(detail.money);
                break;
            case Constant.HUIBAO_ZHOUBAO:
                etime.setText(QZutils.cutTimer(detail.endtime));
                zongjie2_data.setText(detail.summary);
                break;
            case Constant.HUIBAO_YUEBAO:
                zongjie2_data.setText(detail.summary);

                break;
        }
        setHuibaoRenList(detail);
        setChaoSongRenList(detail);
        ArrayList<LocalFileEntity> localfile = new ArrayList<>();
        String imgs[] = getStrs(detail.imgurl);
        if (null != imgs) {
            for (int i = 0; i < imgs.length; i++) {
                LocalFileEntity localFileEntity = new LocalFileEntity();
                localFileEntity.fromNet = true;
                localFileEntity.url = imgs[i];
                localfile.add(localFileEntity);
            }
        }
        uploadlistData.addAll(uploadlistData.size() - 1, localfile);
        uploadImgAdapter.notifyDataSetChanged();
    }


    private String[] getStrs(String str) {
        String[] strs = null;
        if (!TextUtils.isEmpty(str)) {
            if (str.endsWith(",")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.contains(",")) {
                strs = str.split(",");
            } else {
                strs = new String[]{str};
            }
        }

        return strs;
    }

    private void setChaoSongRenList(HuibaoDetail.Data detail) {
        ArrayList<DepAndEmp.Userjson> chaosongRenlist = new ArrayList<>();
        String[] chaosongbaoIds = getStrs(detail.receive);
        String[] chaosongNames = getStrs(detail.receive_names);
        String[] chaosongface = getStrs(detail.receive_names_face);
        if (null != chaosongbaoIds) {
            for (int i = 0; i < chaosongbaoIds.length; i++) {
                DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
                userjson.id = chaosongbaoIds[i];
                userjson.name = chaosongNames[i];
                userjson.face = chaosongface[i];
                chaosongRenlist.add(userjson);
            }
            chaosonglist.addAll(chaosonglist.size() - 1, chaosongRenlist);
            copytomemberHeadAdapter.notifyDataSetChanged();
        }
    }


    private void setHuibaoRenList(HuibaoDetail.Data detail) {
        ArrayList<DepAndEmp.Userjson> huibaoRenlist = new ArrayList<>();
        String[] huibaoIds = getStrs(detail.promoter);
        String[] huibaoNames = getStrs(detail.promoter_names);
        String[] huibaoface = getStrs(detail.promoter_names_face);
        for (int i = 0; i < huibaoIds.length; i++) {
            DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
            userjson.id = huibaoIds[i];
            userjson.name = huibaoNames[i];
            userjson.face = huibaoface[i];
            huibaoRenlist.add(userjson);
        }
        sendTolist.addAll(sendTolist.size() - 1, huibaoRenlist);
        reportomemberHeadAdapter.notifyDataSetChanged();


    }

    @Override
    public void initViews(ViewGroup rootView) {
        staticsendTolist.clear();
        staticchaosonglist.clear();
        thetype = getArguments().getInt(Constant.HUIBAO_TYPE);
        cancelbt = (TextView) rootView.findViewById(R.id.cancel_click);
        title = (TextView) rootView.findViewById(R.id.title);
        finishbt = (TextView) rootView.findViewById(R.id.finish_click);
        stime = (TextView) rootView.findViewById(R.id.stime);
        stimetip = (TextView) rootView.findViewById(R.id.stimetip);
        etimetip = (TextView) rootView.findViewById(R.id.etimetip);
        etime = (TextView) rootView.findViewById(R.id.etime);
        zongjie2_data = (TextView) rootView.findViewById(R.id.zongjie2_data);
        zongjie_data = (TextView) rootView.findViewById(R.id.zongjie_data);
        huibao_data = (TextView) rootView.findViewById(R.id.huibao_data);
        money = (TextView) rootView.findViewById(R.id.money);
        custmernum = (TextView) rootView.findViewById(R.id.custmernum);
        imglist = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.imglist);
        repoartTolist = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.repoartTolist);
        copytolist = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.copytolist);
        inputdatalayout = rootView.findViewById(R.id.inputdatalayout);
        yejielayout = rootView.findViewById(R.id.yejielayout);
        rootView.findViewById(R.id.startlayout).setOnClickListener(this);
        rootView.findViewById(R.id.endlayout).setOnClickListener(this);
        yejielayout.setVisibility(View.GONE);
        initView();
        String title = "";
        switch (thetype) {
            case Constant.HUIBAO_RIBAO:
                title = "日报";
                inputdatalayout.setVisibility(View.VISIBLE);
                zongjie2_data.setVisibility(View.GONE);
                stimetip.setText("日期");
                rootView.findViewById(R.id.endlayout).setVisibility(View.GONE);
                break;
            case Constant.HUIBAO_YEJI:
                title = "业绩";
                stimetip.setText("日期");
                rootView.findViewById(R.id.endlayout).setVisibility(View.GONE);
                yejielayout.setVisibility(View.VISIBLE);
                inputdatalayout.setVisibility(View.GONE);
                zongjie2_data.setVisibility(View.VISIBLE);
                break;
            case Constant.HUIBAO_ZHOUBAO:
                title = "周报";
                stimetip.setText("开始日期");
                zongjie2_data.setVisibility(View.VISIBLE);
                inputdatalayout.setVisibility(View.GONE);
                rootView.findViewById(R.id.endlayout).setVisibility(View.VISIBLE);
                break;
            case Constant.HUIBAO_YUEBAO:
                title = "月报";
                stimetip.setText("日期");
                rootView.findViewById(R.id.endlayout).setVisibility(View.GONE);
                zongjie2_data.setVisibility(View.VISIBLE);
                inputdatalayout.setVisibility(View.GONE);
                break;

        }

        setTitle(title);
        cancelbt.setOnClickListener(this);
        finishbt.setOnClickListener(this);


        LocalFileEntity localfile = new LocalFileEntity();
        uploadlistData.add(localfile);
        uploadImgAdapter = new UploadShenpiImgAdapter(getContext(), this, uploadlistData);
        uploadImgAdapter.setEditMode(true);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        imglist.setLayoutManager(gridLayoutManager3);
        imglist.setAdapter(uploadImgAdapter);


        DepAndEmp.Userjson user = new DepAndEmp.Userjson();
        user.name = "审批人";
        sendTolist.add(user);
        reportomemberHeadAdapter = new MemberHeadAdapter(getContext(), sendTolist, Constant.EMP_FROM_SELECT_HUIBAOTO);
        reportomemberHeadAdapter.setEditMode(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        repoartTolist.setLayoutManager(gridLayoutManager);
        repoartTolist.setAdapter(reportomemberHeadAdapter);

        DepAndEmp.Userjson user2 = new DepAndEmp.Userjson();
        user2.name = "抄送人";
        chaosonglist.add(user2);
        copytomemberHeadAdapter = new MemberHeadAdapter(getContext(), chaosonglist, Constant.EMP_FROM_SELECT_COPYTOHUIBAO);
        copytomemberHeadAdapter.setEditMode(true);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        copytolist.setLayoutManager(gridLayoutManager2);
        copytolist.setAdapter(copytomemberHeadAdapter);

        try {
            HuibaoDetail.Data detail = (HuibaoDetail.Data) getArguments().getSerializable(Constant.HUIBAO_ENTITY);
            huibaoId = detail.id;
            setView(detail);
        } catch (Exception e) {
            e.printStackTrace();
            getDetail();
        }
//        try {
//
//            String id = getArguments().getString(Constant.HUIBAO_ID);
//            if (!TextUtils.isEmpty(id)) {
//                getDetail();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_publichuibao;
    }

    @Override
    public void setViews(View rootView) {
        if (staticsendTolist.size() > 0) {
            sendTolist.addAll(sendTolist.size() - 1, staticsendTolist);
            reportomemberHeadAdapter.notifyDataSetChanged();
            staticsendTolist.clear();
        }
        if (staticchaosonglist.size() > 0) {
            chaosonglist.addAll(chaosonglist.size() - 1, staticchaosonglist);
            copytomemberHeadAdapter.notifyDataSetChanged();
            staticsendTolist.clear();
        }
    }

    public void initViewWithEntity() {
       /* zongjietv.setText(entity.zongjietv);
        huibaotv.setText(entity.huibaotv);

        zongjie_data = zongjietv.getText();
        huibao_data = huibaotv.getText();
       */


    }

    public void getDetail() {

        String id = getArguments().getString(Constant.HUIBAO_ID, "");
        if (TextUtils.isEmpty(id)) {
            return;
        }
        Message message = new Message();
        message.setTarget(handler);
        message.arg2 = GET_DETAIL;
        API.getHuiBaoDetail(message, id);

    }

    @Override
    public void onClick(View v) {
        if (v == cancelbt) {
            FragmentManager.popFragment(getActivity());
        } else if (v == finishbt) {
            commitdata();

        }
        switch (v.getId()) {
            case R.id.startlayout:
                DateScrollerDialog.showTimer(getFragmentManager(), stime, false);
                break;
            case R.id.endlayout:
                DateScrollerDialog.showTimer(getFragmentManager(), etime, false);
                break;


        }


    }

    public void commitdata() {
        final Message message = new Message();
        message.setTarget(handler);
        message.arg2 = COMMIT;
        String temp = "";

        try {
            temp = getArguments().getString(Constant.HUIBAO_ID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(temp)) {
            temp = huibaoId;
        }
        final String id = temp;
        temp = "";
        String plan = "";//只有日报有计划
        final String starttime = stime.getText().toString();
        String endtime = "";//只有周报有结束日期
        String imgurl = "";
        final String moneyStr = money.getText().toString();
        final String customer = custmernum.getText().toString();

        for (int i = 0; i < sendTolist.size() - 1; i++) {
            temp += sendTolist.get(i).id + ",";
        }
        final String promoter = temp;
        temp = "";
        for (int i = 0; i < chaosonglist.size() - 1; i++) {
            temp += chaosonglist.get(i).id + ",";
        }

        final String receive = temp;
        temp = "";
        switch (thetype) {
            case Constant.HUIBAO_RIBAO:
                temp = zongjie_data.getText().toString();
                plan = huibao_data.getText().toString();
                break;
            case Constant.HUIBAO_YEJI:
                temp = zongjie2_data.getText().toString();
                break;
            case Constant.HUIBAO_ZHOUBAO:
                temp = zongjie2_data.getText().toString();
                endtime = etime.getText().toString();
                break;
            case Constant.HUIBAO_YUEBAO:
                temp = zongjie2_data.getText().toString();
                break;
        }
        final String summary = temp;
        final String finalplan = plan;
        final String finalendtime = endtime;

        if (NetWorkUtils.isNetworkAvailable(getContext(), true)) {
            if (hasNewImg()) {
                showWaiting();
                uploadimg(0, new EdittaskFragment.UploadFinishListener() {
                    @Override
                    public void onFinish() {
                        String imgurl = "";
                        for (int i = 0; i < uploadlistData.size() - 1; i++) {
                            LocalFileEntity localFileEntity = uploadlistData.get(i);
                            imgurl += localFileEntity.url + ",";
                        }
                        if (imgurl.endsWith(",")) {

                            imgurl = imgurl.substring(0, imgurl.length() - 1);
                        }
                        clearWaiting();
                        API.commitHuiBao(message, id, thetype, promoter, receive, summary, finalplan, starttime, finalendtime, imgurl, moneyStr, customer);
                    }
                });
            } else {
                API.commitHuiBao(message, id, thetype, promoter, receive, summary, finalplan, starttime, finalendtime, imgurl, moneyStr, customer);
            }
        }


    }

    private boolean hasNewImg() {
        for (int i = 0; i < uploadlistData.size() - 1; i++) {
            if (!uploadlistData.get(i).fromNet) {
                return true;
            }
        }
        return false;
    }


    public void uploadimg(final int postion,
                          final EdittaskFragment.UploadFinishListener listener) {
        final LocalFileEntity itemfile = uploadlistData.get(postion);
        if (!itemfile.fromNet) {
            File file = new File(UriUtils.getPath(getContext(), itemfile.path));
            String TOKEN = null;
            try {
                TOKEN = DataManagers.getImgToken();
            } catch (Exception e) {
                e.printStackTrace();
            }
            App.uploadManager.put(file, "report/image/" + new Date().getTime() + ".png", TOKEN, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject res) {
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if (info.isOK()) {

                        itemfile.url = Constant.QINU_URL + res.optString("key");
                        L.v("qiniu", "qiniu", "Upload Success++++++++++++++++++++++" + itemfile.url);
                    } else {
//                                        uploadlistData.remove(num);
//                                        L.v("qiniu", "Upload Fail" + info.toString());
//                                        uploadImgAdapter.notifyDataSetChanged();
//                                        MeasureListHight(pics, uploadlistData);
                        //暂时没有进行错误之后 的处理
                    }
                    L.v("EdittaskFragment", "EdittaskFragment", postion);
                    L.v("EdittaskFragment", "EdittaskFragment", uploadlistData.size() - 2);
                    if (postion == uploadlistData.size() - 2) {
                        L.v("EditTaskFragment", "begin send messge to Server");
                        listener.onFinish();
                    } else {
                        uploadimg(postion + 1, listener);
                    }
                    L.v("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                }
            }, null);
        } else {
            uploadimg(postion + 1, listener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            mSelected = PicturePickerUtils.obtainResult(data);
            List<LocalFileEntity> datas = new ArrayList<>();
            for (Uri u : mSelected) {
                if (null != u) {
                    LocalFileEntity localFileEntity = new LocalFileEntity();
                    localFileEntity.path = u;
                    localFileEntity.fromNet = false;
                    datas.add(localFileEntity);
                }
            }
            uploadlistData.addAll(uploadlistData.size() - 1, datas);
            uploadImgAdapter.notifyDataSetChanged();
            MeasureListHight(imglist, uploadlistData);
        }
    }

    public void MeasureListHight(RecyclerView recyclerView, List datas) {
        if (datas.size() > 7) {
            int num = datas.size() / 7;
            if (datas.size() % 7 != 0) {
                num += 1;
            }
            float height = num * CommonUtils.getDpDementions(57);
            recyclerView.setMinimumHeight((int) height);
        }
    }

}
