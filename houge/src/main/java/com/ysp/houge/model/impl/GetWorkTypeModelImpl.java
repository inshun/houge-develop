package com.ysp.houge.model.impl;

import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IGetWorkTypeModel;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.HashMap;
import java.util.Map;

public class GetWorkTypeModelImpl implements IGetWorkTypeModel {

	@Override
	public void getWorkTypeList(OnNetResponseCallback netResponseListener) {
		Request<WorkTypeEntity> request = new Request<WorkTypeEntity>(HttpApi.getAbsPathUrl(HttpApi.WORK_TYPE),
				new HashMap<String, String>(), WorkTypeEntity.class, BackType.LIST, true, "", true);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "list", netResponseListener);
	}



	@Override
	public void recommendSetting(String delList, String list, final OnNetResponseCallback netResponseListener) {
		// TODO 买家设置关注
		Map<String, String> map = new HashMap<String, String>();
		if (!TextUtils.isEmpty(list)) {
			map.put("list", list);

		}
		if (!TextUtils.isEmpty(delList)) {
			map.put("dellist", delList);
		}

		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.RECOMMEND_SKILL), map,
				String.class, BackType.STRING, true);
		request.setType(Method.GET);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "msg", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				if (t != null && t instanceof String) {
					netResponseListener.onSuccess((String) t);
				} else {
					netResponseListener.onError(ResponseCode.PARSER_ERROR, "");
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				netResponseListener.onError(errorCode, message);
			}
		});
	}
}
