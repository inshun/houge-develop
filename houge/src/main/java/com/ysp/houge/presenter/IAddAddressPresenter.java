package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;

/**
 * @描述:添加地址Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月23日下午3:04:55
 * @version 1.0
 */
public interface IAddAddressPresenter {
	void setAddress(AddressEntity addressEntity);
	
	/** 地图选点 */
	void chooseMapAddress();

	/** 提交 */
	void submit(String street,String contacts,String contactNum);
	
	void chooseMapFinish(LocationChooseEventBusEntity busEntity);
	
}
