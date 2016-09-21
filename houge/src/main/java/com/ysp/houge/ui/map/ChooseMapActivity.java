package com.ysp.houge.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:选择地图页面(设置常驻地页面)
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月23日下午2:30:57
 * @version 1.0
 */
public class ChooseMapActivity extends BaseFragmentActivity implements OnClickListener {
	/** 进入页面纬度key */
	public static final String LATITUDE = "latitude";
	/** 进入页面经度key */
	public static final String LONGITUDE = "longitude";
	public MyLocationListenner myListener = new MyLocationListenner();
	// 定位相关
	LocationClient mLocClient;
	LocationChooseEventBusEntity localEntity = new LocationChooseEventBusEntity();
	private GeoCoder mSearch;
	private MapView mMapView;
	private Button mSureBtn;
	private InfoWindow mInfoWindow;
	OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				// 没有检索到结果
				return;
			}
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				// 没有找到检索结果
			}
			mMapView.getMap().clear();

			// 为InfoWindow创建View
            View view = LayoutInflater.from(ChooseMapActivity.this).inflate(R.layout.view_map_choose_map,null);
            TextView textView = (TextView) view.findViewById(R.id.tv_address);
            textView.setText(result.getAddress());

			// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            //positionHeight = findViewById(R.id.iv_icon).getLayoutParams().height / 2 + SizeUtils.dip2px(ChooseMapActivity.this,10);
			mInfoWindow = new InfoWindow(view, result.getLocation(),
					(int) (SizeUtils.dip2px(ChooseMapActivity.this, -50)));
			// 显示InfoWindow
			mMapView.getMap().showInfoWindow(mInfoWindow);

			MapStatus mapStatus = new MapStatus.Builder().target(result.getLocation()).build();
			MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
			mMapView.getMap().animateMapStatus(mapStatusUpdate);

            localEntity.address = result.getAddress();
			localEntity.province = result.getAddressDetail().province;
			localEntity.city = result.getAddressDetail().city;
			localEntity.latitude = result.getLocation().latitude;
			localEntity.langtitude = result.getLocation().longitude;
			localEntity.getResult = true;
		}
	};
    //地址文本便宜高度
    private int positionHeight;
	private boolean isFirstLoc = true;
	/** 进入页面的标记值 */
	private int enterPager;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, ChooseMapActivity.class);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	@Override
	protected void initializeState(Bundle savedInstanceState) {
		super.initializeState(savedInstanceState);
		if (getIntent() != null) {
			if (getIntent().getExtras().containsKey(LATITUDE)) {
				localEntity.latitude = getIntent().getExtras().getDouble(LATITUDE);
			}

			if (getIntent().getExtras().containsKey(LONGITUDE)) {
				localEntity.langtitude = getIntent().getExtras().getDouble(LONGITUDE);
			}
		} else {
			LogUtil.setLogWithE("ChooseMapActivity", "no intent, the intent is null");
			finish();
		}
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_choose_map);
		check();
	}

	public void check(){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			//申请WRITE_EXTERNAL_STORAGE权限
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					100);
		}else {
			//已赋予权限
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode==100){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 允许
			} else {
					//无权限
					showToast("请打开对应的权限，否者会导致App无法正常运行！");
			}
		}
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mMapView = (MapView) findViewById(R.id.mMapView);
		mSureBtn = (Button) findViewById(R.id.mSureBtn);
		mSureBtn.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(listener);
		mMapView.showZoomControls(false);
		mMapView.showScaleControl(false);
		mMapView.getMap().getUiSettings().setCompassEnabled(false);
		mMapView.getMap().getUiSettings().setRotateGesturesEnabled(false);
		mMapView.getMap().getUiSettings().setOverlookingGesturesEnabled(false);
		mMapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));
		mMapView.getMap().setMaxAndMinZoomLevel(3, 18);
		if (localEntity.latitude > 0 && localEntity.langtitude > 0) {
			mSearch.reverseGeoCode(
					new ReverseGeoCodeOption().location(new LatLng(localEntity.latitude, localEntity.langtitude)));
		} else {
			// 开启定位图层
			mMapView.getMap().setMyLocationEnabled(true);
			// 定位初始化
			mLocClient = new LocationClient(this);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(1000);
			mLocClient.setLocOption(option);
			mLocClient.start();
		}

		mMapView.getMap().setOnMapTouchListener(new OnMapTouchListener() {

			@Override
			public void onTouch(MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_UP:
                    if (null == mMapView)
                        return;
					Point point = new Point(mMapView.getMap().getMapStatus().targetScreen.x,
							mMapView.getMap().getMapStatus().targetScreen.y);
					LatLng location = mMapView.getMap().getProjection().fromScreenLocation(point);
					mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(location));
					break;

				default:
					break;
				}

			}
		});
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("服务地址");
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

    @Override
	public void onClick(View v) {
        EventBus.getDefault().post(localEntity);
        finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		if (mLocClient != null) {
			// 退出时销毁定位
			mLocClient.stop();
		}
		// 关闭定位图层
		mMapView.getMap().setMyLocationEnabled(false);
//		mMapView.onDestroy();
		mSearch.destroy();
		mMapView = null;
	}

	@Override
	public void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
        MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
        MobclickAgent.onPause(this);
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			if (isFirstLoc) {
				isFirstLoc = false;
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
						.location(new LatLng(location.getLatitude(), location.getLongitude())));
				/** 销毁定位，因为此处我们只需要进入时定位一次即可 */
				mLocClient.stop();

			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
}
