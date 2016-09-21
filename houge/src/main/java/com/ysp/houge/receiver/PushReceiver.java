package com.ysp.houge.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.ysp.houge.app.MyApplication;

public class PushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取-传数据
			byte[] payload = bundle.getByteArray("payload");
			String taskid = bundle.getString("taskid");
			String messageid = bundle.getString("messageid");

			// smartPush第三方回执调用接口，action_id范围为90000-90999，可根据业务场景执行
			boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
			System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

			if (payload != null) {
				String data = new String(payload);
				PushMessageManager.handlePushMsg(context, data);
			}
			break;

		case PushConsts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			if (!TextUtils.isEmpty(cid)) {
				MyApplication.getInstance().getPreferenceUtils().setClientId(cid);
			}
			break;

		case PushConsts.THIRDPART_FEEDBACK:
			break;
		default:
			break;
		}
	}
}
