package com.ysp.houge.utility.volley;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.BuildConfig;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.base.BaseHttpResultEntity;
import com.ysp.houge.model.entity.eventbus.LogoutEventBusEntity;
import com.ysp.houge.utility.FileWriteUtil;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.MD5Utils;
import com.ysp.houge.utility.NetConnectUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author tyn
 * @version 1.0
 * @描述:网络请求对象
 * @Copyright Copyright (c) 2015
 * @Company .
 * @date 2015年7月2日下午5:25:46
 */
public class HttpRequest {
    private final String TAG = getClass().getSimpleName();
    private static DefaultRetryPolicy retryPolicy;
    private static RequestQueue mRequestQueue;
    private static Context mContext;

    public final static String PARAME_NAME_PT = "_pt";
    public final static String PARAME_NAME_VR = "_vr";
    public final static String PARAME_NAME_APT = "_apt";
    public final static String PARAME_NAME_TOKEN = "_token";
    public final static String PARAME_NAME_GPS = "_gps";
    public final static String PARAME_NAME_SIGN = "_sign";

    public final static String PARAME_VALUE_PT = "android";
    public final static String PARAME_VALUE_VR = "1.0";

    private static boolean loadDataFromLocal = false;

    private HttpRequest() {
    }

    /**
     * @param context ApplicationContext
     */
    public static void buildRequestQueue(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        // 设定超时时间：1000ms,
        // 最大的请求次数：2次,
        // 发生冲突时的重传延迟增加数：2f（这个应该和TCP协议有关，冲突时需要退避一段时间，然后再次请求）
        retryPolicy = new DefaultRetryPolicy(30 * 1000, 2, 2f);
        mContext = context;
        // ... do somethingImageRequest request = new ImageRequest(entity.image.)
    }

    public static HttpRequest newInstance() {
        if (mRequestQueue == null) {
            throw new NullPointerException("Call buildRequestQueue method first.");
        }
        // ...
        return new HttpRequest();
    }

    public static void getDataFromLocal(boolean isLocal) {
        loadDataFromLocal = isLocal;
    }

    /*网络请求*/
    public <T> StringRequest request(final Request<T> request, final String dataKey,
                                     final OnNetResponseCallback onNetResponseListener) {
        if (!NetConnectUtil.isConnectingToInternet(mContext)) {
            onNetResponseListener.onError(ResponseCode.TIP_ERROR, mContext.getString(R.string.net_no_connected));
            return null;
        }


//        获取网址
        StringBuilder url = getUrl(request);

        return getDataFromNet(url, request, onNetResponseListener, dataKey);

    }

    /*网络请求 如果本地有缓存从本地获取缓存*/
    public <T> StringRequest request(final Request<T> request, final String dataKey,
                                     final OnNetResponseCallback onNetResponseListener, boolean getCache) {
        //        获取网址
        StringBuilder url = getUrl(request);

        StringRequest request1 = null;

        if (getCache  == false) {
            return null;
        }

        // 如果没有网络且本地有缓存数据就加载本地缓存数据
        if (!NetConnectUtil.isConnectingToInternet(mContext)) {
            Log.d("HttpRequest", "没有网络加载本地缓存数据");
            onNetResponseListener.onError(ResponseCode.TIP_ERROR, mContext.getString(R.string.net_no_connected));
            return getDataFromLocal(url, request, onNetResponseListener, dataKey);
        }

        if (mRequestQueue.getCache().get(url.toString()) == null){
          request1 =  getDataFromNet(url,request,onNetResponseListener,dataKey);
        }else {
          request1 =  getDataFromLocal(url, request, onNetResponseListener, dataKey);
        }



        return request1;

    }

