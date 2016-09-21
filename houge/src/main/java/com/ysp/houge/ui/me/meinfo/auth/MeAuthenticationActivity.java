package com.ysp.houge.ui.me.meinfo.auth;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.AuthPageAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.eventbus.SubmidAuthEvnetBusEntity;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @描述: 认证presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月19日上午10:19:42
 * @version 1.0
 */
public class MeAuthenticationActivity extends BaseFragmentActivity implements OnClickListener, OnPageChangeListener {
	public final static String CURRENT_INDEX_KEY = "index";

	private TextView student, society, company;
	private TextView[] txts = null;
	private ViewPager vp;

	private int index = 0;
	private List<BaseFragment> list;
	private AuthPageAdapter adapter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, MeAuthenticationActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_authentication);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.authentication));
		actionBar.setRightText(R.string.submit);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

		actionBar.setRightClickListenner(this);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		student = (TextView) findViewById(R.id.tv_student_auth);
		society = (TextView) findViewById(R.id.tv_auth);
		company = (TextView) findViewById(R.id.tv_company_auth);
		vp = (ViewPager) findViewById(R.id.vp_auth);

		txts = new TextView[] { student, society, company };
		for (int i = 0; i < txts.length; i++) {
			txts[i].setOnClickListener(this);
		}
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		// 判断是否已经认证
		String auth = MyApplication.getInstance().getUserInfo().verfied;
		if (!TextUtils.isEmpty(auth) && !TextUtils.equals(auth, "0")) {
			YesDialog dialog=new YesDialog(this, new YesOrNoDialogEntity("请注意，您已经通过某一项认证，再次认证将覆盖之前的状态", "", "", getString(R.string.sure)), new OnYesOrNoDialogClickListener() {
				@Override
				public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
				}
			});
			dialog.show();
		}

		list = new ArrayList<BaseFragment>();
		list.add(new StudentAuthFragment());
		list.add(new AuthFragment());
		list.add(new CompanyAuthFragment());

		adapter = new AuthPageAdapter(getSupportFragmentManager(), list);
		vp.setAdapter(adapter);
		vp.addOnPageChangeListener(this);

		if (getIntent().getExtras().containsKey(CURRENT_INDEX_KEY)) {
			index = getIntent().getExtras().getInt(CURRENT_INDEX_KEY);
			vp.setCurrentItem(index);
		}
		txts[index].setBackgroundColor(Color.GREEN);
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
	public void onPageScrollStateChanged(int index) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int index) {
		txts[this.index].setBackgroundColor(Color.WHITE);
		txts[this.index].setTextColor(Color.BLACK);
		this.index = index;
		txts[index].setBackgroundColor(Color.GREEN);
		txts[index].setTextColor(Color.WHITE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_head_right:
			EventBus.getDefault().post(new SubmidAuthEvnetBusEntity(vp.getCurrentItem()));
			break;
		case R.id.tv_student_auth:
			if (index != 0) {
				txts[index].setBackgroundColor(Color.WHITE);
				txts[index].setTextColor(Color.BLACK);
				index = 0;
				vp.setCurrentItem(index);
				txts[index].setBackgroundColor(Color.GREEN);
				txts[index].setTextColor(Color.WHITE);
			}
			break;
		case R.id.tv_auth:
			if (index != 1) {
				txts[index].setBackgroundColor(Color.WHITE);
				txts[index].setTextColor(Color.BLACK);
				index = 1;
				vp.setCurrentItem(index);
				txts[index].setBackgroundColor(Color.GREEN);
				txts[index].setTextColor(Color.WHITE);
			}
			break;
		case R.id.tv_company_auth:
			if (index != 2) {
				txts[index].setBackgroundColor(Color.WHITE);
				txts[index].setTextColor(Color.BLACK);
				index = 2;
				vp.setCurrentItem(index);
				txts[index].setBackgroundColor(Color.GREEN);
				txts[index].setTextColor(Color.WHITE);
			}
			break;
		default:
			txts[1].setBackgroundColor(Color.WHITE);
			txts[1].setTextColor(Color.BLACK);
			txts[2].setBackgroundColor(Color.WHITE);
			txts[2].setTextColor(Color.BLACK);
			index = 0;
			vp.setCurrentItem(index);
			txts[index].setBackgroundColor(Color.GREEN);
			txts[index].setTextColor(Color.WHITE);
			break;
		}
	}
}
