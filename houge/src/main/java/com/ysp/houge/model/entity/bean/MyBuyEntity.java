package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * 描述： 我购买的实体
 *
 * @ClassName: MyBuyEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月24日11:54:03
 * 
 *        版本: 1.0
 */
public class MyBuyEntity implements Serializable {

	private static final long serialVersionUID = 4572597975285174343L;

	public int id;
	public String order_id;
	public String trade_id;
	public double unit_price;
	public int quantity;
	public double discount_fee;
	public double total_fee;
	public double user_should_fee;
	public int address_id;
	public int good_id;
	public int good_user_id;
	public int type;
	public String memo;
	public String name;
	public int pay_type;
	public int seller_user_id;
	public int buyer_user_id;
	public int is_payed;
	public int verfied;
	public int status;
	public int is_seller_rate;
	public int is_buyer_rate;
	public String created_at;
	public String updated_at;
	public UserInfoEntity seller_user;
	public AddressEntity address_info;
	public GoodsDetailEntity good_info;

    public Order order;
    public class Order{
        public  String status_text;
    }
}