    private <T> StringRequest getDataFromLocal(StringBuilder url, final Request<T> request
            , final OnNetResponseCallback onNetResponseListener, final String dataKey) {
        StringRequest stringRequest = null;
        // 如果存在缓存数据
        if (mRequestQueue.getCache().get(url.toString()) != null) {
            LogUtil.deBug(TAG,"获取本地数据",1);
            // 获取本地缓存数据 将缓存数据存入data中
            String response = new String(mRequestQueue.getCache().get(url.toString()).data);

            if (!TextUtils.isEmpty(response)) {
                BaseHttpResultEntity<?> baseHttpResultEntity = null;
                try {
                    switch (request.getBackType()) {
                        case BEAN:
                            baseHttpResultEntity = JsonUtils.reloveJsonToBean(response, dataKey,
                                    request.getClazz());
                            break;
                        case LIST:
                            baseHttpResultEntity = JsonUtils.reloveJsonToListBean(response, dataKey,
                                    request.getClazz());
                            break;
                        case STRING:
                            baseHttpResultEntity = JsonUtils.reloveJsonToString(response, dataKey);
                            break;
                        case JSONELEMENT:
                            baseHttpResultEntity = JsonUtils.reloveJsonToJsonElement(response, dataKey);
                            break;
                        default:
                            break;
                    }

                    if (baseHttpResultEntity != null) {
                        if (baseHttpResultEntity.isResult()) {
                            LogUtil.deBug(TAG,"本地数据读取成功",1);
                            onNetResponseListener.onSuccess(baseHttpResultEntity.getData());
                            if (request.isNeedCache()) {
                                saveData(request.getCachePath(), response);
                            }
                        } else {
                            if ("missing_token".equals(baseHttpResultEntity.getMsg())) {
                                LogUtil.deBug(TAG,"本地数据读取失败MSG",1);
                                onNetResponseListener.onError(ResponseCode.INVALID_ERROR, "");
                                EventBus.getDefault().post(new LogoutEventBusEntity());
                            } else {
                                LogUtil.deBug(TAG,"本地数据读取失败TIP_ERROR",1);
                                onNetResponseListener.onError(ResponseCode.TIP_ERROR,
                                        baseHttpResultEntity.getMsg());
                            }
                        }
                    } else {
                        LogUtil.deBug(TAG,"本地数据读取失败PARSER_ERROR1",1);
                        onNetResponseListener.onError(ResponseCode.PARSER_ERROR, "");
                      stringRequest =  getDataFromNet(url,request,onNetResponseListener,dataKey);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    LogUtil.deBug(TAG,"本地数据读取失败PARSER_ERROR2",1);
                    onNetResponseListener.onError(ResponseCode.PARSER_ERROR, "");
                    stringRequest =  getDataFromNet(url,request,onNetResponseListener,dataKey);

                }
            } else {
                LogUtil.deBug(TAG,"本地数据读取失败SYSTEM_ERROR3",1);
                onNetResponseListener.onError(ResponseCode.SYSTEM_ERROR, "");
                stringRequest =  getDataFromNet(url,request,onNetResponseListener,dataKey);

            }
        }
        return stringRequest;
    }

    // 从网络中获取数据
    private <T> StringRequest getDataFromNet(StringBuilder url, final Request<T> request
            , final OnNetResponseCallback onNetResponseListener
            , final String dataKey) {
        LogUtil.deBug(TAG,"网络数据",1);
        StringRequest stringRequest = null;
        stringRequest = new StringRequest(request.getType(), url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!TextUtils.isEmpty(response)) {
                            LogUtil.deBug(TAG,"网络数据获取成功",1);



                            BaseHttpResultEntity<?> baseHttpResultEntity = null;
                            try {
                                switch (request.getBackType()) {
                                    case BEAN:
                                        baseHttpResultEntity = JsonUtils.reloveJsonToBean(response, dataKey,
                                                request.getClazz());
                                        break;
                                    case LIST:
                                        baseHttpResultEntity = JsonUtils.reloveJsonToListBean(response, dataKey,
                                                request.getClazz());
                                        break;
                                    case STRING:
                                        baseHttpResultEntity = JsonUtils.reloveJsonToString(response, dataKey);
                                        break;
                                    case JSONELEMENT:
                                        baseHttpResultEntity = JsonUtils.reloveJsonToJsonElement(response, dataKey);
                                        break;
                                    default:
                                        break;
                                }

                                if (baseHttpResultEntity != null) {
                                    if (baseHttpResultEntity.isResult()) {
                                        onNetResponseListener.onSuccess(baseHttpResultEntity.getData());
                                        if (request.isNeedCache()) {
                                            saveData(request.getCachePath(), response);
                                        }
                                    } else {
                                        if ("missing_token".equals(baseHttpResultEntity.getMsg())) {
                                            onNetResponseListener.onError(ResponseCode.INVALID_ERROR, "");
                                            EventBus.getDefault().post(new LogoutEventBusEntity());
                                        } else {
                                            onNetResponseListener.onError(ResponseCode.TIP_ERROR,
                                                    baseHttpResultEntity.getMsg());
                                        }
                                    }
                                } else {
                                    onNetResponseListener.onError(ResponseCode.PARSER_ERROR, "");
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                onNetResponseListener.onError(ResponseCode.PARSER_ERROR, "");
                            }
                        } else {
                            onNetResponseListener.onError(ResponseCode.SYSTEM_ERROR, "");
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ParseError) {

                    LogUtil.deBug(TAG,"网络数据获取失败",1);
                    onNetResponseListener.onError(ResponseCode.PARSER_ERROR, error.getMessage());

                    return;
                }
                onNetResponseListener.onError(ResponseCode.VOLLEY_ERROR, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (request.getType() == Method.GET) {
                    return super.getParams();
                }
                if (request.getMap() != null) {
                    return request.getMap();
                } else {
                    return super.getParams();
                }
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(30 * 1000, 2, 2f);
            }
        };
        stringRequest.setRetryPolicy(retryPolicy);
        mRequestQueue.add(stringRequest);

        return stringRequest;
    }

    //  获取网址
    private <T> StringBuilder getUrl(Request<T> request) {
        StringBuilder url = new StringBuilder();
        Map<String, String> mapParame = new HashMap<String, String>();
        //请求地址(由于公共参数需要get请求，所以才请求之前拼接到url里面)
        url.append(request.getUrl());
        url.append("?");
        url.append(PARAME_NAME_VR + "=" + PARAME_VALUE_VR);
        url.append("&");
        url.append(PARAME_NAME_PT + "=" + PARAME_VALUE_PT);
        mapParame.put(PARAME_NAME_PT, PARAME_VALUE_PT);
        mapParame.put(PARAME_NAME_VR, PARAME_VALUE_VR);

        // 如果不包含apt字段才添加（用于地址时间等，需要注入apt的地方）
        if (!request.getMap().containsKey(PARAME_NAME_APT)) {
            url.append("&");
            url.append(PARAME_NAME_APT);
            url.append("=");
            url.append(MyApplication.getInstance().getPreferenceUtils().getLoginStatus());
            mapParame.put(PARAME_NAME_APT, String.valueOf(MyApplication.getInstance().getPreferenceUtils().getLoginStatus()));
        } else {
            url.append("&");
            url.append(PARAME_NAME_APT);
            url.append("=");
            url.append(request.getMap().get(PARAME_NAME_APT));
            mapParame.put(PARAME_NAME_APT, request.getMap().get(PARAME_NAME_APT));
        }

        //如果token存在才添加
        String token = MyApplication.getInstance().getPreferenceUtils().getToken();

        if (!TextUtils.isEmpty(token)) {
            url.append("&");
            url.append(PARAME_NAME_TOKEN);
            url.append("=");
            url.append(token);
            mapParame.put(PARAME_NAME_TOKEN, token);
        }

        //如果GPS存在才添加
        String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)) {

            BDLocation location = new Gson().fromJson(local, BDLocation.class);

            Log.d("HttpRequest", "location.getLatitude():" + location.getLatitude());
            Log.d("HttpRequest", "location.getLongitude():" + location.getLongitude());
            url.append("&");
            url.append(PARAME_NAME_GPS);
            url.append("=");

            url.append(location.getLatitude());
            url.append(",");
            url.append(location.getLongitude());
            mapParame.put(PARAME_NAME_GPS, location.getLatitude() + "," + location.getLongitude());
        }


