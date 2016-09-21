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
package com.ysp.houge.dbmanager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ysp.houge.BuildConfig;
import com.ysp.houge.model.entity.db.SearchHistoryEntity;
import com.ysp.houge.model.entity.db.UserEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @描述:数据库创建类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月23日下午12:13:40
 * @version 1.0
 */
@SuppressWarnings("unused")
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String TABLE_NAME = "houge.db";
	private static final int DATABASE_VERSION = 1;
	private static DatabaseHelper instance;
	private Context context;
	@SuppressWarnings("rawtypes")
	private Map<String, Dao> daos = new HashMap<String, Dao>();

	private DatabaseHelper(Context context) {
		super(context, TABLE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	/**
	 * @描述:DatabaseHelper使用单例模式，且在整这个应用的生命周期只初始化一次，便于整个应用获取数据库帮助类 @方法名:
	 *                                                            init @param
	 *                                                            context @返回类型
	 *                                                            void @创建人
	 *                                                            tyn @创建时间
	 *                                                            2015年8月23日下午12
	 *                                                            :15:31 @修改人
	 *                                                            tyn @修改时间
	 *                                                            2015年8月23日下午12
	 *                                                            :15:
	 *                                                            31 @修改备注 @since @throws
	 */
	public static void init(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context);
		} else {
			Log.d("DatabaseHelper", "DatabaseHelper has been inited!");
		}
	}

	/**
	 * 单例获取该Helper
	 *
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelper getHelper() {
		if (instance == null) {
			throw new IllegalStateException("DatabaseHelper has not been inited.");
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		try {
			TableUtils.createTableIfNotExists(connectionSource, UserInfoEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, SearchHistoryEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

		if (oldVersion < 2) {
			try {
				onUpdateV2(database, connectionSource);
			} catch (SQLException e) {
				if (BuildConfig.DEBUG) {
					Log.d("DatabaseHelper", e.getMessage());
				}
			}
		}
	}

	/**
	 * @描述: @方法名: onUpdateV2 @param db @param connectionSource @throws
	 *      SQLException @返回类型 void @创建人 tyn @创建时间 2015年6月17日下午7:52:52 @修改人
	 *      tyn @修改时间 2015年6月17日下午7:52:52 @修改备注 @since @throws
	 */
	private void onUpdateV2(SQLiteDatabase db, ConnectionSource connectionSource) throws SQLException {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public synchronized Dao getDao(Class clazz) throws SQLException {
		Dao dao = null;
		String className = clazz.getSimpleName();

		if (daos.containsKey(className)) {
			dao = daos.get(className);
		}
		if (dao == null) {
			dao = super.getDao(clazz);
			daos.put(className, dao);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close() {
		super.close();

		for (String key : daos.keySet()) {
			@SuppressWarnings("rawtypes")
			Dao dao = daos.get(key);
			dao = null;
		}
	}
}
