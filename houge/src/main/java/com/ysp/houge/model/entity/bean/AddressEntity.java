package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @描述: 常用地址
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月24日下午12:36:36
 * @version 1.0
 */
@DatabaseTable(tableName = "address")
public class AddressEntity implements Serializable {

	public final static String DEFAULT_TYPE_NO = "no";
	public final static String DEFAULT_TYPE_YES = "yes";
	private static final long serialVersionUID = -7934062697947375568L;
	/** 编号 */
	@DatabaseField(columnName = "id", id = true)
	public int id;
	
	/** 省份 */
	@DatabaseField(columnName = "province")
	public String province;
	
	/** 城市 */
	@DatabaseField(columnName = "city")
	public String city;
	
	/** 街道 */
	@DatabaseField(columnName = "street")
	public String street;
	
	/** 联系人 */
	@DatabaseField(columnName = "real_name")
	public String real_name;
	
	/** 联系方式 */
	@DatabaseField(columnName = "mobile")
	public String mobile;
	
	/** 地理位置 经度 */
	@DatabaseField(columnName = "longitude")
	public Double longitude;
	
	/** 地理位置 纬度 */
	@DatabaseField(columnName = "latitude")
	public Double latitude;
	
	/** 是否是默认位置 */
	@DatabaseField(columnName = "is_default")
	public String is_default;
	
	/** 用户id */
	@DatabaseField(columnName = "user_id")
	public int user_id;
	
	@DatabaseField(columnName = "created_at")
	public int created_at;
	
	@DatabaseField(columnName = "updated_at")
	public String updated_at;
	
	@DatabaseField(columnName = "type")
	public int type;
}
