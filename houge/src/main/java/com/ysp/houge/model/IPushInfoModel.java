package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**  
 * @描述: 推送信息搜集Model层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月25日下午12:12:22
 * @version 1.0
 */
public interface IPushInfoModel {
	void setPushInfo(int device_online,OnNetResponseCallback onNetResponseCallback);
}
