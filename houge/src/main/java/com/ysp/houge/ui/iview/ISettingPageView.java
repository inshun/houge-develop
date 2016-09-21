package com.ysp.houge.ui.iview;

/**
 * @author it_hu
 * 
 *         设置页面View层接口
 *
 */
public interface ISettingPageView extends IBaseView {
	/** 显示联系方式 */
	void showContact(String officePublicNumber, String customServiceNumber);

	/** 显示拨打电话提升框 */
	void showCallPhoneDialog(String telephone);
}
