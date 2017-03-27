package com.qz.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qz.app.R;
import com.qz.app.adapter.DocumentAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.NetDocument;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

/**
 * 公司制度
 * Created by du on 2017/2/22.
 */

public class DocumentlistFragment extends BaseFragment {

    private ListView listView;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    setAdapter((NetDocument) msg.obj);

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
        initTitledView("公司制度");
        listView = (ListView) rootView.findViewById(R.id.documentlist);
        rootView.findViewById(R.id.search_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), SearchdocFragment.class.getName()));
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_documentlist;
    }

    @Override
    public void setViews(View rootView) {
        getDocument();
    }

    public void getDocument() {
        Message message = new Message();
        message.setTarget(handler);
        API.getDocumentlist(message, "", "");
    }

    public void setAdapter(final NetDocument netDocument) {
        if (null == netDocument || null == netDocument.rows) {
            return;
        }
        DocumentAdapter adapter = new DocumentAdapter(context, netDocument.rows);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.DOC_ID, netDocument.rows.get(position).id);
                FragmentManager.addStackFragment(getActivity(), com.qz.app.base.BaseFragment.getInstance(getActivity(), DocdetailFragment.class.getName(), bundle));
            }
        });

    }

}
