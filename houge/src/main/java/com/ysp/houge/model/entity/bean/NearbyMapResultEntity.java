package com.ysp.houge.model.entity.bean;

import com.ysp.houge.model.entity.db.UserInfoEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： 地图页面的返回结果实体
 *
 * @ClassName: NearbyMapResultEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月19日 下午1:44:57
 * 
 *        版本: 1.0
 */
public class NearbyMapResultEntity implements Serializable {
	private List<UserInfoEntity> userList;

	public List<UserInfoEntity> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfoEntity> userList) {
		this.userList = userList;
	}

}
