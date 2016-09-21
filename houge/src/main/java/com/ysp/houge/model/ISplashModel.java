package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/8/3
 * Email: m13478943650@163.com
 */
public interface ISplashModel {
    void getVersionInfoFromNet(OnNetResponseCallback callback);
}
