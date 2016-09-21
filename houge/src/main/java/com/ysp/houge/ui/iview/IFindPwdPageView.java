package com.ysp.houge.ui.iview;

/**
 * @描述:找回密码View层接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月22日下午8:35:47
 * @version 1.0
 */
public interface IFindPwdPageView extends IBaseView {
	/** 开启倒计时(发送短信的) */
	void startTimeCount(int count);

    //跳转到登录
	void jumpToLogin(String phoneNum);
}
