package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.bean.AddressEntity;

/**
 * @描述: 买家发布页面Presenter 层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月27日下午6:15:00
 * @version 1.0
 */
public interface INeedPulishPresenter {

	/** 加载默认的地址信息 */
	void loadDefaultAddress();

	void onRecord();

	void choosePhoto(String path);

	void chooseServerTime();

	void chooseContactWay();

	void nextStup(String title, String desc, String price);

	void deleteRecord();

	void playRecord();

	void deleteImg(int index);

	void pickUp();

	void chooseAddressFinish(AddressEntity an);

	void chooseTimeFinish(String time, int date);

}
