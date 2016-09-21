package com.ysp.houge.presenter;

/**
 * @描述: 注册页面Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月16日下午6:05:06
 * @version 1.0
 */
public interface IRegisterPresenter {

	/** 获取验证码 */
	void getCode(String phoneNum);

	/** 获取验短信证码 */
	void codeRequest(String phoneNum, int op);

	/** 获取验语音证码 */
	void getVoiceCode(String phoneNum);

	/** 下一步 */
	void nextStup(String phoneNum, String code, String pwd);
}
