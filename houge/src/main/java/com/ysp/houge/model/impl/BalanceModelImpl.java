package com.ysp.houge.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IBalanceModel;
import com.ysp.houge.model.entity.bean.BalanceEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

public class BalanceModelImpl implements IBalanceModel {
	public static final int PAY_TYPE_ALIPAY = 1;
	public static final int PAY_TYPE_WECHART = 4;

	@Override
	public void getBalanceList(final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		Request<BalanceEntity> request = new Request<BalanceEntity>(HttpApi.getAbsPathUrl(HttpApi.BALANCE), map,
				BalanceEntity.class, BackType.LIST, true);

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
	public void withdrawDeposit(double price, String alipay_id, String name, String password,
			final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("price", String.valueOf(price));
		map.put("alipay_id", alipay_id);
		map.put("name", name);
		map.put("password", password);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.WITHDRAW_DEPOSIT), map,
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
	public void recharge(int pay_type, double price, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pay_type", String.valueOf(pay_type));
		map.put("price", String.valueOf(price));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.RECHARGE), map, String.class,
				BackType.STRING, true);

		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "order_query", new OnNetResponseCallback() {

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
