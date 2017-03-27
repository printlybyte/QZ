package com.qz.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.CommentAdapter;
import com.qz.app.adapter.HubaoImgAdapter;
import com.qz.app.adapter.ReaderAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.Commitlist;
import com.qz.app.entity.Documentdetail;
import com.qz.app.entity.HuibaoDetail;
import com.qz.app.entity.LocalFileEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.DataManagers;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.QZutils;
import com.qz.app.view.PopWinWithList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class HuibaodetailFragment extends BaseFragment implements View.OnClickListener {
    private static final int DETAIL = 1;
    private static final int PINGLUN = 2;
    private ImageView leftimg;
    private TextView title;
    private TextView writehuibao, commit;
    private ListView listView;
    private EditText content;
    private SelectionDialogFragment dialog;
    private ArrayList<Documentdetail.ReadMan> showreaderlistdata = new ArrayList<>();
    private View pinglunline, yueduline, fujianline, computerimg, commentlayout;

    private RecyclerView readerlist;
    private ReaderAdapter adapter;

    private CommentAdapter commentAdapter;
    private Commitlist commitlistItem;
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

                        case PINGLUN:
                            content.setText("");
                            commentAdapter.addItem(commitlistItem);
                            break;
                        case DETAIL:
                            setHuibaoDetail((HuibaoDetail) msg.obj);
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
        writehuibao = (TextView) rootView.findViewById(R.id.writehuibao);
        commit = (TextView) rootView.findViewById(R.id.commit);
        listView = (ListView) rootView.findViewById(R.id.listview);
        content = (EditText) rootView.findViewById(R.id.content);
        commit.setOnClickListener(this);
        writehuibao.setOnClickListener(this);
        initTitledView("汇报详情");
        getDetail();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_huibaodetail;
    }

    @Override
    public void setViews(View rootView) {


    }

    public void initViewWithEntity() {


    }

    private void setHuibaoDetail(final HuibaoDetail huibaoDetail) {
        final HuibaoDetail.Data item = huibaoDetail.data;
        ((TextView) rootView.findViewById(R.id.name_data)).setText(item.uname);
        ((TextView) rootView.findViewById(R.id.typename_data)).setText(item.type_name + "(" + QZutils.getData(item.created_at) + ")");
        ((TextView) rootView.findViewById(R.id.time_data)).setText(QZutils.cutTimeWithoutYear2(item.created_at));
        pinglunline = rootView.findViewById(R.id.discusscountline);
        commentlayout = rootView.findViewById(R.id.commentlayout);
        yueduline = rootView.findViewById(R.id.readcountline);
        fujianline = rootView.findViewById(R.id.fujianline);
        computerimg = rootView.findViewById(R.id.computerimg);
        readerlist = (RecyclerView) rootView.findViewById(R.id.memberdata);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 7, GridLayoutManager.VERTICAL, false);
        readerlist.setLayoutManager(gridLayoutManager);

        showreaderlistdata.addAll(huibaoDetail.readlist);
        adapter = new ReaderAdapter(getContext(), showreaderlistdata);

        readerlist.setAdapter(adapter);

        commentAdapter = new CommentAdapter(getContext(), huibaoDetail.commitlist);

        listView.setAdapter(commentAdapter);
        final int type = getArguments().getInt(Constant.HUIBAO_FROM);
        showbottomView(type);
