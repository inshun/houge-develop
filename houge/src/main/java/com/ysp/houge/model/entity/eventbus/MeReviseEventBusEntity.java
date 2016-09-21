package com.ysp.houge.model.entity.eventbus;

/**
 * @描述:我页面修改操作标示实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日下午2:05:22
 * @version 1.0
 */
public class MeReviseEventBusEntity {
	private MeReviseType meReviseType;
	private Object object;
	public MeReviseEventBusEntity(MeReviseType meReviseType, Object object) {
		super();
		this.meReviseType = meReviseType;
		this.object = object;
	}

	public MeReviseType getMeReviseType() {
		return meReviseType;
	}

	public void setMeReviseType(MeReviseType meReviseType) {
		this.meReviseType = meReviseType;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public enum MeReviseType {
		/** 修改昵称 */
		ReviseNickName,
		/** 修改性别 */
		ReviseSex,
	}

}
