package com.ysp.houge.model.entity.db;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 4588073186576217946L;

	@DatabaseField(columnName = "id", id = true)
	public int id;

	@DatabaseField(columnName = "nick")
	public String nick;

	@DatabaseField(columnName = "avatar")
	public String avatar;

	@DatabaseField(columnName = "sex")
	public int sex;

	public UserEntity() {
	}

	public UserEntity(int id, String nick, String avatar, int sex) {
		super();
		this.id = id;
		this.nick = nick;
		this.avatar = avatar;
		this.sex = sex;
	}
}
