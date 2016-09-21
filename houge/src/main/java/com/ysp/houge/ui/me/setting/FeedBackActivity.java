package com.ysp.houge.ui.me.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.presenter.IFeedBackPresenter;
import com.ysp.houge.presenter.impl.FeedBackPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IFeedBackPageView;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:意见反馈页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午10:34:53
 * @version 1.0
 */
public class FeedBackActivity extends BaseFragmentActivity implements OnClickListener, IFeedBackPageView {
	private EditText et_feedBack;
	private Button bt_submit;
	private IFeedBackPresenter iFeedBackPresenter;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, FeedBackActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_feedback);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		et_feedBack = (EditText) findViewById(R.id.et_feedback);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		bt_submit.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iFeedBackPresenter = new FeedBackPresenter(this);

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.advance_feedback));
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
			iFeedBackPresenter.checkCanFeedbackState(et_feedBack.getText().toString().trim());
			break;

		default:
			break;
		}
	}

	@Override
	public void submitFeedBackSuccess() {
		et_feedBack.setText("");
	}

	@Override
	public void showSubmitFeedbackDialog() {
		createSubmitFeedBackDialog();
	}

	private void createSubmitFeedBackDialog() {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity(getString(R.string.sure_feedback), "",
				getString(R.string.cancel), getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(FeedBackActivity.this, dialogEntity,
				new OnYesOrNoDialogClickListener() {

					@Override
					public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
						switch (yesOrNoType) {
						case BtnOk:
							iFeedBackPresenter.requstSubmitFeedBack();
							break;

						default:
							break;
						}
					}
				});
		dialog.show();
	}
}
