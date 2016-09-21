package com.ysp.houge.model.impl;

import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IRegisterAndLoginModel;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:注册和登录流程中的Model层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月23日上午11:22:11
 * @version 1.0
 */
public class RegisterAndLoginModel implements IRegisterAndLoginModel {

	// 短信验证码接口
	@Override
	public void getCodeRequest(String mobile, int type, final OnNetResponseCallback onNetResponseCallback) {
        //暂时进行语音验证码拦截
        if (type == CODE_TYPE_VOICE_CODE){
            onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "语音验证码暂未开放...");
            return;
        }

		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.GET_CODE), map, String.class,
				BackType.STRING, false);
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

	// 登陆接口
	@Override
	public void login(String mobile, String password, final OnNetResponseCallback netResponseListener) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("password", password);
		Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.MOBILE_LOGIN), map,
				UserInfoEntity.class, BackType.BEAN, false);
		request.setType(Method.GET);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "user", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				netResponseListener.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				netResponseListener.onError(errorCode, message);
			}
		});
	}

	@Override
	public void checkCodeRequest(String mobile, String code, final OnNetResponseCallback netResponseListener) {
	}

	// 注册
	@Override
	public void registerRequest(String mobile, String code, String password, String nick, String avatar, String sex,
			String invite_id, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("code", code);
		map.put("password", password);
		map.put("nick", nick);
		map.put("avatar", avatar);
		map.put("sex", sex);
		// 这里验证码可能为空
		if (!TextUtils.isEmpty(invite_id)) {
			map.put("invite_id", invite_id);
		}
		Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.REGISTER), map,
				UserInfoEntity.class, BackType.BEAN, false);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "user", new OnNetResponseCallback() {

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
	public void findPwdRequest(String mobile, String code, String password,
			final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("code", code);
		map.put("password", password);

		Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.FIND_PWD), map,
				UserInfoEntity.class, BackType.BEAN, false);
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
}
