package com.ysp.houge.model.impl;
import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IIMModel;
import com.ysp.houge.model.entity.bean.IMEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

import java.util.HashMap;
import java.util.Map;
/**
 * 描述： IMModel层
 *
 * @ClassName: IMModelImpl
 *
 * @author: hx
 *
 * @date: 2015年12月30日 下午7:38:40
 *
 *        版本: 1.0
 */
public class IMModelImpl implements IIMModel {
	@Override
	public void login(String userName, String password, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 登录
		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
			onNetResponseCallback.onError(EMCallBack.ERROR_EXCEPTION_INVALID_PASSWORD_USERNAME, "");
			return;
		}
		EMChatManager.getInstance().login(userName, password, new EMCallBack() {
			@Override
			public void onSuccess() {
				EMChatManager.getInstance().loadAllConversations();
				onNetResponseCallback.onSuccess("");
			}
			@Override
			public void onProgress(int arg0, String arg1) {
			}
			@Override
			public void onError(int arg0, String arg1) {
				onNetResponseCallback.onError(arg0, arg1);
			}
		});
	}
	@Override
	public void register(final OnNetResponseCallback onNetResponseCallback) {
		// TODO 注册
		Map<String, String> map = new HashMap<String, String>();
		Request<IMEntity> request = new Request<IMEntity>(HttpApi.getAbsPathUrl(HttpApi.IM_REGISTER), map, IMEntity.class,
				BackType.BEAN, true);
		request.setType(Method.GET);
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
	@Override
	public void reSetPassword(final OnNetResponseCallback onNetResponseCallback) {
		// TODO 重置密码
		Map<String, String> map = new HashMap<String, String>();
		Request<IMEntity> request = new Request<IMEntity>(HttpApi.getAbsPathUrl(HttpApi.IM_RESETPASS), map, IMEntity.class,
				BackType.BEAN, true);
		request.setType(Method.GET);
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
}