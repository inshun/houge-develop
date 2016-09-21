package com.ysp.houge.ui.me.setting;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.presenter.IChangePasswordPresenter;
import com.ysp.houge.presenter.impl.ChangePasswordPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IChangePasswordPageView;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * @描述:设置页面修改密码
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午11:15:39
 * @version 1.0
 */
public class ChangePasswordActivity extends BaseFragmentActivity
		implements OnClickListener, IChangePasswordPageView {
	private ClearEditText cetOldPwd;
	private ClearEditText cetNewPwd;
	private ClearEditText cetReNewPwd;
	private Button btSubmit;

	private IChangePasswordPresenter iRevisePasswordFromOldPagePresenter;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, ChangePasswordActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_change_password);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		cetOldPwd = (ClearEditText) findViewById(R.id.cet_old_password);
		cetNewPwd = (ClearEditText) findViewById(R.id.cet_new_password);
		cetReNewPwd = (ClearEditText) findViewById(R.id.cet_re_new_password);
		btSubmit = (Button) findViewById(R.id.bt_submit);
		btSubmit.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iRevisePasswordFromOldPagePresenter = new ChangePasswordPresenter(this);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.revise_password));
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_submit:
			iRevisePasswordFromOldPagePresenter.checkCanSubmitState(cetOldPwd.getText().toString().trim(),
					cetNewPwd.getText().toString().trim(), cetReNewPwd.getText().toString().trim());
			break;

		default:
			break;
		}
	}

	@Override
	public void sumitReviseSuccess() {
		finish();
	}

	@Override
	public void showSubmitReviseDialog() {
		createSubmitReviseDialog();
	}

	private void createSubmitReviseDialog() {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity(getString(R.string.sure_revise_password), "",
				getString(R.string.cancel), getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(ChangePasswordActivity.this, dialogEntity,
				new OnYesOrNoDialogClickListener() {

					@Override
					public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
						switch (yesOrNoType) {
						case BtnOk:
							iRevisePasswordFromOldPagePresenter.requstSubmit();
							break;
						default:

							break;
						}
					}
				});
		dialog.show();
	}
}
