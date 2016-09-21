package com.ysp.houge.ui.iview;

import android.os.Bundle;

/**
 * @描述: 注册页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月16日下午3:44:24
 * @version 1.0
 */
public interface IRegisterPageView extends IBaseView {

	/** 获取验证码后倒计时 */
	void sendCodeCountDown(int countDownSecond);

	/** 跳转完善注册信息页面 */
	void jumpToWriteInfo(Bundle bundle);
}
