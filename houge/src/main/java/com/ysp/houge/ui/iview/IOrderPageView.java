package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.OrderEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

import android.os.Bundle;

/**
 * @author it_hu
 *
 *         预定View层接口
 */
public interface IOrderPageView extends IBaseView {

	/** 订单页用于传递订单技能的Key */
	public static final String ORDER_SKILL_KEY = "skill";

	/** 显示订单基本信息 */
	void showOrderInfo(OrderEntity entity);

	/** 跳转安全协议 */
	void jumpToSafeDeal(Bundle bundle);

	/** 跳转优惠券 */
	void jumpToChooseCoupon(Bundle bundle);

	/** 跳转地址列表页面 */
	void jumpToChooseAddress(Bundle bundle);

	/** 显示地址 */
	void showAddress(AddressEntity entity);

	/** 显示服务时间对话框 */
	void showServiceTimeDialog();

	/** 显示服务时间 */
	void showServiceTime(String ServiceTime);

	/**
	 * 显示服务时间对话框
	 * 
	 * 支付方式使用IOrderModel 中PAY_TYPE前缀常量
	 */
	void changePayWay(int payWay);

	/** 支付宝支付 */
	void alipay(String sign);

	/** 订单详情页面 */
	void jumpToOrderDetails(Bundle bundle);

	/** 显示用户 信息 */
	void showUserInfo(UserInfoEntity info);

}
