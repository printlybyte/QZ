package com.qz.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.qz.app.base.BaseAdapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.qz.app.R;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.HuibaoDetail;
import com.qz.app.entity.HuibaoEntity;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.fragment.HuibaodetailFragment;
import com.qz.app.fragment.PublichuibaoFragment;
import com.qz.app.fragment.SelectionDialogFragment;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.GlideUtils;
import com.qz.app.utils.QZutils;

public class HuiBaoAdapter extends BaseAdapter {


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    CommonUtils.showToast("删除成功");
                    entitys.remove(msg.arg2);
                    notifyDataSetChanged();
                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if (null != msg.obj) {
                        message = (String) msg.obj;
                    } else {
                        message = "删除失败";
                        ;
                    }
                    CommonUtils.showToast(message);
                    break;

            }

        }
    };

    private Fragment fragment;
    private SelectionDialogFragment dialog;

    public HuiBaoAdapter(Context context, final Fragment fragment, List<? extends Object> data) {
        super(context, data);
        this.fragment = fragment;
        dialog = SelectionDialogFragment.newInstance(new SelectionDialogFragment.OnButtonClickListener() {
            @Override
            public void OnEdit(int pos) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.HUIBAO_ID,((HuibaoEntity.Rows)entitys.get(pos)).id+"");
                bundle.putInt(Constant.HUIBAO_TYPE,Integer.parseInt(((HuibaoEntity.Rows)entitys.get(pos)).thetype));
                FragmentManager.addStackFragment(fragment.getActivity(),BaseFragment.getInstance(fragment.getActivity(), PublichuibaoFragment.class.getName(),bundle));
            }
            @Override
            public void OnDel(int pos) {
                del(pos);
                notifyDataSetChanged();
            }
            @Override
            public void OnCancel(int pos) {

            }
        });
    }


    public int getLayoutId() {
        return R.layout.item_huibaolist;
    }

    public int[] getConvertItemIds() {
        return new int[]{R.id.pinglun_click,R.id.userhead, R.id.yuedu_click, R.id.fujian_click, R.id.name_data, R.id.summary_data, R.id.typename_data, R.id.showselection, R.id.time_data, R.id.content_data, R.id.plan_data, R.id.images, R.id.discusscount_data, R.id.havereadcount_data, R.id.subfile_data,};
    }

    public void initViews(ViewHolder vh, final int position) {
        HuibaoEntity.Rows item = (HuibaoEntity.Rows) entitys.get(position);
        ((TextView) vh.getView(R.id.name_data)).setText(item.uname);
        ((TextView) vh.getView(R.id.typename_data)).setText(item.type_name + "(" + QZutils.getData(item.created_at) + ")");
        ((TextView) vh.getView(R.id.time_data)).setText(QZutils.cutTimeWithoutYear2(item.created_at));
        GlideUtils.setRoundImage(context,item.face,R.drawable.default_head,R.drawable.default_head, (ImageView) vh.getView(R.id.userhead));
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pinglun_click:
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.HUIBAO_FROM,0);
                        bundle.putString(Constant.HUIBAO_ID,((HuibaoEntity.Rows) entitys.get(position)).id+"");
                        FragmentManager.addStackFragment(fragment.getActivity(), BaseFragment.getInstance(fragment.getActivity(),HuibaodetailFragment.class.getName(),bundle));
                        break;
                    case R.id.yuedu_click:
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt(Constant.HUIBAO_FROM,1);
                        bundle2.putString(Constant.HUIBAO_ID,((HuibaoEntity.Rows) entitys.get(position)).id+"");
                        FragmentManager.addStackFragment(fragment.getActivity(), BaseFragment.getInstance(fragment.getActivity(),HuibaodetailFragment.class.getName(),bundle2));
                        break;
                    case R.id.fujian_click:
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt(Constant.HUIBAO_FROM,2);
                        bundle3.putString(Constant.HUIBAO_ID,((HuibaoEntity.Rows) entitys.get(position)).id+"");
                        FragmentManager.addStackFragment(fragment.getActivity(), BaseFragment.getInstance(fragment.getActivity(),HuibaodetailFragment.class.getName(),bundle3));
                        break;
                    case R.id.showselection:
                        dialog.show(fragment.getFragmentManager(), position);
                        break;

                }


            }
        };
        vh.getView(R.id.showselection).setOnClickListener(onClickListener);
        vh.getView(R.id.pinglun_click).setOnClickListener(onClickListener);
        vh.getView(R.id.yuedu_click).setOnClickListener(onClickListener);
        vh.getView(R.id.fujian_click).setOnClickListener(onClickListener);


        if ("0".equals(item.commit_num) || TextUtils.isEmpty(item.commit_num)) {
            item.commit_num = "";
        } else {
            item.commit_num = " " + item.commit_num;
        }
        ((TextView) vh.getView(R.id.discusscount_data)).setText("评论" + item.commit_num);
        if ("0".equals(item.read_num) || TextUtils.isEmpty(item.read_num)) {
            item.read_num = "";
        } else {
            item.read_num = " " + item.read_num;
        }

        ((TextView) vh.getView(R.id.havereadcount_data)).setText("阅读" + item.read_num);
        if ("0".equals(item.fujian_num) || TextUtils.isEmpty(item.fujian_num)) {
            item.fujian_num = "";
        } else {
            item.fujian_num = " " + item.fujian_num;
        }

        vh.getView(R.id.summary_data).setVisibility(View.GONE);

        if ("1".equals(item.thetype)) {
            ((TextView) vh.getView(R.id.plan_data)).setVisibility(View.VISIBLE);
            ((TextView) vh.getView(R.id.content_data)).setText("今日工作总结：" + item.summary);
            ((TextView) vh.getView(R.id.plan_data)).setText("明日工作计划：" + item.plan);
            CommonUtils.setTextColor((TextView) vh.getView(R.id.content_data), Color.BLACK, 0, 7);
            CommonUtils.setTextColor((TextView) vh.getView(R.id.plan_data), Color.BLACK, 0, 7);
        } else if ("2".equals(item.thetype)) {
            ((TextView) vh.getView(R.id.content_data)).setText("本周工作总结：" + item.summary);
            ((TextView) vh.getView(R.id.plan_data)).setVisibility(View.GONE);
            CommonUtils.setTextColor((TextView) vh.getView(R.id.content_data), Color.BLACK, 0, 7);
        } else if ("3".equals(item.thetype)) {
            ((TextView) vh.getView(R.id.content_data)).setText("本月工作总结：" + item.summary);
            ((TextView) vh.getView(R.id.plan_data)).setVisibility(View.GONE);
            CommonUtils.setTextColor((TextView) vh.getView(R.id.content_data), Color.BLACK, 0, 7);

        } else if ("4".equals(item.thetype)) {
            ((TextView) vh.getView(R.id.plan_data)).setVisibility(View.VISIBLE);
            vh.getView(R.id.summary_data).setVisibility(View.VISIBLE);
            ((TextView) vh.getView(R.id.content_data)).setText("今日营业额：" + item.money);
            ((TextView) vh.getView(R.id.plan_data)).setText("今日营业额：" + item.customer);
            CommonUtils.setTextColor((TextView) vh.getView(R.id.content_data), Color.BLACK, 0, 7);
            CommonUtils.setTextColor((TextView) vh.getView(R.id.plan_data), Color.BLACK, 0, 7);
            ((TextView) vh.getView(R.id.summary_data)).setText("今日总结：" + item.summary);
            CommonUtils.setTextColor((TextView) vh.getView(R.id.summary_data), Color.BLACK, 0, 4);
        }
        ((TextView) vh.getView(R.id.subfile_data)).setText("附件" + item.fujian_num);


        RecyclerView recyclerView = ((RecyclerView) vh.getView(R.id.images));
        if (TextUtils.isEmpty(item.imgurl)) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            setImages(recyclerView, item.imgurl);
        }

    }

    public void setImages(RecyclerView img, String url) {
        ArrayList<LocalFileEntity> items = new ArrayList<>();
        String strs[] = null;
        if (url.contains(",")) {
            strs = url.split(",");
        } else {
            strs = new String[]{url};
        }
        for (int m = 0; m < strs.length; m++) {
            LocalFileEntity itemEntity = new LocalFileEntity();
            itemEntity.fromNet = true;
            itemEntity.url = strs[m];
            items.add(itemEntity);
        }
        HubaoImgAdapter adapter = new HubaoImgAdapter(context, fragment, (ArrayList) items);
        RecyclerView listimgs = (RecyclerView) img.findViewById(R.id.images);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false);
        listimgs.setLayoutManager(gridLayoutManager2);
        listimgs.setAdapter(adapter);

        MeasureListHight(listimgs, items);
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

    public void del(int id) {
        HuibaoEntity.Rows item = (HuibaoEntity.Rows) entitys.get(id);
        Message message = new Message();
        message.setTarget(handler);
        message.arg2 = id;
        API.delHuiBao(message, item.id);

    }


}
