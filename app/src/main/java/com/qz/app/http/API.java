package com.qz.app.http;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.app.App;
import com.qz.app.base.BaseEntity;
import com.qz.app.constant.Constant;
import com.qz.app.entity.CheckVersion;
import com.qz.app.entity.DepAndEmp;
import com.qz.app.entity.DepEntity;
import com.qz.app.entity.Documentdetail;
import com.qz.app.entity.HuibaoDetail;
import com.qz.app.entity.HuibaoEntity;
import com.qz.app.entity.NetDocument;
import com.qz.app.entity.NetTask;
import com.qz.app.entity.NetUser;
import com.qz.app.entity.SearchDemp;
import com.qz.app.entity.ShenPiEntity;
import com.qz.app.entity.ShenpiDetail;
import com.qz.app.entity.TaskDetailEntity;
import com.qz.app.entity.UserDetailNet;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.L;
import com.qz.app.utils.NetWorkUtils;
import com.qz.app.utils.PreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by win7 on 2017/1/24.
 */

public class API {


    private static final String TAG = "API";
    /**
     * 请求成功
     */
    public static final int REQUEST_SUCCESS = 1;
    /**
     * 请求失败
     */
    public static final int REQUEST_FAIL = 2;
    /**
     * 请求开始
     */
    public static final int REQUEST_BEGIN = 3;
    /**
     * 没有网络
     */
    public static final int REQUEST_NO_NETWORK = 4;
    /**
     * 正式服务器
     */
//    private static final String SERVER_HOST = "";
    /**
     * 测试服务器
     */
    public static final String SERVER_HOST = "http://192.168.0.167:9091/";
    /**
     * 检测补丁
     */
    private static final String CHECKPATCH = "";

