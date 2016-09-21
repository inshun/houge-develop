package com.ysp.houge.model.entity.eventbus;

import com.ysp.houge.model.entity.bean.SchoolEntity;

/**
 * @描述:学校选择页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月16日下午1:28:39
 * @version 1.0
 */
public class SchoolChooseEventBusEntity {
	public int whichPageFrom;

	public SchoolEntity schoolEntity;

	/**
	 * @描述
	 * @param whichPageFrom
	 * @param schoolEntity
	 */
	public SchoolChooseEventBusEntity(int whichPageFrom,
			SchoolEntity schoolEntity) {
		super();
		this.whichPageFrom = whichPageFrom;
		this.schoolEntity = schoolEntity;
	}

}
