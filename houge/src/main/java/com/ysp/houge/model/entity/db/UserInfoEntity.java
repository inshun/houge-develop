package com.ysp.houge.model.entity.db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @描述:个人信息实体类
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月13日下午7:45:55
 * @version 1.0
 */
@DatabaseTable(tableName = "userInfo")
public class UserInfoEntity implements Serializable {
	public static final int SEX_MAL = 1;
	public static final int SEX_FEMAL = 2;
	public static final int SEX_DEF = 0;

	private static final long serialVersionUID = 6454598234447615093L;

	@DatabaseField(columnName = "id", id = true)
	public int id;

	@DatabaseField(columnName = "username")
	public String username;

	@DatabaseField(columnName = "mobile")
	public String mobile;

	@DatabaseField(columnName = "email")
	public String email;

	@DatabaseField(columnName = "nick")
	public String nick;

	@DatabaseField(columnName = "sex")
	public int sex;

	@DatabaseField(columnName = "password")
	public String password;

	@DatabaseField(columnName = "goods_info_ids")
	public String goods_info_ids;

	@DatabaseField(columnName = "avatar")
	public String avatar;

	@DatabaseField(columnName = "integral")
	public int integral;// 积分

	@DatabaseField(columnName = "zan_count")
	public int zan_count;

	@DatabaseField(columnName = "view_count")
	public int view_count;

	@DatabaseField(columnName = "invite_id")
	public String invite_id;

	@DatabaseField(columnName = "comment_count")
	public int comment_count;

	@DatabaseField(columnName = "star")
	public double star;

	@DatabaseField(columnName = "balance")
	public double balance;

	@DatabaseField(columnName = "frozen_balance")
	public double frozen_balance;

	@DatabaseField(columnName = "created_at")
	public String created_at;

	@DatabaseField(columnName = "updated_at")
	public String updated_at;

	/** 认证 */
	@DatabaseField(columnName = "verfied")
	public String verfied;

	/** 服务保障 */
	@DatabaseField(columnName = "protected")
    @SerializedName(value = "protected")
	public String serviceSafeguardg;

	@DatabaseField(columnName = "latitude")
	public double latitude;

	@DatabaseField(columnName = "longitude")
	public double longitude;

	@DatabaseField(columnName = "protect_status")
	public String protect_status;

    @DatabaseField(columnName = "im_id")
	public String im_id;

	@DatabaseField(columnName = "im_pass")
	public String im_pass;

	@SerializedName(value = "token")
	public String token;
}
