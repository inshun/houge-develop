package com.ysp.houge.model.impl;

import android.text.TextUtils;
import android.util.SparseArray;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IFileUploadModel;
import com.ysp.houge.model.entity.bean.PictureEntity;
import com.ysp.houge.utility.ImageReduceUtils;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.MD5Utils;
import com.ysp.houge.utility.volley.HttpFileRequest;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述:图片上传model层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月20日下午5:26:37
 * @version 1.0
 */
public class FileUploadModelImpl implements IFileUploadModel {
	@Override
	public void uploadSingleFile(final int orderid, String path, String dataKey, String upload_from,
			final OnNetResponseCallback netResponseCallback) {
		Map<String, File> files = new HashMap<String, File>();
		files.put("file", new File(path));
		Map<String, String> params = new HashMap<String, String>();
		params.put("upload_from", upload_from);
        params.put(HttpRequest.PARAME_NAME_PT,HttpRequest.PARAME_VALUE_PT);
        params.put(HttpRequest.PARAME_NAME_VR,HttpRequest.PARAME_VALUE_VR);
        params.put(HttpRequest.PARAME_NAME_APT,String.valueOf(MyApplication.LOG_STATUS_SELLER));
        String token = MyApplication.getInstance().getPreferenceUtils().getToken();
        if (!TextUtils.isEmpty(token)){
            params.put(HttpRequest.PARAME_NAME_TOKEN, token);
        }
        String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)){
            BDLocation location = new Gson().fromJson(local,BDLocation.class);
            params.put(HttpRequest.PARAME_NAME_GPS, location.getLatitude() + "," + location.getLongitude());
        }
        params.put(HttpRequest.PARAME_NAME_SIGN,MD5Utils.MD5(MD5Utils.CreateLinkString(params) + MD5Utils.SING));
		final PictureEntity pictureEntity = new PictureEntity();
		pictureEntity.setOrderid(orderid);
		Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.UPLOAD_IMAGE), params, null,
				BackType.STRING, false);
        request.setType(com.android.volley.Request.Method.GET);
		/* 数据层操作 */
		HttpFileRequest.newInstance().addPostUploadFileRequest(request, dataKey, upload_from, files,
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object t) {
						if (t != null && t instanceof String) {
							pictureEntity.setPath((String) t);
							netResponseCallback.onSuccess(pictureEntity);
						} else {
							netResponseCallback.onError(ResponseCode.SUCCESS_BACK_PARSER_ERROR, "");
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						netResponseCallback.onError(errorCode, message);
						LogUtil.setLogWithE("onError", errorCode + message);
					}
				});

	}

	@Override
	public void uploadMultiFile(final List<String> picturePaths, String dataKey, String upload_from,
			final OnNetResponseCallback onNetResponseCallback) {
		// 如果图片列表为空,上传失败
		if (null == picturePaths || 0 == picturePaths.size()) {
			onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "本地路径未找到");
			return;
		}
		final SparseArray<String> listResultPath = new SparseArray<String>();
		final int len = picturePaths.size();
		for (int i = 0; i < len; i++) {
			uploadSingleFile(i, ImageReduceUtils.compression(picturePaths.get(i)), dataKey, upload_from, new OnNetResponseCallback() {

				@Override
				public void onSuccess(Object t) {
					if (t != null && t instanceof PictureEntity) {
						// 将返回的地址以及上传顺序插入回传集合(listResultPath)中
						listResultPath.put(((PictureEntity) t).getOrderid(), ((PictureEntity) t).getPath());
						// 如果两个集合的大小一样,则上传成功
						if (listResultPath.size() == len) {
							List<String> pathes = new ArrayList<String>();
							for (int j = 0, count = listResultPath.size(); j < count; j++) {
								pathes.add(listResultPath.get(j));
							}
							// 返回一个排序好先后顺序的列表
							onNetResponseCallback.onSuccess(pathes);
						}
					}
				}

				@Override
				public void onError(int errorCode, String message) {
					// 只要有个文件上传失败,则认为图片上传失败,
					onNetResponseCallback.onError(ResponseCode.INVALID_ERROR, "上传失败");
				}
			});
		}
	}
}
