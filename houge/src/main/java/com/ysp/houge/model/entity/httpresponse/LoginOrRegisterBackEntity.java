package com.ysp.houge.model.entity.httpresponse;

import com.google.gson.annotations.SerializedName;
import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月4日下午6:31:29
 * @version 1.0
 */
public class LoginOrRegisterBackEntity {
	/**
	 * @字段：token
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年7月4日下午6:31:24
	 */
	@SerializedName(value = "token")
	private String token;

	/**
	 * @字段：userInfo
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年7月4日下午6:31:26
	 */
	@SerializedName(value = "userinfo")
	private UserInfoEntity userInfo;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfoEntity getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(UserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

}
