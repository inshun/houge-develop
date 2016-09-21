package com.ysp.houge.utility;

import com.alipay.sdk.app.PayTask;
import com.ysp.houge.app.PayResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

@SuppressLint("HandlerLeak")
public class AliPayUtils {
	private AlipayResponeCallBack alipayResponeCallBack;
	public Handler alipayHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				// 支付结果返回码9000代表成功，8000代表支付结果确认中
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为"9000"则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					alipayResponeCallBack.paySuccess(resultInfo);
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						alipayResponeCallBack.payWait();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						alipayResponeCallBack.payError(msg.what);
					}
				}
				break;
			}
			case 2: {
				// 检查
				break;
			}
			default:
				break;
			}
		};
	};
	private String sign;

	public AliPayUtils() {
	}

	public AliPayUtils(AlipayResponeCallBack alipayResponeCallBack, String sign) {
		this.alipayResponeCallBack = alipayResponeCallBack;
		this.sign = sign;
	}

	/** 开始支付 */
	public void alipay(final Activity activity) {
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(sign,true);

				Message msg = new Message();
				msg.what = 1;
				msg.obj = result;
				alipayHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	public void check(final Activity activity) {
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(activity);
				String result = alipay.pay(sign,true);

				Message msg = new Message();
				msg.what = 2;
				msg.obj = result;
				alipayHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	public void setAlipayResponeCallBack(AlipayResponeCallBack alipayResponeCallBack) {
		this.alipayResponeCallBack = alipayResponeCallBack;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public interface AlipayResponeCallBack {
		void paySuccess(String sign);

		void payWait();

		void payError(int errorCode);

		// void payCheck();
	}

}