    public static OkHttpClient getOKHTTP() {
        OkHttpClient okHttpClient = null;
//        long cacheSize = 1024 * 1024 * 20;//缓存文件最大限制大小20M
//        String cachedirectory = App.mContext.getPackageName() + "/caches";
//        Cache cache = new Cache(new File(cachedirectory), 1);//设置缓存文件路径
        if (null == okHttpClient) {
            synchronized (API.class) {
                if (null == okHttpClient) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(8, TimeUnit.SECONDS);  //设置连接超时时间
                    builder.writeTimeout(8, TimeUnit.SECONDS);//设置写入超时时间
                    builder.readTimeout(8, TimeUnit.SECONDS);//设置读取数据超时时间
//                    builder.retryOnConnectionFailure(false);//设置不进行连接失败重试
//                    builder.cache(cache);//缓存
                    okHttpClient = builder.build();

                }
            }
        }
        return okHttpClient;
    }


    /**
     * Get方法
     *
     * @param tag
     */
    private static void excuteHttpGet(Message msg, final String tag, String subfix, HashMap<String, String> getdata,
                                      java.lang.reflect.Type type, boolean needtoken) {
        String url = SERVER_HOST + subfix;
        url += "?" + encodeParams(getdata) + getCommParams(needtoken);
//      url = CommonUtils.ConfigureFormatURL(App.mContext, url);
        excuteHttp(msg, tag, Method.GET, type, url, null);
    }

    /**
     * @param msg
     * @param tag
     * @param type
     * @param subfix
     * @param postData
     * @param needtoken false=不需要token
     */
    private static void excuteHttpPost(Message msg, final String tag,
                                       java.lang.reflect.Type type, String subfix,
                                       HashMap<String, String> postData, boolean needtoken) {
        String url = SERVER_HOST;
        url += subfix + "/";
//        url = CommonUtils.ConfigureFormatURL(App.mContext, url);

        if (null == postData) {
            postData = new HashMap<>();
        }
        postData.putAll(getCommParamsWithMap(needtoken));
        excuteHttp(msg, tag, Method.POST, type, url, postData);
    }

    /**
     * @param msg
     * @param tag
     * @param type
     * @param subfix
     * @param postData
     * @param needtoken false=不需要token
     */
    private static void excuteHttpPostWithFile(Message msg, final String tag,
                                               java.lang.reflect.Type type, String subfix,
                                               HashMap<String, String> postData, boolean needtoken, MediaType mediaType, String fileName, File[] files) {
        String url = SERVER_HOST;
        url += subfix + "/";
//        url = CommonUtils.ConfigureFormatURL(App.mContext, url);

        if (null == postData) {
            postData = new HashMap<>();
        }
        postData.putAll(getCommParamsWithMap(needtoken));
        excuteHttpWithFile(msg, tag, Method.POST, type, url, postData, mediaType, fileName, null);
    }


    /**
     * @param msg
     * @param url
     * @param tag
     * @param method
     * @param type
     */
    public static void excuteHttp(final Message msg, final String tag, int method, final java.lang.reflect.Type type, String url, final HashMap<String, String> postData) {
        excuteHttpWithFile(msg, tag, method, type, url, postData, null, null, null);
//        L.v(TAG, tag, url);
//        L.v(TAG, tag, null == postData ? "null" : postData.toString());
////        if (!NetWorkUtils.isNetworkAvailable(App.mContext,false)) {
////            final Handler handler = msg.getTarget();
////            Message message = new Message();
////            message.what = REQUEST_NO_NETWORK;
////            message.arg1 = msg.arg1;
////            message.setData(msg.getData());
////            handler.sendMessage(message);
////            return;
////        }
//        final Handler handler = msg.getTarget();
//        Message message = new Message();
//        message.what = REQUEST_BEGIN;
//        message.arg1 = msg.arg1;
//        message.arg2 = msg.arg2;
//
//        message.setData(msg.getData());
//        handler.sendMessage(message);
//
//        Request.Builder requestBuild = new Request.Builder().url(url);
//        if (method == Method.GET) {
//            L.v(TAG, "method", "GET" + url);
//
//        } else if (method == Method.POST) {
//            MultipartBody.Builder formBody = new MultipartBody.Builder();
//            if (null != postData && postData.size() > 0) {
//                Iterator<String> iter = postData.keySet().iterator();
//                while (iter.hasNext()) {
//                    String key = iter.next();
//                    String value = postData.get(key);
//                    formBody.addFormDataPart(key, value);
//                }
//                requestBuild.post(formBody.build());
//            }
//
//        }
//        getOKHTTP().newCall(requestBuild.build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                L.v(TAG, "fail", "fail getData");
//                Message message = msg;
//                if (null != e) {
//                    message.obj = e.toString();
//                }
//                message.what = REQUEST_FAIL;
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                L.v(TAG, "onResponse", "onResponse getData");
//                Message message = msg;
//                try {
//
//
//                    L.v(TAG, "parseNetworkResponse", "have get datas");
//                    String json = new String(response.body().bytes(), "utf-8");
//                    L.v(TAG, "parseNetworkResponse json:", json.toString());
//                    Gson gson = new Gson();
//                    final BaseEntity entity = gson.fromJson(json, type);
//                    L.v(TAG, "entity", entity.msg);
////                    1)	code，处理结果代码
////                    用于标识本次请求的执行结果。
////                    0表示处理成功。
////                    10100表示无访问接口权限。
////                    10101表示访问别人权限范围内的数据、业务（欺骗访问）。
////                    2)	msg，处理结果信息
////                    一般用于记录错误信息。APP有时候会以此信息来提示用户。
//
//                    if (null != response && (entity instanceof BaseEntity)) {
//                        if (((BaseEntity) entity).code.equals("0")) {
//                            msg.what = REQUEST_SUCCESS;
//                            message.obj = entity;
//                            handler.sendMessage(message);
//                            L.v(TAG, "success", "success", ((BaseEntity) entity).msg);
//                        } else if (((BaseEntity) entity).code.equals("10100")) {
//
//                            L.v(TAG, " 10100fail", "fail getData");
//                            message.obj = ((BaseEntity) entity).msg;
//                            message.what = REQUEST_FAIL;
//                            handler.sendMessage(message);
//                        } else if (((BaseEntity) entity).code.equals("10101")) {
//                            L.v(TAG, "10101 fail", "fail getData");
//                            message.what = REQUEST_FAIL;
//                            message.obj = ((BaseEntity) entity).msg;
//                            handler.sendMessage(message);
//
//                        } else {
//                            message.what = REQUEST_FAIL;
//                            message.obj = ((BaseEntity) entity).msg;
//                            handler.sendMessage(message);
//                            L.v(TAG, " ese fail", "fail getData", ((BaseEntity) entity).msg);
//                        }
//                    } else {
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    L.v(tag, "error:");
//                }
//            }
//        });
    }


    /**
     * @param msg
     * @param url
     * @param tag
     * @param method
     * @param type
     */
    public static void excuteHttpWithFile(final Message msg, final String tag, int method, final java.lang.reflect.Type type, String url, final HashMap<String, String> postData, MediaType mediaType, final String filename, final File[] files) {


        L.v(TAG, tag, url);
        L.v(TAG, tag, null == postData ? "null" : postData.toString());
//        if (!NetWorkUtils.isNetworkAvailable(App.mContext,false)) {
//            final Handler handler = msg.getTarget();
//            Message message = new Message();
//            message.what = REQUEST_NO_NETWORK;
//            message.arg1 = msg.arg1;
//            message.setData(msg.getData());
//            handler.sendMessage(message);
//            return;
//        }
        final Handler handler = msg.getTarget();
        Message message = new Message();
        if (!NetWorkUtils.isNetworkAvailable(App.mContext, true)) {
            message.what = REQUEST_FAIL;
            message.arg1 = msg.arg1;
            message.arg2 = msg.arg2;
            message.setData(msg.getData());
            handler.sendMessage(message);
            return;
        }


        message.what = REQUEST_BEGIN;
        message.arg1 = msg.arg1;
        message.arg2 = msg.arg2;

        message.setData(msg.getData());
        handler.sendMessage(message);

        Request.Builder requestBuild = new Request.Builder().url(url);
        if (method == Method.GET) {
            L.v(TAG, "method", "GET" + url);

        } else if (method == Method.POST) {
            MultipartBody.Builder multBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//            FormBody.Builder formBodybulid = new FormBody.Builder();
            if (null != postData && postData.size() > 0) {
                Iterator<String> iter = postData.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next();
                    String value = postData.get(key);
//                    formBodybulid.add(key, value);
                    multBody.addFormDataPart(key, value);
                }
//                multBody.addPart(formBodybulid.build());
            }
//           MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            if (null != files && files.length > 0) {
                for (File file : files) {
                    if (null != file) {
                        L.v(TAG, "addfileaddfileaddfileaddfileaddfile");
                        multBody.addFormDataPart(filename, null, RequestBody.create(mediaType, file));
                    }
                }
            }
            requestBuild.post(multBody.build());
        }
        getOKHTTP().newCall(requestBuild.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.v(TAG, "fail", "fail getData");
                Message message = msg;
                if (null != e) {
                    message.obj = e.toString();
                }
                message.what = REQUEST_FAIL;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) {
                L.v(TAG, "onResponse", "onResponse getData");
                Message message = msg;
                try {
                    L.v(TAG, "parseNetworkResponse", "have get datas");
                    String json = new String(response.body().bytes(), "utf-8");
                    L.v(TAG, "parseNetworkResponse json:", json.toString());
                    Gson gson = new Gson();
                    final BaseEntity entity = gson.fromJson(json, type);
//                    L.v(TAG, "entity", entity.token);
//                    1)	code，处理结果代码
//                    用于标识本次请求的执行结果。
//                    0表示处理成功。
//                    10100表示无访问接口权限。
//                    10101表示访问别人权限范围内的数据、业务（欺骗访问）。
//                    2)	msg，处理结果信息
//                    一般用于记录错误信息。APP有时候会以此信息来提示用户。

                    if (null != response && (entity instanceof BaseEntity)) {
                        if (((BaseEntity) entity).code.equals("0")) {
                            msg.what = REQUEST_SUCCESS;
                            message.obj = entity;
                            handler.sendMessage(message);
                            L.v(TAG, "success", "success", ((BaseEntity) entity).msg);
                        } else if (((BaseEntity) entity).code.equals("10100")) {

                            L.v(TAG, " 10100fail", "fail getData");
                            message.obj = ((BaseEntity) entity).msg;
                            message.what = REQUEST_FAIL;
                            handler.sendMessage(message);
                        } else if (((BaseEntity) entity).code.equals("10101")) {
                            L.v(TAG, "10101 fail", "fail getData");
                            message.what = REQUEST_FAIL;
                            message.obj = ((BaseEntity) entity).msg;
                            handler.sendMessage(message);

                        } else {
                            message.what = REQUEST_FAIL;
                            message.obj = ((BaseEntity) entity).msg;
                            handler.sendMessage(message);
                            L.v(TAG, " ese fail", "fail getData", ((BaseEntity) entity).msg);
                        }
                    } else {
                        message.what = REQUEST_FAIL;
                        message.obj = ((BaseEntity) entity).msg;
                        handler.sendMessage(message);
//                        L.v(TAG, " ese fail", "fail getData", ((BaseEntity) entity).msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    L.v(tag, "error:");
                }
            }
        });
    }


    public static String encodeParams(HashMap<String, String> maps) {

        String url = "";
        if (null != maps && maps.size() > 0) {
            L.v(TAG, "encodeParams", maps.toString());
            Iterator<String> iter = maps.keySet().iterator();

            while (iter.hasNext()) {
                String key = iter.next();
                String value = maps.get(key);
                if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key))
                    url += key + "=" + Uri.encode(value) + "&";
            }
