package com.ysp.houge.presenter;

/**
 * @描述:个人信息页面的p层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日上午10:35:23
 * @version 1.0
 */
public interface IMeInfoPresenter {
	/** 获取个人信息接口 */
	void getMeInfo();

	/** 更新头像 */
	void updateAvatar(String cropAvatarPath);

	/** 点击头像布局 */
	void clickAvatarLayout();

	/** 点击昵称布局 */
	void clickNicknameLayout();

	/** 点击性别布局 */
	void clickSexLayout();

	/** 认证 */
	void clickAuthLayout(int index);
}
