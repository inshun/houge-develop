package com.ysp.houge.model.entity.eventbus;

/**
 * @author it_hu
 * 
 *         更换身份改变的EventBus消息
 *
 */
public class ChangeLoginStatusEventBusEntity {
	private int tab;

	public ChangeLoginStatusEventBusEntity(int tab) {
		this.tab = tab;
	}

	public int getTab() {
		return tab;
	}

}
