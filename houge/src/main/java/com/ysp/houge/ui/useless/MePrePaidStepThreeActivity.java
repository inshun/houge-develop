package com.ysp.houge.ui.useless;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.eventbus.FinishActivityEventBusEntity;
import com.ysp.houge.model.entity.eventbus.FinishActivityEventBusEntity.FinishViewType;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IBaseView;
import com.ysp.houge.widget.MyActionBar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * @描述:预支工资第三部，申请提交成功
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日上午10:20:00
 * @version 1.0
 */
public class MePrePaidStepThreeActivity extends BaseFragmentActivity implements
		IBaseView {
	private Button bt_confirm;
//	private PaidSalaryStepView mPaidSalaryStepView;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_pre_paid_step_three);

	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		bt_confirm = (Button) findViewById(R.id.bt_confirm);
		bt_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				close();
				// 关闭第一步页面
				EventBus.getDefault().post(
						new FinishActivityEventBusEntity(
								FinishViewType.MePrePaidStepOneActivity));
				// 关闭第二步页面
				EventBus.getDefault().post(
						new FinishActivityEventBusEntity(
								FinishViewType.MePrePaidStepTwoActivity));
			}
		});
//		mPaidSalaryStepView = (PaidSalaryStepView) findViewById(R.id.mPaidSalaryStepView);
//		mPaidSalaryStepView.switchPage(2);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("预支工资");
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

	}

}
