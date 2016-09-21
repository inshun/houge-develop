package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @author it_hu
 * 
 *         认证model层接口
 *
 */
public interface IAuthModel {

	void studentAuth(String name, String people_id, String school, String levle_school, String people_img,
			String people_up, String people_back, String school_img, String xuexin_img,
			OnNetResponseCallback onNetResponseCallback);

	void auth(String name, String people_id, String people_img, String people_up, String people_back,
			OnNetResponseCallback onNetResponseCallback);

	void companyAuth(String std_name, String name, String std_img, String people_img, String people_up,
			String people_back, OnNetResponseCallback onNetResponseCallback);
}
