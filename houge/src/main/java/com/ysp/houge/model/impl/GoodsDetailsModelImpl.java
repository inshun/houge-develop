package com.ysp.houge.model.impl;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.entity.bean.CommentResultEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

import java.util.HashMap;
import java.util.Map;

public class GoodsDetailsModelImpl implements IGoodsDetailsModel {

	@Override
	public void requestSkillDetaisl(int id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 技能详情请求
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(HttpApi.getAbsPathUrl(HttpApi.SKILL_DETAIL),
				map, GoodsDetailEntity.class, BackType.BEAN, true);
		request.setType(Method.GET);

		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "good", new OnNetResponseCallback() {

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
	public void requestNeedDetaisl(int id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 技能详情请求
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(HttpApi.getAbsPathUrl(HttpApi.NEED_DETAIL),
				map, GoodsDetailEntity.class, BackType.BEAN, true);
		request.setType(Method.GET);

		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "good", new OnNetResponseCallback() {

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
	public void requestLoveAdd(int id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 赞(暂时不开放)
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		// map.put("content", content);
		// map.put("at", new Gson().toJson(at));
		Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(
				HttpApi.getAbsPathUrl(HttpApi.SERVICE_ZAN_ADD), map, GoodsDetailEntity.class, BackType.BEAN, true);
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

	@Override
	public void requestLoveList(int id, int page, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 赞列表
		Map<String, String> map = new HashMap<String, String>();
		if (id != -1) {
			map.put("id", String.valueOf(id));
		}
		map.put("page", String.valueOf(page));
		Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.SERVICE_ZAN_LIST),
				map, UserInfoEntity.class, BackType.LIST, true);
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
	public void requestMessageAdd(int id, String content, String[] at,
			final OnNetResponseCallback onNetResponseCallback) {
		// TODO 留言
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		map.put("content", content);
		// 艾特功能暂时不做
		// if (null != at) {
		// map.put("at", new Gson().toJson(at));
		// }
		Request<CommentResultEntity> request = new Request<CommentResultEntity>(
				HttpApi.getAbsPathUrl(HttpApi.SERVICE_COMMENT_ADD), map, CommentResultEntity.class, BackType.STRING,
				true);
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
	public void requestCommentList(int id, int page, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 评论列表
		Map<String, String> map = new HashMap<String, String>();
		if (id != -1) {
			map.put("id", String.valueOf(id));
		}
		map.put("page", String.valueOf(page));
		Request<CommentResultEntity> request = new Request<CommentResultEntity>(
				HttpApi.getAbsPathUrl(HttpApi.SERVICE_COMMENT_LIST), map, CommentResultEntity.class, BackType.BEAN,
				true);
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
	public void requestOtherList(int page, int user_id, int id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获得其他服务的列表
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		map.put("page", String.valueOf(page));
		map.put("user_id", String.valueOf(user_id));
		Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(
				HttpApi.getAbsPathUrl(HttpApi.SERVICE_OTHER_LIST), map, GoodsDetailEntity.class, BackType.LIST, true);
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
	public void requestReport(int type, int type_id, int reportType, String title, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获得其他服务的列表
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", String.valueOf(type));
		map.put("type_id", String.valueOf(type_id));
		map.put("reportType", String.valueOf(reportType));
		map.put("title", String.valueOf(title));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.REPORT_ADD), map, String.class,
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

	@Override
	public void requestDeleteGoods(int id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 商品删除
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.GOODS_DELETE), map, String.class,
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
