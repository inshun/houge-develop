package com.ysp.houge.ui.recommend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.TimeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.eventbus.RecommendSettingFinishEantity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;
import com.ysp.houge.presenter.IRecommendSkillSttingPresneter;
import com.ysp.houge.presenter.impl.recmmend.RecommendSkillSettingPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IRecommendSkillSettingPageView;
import com.ysp.houge.ui.me.TimeManagerActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.TimeSector;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 买家关注设置View层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月4日下午8:09:17
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class RecommendSkillSettingActivity extends BaseFragmentActivity
		implements IRecommendSkillSettingPageView, OnClickListener {
	public final static String WORK_TYPE_KEY = "workType";

	private TextView tvAdd;// 点击添加的提示文本
	private TextView textView;// 显示需求列表的文本

	private int size;
	private LinearLayout layoutTime;
	private TimeSector mon, tue, wen, thur, fri, sat, sun;// 服务时间
	private TextView monT, tueT, wenT, thurT, friT, satT, sunT;// 服务时间
	private List<TimeSector> listView;
	private List<TextView> listTxt;

	private int color_orange;
	private int color_gray;

	private IRecommendSkillSttingPresneter iRecommendSkillSttingPresneter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, RecommendSkillSettingActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_recommend_skill_setting);
	}

	@Override
	protected void initActionbar() {
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		findViewById(R.id.line_buyer_recommend_setting).setOnClickListener(this);
		findViewById(R.id.rela_work_type_list).setOnClickListener(this);
		findViewById(R.id.line_recommend_list).setOnClickListener(this);
		findViewById(R.id.line_service_time).setOnClickListener(this);

		layoutTime = (LinearLayout) findViewById(R.id.line_time_sector);

		int screenWidth = SizeUtils.getScreenWidth(this);
		int margin = SizeUtils.dip2px(this, 16);
		size = (screenWidth - margin) / 7;
		layoutTime.getLayoutParams().height = size;

		textView = (TextView) findViewById(R.id.tv_recommend_list);
		tvAdd = (TextView) findViewById(R.id.tv_cliick_to_add);

		tvAdd.setOnClickListener(this);

		mon = (TimeSector) findViewById(R.id.ts_mon);
		tue = (TimeSector) findViewById(R.id.ts_tue);
		wen = (TimeSector) findViewById(R.id.ts_wen);
		thur = (TimeSector) findViewById(R.id.ts_thur);
		fri = (TimeSector) findViewById(R.id.ts_fri);
		sat = (TimeSector) findViewById(R.id.ts_sat);
		sun = (TimeSector) findViewById(R.id.ts_sun);

		listView = new ArrayList<TimeSector>();
		listView.add(mon);
		listView.add(tue);
		listView.add(wen);
		listView.add(thur);
		listView.add(fri);
		listView.add(sat);
		listView.add(sun);

		monT = (TextView) findViewById(R.id.tv_mon);
		tueT = (TextView) findViewById(R.id.tv_tue);
		wenT = (TextView) findViewById(R.id.tv_wen);
		thurT = (TextView) findViewById(R.id.tv_thur);
		friT = (TextView) findViewById(R.id.tv_fri);
		satT = (TextView) findViewById(R.id.tv_sat);
		sunT = (TextView) findViewById(R.id.tv_sun);

		listTxt = new ArrayList<TextView>();
		listTxt.add(monT);
		listTxt.add(tueT);
		listTxt.add(wenT);
		listTxt.add(thurT);
		listTxt.add(friT);
		listTxt.add(satT);
		listTxt.add(sunT);

		color_orange = getResources().getColor(R.color.color_app_theme_orange_bg);
		color_gray = getResources().getColor(R.color.color_e5e5e5);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {

		iRecommendSkillSttingPresneter = new RecommendSkillSettingPresenter(this);
		iRecommendSkillSttingPresneter.getServiceTime();
		if (getIntent().getExtras().containsKey(WORK_TYPE_KEY)) {
			// 获得设置的关注点并显示在上面
			notifyListDate(getIntent().getExtras().getString(WORK_TYPE_KEY));
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			jumpToHome(false);
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 完成设置
		case R.id.line_buyer_recommend_setting:
			iRecommendSkillSttingPresneter.setFinish();
			break;

		// 工种列表
		case R.id.tv_recommend_list:
		case R.id.tv_cliick_to_add:
		case R.id.line_recommend_list:
		case R.id.rela_work_type_list:
			iRecommendSkillSttingPresneter.addSkillList();
			break;

		// 服务时间
		case R.id.line_service_time:
			iRecommendSkillSttingPresneter.setServiceTime();
			break;
		}
	}

	@Override
	public void notifyListDate(List<WorkTypeEntity> entities) {
		if (entities == null || entities.size() < 1) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < entities.size(); i++) {
			if (TextUtils.equals(entities.get(i).getIs_watch(), "yes")) {
				sb.append(entities.get(i).getName());
				sb.append(" 丨 ");
			}
		}

		sb.delete(sb.length() - 3, sb.length());
		notifyListDate(sb.toString());
	}

	@Override
	public void notifyListDate(String string) {
		if (TextUtils.isEmpty(string)) {
			tvAdd.setVisibility(View.VISIBLE);
		} else {
			tvAdd.setVisibility(View.GONE);
			textView.setText(string);
		}
	}

	@Override
	public void notifyServiceTimeDate(List<TimeEntity> entities) {
		for (int i = 0; i < entities.size(); i++) {
			listView.get(i).setEntity(entities.get(i));

			int count = 0;
			if (entities.get(i).getMorning() == TimeEntity.have_time) {
				count++;
			}

			if (entities.get(i).getNoon() == TimeEntity.have_time) {
				count++;
			}

			if (entities.get(i).getNight() == TimeEntity.have_time) {
				count++;
			}

			if (count > 0) {
				listTxt.get(i).setTextColor(color_orange);
			} else {
				listTxt.get(i).setTextColor(color_gray);
			}
		}
	}

	@Override
	public void jumpToHome(boolean isRefresh) {
		// TODO 回到首页
		if (isRefresh) {
			// 发送消息通知首页刷新
			EventBus.getDefault().post(new RecommendSettingFinishEantity());
		}
		finish();
		overridePendingTransition(R.anim.activity_stay, R.anim.slide_out_to_bottom);
	}

	@Override
	public void jumpToSkillList() {
		// TODO 跳转到能力列表
		Bundle bundle = new Bundle();
		bundle.putString(WorkTypeListActivity.KEY, textView.getText().toString());
		WorkTypeListActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToTimeManager(Bundle bundle) {
		// TODO 跳转到时间管理页面
		TimeManagerActivity.jumpIn(this, bundle);
	}

	/** 接收工种列表页面的数据 */
	public void onEventMainThread(WorkTypeSetFinishEventBusEntity busEntity) {
		iRecommendSkillSttingPresneter.finishAddSkillList(busEntity);
	}

	/** 接收时间页面的数据 */
	public void onEventMainThread(String string) {
		iRecommendSkillSttingPresneter.setServiceTimeFinish(string);
	}

}
