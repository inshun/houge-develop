package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * 描述： 卖出的订单实体
 *
 * @ClassName: MySellEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月17日 下午7:57:57
 * 
 *        版本: 1.0
 */
public class MySellEntity implements Serializable {
	private static final long serialVersionUID = 9101056796000711797L;

	public int id;
	public String order_id;
	public double unit_price;
	public int quantity;
	public double total_fee;
	public double user_should_fee;
	public int address_id;
	public int good_id;
	public int good_user_id;
	public int type;
	public String memo;
	public String name;
	public int pay_type;
	public int status;
	public int seller_user_id;
	public int buyer_user_id;
	public int is_payed;
	public int is_buyer_rate;
	public int is_seller_rate;
	public String created_at;
	public String updated_at;
	public UserInfoEntity buyer_user;
	public AddressEntity address_info;
	public GoodsDetailEntity good_info;
    public Param param;
    public Order order;

    public class Param{
        public String start_at;
    }

    public class Order{
        public  String status_text;
    }
}
