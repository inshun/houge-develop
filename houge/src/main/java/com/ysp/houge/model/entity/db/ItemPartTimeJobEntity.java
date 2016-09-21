package com.ysp.houge.model.entity.db;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.ysp.houge.model.entity.bean.CharacteristicEntity;

/**
 * @描述:首页推荐的兼职列表项
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月26日上午10:06:48
 * @version 1.0
 */
public class ItemPartTimeJobEntity implements Serializable {
	/**
	 * @字段：serialVersionUID
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年6月28日上午7:07:24
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @字段：id
	 * @功能描述：兼职的ID
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:43:05
	 */
	@SerializedName(value = "id")
	public int id;
	/**
	 * @字段：title
	 * @功能描述：兼职信息的标题
	 * @创建人：tyn
	 * @创建时间：2015年6月26日上午10:06:36
	 */
	@SerializedName(value = "title")
	public String title;
	/**
	 * @字段：price
	 * @功能描述：兼职的工资
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:43:24
	 */
	@SerializedName(value = "price")
	public String price;
	/**
	 * @字段：priceUnit
	 * @功能描述：工钱单位（例如：元/天）
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:43:41
	 */
	@SerializedName(value = "priceUnit")
	public String priceUnit;
	/**
	 * @字段：range
	 * @功能描述：距离
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:43:46
	 */
	@SerializedName(value = "range")
	public String range;
	/**
	 * @字段：time
	 * @功能描述：兼职的时间
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:43:56
	 */
	@SerializedName(value = "time")
	public String time;
	/**
	 * @字段：lat
	 * @功能描述：经度
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:48:04
	 */
	@SerializedName(value = "lat")
	public double lat;

	/**
	 * @字段：lng
	 * @功能描述：纬度
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:48:09
	 */
	@SerializedName(value = "lng")
	public double lng;

	/**
	 * @字段：priceWay
	 * @功能描述：结算方式
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:56:30
	 */
	public String priceWay;

	/**
	 * @字段：address
	 * @功能描述：兼职地址
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:56:40
	 */
	public String address;

	/**
	 * @字段：createTime
	 * @功能描述：兼职的创建时间
	 * @创建人：tyn
	 * @创建时间：2015年8月22日下午5:18:15
	 */
	@SerializedName(value = "createTime")
	public String createTime;

	/**
	 * @字段：startTime
	 * @功能描述：开始时间
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:56:50
	 */
	@SerializedName(value = "startTime")
	public String startTime;

	/**
	 * @字段：endTime
	 * @功能描述：结束时间
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:56:57
	 */
	@SerializedName(value = "endTime")
	public String endTime;

	/**
	 * @字段：peopleNum
	 * @功能描述：需要人数
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:57:05
	 */
	@SerializedName(value = "peopleNum")
	public String peopleNum;

	/**
	 * @字段：content
	 * @功能描述：工作内容
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:57:11
	 */
	@SerializedName(value = "content")
	public String content;

	/**
	 * @字段：memo
	 * @功能描述：其他说明
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:57:17
	 */
	@SerializedName(value = "memo")
	public String memo;

	/**
	 * @字段：contact
	 * @功能描述：发布人
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:57:41
	 */
	@SerializedName(value = "contact")
	public String contact;
	/**
	 * @字段：mobilePic
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年8月22日下午5:21:27
	 */
	@SerializedName(value = "mobilePic")
	public String mobilePic;
	/**
	 * @字段：mobile
	 * @功能描述：联系电话
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:57:51
	 */
	@SerializedName(value = "mobile")
	public String mobile;

	/**
	 * @字段：jobPersonStyle
	 * @功能描述：所需特点
	 * @创建人：tyn
	 * @创建时间：2015年8月15日下午6:57:59
	 */
	@SerializedName(value = "jobPersonStyle")
	public List<CharacteristicEntity> jobPersonStyle;

}
