package com.qz.app.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.qz.app.App;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DialogButtonsListener;
import com.tencent.tinker.android.dex.util.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class CommonUtils {

    private static final String TAG = "CommonUtils";
    private static Toast mToast;
    public static String g_ver_string = null;




    public static void setTextColor(TextView textView,int color,int from,int to){
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
        builder.setSpan(redSpan, from, to,   Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 各种转换 px to *
     * <p>
     * 第一个参数是unit
     * switch (unit) {
     * case COMPLEX_UNIT_PX:
     * return value 像素;
     * case COMPLEX_UNIT_DIP:
     * return value * metrics.density;（dp,dip）
     * case COMPLEX_UNIT_SP:
     * return value * metrics.scaledDensity;(sp字体大小)
     * case COMPLEX_UNIT_PT:
     * return value * metrics.xdpi * (1.0f/72);（pt）
     * case COMPLEX_UNIT_IN:
     * return value * metrics.xdpi;(不清楚)
     * case COMPLEX_UNIT_MM:
     * return value * metrics.xdpi * (1.0f/25.4f);（不清楚)
     *
     * @param value
     * @return
     */

    public static int getDpDementions(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, App.mContext.getResources().getDisplayMetrics());
    }

    public static int getSpDementions(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                value, App.mContext.getResources().getDisplayMetrics());
    }

    /**
     * 根据id获取文本
     */
    public static String getStringById(int id) {
        return App.mContext.getResources().getString(id);
    }

    /**
     * 设置当前时间格式
     *
     * @param format
     * @return
     */
    public static String getFormatTime(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    /**
     * 设置时间格式
     *
     * @param format
     * @return
     */
    public static String getFormatTime(String format, String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(Long.parseLong(data));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

//	/**
//	 * 封装url
//	 * @param context
//	 * @param url
//     * @return
//     */
//	public static String ConfigureFormatURL(Context context, String url) {
//		if (null == context || null == url) {
//			return "";
//		}
//		StringBuffer output = new StringBuffer(url);
//		if (!url.endsWith("&") && !url.endsWith("?")) {
//			if (url.contains("?"))
//				output.append("&");
//			else
//				output.append("?");
//		}
//		output.append("cp=");
//		output.append(encrypt("23E5BBF9&9#02E5B", initRawCipher(context)));
//		output.append("&nt=" + NetWorkUtils.getNetworkType(context));
//		L.v(TAG, "ConfigureFormatURL =", output.toString());
//		return output.toString();
//	}

    public static String initRawCipher(Context context) {
        StringBuffer sb = new StringBuffer();
//		sb.append("cv=" + configureGetVersion(context));
        sb.append("&uid=" + configureGetUID(context));
        sb.append("&imei=" + configureGetIMEI(context));
        sb.append("&imsi=" + configureGetIMSI(context));
        sb.append("&ua=" + configureGetUa());
        sb.append("&pfv=" + configureGetPlatformVersion());
        sb.append("&vc=" + configureGetVersionCode(context));
        L.v(TAG, "initRawCipher = ", sb.toString());
        return sb.toString();
    }

    public static String configureGetUID(Context context) {
        return DeviceUuidFactory.getInstance().getUuid(context);
    }

    public static String configureGetIMEI(Context context) {
        if (null == context) {
            return "";
        }
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei == null)
            imei = "";
        return imei;
    }

    public static String configureGetIMSI(Context context) {
        if (null == context) {
            return "";
        }
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        if (imsi == null)
            imsi = "";
        return imsi;
    }

    /**
     * 1.2.3.4.5.6.7 1 产品id 2 主版本号 3 子版本号 4 伪版本号 5 标识开发版 发布版 6 主渠道 7 子渠道
     */
//	public static String configureGetVersion(Context context) {
//		String versionName = "1.2.3.4.5.6.7";
//		if (null == context) {
//			return versionName;
//		}
//		if (null == g_ver_string) {
//			try {
//				AssetManager am = context.getAssets();
//				DataInputStream dis = new DataInputStream(
//						am.open("version.txt"));
//				byte[] buffer = new byte[dis.available()];
//				dis.readFully(buffer);
//				versionName = new String(buffer, "utf-8");
//				dis.close();
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			g_ver_string = versionName;
//		}
//		return g_ver_string;
//	}

    /**
     * 版本号
     *
     * @return
     */
    public static String configureGetPlatformVersion() {
        return new StringBuilder()
                .append(Build.VERSION.SDK_INT).toString();
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String configureGetUa() {
        return Build.MODEL;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String configureGetVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int configureGetVersionNum(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /* AES encrypt | decrypt_ */
    public static String encrypt(String key, String cleartext) {
        try {
            byte[] result = encrypt(key.getBytes(), cleartext.getBytes());
            byte[] data = Base64.encode(result, Base64.URL_SAFE
                    | Base64.NO_WRAP);
            String str = new String(data);
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            L.v(TAG, "encrypt", e.getMessage());
        }
        return "";
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void setTextView(TextView textView, String content) {
        if (!TextUtils.isEmpty(content)) {
            textView.setText(content);
        }
    }

    public static void showToast(String text) {

        if (null == mToast) {
            mToast = Toast.makeText(App.mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
        }
        if (!TextUtils.isEmpty(text)) {
            mToast.show();
        }

    }

    public static void showToast(int id) {
        showToast(App.mContext.getResources().getString(id));
    }


    /**
     * 检验手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean checkMobile(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0,6,7,8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        L.v(TAG, "isMobileNO", "m.matches(): " + m.matches());
        return m.matches();
    }

    public static void showErrorToast(String errorcode) {

    }

    /**
     * MD5加密
     *
     * @param plainText
     * @return
     */
    public static String Md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();// 32位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double[] getBaiDuPos(double[] gaodePos) {

        double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
        if (null == gaodePos || gaodePos.length < 2)
            return null;
        double[] baidupos = new double[2];
        double x = gaodePos[0];// PoiLocation.longitude;
        double y = gaodePos[1];// PoiLocation.latitude;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        baidupos[0] = z * Math.cos(theta) + 0.0065;
        baidupos[1] = z * Math.sin(theta) + 0.006;

        return baidupos;
    }

    /**
     * 百度转高德
     *
     * @param baiDu
     * @return
     */
    public static double[] getGaoDePos(double[] baiDu) {
        double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = baiDu[0] - 0.0065, y = baiDu[1] - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double[] gaode = new double[2];
        gaode[0] = z * Math.cos(theta);
        gaode[1] = z * Math.sin(theta);
        return gaode;
    }

    /**
     * 显示修改网络界面
     */
    public static void showChangeNetPage(final Context context) {

        final CommAlertDialog dialog = new CommAlertDialog(context);
//		dialog.setContentInfo(R.string.opennetsetting);
//		dialog.setTitleTv(R.string.opennetsetting_title);
        dialog.setButtonsListener(new DialogButtonsListener() {

            @Override
            public void onOKClick(Objects objects) {
                dialog.dismiss();
                context.startActivity(new Intent(
                        android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }

            @Override
            public void onCancleClick(Objects objects) {
                dialog.dismiss();
            }


        });
        dialog.show();
    }

//	public static void setListViewHeightBasedOnChildren(GridView listView) {
//		// 获取ListView对应的Adapter
//		ListAdapter listAdapter = listView.getAdapter();
//		if (listAdapter == null) {
//			// pre-condition
//			return;
//		}
//		int totalHeight = 0;
//
//		int count = listAdapter.getCount() / 2;
//		if (count <= 0) {
//			count = 1;
//		}
//		totalHeight = count * getDementions(R.dimen.namegride_height);
//
//		ViewGroup.LayoutParams params = listView.getLayoutParams();
//		params.height = totalHeight + getDementions(R.dimen.gridediv_height)
//				* (count - 1);
//		//
//		listView.setLayoutParams(params);
//	}

    /**
     * 指定文本颜色
     *
     * @param strs
     * @param start
     * @param end
     * @return
     */
    public static SpannableStringBuilder getStrWithRedPoint(String strs,
                                                            int start, int end) {

        SpannableStringBuilder builder = new SpannableStringBuilder(strs);
        // ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;

    }

    public static boolean isVirtualFile(Uri uri, String type) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return false;
        }
        if (!DocumentsContract.isDocumentUri(App.mContext, uri)) {
            return false;
        }

        Cursor cursor = App.mContext.getContentResolver().query(uri, new String[]{DocumentsContract.Document.COLUMN_FLAGS}, null, null, null);
        int flag = 0;
        if (cursor.moveToFirst()) {
            flag = cursor.getInt(0);
        }
        cursor.close();
        return flag == DocumentsContract.Document.FLAG_VIRTUAL_DOCUMENT;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)


    /**
     * uri 转流
     */
    public static InputStream getStreamFromUri(Uri uri, String type) {

        ContentResolver contentResolver = App.mContext.getContentResolver();
        if (isVirtualFile(uri, type)) {
            String[] result = contentResolver.getStreamTypes(uri, type);
            if (result == null || result.length <= 0) {
                return null;
            }
            try {
                return contentResolver.openAssetFileDescriptor(uri, result[0], null).createInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            return contentResolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 销毁栈内所有activity
     */
    public static void finishAfinight(AppCompatActivity activity) {
        ActivityCompat.finishAffinity(activity);
    }

    /**
     * @param packageName
     */
    public static void openThridAPK(String packageName) {
        App.mContext.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * @param packagename
     */
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = App.mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent();
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = App.mContext.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent


            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            resolveIntent.setComponent(cn);
            App.mContext.startActivity(resolveIntent);
        }
    }


    /**
     * 判断app是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    public static  String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }


    /**
     * Try to return the absolute file path from the given Uri
     *
     * @
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath( ContentResolver contentResolver, final Uri uri ) {
        /**
         * Gets the corresponding path to a file from the given content:// URI
         * @param selectedVideoUri The content:// URI to find the file path from
         * @param contentResolver The content resolver to use to perform the query.
         * @return the file path as a string
         */

            String filePath;
            String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        
            Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;

    }





/**
 * 提取图片的颜色
 * Palette.generateAsync(bitmap,
 new Palette.PaletteAsyncListener() {
@Override public void onGenerated(Palette palette) {
Palette.Swatch vibrant =
palette.getVibrantSwatch();
if (swatch != null) {
// If we have a vibrant color
// update the title TextView
titleView.setBackgroundColor(
vibrant.getRgb());
titleView.setTextColor(
vibrant.getTitleTextColor());
}
}
});
 */


}
