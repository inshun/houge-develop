package com.ysp.houge.ui.iview;

/**
 * @描述: 优惠券页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月29日下午6:39:32
 * @version 1.0
 */
public interface ICouponPaperView extends IBaseView {

	/**
	 * @描述: 可用被选中 @方法名: onValidChoise @返回类型 void @创建人 tyn @创建时间
	 * 2015年10月5日下午12:49:56 @修改人 tyn @修改时间
	 * 2015年10月5日下午12:49:56 @修改备注 @since @throws
	 */
	void onValidChoise();

	/**
	 * @描述:过期被选中 @方法名: onPastDateChoise @返回类型 void @创建人 tyn @创建时间
	 * 2015年10月5日下午12:50:28 @修改人 tyn @修改时间
	 * 2015年10月5日下午12:50:28 @修改备注 @since @throws
	 */
	void onPastDateChoise();

	/**
	 * @描述:使用 @方法名: use @返回类型 void @创建人 tyn @创建时间 2015年9月29日下午9:07:30 @修改人
	 * tyn @修改时间 2015年9月29日下午9:07:30 @修改备注 @since @throws
	 */
	void use();

	/**
	 * @描述: 取消 @方法名: cancle @返回类型 void @创建人 tyn @创建时间 2015年9月29日下午9:07:43 @修改人
	 * tyn @修改时间 2015年9月29日下午9:07:43 @修改备注 @since @throws
	 */
	void cancle();

	/**
	 * @描述: 改变底部使用取消按钮的可见状态 @方法名: changeButtomStatus @param isShow 可见状态 @返回类型
	 * void @创建人 tyn @创建时间 2015年9月29日下午9:07:52 @修改人 tyn @修改时间
	 * 2015年9月29日下午9:07:52 @修改备注 @since @throws
	 */
	void changeButtomStatus(boolean isShow);
}
