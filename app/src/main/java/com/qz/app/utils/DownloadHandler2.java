package com.qz.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.widget.RemoteViews;

import com.qz.app.App;
import com.qz.app.R;
import com.qz.app.entity.DownloadItem;
import com.qz.app.view.CommAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by win7 on 2017/1/26.
 */

public class DownloadHandler2 {
    private static DownloadHandler2 instance;
    public static final int DOWNLOAD_UPDATE = 1; // 更新下载进度
    public static final int DOWNLOAD_COMPLETE = 2; // 下载完成
    public static final int DOWNLOAD_FAIL = 3; // 下载失败
    public static final int REQEUSTFAIL = 4;
    public static final int SDCARD_NOSPACE = 5;
    public static final int NETWORK_ERROR = 6;
    private File myCaptureFile;
    private Context mContext;

    private NotificationManager mNotificationManager;
    // private Notification mNotification;

    private RemoteViews mRemoveViews;
    private int mNotificationId = 1000;

    // private int mDownloadPrecent = 0; // 下载进度

    private CommAlertDialog customDialog;
    private ArrayList<String> urlList = new ArrayList<String>();



    public DownloadHandler2(Context mContext) {
        this.mContext = mContext;
    }

	public static DownloadHandler2 getInstance(Context mContext) {
		if (null == instance) {
			synchronized (DownloadHandler2.class) {
				if (null == instance) {
					instance = new DownloadHandler2(mContext);
				}
			}
		}
		return instance;
	}

