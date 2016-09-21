package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.model.IGetWorkTypeModel;
import com.ysp.houge.model.entity.bean.CharacteristicEntity;
import com.ysp.houge.model.impl.GetWorkTypeModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ICharacteristicPresenter;
import com.ysp.houge.ui.iview.ICharacteristicListView;

public class CharacteristicListPresenter extends
		BasePresenter<ICharacteristicListView> implements
		ICharacteristicPresenter {

	private IGetWorkTypeModel iGetJobOrCharacteristicModel;

	public CharacteristicListPresenter(ICharacteristicListView view) {
		super(view);
	}

	@Override
	public void initModel() {
		this.iGetJobOrCharacteristicModel = new GetWorkTypeModelImpl();
	}

	@Override
	public void clickNextStepBtn(
			List<CharacteristicEntity> partTimeJobTypeEntities) {
		if (partTimeJobTypeEntities.isEmpty()) {
			mView.showToast(R.string.characteristic_type_loading_failed);
			return;
		}
		ArrayList<CharacteristicEntity> characteristicEntitiesChoosed = new ArrayList<CharacteristicEntity>();
		CharacteristicEntity characteristicEntity = null;
		for (int i = 0, count = partTimeJobTypeEntities.size(); i < count; i++) {
			characteristicEntity = partTimeJobTypeEntities.get(i);
			if (characteristicEntity.isChecked()) {
				characteristicEntitiesChoosed.add(characteristicEntity);
			}
		}
		if (characteristicEntitiesChoosed.isEmpty()) {
			mView.showToast(R.string.please_choose_characteristic);
			return;
		}
		mView.JumpToNextPage(characteristicEntitiesChoosed);
	}

	@Override
	public void getCharacteristicList() {
//		iGetJobOrCharacteristicModel
//				.getCharacteristicList(new OnNetResponseCallback() {
//
//					@SuppressWarnings("unchecked")
//					@Override
//					public void onSuccess(Object data) {
//						mView.hideProgress();
//						if (data != null && data instanceof List<?>) {
//							mView.setList((List<CharacteristicEntity>) data);
//						}
//					}
//
//					@Override
//					public void onError(int errorCode, String message) {
//						switch (errorCode) {
//						case ResponseCode.TIP_ERROR:
//							mView.hideProgress();
//							mView.showToast(message);
//							break;
//						default:
//							mView.hideProgress();
//							mView.showToast(R.string.request_failed);
//							break;
//						}
//					}
//				});
	}

}
