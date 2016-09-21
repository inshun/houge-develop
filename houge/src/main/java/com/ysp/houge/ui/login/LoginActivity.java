package com.ysp.houge.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.presenter.ILoginPresenter;
import com.ysp.houge.presenter.impl.LoginPresenter;
import com.ysp.houge.ui.HomeActivity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ILoginPageView;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述: 登录页面
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月9日下午8:20:01
 * @version 1.0
 */
public class
LoginActivity extends BaseFragmentActivity implements OnClickListener, ILoginPageView {
	public static final String USER_MOBILE = "user_mobile";

	private static final String MOBILE = "mobile";
	private static final String PASSWORD = "password";

	/** 手机号码编辑框 */
	private ClearEditText mTelephoneView;
	/** 密码编辑框 */
	private ClearEditText mPasswordView;
	/** 登录按钮 */
	private Button mLoginBtn;
	/** 忘记密码按钮 */
	private TextView mForgotPassword;
	/** 新用户 */
	private TextView mNewUSer;

	/** 用户填写的手机号码 */
	private String mobile;
	/** 用户填写的密码 */
	private String password;

	private ILoginPresenter iLoginPresenter;

	/** 进入登陆页面 */
	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, LoginActivity.class));
	}

	/** 进入登陆页面 */
	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, LoginActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_login);
		check();
	}

	public void check(){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
				!= PackageManager.PERMISSION_GRANTED) {
			//申请WRITE_EXTERNAL_STORAGE权限
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
					100);
		}else {
			//已赋予过权限
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode==100){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 允许
			} else {
					//无权限
					showToast("请打开对应的权限，否者会导致App无法正常运行！");
			}
		}
	}


	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.login));
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mTelephoneView = (ClearEditText) findViewById(R.id.cet_phone_num);
		mPasswordView = (ClearEditText) findViewById(R.id.cet_password);
		mLoginBtn = (Button) findViewById(R.id.mLoginBtn);
		mForgotPassword = (TextView) findViewById(R.id.mForgotPassword);
		mNewUSer = (TextView) findViewById(R.id.mNewUser);

		mLoginBtn.setOnClickListener(this);
		mForgotPassword.setOnClickListener(this);
		mNewUSer.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iLoginPresenter = new LoginPresenter(this);

		if (null != getIntent() && null != getIntent().getExtras()
				&& getIntent().getExtras().containsKey(USER_MOBILE)) {
			mobile = getIntent().getExtras().getString(USER_MOBILE);
		}

		if (!TextUtils.isEmpty(mobile)) {
			mTelephoneView.setText(mobile);
		}

		mTelephoneView.requestFocus();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 保存页面状态
		mobile = mTelephoneView.getText().toString();
		password = mPasswordView.getText().toString();
		outState.putString(MOBILE, mobile);
		outState.putString(PASSWORD, password);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// 获取保存的页面状态
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(MOBILE)) {
				mobile = savedInstanceState.getString(MOBILE);
			}
			if (savedInstanceState.containsKey(PASSWORD)) {
				password = savedInstanceState.getString(PASSWORD);
			}
		}
		if (!TextUtils.isEmpty(mobile)) {
			mTelephoneView.setText(mobile);
		}
		if (!TextUtils.isEmpty(password)) {
			mPasswordView.setText(password);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
        //忘记密码
		case R.id.mForgotPassword:
			FindPwdActivity.jumpIn(this);
			break;

        //登录
		case R.id.mLoginBtn:
			iLoginPresenter.login(mTelephoneView.getText().toString().trim(),
                    mPasswordView.getText().toString().trim());
			break;

        //注册
		case R.id.mNewUser:
			RegisterActivity.jumpIn(this);
			break;
		default:
			break;
		}
	}

	@Override
	public void junpToHome() {
		Bundle bundle = new Bundle();
        //告诉主页是登录进入
		//1表示登录进主页
		bundle.putInt("TAG", 1);
		HomeActivity.jumpIn(this, bundle);
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		switch (keyCode){
////			case KeyEvent.KEYCODE_BACK:
////				System.exit(0);
//		}
//		return false;
//	}
}
