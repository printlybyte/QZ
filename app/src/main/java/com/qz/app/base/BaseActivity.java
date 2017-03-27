package com.qz.app.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.qz.app.R;
import com.qz.app.utils.SystemBarTintManager;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class BaseActivity extends AppCompatActivity {

    public String TAG = ((Object) this).getClass().getSimpleName();
    private View waitinglayout, errorlayout;
    // private GifView loadinggif;
    private HashMap<String, Integer> watinglayoutMap = new HashMap<String, Integer>();
    private int waitingcount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watinglayoutMap.clear();

        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        // 支持透明状态栏和透明导航栏
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.white);
        setMiuiStatusBarDarkMode(this, true);
        setMeizuStatusBarDarkIcon(this, true);
    }

    public boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    public boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    protected void onResume() {
        super.onResume();
//		MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//		MobclickAgent.onPause(this);
    }

    /*************************************** loading 界面控制 ***************************************/
    /***************************************
     * 此功能 需要在每个界面嵌入 commloadinglayout.xml
     ***************************************/
    protected void initView() {
        // 等待进度条
        // 失败提示
        waitinglayout = findViewById(R.id.waitinglayout);
        // loadinggif = (GifView) findViewById(R.id.loading_gif);
        errorlayout = findViewById(R.id.errorlayout);
    }

    protected void showAnimloadingView() {
        // if (null != loadinggif) {
        // loadinggif.startAnim();
        // }
    }

    protected void DismissAnimloadingView() {
        // if (null != loadinggif) {
        // loadinggif.stopAnim();
        // }
    }

    /**
     * 添加actionbar返回按钮
     */
    protected void initActionBar() {

    }

    /**
     * 显示等待
     */
    public synchronized void showWaiting(String info) {
        waitingcount += 1;
        if (null != waitinglayout) {
            waitinglayout.setVisibility(View.VISIBLE);
        }
//		if (null != waitinglayout) {
//			int count = 0;
//			if (null != watinglayoutMap.get(info)) {
//				count = watinglayoutMap.get(info);
//			}
//			watinglayoutMap.put(info, count + 1);
//			waitinglayout.setVisibility(View.VISIBLE);
//		}

    }

    /**
     * 显示等待
     */
    public void showWaiting() {
        showWaiting("default");

    }

    public void clearWaiting() {
        clearWaiting("default");

    }

    /**
     * 消除等待
     */
    public synchronized void clearWaiting(String info) {
        waitingcount -= 1;
        if (waitingcount <= 0) {
            waitingcount = 0;
            if (null != waitinglayout) {
                waitinglayout.setVisibility(View.GONE);
            }
        }
//		int count = 0;
//		if (null != watinglayoutMap.get(info)) {
//			count = watinglayoutMap.get(info) - 1;
//		}
//		if (count <= 0) {
//			count = 0;
//		}
//		watinglayoutMap.put(info, count);
//		if (count == 0) {
//			if (null != waitinglayout) {
//				waitinglayout.setVisibility(View.GONE);
//			}
//		}
    }

    public void showErrorLayout(int StringId) {
        if (null != errorlayout) {
            if (null != errorlayout) {
                errorlayout.setVisibility(View.VISIBLE);
            }
        }

    }

    public void showErrorLayout() {

    }

    public void dismissErrorLayout() {
        if (null != errorlayout) {
            errorlayout.setVisibility(View.GONE);
        }
    }

    /***************************************
     * getListData
     ***************************************/

    public void getListData(int type) {

    }

    /**
     * 结合xlistView 使用
     *
     * @param handler
     * @param type     FragmentXlistViewController type REFRESH ，LOADMORE，LOAD_INIT
     * @param listSign 当前listView标示符，int类型，可以用 currentlistView.getHashCode();
     */
    public void getListData(Handler handler, int type, int listSign) {

    }
}
