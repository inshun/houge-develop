package com.ysp.houge.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IOrderModel;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

import android.text.TextUtils;

public class OrderModelImpl implements IOrderModel {

	@Override
	public void createOrder(int good_id, int quantity, int address_id, String memo, int pay_type, int type,
			String startDate,int buyer_create_user, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 创建订单
		Map<String, String> map = new HashMap<String, String>();
		map.put("good_id", String.valueOf(good_id));
		map.put("quantity", String.valueOf(quantity));
        map.put("address_id", String.valueOf(address_id));
		map.put("pay_type", String.valueOf(pay_type));
		map.put("type", String.valueOf(type));
        map.put("start_at", startDate);
		if (!TextUtils.isEmpty(memo)) {
			map.put("memo", memo);
		}
        if (buyer_create_user != -1)
            map.put("buyer_create_user",String.valueOf(buyer_create_user));

		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.CRETE_ORDER), map, String.class,
				BackType.STRING, true);
		request.setType(Method.POST);

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

	@Override
	public void getOrderDetails(String order_id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取订单详情
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", order_id);
		Request<OrderDetailsEntity> request = new Request<OrderDetailsEntity>(
				HttpApi.getAbsPathUrl(HttpApi.ORDER_DETAILS), map, OrderDetailsEntity.class, BackType.BEAN, true);
		request.setType(Method.GET);

		/* 数据层操作 */
		HttpRequest.newInstance().request(request, "detail", new OnNetResponseCallback() {

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
	public void getSignByOrderId(String order_id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 获取订单签名
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", order_id);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ORDER_SIGN), map, String.class,
				BackType.STRING, true);
		request.setType(Method.GET);

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

	@Override
	public void changeOrderStatus(String order_id, int status, String tip, int apt,
			final OnNetResponseCallback onNetResponseCallback) {
		// TODO 修改订单状态
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", order_id);
		map.put("status", String.valueOf(status));
		map.put(HttpRequest.PARAME_NAME_APT, String.valueOf(apt));
		if (!TextUtils.isEmpty(tip)) {
			map.put("tip", tip);
		} else {
//			// 开始服务以及结束服务状态是不能缺少描述
//			if (status == OrderEntity.STATUS_SERVICE_FINISH || status == OrderEntity.STATUS_DEPART) {
//				onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "0x0001");
//				return;
//			}
		}
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ORDER_CHANGE_STATUS), map,
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
	public void orderRate(String order_id, float star, String content, int apt,
			final OnNetResponseCallback onNetResponseCallback) {
		// TODO 评价
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", order_id);
		map.put("star", String.valueOf(star));
		map.put("content", content);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ORDER_RATE), map, String.class,
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
	public void payByBalcance(String order_id, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 余额支付
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", order_id);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ORDER_BALANCE_PAY), map,
				String.class, BackType.STRING, true);
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
	public void changePrice(String order_id, double price, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 改价
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", order_id);
		map.put("total_fee", String.valueOf(price));
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ORDER_CHANGE_PRICE), map,
				String.class, BackType.STRING, true);
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
    public void requesTrefund(String order_id, String refund_content, final OnNetResponseCallback onNetResponseCallback) {
        // TODO 申请退款
        Map<String, String> map = new HashMap<String, String>();
        map.put("order_id", order_id);
        map.put("refund_content", refund_content);
        Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ORDER_REQUEST_REFUND), map,
                String.class, BackType.STRING, true);
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
    public void confirmRefund(String order_id, final OnNetResponseCallback onNetResponseCallback) {
        // TODO 同意退款
        Map<String, String> map = new HashMap<String, String>();
        map.put("order_id", order_id);
        Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.ORDER_CONFIRM_REFUND), map,
                String.class, BackType.STRING, true);
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
	/**
	 * 获取个人信息
	 */
	@Override
	public void meInfoRequest(final boolean needSave, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.GET_USER_INFO), map,
				UserInfoEntity.class, BackType.BEAN, true);
		request.setType(Method.GET);
        /* 数据层操作 */
		HttpRequest.newInstance().request(request, "user", new OnNetResponseCallback() {

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
