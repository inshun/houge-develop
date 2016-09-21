package com.ysp.houge.ui.useless;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ysp.houge.R;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.EditChooseViewDescriptor;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.presenter.IRevisePhonePagePresenter;
import com.ysp.houge.presenter.impl.RevisePhonePagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IRevisePhonePageView;
import com.ysp.houge.widget.EditChooseView;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:设置页面修改手机号
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午11:13:14
 * @version 1.0
 */
public class ChangePhoneActivity extends BaseFragmentActivity implements OnClickListener, IRevisePhonePageView {
	private EditChooseView et_password;
	private EditChooseView et_newPhone;
	private Button bt_submit;

	private IRevisePhonePagePresenter iRevisePhonePagePresenter;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, ChangePhoneActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_change_phone);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		et_password = (EditChooseView) findViewById(R.id.et_password);
		et_newPhone = (EditChooseView) findViewById(R.id.et_newPhone);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		bt_submit.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		initPasswordView();
		initPhoneView();
		iRevisePhonePagePresenter = new RevisePhonePagePresenter(this);
	}

	private void initPasswordView() {
		EditChooseViewDescriptor descriptor = new EditChooseViewDescriptor(getString(R.string.input_password),
				getString(R.string.input_login_password), getResources().getColor(R.color.color_actionbar_bg));
		et_password.initData(descriptor);
		et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		et_password.notifyDataSetChanged();
	}

	private void initPhoneView() {
		EditChooseViewDescriptor descriptor = new EditChooseViewDescriptor(getString(R.string.new_telephone_number),
				getString(R.string.sure_correct), getResources().getColor(R.color.color_actionbar_bg));
		et_newPhone.initData(descriptor);
		et_newPhone.setInputType(InputType.TYPE_CLASS_PHONE);
		et_newPhone.notifyDataSetChanged();
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.revise_mobile));
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_submit:
			iRevisePhonePagePresenter.checkCanSubmitState(et_password.getText(), et_newPhone.getText());
			break;

		default:
			break;
		}
	}

	@Override
	public void sumitReviseSuccess() {
		et_password.setText("");
		et_newPhone.setText("");
	}

	@Override
	public void showSubmitReviseDialog() {
		createSubmitReviseDialog();
	}

	private void createSubmitReviseDialog() {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity(getString(R.string.sure_revise_telephone), "",
				getString(R.string.cancel), getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(ChangePhoneActivity.this, dialogEntity,
				new OnYesOrNoDialogClickListener() {

					@Override
					public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
						switch (yesOrNoType) {
						case BtnOk:
							iRevisePhonePagePresenter.requstSubmit();
							break;

						default:
							break;
						}
					}
				});
		dialog.show();
	}
}
