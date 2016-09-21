package com.ysp.houge.dbcontroller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ysp.houge.dbmanager.DBController;
import com.ysp.houge.model.entity.db.SearchHistoryEntity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchHistoryContorller {
	@SuppressWarnings("unchecked")
	public static Dao<SearchHistoryEntity, Integer> getDao() throws SQLException {
		return DBController.getInstance().getDB().getDao(SearchHistoryEntity.class);
	}

	private static List<SearchHistoryEntity> getSearchByTypeAndText(SearchHistoryEntity search) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", search.text);
			map.put("searctType", search.searctType);
			List<SearchHistoryEntity> list = getDao().queryForFieldValues(map);
			if (list != null) {
				return list;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	};

	/** 新建 */
	public static void create(SearchHistoryEntity search) {
		try {
			List<SearchHistoryEntity> list = getSearchByTypeAndText(search);
			if (!list.isEmpty() && list.size() > 0) {
				// 已经存在，把现有的删除，然后新增
				getDao().deleteById(list.get(0).id);
			}
			getDao().create(search);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** 根据类型查询 */
	public static List<SearchHistoryEntity> getByType(int searctType) {
		try {
			QueryBuilder<SearchHistoryEntity, Integer> builder = getDao().queryBuilder();
			// 最大10个
			builder.limit(10L);
			builder.where().eq("searctType", searctType);
			// id倒序查询
			builder.orderByRaw("id DESC");
			return builder.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** 根据类型删除 */
	@SuppressWarnings("unchecked")
	public static int delByType(int searctType) {
		try {
			return getDao().delete((PreparedDelete<SearchHistoryEntity>) getDao().deleteBuilder().where()
					.eq("searctType", searctType).prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
