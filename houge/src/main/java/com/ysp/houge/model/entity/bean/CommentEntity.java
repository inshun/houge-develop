package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * @author it_hu
 * 
 *         评论实体
 *
 */
public class CommentEntity implements Serializable {

	private static final long serialVersionUID = -8150130041644777845L;

	public int sex = UserInfoEntity.SEX_DEF;
	public String avatar;
	public int user_id;
	public String nick;
	public String content;
	public String created_at;
}
