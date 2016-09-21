package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.OrderEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * @author it_hu
 *
 *         预定 Presenter层接口
 */
public interface IOrderPresenter {

    void setIsNeed();
	
	void getDefaultAddress(int user_id);

	/** 设置订单实体 根据接收上一个页面信息创建 */
	void setOrderInfo(OrderEntity entity);

	/** 安全协议点击 */
	void safeDealClick();

	/** 优惠券点击 */
	void chooseCoupomClick();

	/** 联系方式点击 */
	void ContactWayClick();

	/** 联系方式选择返回 */
	void ContactWayChooseFinish(AddressEntity entity);

	/** 服务时间点击 */
	void serviceTimeClick();

	/** 联系方式选择返回 */
	void serviceTimeChooseFinish(String time,int date);

	/** 支付方式 */
	void changePayWay(int payWay);

	/** 支付 */
	void pay(String memo);

	/** 验证支付宝签名 */
	void checkAlipaySign(String sign);

	/** 获取用户信息 */
	void getUserInfo();

}
