package com.ysp.houge.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.dbcontroller.SearchHistoryContorller;
import com.ysp.houge.model.ISearchModel;
import com.ysp.houge.model.entity.bean.SearchGoodResultEntity;
import com.ysp.houge.model.entity.bean.SearchUserResultEntity;
import com.ysp.houge.model.entity.db.SearchHistoryEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

/**
 * 描述： 搜索model层
 *
 * @ClassName: SearchModelImpl
 * 
 * @author: hx
 * 
 * @date: 2015年12月6日 上午10:24:47
 * 
 *        版本: 1.0
 */
public class SearchModelImpl implements ISearchModel {

	@Override
	public void getSearchHistory(int type, OnNetResponseCallback onNetResponseCallback) {
		List<SearchHistoryEntity> list = SearchHistoryContorller.getByType(type);
		onNetResponseCallback.onSuccess(list);
	}

	@Override
	public void delSearchHistory(int type, OnNetResponseCallback onNetResponseCallback) {
		onNetResponseCallback.onSuccess(SearchHistoryContorller.delByType(type));
	}

	@Override
	public void searchUser(int page, String text, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", text);
		map.put("page", String.valueOf(page));
		Request<SearchUserResultEntity> request = new Request<SearchUserResultEntity>(
				HttpApi.getAbsPathUrl(HttpApi.SEARCH_USER), map, SearchUserResultEntity.class, BackType.BEAN, true);
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
	public void searchGood(int page, String text, int type, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", text);
		map.put("type", String.valueOf(type));
		map.put("page", String.valueOf(page));
		Request<SearchGoodResultEntity> request = new Request<SearchGoodResultEntity>(
				HttpApi.getAbsPathUrl(HttpApi.SEARCH_GOOD), map, SearchGoodResultEntity.class, BackType.BEAN, true);
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