//            if (!TextUtils.isEmpty(url) && url.endsWith("&")) {
//                url = url.substring(0, url.lastIndexOf("&"));
//            }
        }
        return url;
    }

    private static String getCommParams(boolean needtoken) {
        String param = "device=2&v=" + CommonUtils.configureGetVersionCode(App.mContext);
        if (needtoken) {
            param += "&token=" + PreferencesUtils.getString(App.mContext, Constant.SP_TOKEN, "");
        }
        return param;
    }

    private static HashMap getCommParamsWithMap(boolean needtoken) {
        HashMap map = new HashMap();
        map.put("device", "2");
        map.put("ver", CommonUtils.configureGetVersionCode(App.mContext));
        if (needtoken) {
            map.put("token", PreferencesUtils.getString(App.mContext, Constant.SP_TOKEN, ""));
        }
        return map;
    }

    public static void putData(HashMap<String, String> datas, String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            datas.put(key, value);
        }

    }


    public static void putData(MultipartBody.Builder builder, String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            builder.addFormDataPart(key, value);
        }


    }


    public static void checkVersion(Message message) {
        java.lang.reflect.Type type = new TypeToken<CheckVersion>() {
        }.getType();
        excuteHttpPost(message, "latestappversion", type, null, null, false);
    }


    public static void checkPatch(Message message) {
        Type type = null;
        excuteHttpPost(message, "checkPatch", type, null, null, false);
    }

