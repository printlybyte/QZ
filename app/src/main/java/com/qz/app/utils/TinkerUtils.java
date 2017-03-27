package com.qz.app.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.qz.app.App;
import com.qz.app.service.CheckPatchService;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.util.List;


/**
 * Created by dyc on 2017/1/17.
 */

public class TinkerUtils {
    public static boolean isAppBackound;


    public static boolean isApplicationToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                isAppBackound = true;
                return true;
            }
        }
        isAppBackound = false;
        return false;
    }

    public static void startCheckPatchService(Context context){
        CheckPatchService.start(context);

    }

    public static void setPacthIsOk(boolean b){
        SharedPreferences sp = App.mContext.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS);
         sp.edit().putBoolean("patchIsOk", true).commit();
    }
      public static boolean getPacthIsOk(){
        SharedPreferences sp = App.mContext.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS);
       return sp.getBoolean("patchIsOk", false);
    }
}
