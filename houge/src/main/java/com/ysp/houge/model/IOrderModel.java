package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

public interface IOrderModel {

	/** 获取个人信息 */
	void meInfoRequest(boolean needSave, OnNetResponseCallback onNetResponseCallback);

	void createOrder(int good_id, int quantity, int address_id, String memo, int pay_type, int type, String startDate,int buyer_create_user,
			OnNetResponseCallback netResponseCallback);

	void getOrderDetails(String order_id, OnNetResponseCallback onNetResponseCallback);

	void getSignByOrderId(String order_id, OnNetResponseCallback onNetResponseCallback);

	void changeOrderStatus(String order_id, int status, String tip, int apt,
			OnNetResponseCallback onNetResponseCallback);

	void orderRate(String order_id, float star, String content, int apt, OnNetResponseCallback onNetResponseCallback);

	void payByBalcance(String order_id, OnNetResponseCallback onNetResponseCallback);

	void changePrice(String order_id, double price, OnNetResponseCallback onNetResponseCallback);

    void requesTrefund(String order_id,String refund_content,OnNetResponseCallback onNetResponseCallback);

    void confirmRefund(String order_id,OnNetResponseCallback onNetResponseCallback);
}