/************************************************************************部门接口******************************************************************************/

    /**
     * 获取部门员工首页
     *
     * @param message
     * @param pid
     */
    public static void getDepAndEmpl(Message message, String pid) {
        java.lang.reflect.Type type = new TypeToken<DepAndEmp>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("pid", pid);
        excuteHttpGet(message, "getDepAndEmpl", "dept/getdatafortip", datas, type, true);
    }


//    public static <T> void userFeedBack(final Message msg, final String feedback) {
//        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
//        }.getType();
//        HashMap<String, String> maps = new HashMap<String, String>();
//        maps.put("_mt", FEEDBACK);
//
//        HashMap<String, String> postData = new HashMap<String, String>();
//        postData.put("feedBack", feedback);
//        excuteHttpPost(msg, "userFeedBack", BaseEntity.class, type, maps,
//                postData);
//    }
//    /**
//     * 修改密码
//     */
//    public static void changePwd(Message msg, String phone, String oldpwd,
//                                 String newpwd) {
//        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
//        }.getType();
//        HashMap<String, String> maps = new HashMap<String, String>();
//        maps.put("_mt", CHANGPWD);
//        maps.put("phone", phone);
//        maps.put("password1", CommonUtils.Md5(oldpwd));
//        maps.put("password2", CommonUtils.Md5(newpwd));
//        excuteHttpGet(msg, "changePwd", BaseEntity.class, type, maps);
//    }

    /**
     * 获取部门
     *
     * @param message
     * @param pid
     */
    public static void getDepDatas(Message message, String pid) {
        java.lang.reflect.Type type = new TypeToken<DepEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("pid", pid);
        excuteHttpGet(message, "getDepDatas", "dept/list", datas, type, true);
    }


    public static void delDep(Message message, String id) {
        java.lang.reflect.Type type = new TypeToken<UserDetailNet>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("id", id);
        excuteHttpGet(message, "delDep", "dept/deldata", datas, type, true);
    }


    public static void addDEP(Message msg, String id, String name, String depId, String headid, String sort) {
        java.lang.reflect.Type type = new TypeToken<SearchDemp>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("id", id);
        datas.put("name", name + "");
        datas.put("pid", depId + "");
        datas.put("headid", headid + "");
        datas.put("sort", sort + "");
        excuteHttpPost(msg, "addDEP", type, "dept/add", datas, true);
    }

    public static void getNoDepEmp(Message message) {
        java.lang.reflect.Type type = new TypeToken<SearchDemp>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        excuteHttpPost(message, "getNoDepEmp", type, "dept/nonedata", datas, true);

    }

    public static void leaveEmpList(Message message) {
        java.lang.reflect.Type type = new TypeToken<SearchDemp>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        excuteHttpPost(message, "leaveEmpList", type, "dept/quitdata", datas, true);

    }


/************************************************************************员工接口******************************************************************************/
    /**
     * @param message
     * @param id
     */
    public static void getEmpDetail(Message message, String id) {
        java.lang.reflect.Type type = new TypeToken<UserDetailNet>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("id", id);
        excuteHttpGet(message, "getEmpDetail", "admin/loadadmin", datas, type, true);


    }

    public static void add_update_user(Message message, String id, String namtv, String worknumtv, String gendertv, String phonetv, String depttv, String deptId, String jobstv, String emailtv, String grandtimetv, String incommetv, String nationaltv, String nativetv, String idcardtv, String birthdaytv, String marrytv, String adresstv, String emergencyphonetv, String emergencypepoletv) {

        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "id", id);
        putData(datas, "name", namtv);
        putData(datas, "mobile", phonetv);
        putData(datas, "sex", gendertv);
        putData(datas, "deptname", depttv);
        putData(datas, "deptid", deptId);
        putData(datas, "workdate", incommetv);
        putData(datas, "email", emailtv);
        putData(datas, "jobnumber", worknumtv);
        putData(datas, "graduationtime", grandtimetv);
        putData(datas, "nation", nationaltv);
        putData(datas, "birthplace", nativetv);
        putData(datas, "marital_status", marrytv);
        putData(datas, "address", adresstv);
        putData(datas, "contact", emergencypepoletv);
        putData(datas, "contact_lx", emergencyphonetv);

