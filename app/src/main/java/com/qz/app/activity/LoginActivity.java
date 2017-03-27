package com.qz.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qz.app.App;
import com.qz.app.MainActivity;
import com.qz.app.R;
import com.qz.app.base.BaseActivity;
import com.qz.app.constant.Constant;
import com.qz.app.entity.NetUser;
import com.qz.app.entity.Userinfo;
import com.qz.app.filter.Hanyu;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.DataManagers;
import com.qz.app.utils.PreferencesUtils;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button login_click;
    private String username_data;
    private String password_data;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();
                    try {
                        Userinfo userinfo = ((NetUser) msg.obj).userinfo;
                        PreferencesUtils.putString(Constant.SP_TOKEN, userinfo.token);
                        DataManagers.saveMyData(userinfo);
                        MainActivity.start(LoginActivity.this);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if(null!=msg.obj){
                        message= (String) msg.obj;
                    } else {
                        message= "登录失败";
                    }
                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }

        }
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.mContext.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewByIds();
        initViewWithData();
        Hanyu hanyu = new Hanyu();
    }

    public void findViewByIds() {
        super.initView();
        username = (EditText) findViewById(R.id.username_data);
        password = (EditText) findViewById(R.id.password_data);
        login_click = (Button) findViewById(R.id.login_click);
        login_click.setOnClickListener(this);
    }

    public void initViewWithData() {

    }

    @Override
    public void onClick(View v) {
        username_data = username.getText().toString();
        password_data = password.getText().toString();
        if (TextUtils.isEmpty(username_data)) {
            CommonUtils.showToast("用户名不能为空!");
            return;
        } else if (TextUtils.isEmpty(password_data)) {
            CommonUtils.showToast("密码不能为空!");
            return;
        }
        showWaiting();
        login(username_data, password_data);
    }

    private void login(String userName, String password) {
        Message message = new Message();
        message.setTarget(handler);
        API.Login(message, username_data, password_data);
    }

}
