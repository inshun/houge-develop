package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

public interface ISettingModel {

	/** 设置服务范围、消息提醒 */
	void setting(String night_notify_status, String sound_status, String shake_status, String service_distance,
			OnNetResponseCallback onNetResponseCallback);

	/** 获取设置的服务范围、消息提醒 */
	void getSetting(OnNetResponseCallback onNetResponseCallback);

	/** 意见反馈请求 */
	void feedBackRequest(String content, OnNetResponseCallback onNetResponseCallback);

	/** 初始化接口(获取微信公众号、客服电话以及用户随机背景的网络请求 */
	void appInit(OnNetResponseCallback onNetResponseCallback);

	/** 修改密码的网络请求 */
	void changePasswordRequest(String oldPassword, String newPassword, String reNewPassword,
			OnNetResponseCallback onNetResponseCallback);

	/** 获取关于我们信息 */
	void getAboutUs(OnNetResponseCallback onNetResponseCallback);

    /**获取所有的计数*/
    void getAllCount();
}
