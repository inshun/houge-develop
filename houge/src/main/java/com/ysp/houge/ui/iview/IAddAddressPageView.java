package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.AddressEntity;

/**
 * @描述: 添加地址View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月23日下午2:52:11
 * @version 1.0
 */
public interface IAddAddressPageView extends IBaseView {
	void showAddress(AddressEntity addressEntity);

	/** 跳转到地图选点页面 */
	void jumpToMapChooseAddress(AddressEntity addressEntity);

	/** 完成添加 */
	void finishAdd();

	void showChooseMap(String address);
}
