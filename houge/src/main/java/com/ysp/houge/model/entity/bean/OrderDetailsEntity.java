package com.ysp.houge.model.entity.bean;

import com.ysp.houge.model.entity.db.UserInfoEntity;

import java.io.Serializable;

/**
 * 描述： 订单详情实体
 *
 * @ClassName: OrderDetailsEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月15日 下午3:24:26
 * 
 *        版本: 1.0
 */
public class OrderDetailsEntity implements Serializable {
	/** 订单类型 任务订单 */
	public static final int ORDER_TYPE_TASK = 1;
	/** 订单类型 技能订单 */
	public static final int ORDER_TYPE_SKILL = 2;
	private static final long serialVersionUID = -2079384178720573822L;
	public OrderEntity order;
	public GoodsDetailEntity good;
	public AddressEntity address;
    public UserInfoEntity seller;
    public UserInfoEntity buyer;

	public class OrderEntity {
		/** 等待付款 */
		public static final int STATUS_WAIT_PAY = 1;
		/** 等待服务 */
		public static final int STATUS_WAIT_SERVICE = 2;
		/** 服务人员已出发 */
		public static final int STATUS_DEPART = 3;
		/** 服务中 */
		public static final int STATUS_IN_SERVICE = 4;
		/** 等待待评价 */
		public static final int STATUS_WAIT_COMMENT = 5;
		/** 服务完成 */
		public static final int STATUS_SERVICE_FINISH = 6;
		/** 过期 */
		public static final int STATUS_STATUS_EXPIRE = 7;
		/** 冻结 */
		public static final int STATUS_FREEZE = 8;
		/** 订单完成 */
		public static final int STATUS_FINISH = 9;
		/** 关闭订单 */
		public static final int STATUS_CLOSE = 10;

		/** 支付类型 支付宝 */
		public static final int PAY_TYPE_ALIPAY = 1;
		/** 支付类型 当面付 */
		public static final int PAY_TYPE_FACE_TO_FACE = 2;
		/** 支付类型 余额 */
		public static final int PAY_TYPE_BALANCE = 3;

		public String order_id;
		public String created_at;
//		public int is_protect;
		public String memo;
		public int status;
		public int pay_type;
        public int type;
        public int is_payed;
        public int is_refund;
		public int is_seller_rate;
		public int is_buyer_rate;
		public int quantity;
		public String user_should_fee;
		public String total_fee;
        public String pay_type_text;
        public String status_text;
        public Param param;

        public class Param{
            public String start_at;
        }
	}
}
