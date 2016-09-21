package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @描述:注册和登录流程中的Model层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月23日上午11:21:29
 * @version 1.0
 */
public interface IRegisterAndLoginModel {
    int CODE_TYPE_CODE = 0; //短信验证码
    int CODE_TYPE_VOICE_CODE = 1; // 语音验证码

	/** 登录 */
	void login(String mobile, String password, OnNetResponseCallback netResponseListener);

	/** 验证码 */
	void getCodeRequest(String mobile, int type, OnNetResponseCallback netResponseCallback);

	/** 验证验证 */
	void checkCodeRequest(String mobile, String code, OnNetResponseCallback netResponseListener);

	/** 注册  */
	void registerRequest(String mobile, String code, String password, String nick, String avatar,String sex, String invite_id,
			OnNetResponseCallback onNetResponseCallback);
	
	void findPwdRequest(String mobile, String code, String password,OnNetResponseCallback onNetResponseCallback);
}