//        datas.put("education",emergencyphonetv+"");//学历
//        datas.put("tel",emergencyphonetv+"");//电话

        excuteHttpPost(message, "add_update_user", type, "admin/add", datas, true);
    }

    /**
     * 获取员工列表
     *
     * @param message
     * @param content 根据content搜索援用
     * @param depId   根据id获取员工
     */
    public static void searchEmp(Message message, String content, String depId) {
        java.lang.reflect.Type type = new TypeToken<SearchDemp>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("page", "1");
        datas.put("limit", Integer.MAX_VALUE + "");
        if (!TextUtils.isEmpty(content)) {
            datas.put("key", content);
        }
        if (!TextUtils.isEmpty(depId)) {
            datas.put("dept", depId);
        }
        excuteHttpPost(message, "searchEmp", type, "/admin/employeelist", datas, true);
    }


    public static void Login(Message message, String username, String password) {
        java.lang.reflect.Type type = new TypeToken<NetUser>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("username", username);
        datas.put("password", password);
        excuteHttpPost(message, "Login", type, "admin/login", datas, false);
    }


//        @Deprecated
//    public static void moveEmp(Message message) {
//        java.lang.reflect.Type type = new TypeToken<SearchDemp>() {
//        }.getType();
//        HashMap<String,String> datas = new HashMap<>();
//        excuteHttpPost(message, "leaveEmpList", type, "dept/quitdata", datas,true);
//    }

    /************************************************************************
     * 公司制度接口
     ******************************************************************************/

    public static void getDocumentlist(Message message, String key, String deptId) {
        java.lang.reflect.Type type = new TypeToken<NetDocument>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("page", "1");
        datas.put("limit", "2000");
//        datas.put("dept","deptId");
//        datas.put("key","key");
        putData(datas, "dept", deptId);
        putData(datas, "key", key);

        excuteHttpPost(message, "getDocumentlist", type, "document/list", datas, true);
    }


    public static void getDocDetail(Message message, String id) {

        java.lang.reflect.Type type = new TypeToken<Documentdetail>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("id", id);
        excuteHttpGet(message, "getDocDetail", "document/info", datas, type, true);
    }


    /************************************************************************
     * 办公 工作 任务
     ******************************************************************************/


    public static void getTaskList(Message message, String key, String time, int status, String pid, int page) {
        getTaskList(message, key, time, status, pid, page, null);

    }

    public static void getTaskList(Message message, String key, String time, int status, String pid, int page, String limit) {
        java.lang.reflect.Type type = new TypeToken<NetTask>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        if (!TextUtils.isEmpty(limit)) {
            putData(datas, "limit", limit);
        } else {
            putData(datas, "limit", Constant.NET_PAGE_PER_NUM + "");
        }
        putData(datas, "page", page + "");
        putData(datas, "pid", pid);
        putData(datas, "key", key);
        putData(datas, "time", time);
        if (status != -1) {
            putData(datas, "status", status + "");
        } else {
            putData(datas, "status", "");
        }
        excuteHttpPost(message, "getTaskList", type, "task/mylist", datas, true);
    }

    public static void getExcuteTaskList(Message message, String key, String time, int status, String pid, int page) {
        getExcuteTaskList(message, key, time, status, pid, page, null);
    }

    public static void getExcuteTaskList(Message message, String key, String time, int status, String pid, int page, String limit) {
        java.lang.reflect.Type type = new TypeToken<NetTask>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        if (!TextUtils.isEmpty(limit)) {
            putData(datas, "limit", limit);
        } else {
            putData(datas, "limit", Constant.NET_PAGE_PER_NUM + "");
        }
        putData(datas, "page", page + "");
        putData(datas, "pid", pid);
        putData(datas, "key", key);
        putData(datas, "time", time);
        excuteHttpPost(message, "getTaskList", type, "task/execute", datas, true);
    }


    public static void getTaskDetail(Message message, String id) {

        java.lang.reflect.Type type = new TypeToken<TaskDetailEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("id", id);
        excuteHttpGet(message, "getTaskDetail", "task/show", datas, type, true);
    }

    /**
     * 完成任务
     *
     * @param message
     * @param id
     */
    public static void finshTask(Message message, String id) {
        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("id", id);
        excuteHttpGet(message, "finshTask", "task/finish", datas, type, true);
    }

    public static void delTask(Message message, String id) {
        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        datas.put("id", id);
        excuteHttpGet(message, "delTask", "task/deltask", datas, type, true);
    }

    public static void update_task(Message message, String id, String pid, String liable_person, String title, String enddate, String member, String imgurl, File[] files) {
        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "id", id);
        putData(datas, "pid", pid);
        putData(datas, "title", title);
        putData(datas, "enddate", enddate);
        putData(datas, "liable_id", liable_person);

        if (!TextUtils.isEmpty(imgurl)) {
            if (imgurl.endsWith(",")) {
                imgurl = imgurl.substring(0, imgurl.length() - 1);
            }
        }
        putData(datas, "thumbpath", imgurl);

