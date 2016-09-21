package com.ysp.houge.presenter;

/**
 * @author it_hu
 * 
 *         设置页面的Presenter层接口
 *
 */
public interface ISettingPagePresenter {
	/** 获取app的联系方式 (微信、客服电话) */
	void getAppContacts();

	/** 联系我们 */
	void clickCustomService();
}
