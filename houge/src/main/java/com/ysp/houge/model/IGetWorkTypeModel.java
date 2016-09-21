package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @描述: 获取工种列表Modle层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月5日上午10:43:27
 * @version 1.0
 */
public interface IGetWorkTypeModel {
	/** 网络获取工种列表 */
	void getWorkTypeList(OnNetResponseCallback netResponseListener);

	/** 关注设置 */
	void recommendSetting(String delList,String list,OnNetResponseCallback netResponseListener);
}
