package com.ysp.houge.model.entity.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.ysp.houge.model.entity.db.ItemWorkInfoEntity;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月13日下午6:44:38
 * @version 1.0
 */
public class WorkExperienceListDataEntity {
	/**
	 * @字段：chooseSchoolEntities
	 * @功能描述：工作经历列表
	 * @创建人：tyn
	 * @创建时间：2015年7月13日下午6:43:59
	 */
	@SerializedName(value = "workexperiencelist")
	public List<ItemWorkInfoEntity> itemWorkInfoEntities;

}
