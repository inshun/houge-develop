package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.VersionInfoEntity;

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
public interface ISplashActivity extends IBaseView {
    void checkVersionInfo(VersionInfoEntity info);
}
