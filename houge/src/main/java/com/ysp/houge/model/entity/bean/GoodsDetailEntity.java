package com.ysp.houge.model.entity.bean;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ysp.houge.model.entity.db.UserInfoEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 技能详情实体
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月1日下午3:24:49
 * @version 1.0
 */
@DatabaseTable(tableName = "skill")
public class GoodsDetailEntity implements Serializable {
	/** 审核中 */
	public final static int STATUS_UNDER_REVIEW = 0;
	/** 审核通过 */
	public final static int STATUS_PASS = 1;
	/** 审核未通过 */
	public final static int STATUS_UN_PASS = 2;
	/** serialVersionUID */
	private static final long serialVersionUID = -74135326011815795L;
	@DatabaseField(columnName = "id", id = true)
	public int id;// 编号

	@DatabaseField(columnName = "title")
	public String title;// 技能名称

	@DatabaseField(columnName = "desc")
	public String desc;// 技能描述

	// 实际解析时的图片数组
	public List<String> image;

	@DatabaseField(columnName = "imgs")
	public String imgs;// 图片,多张图用逗号隔开

	@DatabaseField(columnName = "price")
	public String price;// 单价

	@DatabaseField(columnName = "unit")
	public String unit;// 计价单位

	@DatabaseField(columnName = "user_id")
	public int user_id;// 用户id

	@DatabaseField(columnName = "is_system" )
	public int is_system;

	@DatabaseField(columnName = "goods_info_ids")
	public String goods_info_ids;

	@DatabaseField(columnName = "view_count")
	public int view_count;// 查看次数

	@DatabaseField(columnName = "order_count")
	public int order_count;// 购买次数

	@DatabaseField(columnName = "comment_count")
	public int comment_count;// 评论次数

	@DatabaseField(columnName = "param")
	public String param;

	@DatabaseField(columnName = "geohash")
	public String geohash;// 这个暂时不知道干什么用的

	@DatabaseField(columnName = "latitude")
	public double latitude;// 经纬度

	@DatabaseField(columnName = "longitude")
	public double longitude;// 经纬度

	@DatabaseField(columnName = "distance")
	public String distance;

	@DatabaseField(columnName = "address_detail")
	public String address_detail;

	@DatabaseField(columnName = "status")
	public int status;

    @DatabaseField(columnName = "mobile")
	public String mobile;

	@DatabaseField(columnName = "start_time")
	public String start_time;

	@DatabaseField(columnName = "level")
    @SerializedName(value = "level_id")
	public String level;

    @SerializedName(value = "reason")
	public String reason;

	@SerializedName(value = "user")
	public UserInfoEntity userInfo;

	@SerializedName(value = "address")
	public AddressEntity address;
}
