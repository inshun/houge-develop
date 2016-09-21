package com.ysp.houge.presenter;

import com.ysp.houge.ui.order.OrderDetailsFragment;

/**
 * 描述： 订单详情Fragment Presenter层接口
 *
 * @ClassName: IOrderDetailsFragmentPrsenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月16日 上午11:38:05
 * 
 *        版本: 1.0
 */
public interface IOrderDetailsFragmentPrsenter {
	/** 设置当前商品订单信息 */
	void setOrderInfo(String order_id, String status, int operation, OrderDetailsFragment orderDetailsFragment);

	/** 获取订单详情 */
	void requstOrderDetails();

    void goodClick();

    void userClick();

	void statusOperate();

	/** 打电话 */
	void callPhone();

	/** 发短信 */
	void sendMessage();

	/** 改价点击之后 */
	void changePrice();

	/** 改价 */
	void changePrice(String price);

	/** 取消订单 */
	void cancleOrder();

    /**申请退款*/
    void requesTrefund(String order_id, String refund_content);

    /**同意退款*/
    void confirmRefund(String order_id);

}
