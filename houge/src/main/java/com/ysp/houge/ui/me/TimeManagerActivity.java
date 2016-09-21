package com.ysp.houge.ui.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.TimeEntity;
import com.ysp.houge.presenter.ITimeManagerPagePresenter;
import com.ysp.houge.presenter.impl.TimeManagerPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ITimeManagerPageView;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.TimeSector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author it_hu
 * 
 *         时间管理
 *
 */
@SuppressLint("UseSparseArrays")
public class TimeManagerActivity extends BaseFragmentActivity implements ITimeManagerPageView, OnClickListener {
	/** 买家设置为他服务的时间 */
	public static final String ENTER_KEY_BUYER_SET = "buyer_set";
	/** 买家发布 */
	public static final String ENTER_KEY_SELLER_SET = "seller_set";
	private MyActionBar actionBar;
	private int tsSize;// 经过计算的时间View的宽高度，用于设置外层布局高度
	private LinearLayout lineRound;// 时间View的外层布局

	private TimeSector mon, tue, wen, thur, fri, sat, sun;
	private ImageView monForenoon, monAfternoon, monNight;
	private ImageView tueForenoon, tueAfternoon, tueNight;
	private ImageView wenForenoon, wenAfternoon, wenNight;
	private ImageView thurForenoon, thurAfternoon, thurNight;
	private ImageView friForenoon, friAfternoon, friNight;
	private ImageView satForenoon, satAfternoon, satNight;
	private ImageView sunForenoon, sunAfternoon, sunNight;

	private List<TimeSector> listTs = new ArrayList<TimeSector>();
	private List<SparseArray<ImageView>> listView = new ArrayList<SparseArray<ImageView>>();

	private int img_unCheck = R.drawable.icon_def;// 未选中
	private int img_check;// 选中

