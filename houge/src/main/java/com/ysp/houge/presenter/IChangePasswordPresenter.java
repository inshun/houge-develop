package com.ysp.houge.presenter;

/**
 * @描述:通过老密码修改密码p层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日下午1:12:59
 * @version 1.0
 */
public interface IChangePasswordPresenter {

	void requstSubmit();

	void checkCanSubmitState(String oldPassword, String newPassword, String newRePassword);
}