//        ReaderAdapter readerAdapter = new
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pinglun_click:
                        showbottomView(0);
                        break;
                    case R.id.yuedu_click:
                        showbottomView(1);
                        break;
                    case R.id.fujian_click:
                        showbottomView(2);
                        break;
                    case R.id.showselection:
                        dialog.show(getFragmentManager(), -1);
                        break;
                }


            }
        };
        rootView.findViewById(R.id.showselection).setOnClickListener(onClickListener);
        rootView.findViewById(R.id.pinglun_click).setOnClickListener(onClickListener);
        rootView.findViewById(R.id.yuedu_click).setOnClickListener(onClickListener);
        rootView.findViewById(R.id.fujian_click).setOnClickListener(onClickListener);


        if ("0".equals(item.commit_num) || TextUtils.isEmpty(item.commit_num)) {
            item.commit_num = "";
        } else {
            item.commit_num = " " + item.commit_num;
        }
        ((TextView) rootView.findViewById(R.id.discusscount_data)).setText("  评论" + item.commit_num);
        if ("0".equals(item.read_num) || TextUtils.isEmpty(item.read_num)) {
            item.read_num = "";
        } else {
            item.read_num = " " + item.read_num;
        }

        ((TextView) rootView.findViewById(R.id.havereadcount_data)).setText("  阅读" + item.read_num);
        if ("0".equals(item.fujian_num) || TextUtils.isEmpty(item.fujian_num)) {
            item.fujian_num = "";
        } else {
            item.fujian_num = " " + item.fujian_num;
        }
        ((TextView) rootView.findViewById(R.id.subfile_data)).setText("  附件" + item.fujian_num);
        rootView.findViewById(R.id.summary_data).setVisibility(View.GONE);

        if ("1".equals(item.thetype)) {
            ((TextView) rootView.findViewById(R.id.plan_data)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.content_data)).setText("今日工作总结：" + item.summary);
            ((TextView) rootView.findViewById(R.id.plan_data)).setText("明日工作计划：" + item.plan);
            CommonUtils.setTextColor((TextView) rootView.findViewById(R.id.content_data), Color.BLACK, 0, 7);
            CommonUtils.setTextColor((TextView) rootView.findViewById(R.id.plan_data), Color.BLACK, 0, 7);
        } else if ("2".equals(item.thetype)) {
            ((TextView) rootView.findViewById(R.id.content_data)).setText("本周工作总结：" + item.summary);
            ((TextView) rootView.findViewById(R.id.plan_data)).setVisibility(View.GONE);
            CommonUtils.setTextColor((TextView) rootView.findViewById(R.id.content_data), Color.BLACK, 0, 7);
        } else if ("3".equals(item.thetype)) {
            ((TextView) rootView.findViewById(R.id.content_data)).setText("本月工作总结：" + item.summary);
            ((TextView) rootView.findViewById(R.id.plan_data)).setVisibility(View.GONE);
            CommonUtils.setTextColor((TextView) rootView.findViewById(R.id.content_data), Color.BLACK, 0, 7);

        } else if ("4".equals(item.thetype)) {
            ((TextView) rootView.findViewById(R.id.plan_data)).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.summary_data).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.content_data)).setText("今日营业额：" + item.money);
            ((TextView) rootView.findViewById(R.id.plan_data)).setText("今日营业额：" + item.customer);
            CommonUtils.setTextColor((TextView) rootView.findViewById(R.id.content_data), Color.BLACK, 0, 7);
            CommonUtils.setTextColor((TextView) rootView.findViewById(R.id.plan_data), Color.BLACK, 0, 7);
            ((TextView) rootView.findViewById(R.id.summary_data)).setText("今日总结：" + item.summary);
            CommonUtils.setTextColor((TextView) rootView.findViewById(R.id.summary_data), Color.BLACK, 0, 4);
        }
        RecyclerView recyclerView = ((RecyclerView) rootView.findViewById(R.id.images));
        if (TextUtils.isEmpty(item.imgurl)) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            setImages(recyclerView, item.imgurl);
        }
        dialog = SelectionDialogFragment.newInstance(new SelectionDialogFragment.OnButtonClickListener() {
            @Override
            public void OnEdit(int pos) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.HUIBAO_ENTITY, item);
                bundle.putInt(Constant.HUIBAO_TYPE, Integer.parseInt(item.thetype));
                FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), PublichuibaoFragment.class.getName(), bundle));
            }

            @Override
            public void OnDel(int pos) {
                del();
            }

            @Override
            public void OnCancel(int pos) {

            }
        });

        listView.setAdapter(commentAdapter);
        measureListViewHeight(listView);
    }


    public void measureListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int listViewWidth = getResources().getDisplayMetrics().widthPixels;//listView在布局时的宽度
        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(widthSpec, 0);

            int itemHeight = listItem.getMeasuredHeight();
            totalHeight += itemHeight;
        }
        // 减掉底部分割线的高度
        int historyHeight = totalHeight
                + (listView.getDividerHeight() * listAdapter.getCount() - 1);
        listView.setMinimumHeight(historyHeight);
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
        HubaoImgAdapter adapter = new HubaoImgAdapter(context, this, (ArrayList) items);
        RecyclerView listimgs = (RecyclerView) img.findViewById(R.id.images);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false);
        listimgs.setLayoutManager(gridLayoutManager2);
        listimgs.setAdapter(adapter);

        MeasureListHight(listimgs, items);
    }

    public void showbottomView(int pos) {

        listView.setVisibility(View.GONE);
        computerimg.setVisibility(View.GONE);
        readerlist.setVisibility(View.GONE);
        pinglunline.setVisibility(View.INVISIBLE);
        yueduline.setVisibility(View.INVISIBLE);
        fujianline.setVisibility(View.INVISIBLE);
        commentlayout.setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.subfile_data)).setTextColor(getResources().getColor(R.color.disable));
        ((TextView) rootView.findViewById(R.id.discusscount_data)).setTextColor(getResources().getColor(R.color.disable));
        ((TextView) rootView.findViewById(R.id.havereadcount_data)).setTextColor(getResources().getColor(R.color.disable));
        ((ImageView) rootView.findViewById(R.id.dicussimg)).setImageResource(R.drawable.discuss2);
        ((ImageView) rootView.findViewById(R.id.readcountimg)).setImageResource(R.drawable.yuedu_normal);
        ((ImageView) rootView.findViewById(R.id.subfileimg)).setImageResource(R.drawable.guanlian_normal);


