package com.ysp.houge.model;

import com.ysp.houge.model.entity.bean.SkillEntity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @描述: 卖家订单Model层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月27日下午4:51:44
 * @version 1.0
 */
public interface ISellerOrderModel {
	void releaseOrderRequest(SkillEntity entity,OnNetResponseCallback onNetResponseCallback);
}
