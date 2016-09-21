package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.List;

/**
 * @描述:文件上传model层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月20日下午5:26:10
 * @version 1.0
 */
public interface IFileUploadModel {

	void uploadSingleFile(int orderid,String path, String dataKey, String upload_from, OnNetResponseCallback netResponseCallback);

	void uploadMultiFile(List<String> picturePaths, String dataKey, String upload_from, OnNetResponseCallback onNetResponseCallback);
}
