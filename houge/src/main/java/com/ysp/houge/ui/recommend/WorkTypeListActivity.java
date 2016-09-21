package com.ysp.houge.ui.recommend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;
import com.ysp.houge.presenter.IWorkTypeListPagePresenter;
import com.ysp.houge.presenter.impl.WorkTypeListPagePresenter;
import com.ysp.houge.ui.base.BaseListOrGridFragmentActivity;
import com.ysp.houge.ui.iview.IWorkTypeListPageView;
import com.ysp.houge.ui.publish.NeedPulishActivity;
import com.ysp.houge.ui.publish.SkillPulishActivity;
import com.ysp.houge.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 工种列表页面
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月14日下午1:02:35
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class WorkTypeListActivity extends BaseListOrGridFragmentActivity<WorkTypeEntity>
		implements IWorkTypeListPageView, OnClickListener, OnItemClickListener {

	/** 买家设置为他服务的时间 */
	public static final String ENTER_KEY_BUYER_SET = "buyer_set";
	/** 买家发布 */
	public static final String ENTER_KEY_SELLER_SET = "seller_set";

	public static final String KEY = "recommendSr";
	private static int buy;

	private GridView mGridView;
	private IWorkTypeListPagePresenter iWorkTypeListPagePresenter;

	private List<SparseArray<ImageView>> listView = new ArrayList<SparseArray<ImageView>>();
	private int img_unCheck = R.drawable.icon_def;// 未选中
	private int img_check;// 选中
	private Button finsh;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, WorkTypeListActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void initActionbar() {
		Bundle bundle = getIntent().getExtras();
		if(bundle.getInt("TAG") ==  2){
			buy = 2;
		}
		if(bundle.getInt("TAG") == 3){
			buy = 3;
		}

		MyActionBar actionBar = new MyActionBar(this);
		if(buy == 2){
			actionBar.setTitle("您关注什么服务和资源");
		}
		if(buy == 3){
			actionBar.setTitle("您关注什么活");
			actionBar.setRightTextColor(R.color.color_app_theme_blue_bg);
		}
		if(buy != 2 && buy !=3){
			if(MyApplication.getInstance().getLoginStaus() == MyApplication.LOG_STATUS_BUYER || buy == 2) {
				actionBar.setTitle("您能提供什么服务或者资源");
			}else if(MyApplication.getInstance().getLoginStaus() == MyApplication.LOG_STATUS_SELLER || buy == 3) {
				actionBar.setTitle("您关注什么活");
			}
		}


		actionBar.setLeftEnable(true);
		if(buy == 2){
			actionBar.setRightText("下一步");
			actionBar.setRightClickListenner(this);
		}
		if(buy != 2 && buy != 3){
			actionBar.setRightText("提交");
			actionBar.setRightClickListenner(this);
		}

		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_work_type_list);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		finsh = (Button) findViewById(R.id.btn_finsh);
		if(buy == 3){
			finsh.setVisibility(View.VISIBLE);
			finsh.setOnClickListener(this);
		}

		mGridView = (GridView) findViewById(R.id.mGridView);
		mGridView.setOnItemClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {


		mAdapter = new ListAdapter(mDatas);
		mGridView.setAdapter(mAdapter);
		iWorkTypeListPagePresenter = new WorkTypeListPagePresenter(this);

		if (getIntent().getExtras().containsKey(KEY)  || buy == 2 || buy == 3) {
			iWorkTypeListPagePresenter.setRecommendStr(getIntent().getExtras().getString(KEY));
		} else {
			showToast(R.string.parameter_error);
			close();
		}
		iWorkTypeListPagePresenter.getWorkTypeList(buy);

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
		// 菜单右键 -->提交
		case R.id.menu_head_right:
			//buy 用于判断是从注册界面进入，还是正常逻辑进入
			iWorkTypeListPagePresenter.submit(buy);
			break;

		//完成
			case R.id.btn_finsh:
			iWorkTypeListPagePresenter.submit(buy);
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		iWorkTypeListPagePresenter.itemClick(position);
	}

	@Override
	public View setDataAtPositon(int position, View convertView, ViewGroup viewGroup) {
		final Holder holder;
		final WorkTypeEntity entity = mDatas.get(position);
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(WorkTypeListActivity.this).inflate(R.layout.item_work_type, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			holder.workTypeTxt.setText(entity.getName());
			// 根据选中与否,设置背景
			if (TextUtils.equals(entity.getIs_watch(), "yes")) {
				if (buy == 2) {
					holder.workTypeTxt.setBackgroundResource(R.drawable.shapa_work_type_orange_sel);
					holder.workTypeTxt.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
				}
				if (buy == 3) {
					holder.workTypeTxt.setBackgroundResource(R.drawable.shapa_work_type_blue_sel);
					holder.workTypeTxt.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
				}

				if(buy != 2 && buy != 3) {
					if (MyApplication.getInstance().getPreferenceUtils().getLoginStatus() == MyApplication.LOG_STATUS_SELLER) {
						holder.workTypeTxt.setBackgroundResource(R.drawable.shapa_work_type_blue_sel);
						holder.workTypeTxt.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
					}
					if (MyApplication.getInstance().getPreferenceUtils().getLoginStatus() == MyApplication.LOG_STATUS_BUYER) {
						holder.workTypeTxt.setBackgroundResource(R.drawable.shapa_work_type_orange_sel);
						holder.workTypeTxt.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
					}
				}
			} else {
				holder.workTypeTxt.setBackgroundResource(R.drawable.shapa_work_type_def);
				holder.workTypeTxt.setTextColor(getResources().getColor(R.color.black));
			}
		}
		return convertView;
	}

	@Override
	public void setList(List<WorkTypeEntity> entities) {
		mDatas.clear();
		mDatas.addAll(entities);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void submit(WorkTypeSetFinishEventBusEntity busEntity) {
		// TODO 结束当前页面，发送EventBus消息给关注设置页面
		EventBus.getDefault().post(busEntity);
		this.finish();
	}


	@Override
	public void jumpToSkillPulishActivity() {
		buy += 5;
		Bundle bundle = new Bundle();
		bundle.putInt("TAG", 2);
		SkillPulishActivity.JumpIn(this, bundle);
		this.finish();
	}

	@Override
	public void jumpToNeedPulishActivity() {
		buy += 5;
		Bundle bundle = new Bundle();
		bundle.putInt("TAG", 2);
		NeedPulishActivity.JumpIn(this, bundle);
		this.finish();
	}



	class Holder {
		private TextView workTypeTxt;

		public Holder(View convertView) {
			workTypeTxt = (TextView) convertView.findViewById(R.id.tv_work_type_txt);
		}

	}
}
