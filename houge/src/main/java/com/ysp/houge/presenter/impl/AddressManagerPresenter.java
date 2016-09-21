package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.impl.AddressModelImpl;
import com.ysp.houge.presenter.IAddressManagerPresenter;
import com.ysp.houge.ui.iview.IAddressManagerPageView;
import com.ysp.houge.ui.me.address.AddAddressActivity;
import com.ysp.houge.ui.me.address.AddressManagerActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * @描述: 地址管理Presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 *
 * @author hx
 * @date 2015年10月23日下午1:02:01
 * @version 1.0
 */
public class AddressManagerPresenter implements IAddressManagerPresenter<List<AddressEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	private IAddressModel iAddressModel;
	private IAddressManagerPageView iAddressManagerPageView;
	private List<AddressEntity> list = new ArrayList<AddressEntity>();
	private int delIndex = -1;
	// 表示无地址数据，添加时为默认
	private int enterPage;
	private List<AddressEntity> data;
	private int position;

	public AddressManagerPresenter(IAddressManagerPageView iAddressManagerPageView) {
		super();
		this.iAddressManagerPageView = iAddressManagerPageView;
		iAddressModel = new AddressModelImpl();
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();

		iAddressModel.getAllAddress(page, MyApplication.getInstance().getCurrentUid(), MyApplication.LOG_STATUS_BUYER,
				new OnNetResponseCallback() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(Object data) {
						if (data != null && data instanceof List<?>) {
							list = (List<AddressEntity>) data;
							if (list.isEmpty() || list.size() < 10) {
								hasDate = false;
							} else {
								hasDate = true;
							}
						}
						iAddressManagerPageView.refreshComplete(list);
					}

					@Override
					public void onError(int errorCode, String message) {
						iAddressManagerPageView.refreshComplete(null);
					}
				});
	}

	@Override
	public void loadMore() {
		page++;
		list.clear();

		iAddressModel.getAllAddress(page, MyApplication.getInstance().getCurrentUid(), MyApplication.LOG_STATUS_BUYER,
				new OnNetResponseCallback() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(Object data) {
						if (data != null && data instanceof List<?>) {
							list = (List<AddressEntity>) data;
							if (list.isEmpty() || list.size() < 10) {
								hasDate = false;
							} else {
								hasDate = true;
							}
						}
						iAddressManagerPageView.loadMoreComplete(list);
					}

					@Override
					public void onError(int errorCode, String message) {
						page--;
						iAddressManagerPageView.loadMoreComplete(list);
					}
				});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void initEnterPage(int enterPage) {
		this.enterPage = enterPage;
	}

	@Override
	public void clickListItem(AdapterView<?> parent, View view, int position, long id) {
		AddressEntity an = null;
		if (iAddressManagerPageView.getListViewHelper().getAdapter().getData().size() > position - 1)
			an = iAddressManagerPageView.getListViewHelper().getAdapter().getData().get(position - 1);
		else
			return;
		switch (enterPage) {
			case AddressManagerActivity.ENTER_PAGE_ME:
				Bundle bundle = new Bundle();
				bundle.putSerializable(AddAddressActivity.KEY, an);
				iAddressManagerPageView.jumpToAddAdress(bundle);
				break;
			case AddressManagerActivity.ENTER_PAGE_CHOOSE_ADDRESS:
				EventBus.getDefault().post(an);
				iAddressManagerPageView.finishPage();
				break;
			default:
				break;
		}
	}

	@Override
	public void longClickListItem(int id,int position, List<AddressEntity> list) {
		this.delIndex = id;
		this.data = list;
		this.position = position;
		iAddressManagerPageView.showDeleteDialog();
	}

	@Override
	public void addAdress() {
		iAddressManagerPageView.jumpToAddAdress(null);
	}
	@Override
	public void setDefaultAddress() {
		// 检查是否只有一个被选中了
		int checkCount = 0;
		int checkIndex = -1;
		for (int i = 0; i < list.size(); i++) {
			LogUtil.setLogWithE("setDefaultAddress", i + list.get(i).is_default);
			if (AddressEntity.DEFAULT_TYPE_YES.equals(list.get(i).is_default)) {
				checkCount++;
				checkIndex = list.get(i).id;
			}
		}

		if (checkCount != 1) {
			iAddressManagerPageView.showToast("您只能设置一个默认地址");
		} else {
			iAddressManagerPageView.showProgress();
			final int loginStaus = MyApplication.getInstance().getLoginStaus();
			if (loginStaus != MyApplication.LOG_STATUS_BUYER) {
				MyApplication.getInstance().setLoginStaus(MyApplication.LOG_STATUS_BUYER);
			}
			// 设置默认
			iAddressModel.setDefault(checkIndex, new OnNetResponseCallback() {

				@Override
				public void onSuccess(Object data) {
					iAddressManagerPageView.hideProgress();
					MyApplication.getInstance().setLoginStaus(loginStaus);
					iAddressManagerPageView.showToast("修改成功");
					iAddressManagerPageView.finishPage();
				}

				@Override
				public void onError(int errorCode, String message) {
					iAddressManagerPageView.hideProgress();
					MyApplication.getInstance().setLoginStaus(loginStaus);
					iAddressManagerPageView.showToast("数据错误");
				}
			});
		}
	}

	@Override
	public void delete() {
		iAddressManagerPageView.showProgress();

	//	AddressEntity addressEntity = data.get(position - 1);
//		if(addressEntity.is_default.equals("yes")){
//			list.get(position - 1).is_default = AddressEntity.DEFAULT_TYPE_NO;
//		}
		iAddressModel.delete(delIndex, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				iAddressManagerPageView.hideProgress();
				iAddressManagerPageView.showToast("删除成功");
				//删除的时候吧默认地址删除is_defual
				refresh();
			}

			@Override
			public void onError(int errorCode, String message) {
				iAddressManagerPageView.hideProgress();
				switch (errorCode) {
					case ResponseCode.TIP_ERROR:
						iAddressManagerPageView.showToast(message);

						break;
					default:
						iAddressManagerPageView.showToast("删除失败");
						break;
				}
			}
		});
	}

}
