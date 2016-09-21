package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * 描述： 地址Model层接口
 *
 * @ClassName: IAddressModel
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:49:23
 * 
 *        版本: 1.0
 */
public interface IAddressModel {

	void getAllAddress(int page, int id, int apt, OnNetResponseCallback onNetResponseCallback);

	void addOrUpdateAddress(int apt, int id, int user_id, String real_name, String mobile, String province, String city,
			String street, boolean is_default, Double longitude, Double latitude,
			OnNetResponseCallback onNetResponseCallback);

	void setDefault(int id, OnNetResponseCallback onNetResponseCallback);

	void delete(int id, OnNetResponseCallback onNetResponseCallback);

	void getDefaultAddress(int user_id,int apt, OnNetResponseCallback onNetResponseCallback);
}
