package com.ysp.houge.utility.volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonSyntaxException;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.base.BaseHttpResultEntity;
import com.ysp.houge.utility.LogUtil;

import android.content.Context;
import android.text.TextUtils;

/**
 * @描述:网络请求对象
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月2日下午5:25:46
 * @version 1.0
 */
public class HttpFileRequest {
	private static DefaultRetryPolicy retryPolicy;
	private static RequestQueue mRequestQueue;

	private HttpFileRequest() {
	}

	/**
	 * @param context
	 *            ApplicationContext
	 */
	public static void buildRequestQueue(Context context, HttpStack stack) {
		// 设定超时时间：1000ms,
		// 最大的请求次数：2次,
		// 发生冲突时的重传延迟增加数：2f（这个应该和TCP协议有关，冲突时需要退避一段时间，然后再次请求）
		retryPolicy = new DefaultRetryPolicy(30 * 1000, 2, 2f);
		mRequestQueue = Volley.newRequestQueue(context, stack);
		// ... do something
	}

	public static HttpFileRequest newInstance() {
		if (mRequestQueue == null) {
			throw new NullPointerException("Call buildRequestQueue method first.");
		}
		// ...
		return new HttpFileRequest();
	}

	public <T> void addPostUploadFileRequest(final com.ysp.houge.utility.volley.Request<T> request,
			final String dataKey, String upload_from, final Map<String, File> files,
			final OnNetResponseCallback onNetResponseListener) {
		if (null == request.getUrl() || null == onNetResponseListener) {
			return;
		}

		MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(Method.POST, dataKey, request.getUrl(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (!TextUtils.isEmpty(response)) {
							BaseHttpResultEntity<?> baseHttpResultEntity = null;
							try {
								switch (request.getBackType()) {
								case STRING:
									baseHttpResultEntity = JsonUtils.reloveJsonToString(response, dataKey);
									break;
								default:
									break;
								}
								if (baseHttpResultEntity != null) {
									if (baseHttpResultEntity.isResult()) {
										onNetResponseListener.onSuccess(baseHttpResultEntity.getData());
									} else {
										onNetResponseListener.onError(ResponseCode.TIP_ERROR,
												baseHttpResultEntity.getMsg());
									}
								}
							} catch (JsonSyntaxException e) {
								e.printStackTrace();
								onNetResponseListener.onError(ResponseCode.PARSER_ERROR, "");
							}
						} else {
							onNetResponseListener.onError(ResponseCode.SYSTEM_ERROR, "");
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						if (error instanceof ParseError) {
							onNetResponseListener.onError(ResponseCode.PARSER_ERROR, error.getMessage());
							LogUtil.setLogWithE("PARSER_ERROR", "PARSER_ERROR");
							return;
						}
						onNetResponseListener.onError(ResponseCode.VOLLEY_ERROR, error.getMessage());
					}

				}) {

			@Override
			public Map<String, File> getFileUploads() {
				return files;
			}

			@Override
			public Map<String, String> getStringUploads() {
				if (request.isNeedToken()) {
					if (request.getMap() == null) {
						Map<String, String> map = new HashMap<String, String>();
						request.setMap(map);
					}
					request.getMap().put("token", MyApplication.getInstance().getPreferenceUtils().getToken());
					return request.getMap();
				}
				if (request.getMap() != null) {
					return request.getMap();
				} else {
					return new HashMap<String, String>();
				}
			}

			@Override
			public RetryPolicy getRetryPolicy() {
				RetryPolicy retryPolicy = new DefaultRetryPolicy(request.getTimeout(),
						DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
				return retryPolicy;
			}

		};
		LogUtil.setLogWithE(" volley post : uploadFile", " volley post : uploadFile " + request.getUrl());
		multiPartRequest.setRetryPolicy(retryPolicy);
		mRequestQueue.add(multiPartRequest);
	}
}
