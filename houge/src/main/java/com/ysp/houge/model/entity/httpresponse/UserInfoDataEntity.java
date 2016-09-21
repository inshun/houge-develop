package com.ysp.houge.model.entity.httpresponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @描述:用户个人信息返回的数据实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月23日上午10:23:03
 * @version 1.0
 */
public class UserInfoDataEntity {
	@SerializedName(value = "uid")
	public int uid;
	@SerializedName(value = "schoolname")
	public String schoolname;
	@SerializedName(value = "image_url")
	public String image_url;
	@SerializedName(value = "nickname")
	public String nickname;
	@SerializedName(value = "sex")
	public int sex;
	@SerializedName(value = "schoolid")
	public int schoolid;
	@SerializedName(value = "inschooltime")
	public String inschooltime;
	@SerializedName(value = "educational")
	public int educational;
	@SerializedName(value = "bankcard")
	public String bankcard;
	@SerializedName(value = "cando")
	public String cando;
	@SerializedName(value = "lat")
	public double lat;
	@SerializedName(value = "lng")
	public double lng;
	@SerializedName(value = "mobile")
	public String mobile;
	@SerializedName(value = "range")
	public int range;
	@SerializedName(value = "authStatus")
	public int authStatus;
	@SerializedName(value = "score")
	public float score;
	@SerializedName(value = "partTimeJobTypeEntities")
	public List<WorkTypeEntity> workTypeEntities;
	@SerializedName(value = "truename")
	public String truename;
	@SerializedName(value = "idcard")
	public String idcard;
	@SerializedName(value = "idcardPic")
	public String idcardPic;
	@SerializedName(value = "studentidPic")
	public String studentidPic;
}
