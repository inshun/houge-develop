package com.ysp.houge.presenter;

/**
 * @描述:登录页面的MVP模式中的presenter层的接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月23日下午8:45:25
 * @version 1.0
 */
public interface IFindPwdPresenter {

    /** 发送验证码 op为发送短信的类型，请看IRegisterAndLoginModel常量 */
	void getCode(String phoneNum, int op);

    /** 找回密码 */
	void finishFindPassword(String phoneNum, String code, String newPassword);
}
