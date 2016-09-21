package com.ysp.houge.ui.useless;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.EditChooseViewDescriptor;
import com.ysp.houge.presenter.IRevisePaidAccountPagePresenter;
import com.ysp.houge.presenter.impl.RevisePaidAccountPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IRevisePaidAccountPageView;
import com.ysp.houge.widget.EditChooseView;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:收款账号
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日上午10:21:43
 * @version 1.0
 */
public class MeInfoPaidAccountActivity extends BaseFragmentActivity implements
		IRevisePaidAccountPageView {
	public static final String PAID_ACCOUNT = "paidAccount";
	private String paidAccount;

	private EditChooseView et_accountNum;

	private IRevisePaidAccountPagePresenter iRevisePaidAccountPagePresenter;

	@Override
	protected void initializeState(Bundle savedInstanceState) {
		super.initializeState(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(PAID_ACCOUNT)) {
				paidAccount = savedInstanceState.getString(PAID_ACCOUNT);
			}
		} else if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(PAID_ACCOUNT)) {
				paidAccount = getIntent().getExtras().getString(PAID_ACCOUNT);
			}
		} else {
			finish();
		}
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_info_paidaccount);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		et_accountNum = (EditChooseView) findViewById(R.id.et_accountNum);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		initAccountNumView();

		if (!TextUtils.isEmpty(paidAccount)) {
			et_accountNum.setText(paidAccount);
		}

		iRevisePaidAccountPagePresenter = new RevisePaidAccountPagePresenter(
				this);
	}

	private void initAccountNumView() {
		EditChooseViewDescriptor descriptor = new EditChooseViewDescriptor(
				getString(R.string.paid_account),
				getString(R.string.input_alipay_or_bank_card), getResources()
						.getColor(R.color.color_actionbar_bg));
		et_accountNum.initData(descriptor);
		et_accountNum.setEditGarvity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		et_accountNum.notifyDataSetChanged();
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.paid_account));
		actionBar.setLeftEnable(true);
		actionBar.setLeftText(getString(R.string.back));
		actionBar.setRightText(getString(R.string.submit));
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iRevisePaidAccountPagePresenter.clickRevise(et_accountNum
						.getText());
			}
		});
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	public void sumitReviseSuccess() {

	}

}
