/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ysp.houge.utility;

import com.ysp.houge.app.MyApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @描述:配置类
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月13日下午2:51:15
 * @version 1.0
 */
@SuppressLint("CommitPrefEdits")
public class PreferenceUtils {
	private static final String ID = "id";
	private static final String LOGIN_STATUS = "log_status";
	private static final String LOGIN_MOBILE = "mobile";
	private static final String GET_CLIENTID = "get_client_id";
	private static final String TOKEN = "token";
	private static final String APP_INIT = "app_init";
	private static final String CHAT_UNREAD_COUNT = "chatUnreadCount";
	private static final String LOCAL = "local";
	private static final String NEWJOIN_SKILL = "new_join_skill";
	private static final String NEWJOIN_NEED = "new_join_need";
	private static SharedPreferences mSharedPreferences;
	private static PreferenceUtils mPreferenceUtils;
	private static SharedPreferences.Editor editor;

	private PreferenceUtils(Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * 单例模式，获取instance实例
	 * 
	 * @param cxt
	 * @return
	 */
	public static PreferenceUtils getInstance(Context cxt) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new PreferenceUtils(cxt);
		}
		editor = mSharedPreferences.edit();
		return mPreferenceUtils;
	}

	public int getId() {
		return mSharedPreferences.getInt(ID, 0);
	}

	public void setId(int id) {
		editor.putInt(ID, id);
		editor.commit();
	}

	public int getLoginStatus() {
		return mSharedPreferences.getInt(LOGIN_STATUS, MyApplication.LOG_STATUS_BUYER);
	}

	public void setLoginStatus(int login_status) {
		editor.putInt(LOGIN_STATUS, login_status);
		editor.commit();
	}

	public int getChatUnreadCount(int uid) {
		return mSharedPreferences.getInt(CHAT_UNREAD_COUNT + uid, 0);
	}

	public void setChatUnreadCount(int uid, int count) {
		editor.putInt(CHAT_UNREAD_COUNT + uid, count);
		editor.commit();
	}

	/** 未读消息数量加一 */
	public void addChatUnreadCountIncresed(int uid) {
		int count = getChatUnreadCount(uid);
		editor.putInt(CHAT_UNREAD_COUNT + uid, count + 1);
		editor.commit();
	}

	public String getLoginMobile() {
		return mSharedPreferences.getString(LOGIN_MOBILE, "");
	}

	/**
	 * 
	 * @return
	 */
	public void setLoginMobile(String mobile) {
		editor.putString(LOGIN_MOBILE, mobile);
		editor.commit();
	}

	public String getToken() {
		return mSharedPreferences.getString(TOKEN, "");
	}

	/**
	 * 
	 * @return
	 */
	public void setToken(String token) {
		editor.putString(TOKEN, token);
		editor.commit();
	}

	/**
	 * 获取个推的clientId
	 *
	 * @return
	 */
	public String getClientId() {
		return mSharedPreferences.getString(GET_CLIENTID, "");
	}

	/**
	 * 设置个推的clientId
	 *
	 * @param clientId
	 */
	public void setClientId(String clientId) {
		editor.putString(GET_CLIENTID, clientId);
		editor.commit();
	}

	public String getAppInitInfo() {
		return mSharedPreferences.getString(APP_INIT, "");
	}

	/**
	 * 
	 * @return
	 */
	public void setAppInitInfo(String initInfo) {
		editor.putString(APP_INIT, initInfo);
		editor.commit();
	}

	public String getLocal() {
        return mSharedPreferences.getString(LOCAL, "");
	}

	public synchronized void setLocal(String local) {
		editor.putString(LOCAL, local);
		editor.commit();
	}

	public String getNewJoinSkill() {
		return mSharedPreferences.getString(NEWJOIN_SKILL, "");
	}

	public void setNewJoinSkill(String newJoin) {
		editor.putString(NEWJOIN_SKILL, newJoin);
		editor.commit();
	}

	public String getNewJoinNeed() {
		return mSharedPreferences.getString(NEWJOIN_NEED, "");
	}

	public void setNewJoinNeed(String newJoin) {
		editor.putString(NEWJOIN_NEED, newJoin);
		editor.commit();
	}

	/** 清除所有的Preference */
	public void cleanLoginPreference() {
		editor.remove(ID);
        editor.remove(TOKEN);
		editor.commit();
	}
}
