package com.ysp.houge.presenter.impl;

import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.comparator.SchoolNameComparator;
import com.ysp.houge.model.IChooseSchoolListModel;
import com.ysp.houge.model.entity.bean.SchoolEntity;
import com.ysp.houge.model.entity.eventbus.SchoolChooseEventBusEntity;
import com.ysp.houge.model.entity.httpresponse.SchoolListDataEntity;
import com.ysp.houge.model.impl.ChooseSchoolListModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IChooseSchoolPresenter;
import com.ysp.houge.ui.iview.IChooseSchoolView;
import com.ysp.houge.utility.SortLetterUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * @描述:学校列表页面presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月14日上午8:29:07
 * @version 1.0
 */
public class ChooseSchoolListPresenter extends BasePresenter<IChooseSchoolView>
		implements IChooseSchoolPresenter {
	public static final int JumpFromPriInfoPage = 1;
	public static final int JumpFromPriAuthPage = 2;
	public static final int JumpFromPaidAccountPage = 3;

	private IChooseSchoolListModel iListModel;

	private int whichPageFrom;

	public ChooseSchoolListPresenter(IChooseSchoolView view, int whichPageFrom) {
		super(view);
		this.whichPageFrom = whichPageFrom;
	}

	@Override
	public void initModel() {
		this.iListModel = new ChooseSchoolListModelImpl();
	}

	@Override
	public void getSchoolListRequest(int cityId) {
		// parserData(iListModel.getSchoolListFromCache());
		mView.showProgress();
		iListModel.onRequestSchoolList(new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (data != null && data instanceof SchoolListDataEntity) {
					parserData((SchoolListDataEntity) data);
				} else {
					mView.showToast(R.string.request_failed);
				}
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

	public void parserData(final SchoolListDataEntity schoolListDataEntity) {
		if (schoolListDataEntity != null
				&& schoolListDataEntity.getChooseSchoolEntities() != null) {
			final List<SchoolEntity> schoolEntities = schoolListDataEntity
					.getChooseSchoolEntities();
			new AsyncTask<String, Void, String>() {
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
				}

				@Override
				protected String doInBackground(String... params) {
					SchoolEntity schoolEntity = null;
					for (int i = 0; i < schoolEntities.size(); i++) {
						schoolEntity = schoolListDataEntity
								.getChooseSchoolEntities().get(i);
						schoolEntity.setSortLetter(SortLetterUtil
								.getSortLetter(schoolEntity.getName()));
					}
					// 进行排序
					Collections
							.sort(schoolEntities, new SchoolNameComparator());
					return null;
				}

				@Override
				protected void onPostExecute(String path) {
					super.onPostExecute(path);
					mView.setAllSchoolList(schoolEntities);
				}

			}.execute();

		} else {
			mView.showToast(R.string.request_failed);
		}

	}

	@Override
	public void clickListItem(AdapterView<?> parent, View v, int position,
			long id) {
		SchoolEntity schoolEntity = (SchoolEntity) parent.getAdapter().getItem(
				position);
		if (schoolEntity != null) {
			EventBus.getDefault()
					.post(new SchoolChooseEventBusEntity(whichPageFrom,
							schoolEntity));
			mView.close();
		}
	}
}