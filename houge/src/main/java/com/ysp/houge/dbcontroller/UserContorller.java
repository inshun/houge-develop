package com.ysp.houge.dbcontroller;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.ysp.houge.dbmanager.DBController;
import com.ysp.houge.model.entity.db.UserEntity;

public class UserContorller {

	@SuppressWarnings("unchecked")
	public static Dao<UserEntity, Integer> getDao() throws SQLException {
		return DBController.getInstance().getDB().getDao(UserEntity.class);
	}
	

	public static void createOrUpdate(UserEntity user) {
		try {
			getDao().createOrUpdate(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

	public static UserEntity get(int id) {
		try {
			return getDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
