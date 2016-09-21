package com.ysp.houge.ui.me.meinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IReviseSexPagePresenter;
import com.ysp.houge.presenter.impl.ReviseSexPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IReviseSexPageView;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:选择性别页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日上午10:21:30
 * @version 1.0
 */
public class MeInfoSexActivity extends BaseFragmentActivity implements OnClickListener, IReviseSexPageView {
	public static final String SEX = "sex";

	/** 默认性别为男 */
	private int sex = UserInfoEntity.SEX_MAL;

	private ImageView iv_sex;

	private IReviseSexPagePresenter iReviseSexPagePresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, MeInfoSexActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void initializeState(Bundle savedInstanceState) {
		super.initializeState(savedInstanceState);
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(SEX)) {
				sex = getIntent().getExtras().getInt(SEX);
			}
		}
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_info_sex);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		iv_sex = (ImageView) findViewById(R.id.iv_sex);
		iv_sex.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iReviseSexPagePresenter = new ReviseSexPagePresenter(this);
		switch (sex) {
		case UserInfoEntity.SEX_MAL:
			iv_sex.setImageResource(R.drawable.icon_sex_male);
			break;
		case UserInfoEntity.SEX_FEMAL:
			iv_sex.setImageResource(R.drawable.icon_sex_female);
			break;
		default:
			sex = UserInfoEntity.SEX_MAL;
			iv_sex.setImageResource(R.drawable.icon_sex_male);
			break;
		}
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
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.sex));
		actionBar.setLeftEnable(true);
		actionBar.setRightText(getString(R.string.submit));
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createSubmitReviseDialog();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_sex:
			sex = sex == UserInfoEntity.SEX_MAL ? UserInfoEntity.SEX_FEMAL : UserInfoEntity.SEX_MAL;
			switch (sex) {
			case UserInfoEntity.SEX_MAL:
				iv_sex.setImageResource(R.drawable.icon_sex_male);
				break;
			case UserInfoEntity.SEX_FEMAL:
				iv_sex.setImageResource(R.drawable.icon_sex_female);
				break;
			default:
				sex = UserInfoEntity.SEX_MAL;
				iv_sex.setImageResource(R.drawable.icon_sex_male);
				break;
			}
		}
	}

	@Override
	public void sumitReviseSuccess() {
		close();
	}

	private void createSubmitReviseDialog() {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity(getString(R.string.sure_revise_sex), "",
				getString(R.string.cancel), getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(MeInfoSexActivity.this, dialogEntity,
				new OnYesOrNoDialogClickListener() {

					@Override
					public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
						switch (yesOrNoType) {
						case BtnOk:
							iReviseSexPagePresenter.clickRevise(sex);
							break;

						default:
							break;
						}
					}
				});
		dialog.show();
	}
}
