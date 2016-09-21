package com.ysp.houge.ui.iview;

/**
 * @描述:个人信息页面的操作view
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日上午10:38:51
 * @version 1.0
 */
public interface IMeInfoPageView extends IBaseView {

	/** 显示头像 */
	void updateAvatar(String avatarUrl);

	/** 更新昵称 */
	void updateNickName(String nickname);

	/** 更新性别 */
	void updateSex(int sex);

	/** 更新认证状态 */
	void updateAuth(String authentication);

	/** 更新认证状态 */
	void updateStudentAuth(String authentication);

	/** 更新认证状态 */
	void updateCompanyAuth(String authentication);

	boolean isProgressDialogShow();

	void showChoosePictureDialog();

	void jumpToNickNamePage(String nickname);

	void jumpToSexPage(int sex);

	void jumpToAuth(int index);

}
