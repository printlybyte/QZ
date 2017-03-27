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
import android.widget.EditText;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qz.app.App;
import com.qz.app.R;
import com.qz.app.adapter.MemberHeadAdapter;
import com.qz.app.adapter.UploadImgAdapter;
import com.qz.app.adapter.UploadShenpiImgAdapter;
import com.qz.app.base.BaseEntity;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.L;
import com.qz.app.utils.NetWorkUtils;
import com.qz.app.utils.UriUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.valuesfeng.picker.utils.PicturePickerUtils;

import static android.R.attr.id;
import static android.R.attr.type;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ShenpiyijianFragment extends BaseFragment implements View.OnClickListener {
    private static final int DEAL = 1;
    private static final int GET_TOKEN = 2;
    private TextView cancelbt;
    private TextView title;
    private TextView finishbt;
    public static final int REQUEST_CODE_CHOOSE = 1;
    ArrayList<LocalFileEntity> uploadlistData = new ArrayList<>();
    private String TOKEN;
    private List<Uri> mSelected;
    private UploadShenpiImgAdapter uploadImgAdapter;
    private RecyclerView listimgs, memberlist;
    private MemberHeadAdapter memberHeadAdapter;
    private EditText contenttext;
    public static ArrayList<DepAndEmp.Userjson> memebers = new ArrayList<>();
    private List<DepAndEmp.Userjson> memberlistData = new ArrayList<>();

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
                        case GET_TOKEN:
                            TOKEN = ((BaseEntity) msg.obj).token;
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


    @Override
    public void initViews(ViewGroup rootView) {
        cancelbt = (TextView) rootView.findViewById(R.id.cancel_click);
        title = (TextView) rootView.findViewById(R.id.title);
        finishbt = (TextView) rootView.findViewById(R.id.finish_click);
        listimgs = (RecyclerView) rootView.findViewById(R.id.listimgs);
        memberlist = (RecyclerView) rootView.findViewById(R.id.memberlist);
        contenttext = (EditText) rootView.findViewById(R.id.contenttext);
        title.setText("审批意见");
        cancelbt.setOnClickListener(this);
        finishbt.setOnClickListener(this);
        getToken();


        LocalFileEntity localfile = new LocalFileEntity();
        uploadlistData.add(localfile);
        uploadImgAdapter = new UploadShenpiImgAdapter(getContext(), this, uploadlistData);
        uploadImgAdapter.setEditMode(true);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        listimgs.setLayoutManager(gridLayoutManager2);
        listimgs.setAdapter(uploadImgAdapter);


        DepAndEmp.Userjson user = new DepAndEmp.Userjson();
        user.name = "抄送人";
        memberlistData.add(user);
        memberHeadAdapter = new MemberHeadAdapter(getContext(), memberlistData, Constant.EMP_FROM_SELECT_SHENPI);
        memberHeadAdapter.setEditMode(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        memberlist.setLayoutManager(gridLayoutManager);
        memberlist.setAdapter(memberHeadAdapter);
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shenpiyijian;
    }

    @Override
    public void setViews(View rootView) {
        if (null != memebers && memebers.size() > 0) {
            memberlistData.addAll(memberlistData.size() - 1, memebers);
            memberHeadAdapter.notifyDataSetChanged();
            memebers.clear();
        }
    }


    @Override
    public void onClick(View v) {
        if (v == cancelbt) {
            FragmentManager.popFragment(getActivity());
        } else if (v == finishbt) {
            commit();

        }

    }
//    public void finish(){
//
//        API.DealWithShenPi(message,id,check_type,contentstr,copy_list,imgfile);
//    }

    public void getToken() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg1 = GET_TOKEN;
        API.getToken(message);
    }

    String typeStr = "";
    public void commit() {
        final Message message = new Message();
        message.setTarget(handler);
        message.arg1 = DEAL;

        String memberstr = "";
        for (int a = 0; a < memebers.size(); a++) {
            memberstr += memebers.get(a).id + ",";
        }
        if (memberstr.endsWith(",")) {
            memberstr = memberstr.substring(0, memberstr.length() - 1);
        }

        final String copy_list = memberstr;
        final String imgfile = "";
        final String id = getArguments().getString(Constant.SHENPI_ID);
        final String contentstr = contenttext.getText().toString();
        final int check_type = getArguments().getInt(Constant.SHENPI_DEAL_TYPE);

//      "ok" 处理类型：通过ok拒绝no评论commit转交change
        switch (check_type) {
            case 1:
                typeStr = "ok";
                break;
            case 2:
                typeStr = "no";
                break;
            case 3:
                typeStr = "commit";
                break;
            case 4:
                typeStr = "change";
                break;
        }
        if (NetWorkUtils.isNetworkAvailable(getContext(), true)) {
            if (hasNewImg()) {
                showWaiting();
                if (!TextUtils.isEmpty(TOKEN)) {
                    showWaiting();
                    uploadimg(0, new EdittaskFragment.UploadFinishListener() {
                        @Override
                        public void onFinish() {
                            String imgurl = "";
                            for (int m = 0; m < uploadlistData.size() - 1; m++) {
                                LocalFileEntity finalfile = uploadlistData.get(m);
                                imgurl += finalfile.url + ",";
                            }
                            if (imgurl.endsWith(",")) {
                                imgurl = imgurl.substring(0, imgurl.length() - 1);
                            }
                            clearWaiting();
                            API.DealWithShenPi(message, id, typeStr, contentstr, copy_list, imgurl);
                        }
                    });
                }
            } else {
                String imgurl = "";
                for (int m = 0; m < uploadlistData.size() - 1; m++) {
                    LocalFileEntity finalfile = uploadlistData.get(m);
                    imgurl += finalfile.url + ",";
                }
                API.DealWithShenPi(message, id, typeStr, contentstr, copy_list, imgfile);
            }
        }
    }


    private boolean hasNewImg() {
        for (int a = 0; a < uploadlistData.size() - 1; a++) {
            LocalFileEntity item = uploadlistData.get(a);
            if (!item.fromNet) {
                return true;
            }
        }
        return false;
    }


    public void uploadimg(final int postion, final EdittaskFragment.UploadFinishListener listener) {
        final LocalFileEntity itemfile = uploadlistData.get(postion);
        if (!itemfile.fromNet) {
            File file = new File(UriUtils.getPath(getContext(), itemfile.path));
            App.uploadManager.put(file, "task/image/" + new Date().getTime() + ".png", TOKEN, new UpCompletionHandler() {
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
            MeasureListHight(listimgs, uploadlistData);
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
