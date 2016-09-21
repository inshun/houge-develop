package com.ysp.houge.model.impl;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IBuyerOrderModel;
import com.ysp.houge.model.entity.bean.NeedEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述: 买家订单Model层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月27日下午4:52:07
 * @version 1.0
 */
public class BuyerOrderModelImpl implements IBuyerOrderModel {

	@Override
	public void releaseOrderRequest(NeedEntity entity, final OnNetResponseCallback onNetResponseCallback) {
		// TODO 发布订单的请求
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", entity.title);
		map.put("desc", entity.desc);
        if (null != entity.recordPath && !TextUtils.isEmpty(entity.recordPath)){
		    map.put("video", entity.recordPath);
        }else {
            map.remove("video");
        }
		map.put("image", entity.picPath);
		map.put("price", String.valueOf(entity.price));
		// map.put("unit", entity.unit);
		// map.put("quantity", String.valueOf(entity.count));
		map.put("address_id", String.valueOf(entity.contact));
		map.put("start_at", String.valueOf(entity.serverTime));
        map.put("_apt",String.valueOf(MyApplication.LOG_STATUS_SELLER));

		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.PUBLISH_BUYER), map, String.class,
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
