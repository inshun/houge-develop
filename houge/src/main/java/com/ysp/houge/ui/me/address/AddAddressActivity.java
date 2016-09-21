package com.ysp.houge.ui.me.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.eventbus.AddAddressSuccessEventBusEntity;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.presenter.IAddAddressPresenter;
import com.ysp.houge.presenter.impl.AddAddressPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IAddAddressPageView;
import com.ysp.houge.ui.map.ChooseMapActivity;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述: 添加地址
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 *
 * @author hx
 * @date 2015年10月23日下午2:17:27
 * @version 1.0
 */
public class AddAddressActivity extends BaseFragmentActivity implements IAddAddressPageView, OnClickListener {
	public static final String KEY = "address";

	private EditText mapAddress; // 地图地点
	private EditText detailsAddress;// 详细地址
	private EditText contacts;// 联系人
	private EditText contactNum;// 联系电话

	private IAddAddressPresenter iAddAddressPagePresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, AddAddressActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_add_address);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("添加服务地址");
		actionBar.setLeftEnable(true);
		actionBar.setRightText("提交");
		actionBar.setRightClickListenner(this);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);

		mapAddress = (EditText) findViewById(R.id.et_map_address);
		detailsAddress = (EditText) findViewById(R.id.et_details_address);
		contacts = (EditText) findViewById(R.id.et_contact);
		contactNum = (EditText) findViewById(R.id.et_contact_num);

		mapAddress.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
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
	protected void initializeData(Bundle savedInstanceState) {
		iAddAddressPagePresenter = new AddAddressPresenter(this);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(KEY)) {
			iAddAddressPagePresenter.setAddress((AddressEntity) getIntent().getExtras().getSerializable(KEY));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 提交添加
		case R.id.menu_head_right:
			iAddAddressPagePresenter.submit(detailsAddress.getText().toString().toString().trim(),
                    contacts.getText().toString().toString().trim(), contactNum.getText().toString().toString().trim());
			break;
		// 地图选点
		case R.id.et_map_address:
			iAddAddressPagePresenter.chooseMapAddress();
			break;
		default:
			break;
		}
	}

	@Override
	public void showAddress(AddressEntity addressEntity) {
		if (addressEntity != null) {
			// 截断地址
			StringBuilder sb = new StringBuilder();
			sb.append(addressEntity.street);
			if (sb.indexOf(",") > 0) {
				// 如果有逗号,拆分
				mapAddress.setText(sb.subSequence(0, sb.indexOf(",")));
				detailsAddress.setText(sb.subSequence(sb.indexOf(",") + 1, sb.length()));
			} else {
				mapAddress.setText(sb);
				detailsAddress.setText("");
			}

			contacts.setText(addressEntity.real_name);
			contactNum.setText(addressEntity.mobile);
		}
	}

	@Override
	public void jumpToMapChooseAddress(AddressEntity addressEntity) {
		Bundle bundle = new Bundle();
		if (addressEntity != null) {
			bundle.putDouble(ChooseMapActivity.LATITUDE, addressEntity.latitude);
			bundle.putDouble(ChooseMapActivity.LONGITUDE, addressEntity.longitude);
		}
		ChooseMapActivity.jumpIn(this, bundle);
	}

	@Override
	public void finishAdd() {
		// 发送消息 通知列表页面刷新
		EventBus.getDefault().post(new AddAddressSuccessEventBusEntity());
		this.finish();
	}

	@Override
	public void showChooseMap(String address) {
		mapAddress.setText(address);
	}

	/** 接收地图页面的地址信息 */
	public void onEventMainThread(LocationChooseEventBusEntity busEntity) {
		iAddAddressPagePresenter.chooseMapFinish(busEntity);
	}
}