//        putData(datas, "member", member);
//        putData(datas, "imgurl", imgurl);
        MediaType MEDIA_TYPE = MediaType.parse("image/*");
        excuteHttpPostWithFile(message, "update_task", type, "task/save", datas, true, MEDIA_TYPE, "img", files);
//        excuteHttpPost(message, "update_task", type, "task/save", datas, true);
    }

    public static void DISCUSSTASK(Message message, String contentstr, String id) {
        java.lang.reflect.Type type = new TypeToken<NetTask>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "content", contentstr + "");
        putData(datas, "id", id);
        excuteHttpPost(message, "DISCUSSTASK", type, "task/commit", datas, true);
    }


    /**
     * 我发起的审批
     *
     * @param message
     */
    public static void getMyShenPi(Message message, String key, int flow_id, int status, String time, int page, String limit) {
        java.lang.reflect.Type entitytype = new TypeToken<ShenPiEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        if (!TextUtils.isEmpty(limit)) {
            putData(datas, "limit", limit);
        } else {
            putData(datas, "limit", Constant.NET_PAGE_PER_NUM + "");
        }
        putData(datas, "page", page + "");
        putData(datas, "key", key);
        if (flow_id != 0) { //全部传空
            putData(datas, "flow_id", flow_id + "");
        }
        if (status != 3) {//全部传空
            putData(datas, "status", status + "");
        }
        putData(datas, "time", time + "");
        excuteHttpPost(message, "getMyShenPi", entitytype, "flow-check/mylist", datas, true);


    }

    /**
     * 待我的审批
     *
     * @param message
     */
    public static void getDaiwoShenPi(Message message, String key,int flow_id,int page, String limit,String starttime,String endtime) {
        java.lang.reflect.Type entitytype = new TypeToken<ShenPiEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        if (!TextUtils.isEmpty(limit)) {
            putData(datas, "limit", limit);
        } else {
            putData(datas, "limit", Constant.NET_PAGE_PER_NUM + "");
        }
        putData(datas, "page", page + "");
        putData(datas, "key", key);
        putData(datas, "flow_id", flow_id+"");
        putData(datas, "start_time", starttime);
        putData(datas, "end_time", endtime);
        excuteHttpPost(message, "getDaiwoShenPi", entitytype, "flow-check/mycheck", datas, true);

    }

    /**
     * 我审批的
     *
     * @param message
     */
    public static void
    getShenPi(Message message, String key, int flow_id, int status, String time, int page, String limit) {
        java.lang.reflect.Type entitytype = new TypeToken<ShenPiEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        if (!TextUtils.isEmpty(limit)) {
            putData(datas, "limit", limit);
        } else {
            putData(datas, "limit", Constant.NET_PAGE_PER_NUM + "");
        }
        putData(datas, "page", page + "");
        putData(datas, "key", key);
        putData(datas, "flow_id", flow_id + "");
        putData(datas, "status", status + "");
        putData(datas, "time", time + "");
        excuteHttpPost(message, "getShenPi", entitytype, "flow-check/mychecked", datas, true);

    }

    /**
     * 转发我
     *
     * @param message
     */
    public static void getChaosongShenPi(Message message, String key, int flow_id, int status, String time, int page, String limit) {
        java.lang.reflect.Type entitytype = new TypeToken<ShenPiEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        if (!TextUtils.isEmpty(limit)) {
            putData(datas, "limit", limit);
        } else {
            putData(datas, "limit", Constant.NET_PAGE_PER_NUM + "");
        }
        putData(datas, "page", page + "");
        putData(datas, "key", key);
        putData(datas, "flow_id", flow_id + "");
        putData(datas, "status", status + "");
        putData(datas, "time", time + "");
        excuteHttpPost(message, "getChaosongShenPi", entitytype, "flow-check/mycopylist", datas, true);

    }


    public static void getToken(Message message) {

        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        excuteHttpGet(message, "getToken", "qiniu/gettoken", null, type, true);
    }

    public static void getShenPiDetail(Message message, String id) {

        java.lang.reflect.Type type = new TypeToken<ShenpiDetail>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "id", id);
        excuteHttpGet(message, "getShenPiDetail", "flow-check/checkshow", datas, type, true);

    }

    public static void DealWithShenPi(Message message, String id, String check_type, String content, String copy_list, String imgfile) {

        java.lang.reflect.Type entitytype = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "id", id + "");
        putData(datas, "check_type", check_type + "");
        putData(datas, "content", content);
        putData(datas, "copy_list", copy_list);
        putData(datas, "imgurl", imgfile);
        excuteHttpPost(message, "DealWithShenPi", entitytype, "flow-check/check", datas, true);
    }

    public static void qingjia(Message message, String modeid, String modenum, String kind, String qjkind, String stime, String etime, String totals, String explain, String receid, String copy_list) {


        java.lang.reflect.Type entitytype = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "modeid", modeid);
        putData(datas, "modenum", modenum);
        putData(datas, "kind", kind);
        putData(datas, "qjkind", qjkind);
        putData(datas, "stime", stime);
        putData(datas, "etime", etime);
        putData(datas, "totals", totals);
        putData(datas, "explain", explain);
        putData(datas, "receid", receid);
        putData(datas, "copy_list", copy_list);
        excuteHttpPost(message, "qingjia", entitytype, "flow-apply/flowsave", datas, true);
    }

    /**
     *
     * @param message
     * @param key
     * @param thetype
     * @param starttime
     * @param endtime
     * @param deptId  empId  没用到这两个
     *
     */
    public static void getMyHuiBao(Message message, String key, int thetype, String starttime, String endtime, String deptId, String empId) {

        java.lang.reflect.Type entitytype = new TypeToken<HuibaoEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "key", key);
        putData(datas, "thetype", thetype+"");
        putData(datas, "starttime", starttime);
        putData(datas, "endtime", endtime);
