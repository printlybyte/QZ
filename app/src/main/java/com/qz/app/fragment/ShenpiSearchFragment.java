package com.qz.app.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.adapter.RenwuAdapter;
import com.qz.app.adapter.ShenpiAdapter;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.NetTask;
import com.qz.app.entity.Selection_item;
import com.qz.app.entity.ShenPiEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;

import static com.qz.app.R.id.cancel_click;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ShenpiSearchFragment extends BaseFragment implements View.OnClickListener {

    private EditText search_content_data;
    private FrameLayout contentlist;
    private ListView filterlist;
    private LinearLayout search_noinfo;
    private ShenpiAdapter adapter;
    private ImageView delimg;
    private TextView cancelbt;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    setAdapter((ShenPiEntity) msg.obj);

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
        search_content_data = (EditText) rootView.findViewById(R.id.search_content_data);
        cancelbt = (TextView) rootView.findViewById(cancel_click);
        contentlist = (FrameLayout) rootView.findViewById(R.id.contentlist);
        filterlist = (ListView) rootView.findViewById(R.id.filterlist);
        search_noinfo = (LinearLayout) rootView.findViewById(R.id.search_noinfo);
        delimg = (ImageView) rootView.findViewById(R.id.delimg);
        cancelbt.setOnClickListener(this);
        delimg.setOnClickListener(this);
        search_content_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != adapter) {
                    adapter.cleanData();
                }
                String content = search_content_data.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    delimg.setVisibility(View.INVISIBLE);
                    search_noinfo.setVisibility(View.VISIBLE);
                    return;
                }

                delimg.setVisibility(View.VISIBLE);
                getShenPilist(content);
            }
        });

        filterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_renwusearch;
    }

    @Override
    public void setViews(View rootView) {



    }


    @Override
    public void onClick(View v) {
        if (v == cancelbt) {
            FragmentManager.popFragment(getActivity());
        } else if (v == delimg) {
            search_content_data.setText("");
            delimg.setVisibility(View.INVISIBLE);
        }
    }

    private void setAdapter(ShenPiEntity shnepientity) {

        if (null == shnepientity || null == shnepientity.rows||shnepientity.rows.size()<=0) {
            search_noinfo.setVisibility(View.VISIBLE);
            contentlist.setVisibility(View.INVISIBLE);
            return;
        }
        search_noinfo.setVisibility(View.INVISIBLE);
        contentlist.setVisibility(View.VISIBLE);
        adapter = new ShenpiAdapter(context, shnepientity.rows);
        filterlist.setAdapter(adapter);
    }

    public void getShenPilist(String content) {
        Message message = new Message();
        message.setTarget(handler);
       int type = getArguments().getInt(Constant.SHENPI_SEARCH_FROM);
        switch (type){
            case Constant.SHENPI_SEARCH_FROM_FABU:
                API.getMyShenPi(message,  content,0, 3, "created_at",  1, 1000+"");
             break;
            case Constant.RENWU_SEARCH_FROM_DAIWO:
                API.getDaiwoShenPi(message, content,0,1, 1000+"","","");
                break;
            case Constant.RENWU_SEARCH_FROM_SHNEPI:
                API.getShenPi(message,  content,0, 3, "created_at",  1, 1000+"");
                break;
            case Constant.RENWU_SEARCH_FROM_CHAOSONG:
                API.getChaosongShenPi(message,  content,0, 3, "created_at",  1, 1000+"");
                break;
        }
    }


}