//        ((TextView) rootView.findViewById(R.id.discusscount_data)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.discuss2), null, null, null);
//        ((TextView) rootView.findViewById(R.id.havereadcount_data)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.yuedu_normal), null, null, null);
//        ((TextView) rootView.findViewById(R.id.subfile_data)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.guanlian_normal), null, null, null);
        switch (pos) {
            case 0:
                commentlayout.setVisibility(View.VISIBLE);
                ((ImageView) rootView.findViewById(R.id.dicussimg)).setImageResource(R.drawable.pinglun_pressed);
                ((ImageView) rootView.findViewById(R.id.readcountimg)).setImageResource(R.drawable.yuedu_normal);
                ((ImageView) rootView.findViewById(R.id.subfileimg)).setImageResource(R.drawable.guanlian_normal);
                ((TextView) rootView.findViewById(R.id.discusscount_data)).setTextColor(getResources().getColor(R.color.selectedcolor));
//                ((TextView) rootView.findViewById(R.id.discusscount_data)).setTextColor(getResources().getColor(R.color.selectedcolor));
//                ((TextView) rootView.findViewById(R.id.discusscount_data)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.pinglun_pressed), null, null, null);
                pinglunline.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                break;
            case 1:
                ((ImageView) rootView.findViewById(R.id.subfileimg)).setImageResource(R.drawable.guanlian_normal);
                ((ImageView) rootView.findViewById(R.id.dicussimg)).setImageResource(R.drawable.discuss2);
                ((ImageView) rootView.findViewById(R.id.readcountimg)).setImageResource(R.drawable.yuedu_pressed);
                ((TextView) rootView.findViewById(R.id.havereadcount_data)).setTextColor(getResources().getColor(R.color.selectedcolor));
//                ((TextView) rootView.findViewById(R.id.havereadcount_data)).setTextColor(getResources().getColor(R.color.selectedcolor));
//                ((TextView) rootView.findViewById(R.id.havereadcount_data)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.yuedu_pressed), null, null, null);
                readerlist.setVisibility(View.VISIBLE);
                yueduline.setVisibility(View.VISIBLE);
                break;
            case 2:
                ((ImageView) rootView.findViewById(R.id.dicussimg)).setImageResource(R.drawable.discuss2);
                ((ImageView) rootView.findViewById(R.id.readcountimg)).setImageResource(R.drawable.yuedu_normal);
                ((ImageView) rootView.findViewById(R.id.subfileimg)).setImageResource(R.drawable.guanlian_pressed);
                ((TextView) rootView.findViewById(R.id.subfile_data)).setTextColor(getResources().getColor(R.color.selectedcolor));
//                ((TextView) rootView.findViewById(R.id.subfile_data)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.guanlian_pressed), null, null, null);
//                ((TextView) rootView.findViewById(R.id.subfile_data)).setTextColor(getResources().getColor(R.color.selectedcolor));
                computerimg.setVisibility(View.VISIBLE);
                fujianline.setVisibility(View.VISIBLE);
                break;
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

    private void getDetail() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg2 = DETAIL;
        String id = getArguments().getString(Constant.HUIBAO_ID);
        API.getHuiBaoDetail(message, id);
    }

    private void comment() {
        Message message = new Message();
        message.setTarget(handler);
        message.arg2 = PINGLUN;
        String id = getArguments().getString(Constant.HUIBAO_ID);
        String contentStr = content.getText().toString();
        commitlistItem = new Commitlist();
        try {
            commitlistItem.name = DataManagers.getMyData().username;
            commitlistItem.adddt = "刚刚";
            commitlistItem.content = contentStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        API.huibaoComment(message, id, contentStr);
    }


    @Override
    public void onClick(View v) {
        if (v == commit) {
            comment();
        } else if (v == writehuibao) {

            showMore(writehuibao);
        }
    }

    public void del() {
        String id = getArguments().getString(Constant.HUIBAO_ID);
        Message message = new Message();
        message.setTarget(handler);

        API.delHuiBao(message, id);

    }

    public void showMore(View morView) {
        ViewGroup popview = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.popwin_huibao, null);
        final PopWinWithList popWinWithList = new PopWinWithList(getActivity(), popview);
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                switch (v.getId()) {
                    case R.id.ribao_click:
                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_RIBAO);
                        break;
                    case R.id.zhoubao_click:

                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_ZHOUBAO);
                        break;
                    case R.id.yuebao_click:

                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_YUEBAO);
                        break;
                    case R.id.yeji_click:
                        bundle.putInt(Constant.HUIBAO_TYPE, Constant.HUIBAO_YEJI);
                        break;
                }
                popWinWithList.hide();
                FragmentManager.addStackFragment(getActivity(), BaseFragment.getInstance(getActivity(), PublichuibaoFragment.class.getName(), bundle));
            }
        };
        popview.findViewById(R.id.ribao_click).setOnClickListener(clicListener);
        popview.findViewById(R.id.zhoubao_click).setOnClickListener(clicListener);
        popview.findViewById(R.id.yuebao_click).setOnClickListener(clicListener);
        popview.findViewById(R.id.yeji_click).setOnClickListener(clicListener);
        popWinWithList.show(morView);
    }

}
