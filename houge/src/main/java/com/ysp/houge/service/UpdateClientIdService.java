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
package com.ysp.houge.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ysp.houge.utility.PreferenceUtils;

/**
 * This class is used for 开启服务更新个推的clientId到服务器
 * 
 * @author tyn
 * @version 1.0, 2014-11-20 下午5:45:57
 */

public class UpdateClientIdService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int uid = PreferenceUtils.getInstance(this).getId();
		if (uid != 0) {
			updateClientId();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void updateClientId() {
		stopSelf();
	}

}
