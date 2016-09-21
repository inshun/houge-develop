package com.ysp.houge.ui.useless;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ysp.houge.R;
import com.ysp.houge.ui.base.BaseFragment;

/**
 * @描述:个人认证成功提示页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月4日上午11:08:36
 * @version 1.0
 */
public class PriAuthSubmitSuccessTipFragment extends BaseFragment {

	private Button mBtn;

	@Override
	protected void initActionbar(View view) {

	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_sumbit_success;
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		mBtn = (Button) view.findViewById(R.id.mBtn);
		mBtn.setText(getString(R.string.sure));
		mBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {

	}

}
