package com.qz.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qz.app.service.SampleResultService;
import com.qz.app.utils.TinkerUtils;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;


//这里的application是manifest里面的 不需要实际写出类
@DefaultLifeCycle(application = "com.qz.app.QZTinkerApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
//更改为继承DefaultApplicationLike
public class App extends DefaultApplicationLike {

    private static final String TAG = "App";
    public static App instance;
    public static Context mContext;
    public static UploadManager uploadManager;
    public App(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
               long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    //以前application写在oncreate的东西搬到这里来初始化
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        mContext = getApplication();
        MultiDex.install(base);
        TinkerInstaller.install(this, new DefaultLoadReporter(mContext), new DefaultPatchReporter(mContext), new DefaultPatchListener(mContext), SampleResultService.class, new UpgradePatch());
        instance = this;
        this.mContext = base;

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
//                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone1) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
         uploadManager = new UploadManager(config);


    }

//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
//        getApplication().registerActivityLifecycleCallbacks(callback);
//    }
}