package com.ysp.houge.model.impl;
import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

import java.util.HashMap;
import java.util.Map;
/**
 * 描述： 地址Model层
 *
 * @ClassName: AddressModelImpl
 *
 * @author: hx
 *
 * @date: 2015年12月4日 下午2:50:52
 *
 *        版本: 1.0
 */
public class AddressModelImpl implements IAddressModel {
	@Override
	public void getAllAddress(int page, int id, int apt, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", String.valueOf(id));
		map.put("page", String.valueOf(page));
		map.put(HttpRequest.PARAME_NAME_APT, String.valueOf(apt));
		Request<AddressEntity> request = new Request<AddressEntity>(HttpApi.getAbsPathUrl(HttpApi.ADDRESS_LIST), map,
				AddressEntity.class, BackType.LIST, true);
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
	@Override
	public void addOrUpdateAddress(int apt, int id, int user_id, String real_name, String mobile, String province,
								   String city, String street, boolean is_default, Double longitude, Double latitude,
								   final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(HttpRequest.PARAME_NAME_APT, String.valueOf(apt));
		// 这里id设置为0代表新增
		map.put("id", String.valueOf(id));
		map.put("user_id", String.valueOf(user_id));
		if (!TextUtils.isEmpty(real_name)) {
			map.put("real_name", real_name);
		}
		if (!TextUtils.isEmpty(province)) {
			map.put("province", province);
		}
		if (!TextUtils.isEmpty(city)) {
			map.put("city", city);
		}
		if (!TextUtils.isEmpty(street)) {
			map.put("street", street);
		}
		if (!TextUtils.isEmpty(mobile)) {
			map.put("mobile", mobile);
		}
		// 改变默认的值
		if (is_default) {
			map.put("is_default", AddressEntity.DEFAULT_TYPE_YES);
		} else {
			map.put("is_default", AddressEntity.DEFAULT_TYPE_NO);
		}
		map.put("longitude", String.valueOf(longitude));
		map.put("latitude", String.valueOf(latitude));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.EDITE_ADD), map, String.class,
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
	public void setDefault(int id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 设置默认
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.SET_ADDRESS_DEFAULT), map,
				String.class, BackType.STRING, true);
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
	public void delete(int id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 删除地址
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ADDRESS_DELETE), map, String.class,
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
	public void getDefaultAddress(int user_id,int apt, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取默认地址
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", String.valueOf(user_id));
		map.put(HttpRequest.PARAME_NAME_APT, String.valueOf(apt));
		Request<AddressEntity> request = new Request<AddressEntity>(HttpApi.getAbsPathUrl(HttpApi.GET_ADDRESS_DEFAULT),
				map, AddressEntity.class, BackType.BEAN, true);
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

