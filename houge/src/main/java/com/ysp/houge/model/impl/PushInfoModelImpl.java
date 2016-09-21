package com.ysp.houge.model.impl;

import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IPushInfoModel;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.HashMap;
import java.util.Map;

public class PushInfoModelImpl implements IPushInfoModel {

	@Override
	public void setPushInfo(int device_online, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 推送信息收集接口 device_online在线状态 0表示在线，1表示离线
		Map<String, String> map = new HashMap<String, String>();
        if (TextUtils.isEmpty(MyApplication.getInstance().getPhoneInfo().getDevice_id())){
				onNetResponseCallback.onError(ResponseCode.SYSTEM_ERROR,"");
			return;
		}else {
			map.put("device_id", MyApplication.getInstance().getPhoneInfo().getDevice_id());
		}
		if (!TextUtils.isEmpty(MyApplication.getInstance().getPhoneInfo().getDevice_model())){
			map.put("device_name", MyApplication.getInstance().getPhoneInfo().getDevice_model());
		}
		map.put("platform", String.valueOf(1));
		String platform_id = MyApplication.getInstance().getPreferenceUtils().getClientId();
		if (!TextUtils.isEmpty(platform_id)) {
			map.put("platform_id", platform_id);
		} else {
			onNetResponseCallback.onError(ResponseCode.SYSTEM_ERROR,"");
			return;
		}
		map.put("device_type", "android");
		map.put("device_online", String.valueOf(device_online));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.PUSH_INFO), map, String.class,
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

}
