package com.ysp.houge.model.impl;
import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.INearbyModel;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.NearbyListEntity;
import com.ysp.houge.model.entity.bean.NearbyMapResultEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 描述： 附近model层
 *
 * @ClassName: NearbyModelImpl
 *
 * @author: hxs
 *
 * @date: 2015年12月4日 下午3:57:12

 *        版本: 1.0
 */
public class NearbyModelImpl implements INearbyModel {
	@Override
	public void getWorkType(final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取附近分类
		Map<String, String> map = new HashMap<String, String>();
		Request<WorkTypeEntity> request = new Request<WorkTypeEntity>(HttpApi.getAbsPathUrl(HttpApi.NEARBY_TYPE), map,
				WorkTypeEntity.class, BackType.LIST, true);
		request.setType(Method.GET);
        /* 数据层操作 */
		HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object t) {
				WorkTypeEntity entity = new WorkTypeEntity(0, "全部", "no");
				List<WorkTypeEntity> list;
				if (t == null) {
					list = new ArrayList<WorkTypeEntity>();
					list.add(entity);
				} else if (t instanceof List<?>) {
					list = (List<WorkTypeEntity>) t;
					list.add(0, entity);
				}
				onNetResponseCallback.onSuccess(t);
			}
			@Override
			public void onError(int errorCode, String message) {
				onNetResponseCallback.onError(errorCode, message);
			}
		});
	}
	@Override
	public void getNearbySkillList(int page, int id, int order, String gps,
								   final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取附近技能列表
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));// 分页
		map.put("id", String.valueOf(id));
		map.put("order", String.valueOf(order + 1));
		if (TextUtils.isEmpty(gps)) {
			onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "");
			return;
		} else {
			map.put("gps", gps);
		}
		Request<NearbyListEntity> request = new Request<NearbyListEntity>(HttpApi.getAbsPathUrl(HttpApi.NEARBY_SKILL),
				map, NearbyListEntity.class, BackType.BEAN, true);
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
	public void getNearbyNeedList(int page, int id, int order, String gps, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取附需求能列表
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));// 分页
		map.put("id", String.valueOf(id));
		map.put("order", String.valueOf(order + 1));
		if (TextUtils.isEmpty(gps)) {
			onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "");
			return;
		} else {
			map.put("gps", gps);
		}
		Request<NearbyListEntity> request = new Request<NearbyListEntity>(HttpApi.getAbsPathUrl(HttpApi.NEARBY_NEED),
				map, NearbyListEntity.class, BackType.BEAN, true);
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
	public void getMapNearbySkillList(int id, String gps, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取附需求能列表
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		if (TextUtils.isEmpty(gps)) {
			onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "");
			return;
		} else {
			map.put("gps", gps);
		}
		Request<NearbyMapResultEntity> request = new Request<NearbyMapResultEntity>(
				HttpApi.getAbsPathUrl(HttpApi.MAP_NEARBY_SKILL), map, NearbyMapResultEntity.class, BackType.BEAN, true);
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
	public void getMapNearbyNeedList(int id, String gps, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取附需求能列表
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		if (TextUtils.isEmpty(gps)) {
			onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "");
			return;
		} else {
			map.put("gps", gps);
		}
		Request<NearbyMapResultEntity> request = new Request<NearbyMapResultEntity>(
				HttpApi.getAbsPathUrl(HttpApi.MAP_NEARBY_NEED), map, NearbyMapResultEntity.class, BackType.BEAN, true);
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
	public void getNewJoinSkillCount(int type, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取最新加入的数量
		Map<String, String> map = new HashMap<String, String>();
		// map.put("", String.valueOf(type));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.NEW_JOIN_COUNT), map, String.class,
				BackType.STRING, true);
		request.setType(Method.GET);
        /* 数据层操作 */
		HttpRequest.newInstance().request(request, "count", new OnNetResponseCallback() {
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
	public void getNewJoinSkillList(int page, int type, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取最新加入的数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		map.put("type", String.valueOf(type));
		Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(
				HttpApi.getAbsPathUrl(HttpApi.NEW_JOIN_LIST), map, GoodsDetailEntity.class, BackType.LIST, true);
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