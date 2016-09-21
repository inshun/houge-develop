package com.ysp.houge.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.TextUtils;

public class SMSReceiveUtil {

	/**
	 * 注册短信验证码监听广播
	 */
	public static BroadcastReceiver initSMSListenerReceiver(Context context,
			final Handler handler) {
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);
		BroadcastReceiver smsReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					Object[] objs = (Object[]) bundle.get("pdus");
					for (Object obj : objs) {
						byte[] pdu = (byte[]) obj;
						SmsMessage sms = SmsMessage.createFromPdu(pdu);
						// 短信的内容
						String message = sms.getMessageBody();
						// 短息的手机号。。+86开头？
						String from = sms.getOriginatingAddress();
						if (!TextUtils.isEmpty(from)) {
							String code = patternCode(message);
							if (!TextUtils.isEmpty(code)) {
								Message msg = new Message();
								msg.what = 1;
								msg.obj = code;
								handler.sendMessage(msg);
							}
						}
					}
				}

			}
		};
		context.registerReceiver(smsReceiver, filter);
		return smsReceiver;
	}

	/**
	 * 匹配短信中间的4个数字（验证码等）
	 * 
	 * @param patternContent
	 * @return
	 */
	public static String patternCode(String patternContent) {
		String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
		if (TextUtils.isEmpty(patternContent)) {
			return null;
		}
		Pattern p = Pattern.compile(patternCoder);
		Matcher matcher = p.matcher(patternContent);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
}