//        putData(datas, "deptId", deptId);
//        putData(datas, "empId", empId);
        excuteHttpPost(message, "getMyHuiBao", entitytype, "report/mylist", datas, true);
//
    }
    public static void getToMeHuiBao(Message message, String key, int thetype, String starttime, String endtime, String deptId, String empId) {
        java.lang.reflect.Type entitytype = new TypeToken<HuibaoEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "key", key);
        putData(datas, "thetype", thetype+"");
        putData(datas, "starttime", starttime);
        putData(datas, "endtime", endtime);
        putData(datas, "deptId", deptId);
        putData(datas, "empId", empId);
        excuteHttpPost(message, "getToMeHuiBao", entitytype, "report/myreceive", datas, true);
//
    }

    public static void getChaoSongToMeHuiBao(Message message, String key, int thetype, String starttime, String endtime, String deptId, String empId) {

        java.lang.reflect.Type entitytype = new TypeToken<HuibaoEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "key", key);
        putData(datas, "thetype", thetype+"");
        putData(datas, "starttime", starttime);
        putData(datas, "endtime", endtime);
        putData(datas, "deptId", deptId);
        putData(datas, "empId", empId);
        excuteHttpPost(message, "getChaoSongToMeHuiBao", entitytype, "report/mycopy", datas, true);
//
    }

    /**
     * 删除汇报
     * @param message
     * @param id
     */
    public static void delHuiBao(Message message, String id) {
        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "id", id);
        excuteHttpGet(message, "delHuiBao", "report/del", datas, type, true);
    }

    public static void getHuiBaoDetail(Message message, String id) {

        java.lang.reflect.Type type = new TypeToken<HuibaoDetail>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "id", id);
        excuteHttpGet(message, "getHuiBaoDetail", "report/show", datas, type, true);
    }

    public static void commitHuiBao(Message message, String id, int thetype, String promoter, String receive, String summary, String plan, String starttime, String endtime, String imgurl, String money, String customer) {


        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "id", id);
        putData(datas, "thetype", thetype+"");
        putData(datas, "promoter", promoter);
        putData(datas, "receive", receive);
        putData(datas, "summary", summary);
        putData(datas, "plan", plan);
        putData(datas, "starttime", starttime);
        putData(datas, "endtime", endtime);
        putData(datas, "imgurl", imgurl);
        putData(datas, "money", money);
        putData(datas, "customer", customer);
        excuteHttpPost(message, "commitHuiBao", type, "report/add", datas, true);
    }

    public static void huibaoComment(Message message, String id, String contentStr) {

        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "report_id", id);
        putData(datas, "content", contentStr);
        excuteHttpPost(message, "commitHuiBao", type, "report/commit", datas, true);
    }

    /**
     *
     * @param message
     * @param num  审批别名
     */
    public static void getShenpiFlow(Message message,String num){


        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "num", num+"");
        excuteHttpGet(message, "getShenpiFlow", "flow-apply/apply", datas, type, true);
//        excuteHttpPost(message, "getShenpiFlow", type, "flow-apply/apply", datas, true);
    }


    public static void getShenpiSet(Message message,int thetype){
        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        putData(datas, "thetype", thetype+"");
        excuteHttpPost(message, "getShenpiSet", type, "flow-apply/getflowdata", datas, true);

    }

    public static void getKaoQin(Message message) {

        java.lang.reflect.Type type = new TypeToken<BaseEntity>() {
        }.getType();
        HashMap<String, String> datas = new HashMap<>();
        excuteHttpGet(message, "getKaoQin", "kaoqin-manage/list", datas, type, true);
//        excuteHttpPost(message, "getKaoQin", type, "/kaoqin-manage/list", datas, true);
    }