        if (request.getType() == Method.GET) {
            if ((request.getMap() == null || request.getMap().isEmpty()) && !request.isNeedToken()) {
                LogUtil.setLogWithE("HttpRequest",
                        "this mast be a error response ,that's no request parameter ,no need token");
                return null;
            } else {
                url.append("&");
                if (request.getMap() != null && !request.getMap().isEmpty()) {
                    Map<String, String> map = request.getMap();
                    //拼接get请求地址
                    for (Entry<String, String> entry : map.entrySet()) {
                        mapParame.put(entry.getKey(), entry.getValue());

                        url.append(entry.getKey());
                        url.append("=");

                        // 对值进行编码
                        String value = "";
                        try {
                            value = URLEncoder.encode(entry.getValue(), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (!TextUtils.isEmpty(value)) {
                            url.append(value);
                        }
                        url.append("&");
                    }
                }
                url.append(PARAME_NAME_SIGN + "=" + MD5Utils.MD5(MD5Utils.CreateLinkString(mapParame) + MD5Utils.SING));
            }
        } else {
            if (request.getMap() != null && !request.getMap().isEmpty()) {
                Map<String, String> map = request.getMap();
                //拼接get请求地址
                for (Entry<String, String> entry : map.entrySet()) {
                    mapParame.put(entry.getKey(), entry.getValue());
                }
            }
            url.append("&" + PARAME_NAME_SIGN + "=" + MD5Utils.MD5(MD5Utils.CreateLinkString(mapParame) + MD5Utils.SING));
        }

        LogUtil.setLogWithE("url", url.toString());
        return url;
    }

    public void saveData(String path, String content) {
        if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(path)) {
            FileWriteUtil.writeToPath(path, content);
        }
    }

}
