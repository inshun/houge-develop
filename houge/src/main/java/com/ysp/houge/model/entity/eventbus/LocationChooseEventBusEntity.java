package com.ysp.houge.model.entity.eventbus;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月25日下午2:40:08
 * @version 2.4
 */
public class LocationChooseEventBusEntity {
	public double latitude;

	public double langtitude;

	public String address;
	
	public String province;
	
	public String city;
	
	public boolean getResult = false;
	
	public LocationChooseEventBusEntity() {
	}

	public LocationChooseEventBusEntity(double latitude, double langtitude, String address) {
		super();
		this.latitude = latitude;
		this.langtitude = langtitude;
		this.address = address;
	}

	
}
