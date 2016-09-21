package com.ysp.houge.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ysp.houge.R;

import java.text.MessageFormat;

/**
 * @描述:toast提示工具类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author WL
 * @date 2015年3月19日下午1:44:05
 * @version 1.0
 */
public class Toaster {
	private static Toaster mInstance;
	private final Context mContext;
	private final Handler mHToaster;
	private Toast toast;

	@SuppressLint("HandlerLeak")
	private Toaster(Context context) {
		this.mContext = context;
		this.mHToaster = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				showToast((String) msg.obj, msg.what);
			}
		};
	}

	public static void init(Context context) {
		if (mInstance != null)
			throw new IllegalStateException("Toaster has been inited.");
		mInstance = new Toaster(context);
	}

	public static Toaster getInstance() {
		if (mInstance == null)
			throw new IllegalStateException("Toaster has not been inited.");
		return mInstance;
	}

	private void showToast(String mes, int imgres) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.customer_toast_layout, null);
		ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);
		// 从layout中按照id查找imageView对象
		ImageView imageView = (ImageView) layout.findViewById(R.id.ivForToast);
		// 设置ImageView的图片
		if (0 == imgres || -1 == imgres) {
			imageView.setVisibility(View.GONE);
		} else {
			imageView.setBackgroundResource(imgres);
			imageView.setVisibility(View.VISIBLE);
		}
		// 从layout中按照id查找TextView对象
		TextView textView = (TextView) layout.findViewById(R.id.tvForToast);
		// 设置TextView的text内容
		textView.setText(mes);
		// 实例化一个Toast对象
		if (null == toast) {
			toast = new Toast(mContext);

			toast.setDuration(Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.BOTTOM, 20, 20);
			toast.setGravity(Gravity.CLIP_VERTICAL, 0, 0);
		}
		toast.setView(layout);
		toast.show();
	}

	public void displayToast(String str) {
		Message msg = Message.obtain(mHToaster, 0, str);
		msg.sendToTarget();
	}

	public void displayToast(int res) {
		displayToast(mContext.getString(res));
	}

	public void displayToast(int formatRes, Object[] params) {
		displayToast(MessageFormat
				.format(mContext.getString(formatRes), params));
	}

	public void displayToast(String str, int imgres) {
		Message msg = Message.obtain(mHToaster, imgres, str);
		msg.sendToTarget();
	}

	public void displayToast(int strres, int imgres) {
		displayToast(mContext.getString(strres), imgres);
	}

	public void displayToast(int formatRes, Object[] params, int imgres) {
		displayToast(
				MessageFormat.format(mContext.getString(formatRes), params),
				imgres);
	}
}
