package com.ysp.houge.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;
import com.google.gson.JsonElement;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.ISettingModel;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.bean.SettingEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import android.text.TextUtils;

public class SettingModelImpl implements ISettingModel {

	@Override
	public void setting(String night_notify_status, String sound_status, String shake_status, String service_distance,
			final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		if (TextUtils.isEmpty(night_notify_status) && TextUtils.isEmpty(sound_status) && TextUtils.isEmpty(shake_status)
				&& TextUtils.isEmpty(service_distance)) {
			// 如果所有参数都是空的，直接返回
			onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "请最少拥有一个设置参数");
			return;
		}

		// yes是no否
		if (!TextUtils.isEmpty(night_notify_status)) {
			map.put("night_notify_status", night_notify_status);
		}

		if (!TextUtils.isEmpty(sound_status)) {
			map.put("sound_status", sound_status);
		}

		if (!TextUtils.isEmpty(shake_status)) {
			map.put("shake_status", shake_status);
		}

		if (!TextUtils.isEmpty(service_distance)) {
			map.put("service_distance", service_distance);
		}
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.APP_SETTING), map, String.class,
				BackType.STRING, true);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "msg", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				onNetResponseCallback.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				onNetResponseCallback.onError(errorCode, message);
			}
		});
	}

	@Override
	public void getSetting(final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		Request<SettingEntity> request = new Request<SettingEntity>(HttpApi.getAbsPathUrl(HttpApi.GET_APP_SETTING), map,
				SettingEntity.class, BackType.LIST, true);
        request.setType(Method.GET);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				onNetResponseCallback.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				onNetResponseCallback.onError(errorCode, message);
			}
		});
	}

	/** 意见反馈请求 */
	@Override
	public void feedBackRequest(String content, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("content", content);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.FEED_BACK), map, String.class,
				BackType.STRING, true);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "msg", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				onNetResponseCallback.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				onNetResponseCallback.onError(errorCode, message);
			}
		});

	}

	/** 修改密码请求 */
	@Override
	public void changePasswordRequest(String oldPassword, String newPassword, String reNewPassword,
			final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("oldpass", oldPassword);
		map.put("password", newPassword);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.EDIT_PASSWORD), map, String.class,
				BackType.STRING, true);
		request.setType(Method.GET);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				onNetResponseCallback.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				onNetResponseCallback.onError(errorCode, message);
			}
		});
	}

	/** 获取app的联系方式请求 */
	@Override
	public void appInit(final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		Request<AppInitEntity> request = new Request<AppInitEntity>(HttpApi.getAbsPathUrl(HttpApi.GET_APP_CONTACT), map,
				AppInitEntity.class, BackType.BEAN, true);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "data", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				onNetResponseCallback.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				onNetResponseCallback.onError(errorCode, message);
			}
		});
	}

	/** 获取关于我们的信息 */
	@Override
	public void getAboutUs(final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		Request<JsonElement> request = new Request<JsonElement>(HttpApi.getAbsPathUrl(HttpApi.GET_APP_ABOUT_US), map,
				null, BackType.JSONELEMENT, true);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				onNetResponseCallback.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				onNetResponseCallback.onError(errorCode, message);
			}
		});
	}

    @Override
    public void getAllCount() {
        Map<String, String> map = new HashMap<String, String>();
        Request<MessageCountEntity> request = new Request<MessageCountEntity>(HttpApi.getAbsPathUrl(HttpApi.MESSAGE_NEW_COUNT), map, MessageCountEntity.class,
                BackType.BEAN, true);

		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "data", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                if(null != t && t instanceof MessageCountEntity){
                    MyApplication.getInstance().setMessageCountEntity((MessageCountEntity) t);
                    //发送通知
                    EventBus.getDefault().post(new MessageCountEntity());
                }
            }

            @Override
            public void onError(int errorCode, String message) {
            }
        });
    }

}
