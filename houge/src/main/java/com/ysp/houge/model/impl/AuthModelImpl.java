package com.ysp.houge.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IAuthModel;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

public class AuthModelImpl implements IAuthModel {

	@Override
	public void studentAuth(String name, String people_id, String school, String levle_school, String people_img,
			String people_up, String people_back, String school_img, String xuexin_img,
			final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", String.valueOf(1));
		map.put("name", name);
		map.put("people_id", people_id);
		map.put("school", school);
		map.put("levle_school", levle_school);
		map.put("people_img", people_img);
		map.put("people_up", people_up);
		map.put("people_back", people_back);
		map.put("school_img", school_img);
		map.put("xuexin_img", xuexin_img);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.AUTH), map, String.class,
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
	public void auth(String name, String people_id, String people_img, String people_up, String people_back,
			final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", String.valueOf(2));
		map.put("name", name);
		map.put("people_id", people_id);
		map.put("people_img", people_img);
		map.put("people_up", people_up);
		map.put("people_back", people_back);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.AUTH), map, String.class,
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
	public void companyAuth(String std_name, String name, String std_img, String people_img, String people_up,
			String people_back, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", String.valueOf(3));
		map.put("std_name", std_name);
		map.put("name", name);
		map.put("std_img", std_img);
		map.put("people_img", people_img);
		map.put("people_up", people_up);
		map.put("people_back", people_back);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.AUTH), map, String.class,
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

}
