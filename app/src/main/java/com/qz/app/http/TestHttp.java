package com.qz.app.http;

import com.google.gson.reflect.TypeToken;
import com.qz.app.entity.NetUser;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.L;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by du on 2017/2/11.
 */

public class TestHttp {
    public static final String TAG = "TestHttp";
    /**
     * 正式服务器
     */
//    private static final String SERVER_HOST = "";
    /**
     * 测试服务器
     */
    private static final String SERVER_HOST = "http://192.168.0.167:9091/";
    public static void main(String[] args) {
        HashMap map = new HashMap();
//        map.put("device", "2");
//        map.put("ver", "1.0");
        String subfix = "";
//      subfix= "latestappversion"; //版本检测
//        subfix = "admin/login";//登陆
//        map.put("username","15900000001");
//        map.put("password","123456");
//        subfix = "dept/getdatafortip?pid=0";
        map.put("id","1");
//        excuteTestUrl(Method.GET, null, SERVER_HOST+subfix );
//        subfix = "admin/employeelist";
//        subfix = "/document/info?token=2017021612055530327";
        subfix = "/flow-apply/apply?token=2017021612055530327";
        map.put("num","请假");

//        HashMap<String, String> datas = new HashMap<>();
//        map.put("page", "1");
//        map.put("limit", Integer.MAX_VALUE + "");
//        map.put("key", "李");
//        map.put("token", "2017021612055530327");
        excuteTestUrl(Method.GET, map, SERVER_HOST + subfix);
//      System.out.println(CommonUtils.convert("\\u5bc6\\u7801\\u9519\\u8bef"));
    }


    public static void excuteTestUrl(int method, HashMap<String, String> postData, String url) {
        System.out.println(url);
        System.out.println(postData);
        Request.Builder requestBuild = new Request.Builder().url(url);
        if (Method.GET == method) {
//            L.v(TAG, "method", "GET");
            url += "&device=2&ver=1.0";
        } else if (Method.POST == method) {
            FormBody.Builder formBody = new FormBody.Builder();
            if (null != postData && postData.size() > 0) {
                Iterator<String> iter = postData.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next();
                    String value = postData.get(key);
                    formBody.add(key, value);
                }
                requestBuild.post(formBody.build());
            }

        }
        new OkHttpClient().newCall(requestBuild.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("fail fail getData");

            }

            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("onResponse onResponse getData");

                try {

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
