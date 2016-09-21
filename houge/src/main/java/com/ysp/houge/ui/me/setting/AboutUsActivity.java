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
package com.ysp.houge.ui.me.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.SystemUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述: 关于我们页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日下午5:32:18
 * @version 1.0
 */
public class AboutUsActivity extends BaseFragmentActivity {
	/** 版本号显示 */
	private TextView mAboutText;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context,AboutUsActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_about_us);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mAboutText = (TextView) findViewById(R.id.mAboutText);

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(R.string.about_us);
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		// 显示当前版本
		showProgress();
		mAboutText.setText("当前版本:"
				+ SystemUtils.getAppVersionName(AboutUsActivity.this));
//		new SettingModelImpl().getAboutUs(new OnNetResponseCallback() {
//
//			@Override
//			public void onSuccess(Object data) {
//				hideProgress();
//				showToast("获取成功");
//			}
//
//			@Override
//			public void onError(int errorCode, String message) {
//				hideProgress();
//				showToast("获取失败");
//			}
//		});
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("AboutActivity"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("AboutActivity"); // 保证 onPageEnd
		MobclickAgent.onPause(this);
	}
}