    private  final OkHttpClient client = new OkHttpClient();
    private HashMap<String, Notification> notificationMap = new HashMap<String, Notification>();
    private  final String APK_PATH = Environment.getExternalStorageDirectory()
            + App.mContext.getString(R.string.saveDataPathRoot)
            + App.mContext.getString(R.string.saveDataPathAPK);
    private Handler mHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownloadItem downitem = (DownloadItem) msg.obj;
            if (msg != null) {

                switch (msg.what) {

                    case DOWNLOAD_UPDATE:

                        if (mNotificationManager == null) {
                            // L.i("init mNotificationManager", "1111111111");
                            mNotificationManager = (NotificationManager) mContext
                                    .getSystemService(Context.NOTIFICATION_SERVICE);
                            // mNotification = new Notification();
                            // mNotification.icon =
                            // android.R.drawable.stat_sys_download;
                            // // mNotification.tickerText =
                            // mContext.getString(R.string.app_name) + "更新";
                            // mNotification.tickerText = msg.obj.toString();
                            // mNotification.when = System.currentTimeMillis();
                            // mNotification.defaults = Notification.DEFAULT_LIGHTS;
                            // Intent intent = new
                            // Intent(mContext.getApplicationContext(),mContext.getClass());
                            // PendingIntent contentIntent =
                            // PendingIntent.getActivity(mContext, 0,
                            // intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            // mNotification.contentIntent = contentIntent;
                            // mRemoveViews = new
                            // RemoteViews(mContext.getPackageName(),
                            // R.layout.update);
                            // mNotification.contentView = mRemoveViews;
                            // mNotificationManager.notify(mNotificationId,mNotification);
                        }

                        Notification notifcation = getNotifcation(downitem.url);
                        notifcation.tickerText = downitem.itemName + "";
                        mRemoveViews = new RemoteViews(mContext.getPackageName(),
                                R.layout.update);
                        mRemoveViews.setTextViewText(R.id.down_title,
                                downitem.itemName);
                        mRemoveViews.setTextViewText(R.id.tvProcess, "已下载"
                                + downitem.itemPercent + "%");
                        mRemoveViews.setProgressBar(R.id.pbDownload, 100,
                                (int)downitem.itemPercent, false);

//					if (null != downitem.type
//							&& DOWNLOAD_MM.equals(downitem.type)) {
                        mRemoveViews.setImageViewResource(R.id.ivLogo,
                                R.mipmap.ic_launcher);
//					}
                        // else {
                        // mRemoveViews.setImageViewResource(R.id.ivLogo,
                        // R.drawable.about_tv_logo);
                        // }
                        notifcation.contentView = mRemoveViews;
                        mNotificationManager.notify(downitem.url.hashCode(),
                                notifcation);

                        // if (mNotificationManager != null) {
                        // mNotification.tickerText = msg.obj.toString();
                        // mRemoveViews.setTextViewText(R.id.tvProcess, "已下载"+
                        // mDownloadPrecent + "%");
                        // mRemoveViews.setProgressBar(R.id.pbDownload,
                        // 100,mDownloadPrecent, false);
                        // mNotification.contentView = mRemoveViews;
                        // mNotificationManager.notify(mNotificationId,mNotification);
                        // }
                        break;
                    case DOWNLOAD_COMPLETE:

                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
                        File newFile = new File(downitem.localFilePath);
                        install(newFile, mContext);
                        break;
                    case DOWNLOAD_FAIL:
                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
                        if (customDialog != null)
                            customDialog.dismiss();
                        CommonUtils.showToast(R.string.downloadfail);
                        break;
                    case NETWORK_ERROR:
                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
                        if (customDialog != null)
                            customDialog.dismiss();
                        CommonUtils.showToast(R.string.network_error);
                        break;
                    // case SHOW_WAITING:
                    // customDialog.show();
                    // break;
                    // case DISMISS_WAITINGL:
                    // customDialog.dismiss();
                    // break;
                    case REQEUSTFAIL:
                        CommonUtils.showToast(R.string.downloadfail);

                    case SDCARD_NOSPACE:

                        if (mNotificationManager != null
                                && notificationMap.get(downitem.url) != null) {
                            mNotificationManager.cancel(downitem.url.hashCode());
                            notificationMap.remove(downitem.url);
                        }
                        if (customDialog != null)
                            customDialog.dismiss();
                        // CommonUtil.showToast(R.string.downloadfail, 0);
                        CommonUtils.showToast(R.string.nofreespace);
                        break;
                    default:
                        break;
                }
            }
        }
    };



    public  void getData(final String url,final String title) {
       final  DownloadItem downitem = new DownloadItem();
        downitem.url = url;
        downitem.itemName = title;
        downitem.itemPercent = 0;
        if(!sdCardIsAvailable()){

        }
        if (!urlList.contains(url)) {
            urlList.add(url);
            final ProgressListener progressListener = new ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    L.v("%d%% done\n", (100 * bytesRead) / contentLength);
                    if (done) {
                        L.i("changName", "to create new file");
                        File newFile = new File(myCaptureFile.getParent()
                                + File.separator + url.substring(url.lastIndexOf("/") + 1));
                        L.i("changName",
                                "to create new file2"
                                        + newFile.getAbsolutePath());
                        if (newFile.exists()) {
                            newFile.delete();
                        }
                        try {
                            newFile.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendDwnloadFileMessage(downitem);
                            return;

                        }

                        L.i("changName", newFile.getName());
                        myCaptureFile.renameTo(newFile);
                        downitem.itemPercent = 100;
                        downitem.localFilePath = newFile.getAbsolutePath();
                        Message message = new Message();
                        message.what = DOWNLOAD_COMPLETE;
                        message.obj = downitem;
                        mHandler.sendMessage(message);
                        urlList.remove(url);
                    } else {
                        downitem.itemPercent = (100 * bytesRead) / contentLength;
                        downitem.itemName = title;
        //				downitem.type = type;
                        Message message = new Message();
                        message.what = DOWNLOAD_UPDATE;
                        message.obj = downitem;
                        mHandler.sendMessage(message);
                    }
                }
            };

            //添加拦截器，自定义ResponseBody，添加下载进度
            client.networkInterceptors().add(new Interceptor() {
                                                 @Override
                                                 public Response intercept(Chain chain) throws IOException {
                                                     Response originalResponse = chain.proceed(chain.request());
                                                     return originalResponse.newBuilder().body(
                                                             new ProgressResponseBody(originalResponse.body(), progressListener))
                                                             .build();

                                                 }
                                             }

            );

            //封装请求
            Request request = new Request.Builder().url(url).build();
            //发送异步请求
            client.newCall(request).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    sendDwnloadFileMessage(downitem);
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    //将返回结果转化为流，并写入文件
                                                    int len;
                                                    byte[] buf = new byte[2048];
                                                    InputStream inputStream = response.body().byteStream();
                                                    //可以在这里自定义路径
                                                    File dir = new File(APK_PATH);
                                                    if (dir.exists()) {
                                                        dir.delete();
                                                    }
                                                    dir.mkdirs();
                                                    myCaptureFile = new File(dir, url.hashCode() + "down.data");

                                                    FileOutputStream fileOutputStream = new FileOutputStream(myCaptureFile);

                                                    while ((len = inputStream.read(buf)) != -1) {
                                                        fileOutputStream.write(buf, 0, len);
                                                    }
                                                    fileOutputStream.flush();
                                                    fileOutputStream.close();
                                                    inputStream.close();
                                                }
                                            }
            );
        }
    }


    /**
     * 添加进度监听的ResponseBody
     */
    private static class ProgressResponseBody extends ResponseBody {
        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }


        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }


    }

    public interface ProgressListener {
        /**
         * @param bytesRead     已下载字节数
         * @param contentLength 总字节数
         * @param done          是否下载完成
         */
        void update(long bytesRead, long contentLength, boolean done);
    }

    private Notification getNotifcation(String url) {
        Notification notification = null;
        if (notificationMap.get(url) == null) {
            notification = new Notification();
            notification.icon = android.R.drawable.stat_sys_download;
            // mNotification.tickerText = mContext.getString(R.string.app_name)
            // + "更新";
            notification.when = System.currentTimeMillis();
            notification.defaults = Notification.DEFAULT_LIGHTS;
            Intent intent = new Intent(mContext.getApplicationContext(),
                    mContext.getClass());
            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.contentIntent = contentIntent;
            notificationMap.put(url, notification);
        } else {
            notification = notificationMap.get(url);
        }

        return notification;
    }

    // 安装下载后的apk文件
    private void install(File file, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);

    }
    private void sendDwnloadFileMessage(DownloadItem item) {
        Message message = new Message();
        message.what = DOWNLOAD_FAIL;
        message.obj = item;
        mHandler.sendMessage(message);
        urlList.remove(item.url);
    }


    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

}

