package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * 描述： IMModel层接口
 *
 * @ClassName: IIMModel
 * 
 * @author: hx
 * 
 * @date: 2015年12月30日 下午7:36:07
 * 
 *        版本: 1.0
 */
public interface IIMModel {

	/** 登录IM */
	void login(String userName, String password, OnNetResponseCallback onNetResponseCallback);

	/** 环信注册 */
	void register(OnNetResponseCallback onNetResponseCallback);

    /**环信重置密码*/
    void reSetPassword(OnNetResponseCallback onNetResponseCallback);

}
