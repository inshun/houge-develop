package com.ysp.houge.presenter;

/**
 * @描述:预支工资第一步页面的MVP模式中的presenter层的接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月23日下午8:45:25
 * @version 1.0
 */
public interface IPriPaidStepOnePagePresenter {

	/**
	 * @描述:点击下一步按钮
	 * @方法名: clickNextBtn
	 * @param advanceMoney
	 * @param collectionAccount
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年7月12日下午2:50:48
	 * @修改人 tyn
	 * @修改时间 2015年7月12日下午2:50:48
	 * @修改备注
	 * @since
	 * @throws
	 */
	void clickNextBtn(String advanceMoney, String collectionAccount);

	/**
	 * @描述:获取认证状态
	 * @方法名: getPriAuthState
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午3:21:42
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午3:21:42
	 * @修改备注
	 * @since
	 * @throws
	 */
	void getPriAuthState();
}
