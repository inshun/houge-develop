package com.ysp.houge.ui.iview;

import android.os.Bundle;

import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.bean.OrderEntity;

/**
 * 描述： 技能详情Fragment View层接口
 *
 * @ClassName: IOrderDetailsFragmentView
 *
 * @author: hx
 *
 * @date: 2015年12月16日 上午11:35:29
 *
 *        版本: 1.0
 */
public interface IOrderDetailsFragmentView extends IBaseView {

	/** 设置订单基本信息 */
	void setOrderInfo(String order_id, String status, int operation);

	/** 显示订单详情 */
	void setOrderInfo(OrderDetailsEntity orderDetailsEntity);

	/** 跳转支付页面 */
	void jumpToPay(Bundle bundle);

	/** 跳转到下订单页面去 */
	void jumpToOrder(OrderEntity orderEntity);

	/** 跳转到评价页面去 */
	void jumpToComment(String id);

	/** 打电话 */
	void callPhone(String phone);

	void jumpToChatPage();

	void jumpToSkilldetail(int id);

	void jumpToNeedDetail(int id);

	void jumpToUser();

	/** 显示更改价格pop */
	void showChangePirceDialog();

	/** 显示退款理由pop */
	void showTrefundReasonDialog();

	/**显示确认完称Dialog*/
	void showSureFinishDoalog();

	/*获申请退款or同意退款的Text*/
	String getBtnText();

	/*获取订单状态的text*/
	String getOrderStaus();
}
