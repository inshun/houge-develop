package com.ysp.houge.model.impl;

import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IReIMModel;
import com.ysp.houge.model.entity.bean.ReUserEntity;
import com.ysp.houge.model.entity.eventbus.IMLoginEventBusEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/8/11
 * Email: m13478943650@163.com
 */
public class ReIMModelImpl implements IReIMModel {
    private ReUserEntity entity;

    @Override
    public void login() {
        entity = new ReUserEntity();
        final IMLoginEventBusEntity eventBusEntity = new IMLoginEventBusEntity();
        Map<String, String> map = new HashMap<String, String>();
        Request<ReUserEntity> request = new Request<ReUserEntity>(HttpApi.getAbsPathUrl(HttpApi.IM_TALK_RESETPASS), map, ReUserEntity.class,
                Request.BackType.BEAN, true);
        request.setType(com.android.volley.Request.Method.GET);
        /* 请求到重置的IM密码和账号 */
        HttpRequest.newInstance().request(request, "data", new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object t) {

                entity = (ReUserEntity) t;
                //  重新登陆IM账号
                EMChatManager.getInstance().login(entity.getUsername(), entity.getPassword(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMChatManager.getInstance().loadAllConversations();
                        eventBusEntity.setResult(0);
                        EventBus.getDefault().post(eventBusEntity);
                    }

                    @Override
                    public void onProgress(int arg0, String arg1) {
                    }

                    @Override
                    public void onError(int arg0, String arg1) {
                        Log.d("ReIMModelImpl", "登陆失败");
                    }
                });
            }

            @Override
            public void onError(int errorCode, String message) {
                eventBusEntity.setResult(1);
                EventBus.getDefault().post(eventBusEntity);
            }
        });

    }
}
