package com.ysp.houge.ui.useless;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ysp.houge.R;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:预支工资说明页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月12日下午1:45:07
 * @version 1.0
 */
public class MePrePaidExplainActivity extends BaseFragmentActivity {

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_pri_paid_explain);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {

	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.explain));
		actionBar.setLeftEnable(false);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

}
