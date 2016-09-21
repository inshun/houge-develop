package com.ysp.houge.model.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.ysp.houge.app.AppException;
import com.ysp.houge.app.Constants;
import com.ysp.houge.model.IChooseSchoolListModel;
import com.ysp.houge.model.entity.bean.SchoolEntity;
import com.ysp.houge.model.entity.base.BaseHttpResultEntity;
import com.ysp.houge.model.entity.httpresponse.SchoolListDataEntity;
import com.ysp.houge.utility.FileRead;
import com.ysp.houge.utility.JsonParser;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

public class ChooseSchoolListModelImpl implements IChooseSchoolListModel {
	private static final String SCHOOL_LIST = "schoolList";
	private String schoolListPath = Constants.DB_PATH + File.separator
			+ SCHOOL_LIST;

	@Override
	public void onRequestSchoolList(
			final OnNetResponseCallback onNetResponseListener) {
//		Map<String, String> map = new HashMap<String, String>();
//		/* 数据层操作 */
//		Request<SchoolListDataEntity> request = new Request<SchoolListDataEntity>(
//				HttpApi.getAbsPathUrl(HttpApi.GET_SCHOOL_LIST), map,
//				SchoolListDataEntity.class, BackType.BEAN, true,
//				schoolListPath, true);
//		/* 数据层操作 */
//		HttpRequest.newInstance().request(request,"", new OnNetResponseCallback() {
//
//			@Override
//			public void onSuccess(Object t) {
//				onNetResponseListener.onSuccess(t);
//			}
//
//			@Override
//			public void onError(int errorCode, String message) {
//				onNetResponseListener.onError(errorCode, message);
//			}
//		});
	}

	@Override
	public SchoolListDataEntity getSchoolListFromCache() {
		String cacheData = FileRead.readTxtFile(schoolListPath);
		if (!TextUtils.isEmpty(cacheData)) {
			BaseHttpResultEntity<SchoolListDataEntity> resultEntity;
			try {
				resultEntity = JsonParser
						.deserializeByJson(
								cacheData,
								new TypeToken<BaseHttpResultEntity<SchoolListDataEntity>>() {
								}.getType());
				if (resultEntity != null) {
					return resultEntity.getData();
				}
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private List<SchoolEntity> initTestData() {
		List<SchoolEntity> chooseSchoolEntitys = new ArrayList<SchoolEntity>();
		for (int i = 0; i < 20; i++) {
		}
		return chooseSchoolEntitys;
	}

}
