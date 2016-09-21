package com.ysp.houge.ui.useless;

import com.ysp.houge.R;
import com.ysp.houge.app.AppManager;
import com.ysp.houge.model.entity.bean.EditChooseViewDescriptor;
import com.ysp.houge.presenter.INewPasswordPagePresenter;
import com.ysp.houge.presenter.impl.NewPasswordPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.INewPasswordPageView;
import com.ysp.houge.widget.EditChooseView;
import com.ysp.houge.widget.MyActionBar;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月10日下午2:46:47
 * @version 1.0
 */
public class WriteNewPasswordActivity extends BaseFragmentActivity implements
		OnClickListener, INewPasswordPageView {
	public static final String MOBILE = "mobile";
	public static final String CHECK_CODE = "checkCode";
	private static final String NEW_PASSWORD = "newPassword";
	private static final String INPUT_PASSWORD_DOUBLE = "intputPasswordDouble";
	private String checkCode;
	private String mobile;
	private String newPassword;
	private String inputPasswordDouble;
	/** 新密码编辑框 */
	private EditChooseView mNewPasswordView;
	/** 再次新密码编辑框 */
	private EditChooseView mInputPasswordDoubleView;
	/** 登录按钮 */
	private Button mFinishBtn;

	private INewPasswordPagePresenter iNewPasswordPagePresenter;

	@Override
	protected void initializeState(Bundle savedInstanceState) {
		super.initializeState(savedInstanceState);
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(CHECK_CODE)) {
				checkCode = getIntent().getExtras().getString(CHECK_CODE);
			}
			if (getIntent().getExtras().containsKey(MOBILE)) {
				mobile = getIntent().getExtras().getString(MOBILE);
			}

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(MOBILE, mobile);
		outState.putString(CHECK_CODE, checkCode);
		outState.putString(NEW_PASSWORD, newPassword);
		outState.putString(INPUT_PASSWORD_DOUBLE, inputPasswordDouble);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState.containsKey(CHECK_CODE)) {
			checkCode = savedInstanceState.getString(CHECK_CODE);
		}
		if (savedInstanceState.containsKey(MOBILE)) {
			mobile = savedInstanceState.getString(MOBILE);
		}
		if (savedInstanceState.containsKey(NEW_PASSWORD)) {
			newPassword = savedInstanceState.getString(NEW_PASSWORD);
		}
		if (savedInstanceState.containsKey(INPUT_PASSWORD_DOUBLE)) {
			inputPasswordDouble = savedInstanceState
					.getString(INPUT_PASSWORD_DOUBLE);
		}

	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_write_new_password);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mNewPasswordView = (EditChooseView) findViewById(R.id.mNewPasswordView);
		mInputPasswordDoubleView = (EditChooseView) findViewById(R.id.mInputPasswordDoubleView);
		mFinishBtn = (Button) findViewById(R.id.mFinishBtn);
		mFinishBtn.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		initNewPasswordView();
		initInputPasswordDoubleView();
		iNewPasswordPagePresenter = new NewPasswordPagePresenter(this);

		if (!TextUtils.isEmpty(newPassword)) {
			mNewPasswordView.setText(newPassword);
		}
		if (!TextUtils.isEmpty(inputPasswordDouble)) {
			mInputPasswordDoubleView.setText(inputPasswordDouble);
		}
	}

	/**
	 * 
	 */
	private void initNewPasswordView() {
		EditChooseViewDescriptor descriptor = new EditChooseViewDescriptor(
				getString(R.string.password_hint),
				getString(R.string.set_new_password));
		mNewPasswordView.initData(descriptor);
		mNewPasswordView.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		mNewPasswordView.notifyDataSetChanged();
	}

	/**
	 * 
	 */
	private void initInputPasswordDoubleView() {
		EditChooseViewDescriptor descriptor = new EditChooseViewDescriptor(
				getString(R.string.keep_agreement),
				getString(R.string.reset_input));
		mInputPasswordDoubleView.initData(descriptor);
		mInputPasswordDoubleView.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		mInputPasswordDoubleView.notifyDataSetChanged();
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.set_new_password));
		actionBar.setLeftEnable(true);
		actionBar.setLeftText(getString(R.string.back));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.mFinishBtn:
			iNewPasswordPagePresenter.revisePassword(
					mNewPasswordView.getText(),
					mInputPasswordDoubleView.getText(), checkCode, mobile);
			break;
		default:
			break;
		}
	}

	@Override
	public void reviseSuccess() {
		AppManager.getAppManager().finishAllActivity();
//		UIHelper.jumpToLogin(WriteNewPasswordActivity.this);
	}

}
