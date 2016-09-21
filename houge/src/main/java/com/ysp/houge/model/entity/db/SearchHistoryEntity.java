package com.ysp.houge.model.entity.db;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 描述： 搜索历史实体
 *
 * @ClassName: SearchHistoryEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月6日 上午9:37:52
 * 
 *        版本: 1.0
 */
@DatabaseTable(tableName = "search_history")
public class SearchHistoryEntity implements Serializable {
	private static final long serialVersionUID = 4955376893137334317L;
	@DatabaseField(columnName = "id", generatedId = true)
	public int id;

	@DatabaseField(columnName = "text")
	public String text;// 搜索文本

	@DatabaseField(columnName = "searctType")
	public int searctType;// 搜索类型

	public SearchHistoryEntity() {
		super();
	}

	public SearchHistoryEntity(String text, int searctType) {
		super();
		this.text = text;
		this.searctType = searctType;
	}

}
