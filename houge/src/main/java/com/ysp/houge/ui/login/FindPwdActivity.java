package com.ysp.houge.ui.login;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IRegisterAndLoginModel;
import com.ysp.houge.presenter.IFindPwdPresenter;
import com.ysp.houge.presenter.impl.FindPwdPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IFindPwdPageView;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

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

/**
 * @描述: 找回密码View层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月17日下午9:44:53
 * @version 1.0
 */
public class FindPwdActivity extends BaseFragmentActivity implements OnClickListener, IFindPwdPageView {
	private ClearEditText mMobileEdit; // 手机号
	private EditText mCheckCodeView;// 验证码
	private Button mResendBtn;// 发送短信验证码
	private TextView mSendVoiceCode;// 发送语音验证码
	private ClearEditText mNewPassword; // 新密码
	private Button mFinishBtn;// 完成

    //发送短信倒计时类
	private TimeCount timeCount;
    //短信接收的广播
	//private BroadcastReceiver smsReceiver;
    //控制层对象
	private IFindPwdPresenter iFindPwdPresenter;

    public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, FindPwdActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_find_pwd);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.find_password));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mCheckCodeView = (EditText) findViewById(R.id.mCheckCodeView);
		mMobileEdit = (ClearEditText) findViewById(R.id.mMobileEdit);
		mSendVoiceCode = (TextView) findViewById(R.id.tv_voice_code);
		mNewPassword = (ClearEditText) findViewById(R.id.et_new_password);
		mResendBtn = (Button) findViewById(R.id.mResendBtn);
		mFinishBtn = (Button) findViewById(R.id.mFinishBtn);

        //语音短信，暂时禁用，启用后需要对全局身份变量进行识别后设置不同的颜色
		//mSendVoiceCode = (TextView) findViewById(R.id.tv_voice_code);
		//mSendVoiceCode.setText(Html.fromHtml("收不到验证短信？点击获取 <font color='#fbb217'>语音验证码</font>"));
		//mSendVoiceCode.setOnClickListener(this);

		mFinishBtn.setOnClickListener(this);
		mResendBtn.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
        iFindPwdPresenter = new FindPwdPresenter(this);
        //短信接收广播
		//smsReceiver = SMSReceiveUtil.initSMSListenerReceiver(this, new Handler() {
			//public void handleMessage(android.os.Message msg) {
				//String message = msg.obj.toString();
				//mCheckCodeView.setText(message);
			//}
		//});
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
		//if (smsReceiver != null) {
			//unregisterReceiver(smsReceiver);
		//}
        //销毁倒计时对象
		if (timeCount != null) {
			timeCount.cancel();
            timeCount = null;
		}
	}

	@Override
	public void startTimeCount(int count) {
        //设置倒计时文字的颜色
		switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_BUYER:
                mResendBtn.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                break;
            case MyApplication.LOG_STATUS_SELLER:
                mResendBtn.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                break;
        }
        //设置发送按钮不可点击
		mResendBtn.setClickable(false);
        //实例化并启动倒计时
		timeCount = new TimeCount(count * 1000, 1000);
		timeCount.start();
	}

	@Override
	public void jumpToLogin(String phoneNum) {
		Bundle bundle = new Bundle();
        //把电话号码传递给
		bundle.putString(LoginActivity.USER_MOBILE, phoneNum);
		LoginActivity.jumpIn(this, bundle);
        //关闭当前页面
		close();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
        //找回密码
		case R.id.mFinishBtn:
            iFindPwdPresenter.finishFindPassword(mMobileEdit.getText().toString(),
					mCheckCodeView.getText().toString().trim(), mNewPassword.getText().toString().trim());
			break;

        //发送短信
		case R.id.mResendBtn:
            //双击判断
            if (DoubleClickUtil.isFastClick())
                return;
            iFindPwdPresenter.getCode(mMobileEdit.getText().toString(), IRegisterAndLoginModel.CODE_TYPE_CODE);
			break;

        //语音验证码
		case R.id.tv_voice_code:
            //双击判断
            if (DoubleClickUtil.isFastClick())
                return;
            iFindPwdPresenter.getCode(mMobileEdit.getText().toString(), IRegisterAndLoginModel.CODE_TYPE_VOICE_CODE);
			break;

		default:
			break;
		}
	}

    /**
     * 描述： 短信发送倒计时类
     */
	public class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
            //文字颜色还原
			mResendBtn.setTextColor(getResources().getColor(R.color.color_555555));
            //文本还原
			mResendBtn.setText(getString(R.string.reset_send));
            //可以点击
			mResendBtn.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mResendBtn.setText(getString(R.string.reset_send) + "(" + millisUntilFinished / 1000 + ")");
		}
	}
}
