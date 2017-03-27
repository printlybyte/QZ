package com.qz.app.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.ChucahiAdapter;
import com.qz.app.adapter.MemberHeadAdapter;
import com.qz.app.adapter.UploadImgAdapter;
import com.qz.app.adapter.UploadShenpiImgAdapter;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.utils.CommonUtils;
import com.qz.app.view.DateScrollerDialog;

import java.util.ArrayList;
import java.util.List;

import io.valuesfeng.picker.utils.PicturePickerUtils;

/**
 *
 * Created by du on 2017/3/15.
 *
 */

public abstract class BaseShenpiListFragment extends BaseFragment implements View.OnClickListener {
    protected TextView finish;
    public static ArrayList<DepAndEmp.Userjson> copylistdata = new ArrayList<>();
    protected ArrayList<DepAndEmp.Userjson> localcopylistdata = new ArrayList<>();
    public static ArrayList<DepAndEmp.Userjson> memberlistdata = new ArrayList<>();
    protected ArrayList<DepAndEmp.Userjson> localmemberlistdata = new ArrayList<>();
    private android.support.v7.widget.RecyclerView memberlist, copylist,listimgs;
    private MemberHeadAdapter memberAdapter, copyAdapter;
    private UploadShenpiImgAdapter
            uploadImgAdapter;
    public static final int REQUEST_CODE_CHOOSE = 1;
    private List<Uri> mSelected;
    private  ArrayList<LocalFileEntity> uploadlistData = new ArrayList<>();
    protected  ListView listView;
    private BaseAdapter baseAdapter;
    @Override
    public void initViews(ViewGroup rootView) {
        copylistdata.clear();
        memberlistdata.clear();;
        listView = (ListView) rootView.findViewById(R.id.listview);
        baseAdapter = getAdapter();
        listView.setAdapter(baseAdapter);
        finish = (TextView) rootView.findViewById(R.id.finish);
        finish.setOnClickListener(this);


        View footView = getFootView();
        listimgs = (RecyclerView) footView.findViewById(R.id.listimgs);
        memberlist = (android.support.v7.widget.RecyclerView) footView.findViewById(R.id.memberlist);
        copylist = (android.support.v7.widget.RecyclerView) footView.findViewById(R.id.copylist);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);


        memberlist.setLayoutManager(gridLayoutManager1);
        copylist.setLayoutManager(gridLayoutManager2);
        DepAndEmp.Userjson userjson = new DepAndEmp.Userjson();
        userjson.name = "审批人";
        localmemberlistdata.add(userjson);

        DepAndEmp.Userjson copyuserjson = new DepAndEmp.Userjson();
        copyuserjson.name = "抄送人";
        localcopylistdata.add(copyuserjson);

        LocalFileEntity localfile = new LocalFileEntity();
        uploadlistData.add(localfile);

        memberAdapter = new MemberHeadAdapter(getContext(), localmemberlistdata, Constant.EMP_FROM_SELECT_HUIBAOTO);
        copyAdapter = new MemberHeadAdapter(getContext(), localcopylistdata, Constant.EMP_FROM_SELECT_COPYTOHUIBAO);
        uploadImgAdapter = new UploadShenpiImgAdapter(getContext(), this, uploadlistData);
        memberlist.setAdapter(memberAdapter);
        copylist.setAdapter(copyAdapter);
        if(null!=listimgs) {
            listimgs.setAdapter(uploadImgAdapter);
            listimgs.setLayoutManager(gridLayoutManager3);
        }
        uploadImgAdapter.setEditMode(true);
        memberAdapter.setEditMode(true);
        copyAdapter.setEditMode(true);
        listView.addFooterView(footView);
        MeasureListHight(listimgs, uploadlistData);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void setViews(View rootView) {
        if (memberlistdata.size() > 0) {
            localmemberlistdata.addAll(localmemberlistdata.size() - 1, memberlistdata);
            memberAdapter.notifyDataSetChanged();
            MeasureListHight(memberlist, localmemberlistdata);
            memberlistdata.clear();
        }
        if (copylistdata.size() > 0) {
            localcopylistdata.addAll(localcopylistdata.size() - 1, copylistdata);
            copyAdapter.notifyDataSetChanged();
            MeasureListHight(copylist, localcopylistdata);
            copylistdata.clear();
        }

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

    protected  void notifyListData(){
        if(null!=baseAdapter){
            baseAdapter.notifyDataSetChanged();
        }
    }

    protected abstract BaseAdapter getAdapter();

    protected abstract View getFootView();

}
