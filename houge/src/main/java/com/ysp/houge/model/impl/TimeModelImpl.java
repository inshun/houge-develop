package com.ysp.houge.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.ITimeModel;
import com.ysp.houge.model.entity.bean.TimeEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

/**
 * @author it_hu
 * 
 *         时间设置Model层
 *
 */
public class TimeModelImpl implements ITimeModel {

	@Override
	public void createOrUpdateTime(int apt, String time, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(HttpRequest.PARAME_NAME_APT, String.valueOf(apt));
		map.put("time", time);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.TIME_CREATE), map, String.class,
				BackType.STRING, true);
		request.setType(Method.GET);

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
	public void getTime(int apt, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(HttpRequest.PARAME_NAME_APT, String.valueOf(apt));
		Request<TimeEntity> request = new Request<TimeEntity>(HttpApi.getAbsPathUrl(HttpApi.TIME_GET), map,
				TimeEntity.class, BackType.LIST, true);
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

}
