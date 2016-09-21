package com.ysp.houge.ui.iview;

/**
 * 描述： 完善注册信息View层接口
 *
 * @ClassName: IPerfectRegisterInfoPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月24日 下午1:10:50
 * 
 *        版本: 1.0
 */
public interface IPerfectRegisterInfoPageView extends IBaseView {
	/** 选择头像 */
	void choosePicture();

	/** 设置性别,根据设置的性别显示对应的图片 */
	void setAvatarAndSex(String avatarUrl,int sex);

	/** 跳转到主页面 */
	void jumpToHomePage(int Uid);
}
