package com.qz.app.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.ReaderAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.Documentdetail;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;

import java.util.ArrayList;

import static com.qz.app.R.id.contenttv;

/**
 * Created by Administrator on 2015/6/26.
 */
public class DocdetailFragment extends BaseFragment {
    private TextView title_data;
    private TextView time_data;
    private TextView content_data;
    private TextView totalread_data;
    private RecyclerView readerlist;
    private View showmore, readerlist_layout;
    private ArrayList<Documentdetail.ReadMan> showreaderlistdata;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();

                    initViewWithEntity((Documentdetail) msg.obj);
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
        title_data = (TextView) rootView.findViewById(R.id.title_data);
        time_data = (TextView) rootView.findViewById(R.id.time_data);
        content_data = (TextView) rootView.findViewById(R.id.content_data);
        totalread_data = (TextView) rootView.findViewById(R.id.totalread_data);
        readerlist = (RecyclerView) rootView.findViewById(R.id.readerlist);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 7, GridLayoutManager.VERTICAL, false);
        showmore = rootView.findViewById(R.id.showmoreperson);
        readerlist_layout = rootView.findViewById(R.id.readerlist_layout);
        readerlist.setLayoutManager(gridLayoutManager);

        initTitledView("公司制度");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_docdetail;
    }

    @Override
    public void setViews(View rootView) {

        String id = getArguments().getString(Constant.DOC_ID);
        getDetail(id);


    }

    public void initViewWithEntity(Documentdetail documentdetail) {

        final Documentdetail.DocDetail docDetail = documentdetail.data;
        if (null != docDetail) {
            title_data.setText(docDetail.title);
            time_data.setText(docDetail.created_at + " · " + docDetail.dept_name);
            content_data.setText(Html.fromHtml(docDetail.content));
            totalread_data.setText(docDetail.read_log.size() + "人已阅");
            if (null != docDetail.read_log && docDetail.read_log.size() > 0) {
                showreaderlistdata=new ArrayList<>();
             final   ReaderAdapter adapter = new ReaderAdapter(getContext(), showreaderlistdata);
                if (docDetail.read_log.size() > 7) {
                    for (int a = 0; a < 7; a++) {
                        showreaderlistdata.add(docDetail.read_log.get(a));
                    }
                    adapter.notifyDataSetChanged();
                    showmore.setVisibility(View.VISIBLE);

                    showmore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showmore.setVisibility(View.GONE);
                            showreaderlistdata.clear();
                            showreaderlistdata.addAll(docDetail.read_log);
                            adapter.notifyDataSetChanged();
                        }
                    });

                } else {
                    showmore.setVisibility(View.GONE);
                }



                readerlist.setAdapter(adapter);
            } else {
                readerlist_layout.setVisibility(View.GONE);
            }
        }
    }


    public void getDetail(String id) {
        Message message = new Message();
        message.setTarget(handler);
        API.getDocDetail(message, id);

    }

}
