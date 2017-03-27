package com.qz.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qz.app.App;
import com.qz.app.R;
import com.qz.app.adapter.MemberHeadAdapter;
import com.qz.app.adapter.UploadImgAdapter;
import com.qz.app.base.BaseEntity;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.entity.TaskDetailEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.GlideUtils;
import com.qz.app.utils.L;
import com.qz.app.utils.NetWorkUtils;
import com.qz.app.utils.QZutils;
import com.qz.app.utils.UriUtils;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DateScrollerDialog;
import com.qz.app.view.DialogButtonsListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.valuesfeng.picker.utils.PicturePickerUtils;

/**
 * Created by Administrator on 2015/6/26.
 */
public class EdittaskFragment extends BaseFragment implements View.OnClickListener {
    private static final int ACION_DEL = 1;
    private static final int ACION_EDIT = 2;
    private static final int GET_DETAIL = 99;
    private static final int GET_TOKEN = 88;
    private EditText content;
    private android.support.v7.widget.RecyclerView pics;
    private TextView name_data;
    private TextView endtime_data;
    private TextView okBut, cancelBut;
    private TextView delbutton;
    private android.support.v7.widget.RecyclerView memberlist;
    private String id;
    private int pageType;
    TaskDetailEntity.Data detail;
    public static DepAndEmp.Userjson chargeman;
    public static ArrayList<DepAndEmp.Userjson> memebers = new ArrayList<>();
    private View chargeman_layout;
    private View endtime_layout;
    private List imges = new ArrayList();
    private UploadImgAdapter uploadImgAdapter;
    private MemberHeadAdapter memberHeadAdapter;
    private List<DepAndEmp.Userjson> memberlistData = new ArrayList<>();
    private List<LocalFileEntity> uploadlistData = new ArrayList<>();
    public static final int REQUEST_CODE_CHOOSE = 1;
    int count = 0;
    private List<Uri> mSelected;
    private String liable_person;//id,不是文字
    private View addfile_layout;
    private String TOKEN;//七牛 token
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:
                    showWaiting();
                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    switch (msg.arg1) {
                        case GET_TOKEN:
                            TOKEN = ((BaseEntity) msg.obj).token;
                            break;
                        case ACION_DEL:
                            FragmentManager.popFragmentTo(getActivity(), RenwudetailFragment.class.getName());
                            break;
                        case ACION_EDIT:
                            CommonUtils.showToast("操作成功");
                            if (pageType == Constant.TASK_CREAT) {
                                FragmentManager.popFragment(getActivity());
                                break;
                            }
                            try {
                                if (null != getArguments().getSerializable(Constant.TASK_DETAIL)) {
                                    FragmentManager.popFragment(getActivity());
                                    return;
                                }
                            } catch (Exception e) {

                            }
                            setView(false);
//                            okBut.setText("编辑");
//                            leftimg.setVisibility(View.VISIBLE);
//                            cancelBut.setVisibility(View.GONE);
//                            uploadlistData.remove(uploadlistData.size() - 1);
//                            memberlistData.remove(memberlistData.size() - 1);
//                            uploadImgAdapter.setEditMode(false);
//                            memberHeadAdapter.setEditMode(false);
//                            MeasureListHight(pics, uploadlistData);
//                            MeasureListHight(memberlist, memberlistData);
                            break;
                        case GET_DETAIL:
                            TaskDetailEntity.Data data = ((TaskDetailEntity) msg.obj).data;
                            detail = data;
                            if(!TextUtils.isEmpty(data.thumbpath)&&data.thumbpath.endsWith(",")) {
                                data.thumbpath = data.thumbpath.substring(0,data.thumbpath.length()-1);
                            }
//                            String[] memberIds = data.member.split(",");

                            id = data.id;
                            content.setText(data.title);
                            name_data.setText(data.liable_person);
                            endtime_data.setText(QZutils.cutTimer(data.enddate));
                            setView(false);

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
    private TextView addFileNum;

    public void setView(boolean enable) {
        chargeman_layout.setEnabled(enable);
        endtime_layout.setEnabled(enable);
        content.setEnabled(enable);
        if (null != detail && !"1".equals(detail.status)) {
            okBut.setVisibility(View.GONE);
        }
        if (enable) {
//            Glide.with(mContext)
//                    .load("url")
//                    .downloadOnly(500, 500);
            addfile_layout.setVisibility(View.GONE);
            LocalFileEntity localfile = new LocalFileEntity();
            uploadlistData.add(localfile);
            DepAndEmp.Userjson user = new DepAndEmp.Userjson();
            memberlistData.add(user);
            MeasureListHight(pics, uploadlistData);
            MeasureListHight(memberlist, memberlistData);
            memberHeadAdapter.setEditMode(enable);
            uploadImgAdapter.setEditMode(enable);
            delbutton.setVisibility(View.VISIBLE);
            okBut.setText("完成");
            cancelBut.setVisibility(View.VISIBLE);
            leftimg.setVisibility(View.GONE);
            delbutton.setVisibility(View.VISIBLE);
        } else {
            uploadlistData.clear();
            if (null != detail && !TextUtils.isEmpty(detail.thumbpath)) {
                String strs[] = detail.thumbpath.split(",");
                for (int i = 0; i < strs.length; i++) {
                    LocalFileEntity localFileEntity = new LocalFileEntity();
                    localFileEntity.fromNet = true;
                    localFileEntity.url = strs[i];
                    uploadlistData.add(localFileEntity);
                }
                uploadImgAdapter.notifyDataSetChanged();
                MeasureListHight(pics, uploadlistData);
            }
            liable_person = detail.liable_id;
            memberlistData.clear();
            if (null != detail && null != detail.members && detail.members.size() > 0) {
                for (int a = 0; a < detail.members.size(); a++) {
                    DepAndEmp.Userjson userjsonItem = new DepAndEmp.Userjson();
                    userjsonItem.name = detail.members.get(a).username;
                    userjsonItem.face = detail.members.get(a).userface;
                    userjsonItem.fromNet = true;
                    try {
                        userjsonItem.id = detail.members.get(a).memberid;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    memberlistData.add(userjsonItem);
                }
                memberHeadAdapter.notifyDataSetChanged();
                MeasureListHight(memberlist, memberlistData);
            }
            if (null != detail && !TextUtils.isEmpty(detail.filename)) {
                addfile_layout.setVisibility(View.VISIBLE);
                addFileNum.setText("共有" + detail.filesize + "个附件");
            } else {
                addfile_layout.setVisibility(View.GONE);
            }

//            cancelBut.setVisibility(View.GONE);
//            leftimg.setVisibility(View.VISIBLE);
//            delbutton.setVisibility(View.GONE);
//            okBut.setText("编辑");
//            uploadlistData.remove(uploadlistData.size()-1);
//            memberlistData.remove(memberlistData.size()-1);
//            uploadImgAdapter.setEditMode(false);
//            uploadImgAdapter.setEditMode(false);
//            uploadImgAdapter.notifyDataSetChanged();
//            memberHeadAdapter.notifyDataSetChanged();
//            MeasureListHight(pics, uploadlistData);
//            MeasureListHight(memberlist, memberlistData);
//            uploadlistData.remove(uploadlistData.size() - 1);
//            memberlistData.remove(memberlistData.size() - 1);
            memberHeadAdapter.setEditMode(enable);
            uploadImgAdapter.setEditMode(enable);
            okBut.setText("编辑");
            cancelBut.setVisibility(View.GONE);
            leftimg.setVisibility(View.VISIBLE);
            delbutton.setVisibility(View.GONE);
            MeasureListHight(pics, uploadlistData);
            MeasureListHight(memberlist, memberlistData);
            if(pageType!=Constant.TASK_GET_SUBTASK) {
                setTitle("编辑任务");
            } else {
                setTitle("子任务");
            }


        }


    }


    ImageView leftimg;

    @Override
    public void initViews(ViewGroup rootView) {
        count = 0;
        chargeman = null;
        memebers.clear();
        initView();
        memberHeadAdapter = new MemberHeadAdapter(getContext(), memberlistData);
        uploadImgAdapter = new UploadImgAdapter(getContext(), this, (ArrayList) uploadlistData);
        pageType = getArguments().getInt(Constant.EDIT_TYPE);
        content = (EditText) rootView.findViewById(R.id.content);
        pics = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.pics);
        name_data = (TextView) rootView.findViewById(R.id.name_data);
        endtime_data = (TextView) rootView.findViewById(R.id.endtime_data);
        memberlist = (android.support.v7.widget.RecyclerView) rootView.findViewById(R.id.chargemanlist);
        cancelBut = (TextView) rootView.findViewById(R.id.cancel_click);
        addFileNum = (TextView) rootView.findViewById(R.id.addfilenum);
        okBut = (TextView) rootView.findViewById(R.id.ok_button
        );
        leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        chargeman_layout = rootView.findViewById(R.id.chargeman_layout);
        endtime_layout = rootView.findViewById(R.id.endtime_layout);
        addfile_layout = rootView.findViewById(R.id.addfile_layout);
        delbutton = (TextView) rootView.findViewById(R.id.delbutton);
        cancelBut.setOnClickListener(this);
        okBut.setOnClickListener(this);
        delbutton.setOnClickListener(this);
        chargeman_layout.setOnClickListener(this);
        endtime_layout.setOnClickListener(this);
        leftimg.setOnClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 7, GridLayoutManager.VERTICAL, false);
        memberlist.setLayoutManager(gridLayoutManager);
        memberlist.setAdapter(memberHeadAdapter);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        pics.setLayoutManager(gridLayoutManager2);
        pics.setAdapter(uploadImgAdapter);


        String title = "";
        switch (pageType) {
            case Constant.TASK_EDIT:
                title = "编辑任务";
                detail = (TaskDetailEntity.Data) getArguments().getSerializable(Constant.TASK_DETAIL);
                delbutton.setVisibility(View.VISIBLE);
                cancelBut.setVisibility(View.VISIBLE);
                leftimg.setVisibility(View.GONE);
                memberHeadAdapter.setEditMode(true);
                uploadImgAdapter.setEditMode(true);
                addfile_layout.setVisibility(View.GONE);
                TaskDetailEntity.Data data = (TaskDetailEntity.Data) getArguments().getSerializable(Constant.TASK_DETAIL);
                if (null != data && !TextUtils.isEmpty(data.thumbpath)) {
                    String strs[] = data.thumbpath.split(",");
                    for (int i = 0; i < strs.length; i++) {
                        LocalFileEntity localFileEntity = new LocalFileEntity();
                        localFileEntity.fromNet = true;
                        localFileEntity.url = strs[i];
                        uploadlistData.add(localFileEntity);
                    }
                }
                liable_person = data.liable_id;
//                String[] memberIds = data.member.split(",");
                if (null != data.members && data.members.size() > 0) {
                    for (int a = 0; a < data.members.size(); a++) {
                        DepAndEmp.Userjson userjsonItem = new DepAndEmp.Userjson();
                        userjsonItem.name = data.members.get(a).username;
                        userjsonItem.face = data.members.get(a).userface;
                        userjsonItem.fromNet = true;
                        try {
                            userjsonItem.id = data.members.get(a).memberid;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        memberlistData.add(userjsonItem);
                    }
                }
                id = data.id;
                content.setText(data.title);
                name_data.setText(data.liable_person);
                endtime_data.setText(QZutils.cutTimer(data.enddate));
                break;
            case Constant.TASK_CREAT:
                title = "创建任务";
                addfile_layout.setVisibility(View.GONE);
                uploadImgAdapter.setEditMode(true);
                memberHeadAdapter.setEditMode(true);
                cancelBut.setVisibility(View.VISIBLE);
                delbutton.setVisibility(View.GONE);
                leftimg.setVisibility(View.GONE);
                break;
            case Constant.TASK_GET_SUBTASK:
                okBut.setText("编辑");
                cancelBut.setVisibility(View.GONE);
                title = "子任务";
                delbutton.setText("删除子任务");
                leftimg.setVisibility(View.VISIBLE);
                delbutton.setVisibility(View.GONE);
                getDetail();
                break;

        }
        initTitledView(title);
        initAdapter();
        getToken();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edittask;
    }

    @Override
    public void setViews(View rootView) {
        if (null != chargeman) {
            name_data.setText(chargeman.name);
        }
        if (memebers.size() > 0) {
            memberlistData.addAll(memberlistData.size() - 1, memebers);
        }
        memebers.clear();
        MeasureListHight(memberlist, memberlistData);
        memberHeadAdapter.notifyDataSetChanged();
    }

    public void MeasureListHight(RecyclerView recyclerView, List datas) {
        if (datas.size() > 7) {
            int num = datas.size() / 7;

            if (datas.size() % 7 != 0) {
                num += 1;
            }
            float height = num * CommonUtils.getDpDementions(73);
            recyclerView.setMinimumHeight((int) height);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == okBut) {
            String text = okBut.getText().toString();
            if ("完成".equals(text)) {
                editTask();
            } else if ("编辑".equals(text)) {
                setView(true);
//                okBut.setText("完成");
//                cancelBut.setVisibility(View.VISIBLE);
//                leftimg.setVisibility(View.GONE);
//                delbutton.setVisibility(View.VISIBLE);


//                LocalFileEntity localfile = new LocalFileEntity();
//                uploadlistData.add(localfile);
//
//                DepAndEmp.Userjson user= new DepAndEmp.Userjson();
//                memberlistData.add(user);
//
//                MeasureListHight(pics, uploadlistData);
//                MeasureListHight(memberlist, memberlistData);
//                memberHeadAdapter.setEditMode(true);
//                uploadImgAdapter.setEditMode(true);
//                memberHeadAdapter.notifyDataSetChanged();
//                uploadImgAdapter.notifyDataSetChanged();


            }
        } else if (v == cancelBut) {


            if (null != getArguments().getSerializable(Constant.TASK_DETAIL) || pageType == Constant.TASK_CREAT) {
                FragmentManager.popFragment(getActivity());
            } else {
                setView(false);


            }


        } else if (v == delbutton) {

            final CommAlertDialog dialog = new CommAlertDialog(getContext());
            dialog.setTitleTv("提示");
            dialog.setContentInfo("是否要删除此任务");
            dialog.show();
            dialog.setButtonsListener(new DialogButtonsListener() {
                @Override
                public void onOKClick(Objects objects) {
                    delTask();
                    dialog.dismiss();
                }

                @Override
                public void onCancleClick(Objects objects) {
                    dialog.dismiss();
                }
            });

        } else if (v == chargeman_layout) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.EMP_FROM, Constant.EMP_FROM_SELECT_CHARGEMAN);
            FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), EmployeesFragment.class.getName(), bundle));
        } else if (v == endtime_layout) {
            QZutils.showTimer(endtime_data, getFragmentManager());
        }
    }

    public void delTask() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg1 = ACION_DEL;
        API.delTask(message, id);
    }

    public void initAdapter() {
        LocalFileEntity entity = new LocalFileEntity();
        uploadlistData.add(entity);
        DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
        memberlistData.add(userjson);
        MeasureListHight(pics, uploadlistData);
    }


    public void editTask() {
        final Message message = new Message();
        message.setTarget(handler);
        message.arg1 = ACION_EDIT;
        String id = "";
//      TaskDetailEntity.Data data = (TaskDetailEntity.Data) getArguments().getSerializable(Constant.TASK_DETAIL);
        if (pageType == Constant.TASK_EDIT) {
            id = detail.id;
        }
        String pid = "";
        String title = content.getText().toString();
        String enddate = endtime_data.getText().toString();
        String member = "";
        if (memberlistData.size() > 1) {
            for (int i = 0; i < memberlistData.size() - 1; i++) {
                member += memberlistData.get(i).id + ",";
            }
        }

        if (pageType == Constant.TASK_CREAT) {
            try {
                pid = getArguments().getString(Constant.TASK_PARENT_ID);
            } catch (Exception e) {

            }
        }


        if (TextUtils.isEmpty(title)) {
            CommonUtils.showToast("请输入内容!");
            return;
        }






//        File files[] = new File[]{new File("/storage/emulated/0/Download/IMG_20170227_090820.jpg"), new File("/storage/emulated/0/Download/file2.jpg"), new File("/storage/emulated/0/Download/file3.jpg")};
//        API.upluad(message, id, pid, title, enddate, member, imgurl, files);

        if (null != chargeman) {
            liable_person = chargeman.id;
        }

        if(TextUtils.isEmpty(liable_person)) {
            CommonUtils.showToast("负责人不能为空!");
            return;

        }



            if (TextUtils.isEmpty(enddate)) {
                CommonUtils.showToast("请输入截至日期!");
                return;
            }
//        File files[] = null;
//        if (uploadlistData.size() > 1) {
//            files = new File[uploadlistData.size()];
//            for (int i = 0; i < uploadlistData.size() - 1; i++) {
//                try {
//                    if(!uploadlistData.get(i).fromNet) {
//                        files[i] = new File(UriUtils.getPath(getContext(), uploadlistData.get(i).path));
//                    }
//                } catch (Exception e) {
//                }
//            }
//        }
        if (NetWorkUtils.isNetworkAvailable(getContext(), true)) {

            if(hasNewImg()) {
            if (!TextUtils.isEmpty(TOKEN)) {
                final String taskId = id;
                final String taskFatherId = pid;
                final String tasktitle = title;
                final String taskenddate = enddate;
                final String taskmember = member;
                showWaiting();
                uploadimg(0, new UploadFinishListener() {
                    @Override
                    public void onFinish() {
                        String imgurl = "";
                        for (int m = 0; m < uploadlistData.size() - 1; m++) {
                            LocalFileEntity finalfile = uploadlistData.get(m);
                            imgurl += finalfile.url + ",";
                        }
                        clearWaiting();
                        API.update_task(message, taskId, taskFatherId, liable_person, tasktitle, taskenddate, taskmember, imgurl, null);
                    }
                });
            }
            }else {
                 String imgurl = "";
                for (int m = 0; m < uploadlistData.size() - 1; m++) {
                    LocalFileEntity finalfile = uploadlistData.get(m);
                    imgurl += finalfile.url + ",";
                }
                API.update_task(message, id, pid, liable_person, title, enddate, member, imgurl, null);
            }

        }
    }


    private boolean hasNewImg(){
        for(int a=0;a<uploadlistData.size()-1;a++) {
            LocalFileEntity item =uploadlistData.get(a);
            if(!item.fromNet){
                return true;
            }
        }
        return false;
    }


    public void uploadimg(final int postion, final UploadFinishListener listener ){
        final LocalFileEntity itemfile = uploadlistData.get(postion);
        if(!itemfile.fromNet) {
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
                    }
                    L.v("EdittaskFragment", "EdittaskFragment", postion);
                    L.v("EdittaskFragment", "EdittaskFragment", uploadlistData.size() - 2);
                    if (postion == uploadlistData.size() - 2) {
                        L.v("EditTaskFragment", "begin send messge to Server");
                        listener.onFinish();
                    } else {
                        uploadimg(postion+1, listener);
                    }
                    L.v("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                }
            }, null);
        }else {
            uploadimg(postion+1, listener);
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
            MeasureListHight(pics, uploadlistData);
        }
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Uri uri = null;
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case 1:
//
//                    uri = SelectionDialogFragment.imagUrl;
//                    Intent intent = new Intent("com.android.camera.action.CROP");
//                    intent.setDataAndType(uri, "image/*");
//                    intent.putExtra("scale", true);
//                    intent.putExtra("crop", true);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, SelectionDialogFragment.imagUrl);
//                    startActivityForResult(intent, 2);
//                    break;
//
//                case 2:
//                    uri = SelectionDialogFragment.imagUrl;
//                    LocalFileEntity fileEntity = new LocalFileEntity();
//                    fileEntity.path =uri;
//                    uploadlistData.add(fileEntity);
//                    uploadImgAdapter.notifyDataSetChanged();
//                    MeasureListHight(pics,uploadlistData);
////                    GlideUtils.setLocalRoundImage2(getContext(), uri, R.drawable.default_head, R.drawable.default_head, headimg);
////                    Bitmap bitmap = null;
////                    try {
////                        bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));
////                    } catch (FileNotFoundException e) {
////                        e.printStackTrace();
////                    }
//                    break;
//
//                case 3:
//                    if (null != data && null != data.getData()) {
//                        uri = data.getData();
//                    }
//                    intent = new Intent("com.android.camera.action.CROP");
//                    intent.setDataAndType(uri, "image/*");
//                    intent.putExtra("scale", true);
//                    intent.putExtra("crop", true);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, SelectionDialogFragment.imagUrl);
//                    startActivityForResult(intent, 2);
//                    break;
//
//
//            }
//
//        }
//    }


    private void getDetail() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg1 = GET_DETAIL;
        String id = "";

        id = getArguments().getString(Constant.TASK_ID);
        if (!TextUtils.isEmpty(id)) {
            API.getTaskDetail(message, id);
        }
    }

    private void getToken() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg1 = GET_TOKEN;
        API.getToken(message);
    }

    public interface  UploadFinishListener{

        void onFinish();

    }


}
