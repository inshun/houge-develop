package com.ysp.houge.model.impl;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.ISellerOrderModel;
import com.ysp.houge.model.entity.bean.SkillEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

/**
 * @描述: 买家订单Model层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月27日下午4:52:07
 * @version 1.0
 */
public class SellerOrderModelImpl implements ISellerOrderModel {

	@Override
	public void releaseOrderRequest(SkillEntity entity, final OnNetResponseCallback onNetResponseCallback) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", entity.title);
		map.put("desc", entity.desc);
		map.put("image", entity.image);
        if(entity.price != 0) {
            map.put("price", String.valueOf(entity.price));
        }
        if (!TextUtils.isEmpty(entity.unit)){
		    map.put("unit", entity.unit);
        }
		map.put("is_no_salary", entity.is_no_salary);
		map.put("is_room", entity.is_room);
		map.put("is_good", entity.is_good);

		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.PUBLISH_SELLER), map, String.class,
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
