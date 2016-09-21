/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.dbcontroller;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.ysp.houge.dbmanager.DBController;
import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * This class is used for 登录用户的数据库操作类
 * 
 * @author tyn
 * @version 1.0, 2014-9-20 下午5:14:02
 */

public class UserInfoController {
	// 数据库用户表中各列的的列名(需要更改单列的时候才会用到)
	public static final String ID = "id";
	public static final String NICK = "nick";
	public static final String PASSWORD = "password";
	public static final String SEX = "sex";
	public static final String AVATAR = "avatar";

	@SuppressWarnings("unchecked")
	public static Dao<UserInfoEntity, Integer> getDao() throws SQLException {
		return DBController.getInstance().getDB().getDao(UserInfoEntity.class);
	}

	public static void createOrUpdate(UserInfoEntity user) {
		try {
			getDao().createOrUpdate(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @描述:更新某一个行的内容 @方法名: update @param user @return @返回类型 int @创建人 tyn @创建时间
	 *               2015年7月12日下午7:20:55 @修改人 tyn @修改时间
	 *               2015年7月12日下午7:20:55 @修改备注 @since @throws
	 */
	public static int update(UserInfoEntity user) {
		try {
			return getDao().update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int update(String key, Object value, int id) {
		try {
			UpdateBuilder<UserInfoEntity, Integer> updateBuilder = getDao().updateBuilder();
			updateBuilder.updateColumnValue(key, value).where().eq(ID, id);
			return updateBuilder.update();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static UserInfoEntity get(int id) {
		try {
			return getDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
