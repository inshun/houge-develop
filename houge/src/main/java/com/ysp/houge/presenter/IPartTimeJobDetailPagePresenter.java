package com.ysp.houge.presenter;

/**
 * @描述:兼职详情页面的p层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月22日下午3:22:06
 * @version 1.0
 */
public interface IPartTimeJobDetailPagePresenter {

	/**
	 * @描述:请求兼职详情信息
	 * @方法名: requestDetailInfo
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:22:20
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:22:20
	 * @修改备注
	 * @since
	 * @throws
	 */
	void requestDetailInfo();

	/**
	 * @描述:点击拨打电话按钮
	 * @方法名: clickCallTelephoneBtn
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:23:08
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:23:08
	 * @修改备注
	 * @since
	 * @throws
	 */
	void clickCallTelephoneBtn();

	/**
	 * @描述:点击发送短信按钮
	 * @方法名: clickSendMessageBtn
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:23:19
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:23:19
	 * @修改备注
	 * @since
	 * @throws
	 */
	void clickSendMessageBtn();

	/**
	 * @描述:确认拨打电话
	 * @方法名: submitCallPhone
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:27:10
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:27:10
	 * @修改备注
	 * @since
	 * @throws
	 */
	void submitCallPhone();

}
