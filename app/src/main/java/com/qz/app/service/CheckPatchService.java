package com.qz.app.service;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.qz.app.App;
import com.qz.app.http.API;
import com.qz.app.utils.L;
import com.qz.app.utils.SimpleDownload;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * Created by du on 2017/2/10.
 */

public class CheckPatchService extends IntentService {
    private static final String TAG = "CheckPatchService";
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String url = "";
            SimpleDownload.downloadPatch(CheckPatchService.this, url, new SimpleDownload.OndownLoad() {
                @Override
                public void onFail() {

                }

                @Override
                public void onSuccess() {
                    TinkerInstaller.onReceiveUpgradePatch(App.mContext, Environment.getExternalStorageDirectory().getAbsolutePath() + "/QZpatch");

                }
            });
        }
    };
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public CheckPatchService() {
        super("CheckPatchService");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_REDELIVER_INTENT;
    }




    public static void start(Context context){
        Intent intent = new Intent(context,CheckPatchService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent in) {
                String action = in == null ? "" : in.getAction();
                L.i(TAG, "ScreenReceiver action [%s] ", action);
                if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Message message = new Message();
                    message.setTarget(handler);
                    API.checkPatch(message);

                }
            }
        }, filter);
    }
}
