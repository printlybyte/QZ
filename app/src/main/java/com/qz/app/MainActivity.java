package com.qz.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.qz.app.base.BaseActivity;
import com.qz.app.base.BaseEntity;
import com.qz.app.base.BaseFragment;
import com.qz.app.easypermissions.AppSettingsDialog;
import com.qz.app.easypermissions.PermissionGrantUtils;
import com.qz.app.fragment.MessagepageFragment;
import com.qz.app.fragment.MypageFragment;
import com.qz.app.fragment.StatisticalpageFragment;
import com.qz.app.fragment.WorkpageFragment;
import com.qz.app.http.API;
import com.qz.app.utils.DataManagers;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.TinkerUtils;
import com.qz.app.utils.L;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;
import com.tencent.tinker.android.dex.util.FileUtils;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends BaseActivity implements PermissionGrantUtils.PermissionCallbacks, View.OnClickListener {
    public static final int RC_STORE = 123;
    private final int GET_TOKEN=234;
    private static final String TAG = "MainActivity";

    private LinearLayout messgepage_click;

    private LinearLayout statisticalpage_click;
    private LinearLayout workpage_click;
    private LinearLayout mypage_click;
    private View bottombar;
    private ImageView message_icon, statistical_icon, workimg, my_icon;
    private TextView message_tv, statistical_tv, work_tv, mytv;
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
                           String TOKEN = ((BaseEntity) msg.obj).token;
                            try {
                                DataManagers.saveImgToken(TOKEN);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                case API.REQUEST_FAIL:

                    clearWaiting();
                    break;
            }

        }
    };


    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.mContext.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MobclickAgent.setDebugMode(true);
        // MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        setContentView(R.layout.activity_main);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        // MobclickAgent.openActivityDurationTrack(false);


        //授权
        if (!PermissionGrantUtils.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE)) {
            PermissionGrantUtils.requestPermissions(this, getString(R.string.get_store_phonestate), RC_STORE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(ContextCompat.checkSelfPermission(App.mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this,
//                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
//                        321);
//            }
//        }
//        findViewById(R.id.bbbbb).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TinkerInstaller.onReceiveUpgradePatch(App.mContext, Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
////                 Intent intent = new Intent(MainActivity.this, LoginPage.class);
////                startActivity(intent);
//            }
//        });


        messgepage_click = (LinearLayout) findViewById(R.id.messgepage_click);
        statisticalpage_click = (LinearLayout) findViewById(R.id.statisticalpage_click);
        workpage_click = (LinearLayout) findViewById(R.id.workpage_click);
        mypage_click = (LinearLayout) findViewById(R.id.mypage_click);
        bottombar = findViewById(R.id.bottombar);
        message_icon = (ImageView) findViewById(R.id.message_icon);
        statistical_icon = (ImageView) findViewById(R.id.statistical_icon);
        workimg = (ImageView) findViewById(R.id.workimg);
        my_icon = (ImageView) findViewById(R.id.my_icon);

        message_tv = (TextView) findViewById(R.id.message_tv);
        statistical_tv = (TextView) findViewById(R.id.statistical_tv);
        work_tv = (TextView) findViewById(R.id.work_tv);
        mytv = (TextView) findViewById(R.id.mytv);


        messgepage_click.setOnClickListener(this);
        statisticalpage_click.setOnClickListener(this);
        workpage_click.setOnClickListener(this);
        mypage_click.setOnClickListener(this);
        setBottombar(0);
        FragmentManager.setFragmentWithStrName(this, MessagepageFragment.class.getName());
        getToken();
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        L.v(TAG, "##############onRequestPermissionsResult");
        PermissionGrantUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // 提示用户去应用设置界面手动开启权限
    @Override
    protected void onResume() {
        super.onResume();
        TinkerUtils.isAppBackound = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        TinkerUtils.isApplicationToBackground(App.mContext);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        L.v(TAG, "##############onPermissionsGranted");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        L.v(TAG, "################onPermissionsDenied");
//        if (PermissionGrantUtils.somePermissionPermanentlyDenied(this,perms)) {
        L.v(TAG, "################showDialog");
        new AppSettingsDialog.Builder(this, "为了让您能够很好使用本应用，请选择允许读写存储卡和打电话权限")
                .setTitle("授权提醒")
                .setPositiveButton("设置")
                .setRequestCode(RC_STORE)
                .build().setCancleAble(false)
                .show();
//        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_STORE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            if (!PermissionGrantUtils.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE)) {
                new AppSettingsDialog.Builder(this, "为了让您能够很好使用本应用，请选择允许读写存储卡和打电话权限,否则本应用会退出")
                        .setTitle("授权提醒")
                        .setPositiveButton("设置")
                        .setRequestCode(RC_STORE)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.this.finish();
                            }
                        })
                        .build().setCancleAble(false)
                        .show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messgepage_click:
                setBottombar(0);
                FragmentManager.setFragmentWithStrName(this, MessagepageFragment.class.getName());
                break;
            case R.id.statisticalpage_click:
                setBottombar(1);
                FragmentManager.setFragmentWithStrName(this, StatisticalpageFragment.class.getName());
                break;
            case R.id.workpage_click:
                setBottombar(2);
                FragmentManager.setFragmentWithStrName(this, WorkpageFragment.class.getName());
                break;
            case R.id.mypage_click:
                setBottombar(3);
                FragmentManager.setFragmentWithStrName(this, MypageFragment.class.getName());
                break;

        }
    }

    public void setBottombar(int num) {
        message_icon.setImageResource(R.drawable.message_normal);
        statistical_icon.setImageResource(R.drawable.tongji_normal);
        workimg.setImageResource(R.drawable.work_normal);
        my_icon.setImageResource(R.drawable.my_normal);
        message_tv.setTextColor(getResources().getColor(R.color.disable));
        statistical_tv.setTextColor(getResources().getColor(R.color.disable));
        work_tv.setTextColor(getResources().getColor(R.color.disable));
        mytv.setTextColor(getResources().getColor(R.color.disable));
        switch (num) {
            case 0:
                message_icon.setImageResource(R.drawable.message_select);
                message_tv.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 1:
                statistical_icon.setImageResource(R.drawable.tongji_select);
                statistical_tv.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 2:
                workimg.setImageResource(R.drawable.work_icon);
                work_tv.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 3:
                my_icon.setImageResource(R.drawable.mys_select);
                mytv.setTextColor(getResources().getColor(R.color.blue));
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment ft = getSupportFragmentManager().findFragmentById(R.id.fragmentlayout);
        if (null != ft && ft instanceof BaseFragment) {

            if (ft instanceof MessagepageFragment || ft instanceof MypageFragment || ft instanceof StatisticalpageFragment || ft instanceof WorkpageFragment) {
                final CommAlertDialog dialog = new CommAlertDialog(this);
                dialog.setTitleTv("退出提示");
                dialog.setContentInfo("   确定退出吗?");
                dialog.setButtonColor(R.color.blue);
                dialog.setCannelBtnName("取消");
                dialog.setOkBtnName("确定");

                dialog.setButtonsListener(new DialogButtonsListener() {
                    @Override
                    public void onOKClick(Objects objects) {
                        finish();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancleClick(Objects objects) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return ((BaseFragment) ft).onKeyDown(keyCode, event);
            }

            return ((BaseFragment) ft).onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }


    public void dismissBottom() {
        if (null != bottombar)
            bottombar.setVisibility(View.GONE);
    }

    public void showBottom() {
        if (null != bottombar)
            bottombar.setVisibility(View.VISIBLE);
    }


    public void getToken(){
        Message message = new Message();
        message.setTarget(handler);
        message.arg1 = GET_TOKEN;
        API.getToken(message);
    }


}

