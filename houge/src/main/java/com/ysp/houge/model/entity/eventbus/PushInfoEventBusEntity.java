package com.ysp.houge.model.entity.eventbus;

/**
 * 描述： 推送跳转页面的实体
 *
 * @ClassName: PushInfoEventBusEntity
 * 
 * @author: hx
 * 
 * @date: 2016年1月6日 下午1:38:36
 * 
 *        版本: 1.0
 */
public class PushInfoEventBusEntity {

	public static final int PAGE_SKILL = 0;
	public static final int PAGE_NEED = 1;
	public static final int PAGE_MESSAGE = 2;
	public static final int PAGE_WEB = 3;
	public static final int PAGE_ORDER = 4;
	public static final int PAGE_USER_CHANGELOG = 5;
	public static final int PAGE_NOTICE = 6;

	public int page_id;

	public int id;

	public String url;

	public PushInfoEventBusEntity(int page_id, int id) {
		super();
		this.page_id = page_id;
		this.id = id;
	}

	public PushInfoEventBusEntity(int page_id, String url) {
		super();
		this.page_id = page_id;
		this.url = url;
	}

}
