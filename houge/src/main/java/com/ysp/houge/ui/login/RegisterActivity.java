package com.ysp.houge.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.presenter.IRegisterPresenter;
import com.ysp.houge.presenter.impl.RegisterPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IRegisterPageView;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:注册页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月16日下午2:41:01
 * @version 1.0
 */
public class RegisterActivity extends BaseFragmentActivity implements IRegisterPageView, OnClickListener {
	private IRegisterPresenter iRegisterPresenter;
	private ClearEditText mPhoneNum, mPassword;
	private EditText mCodeNum;
	private TextView mVoiceCode;
	private Button mSent, mNextStup;
	private SendCountDown countDown;

    public static void jumpIn(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_register);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.register));
//		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mPhoneNum = (ClearEditText) findViewById(R.id.cet_phone_num);
		mCodeNum = (EditText) findViewById(R.id.et_code);
		mPassword = (ClearEditText) findViewById(R.id.cet_password);

        //这一部分暂时已经隐藏，如需显示，需要判断全局身份变量后设置对应的字体颜色
		//mVoiceCode = (TextView) findViewById(R.id.tv_send_voice_sms);
		//mVoiceCode.setText(Html.fromHtml("收不到验证短信？点击获取 <font color='#fbb217'>语音验证码</font>"));
		//mVoiceCode.setOnClickListener(this);

		mSent = (Button) findViewById(R.id.btn_get_code);
		mNextStup = (Button) findViewById(R.id.btn_next);

		mSent.setOnClickListener(this);
		mNextStup.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
        //实例化Presenter层对象
        iRegisterPresenter = new RegisterPresenter(this);
        //实例化短信发送倒计时类对象
		countDown = new SendCountDown(60 * 1000, 1000);
	}

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
        //Activity销毁的时候，倒计时跟着取消并销毁
		if (countDown != null) {
			countDown.cancel();
            countDown = null;
		}
	}

	@Override
	public void sendCodeCountDown(int countDownSecond) {
        //不同的全局变量身份使用不同的文字颜色
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_BUYER:
		        mSent.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                break;
            case MyApplication.LOG_STATUS_SELLER:
                mSent.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                break;
        }
        //设置发送按钮不可点击
		mSent.setClickable(false);
        //倒计时开始
		countDown.start();
	}

	@Override
	public void jumpToWriteInfo(Bundle bundle) {
		PerfectRegisterInfoActivity.jumpIn(this, bundle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 语音验证码
		case R.id.tv_send_voice_sms:
            iRegisterPresenter.getVoiceCode(mPhoneNum.getText().toString().trim());
			break;

		// 验证码
		case R.id.btn_get_code:
            if (DoubleClickUtil.isFastClick())
                return;
            iRegisterPresenter.getCode(mPhoneNum.getText().toString().trim());
			break;

        // 下一步
		case R.id.btn_next:
            iRegisterPresenter.nextStup(mPhoneNum.getText().toString().trim(), mCodeNum.getText().toString().trim(),
					mPassword.getText().toString());
			break;
		default:
			break;
		}
	}

	/** 短信发送倒计时器 */
	private class SendCountDown extends CountDownTimer {

		public SendCountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
            //还原文本颜色
			mSent.setTextColor(getResources().getColor(R.color.color_555555));
            //还原文本
			mSent.setText(getString(R.string.reset_send));
            //可以点击
			mSent.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mSent.setText(getString(R.string.reset_send) + "(" + millisUntilFinished / 1000 + ")");
		}
    }
}