//    public static void upluad(final Message msg, String id, String pid, String title, String enddate, String member, String imgurl, File[] files) {
//        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//        /* 第一个要上传的file */
//        ArrayList<RequestBody> list = new ArrayList<>();
//        for (int i = 0; i < files.length; i++) {
//            RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"), files[i]);
//            list.add(fileBody1);
//        }
////        File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
////        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
////        String file1Name = "testFile1.txt";
////        /* 第二个要上传的文件,这里偷懒了,和file1用的一个图片 */
////        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
////        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
////        String file2Name = "testFile2.txt";
//        /* form的分割线,自己定义 */
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        putData(builder, "id", id);
//        putData(builder, "pid", pid);
//        putData(builder, "title", title);
//        putData(builder, "enddate", enddate);
//        putData(builder, "member", member);
//        putData(builder, "token", "2017022717322579946");
//        putData(builder, "ver", CommonUtils.configureGetVersionCode(App.mContext));
//        putData(builder, "device", "2");
//        for (File file : files) {
//            builder.addFormDataPart("img", null, RequestBody.create(MEDIA_TYPE_PNG, file));
//        }
//
//        MultipartBody mBody = builder.build();
//
//
//
//    }


//    public static void excuteHttpPostWithFile(final Message msg, final String tag, int method, final java.lang.reflect.Type type, String url, final HashMap<String, String> postData,File[]files){
//
//        final Handler handler = msg.getTarget();
//        Message message = new Message();
//        message.what = REQUEST_BEGIN;
//        message.arg1 = msg.arg1;
//        message.arg2 = msg.arg2;
//
//        message.setData(msg.getData());
//        handler.sendMessage(message);
//
//        MultipartBody builder = new MultipartBuilder().type(MultipartBuilder.FORM);
//
//        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
//        for (File path : files) {
//            builder.addFormDataPart("upload", null, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
//        }
//
//
//        //构建请求体
//        RequestBody requestBody = builder.build();
//        Request request = new Request.Builder()
//                .url(url)//地址
//                .post(requestBody)//添加请求体
//                .build();
////        Request request = new Request.Builder().url(+"task/save").post(mBody).build();
//        getOKHTTP().newCall(request).enqueue(new Callback() {
//            public void onResponse(Call call, Response response) throws IOException {
//
//                L.v(TAG, "onResponse", "onResponse getData");
//                Message message = msg;
//                try {
//
//
//                    L.v(TAG, "parseNetworkResponse", "have get datas");
//                    String json = new String(response.body().bytes(), "utf-8");
//                    L.v(TAG, "parseNetworkResponse json:", json.toString());
//                    Gson gson = new Gson();
//                    final BaseEntity entity = gson.fromJson(json, type);
//                    L.v(TAG, "entity", entity.msg);
////                    1)	code，处理结果代码
////                    用于标识本次请求的执行结果。
////                    0表示处理成功。
////                    10100表示无访问接口权限。
////                    10101表示访问别人权限范围内的数据、业务（欺骗访问）。
////                    2)	msg，处理结果信息
////                    一般用于记录错误信息。APP有时候会以此信息来提示用户。
//
//                    if (null != response && (entity instanceof BaseEntity)) {
//                        if (((BaseEntity) entity).code.equals("0")) {
//                            msg.what = REQUEST_SUCCESS;
//                            message.obj = entity;
//                            handler.sendMessage(message);
//                            L.v(TAG, "success", "success", ((BaseEntity) entity).msg);
//                        } else if (((BaseEntity) entity).code.equals("10100")) {
//
//                            L.v(TAG, " 10100fail", "fail getData");
//                            message.obj = ((BaseEntity) entity).msg;
//                            message.what = REQUEST_FAIL;
//                            handler.sendMessage(message);
//                        } else if (((BaseEntity) entity).code.equals("10101")) {
//                            L.v(TAG, "10101 fail", "fail getData");
//                            message.what = REQUEST_FAIL;
//                            message.obj = ((BaseEntity) entity).msg;
//                            handler.sendMessage(message);
//
//                        } else {
//                            message.what = REQUEST_FAIL;
//                            message.obj = ((BaseEntity) entity).msg;
//                            handler.sendMessage(message);
//                            L.v(TAG, " ese fail", "fail getData", ((BaseEntity) entity).msg);
//                        }
//                    } else {
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    L.v(tag, "error:");
//                }
//            }
//            public void onFailure(Call call, final IOException e) {
//                Message message = msg;
//                if (null != e) {
//                    message.obj = e.toString();
//                }
//                message.what = REQUEST_FAIL;
//                handler.sendMessage(message);
//
//
//            }
//        });
//
//    }


}