	private ITimeManagerPagePresenter iTimeManagerPagePresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, TimeManagerActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_time_manager);
	}

	@Override
	protected void initActionbar() {
		actionBar = new MyActionBar(this);
		Bundle bundle = getIntent().getExtras();
		actionBar.setRightText("下一步");
		actionBar.setTitle("可接活时间");
		actionBar.setRightClickListenner(this);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		lineRound = (LinearLayout) findViewById(R.id.line_round);

		// 计算时间View的宽度
		int width = tsSize = SizeUtils.getScreenWidth(this);
		int margen = SizeUtils.dip2px(this, 10) * 2;
		tsSize = (width - margen) / 7;
		lineRound.getLayoutParams().height = tsSize;

		mon = (TimeSector) findViewById(R.id.ts_mon);
		tue = (TimeSector) findViewById(R.id.ts_tue);
		wen = (TimeSector) findViewById(R.id.ts_wen);
		thur = (TimeSector) findViewById(R.id.ts_thur);
		fri = (TimeSector) findViewById(R.id.ts_fri);
		sat = (TimeSector) findViewById(R.id.ts_sat);
		sun = (TimeSector) findViewById(R.id.ts_sun);

		listTs.add(mon);
		listTs.add(tue);
		listTs.add(wen);
		listTs.add(thur);
		listTs.add(fri);
		listTs.add(sat);
		listTs.add(sun);

		// 周一
		monForenoon = (ImageView) findViewById(R.id.iv_mon_forenoon);
		monAfternoon = (ImageView) findViewById(R.id.iv_mon_afternoon);
		monNight = (ImageView) findViewById(R.id.iv_mon_night);

		SparseArray<ImageView> arrayMon = new SparseArray<ImageView>();
		arrayMon.append(0, monForenoon);
		arrayMon.append(1, monAfternoon);
		arrayMon.append(2, monNight);
		listView.add(arrayMon);

		// 周二
		tueForenoon = (ImageView) findViewById(R.id.iv_tue_forenoon);
		tueAfternoon = (ImageView) findViewById(R.id.iv_tue_afternoon);
		tueNight = (ImageView) findViewById(R.id.iv_tue_night);

		SparseArray<ImageView> arrayTue = new SparseArray<ImageView>();
		arrayTue.append(0, tueForenoon);
		arrayTue.append(1, tueAfternoon);
		arrayTue.append(2, tueNight);
		listView.add(arrayTue);

		// 周三
		wenForenoon = (ImageView) findViewById(R.id.iv_wen_forenoon);
		wenAfternoon = (ImageView) findViewById(R.id.iv_wen_afternoon);
		wenNight = (ImageView) findViewById(R.id.iv_wen_night);

		SparseArray<ImageView> arrayWen = new SparseArray<ImageView>();
		arrayWen.append(0, wenForenoon);
		arrayWen.append(1, wenAfternoon);
		arrayWen.append(2, wenNight);
		listView.add(arrayWen);

		// 周四
		thurForenoon = (ImageView) findViewById(R.id.iv_thur_forenoon);
		thurAfternoon = (ImageView) findViewById(R.id.iv_thur_afternoon);
		thurNight = (ImageView) findViewById(R.id.iv_thur_night);

		SparseArray<ImageView> arrayThur = new SparseArray<ImageView>();
		arrayThur.append(0, thurForenoon);
		arrayThur.append(1, thurAfternoon);
		arrayThur.append(2, thurNight);
		listView.add(arrayThur);

		// 周五
		friForenoon = (ImageView) findViewById(R.id.iv_fri_forenoon);
		friAfternoon = (ImageView) findViewById(R.id.iv_fri_afternoon);
		friNight = (ImageView) findViewById(R.id.iv_fri_night);

		SparseArray<ImageView> arrayFri = new SparseArray<ImageView>();
		arrayFri.append(0, friForenoon);
		arrayFri.append(1, friAfternoon);
		arrayFri.append(2, friNight);
		listView.add(arrayFri);

		// 周六
		satForenoon = (ImageView) findViewById(R.id.iv_sat_forenoon);
		satAfternoon = (ImageView) findViewById(R.id.iv_sat_afternoon);
		satNight = (ImageView) findViewById(R.id.iv_sat_night);

		SparseArray<ImageView> arraySat = new SparseArray<ImageView>();
		arraySat.append(0, satForenoon);
		arraySat.append(1, satAfternoon);
		arraySat.append(2, satNight);
		listView.add(arraySat);

		// 周日
		sunForenoon = (ImageView) findViewById(R.id.iv_sun_forenoon);
		sunAfternoon = (ImageView) findViewById(R.id.iv_sun_afternoon);
		sunNight = (ImageView) findViewById(R.id.iv_sun_night);

		SparseArray<ImageView> arraySun = new SparseArray<ImageView>();
		arraySun.append(0, sunForenoon);
		arraySun.append(1, sunAfternoon);
		arraySun.append(2, sunNight);
		listView.add(arraySun);

		// 添加完了循环设置点击事件
		for (int i = 0; i < listView.size(); i++) {
			for (int j = 0; j < 3; j++) {
				listView.get(i).get(j).setOnClickListener(this);
			}
		}

        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_SELLER:
                img_check = R.drawable.icon_sel_blue;
                break;
            case MyApplication.LOG_STATUS_BUYER:
                img_check = R.drawable.icon_sel_orange;
                break;
        }
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iTimeManagerPagePresenter = new TimeManagerPresenter(this);
		// 根据页面做不同的处理
		List<TimeEntity> listDate = null;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.containsKey(ENTER_KEY_BUYER_SET)) {
				iTimeManagerPagePresenter.setEnterPage(ENTER_KEY_BUYER_SET);
				//actionBar.setTitle("为我服务的时段");
				// 获取设置页面获取到的服务时间
				String json = bundle.getString(ENTER_KEY_BUYER_SET);
				listDate = new Gson().fromJson(json, new TypeToken<ArrayList<TimeEntity>>() {
				}.getType());
				iTimeManagerPagePresenter.setData(listDate);
			}
		} else {
			iTimeManagerPagePresenter.setEnterPage(ENTER_KEY_SELLER_SET);
			//actionBar.setTitle("可接活时间");
			// 获取我的服务时间
			iTimeManagerPagePresenter.getMySettingTime();
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
	public void initAllCheck(List<TimeEntity> listDate) {
		// 如果经过以上步骤list还是为空，或者size为0,那么初始化全部选中
		if (null == listDate || listDate.size() == 0) {
			// 初始化所有数据为选中
			listDate = new ArrayList<TimeEntity>();
			for (int i = 0; i < 7; i++) {
				TimeEntity entity = new TimeEntity();
				entity.setWeek_day(i + 1);
				entity.setMorning(TimeEntity.have_time);
				entity.setNoon(TimeEntity.have_time);
				entity.setNight(TimeEntity.have_time);

				listDate.add(entity);
			}
			iTimeManagerPagePresenter.setData(listDate);
		}
	}

	@Override
	public void initView(List<TimeEntity> list) {
        if (null == list || list.isEmpty() || list.size() != listView.size()){
            showToast("错误的信息");
            return;
        }
		for (int i = 0; i < listView.size(); i++) {
			// 为每一个时间View设置数据
			listTs.get(i).setEntity(list.get(i));

			if (list.get(i).getMorning() == TimeEntity.have_time) {
				listView.get(i).get(0).setImageResource(img_check);
			} else {
				listView.get(i).get(0).setImageResource(img_unCheck);
			}

			if (list.get(i).getNoon() == TimeEntity.have_time) {
				listView.get(i).get(1).setImageResource(img_check);
			} else {
				listView.get(i).get(1).setImageResource(img_unCheck);
			}

			if (list.get(i).getNight() == TimeEntity.have_time) {
				listView.get(i).get(2).setImageResource(img_check);
			} else {
				listView.get(i).get(2).setImageResource(img_unCheck);
			}
		}
	}

	@Override
	public void postDate(List<TimeEntity> list) {
        if (null != list) {
            Gson gson = new Gson();
            EventBus.getDefault().post(gson.toJson(list));
        }else {
            EventBus.getDefault().post("");
        }
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 右上菜单，已知功能 提交
		case R.id.menu_head_right:
			// 买家设置为他服务时间的提交
			iTimeManagerPagePresenter.submit();
			break;

		default:
			// 选中点击事件
			if (v.getTag() != null) {
				String tag = (String) v.getTag();
				int i = Integer.parseInt(String.valueOf(tag.charAt(0)));
				int j = Integer.parseInt(String.valueOf(tag.charAt(1)));

				// 改变对应位置的对应值
				iTimeManagerPagePresenter.changeValue(i, j);
			} else {
				LogUtil.setLogWithE("TimeManagerActivity onClick", "TAG is null");
			}
			break;
		}
	}
}
