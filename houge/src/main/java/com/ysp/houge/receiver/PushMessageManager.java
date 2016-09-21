package com.ysp.houge.receiver;

import android.content.Context;
import android.text.TextUtils;

import com.igexin.sdk.PushManager;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.model.entity.eventbus.PushInfoEventBusEntity;

/**
 * @描述:推送消息管理类
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月20日下午3:07:34
 * @version 1.0
 */
public class PushMessageManager {
	// 1jiehuo://openService?id=123
	// 打开技能服务页面
	// 2jihuo://openWork?id=123
	// 打开任务需求页面
	// 3jiehuo://openNotice
	// 打开消息列表页面
	// 4jiehuo://openH5?url=url
	// 打开h5
	// 5jiehuo://openOrderDetail?id=id
	// 打开订单消息页面
	//6jiehuo://openUserChangelog
	//打开余额详情页面
	private static final String OPEN_SKILL = "openService";
	private static final String OPEN_NEED = "openWork";
	private static final String OPEN_MESSAGE = "openNotice";
	private static final String OPEN_H5 = "openH5";
	private static final String OPEN_ORDER = "openOrderDetail";
	private static final String OPEN_USER_CHANGELOG = "openUserChangelog";

	public static void handlePushMsg(Context context, String data) {
		if (data.indexOf(OPEN_SKILL) >= 0) {
			// 解析出来id
			String skill_id = data.substring(data.indexOf("id=") + 3, data.length());
			int id = -1;
			try {
				id = Integer.parseInt(skill_id);
			} catch (Exception e) {
			}

			EventBus.getDefault().post(new PushInfoEventBusEntity(PushInfoEventBusEntity.PAGE_SKILL, id));
		} else if (data.indexOf(OPEN_NEED) >= 0) {
			// 解析出来id
			String skill_id = data.substring(data.indexOf("id=") + 3, data.length());
			int id = -1;
			try {
				id = Integer.parseInt(skill_id);
			} catch (Exception e) {
			}

			EventBus.getDefault().post(new PushInfoEventBusEntity(PushInfoEventBusEntity.PAGE_NEED, id));
		} else if (data.indexOf(OPEN_MESSAGE) >= 0) {
			EventBus.getDefault().post(new PushInfoEventBusEntity(PushInfoEventBusEntity.PAGE_MESSAGE, 0));
		} else if (data.indexOf(OPEN_H5) >= 0) {
			// 解析出来id
			String url = data.substring(data.indexOf("url=") + 4, data.length());
			EventBus.getDefault().post(new PushInfoEventBusEntity(PushInfoEventBusEntity.PAGE_WEB, url));
		} else if (data.indexOf(OPEN_ORDER) >= 0) {
			// 解析出来id
			String skill_id = data.substring(data.indexOf("id=") + 3, data.length());
            if (TextUtils.isEmpty(skill_id))
                return;
			EventBus.getDefault().post(new PushInfoEventBusEntity(PushInfoEventBusEntity.PAGE_ORDER, skill_id));
		} else if(data.indexOf(OPEN_USER_CHANGELOG) > 0){
			EventBus.getDefault().post(new PushInfoEventBusEntity(PushInfoEventBusEntity.PAGE_USER_CHANGELOG, 0));
		}
	}

	public static void bindAlias(Context context, int uid) {
		PushManager.getInstance().bindAlias(context, uid + "");
	}

	public static void unBindAlias(Context context, int uid) {
		PushManager.getInstance().unBindAlias(context, uid + "", true);
	}
}
