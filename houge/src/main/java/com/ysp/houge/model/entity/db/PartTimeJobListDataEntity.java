package com.ysp.houge.model.entity.db;

import java.util.List;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月16日下午4:59:27
 * @version 1.0
 */
public class PartTimeJobListDataEntity {
	private List<ItemPartTimeJobEntity> jobList;

	/**
	 * @return the jobList
	 */
	public List<ItemPartTimeJobEntity> getJobList() {
		return jobList;
	}

	/**
	 * @param jobList
	 *            the jobList to set
	 */
	public void setJobList(List<ItemPartTimeJobEntity> jobList) {
		this.jobList = jobList;
	}

}
