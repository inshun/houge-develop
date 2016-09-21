package com.ysp.houge.model.impl;

import android.util.Log;

import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.ISplashModel;
import com.ysp.houge.model.entity.bean.VersionInfoEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.android.volley.Request.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/8/3
 * Email: m13478943650@163.com
 */
public class SplashActivityModelImpl implements ISplashModel {
    private VersionInfoEntity entity;

    @Override
    public void getVersionInfoFromNet(final OnNetResponseCallback callback) {
        Map<String, String> map = new HashMap<>();
        /**请求数据 包括 升级代码版本号 产品版本号 升级信息 是否强制升级 如遇重大更新或是业务逻辑升级需使用强制更新*/
        Request<VersionInfoEntity> request = new Request<VersionInfoEntity>(Method.GET, HttpApi.getAbsPathUrl(HttpApi.VERSION), map,
                VersionInfoEntity.class, Request.BackType.BEAN, true);

        HttpRequest.newInstance().request(request, "data", new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                Log.d("SplashActivityModelImpl", "data*+++:" + data);

                callback.onSuccess(data);
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.d("SplashActivityModelImpl", "错误");
            }
        });


    }
}
