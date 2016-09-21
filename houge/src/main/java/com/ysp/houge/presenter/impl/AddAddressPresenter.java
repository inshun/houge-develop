package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.model.impl.AddressModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IAddAddressPresenter;
import com.ysp.houge.ui.iview.IAddAddressPageView;
import com.ysp.houge.utility.MatcherUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * @描述: 添加地址页面Presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月25日上午8:51:13
 * @version 1.0
 */
public class
AddAddressPresenter extends BasePresenter<IAddAddressPageView> implements IAddAddressPresenter {

	private boolean isMapChoose = false;
	private AddressEntity addressEntity;
	private LocationChooseEventBusEntity localEntity;

	private IAddressModel iAddressModel;

	public AddAddressPresenter(IAddAddressPageView view) {
		super(view);
	}

	@Override
	public void setAddress(AddressEntity addressEntity) {
		this.addressEntity = addressEntity;
		mView.showAddress(addressEntity);
	}

	@Override
	public void chooseMapAddress() {
		mView.jumpToMapChooseAddress(addressEntity);
	}

	@Override
	public void initModel() {
		iAddressModel = new AddressModelImpl();
	}

	@Override
	public void submit(String street, String contacts, String contactNum) {
		if (TextUtils.isEmpty(street)) {
			mView.showToast("请输入详细地址");
			return;
		}

		if (TextUtils.isEmpty(contacts)) {
			mView.showToast("请输入联系人");
			return;
		}

		if (TextUtils.isEmpty(contactNum)) {
			mView.showToast(R.string.please_input_telephone);
			return;
		}

		if (!MatcherUtil.checkTelephoneNumberVilid(contactNum)) {
			mView.showToast("请输入正确的手机号");
			return;
		}

		if (!isMapChoose && addressEntity == null) {
			mView.showToast("请先选择一个地图地点");
			return;
		}
		mView.showProgress();
		int user_id = MyApplication.getInstance().getCurrentUid();

		// 处理修改以及编辑问题
		if (addressEntity != null) {
			// 修改
			editAddress(user_id, street, contacts, contactNum);
		} else {
			// 添加
			addAddress(user_id, contacts, contactNum, localEntity.province, localEntity.city, street, false,
					localEntity.langtitude, localEntity.latitude);
		}
	}

	/** 修改地址 */
	private void editAddress(int user_id, String street, String contacts, String contactNum) {
		// 处理街道字段
		// 这里的街道字段是地图传过来的(在传过来的时候已经处理过地址里面的省份问题)
		// 因为是编辑,所以要去掉之前的stree字段即逗号后面的
		StringBuilder sb = new StringBuilder();
		if (addressEntity.street.indexOf(",") > 0) {
			sb.append(addressEntity.street.subSequence(0, addressEntity.street.indexOf(",")));
		}else {
			sb.append(addressEntity.street);
		}
		sb.append(",");// 分隔符
		sb.append(street);// 用户填写的详细地址

		// 处理是否默认地址问题
		boolean isDefault = false;
		if (!TextUtils.equals(AddressEntity.DEFAULT_TYPE_NO, addressEntity.is_default)) {
			isDefault = true;
		}

		iAddressModel.addOrUpdateAddress(MyApplication.LOG_STATUS_BUYER, addressEntity.id, user_id, contacts,
				contactNum, addressEntity.province, addressEntity.city, sb.toString(), isDefault,
				addressEntity.longitude, addressEntity.latitude, new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						mView.hideProgress();
						if (data != null && data instanceof String) {
							mView.showToast("修改成功");
						} else {
							mView.showToast("数据错误");
						}
						mView.finishAdd();
					}

					@Override
					public void onError(int errorCode, String message) {
						mView.hideProgress();
						switch (errorCode) {
						case ResponseCode.TIP_ERROR:
							mView.showToast(message);
							break;
						default:
							mView.showToast(R.string.request_failed);
							break;
						}
					}
				});
	}

	/** 添加地址 */
	private void addAddress(int user_id, String contacts, String contactNum, String province, String city,
			String street, boolean b, double langtitude, double latitude) {
		// 处理上传的街道字段（地图取点不需要省份字段,加上用户详细描述的地点,中间用‘,’分割，其他地方有他用）
		// 这里为了保险起见,是将地图取点的地址中去掉地图中返回的省份字段
		// 这样防止了直辖市(京、沪、津、渝)的取值问题
		String ad = localEntity.address.replaceAll(localEntity.province, "");
		ad += ",";
		ad += street;

		iAddressModel.addOrUpdateAddress(MyApplication.LOG_STATUS_BUYER, 0, user_id, contacts, contactNum,
				localEntity.province, localEntity.city, ad, false, localEntity.langtitude, localEntity.latitude,
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						mView.finishAdd();
						if (data != null && data instanceof String) {
							mView.showToast("添加成功");
						} else {
							mView.showToast("数据错误");
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						switch (errorCode) {
						case ResponseCode.TIP_ERROR:
							mView.showToast(message);
							break;
						default:
							mView.showToast(R.string.request_failed);
							break;
						}
					}
				});
	}

	@Override
	public void chooseMapFinish(LocationChooseEventBusEntity busEntity) {
		if (busEntity.getResult) {
			isMapChoose = busEntity.getResult;
			localEntity = busEntity;
			mView.showChooseMap(localEntity.address);

			if (addressEntity != null) {
				// addressEntity不为null事代表修改 需要将 地图返回的经纬度，地址等信息赋值给addressEntity
				addressEntity.latitude = busEntity.latitude;
				addressEntity.longitude = busEntity.langtitude;
				// 街道(去掉省份字段)
				addressEntity.street = localEntity.address.replaceAll(localEntity.province, "");
				addressEntity.province = busEntity.province;
				addressEntity.city = busEntity.city;
				mView.showChooseMap(addressEntity.street);
				return;
			}
		}else {
			isMapChoose = false;
			mView.showChooseMap("错误的信息，请再次选取");
        }
	}

}
