package com.qz.app.utils;

import android.content.Context;

import com.qz.app.http.API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by du on 2017/2/10.
 */

public class SimpleDownload {

    public interface OndownLoad {
             void onFail();
             void onSuccess();
    }

    public static void downloadPatch(Context context,String url,OndownLoad ondownLoad){

        download(context,url,ondownLoad,"QZpatch");
    }


    public static void download(Context context, String url, OndownLoad ondownLoad, String fileName) {
        FileOutputStream fout = null;
        InputStream is=null;
        try {
            OkHttpClient client = API.getOKHTTP();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
             is = response.body().byteStream();
            String path = context.getFilesDir().getAbsolutePath();
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            File destfile=new File(tempFile.getPath() + File.separator + fileName);
            if(destfile.exists()){
                destfile.delete();
            }
            destfile.createNewFile();
            fout = new FileOutputStream(destfile);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                fout.write(bs, 0, len);
            }
            ondownLoad.onSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            ondownLoad.onFail();
        }finally {
            try {
            if(null!=is){
                is.close();
            }
            if(null!=fout) {
                fout.close();
            }
            }catch (Exception e) {

            }
        }
    }
}
