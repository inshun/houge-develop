package com.ysp.houge.ui.nearby;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.entity.eventbus.ChangeLoginStatusEventBusEntity;
import com.ysp.houge.popwindow.MapPopupWindow;
import com.ysp.houge.presenter.INearbyMapPresenter;
import com.ysp.houge.presenter.impl.NearbyMapPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.INearbyMapPageView;
import com.ysp.houge.utility.DoubleClickUtil;

import java.util.List;
/**
 * 描述： 附近地图页面
 *
 * @ClassName: NearbyMapFragment
 *
 * @author: hx
 *
 * @date: 2015年12月9日 下午1:31:22
 *
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class NearbyMapFragment extends BaseFragment implements INearbyMapPageView, OnMarkerClickListener {
	public MyLocationListenner myListener = new MyLocationListenner();
	private ImageView local;
	private MapView mMapView;
	// 定位相关
	private LocationClient mLocClient;
	// 构建Marker图标
	private BitmapDescriptor bitmap = null;
	private WorkTypeEntity workTypeEntity;
	private INearbyMapPresenter iNearbyMapPresenter;
	@Override
	protected int getContentView() {
		return R.layout.fragment_map_nearby;
	}
	@Override
	protected void initActionbar(View view) {
	}
	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		local = (ImageView) view.findViewById(R.id.iv_local);
		mMapView = (MapView) view.findViewById(R.id.mMapView);
		local.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
				if (TextUtils.isEmpty(local))
					return;
				BDLocation location = new  Gson().fromJson(local,BDLocation.class);
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
						.latitude(latLng.latitude).longitude(latLng.longitude).build();
				mMapView.getMap().setMyLocationData(locData);
				MapStatus mapStatus = new MapStatus.Builder().target(latLng).build();
				MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
				mMapView.getMap().animateMapStatus(mapStatusUpdate);
			}
		});
	}
	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iNearbyMapPresenter = new NearbyMapPresenter(this);
		iNearbyMapPresenter.setWorkType(workTypeEntity);
		// 缩放按钮隐藏
		mMapView.showZoomControls(false);
		mMapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(14).build()));
		mMapView.getMap().setMaxAndMinZoomLevel(12, 18);
		setWorkTypeEntity(workTypeEntity);
		mMapView.getMap().setOnMarkerClickListener(this);
		startLocal();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		// 关闭定位图层
		mMapView.getMap().setMyLocationEnabled(false);
		// 回收 bitmap 资源
		if (bitmap != null) {
			bitmap.recycle();
		}
		if (mLocClient != null) {
			// 退出时销毁定位
			mLocClient.stop();
		}
		mMapView.onDestroy();
	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName());
		mMapView.onResume();
	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClass().getSimpleName());
		mMapView.onPause();
	}
	public void setWorkTypeEntity(WorkTypeEntity workTypeEntity) {
		this.workTypeEntity = workTypeEntity;
	}
	@Override
	public void startLocal() {
		showProgress();
		// 开启定位图层
		mMapView.getMap().setMyLocationEnabled(true);
		// 定位初始化
		if (null == mLocClient) {
			mLocClient = new LocationClient(getActivity());
			mLocClient.registerLocationListener(myListener);
		}
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000 * 10);// 10秒定位扫描一次
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
	@Override
	public void showMapMarkerList(final List<UserInfoEntity> list) {
		if (list != null && list.size() > 0) {
			mMapView.getMap().clear();
			// 循环添加地图marker点
			for (int i = 0; i < list.size(); i++) {
				final UserInfoEntity entity = list.get(i);
				final  LatLng point = new LatLng(list.get(i).latitude, list.get(i).longitude);
				// 创建marker的View对象
				final View v = LayoutInflater.from(getActivity()).inflate(R.layout.view_map_icon, null);
				RelativeLayout bg = (RelativeLayout) v.findViewById(R.id.line_bg);
				ImageView icon = (ImageView) v.findViewById(R.id.iv_user_avatar);
				// 性别，背景框
				switch (entity.sex) {
					case UserInfoEntity.SEX_MAL:
						bg.setBackgroundResource(R.drawable.icon_map_user_mal);
						break;
					case UserInfoEntity.SEX_FEMAL:
						bg.setBackgroundResource(R.drawable.icon_map_user_famal);
						break;
					default:
						bg.setBackgroundResource(R.drawable.icon_map_user_def);
						break;
				}
				// 加载头像
				if (!TextUtils.isEmpty(entity.avatar)) {
					final int index = i;
					ImageLoader.getInstance().displayImage(entity.avatar, icon, MyApplication.getInstance().getImageLoaderManager().getmAvatarOptions(), new ImageLoadingListener() {
						@Override
						public void onLoadingStarted(String s, View view) {
						}
						@Override
						public void onLoadingFailed(String s, View view, FailReason failReason) {
							// 构建Marker的Bulder信息
							Bundle bundle = new Bundle();
							bundle.putInt("user_id", entity.id);
							NearbyMapFragment.this.bitmap = BitmapDescriptorFactory.fromView(v);
							// 定义Maker坐标点
							// 构建MarkerOption，用于在地图上添加Marker
							OverlayOptions option = new MarkerOptions().position(point).icon(NearbyMapFragment.this.bitmap).extraInfo(bundle);
							// 在地图上添加Marker，并显示
							mMapView.getMap().addOverlay(option);
						}
						@Override
						public void onLoadingComplete(String s, View view, Bitmap bitmap) {
							// 构建Marker的Bulder信息
							Bundle bundle = new Bundle();
							bundle.putInt("user_id", entity.id);
							bundle.putString("goods_info_ids", entity.goods_info_ids);
							NearbyMapFragment.this.bitmap = BitmapDescriptorFactory.fromView(v);
							// 定义Maker坐标点
							// 构建MarkerOption，用于在地图上添加Marker
							OverlayOptions option = new MarkerOptions().position(point).icon(NearbyMapFragment.this.bitmap).extraInfo(bundle);
							// 在地图上添加Marker，并显示
							mMapView.getMap().addOverlay(option);
						}
						@Override
						public void onLoadingCancelled(String s, View view) {
							// 构建Marker的Bulder信息
							Bundle bundle = new Bundle();
							bundle.putInt("user_id", entity.id);
							NearbyMapFragment.this.bitmap = BitmapDescriptorFactory.fromView(v);
							// 定义Maker坐标点
							// 构建MarkerOption，用于在地图上添加Marker
							OverlayOptions option = new MarkerOptions().position(point).icon(NearbyMapFragment.this.bitmap).extraInfo(bundle);
							// 在地图上添加Marker，并显示
							mMapView.getMap().addOverlay(option);
						}
					});
				}
			}
		} else {
			mMapView.getMap().clear();
		}
		hideProgress();
	}
	/** 附近页面关注点修改的消息 */
	public void onEventMainThread(WorkTypeEntity workTypeEntity) {
		setWorkTypeEntity(workTypeEntity);
		if (isAdded() && isVisible()) {
			startLocal();
		}
	}
	/** 身份改变后刷新导数据 */
	public void onEventMainThread(ChangeLoginStatusEventBusEntity entity) {
		if (isAdded() && isVisible()) {
			startLocal();
		}
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		if (DoubleClickUtil.isFastClick()) {
			return false;
		}
		// Marker点击事件
		Bundle bundle = marker.getExtraInfo();
		if (null != bundle && bundle.containsKey("user_id")) {
			// 获取到了UserID(显示用户的需求or技能pop)
			new MapPopupWindow(bundle.getInt("user_id"),  bundle.getString("goods_info_ids"),getActivity());
		}
		return false;
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
			//移动定位点到屏幕中心
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					.latitude(latLng.latitude).longitude(latLng.longitude).build();
			mMapView.getMap().setMyLocationData(locData);
			MapStatus mapStatus = new MapStatus.Builder().target(latLng).build();
			MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
			mMapView.getMap().animateMapStatus(mapStatusUpdate);
			// 根据当前的登录身份 加载不同的 数据
			int logStatus = MyApplication.getInstance().getLoginStaus();
			if (null == iNearbyMapPresenter)
				return;
			switch (logStatus) {
				case MyApplication.LOG_STATUS_BUYER:
					iNearbyMapPresenter.setWorkType(workTypeEntity);
					iNearbyMapPresenter.getNearBySkillList(location.getLatitude(), location.getLongitude());
					break;
				case MyApplication.LOG_STATUS_SELLER:
					iNearbyMapPresenter.setWorkType(workTypeEntity);
					iNearbyMapPresenter.getNearByNeedList(location.getLatitude(), location.getLongitude());
					break;
			}
			mLocClient.stop();
		}
		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
}