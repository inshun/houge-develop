package com.ysp.houge.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IRefreshListModel;
import com.ysp.houge.model.entity.db.ItemPartTimeJobEntity;
import com.ysp.houge.model.entity.db.ItemPrePaidRecordEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

public class PriPaidRecordListModelImpl implements IRefreshListModel {

	@Override
	public void onListModelRequest(int page, int size,
			final OnNetResponseCallback onNetResponseListener) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));

		Request<ItemPartTimeJobEntity> request = new Request<ItemPartTimeJobEntity>(
				HttpApi.getAbsPathUrl(HttpApi.GET_CODE), map,
				ItemPartTimeJobEntity.class, BackType.LIST, true);
		/* 数据层操作 */
		HttpRequest.newInstance().request(request,"", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object t) {
				List<ItemPrePaidRecordEntity> itemPrePaidRecordEntities = initTestData();
				onNetResponseListener.onSuccess(itemPrePaidRecordEntities);
				// onNetResponseListener.onSuccess(t);
			}

			@Override
			public void onError(int errorCode, String message) {
				List<ItemPrePaidRecordEntity> itemPrePaidRecordEntities = initTestData();
				onNetResponseListener.onSuccess(itemPrePaidRecordEntities);
				// onNetResponseListener.onError(errorCode, message);
			}
		});

	}

	private List<ItemPrePaidRecordEntity> initTestData() {
		List<ItemPrePaidRecordEntity> itemPrePaidRecordEntities = new ArrayList<ItemPrePaidRecordEntity>();
		ItemPrePaidRecordEntity itemPrePaidRecordEntity = null;
		for (int i = 0; i < 20; i++) {
			itemPrePaidRecordEntity = new ItemPrePaidRecordEntity();
			itemPrePaidRecordEntity.setCreated("05-23 15:30");
			itemPrePaidRecordEntity.setMoney("5000.00");
			itemPrePaidRecordEntity.setState(new Random().nextInt(2));
			itemPrePaidRecordEntities.add(itemPrePaidRecordEntity);
		}
		return itemPrePaidRecordEntities;
	}

}
