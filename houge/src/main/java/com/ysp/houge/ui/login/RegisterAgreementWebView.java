package com.ysp.houge.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.MD5Utils;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.widget.MyActionBar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WT on 2016/6/21.
 */
public class RegisterAgreementWebView extends BaseFragmentActivity{

    private WebView webView;

    public static void jumpIn(Context context) {
        context.startActivity(new Intent(context, RegisterAgreementWebView.class));
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.register_agreement);
    }

    @Override
    protected void initActionbar() {
        MyActionBar actionBar = new MyActionBar(this);
        actionBar.setTitle("注册协议");
        actionBar.setBackgroundColor(getResources().getColor(R.color.white));
        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        webView = (WebView) findViewById(R.id.wv_agreement);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        webView.loadUrl(createUrl());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private String createUrl(){
        StringBuilder url = new StringBuilder(HttpApi.getAbsPathUrl(HttpApi.SITE_ABOUTUSER));
        url.append("?");

        //一些全局参数
        Map<String, String> params = new HashMap<String, String>();
        params.put(HttpRequest.PARAME_NAME_PT,HttpRequest.PARAME_VALUE_PT);
        params.put(HttpRequest.PARAME_NAME_VR,HttpRequest.PARAME_VALUE_VR);
        params.put(HttpRequest.PARAME_NAME_APT,String.valueOf(MyApplication.LOG_STATUS_SELLER));
        String token = MyApplication.getInstance().getPreferenceUtils().getToken();
        if (!TextUtils.isEmpty(token)){
            params.put(HttpRequest.PARAME_NAME_TOKEN, token);
        }
        String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)){
            BDLocation location = new Gson().fromJson(local,BDLocation.class);
            params.put(HttpRequest.PARAME_NAME_GPS, location.getLatitude() + "," + location.getLongitude());
        }
        params.put(HttpRequest.PARAME_NAME_SIGN, MD5Utils.MD5(MD5Utils.CreateLinkString(params) + MD5Utils.SING));

        //拼接上全局参数
        for (Map.Entry<String,String> entry : params.entrySet()){
            url.append(entry.getKey());
            url.append("=");
            url.append(entry.getValue());
            url.append("&");
        }

        //删除最后一个&符号
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }


}
